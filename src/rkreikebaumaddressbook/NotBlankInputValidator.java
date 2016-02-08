/**
 * 
 */
package rkreikebaumaddressbook;

import org.eclipse.jface.dialogs.*;

/**
 * @author Rick
 *
 */
public class NotBlankInputValidator implements IInputValidator {

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IInputValidator#isValid(java.lang.String)
	 */
	public String isValid(String newText) {
		// TODO Auto-generated method stub
		
		// Return an error string if input is blank (""). A Text.getText() method will never return a null string, so we probably don't need to handle this case.
		if (newText.equals(""))
			return "Not enough information";
		return null;
	}

}
