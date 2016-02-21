package newProjectBook.edu.UserBook;

public class Book {
	/*
	 Instance variable for book
	 */
    public String author, name, id, CheckedOutBy;

    public Book(String author, String name, String id) 
    {
        this.author = author;
        this.name = name;
        this.id = id;
        this.CheckedOutBy = null ;
    }

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCheckedOutBy() {
		return CheckedOutBy;
	}

	public void setCheckedOutBy(String checkedOutBy) {
		CheckedOutBy = checkedOutBy;
	}

 
}
