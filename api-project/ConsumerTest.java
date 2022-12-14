package liveProject;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;

import au.com.dius.pact.core.model.annotations.Pact;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

@ExtendWith(PactConsumerTestExt.class) // consider the below class as pact class

public class ConsumerTest {

    Map<String, String> headers = new HashMap<>();
    //Resource path
    String resourcePath = "/api/users";

    @Pact(consumer = "UserConsumer", provider = "UserProvider")
    public RequestResponsePact createPact(PactDslWithProvider builder) {

        //creating headers
        headers.put("Content-Type", "application/json");


        DslPart reqResBody = new PactDslJsonBody()
                .numberType("id", 111)
                .stringType("firstName", "Kota")
                .stringType("lastName", "Mamatha")
                .stringType("email", "mamathak@gmail.com");

        //create contract
        return builder.given("Request to create a user")
                .uponReceiving("Request to create a user")
                .method("POST")
                .path(resourcePath)
                .headers(headers)
                .body(reqResBody)
                .willRespondWith()
                .status(201)
                .body(reqResBody)
                .toPact();
    }

    @Test
    @PactTestFor(providerName = "UserProvider", port = "8282")
    public void consumerTest() {
        String baseURI = "http://localhost:8282";
//set request body
        Map<String, Object> reqBody = new HashMap<>();
        reqBody.put("id", 120);
        reqBody.put("firstName", "Kota");
        reqBody.put("lastName", "Mamatha");
        reqBody.put("email","mamathak@gmail.com");

        Response response = given().headers(headers).when().body(reqBody)
                .post(baseURI + resourcePath);

        System.out.println(response.getBody().asPrettyString());
        //response.
        System.out.println(response.getStatusCode());



    }


}
