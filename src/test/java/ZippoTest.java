import POJO.Location;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.Filter;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.*;
import io.restassured.mapper.ObjectMapper;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.security.KeyStore;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class ZippoTest {
@Test
    public void test(){
    given()
                // hazırlık işlemlerini yapacağız(token,send body,parameteler)
            .when()
                // linki ve netodu veriyoruz
            .then();
                // assertion ve verileri ele alma extract

}
    @Test
    public void statusCodaTest(){
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body() // log.All() büyün respons u gösterir
                .statusCode(200)// status kontrolü
                .contentType(ContentType.JSON)
        ;



    }
    @Test
    public void checkStateInResponseBody(){
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .body("country",equalTo("United States"))
                .statusCode(200)

        ;



    }
    @Test
    public void bodyJsonPathTest2(){
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .body("country",equalTo("United States"))
                .body("places[0].'place name'",equalTo("Beverly Hills"))
                .statusCode(200)

        ;



    }
    @Test
    public void bodyJsonPathTest3(){
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .body("country",equalTo("United States"))
                .body("places[0].'place name'",equalTo("Beverly Hills"))
                .statusCode(200)

        ;



    }
    @Test
    public void bodyJsonPathTest4(){
        given()

                .when()
                .get("http://api.zippopotam.us/tr/01000")

                .then()
                .log().body()
                .body("places.'place name'",hasItem("Çaputçu Köyü"))//bir index verilmezde dizinin tüm elemanlarında arar
                .statusCode(200)
            //"places.'place name'"  bu bilgiler "Çaputçu Köyü" bu iteme sahip mi
        ;



    }
    @Test
    public void bodyJsonPathTest5(){
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .body("places",hasSize(1)) //uzunluk eşit mi
                .body("country",equalTo("United States"))
                .body("places[0].'place name'",equalTo("Beverly Hills"))
                .statusCode(200)

        ;



    }
    @Test
    public void pathParamTest1(){
        given()
                .pathParam("Country","us")
                .pathParam("ZipKod","90210")
                .log().uri()
                .when()
                .get("http://api.zippopotam.us/{Country}/{ZipKod}")

                .then()
                .log().body()

                .statusCode(200)

        ;



    }
    @Test
    public void pathParamTest2(){
    //// 90210 dan 90250 kadar test sonuçlarında places in size nın hepsinde 1 gediğini test ediniz.
        for (int i = 90210; i < 90214; i++) {

        // if(i>90213&&i<90230)continue;
        given()
                .pathParam("Country","us")
                .pathParam("ZipKod",i)
                .log().uri()
                .when()
                .get("http://api.zippopotam.us/{Country}/{ZipKod}")

                .then()
                .log().body()
                .body("places",hasSize(1))
                .statusCode(200)

        ;


        }

    }
    @Test
    public void queryParamTest(){
        given()
                .param("page",1)
                .log().uri()
                .when()
                .get("http://gorest.co.in/public/v1/users")

                .then()
                .log().body()
                .body("meta.pagination.page",equalTo(1))
                .statusCode(200)

        ;



    }
    @Test
    public void queryParamTest2(){
        for (int i = 1; i < 11; i++) {


    given()
                .param("page",i)
                .log().uri()
                .when()
                .get("http://gorest.co.in/public/v1/users")

                .then()
                .log().body()
            .body("meta.pagination.page",equalTo(i))
                .statusCode(200)

        ;

        }

    }
    RequestSpecification requestSpecs;
    ResponseSpecification responseSpecs;

    @BeforeClass
    void Setup(){
        baseURI="http://gorest.co.in/public/v1";
        requestSpecs=new RequestSpecBuilder()
                .log(LogDetail.URI)
                .setAccept(ContentType.JSON)
                .build();
        responseSpecs=new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .log(LogDetail.BODY)
                .build();
    }
    @Test
    public void requestResponseSpecification(){
        given()
                .param("page",1)
                .log().uri()
                .when()
                .get("/users")//url nin başında http yoksa baseURİ deki değer otomatik atanır

                .then()
                .log().body()
                .body("meta.pagination.page",equalTo(1))
                .statusCode(200)

        ;



    }

    @Test
    public void extractingJsonPath(){
        String placeName=
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
               // .log().body()

                .statusCode(200)
                .extract().path("places[0].'place name'")
                //.extract() methodu ile given ile başlayan satır bir değer döndürür hale geldi, en sonda .extract() olmalı
        ;

        System.out.println("placeName"+placeName);

    }

    @Test
    public void extractingJsonPathInt(){
        int limit=
                given()

                        .when()
                        .get("https://gorest.co.in/public/v1/users")

                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().path("meta.pagination.limit")

                ;
        System.out.println("limit = " + limit);
        Assert.assertEquals(limit,10,"test sonucu");
    }
    @Test
    public void extractingJsonPathInt2(){
        int id=
                given()

                        .when()
                        .get("https://gorest.co.in/public/v1/users")

                        .then()
                       // .log().body()
                        .statusCode(200)
                        .extract().path(" data[2].id")

                ;
        System.out.println("id = " + id);

    }
    @Test
    public void extractingJsonPathInt3(){
        int id=
                given()

                        .when()
                        .get("https://gorest.co.in/public/v1/users")

                        .then()
                        // .log().body()
                        .statusCode(200)
                        .extract().path(" data.id")// datadaki bütün id leri verir

                ;
        System.out.println("id = " + id);

    }

    @Test
    public void extractingJsonPathList(){
   List<Integer> idler=
                given()

                        .when()
                        .get("https://gorest.co.in/public/v1/users")

                        .then()
                        // .log().body()
                        .statusCode(200)
                        .extract().path(" data.id")// datadaki bütün id leri list şeklinde verir

                ;
        System.out.println("idler = " + idler);
        Assert.assertTrue(idler.contains(3045));
    }


    @Test
    public void extractingJsonPathStringList(){
        List<String> isimler=
                given()

                        .when()
                        .get("https://gorest.co.in/public/v1/users")

                        .then()
                        // .log().body()
                        .statusCode(200)
                        .extract().path("data.name")// datadaki bütün isimleri leri verir

                ;
        System.out.println("isimler = " + isimler);
        Assert.assertTrue(isimler.contains("Datta Achari"));
    }
    @Test
    public void extractingJsonPathResponseAll(){
        Response response=//response adında değişken tanımladık ve tüm sonucu bu değişkene atacağız.
                given()

                        .when()
                        .get("https://gorest.co.in/public/v1/users")

                        .then()
                        // .log().body()
                        .statusCode(200)
                        .extract().response()// büyün body alındı

                ;
        System.out.println("isimler = " + response);
    List<Integer> idler=response.path("data.id");
    List<String> isimler=response.path("data.name");
    int limit=response.path("meta.pagination.limit");

        System.out.println("idler = " + idler);
        System.out.println("isimler = " + isimler);
        System.out.println("limit = " + limit);


    }
    @Test
    public void extractingJsonPOJO(){//POJO: JSon object i(Plain Old Java)
        Location yer=
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .statusCode(200)
                .extract().as(Location.class)// location şablonu
        ;
        System.out.println("yer = " + yer);

        System.out.println("yer.getCountry() = " + yer.getCountry());
        System.out.println("yer.getPlaces().get(0).getPlacename() = " + yer.getPlaces().get(0).getPlacename());


    }

   // http://api.zippopotam.us/us/90210

    //body.country->body("country","karşılaştırma")
    //body.'post code'->body("post code","karşılaştırma")
    //body.'country abbreviation'->body("country abbreviation","karşılaştırma")
    //body.places[0].'place name'->body("places[0].'place name'","karşılaştırma")
    //body.places[0].state->body("places[0].state","karşılaştırma")
}
