package petstore;

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;

public class Pet {

    String uri = "https://petstore.swagger.io/v2/pet";

    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    //Create - Post
    @Test(priority = 1) //Identifica método ou função como teste
    public void incluirPet() throws IOException {
        String jsonBody = lerJson("db/pet1.json");

        //Sintaxe Gherkin
        //Dado - Quando - Entao
        //Given - When - Then

        given()//Dado
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when() //Quando
                .post(uri)
        .then() //Entao
                .log().all()
                .statusCode(200)
                .body("name", is("Rick"))
                .body("status", is("available"))
                .body("category.name", is("dog"))
                .body("tags.name", contains("sta"))
        ;
    }

    @Test(priority = 2)
    private void consultarPet(){

        String petId = "1992051692";

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .get(uri+"/"+petId)
        .then()
                .statusCode(200)
                .log().all()
                .body("name", is("Rick"))
                .body("category.name", is("dog"))
        ;
    }

    @Test(priority = 3)
    public void alterarPet() throws IOException {
        String jsonBody = lerJson("db/pet2.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .put(uri)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Rick"))
                .body("status", is("sold"))
        ;
    }

    @Test(priority = 4)
    public void excluirPet(){
        String petId = "1992051692";

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .delete(uri +"/"+petId)
        .then()
                .log().all()
                .statusCode(200)
                .body("code", is(200))
                .body("type", is("unknown"))
                .body("message", is(petId))
        ;
    }
}
