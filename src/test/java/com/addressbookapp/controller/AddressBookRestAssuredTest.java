package com.addressbookapp.controller;

import com.addressbookapp.model.Contact;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpServer;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.web.context.WebApplicationContext;
import com.addressbookapp.service.AddressBookService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class AddressBookRestAssuredTest {
    private static final Gson GSON = new Gson();

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private AddressBookService addressBookService;

    private HttpServer jsonServer;
    private String jsonServerUrl;
    private List<Contact> postedContacts;
    private List<Map<String, String>> jsonServerContacts;
    private AtomicInteger nextJsonServerId;

    @BeforeEach
    void setUp() throws IOException {
        RestAssuredMockMvc.webAppContextSetup(context);
        postedContacts = Collections.synchronizedList(new ArrayList<>());
        jsonServerContacts = Collections.synchronizedList(new ArrayList<>());
        jsonServerContacts.add(createJsonServerContact("1", "Rudresh", "Sharma", "Street 1", "Indore", "MP", "452001", "9000000001", "rudresh@gmail.com"));
        jsonServerContacts.add(createJsonServerContact("2", "Aman", "Verma", "Street 2", "Bhopal", "MP", "462001", "9000000002", "aman@gmail.com"));
        nextJsonServerId = new AtomicInteger(3);

        jsonServer = HttpServer.create(new InetSocketAddress(0), 0);
        jsonServer.createContext("/contacts", exchange -> {
            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();

            if ("GET".equalsIgnoreCase(method) && "/contacts".equals(path)) {
                Map<String, String> queryParams = parseQueryParams(exchange.getRequestURI().getRawQuery());
                String firstNameFilter = queryParams.get("firstName");

                List<Map<String, String>> responseContacts = new ArrayList<>();
                for (Map<String, String> contact : jsonServerContacts) {
                    if (firstNameFilter == null || contact.get("firstName").equalsIgnoreCase(firstNameFilter)) {
                        responseContacts.add(contact);
                    }
                }
                writeJsonResponse(exchange, HttpStatus.OK.value(), responseContacts);
                return;
            }

            if ("POST".equalsIgnoreCase(method) && "/contacts".equals(path)) {
                String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                Contact postedContact = GSON.fromJson(requestBody, Contact.class);
                postedContacts.add(postedContact);

                Map<String, String> createdContact = createJsonServerContact(
                        String.valueOf(nextJsonServerId.getAndIncrement()),
                        postedContact.getFirstName(),
                        postedContact.getLastName(),
                        postedContact.getAddress(),
                        postedContact.getCity(),
                        postedContact.getState(),
                        postedContact.getZip(),
                        postedContact.getPhoneNumber(),
                        postedContact.getEmail()
                );
                jsonServerContacts.add(createdContact);
                writeJsonResponse(exchange, HttpStatus.CREATED.value(), createdContact);
                return;
            }

            if ("PUT".equalsIgnoreCase(method) && path.startsWith("/contacts/")) {
                String id = path.substring("/contacts/".length());
                String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                Contact updatedContact = GSON.fromJson(requestBody, Contact.class);

                Map<String, String> existingContact = jsonServerContacts.stream()
                        .filter(contact -> contact.get("id").equals(id))
                        .findFirst()
                        .orElse(null);
                if (existingContact == null) {
                    exchange.sendResponseHeaders(HttpStatus.NOT_FOUND.value(), -1);
                    return;
                }

                existingContact.put("firstName", safe(updatedContact.getFirstName()));
                existingContact.put("lastName", safe(updatedContact.getLastName()));
                existingContact.put("address", safe(updatedContact.getAddress()));
                existingContact.put("city", safe(updatedContact.getCity()));
                existingContact.put("state", safe(updatedContact.getState()));
                existingContact.put("zip", safe(updatedContact.getZip()));
                existingContact.put("phoneNumber", safe(updatedContact.getPhoneNumber()));
                existingContact.put("email", safe(updatedContact.getEmail()));
                writeJsonResponse(exchange, HttpStatus.OK.value(), existingContact);
                return;
            }

            if ("DELETE".equalsIgnoreCase(method) && path.startsWith("/contacts/")) {
                String id = path.substring("/contacts/".length());
                boolean removed = jsonServerContacts.removeIf(contact -> contact.get("id").equals(id));
                if (!removed) {
                    exchange.sendResponseHeaders(HttpStatus.NOT_FOUND.value(), -1);
                    return;
                }
                writeJsonResponse(exchange, HttpStatus.OK.value(), Map.of("status", "deleted"));
                return;
            }

            exchange.sendResponseHeaders(HttpStatus.NOT_FOUND.value(), -1);
        });
        jsonServer.start();
        jsonServerUrl = "http://localhost:" + jsonServer.getAddress().getPort() + "/contacts";
    }

    @AfterEach
    void tearDown() {
        RestAssuredMockMvc.reset();
        if (jsonServer != null) {
            jsonServer.stop(0);
        }
    }

    @Test
    void shouldSupportUc22RetrieveEntriesFromJsonServerAndSyncMemory() {
        String addressBookName = "JsonServerBookUc22";

        given()
                .when()
                .post("/api/address-books/{name}", addressBookName)
                .then()
                .statusCode(HttpStatus.OK.value());

        given()
                .queryParam("serverUrl", jsonServerUrl)
                .when()
                .post("/api/address-books/{name}/contacts/json-server/read", addressBookName)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("message", equalTo("Contacts read from JSON Server successfully"))
                .body("addedCount", equalTo("2"));

        given()
                .when()
                .get("/api/address-books/{name}/contacts", addressBookName)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("size()", equalTo(2))
                .body("firstName", hasItems("Rudresh", "Aman"));
    }

    @Test
    void shouldSupportUc23AddMultipleEntriesToJsonServerAndSyncMemory() {
        String addressBookName = "JsonServerBookUc23";
        addressBookService.addAddressBook(addressBookName);
        addressBookService.addContacts(addressBookName, List.of(
                new Contact("Rudresh", "Sharma", "Street 1", "Indore", "MP", "452001", "9000000001", "rudresh@gmail.com"),
                new Contact("Aman", "Verma", "Street 2", "Bhopal", "MP", "462001", "9000000002", "aman@gmail.com"),
                new Contact("Ravi", "Kumar", "Street 3", "Delhi", "Delhi", "110001", "9000000003", "ravi@gmail.com")
        ));

        given()
                .queryParam("serverUrl", jsonServerUrl)
                .when()
                .post("/api/address-books/{name}/contacts/json-server/add-multiple", addressBookName)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("message", equalTo("Contacts added to JSON Server and synced with memory"))
                .body("addedCount", equalTo("3"));

        given()
                .when()
                .get("/api/address-books/{name}/contacts", addressBookName)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("size()", equalTo(3))
                .body("firstName", hasItems("Rudresh", "Aman", "Ravi"));

        assertEquals(3, postedContacts.size());
        assertTrue(postedContacts.stream().anyMatch(contact -> "Rudresh".equals(contact.getFirstName())));
        assertTrue(postedContacts.stream().anyMatch(contact -> "Aman".equals(contact.getFirstName())));
        assertTrue(postedContacts.stream().anyMatch(contact -> "Ravi".equals(contact.getFirstName())));
    }

    @Test
    void shouldSupportUc24UpdateEntryInJsonServerAndSyncMemory() {
        String addressBookName = "JsonServerBookUc24";
        addressBookService.addAddressBook(addressBookName);
        addressBookService.addContact(addressBookName, new Contact(
                "Rudresh", "Sharma", "Street 1", "Indore", "MP", "452001", "9000000001", "rudresh@gmail.com"
        ));
        addressBookService.editContact(addressBookName, "Rudresh", new Contact(
                "Rudresh", "Sharma", "New Address", "Pune", "MH", "411001", "9000000999", "new@mail.com"
        ));

        given()
                .queryParam("serverUrl", jsonServerUrl)
                .when()
                .put("/api/address-books/{name}/contacts/{firstName}/json-server/sync", addressBookName, "Rudresh")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("message", equalTo("Contact updated in JSON Server and synced with memory"));

        given()
                .when()
                .get("/api/address-books/{name}/contacts", addressBookName)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("find { it.firstName == 'Rudresh' }.city", equalTo("Pune"))
                .body("find { it.firstName == 'Rudresh' }.state", equalTo("MH"));

        assertTrue(jsonServerContacts.stream().anyMatch(contact ->
                "Rudresh".equals(contact.get("firstName"))
                        && "Pune".equals(contact.get("city"))
                        && "MH".equals(contact.get("state"))
                        && "new@mail.com".equals(contact.get("email"))
        ));
    }

    @Test
    void shouldSupportUc25DeleteEntryInJsonServerAndSyncMemory() {
        String addressBookName = "JsonServerBookUc25";
        addressBookService.addAddressBook(addressBookName);
        addressBookService.addContact(addressBookName, new Contact(
                "Rudresh", "Sharma", "Street 1", "Indore", "MP", "452001", "9000000001", "rudresh@gmail.com"
        ));

        given()
                .queryParam("serverUrl", jsonServerUrl)
                .when()
                .delete("/api/address-books/{name}/contacts/{firstName}/json-server/sync", addressBookName, "Rudresh")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("message", equalTo("Contact deleted from JSON Server and memory"));

        given()
                .when()
                .get("/api/address-books/{name}/contacts", addressBookName)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("size()", equalTo(0));

        assertTrue(jsonServerContacts.stream().noneMatch(contact ->
                "Rudresh".equals(contact.get("firstName"))
        ));
    }

    private Map<String, String> createJsonServerContact(String id,
                                                        String firstName,
                                                        String lastName,
                                                        String address,
                                                        String city,
                                                        String state,
                                                        String zip,
                                                        String phoneNumber,
                                                        String email) {
        Map<String, String> contact = new LinkedHashMap<>();
        contact.put("id", id);
        contact.put("firstName", safe(firstName));
        contact.put("lastName", safe(lastName));
        contact.put("address", safe(address));
        contact.put("city", safe(city));
        contact.put("state", safe(state));
        contact.put("zip", safe(zip));
        contact.put("phoneNumber", safe(phoneNumber));
        contact.put("email", safe(email));
        return contact;
    }

    private Map<String, String> parseQueryParams(String rawQuery) {
        Map<String, String> params = new LinkedHashMap<>();
        if (rawQuery == null || rawQuery.isBlank()) {
            return params;
        }

        String[] pairs = rawQuery.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=", 2);
            String key = URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8);
            String value = keyValue.length > 1 ? URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8) : "";
            params.put(key, value);
        }
        return params;
    }

    private void writeJsonResponse(com.sun.net.httpserver.HttpExchange exchange, int statusCode, Object body) throws IOException {
        byte[] response = GSON.toJson(body).getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, response.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response);
        }
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }
}
