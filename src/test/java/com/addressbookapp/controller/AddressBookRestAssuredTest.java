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
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    @BeforeEach
    void setUp() throws IOException {
        RestAssuredMockMvc.webAppContextSetup(context);
        postedContacts = Collections.synchronizedList(new ArrayList<>());

        jsonServer = HttpServer.create(new InetSocketAddress(0), 0);
        jsonServer.createContext("/contacts", exchange -> {
            if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
                String body = """
                        [
                          {
                            "firstName":"Rudresh",
                            "lastName":"Sharma",
                            "address":"Street 1",
                            "city":"Indore",
                            "state":"MP",
                            "zip":"452001",
                            "phoneNumber":"9000000001",
                            "email":"rudresh@gmail.com"
                          },
                          {
                            "firstName":"Aman",
                            "lastName":"Verma",
                            "address":"Street 2",
                            "city":"Bhopal",
                            "state":"MP",
                            "zip":"462001",
                            "phoneNumber":"9000000002",
                            "email":"aman@gmail.com"
                          }
                        ]
                        """;
                byte[] response = body.getBytes(StandardCharsets.UTF_8);
                exchange.getResponseHeaders().add("Content-Type", "application/json");
                exchange.sendResponseHeaders(HttpStatus.OK.value(), response.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response);
                }
                return;
            }

            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                Contact postedContact = GSON.fromJson(requestBody, Contact.class);
                postedContacts.add(postedContact);

                String responseBody = "{\"status\":\"created\"}";
                byte[] response = responseBody.getBytes(StandardCharsets.UTF_8);
                exchange.getResponseHeaders().add("Content-Type", "application/json");
                exchange.sendResponseHeaders(HttpStatus.CREATED.value(), response.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response);
                }
                return;
            }

            exchange.sendResponseHeaders(HttpStatus.METHOD_NOT_ALLOWED.value(), -1);
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
}
