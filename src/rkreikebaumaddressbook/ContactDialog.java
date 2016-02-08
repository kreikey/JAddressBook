package rkreikebaumaddressbook;

import org.eclipse.jface.dialogs.*;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class ContactDialog extends Dialog {
	private String title;
	private IInputValidator validator;
	@SuppressWarnings("unused")
	private Button okButton;
	@SuppressWarnings("unused")
	private Button cancelButton;
	private Text errorMessageText;
	@SuppressWarnings("unused")
	private String errorMessage;
	private Boolean okPressed;
	private Label lblFirstName;
	private Label lblLastName;
	private Label lblPhoneNumber;
	private Label lblEMail;
	private Text txtFirstName;
	private Text txtLastName;
	private Text txtPhoneNumber;
	private Text txtEMail;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String eMail;
	
	public ContactDialog(Shell parentShell, String dialogTitle, IInputValidator validator) {
		super(parentShell);
		this.title = dialogTitle;
		this.validator = validator;
		okPressed = false;
		
	}

	protected void buttonPressed(int buttonId) {
        if (buttonId == IDialogConstants.OK_ID) {
            // Put everything from the text fields into their respective strings
        	firstName = txtFirstName.getText();
        	lastName = txtLastName.getText();
        	phoneNumber = txtPhoneNumber.getText();
        	eMail = txtEMail.getText();
        	
        	okPressed = true;
        } else {
            okPressed = false;
        }
        super.buttonPressed(buttonId);
 	}

    protected void configureShell(Shell shell) {
        super.configureShell(shell);
        if (title != null) {
			shell.setText(title);
		}
    }
    
    protected void createButtonsForButtonBar(Composite parent) {
        // create OK and Cancel buttons by default
        okButton = createButton(parent, IDialogConstants.OK_ID,
                IDialogConstants.OK_LABEL, true);
        createButton(parent, IDialogConstants.CANCEL_ID,
                IDialogConstants.CANCEL_LABEL, false);
         txtFirstName.setFocus();
    }

    protected Control createDialogArea(Composite parent) {
        // create composite
        Composite composite = (Composite) super.createDialogArea(parent);
        composite.setLayoutData(new GridData(340, 210));
        //composite.setLayoutData(new GridData(400, 300, true, true));
        composite.setLayout(null);
		{
			lblFirstName = new Label(composite, SWT.LEFT);
			lblFirstName.setText("First Name:");
			lblFirstName.setBounds(12, 10, 100, 30);
		}
		{
			lblLastName = new Label(composite, SWT.LEFT);
			lblLastName.setText("Last Name:");
			lblLastName.setBounds(12, 40, 100, 30);
		}
		{
			lblPhoneNumber = new Label(composite, SWT.LEFT);
			lblPhoneNumber.setText("Phone Number:");
			lblPhoneNumber.setBounds(12, 70, 100, 30);
		}
		{
			lblEMail = new Label(composite, SWT.LEFT);
			lblEMail.setText("E-Mail:");
			lblEMail.setBounds(12, 100, 100, 30);
		}
		{
			txtFirstName = new Text(composite, SWT.BORDER);
			txtFirstName.setBounds(136, 10, 192, 25);
	        txtFirstName.addModifyListener(new ModifyListener() {
	            public void modifyText(ModifyEvent e) {
	            	validateInput();
	            }
	        });
	        txtFirstName.addFocusListener(new FocusAdapter() {
	        	public void focusGained(FocusEvent e) {
	            	validateInput();
	        	}
	        });	       
		}
		{
			txtLastName = new Text(composite, SWT.BORDER);
			txtLastName.setBounds(136, 40, 192, 25);
			txtLastName.addModifyListener(new ModifyListener() {
	            public void modifyText(ModifyEvent e) {
	            	validateInput();
	            }
	        });
			txtLastName.addFocusListener(new FocusAdapter() {
	        	public void focusGained(FocusEvent e) {
	            	validateInput();
        	}
	        });	       
		}
		{
			txtPhoneNumber = new Text(composite, SWT.BORDER);
			txtPhoneNumber.setBounds(136, 70, 192, 25);
			txtPhoneNumber.addModifyListener(new ModifyListener() {
	            public void modifyText(ModifyEvent e) {
	                validateInput();
	            }
	        });
			txtPhoneNumber.addFocusListener(new FocusAdapter() {
	        	public void focusGained(FocusEvent e) {
	                validateInput();
	        	}
	        });	       
		}
		{
			txtEMail = new Text(composite, SWT.BORDER);
			txtEMail.setBounds(136, 100, 192, 25);
			txtEMail.addModifyListener(new ModifyListener() {
	            public void modifyText(ModifyEvent e) {
	                validateInput();
	            }
	        });
			txtEMail.addFocusListener(new FocusAdapter() {
	        	public void focusGained(FocusEvent e) {
	                validateInput();
	        	}
	        });	       
		}
		
        errorMessageText = new Text(composite, SWT.READ_ONLY | SWT.WRAP);
//        errorMessageText.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
//                | GridData.HORIZONTAL_ALIGN_FILL));
        errorMessageText.setBounds(10, 140, 318, 50);
        errorMessageText.setBackground(errorMessageText.getDisplay()
                .getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
        // Set the error message text
        // See https://bugs.eclipse.org/bugs/show_bug.cgi?id=66292
        //setErrorMessage(errorMessage);
        
        applyDialogFont(composite);
        return composite;
    }
    
    public String getFirstName()
    {
    	return firstName;
    }
    
    public String getLastName()
    {
    	return lastName;
    }
    
    public String getPhoneNumber()
    {
    	return phoneNumber;
    }
    
    public String getEMail()
    {
    	return eMail;
    }
    
    public Boolean getOkPressed()
    {
    	return okPressed;
    }
    
    protected void validateInput() {
        String errorMessage = null;
        if (validator != null) {
            errorMessage = validator.isValid(txtFirstName.getText());
            if (errorMessage == null)
            	errorMessage = validator.isValid(txtLastName.getText());
            if (errorMessage == null)
            	errorMessage = validator.isValid(txtPhoneNumber.getText());
            if (errorMessage == null)
            	errorMessage = validator.isValid(txtEMail.getText());
        }
        // Bug 16256: important not to treat "" (blank error) the same as null
        // (no error)
        setErrorMessage(errorMessage);
        //return errorMessage;		//Instead of setting the ErrorMessage here, we want to return it for further processing.
    }
    
    public void setErrorMessage(String errorMessage) {
    	this.errorMessage = errorMessage;
    	if (errorMessageText != null && !errorMessageText.isDisposed()) {
    		errorMessageText.setText(errorMessage == null ? " \n " : errorMessage); //$NON-NLS-1$
    		// Disable the error message text control if there is no error, or
    		// no error text (empty or whitespace only).  Hide it also to avoid
    		// color change.
    		// See https://bugs.eclipse.org/bugs/show_bug.cgi?id=130281
    		boolean hasError = errorMessage != null && (StringConverter.removeWhiteSpaces(errorMessage)).length() > 0;
    		errorMessageText.setEnabled(hasError);
    		errorMessageText.setVisible(hasError);
    		errorMessageText.getParent().update();
    		// Access the ok button by id, in case clients have overridden button creation.
    		// See https://bugs.eclipse.org/bugs/show_bug.cgi?id=113643
    		Control button = getButton(IDialogConstants.OK_ID);
    		if (button != null) {
    			button.setEnabled(errorMessage == null);
    		}
    	}
    }    
    
    protected int getInputTextStyle() {
		return SWT.SINGLE | SWT.BORDER;
	} 
}
