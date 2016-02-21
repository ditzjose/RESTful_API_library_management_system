package newProjectBook.edu.UserBook;

import java.util.ArrayList;
/*
All User object are stored in this ArrayList
*/
public class UserService {

	public static ArrayList<User> ArrayUser = new ArrayList<User>();
	public ArrayList<User> getAllUser()
	{
		return ArrayUser;
	}

}
