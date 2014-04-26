package com.dkhalife.projects;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * This class represents a textfield with a limited input size
 * 
 * @author Dany Khalife
 * 
 */
class JTextFieldLimit extends PlainDocument {
	/**
	 * Eclipse generated serial UID
	 */
	private static final long serialVersionUID = -2786287018211788411L;

	// The maximum length of this textfield
	private int maxlength;

	/**
	 * In order to construct a limited textfield you only need to specify a
	 * maximum length
	 * 
	 * @param maxlength The maximum length for the textfield
	 */
	JTextFieldLimit(int maxlength) {
		this.maxlength = maxlength;
	}

	/**
	 * This method overrides the super method in order to apply the limit
	 */
	public void insertString(int offset, String input, AttributeSet attr) throws BadLocationException {
		if (input == null) {
			return;
		}
		if (getLength() + input.length() <= this.maxlength)
			super.insertString(offset, input, attr);
	}
}