package rkreikebaumaddressbook;

import java.io.*;
import java.util.*;

import com.cloudgarden.resource.*;

import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class MainWindow extends org.eclipse.swt.widgets.Composite {

	Properties appSettings = new Properties();
	Cursor defaultCursor; // To change the cursor to an arrow at any point after MainWindow() has executed, use setCursor(defaultCursor);
	Cursor waitCursor; // To change the cursor to an hourglass at any point after MainWindow() has executed, use setCursor(waitCursor);
	private Menu menu1;
	private MenuItem aboutMenuItem;
	private Menu helpMenu;
	private MenuItem helpMenuItem;
	private MenuItem exitMenuItem;
	private MenuItem closeFileMenuItem;
	private MenuItem saveFileMenuItem;
	private MenuItem newFileMenuItem;
	private MenuItem openFileMenuItem;
	private ToolItem newToolItem;
	private ToolItem saveToolItem;
	private ToolItem openToolItem;
	private ToolBar toolBar;
	@SuppressWarnings("unused")
	private MenuItem fileMenuSep2;
	@SuppressWarnings("unused")
	private MenuItem fileMenuSep1;
	private Composite clientArea;
	private Label statusText;
	private Composite statusArea;
	private Menu fileMenu;
	private MenuItem fileMenuItem;
	private Label lblID;
	private Label lblFirstName;
	private Label lblLastName;
	private Label lblPhoneNumber;
	private Label lblEMail;
	private Text txtID;
	private Text txtFirstName;
	private Text txtLastName;
	private Text txtEMail;
	private Button btnNext;
	private Button btnPrev;
	private Button btnJumpAhead;
	private Button btnJumpBack;
	private Button btnInsert;
	private Button btnEdit;
	private Button btnDelete;
	private Button btnSearch;
	private Button btnSort;
	private Text txtSearchLastName;
	private Group grpSearch;
	private Text txtPhoneNumber;
	private Composite cmpOperations;
	private Composite cmpNavigation;
	private Group grpContact;
	private ContactManager addressBook;
	private File curFile;
	
	{
		// Register as a resource user - SWTResourceManager will handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}

	public static void main(String[] args) {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		@SuppressWarnings("unused")
		MainWindow inst = new MainWindow(shell, SWT.NULL);
		shell.setLayout(new FillLayout());
		shell.setImage(SWTResourceManager.getImage("images/16x16.png"));
		shell.setText("RKreikebaumAddressBook");
		shell.setBackgroundImage(SWTResourceManager.getImage("images/ToolbarBackground.gif"));
		shell.layout();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	public MainWindow(Composite parent, int style) {
		super(parent, style);
		initGUI();
		setPreferences();
		addressBook = new ContactManager();
		waitCursor = getDisplay().getSystemCursor(SWT.CURSOR_WAIT);
		defaultCursor = getDisplay().getSystemCursor(SWT.CURSOR_ARROW);
		clientArea.setFocus();
	}

	// Load application settings from .ini file
	private void setPreferences() {
		try {
			appSettings.load(new FileInputStream("appsettings.ini"));
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}

		// By default, center window
		int width = Integer.parseInt(appSettings.getProperty("width", "640"));
		int height = Integer.parseInt(appSettings.getProperty("height", "480"));
		Rectangle screenBounds = getDisplay().getBounds();
		int defaultTop = (screenBounds.height - height) / 2;
		int defaultLeft = (screenBounds.width - width) / 2;
		int top = Integer.parseInt(appSettings.getProperty("top", String.valueOf(defaultTop)));
		int left = Integer.parseInt(appSettings.getProperty("left", String.valueOf(defaultLeft)));
		getShell().setSize(width, height);
		getShell().setLocation(left, top);
		saveShellBounds();
	}

	/**
	 * Initializes the GUI.
	 */
	private void initGUI() {
		try {
			getShell().addDisposeListener(new DisposeListener() {
				public void widgetDisposed(DisposeEvent evt) {
					shellWidgetDisposed(evt);
				}
			});

			getShell().addControlListener(new ControlAdapter() {
				public void controlResized(ControlEvent evt) {
					shellControlResized(evt);
				}
			});

			getShell().addControlListener(new ControlAdapter() {
				public void controlMoved(ControlEvent evt) {
					shellControlMoved(evt);
				}
			});

			GridLayout thisLayout = new GridLayout();
			this.setLayout(thisLayout);
			{
				GridData toolBarLData = new GridData();
				toolBarLData.grabExcessHorizontalSpace = true;
				toolBarLData.horizontalAlignment = GridData.FILL;
				toolBar = new ToolBar(this, SWT.FLAT);
				toolBar.setLayoutData(toolBarLData);
				toolBar.setBackgroundImage(SWTResourceManager.getImage("images/ToolbarBackground.gif"));
				{
					newToolItem = new ToolItem(toolBar, SWT.NONE);
					newToolItem.setImage(SWTResourceManager.getImage("images/new.gif"));
					newToolItem.setToolTipText("New");
				}
				{
					openToolItem = new ToolItem(toolBar, SWT.NONE);
					openToolItem.setToolTipText("Open");
					openToolItem.setImage(SWTResourceManager.getImage("images/open.gif"));
					openToolItem.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							openToolItemWidgetSelected(evt);
						}
					});
				}
				{
					saveToolItem = new ToolItem(toolBar, SWT.NONE);
					saveToolItem.setToolTipText("Save");
					saveToolItem.setImage(SWTResourceManager.getImage("images/save.gif"));
					saveToolItem.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							saveToolItemWidgetSelected(evt);
						}
					});
				}
			}
			{
				clientArea = new Composite(this, SWT.NONE);
				GridData clientAreaLData = new GridData();
				clientAreaLData.grabExcessHorizontalSpace = true;
				clientAreaLData.grabExcessVerticalSpace = true;
				clientAreaLData.horizontalAlignment = GridData.FILL;
				clientAreaLData.verticalAlignment = GridData.FILL;
				clientArea.setLayoutData(clientAreaLData);
				clientArea.setLayout(null);
				{
					grpContact = new Group(clientArea, SWT.NONE);
					grpContact.setLayout(null);
					grpContact.setText("Current Contact");
					grpContact.setBounds(12, 12, 346, 181);
					{
						lblID = new Label(grpContact, SWT.LEFT);
						lblID.setText("ID:");
						lblID.setBounds(12, 20, 100, 30);
					}
					{
						txtID = new Text(grpContact, SWT.READ_ONLY);
						txtID.setBounds(118, 22, 212, 24);
					}
					{
						lblFirstName = new Label(grpContact, SWT.LEFT);
						lblFirstName.setText("First Name:");
						lblFirstName.setBounds(12, 50, 100, 30);
					}
					{
						lblLastName = new Label(grpContact, SWT.LEFT);
						lblLastName.setText("Last Name:");
						lblLastName.setBounds(12, 80, 100, 30);
					}
					{
						lblPhoneNumber = new Label(grpContact, SWT.LEFT);
						lblPhoneNumber.setText("Phone Number:");
						lblPhoneNumber.setBounds(12, 116, 100, 30);
					}
					{
						lblEMail = new Label(grpContact, SWT.LEFT);
						lblEMail.setText("E-Mail:");
						lblEMail.setBounds(12, 147, 100, 30);
					}
					{
						txtFirstName = new Text(grpContact, SWT.READ_ONLY);
						txtFirstName.setBounds(118, 52, 212, 24);
					}
					{
						txtLastName = new Text(grpContact, SWT.READ_ONLY);
						txtLastName.setBounds(118, 82, 212, 25);
					}
					{
						txtEMail = new Text(grpContact, SWT.READ_ONLY);
						txtEMail.setBounds(118, 145, 212, 24);
					}
					{
						txtPhoneNumber = new Text(grpContact, SWT.READ_ONLY);
						txtPhoneNumber.setBounds(118, 113, 212, 26);
					}
				}
				{
					cmpNavigation = new Composite(clientArea, SWT.NONE);
					cmpNavigation.setLayout(null);
					cmpNavigation.setBounds(12, 199, 346, 42);
					{
						btnJumpBack = new Button(cmpNavigation, SWT.PUSH | SWT.CENTER);
						btnJumpBack.setText("<< Jump Back");
						btnJumpBack.setBounds(5, 5, 91, 30);
						btnJumpBack.addMouseWheelListener(new MouseWheelListener() {
							public void mouseScrolled(MouseEvent evt) {
								btnJumpBackMouseScrolled(evt);
							}
						});
						btnJumpBack.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent evt) {
								btnJumpBackWidgetSelected(evt);
							}
						});
					}
					{
						btnPrev = new Button(cmpNavigation, SWT.PUSH | SWT.CENTER);
						btnPrev.setText("< Prev");
						btnPrev.setBounds(102, 5, 60, 30);
						btnPrev.addMouseWheelListener(new MouseWheelListener() {
							public void mouseScrolled(MouseEvent evt) {
								btnPrevMouseScrolled(evt);
							}
						});
						btnPrev.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent evt) {
								btnPrevWidgetSelected(evt);
							}
						});
					}
					{
						btnNext = new Button(cmpNavigation, SWT.PUSH | SWT.CENTER);
						btnNext.setText("Next >");
						btnNext.setBounds(172, 5, 60, 30);
						btnNext.addMouseWheelListener(new MouseWheelListener() {
							public void mouseScrolled(MouseEvent evt) {
								btnNextMouseScrolled(evt);
							}
						});
						btnNext.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent evt) {
								btnNextWidgetSelected(evt);
							}
						});
					}
					{
						btnJumpAhead = new Button(cmpNavigation, SWT.PUSH | SWT.CENTER);
						btnJumpAhead.setText("Jump Ahead >>");
						btnJumpAhead.setBounds(238, 5, 102, 30);
						btnJumpAhead.addMouseWheelListener(new MouseWheelListener() {
							public void mouseScrolled(MouseEvent evt) {
								btnJumpAheadMouseScrolled(evt);
							}
						});
						btnJumpAhead.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent evt) {
								btnJumpAheadWidgetSelected(evt);
							}
						});
					}
				}
				{
					cmpOperations = new Composite(clientArea, SWT.NONE);
					cmpOperations.setLayout(null);
					cmpOperations.setBounds(372, 19, 78, 222);
					{
						btnInsert = new Button(cmpOperations, SWT.PUSH | SWT.CENTER);
						btnInsert.setText("Add");
						btnInsert.setBounds(6, 6, 60, 30);
						btnInsert.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent evt) {
								btnInsertWidgetSelected(evt);
							}
						});
					}
					{
						btnDelete = new Button(cmpOperations, SWT.PUSH | SWT.CENTER);
						btnDelete.setText("Delete");
						btnDelete.setBounds(6, 78, 60, 30);
						btnDelete.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent evt) {
								btnDeleteWidgetSelected(evt);
							}
						});
					}
					{
						btnEdit = new Button(cmpOperations, SWT.PUSH | SWT.CENTER);
						btnEdit.setText("Edit");
						btnEdit.setBounds(6, 42, 60, 30);
						btnEdit.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent evt) {
								btnEditWidgetSelected(evt);
							}
						});
					}
					{
						btnSort = new Button(cmpOperations, SWT.PUSH | SWT.CENTER);
						btnSort.setText("Sort");
						btnSort.setSize(60, 30);
						btnSort.setBounds(6, 114, 60, 30);
						btnSort.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent evt) {
								btnSortWidgetSelected(evt);
							}
						});
					}
				}
				{
					grpSearch = new Group(clientArea, SWT.NONE);
					grpSearch.setLayout(null);
					grpSearch.setText("Search by Last Name");
					grpSearch.setBounds(12, 253, 346, 73);
					{
						txtSearchLastName = new Text(grpSearch, SWT.BORDER);
						txtSearchLastName.setBounds(12, 26, 243, 29);
						txtSearchLastName.addFocusListener(new FocusAdapter() {
							public void focusLost(FocusEvent evt) {
								txtSearchLastNameFocusLost(evt);
							}
							public void focusGained(FocusEvent evt) {
								txtSearchLastNameFocusGained(evt);
							}
						});
					}
					{
						btnSearch = new Button(grpSearch, SWT.PUSH | SWT.CENTER);
						btnSearch.setText("Search");
						btnSearch.setBounds(269, 26, 65, 27);
						btnSearch.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent evt) {
								btnSearchWidgetSelected(evt);
							}
						});
					}
				}
			}
			{
				statusArea = new Composite(this, SWT.NONE);
				GridLayout statusAreaLayout = new GridLayout();
				statusAreaLayout.makeColumnsEqualWidth = true;
				statusAreaLayout.horizontalSpacing = 0;
				statusAreaLayout.marginHeight = 0;
				statusAreaLayout.marginWidth = 0;
				statusAreaLayout.verticalSpacing = 0;
				statusAreaLayout.marginLeft = 3;
				statusAreaLayout.marginRight = 3;
				statusAreaLayout.marginTop = 3;
				statusAreaLayout.marginBottom = 3;
				statusArea.setLayout(statusAreaLayout);
				GridData statusAreaLData = new GridData();
				statusAreaLData.horizontalAlignment = GridData.FILL;
				statusAreaLData.grabExcessHorizontalSpace = true;
				statusArea.setLayoutData(statusAreaLData);
				statusArea.setBackground(SWTResourceManager.getColor(239, 237, 224));
				{
					statusText = new Label(statusArea, SWT.BORDER);
					statusText.setText(" Ready");
					GridData txtStatusLData = new GridData();
					txtStatusLData.horizontalAlignment = GridData.FILL;
					txtStatusLData.grabExcessHorizontalSpace = true;
					txtStatusLData.verticalIndent = 3;
					statusText.setLayoutData(txtStatusLData);
				}
			}
			thisLayout.verticalSpacing = 0;
			thisLayout.marginWidth = 0;
			thisLayout.marginHeight = 0;
			thisLayout.horizontalSpacing = 0;
			thisLayout.marginTop = 3;
			{
				menu1 = new Menu(getShell(), SWT.BAR);
				getShell().setMenuBar(menu1);
				{
					fileMenuItem = new MenuItem(menu1, SWT.CASCADE);
					fileMenuItem.setText("&File");
					{
						fileMenu = new Menu(fileMenuItem);
						{
							newFileMenuItem = new MenuItem(fileMenu, SWT.PUSH);
							newFileMenuItem.setText("&New");
							newFileMenuItem.setImage(SWTResourceManager.getImage("images/new.gif"));
						}
						{
							openFileMenuItem = new MenuItem(fileMenu, SWT.PUSH);
							openFileMenuItem.setText("&Open");
							openFileMenuItem.setImage(SWTResourceManager.getImage("images/open.gif"));
							openFileMenuItem.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent evt) {
									openFileMenuItemWidgetSelected(evt);
								}
							});
						}
						{
							closeFileMenuItem = new MenuItem(fileMenu, SWT.CASCADE);
							closeFileMenuItem.setText("Close");
						}
						{
							fileMenuSep1 = new MenuItem(fileMenu, SWT.SEPARATOR);
						}
						{
							saveFileMenuItem = new MenuItem(fileMenu, SWT.PUSH);
							saveFileMenuItem.setText("&Save");
							saveFileMenuItem.setImage(SWTResourceManager.getImage("images/save.gif"));
							saveFileMenuItem.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent evt) {
									saveFileMenuItemWidgetSelected(evt);
								}
							});
						}
						{
							fileMenuSep2 = new MenuItem(fileMenu, SWT.SEPARATOR);
						}
						{
							exitMenuItem = new MenuItem(fileMenu, SWT.CASCADE);
							exitMenuItem.setText("E&xit");
							exitMenuItem.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent evt) {
									exitMenuItemWidgetSelected(evt);
								}
							});
						}
						fileMenuItem.setMenu(fileMenu);
					}
				}
				{
					helpMenuItem = new MenuItem(menu1, SWT.CASCADE);
					helpMenuItem.setText("&Help");
					{
						helpMenu = new Menu(helpMenuItem);
						{
							aboutMenuItem = new MenuItem(helpMenu, SWT.CASCADE);
							aboutMenuItem.setText("&About");
							aboutMenuItem.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent evt) {
									aboutMenuItemWidgetSelected(evt);
								}
							});
						}
						helpMenuItem.setMenu(helpMenu);
					}
				}
			}
			pack();
			this.setSize(466, 438);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void exitMenuItemWidgetSelected(SelectionEvent evt) {
		try {
			// Save app settings to file
			appSettings.store(new FileOutputStream("appsettings.ini"), "");
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
		getShell().dispose();
	}

	private void openFileMenuItemWidgetSelected(SelectionEvent evt) {
		String[] exts = new String[1];
		exts[0] = "*.xml";
		String fileName;
		String error = null;
				
		FileDialog dialog = new FileDialog(getShell(), SWT.OPEN);
		dialog.setFilterExtensions(exts);
		dialog.setFilterIndex(0);
		fileName = dialog.open();
		
		if (fileName != null) {
			curFile = new File(fileName);
			getShell().setText(curFile.getName());
			error = addressBook.loadContacts(curFile);
			displayContact(addressBook.getCur());
			displayContactNumber();
			if (error != null)
			{
				setStatus(error);
			}
		}
	}

	private void openToolItemWidgetSelected(SelectionEvent evt) {
		openFileMenuItemWidgetSelected(evt);
	}

	private void aboutMenuItemWidgetSelected(SelectionEvent evt) {
		MessageBox message = new MessageBox(getShell(), SWT.OK | SWT.ICON_INFORMATION);
		message.setText("About RKreikebaumAddressBook");
		message.setMessage("A friendly little Address Book program by Rick Kreikebaum\n\nRKreikebaumAddressBook v1.0");
		message.open();
	}

	private void shellWidgetDisposed(DisposeEvent evt) {
		try {
			// Save app settings to file
			appSettings.store(new FileOutputStream("appsettings.ini"), "");
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
	}

	private void shellControlResized(ControlEvent evt) {
		saveShellBounds();
	}

	// Save window location in appSettings hash table
	private void saveShellBounds() {
		// Save window bounds in app settings
		Rectangle bounds = getShell().getBounds();
		appSettings.setProperty("top", String.valueOf(bounds.y));
		appSettings.setProperty("left", String.valueOf(bounds.x));
		appSettings.setProperty("width", String.valueOf(bounds.width));
		appSettings.setProperty("height", String.valueOf(bounds.height));
	}

	private void shellControlMoved(ControlEvent evt) {
		saveShellBounds();
	}

	private void setStatus(String message) {
		statusText.setText(message);
	}
	
	private void displayContact(Contact c)
	{
		if (c != null)
		{
			txtID.setText(Integer.toString(c.getID()));
			txtLastName.setText(c.getLastName());
			txtFirstName.setText(c.getFirstName());
			txtPhoneNumber.setText(c.getPhoneNumber());
			txtEMail.setText(c.getEMail());
		}
		else
		{
			txtID.setText("");
			txtLastName.setText("");
			txtFirstName.setText("");
			txtPhoneNumber.setText("");
			txtEMail.setText("");
		}
	}
	
	private void displayContactNumber()
	{
		//addressBook.getIndex()
		//addressBook.getCount()
		setStatus("Displaying Contact " + addressBook.getIndex() + " of " + addressBook.getCount() + ".");
	}
	
	private void btnInsertWidgetSelected(SelectionEvent evt) {
		//System.out.println("btnInsert.widgetSelected, event="+evt);
		Contact c = new Contact();
		NotBlankInputValidator validator = new NotBlankInputValidator(); 
		ContactDialog dlg = new ContactDialog(getShell(), "Enter a new contact.", validator);
		dlg.open();
		if (dlg.getOkPressed())
		{
			c.setID(addressBook.getMaxID() + 1);
			c.setFirstName(dlg.getFirstName().toUpperCase());
			c.setLastName(dlg.getLastName().toUpperCase());
			c.setPhoneNumber(dlg.getPhoneNumber());
			c.setEMail(dlg.getEMail());
			addressBook.insert(c);
			displayContact(c);
			displayContactNumber();
		}
	}
	
	private void btnPrevWidgetSelected(SelectionEvent evt) {
		//System.out.println("btnPrev.widgetSelected, event="+evt);
		displayContact(addressBook.getPrev());
		displayContactNumber();
	}
	
	private void btnNextWidgetSelected(SelectionEvent evt) {
		//System.out.println("btnNext.widgetSelected, event="+evt);
		displayContact(addressBook.getNext());
		displayContactNumber();
	}
	
	private void btnJumpAheadWidgetSelected(SelectionEvent evt) {
//		System.out.println("btnJumpAhead.widgetSelected, event="+evt);
		displayContact(addressBook.jumpAhead());
		displayContactNumber();
	}
	
	private void btnJumpBackWidgetSelected(SelectionEvent evt) {
//		System.out.println("btnJumpBack.widgetSelected, event="+evt);
		displayContact(addressBook.jumpBack());
		displayContactNumber();
	}
	
	private void btnDeleteWidgetSelected(SelectionEvent evt) {
//		System.out.println("btnDelete.widgetSelected, event="+evt);
		addressBook.delCur();
		displayContact(addressBook.getCur());
		displayContactNumber();
	}
	
	private void saveFileMenuItemWidgetSelected(SelectionEvent evt) {
		//System.out.println("saveFileMenuItem.widgetSelected, event="+evt);
		String[] exts = new String[1];
		exts[0] = "*.xml";
		String fileName;
				
		if (curFile == null)
		{
			FileDialog dialog = new FileDialog(getShell(), SWT.SAVE);
			dialog.setFilterExtensions(exts);
			dialog.setFilterIndex(0);
			
			fileName = dialog.open();
			if (fileName != null) {
				curFile = new File(fileName);
				getShell().setText(fileName);
				
				// Call ContactManager.saveContacts() method
				addressBook.saveContacts(curFile);
			}
		}
		else
		{
		// Call ContactManager.saveContacts() method
			addressBook.saveContacts(curFile);
		}
		setStatus(" Saved");
	}
	
	private void saveToolItemWidgetSelected(SelectionEvent evt) {
		//System.out.println("saveToolItem.widgetSelected, event="+evt);
		saveFileMenuItemWidgetSelected(evt);
	}
	
	private void btnEditWidgetSelected(SelectionEvent evt) {
		setStatus(" Not Implemented");
	}
	
	private void btnSearchWidgetSelected(SelectionEvent evt) {
		Contact c = addressBook.search(txtSearchLastName.getText().toUpperCase());
		if (c == null) {
			setStatus(" Contact not found");
			return;
		}
		else {
			displayContact(c);
			displayContactNumber();
		}
	}
	
	private void btnSortWidgetSelected(SelectionEvent evt) {
		long startTime;
		long endTime;
		int elapsed;

		startTime = System.nanoTime();		
		addressBook.sort();
		endTime = System.nanoTime();
		elapsed = Math.round((endTime - startTime)/1000000);
		displayContact(addressBook.getCur());
		displayContactNumber();
		statusText.setText(statusText.getText() + " Sorted in " + Integer.toString(elapsed) + " milliseconds.");
//		if (!addressBook.confirmSort()) {
//			displayContact(addressBook.getCur());
//			displayContactNumber();
//			statusText.setText(statusText.getText() + " Sort failed!");
//		}
	}
	
	private void txtSearchLastNameFocusGained(FocusEvent evt) {
		getShell().setDefaultButton(btnSearch);
	}
	
	private void txtSearchLastNameFocusLost(FocusEvent evt) {
		getShell().setDefaultButton(null);
	}
	
	private void btnNextMouseScrolled(MouseEvent evt) {
		displayContact(addressBook.getNext());
		displayContactNumber();
	}
	
	private void btnPrevMouseScrolled(MouseEvent evt) {
		displayContact(addressBook.getPrev());
		displayContactNumber();
	}
	
	private void btnJumpAheadMouseScrolled(MouseEvent evt) {
		displayContact(addressBook.jumpAhead());
		displayContactNumber();
	}
	
	private void btnJumpBackMouseScrolled(MouseEvent evt) {
		displayContact(addressBook.jumpBack());
		displayContactNumber();
	}
	
	private void btnGenerateWidgetSelected(SelectionEvent evt) {
		String lastName, firstName, phoneNumber, eMail;
		int ID;
		Contact c;
		char[] data = new char[5];
		char[] numbers = new char[10];
		
		addressBook.deleteAll();
		
		for (int x = 0; x < 500000; x++) {
			ID = x;
			for (int y = 0; y < 5; ++y) {
				data[y] = (char)(Math.random() * 26 + 65);
			}
			lastName = new String(data);
			for (int y = 0; y < 5; ++y) {
				data[y] = (char)(Math.random() * 26 + 65);
			}
			firstName = new String(data);
			for (int y = 0; y < 10; ++y) {
				numbers[y] = (char)(Math.random() * 10 + 48);
			}
			phoneNumber = new String(numbers);
			for (int y = 0; y < 5; ++y) {
				data[y] = (char)(Math.random() * 26 + 65);
			}
			eMail = new String(data);
			
			c = new Contact();
			c.setID(ID);
			c.setLastName(lastName);
			c.setFirstName(firstName);
			c.setPhoneNumber(phoneNumber);
			c.setEMail(eMail);
			
			addressBook.insert(c);
		}
		displayContact(addressBook.getCur());
		displayContactNumber();
	}

}
