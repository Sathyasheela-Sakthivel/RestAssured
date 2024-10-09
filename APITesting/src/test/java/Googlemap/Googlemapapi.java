package Googlemap;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io .restassured.RestAssured.*;

public class Googlemapapi {

	@Test
	public void e2eflow() {

		RestAssured.baseURI="https://rahulshettyacademy.com/";

		String response=given().log().all().contentType(ContentType.JSON).body(mapbody1.map()).
				when().post("maps/api/place/add/json?key=qaclick123").
				then().log().all().assertThat().statusCode(200).body("scope", Matchers.equalTo("APP")).extract().response().asString();

		JsonPath json=new JsonPath(response);
		String placeid=json.get("place_id");
		System.out.println(placeid);


		Response res2=given().queryParam("place_id",placeid).queryParam("key","qaclick123").when().get("http://rahulshettyacademy.com/maps/api/place/get/json").
				then().log().all().statusCode(200).extract().response();



		Response response3=given().headers("Content-Type","application/json").queryParam("place_id",placeid).queryParam("key","qaclick123")
				.body("{\"place_id\":\""+placeid+"\",\r\n"
						+ "\"address\":\"13 schoolchrist walk road, UK\",\r\n"
						+ "\"key\":\"qaclick123\"}\r\n"
						+ "").when().put("maps/api/place/update/json").then().log().all().assertThat().body("msg",Matchers .equalTo("Address successfully updated")).extract().response();
		System.out.println(response3);


		Response res4=given().queryParam("place_id",placeid).queryParam("key","qaclick123").when().get("http://rahulshettyacademy.com/maps/api/place/get/json").
				then().log().all().statusCode(200).extract().response();
		
		
		
		


		Response response5=given().headers("Content-Type","application/json").queryParam("key","qaclick123").body("{\"place_id\":\""+placeid+"\"}").
				when().delete("maps/api/place/delete/json").then().log().all().extract().response();

		System.out.println(response5);




	}
}
