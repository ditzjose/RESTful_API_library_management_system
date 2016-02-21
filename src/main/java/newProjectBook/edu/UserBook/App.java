package newProjectBook.edu.UserBook;

import static spark.Spark.*;

import java.util.ArrayList;



public class App 
{
	/*
	 Rules for validation:
	  */
	 static String regExfirstName = "[A-Za-z]+";
	 static String regExMiddleName = "[A-Za-z]?";
	 static String regExLastName = "[A-Za-z]+";
	 
	 static String regExGender = "[MF]";
	 static String regExphone = "[0-9]{10}";
	 static int userIDCounter = 0;
	 static int bookIDCounter = 0;
    @SuppressWarnings("unused")
	public static void main( String[] args )
    {
  
    	 /*
    	  	createUser 
			Creates the user if he is not already available in the data store.

    	  */
    	
        UserService userService = new UserService();
        BookService bookService = new BookService();
    	
        put("/deleteUser/:userId",(req,res)->{
        	String userId = req.params(":userId");
        	
        	for(int i = 0 ; i < UserService.ArrayUser.size() ; i++)
        	{
        		if(UserService.ArrayUser.get(i).getId().equals(userId))
        		{
        			UserService.ArrayUser.remove(i);
        			res.status(201);
            		return UserService.ArrayUser;
        		}
        		else
        		{
        			res.status(404);
        			return "UserNotFound";
        		}
        		
        	}
        	
        	return null;
        },JsonUtil.json());
        
        post("/createUser", (request, response) -> {
           
    		 String firstName = request.queryParams("firstName"); 
    		 String middleName = request.queryParams("middleName"); // validate
    		 String lastName = request.queryParams("lastName");
    		 String age = request.queryParams("age");
    		 String gender = request.queryParams("gender");
    		 String phone = request.queryParams("phone");
    		 String zip = request.queryParams("zip");
    		 int idAdd = userIDCounter++;
    		 
    		 int ageTemp = 0;
    		 try
    		 {
    		 ageTemp = Integer.parseInt(age);
    		 }
    		 catch(NumberFormatException n)
    		 {
    			 n.printStackTrace();
    			 ageTemp = -99;
    		 }
    		 catch(Exception e)
    		 {
    			 e.printStackTrace();
    			 ageTemp = -99;
    		 }
    		 if(firstName.matches(regExfirstName) && lastName.matches(regExLastName) && phone.matches(regExphone) && middleName.matches(regExMiddleName)
    				 && gender.matches(regExGender) && ageTemp > 0 ) 
    		 {
             User user = new User(idAdd+"",firstName ,middleName, lastName, age,gender , phone, zip);
             
             userService.getAllUser().add(user);

             response.status(201);
             return user; 
    		 }
    		 else
    		 {
    			 response.status(400); 
    			 return "Check input again";
    		 }
         }, JsonUtil.json());

    	 /*
    	  	addBook
			Adds a new book to the inventory
    	   */
         post("/addBook", (request, response) -> {       	
        	 String author = request.queryParams("author");
        	 String name = request.queryParams("name");
        	 //String CheckedOutBy = request.queryParams("CheckedOutBy");
        	 int idAdd = bookIDCounter++;
             Book book = new Book(author,name,idAdd+"");
             bookService.getAllBook().add(book);
             response.status(201);
             return book;
         }, JsonUtil.json());

         /*
         getAllUsers
		  Gives the list of all users registered in the library

         */
        get("/getAllUsers", (request, response) -> {
            return userService.getAllUser();
        }, JsonUtil.json());
         
        /*
        updateUser
		Update an existing user
         */
         put("/updateUser/:idUser", (request, response) -> {
           String userId = request.params(":idUser");
           String message ="";
             for(int i = 0; i < userService.getAllUser().size(); i++)
             {
            	 if(userService.getAllUser().get(i).getId().equals(userId))
            	 {
            		 String firstName = request.queryParams("firstName");
            		 String middleName = request.queryParams("middleName");
            		 String lastName = request.queryParams("lastName");
            		 String age = request.queryParams("age");
            		 String gender = request.queryParams("gender");
            		 String phone = request.queryParams("phone");
            		 String zip = request.queryParams("zip");
            		 
            		 if(zip != null)
            		 {
            			 userService.getAllUser().get(i).setZip(zip);
            		 }
            		 if(firstName != null && firstName.matches(regExfirstName))
            		 {
            			 userService.getAllUser().get(i).setFirstName(firstName);
            		 }
            		 if(middleName != null && middleName.matches(regExMiddleName))
            		 {
            			 userService.getAllUser().get(i).setMiddleName(middleName); 
            		 }
            		 if(lastName != null && lastName.matches(regExLastName))
            		 {
            			 userService.getAllUser().get(i).setLastName(lastName); 
            		 }
            		 if(age != null && (Integer.parseInt(age) > 0))
            		 {
            			 userService.getAllUser().get(i).setAge(age); 
            		 }
            		 if(gender != null && gender.matches(regExGender))
            		 {
            			 userService.getAllUser().get(i).setGender(gender);
            		 }
            		 if(phone != null && phone.matches(regExphone))
            		 {
            			 userService.getAllUser().get(i).setPhone(phone);
            		 }
            		 response.status(200);
            		 return (User)userService.getAllUser().get(i);
            	 }
            	 else
            	 {
            		 response.status(404);
            		 message = "UserNotFound";
            	 }
             }
             
             response.status(404);
             return message;
            
         }, JsonUtil.json());
        
        /*
         	findBookByName
			Given the name of the book, searches for any book that contains the given name
          */
         get("/findBookByName/:name", (request, response) -> {
             String bookName = request.params(":name");
             
             if (bookName == null || bookName == "")
             {
            	 response.status(400);
                 return "No book name entered";
             }
             
             ArrayList<Book> booksToReturn = new ArrayList<Book>();
             for(int i = 0 ; i < bookService.getAllBook().size() ; i++)
             {
            	 if(bookService.getAllBook().get(i).getName().equals(bookName) )
            	 {
            		 booksToReturn.add(bookService.getAllBook().get(i));            		 
            	 }
             }
             
             if (booksToReturn.size() > 0) {
                 return booksToReturn;
             } 
             else
             {
            	 response.status(404);
            	 return "Book Not found";
             }
         }, JsonUtil.json());
         
         /*
			checkOutBook
			Takes the user id and book id as inputs
			A book that is already checked out, cannot be checked out again (send proper error codes back) 
           */        
         	post("/checkOutBook/:idBook/:idUser", (request, response) -> {
             String idUser = request.params(":idUser");
             if (idUser == null || idUser == "") {
	        	 response.status(400);
	        	 return "BadRequest";
	         }
             
             String idBook = request.params(":idBook");
             if (idBook == null || idBook.equals("")) {
	        	 response.status(400);
	        	 return "BadRequest";
	         }
             
             for(int i = 0; i < bookService.getAllBook().size(); i++)
             {
            	 if(bookService.getAllBook().get(i).getId().equals(idBook))
            	 {
            		 Book bookToUpdate = bookService.getAllBook().get(i);
            		  if(bookToUpdate.getCheckedOutBy() !=  null &&
            				  bookToUpdate.getCheckedOutBy() != idUser)
            		  {
            			  response.status(409);
         	        	  return "BookAlreadyTaken";
            		  }
            		  else
            		  {
            			  bookToUpdate.setCheckedOutBy(idUser);
            			  response.status(201);
            			  return bookToUpdate;
           		  }
            	 }
            	 else
            	 {
            		 response.status(404);
    	        	 return "BookNotFound";
            	 }
             }
             
             response.status(404);
             return "BookNotFound";
         }, JsonUtil.json());
 
         /*
          	getAllBooks
			Gives list of all books in the library

          */
         get("/getAllBooks", (request, response) -> {
              
             return bookService.getAllBook();
         }, JsonUtil.json());
     }
}