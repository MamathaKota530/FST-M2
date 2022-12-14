package RestAssuredProject;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import static io.restassured.RestAssured.given;

public class gitHubProjectTest {

    RequestSpecification resSpec;
    Response response;
    String sshKey;
    int id;

    @BeforeClass
    public void setUp() {
        resSpec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri("https://api.github.com")
                .addHeader("Authorization", "token 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")
                .build();


    }

    @Test(priority = 0 )
    public void postMethod() {

        String reqBody ="{\n" +
                "    \"title\": \"TestAPIKey\",\n" +
                "    \"key\": \"ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCd8mBxWo6zu6ceiNaHBHOYtsppv+CwqspGxj7p6/9wyEm6vKZ5HhQIORapmrLoKEb/ohoK4jGs/LLkS8ghxeiGjqWsLkiPULU/+hxWD3UhN78NGw2QyHrLq3Nr+bdBI1EHdVesSg3Zl39kx7pB1C5Cb57IoLbXaHl0nOV67Pfd2Z3d0ZPnC1EQLtfIby6GqjaFCtL0rhHjh9G3gbNIIt0wqhc56G2opFJFAVvv3pNsq2Y1feRye+eF2l4kwBLLOXXdeq7+YX8moVcrb+U9FmGWfte2fFJOM7H9K9SwoZAtjoAUkXT8qOcbSXJbj1mH90B6nXolsA9aU61ERv8i4D1B\"}";
        response = given().spec(resSpec).body(reqBody).when().post("/user/keys");

        System.out.println("response from post  "+response.getBody().asPrettyString());
        id=response.then().extract().path("id");
        System.out.println("id"+id);
        System.out.println(response.statusCode());
        int postcode=response.getStatusCode();
        Assert.assertEquals(postcode,201);

    }
    @Test(priority = 1)
    public void getMethod()
    {
        System.out.println("id"+id);
        response=given().spec(resSpec)
                .pathParam("keyId",id)
                .when().get("/user/keys/{keyId}");
        System.out.println("GET response "+response.getBody().asPrettyString());
        int getCode = response.getStatusCode();
        Assert.assertEquals(getCode,200);
    }

    @Test(priority = 2)
    public void deleteMethod()
    {
        response=given().spec(resSpec).pathParam("keyId",id).when().delete("/user/keys/{keyId}");
        System.out.println(response.getBody().asPrettyString());
        int deleteCode = response.getStatusCode();
        Assert.assertEquals(deleteCode,204);
    }
}
