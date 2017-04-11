/**
* A subclass of the Exception class, which will be used if there is any syntactically incorrect tokens in the input file   
* @author Nabeel Hussain 
*/
@SuppressWarnings("serial")
public class SyntaxException extends Exception {
	
	/**
	 * no-arg Constructor
	 */
	public SyntaxException()
	{	
	}
	
	/**
	 * Constructor that will take in a message, which will be displayed if an Syntax error occurs. 
	 * 
	 *  @param message the error message that will be shown if the exception is thrown
	 */
	public SyntaxException(String message)
	{	
		super(message);	
	}
}
