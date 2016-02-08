package rkreikebaumaddressbook;

public class Contact implements Comparable<Contact> {
	private int ID;
	private String lastName;
	private String firstName;
	private String phoneNumber;
	private String eMail;
	
	public Contact() {
		super();
		ID = 0;
		lastName = "";
		firstName = "";
		phoneNumber = "";
		eMail = "";
	}
	
	public int getID() {
		return ID;
	}
	
	public void setID(int ID) {
		this.ID = ID;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getEMail() {
		return eMail;
	}
	
	public void setEMail(String eMail) {
		this.eMail = eMail;
	}
	
	public int compareTo(Contact anotherContact) {
		// using CompareToIgnoreCase would be more robust, but using CompareTo is about 3x faster.
		return this.lastName.compareTo(anotherContact.getLastName());
	}
}
