import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;

public class TestGet {

    private Gson gson;
    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "https://reqres.in/api";
        gson = new Gson();
    }

    @Test
    public void getList() {
        Response response = given()
                .when()
                .get("/unknown")
                .then()
                .extract().response();
        Assertions.assertEquals(200, response.statusCode());

        ListDTO dto = gson.fromJson(response.body().asString(), ListDTO.class);
        int expectedPage = 1;
        int expectedPerPage = 6;
        int totals = 12;
        int totalPages = 2;

        Assertions.assertEquals(expectedPage, dto.page);
        Assertions.assertEquals(expectedPerPage, dto.per_page);
        Assertions.assertEquals(totals, dto.total);
        Assertions.assertEquals(totalPages, dto.total_pages);

        for (var element : dto.data) {
            System.out.println(element.name);

        }
    }
}

