package newProjectBook.edu.UserBook;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import junit.framework.TestCase;

import org.junit.Test;

import spark.Spark;
import spark.utils.IOUtils;

public class UnitTest extends TestCase {

	public static boolean setupOnce = false;
	
	@SuppressWarnings("unchecked")
	public void testUser() throws Exception
	{
		setUpBeforeClass();
		/*
		 Unit Testing for creating Users:
		 */
		TestHttpResponse res = SendHttpRequest("POST", "/createUser?firstName=john&lastName=JOse&age=26&gender=M&phone=1234567891&zip=123&middleName=M");
		User user = new Gson().fromJson(res.body, User.class);
		assertEquals(201, res.status);
		assertEquals("john", user.firstName);
		assertEquals("JOse", user.lastName);
		assertEquals("26", user.age);
		assertEquals("M", user.gender);
		assertEquals("1234567891", user.phone);
		assertEquals("M", user.middleName); 
		assertNotNull(user.id);
		
		res = SendHttpRequest("POST", "/createUser?firstName=Rahey&lastName=Krisha&age=36&gender=M&phone=1234567891&zip=123&middleName=M");
		user = new Gson().fromJson(res.body, User.class);
		assertEquals(201, res.status);
		assertEquals("Rahey", user.firstName);
		assertEquals("Krisha", user.lastName);
		assertEquals("36", user.age);
		assertEquals("M", user.gender);
		assertEquals("1234567891", user.phone);
		assertEquals("M", user.middleName); 
		assertNotNull(user.id);
		
		@SuppressWarnings("unused")
		String id = user.id;
		res = SendHttpRequest("GET", "/getAllUsers");
		/*
		 Unit testing to get all the users:
		 */
		java.lang.reflect.Type typeName = new TypeToken<ArrayList<User>>(){}.getType();
		ArrayList<User> users = (ArrayList<User>)new Gson().fromJson(res.body, typeName);
		assertEquals(200, res.status);
		assertEquals(2, users.size());
		user = (User) users.get(0);
		assertEquals("john", user.firstName);
		assertEquals("JOse", user.lastName);
		assertEquals("26", user.age);
		assertEquals("M", user.gender);
		assertEquals("1234567891", user.phone);
		assertEquals("M", user.middleName); 
		
		user = (User) users.get(1);
		assertEquals("Rahey", user.firstName);
		assertEquals("Krisha", user.lastName);
		assertEquals("36", user.age);
		assertEquals("M", user.gender);
		assertEquals("1234567891", user.phone);
		assertEquals("M", user.middleName); 
		
		/*
		 Unit testing to update user: 
		  */
		res = SendHttpRequest("PUT", "/updateUser/0?firstName=Ditz");
		user = new Gson().fromJson(res.body, User.class);
		assertEquals(200, res.status);
		assertEquals("Ditz", user.firstName);
		assertEquals("JOse", user.lastName);
		assertEquals("26", user.age);
		assertEquals("M", user.gender);
		assertEquals("1234567891", user.phone);
		assertEquals("M", user.middleName);
		/*
		 Unit testing to get all the Users:
		 */
		res = SendHttpRequest("GET", "/getAllUsers");
		typeName = new TypeToken<ArrayList<User>>(){}.getType();
		users = (ArrayList<User>)new Gson().fromJson(res.body, typeName);
		assertEquals(200, res.status);
		assertEquals(2, users.size());
		user = (User) users.get(0);
		assertEquals("Ditz", user.firstName);
		assertEquals("JOse", user.lastName);
		assertEquals("26", user.age);
		assertEquals("M", user.gender);
		assertEquals("1234567891", user.phone);
		assertEquals("M", user.middleName); 
		
		user = (User) users.get(1);
		assertEquals("Rahey", user.firstName);
		assertEquals("Krisha", user.lastName);
		assertEquals("36", user.age);
		assertEquals("M", user.gender);
		assertEquals("1234567891", user.phone);
		assertEquals("M", user.middleName);
		
		try
		{
			/*
			 Unit testing to update users
			 */
			SendHttpRequest("PUT", "/updateUser/200?firstName=Ditz");
			fail("Resource should not be found");		}
		catch (Exception ex)
		{}
		
		
		System.out.println("Test User create Succeeded");
	
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testBook() throws Exception
	{
		/*
		 Unit testing to add books
		 */
		setUpBeforeClass();
		TestHttpResponse res = SendHttpRequest("POST", "/addBook?author=tittu&name=LifeBook");
		Book book1 = new Gson().fromJson(res.body, Book.class);
		assertEquals(201, res.status);
		assertEquals("tittu", book1.author);
		assertEquals("LifeBook", book1.name);
		assertNotNull(book1.id);
		
		res = SendHttpRequest("POST", "/addBook?author=Angelo&name=Michel");
		Book book2 = new Gson().fromJson(res.body, Book.class);
		assertEquals(201, res.status);
		assertEquals("Angelo", book2.author);
		assertEquals("Michel", book2.name);
		assertNotNull(book2.id);
		/*
		 Unit testing to test retrieval of the books
		 */
		res = SendHttpRequest("GET", "/getAllBooks");
		java.lang.reflect.Type typeName = new TypeToken<ArrayList<Book>>(){}.getType();
		ArrayList<Book> books = (ArrayList<Book>)new Gson().fromJson(res.body, typeName);
		assertEquals(200, res.status);
		assertEquals(2, books.size());
		
		book1 = books.get(0);
		book2 = books.get(1);
		
		assertEquals("tittu", book1.author);
		assertEquals("LifeBook", book1.name);
		assertNotNull(book1.id);
		assertEquals("Angelo", book2.author);
		assertEquals("Michel", book2.name);
		assertNotNull(book2.id);
		/*
		 Unit testing to find out book by name
		 */
		res = SendHttpRequest("GET", "/findBookByName/LifeBook");
		typeName = new TypeToken<ArrayList<Book>>(){}.getType();
		books = (ArrayList<Book>)new Gson().fromJson(res.body, typeName);
		assertEquals(200, res.status);
		assertEquals(1, books.size());
		book1 = books.get(0);
		assertEquals("tittu", book1.author);
		assertEquals("LifeBook", book1.name);
		assertNotNull(book1.id);
		
		try
		{
			SendHttpRequest("GET", "/findBookByName/Life");
			fail("Resource should not be found");		}
		catch (Exception ex)
		{}
		
		res = SendHttpRequest("POST", "/checkOutBook/" + book1.id + "/1");
		book1 = new Gson().fromJson(res.body, Book.class);
		assertEquals(201, res.status);
		assertEquals("tittu", book1.author);
		assertEquals("LifeBook", book1.name);
		assertEquals("1", book1.CheckedOutBy);
		assertNotNull(book1.id);
		
		
		
		try
		{
			/*
			 Unit tesing to Check out books 
			 */
			SendHttpRequest("POST", "/checkOutBook/" + book1.id + "/1");
			fail("Book already checkedout");
		}
		catch (IOException e)
		{
		}
		
		System.out.println("Test Book create Succeeded");
	}
	
	private TestHttpResponse SendHttpRequest(String httpMethod, String urlSegment) throws IOException
	{
		try {
			URL url = new URL("http://localhost:4567" + urlSegment);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(httpMethod);
			connection.setDoOutput(true);
			connection.connect();
			String body = IOUtils.toString(connection.getInputStream());
			return new TestHttpResponse(connection.getResponseCode(), body);
		} catch (IOException e) {
			//e.printStackTrace();
			throw e;
		}
	}
	
	public static void setUpBeforeClass() throws Exception {
		if (setupOnce == false)
		{
			setupOnce = true;
			Spark.stop();
			App.main(null);
			Thread.sleep(500);
		}
	}
}

 class TestHttpResponse {

	public final String body;
	public final int status;

	public TestHttpResponse(int status, String body) {
		this.status = status;
		this.body = body;
	}
}
