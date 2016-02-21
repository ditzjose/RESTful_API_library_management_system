package newProjectBook.edu.UserBook;

public class User {
	/*
	 Instance variables for User
	 */
	String id, firstName,middleName, lastName, age, gender, phone , zip;
	
	public User(String id, String firstName, String middleName, String lastName, String age, String gender , String phone, String zip)
	{
		this.id =id;
		this.firstName = firstName;
		this.middleName = middleName ;
		this.lastName =  lastName;
		this.age =  age;
		this.gender = gender;
		this.phone = phone;
		this.zip =zip;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
	


}
