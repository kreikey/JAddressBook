package rkreikebaumaddressbook;

import java.io.*;
import nu.xom.*;

public class ContactManager {
	private int maxID;
	private LinkedList<Contact> contactList;
	
	public ContactManager() {
		super();
		contactList = new LinkedList<Contact>();
		maxID = 0;
	}
	
	public int getCount() {
		return contactList.getLength();
	}
	
	public int getIndex() {
		return contactList.getIndex() + 1;
	}
	
	public Contact search(String lastName) {
//		throw new UnsupportedOperationException();
		Contact t = this.getCur();
		Contact c = t;
		do {
			if (c.getLastName().compareToIgnoreCase(lastName) == 0)
				return c;
			c = this.getNext();
		} while (c != t);
		return null;
	}

	public String loadContacts(File inFile) {
		String error = null;
		
		try {
			Builder builder = new Builder();
			Document doc = builder.build(inFile);
			Element root = doc.getRootElement(), element, child;
			Elements entries = root.getChildElements();
			Contact contact;
			Boolean enoughInfo = true;
			
			// if list is not empty, empty it here.
			this.deleteAll();
			
			for (int x = 0; x < entries.size(); x++)
			{
				contact = new Contact();
				element = entries.get(x);
				child = element.getFirstChildElement("id");
				if (child != null)
					contact.setID(Integer.parseInt(child.getValue()));
				else
					contact.setID(this.getMaxID() + 1);
				child = element.getFirstChildElement("last");
				if (child != null)
					contact.setLastName(child.getValue().toUpperCase());
				else
					enoughInfo = false;
				child = element.getFirstChildElement("first");
				if (child != null)
					contact.setFirstName(child.getValue().toUpperCase());
				else
					enoughInfo = false;
				child = element.getFirstChildElement("phone");
				if (child != null)
					contact.setPhoneNumber(child.getValue());
				else
					enoughInfo = false;
				child = element.getFirstChildElement("email");
				if (child != null)
					contact.setEMail(child.getValue());
				else
					enoughInfo = false;
				if (enoughInfo)
					this.insert(contact);
				enoughInfo = true;
			}
			
			this.getNext();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			error = " File Not Found";
		} catch (ValidityException e) {
			e.printStackTrace();
			error = " Validity Exception";
		} catch (ParsingException e) {
			e.printStackTrace();
			error = " Parsing Exception";
		} catch (IOException e) {
			e.printStackTrace();
			error = " IO Exception";
		}
		
		//When we catch these exceptions, we need to put the right error message into a string and return it.
		return error;
	}

	public String saveContacts(File outFile) {				
		Element root = new Element("contacts");
		Element contact;
		Element id;
		Element last;
		Element first;
		Element phone;
		Element email;
		Document doc;
		String error = null;
		Contact c;
		
		c = this.getFirst();

		do
		{
			contact = new Element("contact");
			id = new Element("id");
			last = new Element("last");
			first = new Element("first");
			phone = new Element("phone");
			email = new Element("email");
			
			if (c != null) {
				id.appendChild(Integer.toString(c.getID()));
				last.appendChild(c.getLastName().toUpperCase());
				first.appendChild(c.getFirstName().toUpperCase());
				phone.appendChild(c.getPhoneNumber());
				email.appendChild(c.getEMail());
				contact.appendChild(id);
				contact.appendChild(last);
				contact.appendChild(first);
				contact.appendChild(phone);
				contact.appendChild(email);
				root.appendChild(contact);
			}
			c = this.getNext();
		} while (!this.atFirst());
		
		doc = new Document(root);

		try {
			Serializer serializer = new Serializer(new FileOutputStream(outFile));
			serializer.setIndent(4);
			serializer.setMaxLength(120);
			serializer.write(doc);
		} catch (IOException e) {
			System.err.println(e);
			error = " IO Exception";
		}
		
		return error;
	}

	public void insert(Contact c) {
		Node<Contact> n = new Node<Contact>();
		if (c.getID() > maxID)
			maxID = c.getID();
		n.content = c;
		contactList.insert(n);
	}

	public Contact delCur() {
		Node<Contact> tempNode = contactList.deleteCur();
		if (tempNode != null)
			return tempNode.content;
		return null;
	}

	public Contact getCur() {
		Node<Contact> tempNode = contactList.getCur();
		if (tempNode != null)
			return tempNode.content;
		return null;
	}

	public Contact getPrev() {
		Node<Contact> tempNode = contactList.getPrev();
		if (tempNode != null)
			return tempNode.content;
		return null;
	}

	public Contact getNext() {
		Node<Contact> tempNode = contactList.getNext();
		if (tempNode != null)
			return tempNode.content;
		return null;
	}

	public Contact jumpAhead() {
		Node<Contact> tempNode = contactList.jumpAhead();
		if (tempNode != null)
			return tempNode.content;
		return null;
	}

	public Contact jumpBack() {
		Node<Contact> tempNode = contactList.jumpBack();
		if (tempNode != null)
			return tempNode.content;
		return null;
	}
	
	public int getMaxID() {
		return maxID;
	}
	
	public Contact getFirst() {
		Node<Contact> tempNode = contactList.getFirst();
		if (tempNode != null)
			return tempNode.content;
		return null;
	}
	
	public boolean atFirst() {
		return contactList.atFirst();
	}
	
	public void deleteAll() {
//		contactList.deleteAll();
		contactList = new LinkedList<Contact>();	// I trust the garbage collector to get rid of my old linked list, circular references and all.
		maxID = 0;
	}
	
	public void sort() {
		contactList.sort();
	}
	
	public boolean confirmSort() {
		return contactList.confirmSort();
	}

}