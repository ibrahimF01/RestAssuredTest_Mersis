package GoRest;

import io.restassured.http.ContentType;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.response.Response;

import static org.hamcrest.Matchers.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class GoRestUsersTests {

    @BeforeClass
    void Setup(){
        //RestAssured kendi statik değişkeni tanımlı değer atanıyor
        baseURI="https://gorest.co.in/public/v2/";
    }

    @Test
    public void createUser(){
        int userID=
        given()
                // api metoduna gitmeden önceki hazırlıklar: token, gidecek body, parametreleri

                .header("Authorization","Bearer 32ead2be7e99e4282a603519813b2652fbe1b4b430d3a916381ad20543668cc6")
                .contentType(ContentType.JSON)
                .body("{\"name\":\""+getRandomName()+"\", \"gender\":\"male\", \"email\":\""+getRandomEmail()+"\", \"status\":\"active\"}")
                .when()
                .post("users")


                .then()
                .log().body()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .extract().path("id")

        ;
        System.out.println("userID = " + userID);
    }

    @Test
    public void createUserMap(){
        Map<String,String > newUser=new HashMap<>();
        newUser.put("name",getRandomName());
        newUser.put("gender","male");
        newUser.put("email",getRandomEmail());
        newUser.put("status","active");





        int userID=
                given()
                        // api metoduna gitmeden önceki hazırlıklar: token, gidecek body, parametreleri

                        .header("Authorization","Bearer 32ead2be7e99e4282a603519813b2652fbe1b4b430d3a916381ad20543668cc6")
                        .contentType(ContentType.JSON)
                        .body(newUser)
                        .when()
                        .post("users")


                        .then()
                        .log().body()
                        .statusCode(201)
                        .contentType(ContentType.JSON)
                        .extract().path("id")

                ;
        System.out.println("userID = " + userID);
    }

    @Test
    public void createUserObject(){

        User newUser=new User();
        newUser.setName(getRandomName());
        newUser.setGender("male");
        newUser.setEmail(getRandomEmail());
        newUser.setStatus("active");

        int userID=
                given()
                        // api metoduna gitmeden önceki hazırlıklar: token, gidecek body, parametreleri

                        .header("Authorization","Bearer 32ead2be7e99e4282a603519813b2652fbe1b4b430d3a916381ad20543668cc6")
                        .contentType(ContentType.JSON)
                        .body(newUser)
                        .when()
                        .post("users")


                        .then()
                        .log().body()
                        .statusCode(201)
                        .contentType(ContentType.JSON)
                        .extract().path("id")

                ;
        System.out.println("userID = " + userID);
    }
    public int userID;
    @Test
    public void createUserObject2(){

        User newUser=new User();
        newUser.setName(getRandomName());
        newUser.setGender("male");
        newUser.setEmail(getRandomEmail());
        newUser.setStatus("active");

        userID=
                given()
                        // api metoduna gitmeden önceki hazırlıklar: token, gidecek body, parametreleri

                        .header("Authorization","Bearer 32ead2be7e99e4282a603519813b2652fbe1b4b430d3a916381ad20543668cc6")
                        .contentType(ContentType.JSON)
                        .body(newUser)
                        .when()
                        .post("users")


                        .then()
                        .log().body()
                        .statusCode(201)
                        .contentType(ContentType.JSON)
                        //.extract().path("id")
                        .extract().jsonPath().getInt("id")
                ;
        // path : class veya tip dönüşümüne imkan veremeyen direk veriyi verir. List<String> gibi
        // jsonPath : class dönüşümüne ve tip dönüşümüne izin vererek , veriyi istediğimiz formatta verir.
        System.out.println("userID = " + userID);
//
//        newUser.setName("ibrahim reis"); deneme idi bu çalışıyor update için ya da nesneyi dışarıda public yapabiliriz!
//        given()
//                // api metoduna gitmeden önceki hazırlıklar: token, gidecek body, parametreleri
//
//                .header("Authorization","Bearer 32ead2be7e99e4282a603519813b2652fbe1b4b430d3a916381ad20543668cc6")
//                .contentType(ContentType.JSON)
//                .body(newUser)
//                .log().body()
//                .pathParam("userID",userID)
//                .when()
//                .put("users/{userID}")
//
//
//                .then()
//                .log().body()
//                .statusCode(200)
//                .body("name",equalTo("ibrahim reis"))
//        ;
//
//        System.out.println("userID = " + userID);
    }

    @Test(dependsOnMethods = "createUserObject2",priority = 1)
    public void createUserObject3(){
        Map<String,String > updateUser=new HashMap<>();
        updateUser.put("name","ibrahim figen");
                given()
                        // api metoduna gitmeden önceki hazırlıklar: token, gidecek body, parametreleri

                        .header("Authorization","Bearer 32ead2be7e99e4282a603519813b2652fbe1b4b430d3a916381ad20543668cc6")
                        .contentType(ContentType.JSON)
                        .body(updateUser)
                        .log().body()
                        .pathParam("userID",userID)
                        .when()
                        .put("users/{userID}")


                        .then()
                        .log().body()
                        .statusCode(200)
                        .body("name",equalTo("ibrahim figen"))
                ;
        System.out.println("userID = " + userID);
    }
    @Test(dependsOnMethods = "createUserObject2",priority = 2)
    public void getUserByID(){
        given()
                // api metoduna gitmeden önceki hazırlıklar: token, gidecek body, parametreleri

                .header("Authorization","Bearer 32ead2be7e99e4282a603519813b2652fbe1b4b430d3a916381ad20543668cc6")
                .contentType(ContentType.JSON)
                .pathParam("userID",userID)

                .when()
                .put("users/{userID}")


                .then()
                .log().body()
                .statusCode(200)
                .body("id",equalTo(userID))
        ;

        System.out.println("userID = " + userID);
    }

    @Test(dependsOnMethods = "createUserObject2",priority = 3)
    public void deleteUserByID(){
        given()
                // api metoduna gitmeden önceki hazırlıklar: token, gidecek body, parametreleri

                .header("Authorization","Bearer 32ead2be7e99e4282a603519813b2652fbe1b4b430d3a916381ad20543668cc6")
                .contentType(ContentType.JSON)
                .pathParam("userID",userID)

                .when()
                .delete("users/{userID}")


                .then()
                .log().body()
                .statusCode(204)

        ;
    }
    @Test(dependsOnMethods = "deleteUserByID")
    public void deleteUserByIDNegative(){
        given()
                // api metoduna gitmeden önceki hazırlıklar: token, gidecek body, parametreleri

                .header("Authorization","Bearer 32ead2be7e99e4282a603519813b2652fbe1b4b430d3a916381ad20543668cc6")
                .contentType(ContentType.JSON)
                .pathParam("userID",userID)

                .when()
                .delete("users/{userID}")


                .then()
                .log().body()
                .statusCode(404) ///buradaki hataya bakılacak

        ;
    }

    @Test
    public void getUsers()
    {
        Response response=
                given()
                        .header("Authorization","Bearer 32ead2be7e99e4282a603519813b2652fbe1b4b430d3a916381ad20543668cc6")

                        .when()
                        .get("users")

                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().response()
                ;

        // TODO : 3. usersın id sini alınız (path ve jsonPath ile ayrı ayrı yapınız)
        int idUser3path=response.path("[2].id");
        int idUser3JsonPath=response.jsonPath().getInt("[2].id");
        System.out.println("idUser3path = " + idUser3path);
        System.out.println("idUser3JsonPath = " + idUser3JsonPath);



        // TODO : GetUserByID testinde dönen user ı bir nesneye atınız.
        User[] usersPath=response.as(User[].class);
        System.out.println("Arrays.toString(usersPath) = " + Arrays.toString(usersPath));
        List<User> usersJsonPath =response.jsonPath().getList("",User.class);
        System.out.println("usersJsonPath = " + usersJsonPath);
    }
    @Test
    public void getUsersByIDExtract()
    {  // TODO : Tüm gelen veriyi bir nesneye atınız
        User user=
                given()
                        .header("Authorization","Bearer 32ead2be7e99e4282a603519813b2652fbe1b4b430d3a916381ad20543668cc6")
                        .contentType(ContentType.JSON)
                        .pathParam("userID",2303)
                        .when()
                        .get("users/{userID}")

                        .then()
                        //.log().body()
                        .statusCode(200)
                       // .extract().as(User.class)
                        .extract().jsonPath().getObject("",User.class);

        System.out.println("user = " + user);

    }
    @Test
    public void getUsersV1()
    {
                Response response=
                given()
                        .header("Authorization","Bearer 32ead2be7e99e4282a603519813b2652fbe1b4b430d3a916381ad20543668cc6")

                        .when()
                        .get("https://gorest.co.in/public/v1/users")

                        .then()
                        //.log().body()
                        .statusCode(200)
                        .extract().response();

               // response.as();//tüm gelen response uygun nesneler için tüm classların yapılması gerekiyor.
        List<User> dataUsers=response.jsonPath().getList("data",User.class); //JSONPATH ile response içindeki bir parçayı
                                                                                    //Nesneye dönüştürebiliriz.
        System.out.println("dataUsers = " + dataUsers);

        // Daha önceki örneklerde (as) Clas dönüşümleri için tüm yapıya karşılık gelen
        // gereken tüm classları yazarak dönüştürüp istediğimiz elemanlara ulaşıyorduk.
        // Burada ise(JsonPath) aradaki bir veriyi clasa dönüştürerek bir list olarak almamıza
        // imkan veren JSONPATH i kullandık.Böylece tek class ise veri alınmış oldu
        // diğer class lara gerek kalmadan

        // path : class veya tip dönüşümüne imkan veremeyen direk veriyi verir. List<String> gibi
        // jsonPath : class dönüşümüne ve tip dönüşümüne izin vererek , veriyi istediğimiz formatta verir.
    }
    public  String getRandomName()
    {
        return RandomStringUtils.randomAlphabetic(8);
    }
    public  String getRandomEmail()
    {
        return RandomStringUtils.randomAlphabetic(8)+"@gmail.com";
    }



}
class User{
  private String  id;
  private String  name;
  private String  gender;
  private String  email;
  private String  status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getStatus() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}