import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.path.json.JsonPath;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;

public class api_automation {

//    List of test cases proposed for automation:
//    Verify that a valid API request returns a response with a status code of 200 (OK).
//    Verify that an invalid API request returns a response with the desired error status code (e.g., 400 Bad Request).
//    Verify that the response contains the type field and is not equal to null.
//    Verify that the "activity" field in the response is not empty.
//    Verify that the "participants" field in the response is a positive integer.
//    Verify that the "price" field in the response is a decimal number between 0 and 1.


    @Test
    public void testValidAPIRequestReturnsStatusCode200() {
        // Set base URI for the API
        RestAssured.baseURI = "https://www.boredapi.com";
        Response response = given()
                .when()
                .get("/api/activity");
        assertEquals(response.getStatusCode(), 200, "Status code is not 200");
    }
    @Test
    public void testInvalidEndpointReturns404() {
        RestAssured.baseURI = "https://api.publicapis.org";
        Response response = RestAssured.get("/non-existing-endpoint");
        assertEquals(response.getStatusCode(), 404);
    }
    @Test
    public void testTypeFieldIsExist() {

        RestAssured.baseURI = "https://www.boredapi.com";
        Response response = RestAssured.get("/api/activity");
        assertEquals(response.getStatusCode(), 200, "Unexpected status code");
        JsonPath jsonPath = response.jsonPath();
        String link = jsonPath.getString("type");
        assertTrue(link != null && !link.isEmpty(), "type field is null or empty");
        assertTrue(link instanceof String, "type field has incorrect data type");
    }
    @Test
    public void testActivityFieldNotEmpty() {

        RestAssured.baseURI = "https://www.boredapi.com";
        Response response = RestAssured.get("/api/activity");
        assert response.getStatusCode() == 200;
        JsonPath jsonPath = response.jsonPath();
        String activity = jsonPath.getString("activity");
        assertNotNull(activity, "Activity field is null");
        assertFalse(activity.isEmpty(), "Activity field is empty");
    }
    @Test
    public void testParticipantsFieldPositiveInteger() {
        RestAssured.baseURI = "https://www.boredapi.com";
        Response response = RestAssured.get("/api/activity");
        assert response.getStatusCode() == 200;
        JsonPath jsonPath = response.jsonPath();
        int participants = jsonPath.getInt("participants");
        assertTrue(participants > 0, "Participants field is not a positive integer");
    }
    @Test
    public void testPriceFieldInRange() {
        RestAssured.baseURI = "https://www.boredapi.com";
        Response response = RestAssured.get("/api/activity");
        assertEquals(response.getStatusCode(), 200, "Unexpected status code");
        JsonPath jsonPath = response.jsonPath();
        Double price = jsonPath.getDouble("price");
        assertNotNull(price, "Price field is null");
        assertTrue(price >= 0 && price <= 1, "Price is not between 0 and 1");
    }

}