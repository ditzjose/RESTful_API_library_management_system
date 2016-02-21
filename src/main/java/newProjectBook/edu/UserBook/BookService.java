package newProjectBook.edu.UserBook;

import java.util.ArrayList;
/*
 All book object are stored in this ArrayList
 */
public class BookService {

	public static ArrayList<Book> ArrayBooks = new ArrayList<Book>();

	public ArrayList<Book> getAllBook()
	{
		return ArrayBooks;
	}

}
