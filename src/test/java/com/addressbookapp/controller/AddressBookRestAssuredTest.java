package com.addressbookapp.controller;

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

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class AddressBookRestAssuredTest {

    @Autowired
    private WebApplicationContext context;

    private HttpServer jsonServer;
    private String jsonServerUrl;

    @BeforeEach
    void setUp() throws IOException {
        RestAssuredMockMvc.webAppContextSetup(context);

        jsonServer = HttpServer.create(new InetSocketAddress(0), 0);
        jsonServer.createContext("/contacts", exchange -> {
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
}
