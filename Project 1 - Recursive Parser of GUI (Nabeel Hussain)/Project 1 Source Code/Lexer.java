import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StreamTokenizer;

/**
 * This is the lexer/scanner class, which will read in an input file and break it down into the appropriate tokens. 
 * 
 * @author Nabeel Hussain
 */

public class Lexer
{
	private InputStream fileInputStream;   // It will be used to scan through the contents of a specified .txt file
	public StreamTokenizer st;             // It will parse the file into tokens
	private String punctuation = ",:;.()"; // a string that will contain all the punctuation tokens needed for the grammar. 
    private Token[] punctuationTokens = // an array that will hold all Tokens that correspond to a punctuation, in the order they are presented in the above string. 
    {
        Token.COMMA, Token.COLON, Token.SEMICOLON, Token.PERIOD, Token.LEFT_PAREN, Token.RIGHT_PAREN
    };
    
    
	/** 
	 * Constructor that will open up a stream connection to an input file 
	 * 
	 *@param fileName the input file containing the gui definition instructions
	 *@throws FileNotFoundException
	 */
	public Lexer(File fileName) throws FileNotFoundException
    {
		// open a stream connection to the file that is input by the user
		fileInputStream = new FileInputStream(fileName);	
    }
	
	
	/** 
	 * Method that will use the StreamTokenizer class to separate the input file into tokens 
	 */
	@SuppressWarnings("deprecation")
	public void parseFile()
	{	
		// parse or convert the file from the constructor class into individual tokens, using a StreamTokenizer
		st = new StreamTokenizer(fileInputStream);
	}
	
	
	/** 
	 * Method that will use the StreamTokenizer class to separate the input file into tokens 
	 * 
	 *@return the next token from the input file, if it corresponds to one of the predefined enum's from the Token class.
	 *@throws SyntaxException
	 */
	public Token getNextToken() throws SyntaxException, IOException
    {
		//Will display the next token in the StreamTokenizer
		int token = st.nextToken();
		
		// uses a switch statement to determine what the next token from the input file corresponds to from the enum Token class. 
		switch(token)
		{
			// If the token is a number
			case StreamTokenizer.TT_NUMBER:
			{
				// return the NUMBER enumerator from the Token class. 
				return Token.NUMBER;
			}
			//If the token is a word
			case StreamTokenizer.TT_WORD:
			{
				// and if that token contains a "." in it, then separate the word and the "." into different tokens. 
				st.ordinaryChar('.');
				
				// for all the enumerator keywords in the ToKen 
	            for (Token t : Token.values())
	            {
	            	// if the token from the StreamTokenizer(converted to all uppercase), matches any of the enumeration words from the Token class,
	            	//then return that specific enum. 
	                if (t.toString().equals(st.sval.toUpperCase()))
	                {
	                    return t;
	                }
	            }
	            // if the token does not match any of the words from the list, then throw a syntax error, because the parser will not be able to recognize the word when
	            // following the grammar rules to create the gui. 
	            throw new SyntaxException("Invalid token " + st.sval);
			}
			//If the token is a quotation mark symbol, then all the characters after it until the next quotation mark symbol will is put into a single string token.  
			case '"':
			{
				//If a string quote character is encountered, then a string is recognized, consisting of all characters after (but not including)
				// the string quote character, up to (but not including) the next occurrence of that same string quote character
				st.quoteChar('"');
				
				return Token.STRING;
			}	
			default:
				// for all the punctuation types
				 for (int i = 0; i < punctuation.length(); i++)
				 {
					 // if the token equals any of the punctuation characters from the punctuation string ",:;.()"
					 if (token == punctuation.charAt(i))
					 {
						 // then return the enum from the Token class that corresponds to that character position from the punctuationTokens[] array.
	                     return punctuationTokens[i];
					 }
				 }
		}
		
		// if the token doesn't fall into any of the above specifications and nothing is returned, then there is an invalid input token in the file.
		// it will not be able to correctly parse to create the gui
		throw new SyntaxException("Invalid token." );
    }
}