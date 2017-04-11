import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.*;

/**
 * This is the main parser class, which will follow the grammar to create a GUI. It will read in the tokens from a gui definition input file the user selects. 
 * 
 * Name: Nabeel Hussain
 * Class: CMSC 330
 * Professor: Amitava Karmaker 
 * Project 1
 * Date: 1/31/2017
 * 
 * @author Nabeel Hussain
 */

@SuppressWarnings("serial")
public class Parser extends JFrame
{
	private JFileChooser fileChooser;
	private File selectedFile;
	private Token token;  // Variable that will keep track of the next token read, from the gui definition input file that is selected.   
	private Lexer lexer;  // An instance of the scanner class, which will determine the next token from the input .txt file that is read in.
	private Token panelNestingLevel; // will keep track which level the token being parsed is on, since panels can be nested in other panels. 
	
	
	private JFrame frame; // A frame which will be used to display the GUI
	private JPanel panel; // A panel which will be added to the gui frame, and other multiple panels will be nested inside it if necessary
	private JRadioButton radioButton;
    
	public Parser()
	{
		// Will display a file chooser box allowing the user to select a file to open, in order to read its data. 
		fileChooser = new JFileChooser();
		int status = fileChooser.showOpenDialog(null);
		
		if (status == JFileChooser.APPROVE_OPTION)
		{
			// store the file in the selectedFile variable. 
			selectedFile = fileChooser.getSelectedFile();
		}
			// Use an instance of the scanner class, in order to break the input file into tokens using its parseFile() method. 
			try
			{
				lexer = new Lexer(selectedFile);
				
				lexer.parseFile();
				
				// Will initialize the Token class variable to contain the first token from the input file, using the lexer scanner class.
				token = lexer.getNextToken(); 
				
				// Create the gui, by calling the createGUI() method of this recursive descent parser. 
				this.gui();
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
			catch (SyntaxException e)
			{
				e.printStackTrace();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
	}
	
	/***
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[]args) throws IOException
	{
		new Parser();
	}
	
	
	/** 
	 * Method to parse the <gui> production. 
	 * 
	 * <gui> ::= Window STRING '(' NUMBER ',' NUMBER ')' <layout> <widgets> End '.'
	 * 
	 * @return a boolean value: true, if the statement parses and false otherwise. 
	 */
	public boolean gui() throws SyntaxException, IOException
	{
		int windowWidthSize;  // variable to hold the window frame width size
		int windowHeightSize; // variable to hold the window frame height size
		
		
		// We enter every method assuming that the next token is already in the token variable and leave every method having gotten the next token. 
		
		// If the first token read is "Window"
		if(token == Token.WINDOW)
        {
			panelNestingLevel = Token.WINDOW;
			
			// then create a new window frame.
            frame = new JFrame();
            
            // Whenever a token is recognized, it is consumer, and another token must be called. 
            token = lexer.getNextToken();
            
            // If the next token read is a String
            if(token == Token.STRING)
            {
            	// set the string contained in the token as the title border of the window frame.
                frame.setTitle(lexer.st.sval);
                
                // get the next token from the lexer scanner class
                token = lexer.getNextToken();
                
                // If the token read is the punctuation "("
                if(token == Token.LEFT_PAREN)
                {
                	// get the next token from the lexer scanner class
                    token = lexer.getNextToken();
                    
                    // If the token read is a number
                    if(token == Token.NUMBER)
                    {
                    	// store the number contained in the token to be used as the width parameter of the JFrame.
                    	windowWidthSize = (int) lexer.st.nval;
                        
                    	// get the next token from the lexer scanner class
                        token = lexer.getNextToken();
                        
                        // If the token read is the punctuation ","
                        if(token == Token.COMMA)
                        {
                        	// get the next token from the lexer scanner class
                            token = lexer.getNextToken();
                            
                            // If the token read is a number
                            if(token == Token.NUMBER)
                            {
                            	// store the number contained in the token to be used as the height parameter of the JFrame.
                            	windowHeightSize = (int) lexer.st.nval;
                            	
                            	// get the next token from the lexer scanner class
                                token = lexer.getNextToken();
                                
                                // If the token read is the punctuation ")"
                                if(token == Token.RIGHT_PAREN)
                                {
                                	// set the size of the frame using the windowWidthSize and windowHeightSize variables.
                                    frame.setSize(windowWidthSize, windowHeightSize);
                                    
                                	// get the next token from the lexer scanner class
                                    token = lexer.getNextToken();
                                    
                                    // if the <layout> production is true
                                    if(guiLayout() == true)
                                    {
                                    	// if the <widgets> production is true
                                        if(widgets() == true)
                                        {
                                        	// If the token read is the word "End"
                                            if(token == Token.END)
                                            {
                                            	// get the next token from the lexer scanner class
                                                token = lexer.getNextToken();
                                                
                                            	// If the token read is the punctuation "."
                                                if(token == Token.PERIOD)
                                                {
                                                	// set the JFrame to be visible, so that the newly created GUI will be displayed. 
                                                    frame.setVisible(true);
                                                    // returns true, if all the above conditions are met for the <gui> production. 
                                                    return true;
                                                }
                            	                //else, if the token is not a "."
                            	                //then throw a syntax exception indicating that the file does not syntactically follow the grammar rules. 
                            	                else
                            	                {
                            	                	throw new SyntaxException("Syntax error in file. File does not syntactically follow the grammar rules" );
                            	                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
		// returns false, if the grammar rules are not met in the GUI definition language that's defined in the input file. 
		return false;
	}
	
	
	/** 
	 * Method to parse the <layout> production.
	 * 
	 * @return a boolean value: true, if the statement parses and false otherwise. 
	 * @throws IOException 
	 * @throws SyntaxException 
	 */
	public boolean guiLayout() throws SyntaxException, IOException
	{
		// If the current token is "Layout"
		if(token == Token.LAYOUT)
		{
			// get the next token from the lexer scanner class
			token = lexer.getNextToken();
			
			// if the <layout_type> production is true
			if(layoutType() == true)
			{
				// If the token read is the punctuation ":"
				if(token == Token.COLON)
				{
					// get the next token from the lexer scanner class
                    token = lexer.getNextToken();
                    
                    // return true if all the above requirements are met, indicating that this production has correctly parsed. 
                    return true;
                }
                //else, if the token is not a ":"
                //then throw a syntax exception indicating that the file does not syntactically follow the grammar rules. 
                else
                {
                	throw new SyntaxException("Syntax error in file. File does not syntactically follow the grammar rules" );
                }
			}		
		}
		// If the beginning token for the <layout> production is not the string "Layout," then it will not parse. 
		return false;
	}
	
	
	/** 
	 * Method to parse the <layout_type> production.
	 * 
	 * @return a boolean value: true, if the statement parses and false otherwise.
	 * @throws IOException 
	 * @throws SyntaxException  
	 */
	public boolean layoutType() throws SyntaxException, IOException
	{
		// Variables to hold the grid layout parameters.
		int gridRows;
		int gridColumns;
		int gridHorizontalGap;
		int gridVerticleGap;
		
		switch(token)
		{
	    	//If the token corresponds to the "Flow" option in the <layout_type> production	
			case FLOW:
				// If we are adding elements to the window frame, then add the layout to the frame variable
				if(panelNestingLevel == Token.WINDOW)
				{	
					frame.setLayout(new FlowLayout());
				}
				// otherwise, it will be added to the panel
				else
				{
					panel.setLayout(new FlowLayout());
				}
							
				// get the next token from the lexer scanner class
				token = lexer.getNextToken();
				
				return true;
			
			//If the token corresponds to the "Grid" option in the <layout_type> production	
			case GRID:
				//In the production for <layout_type> that define the grid layout, the first two numbers represent the number of rows and columns,
				//and the optional next two numbers represent the horizontal and vertical gaps.
				//ex. Grid(4, 3, 5, 5)		
				
				// Grid '(' NUMBER ',' NUMBER [',' NUMBER ',' NUMBER] ')'
							
				// get the next token from the lexer scanner class
				token = lexer.getNextToken();
				
				// If the token is a "("
				if(token == Token.LEFT_PAREN)
				{
					// get the next token from the lexer scanner class
					token = lexer.getNextToken();
					
					// If the token is a number
					if (token == Token.NUMBER)
					{
						// store the integer value of the current token into the gridRows variable
						gridRows = (int) lexer.st.nval;
						
						// get the next token from the lexer scanner class
						token = lexer.getNextToken();
						
						// If the token is a ","
						if (token == Token.COMMA)
						{						
							token = lexer.getNextToken();
							
							// If the token is a number
							if (token == Token.NUMBER)
							{
								// store the integer value of the current token into the gridColumns variable
								gridColumns = (int) lexer.st.nval;
								
								token = lexer.getNextToken();
								
								// If the token is a ")"
								if(token == Token.RIGHT_PAREN)
								{
									// If we are adding elements to the window frame, then add the gridlayout to the frame variable
	                                if(panelNestingLevel == Token.WINDOW)
	                                {
	                                	frame.setLayout(new GridLayout(gridRows, gridColumns)); 
	                                }
	                                // otherwise, it will be added to the panel
	                                else
	                                {
	                                    panel.setLayout(new GridLayout(gridRows, gridColumns)); 
	                                }
	                                								
									token = lexer.getNextToken();
									
									return true;
								}
								// if the token is not ")", but another "," instead, then it will indicate that we are setting the horizontal and vertical gaps for the gridlayout as well. 
								else if(token == Token.COMMA)
								{
									token = lexer.getNextToken();
									
									// If the token is a number
									if (token == Token.NUMBER)
									{
										// store the integer value of the current token into the gridHorizontalGap variable
										gridHorizontalGap = (int) lexer.st.nval;
										
										token = lexer.getNextToken();
										
										if (token == Token.COMMA)
										{						
											token = lexer.getNextToken();
											
											// If the token is a number
											if (token == Token.NUMBER)
											{
												// store the integer value of the current token into the gridVerticleGap variable
												gridVerticleGap = (int) lexer.st.nval;
												
												token = lexer.getNextToken();
												
												// If the token is a ")"
												if(token == Token.RIGHT_PAREN)
												{
					                                if(panelNestingLevel == Token.WINDOW)
					                                {
														frame.setLayout(new GridLayout(gridRows, gridColumns, gridHorizontalGap, gridVerticleGap));
					                                }
					                                else
					                                {
					                                    panel.setLayout(new GridLayout(gridRows, gridColumns, gridHorizontalGap, gridVerticleGap));
					                                }
					                                												
													token = lexer.getNextToken();
													
													return true;
												}
											}
										}
									}
								}
							}
						}
					}
				}
			// return false if this production does not parse
			default:
				return false;		
		}
	}
	
	
	/** 
	 * Method to parse the <widgets> production.
	 * 
	 * @return a boolean value: true, if the statement parses and false otherwise. 
	 * @throws IOException 
	 * @throws SyntaxException 
	 */
	public boolean widgets() throws SyntaxException, IOException
	{
		// If the <widget> production is true
		if (widget() == true)
		{
			// Recursively call the <widgets> production, in order to follow the grammar requirements. 
			//If the <widgets> production is true
			if (widgets() == true)
			{
				// if both the <widget> and <widgets> productions are true, then one possible choice for the <widgets> production is chosen and it will have correctly parsed
				return true;
			}
			
			// if only the <widget> production is true, then the other possible choice for the <widgets> production is chosen, and it will have correctly parsed.
			return true;
		}
		//otherwise, return false indicating that the production was not correctly parsed. 
		return false;
	}
	
	
	/** 
	 * Method to parse the <widget> production.
	 * 
	 * @return a boolean value: true, if the statement parses and false otherwise. 
	 * @throws IOException 
	 * @throws SyntaxException 
	 */
	public boolean widget() throws SyntaxException, IOException
	{
		String button;      // will hold the name of the button that is created
		String label;       // will hold the name of the label that is created
		int textfieldWidth; // will hold the size of the textfield length
		
		switch(token)
		{
			//If the token corresponds to the "Button" option in the <widget> production
			case BUTTON:
				
				token = lexer.getNextToken();
				
				if(token == Token.STRING)
				{
					//The string contained in the token will be the name of the JButton
					button = lexer.st.sval;
					
					// get the next token from the lexer scanner class
					token = lexer.getNextToken();
					
	                if(token == Token.SEMICOLON)
	                {
	                	// If we are adding elements to the window frame, then add the JButton to the frame variable
						if(panelNestingLevel == Token.WINDOW)
						{	
							frame.add(new JButton(button));
						}
						// otherwise, it will be added to the panel
						else
						{
							panel.add(new JButton(button));
						}
						                	
	                	token = lexer.getNextToken();
	                    return true;
	                }
	                //else, if the token is not a ";"
	                //then throw a syntax exception indicating that the file does not syntactically follow the grammar rules. 
	                else
	                {
	                	throw new SyntaxException("Syntax error in file. File does not syntactically follow the grammar rules" );
	                }
				}
				
			//If the token corresponds to the "Group" option in the <widget> production
			case GROUP: 	
				if (token == Token.GROUP)
				{					
					// get the next token from the lexer scanner class
					token = lexer.getNextToken();
					
					// if the <radio_buttons> production is true 
					if(radioButtons() == true)
					{
						if(token == Token.END)
						{
							// get the next token from the lexer scanner class
		                    token = lexer.getNextToken();
		                    
		                    if(token == Token.SEMICOLON)
		                    {
		                        token = lexer.getNextToken();
		                        
		                        return true;
		                    }
			                //else, if the token is not a ";"
			                //then throw a syntax exception indicating that the file does not syntactically follow the grammar rules. 
			                else
			                {
			                	throw new SyntaxException("Syntax error in file. File does not syntactically follow the grammar rules" );
			                }
		                }	
					}
				}
			//If the token corresponds to the "Label" option in the <widget> production	
			case LABEL:
				if (token == Token.LABEL)
				{
					// get the next token from the lexer scanner class
					token = lexer.getNextToken();
		            
		            if(token == Token.STRING)
		            {
		    			// The string contained in the token will be the text that is to be placed in the label
		            	label = lexer.st.sval;
		            	
						// get the next token from the lexer scanner class
		            	token = lexer.getNextToken();
		            	
		                if(token == Token.SEMICOLON)
		                {
		                	// If we are adding elements to the window frame, then add the JLabel to the frame variable
							if(panelNestingLevel == Token.WINDOW)
							{	
								frame.add(new JLabel(label));
							}
							// otherwise, it will be added to the panel
							else
							{
								panel.add(new JLabel(label));
							}
		                	
							// get the next token from the lexer scanner class
		                	token = lexer.getNextToken();
		                	
		                	return true;
		                }
		                //else, if the token is not a ";"
		                //then throw a syntax exception indicating that the file does not syntactically follow the grammar rules. 
		                else
		                {
		                	throw new SyntaxException("Syntax error in file. File does not syntactically follow the grammar rules" );
		                }
		            }
				}
			//If the token corresponds to the "Panel" option in the <widget> production	
		    case PANEL:
		    	if (token == Token.PANEL)
				{
					if(panelNestingLevel == Token.WINDOW)
					{	
						// Add a new JPanel to the window frame
						frame.add(panel = new JPanel());
					}
					else
					{
						panel.add(panel = new JPanel());
					}
					
					// Set the panelNestingLevel as Token.Panel, which will help when adding elements to a panel that are nested inside another panel. 
					panelNestingLevel = Token.PANEL;
					
					// get the next token from the lexer scanner class
					token = lexer.getNextToken();
					
					// If the <layout> production is true
					if(guiLayout() == true)
					{
						// If the <widgets> production is true
						if(widgets() == true)
						{
							if(token == Token.END)
							{								
								token = lexer.getNextToken();
								
								if(token == Token.SEMICOLON)
								{
									//once a panel has ended, then the token level needs to be moved up one level, in order to properly keep track of the nested panels. 
									panelNestingLevel = Token.WINDOW;
									
		                            token = lexer.getNextToken();
		                            
		                            // If all the above conditions are met, then the panel option of the <widget> production will have correctly parsed and return true. 
		                            return true;
								}
				                //else, if the token is not a ";"
				                //then throw a syntax exception indicating that the file does not syntactically follow the grammar rules. 
				                else
				                {
				                	throw new SyntaxException("Syntax error in file. File does not syntactically follow the grammar rules" );
				                }
							}
						}
					}
				}
		    //If the token corresponds to the "Textfield" option in the <widget> production	
		    case TEXTFIELD:
		    	if (token == Token.TEXTFIELD)
				{
					token = lexer.getNextToken();
					
					if(token == Token.NUMBER)
					{	
						//The number contained in the token will be the width of the text field
						textfieldWidth= (int) lexer.st.nval;
						
						token = lexer.getNextToken();
						
						
						if(token == Token.SEMICOLON)
						{
		                	// If we are adding elements to the window frame, then add the JTextField to the frame variable
							if(panelNestingLevel == Token.WINDOW)
							{	
								frame.add(new JTextField(textfieldWidth));
							}
							// otherwise, it will be added to the panel
							else
							{
								panel.add(new JTextField(textfieldWidth));
							}
							
							token = lexer.getNextToken();
												
							return true;		
						}
		                //else, if the token is not a ";"
		                //then throw a syntax exception indicating that the file does not syntactically follow the grammar rules. 
		                else
		                {
		                	throw new SyntaxException("Syntax error in file. File does not syntactically follow the grammar rules" );
		                }
					}		
				}
		    // return false if this production does not parse
			default:
				return false;
		}
	}
	
	
	/** 
	 * Method to parse the <radio_buttons> production.
	 * 
	 * @return a boolean value: true, if the statement parses and false otherwise.
	 * @throws IOException 
	 * @throws SyntaxException  
	 */
	public boolean radioButtons() throws SyntaxException, IOException
	{
		// If the <radio_button> production is true
		if(radioButton() == true)
		{
			// Recursively call the <radio_buttons> production, in order to follow the grammar requirements. 
			// If the <radio_buttons> production is true
            if(radioButtons() == true)
            {
            	// If both the <radio_button> and <radio_buttons> productions are true, then one possible choice for the <radio_buttons> production is chosen and it will have correctly parsed
                return true;
            }
            // if only the <radio_button> production is true, then the other possible choice for the <radio_buttons> production is chosen, and it will have correctly parsed.
            return true;
        }
		//otherwise, return false indicating that the production was not correctly parsed. 
		return false;
	}
	
	/** 
	 * Method to parse the <radio_button> production.
	 * 
	 * @return a boolean value: true, if the statement parses and false otherwise. 
	 * @throws IOException 
	 * @throws SyntaxException 
	 */
	public boolean radioButton() throws SyntaxException, IOException
	{
		String radioBtnLabel;
		
		if(token == Token.RADIO)
		{
			token = lexer.getNextToken();
			
			if(token == Token.STRING)
			{
				// The string contained in the token will be used as the label of the radioButton
				radioBtnLabel = lexer.st.sval;
				
				token = lexer.getNextToken();
				
				if(token == Token.SEMICOLON)
				{
					// Create the new radio button that will be added
					radioButton = new JRadioButton(radioBtnLabel);
					
    				// If we are adding elements to the window frame, then add the radioButton to the frame variable
                    if(panelNestingLevel == Token.WINDOW)
                    {
                        frame.add(radioButton);
                    }
                    // otherwise, it will be added to the panel
                    else
                    {
                        panel.add(radioButton);
                    }                             
                    
                    token = lexer.getNextToken();
                    
                    return true;
				}
                //else, if the token is not a ";"
                //then throw a syntax exception indicating that the file does not syntactically follow the grammar rules. 
                else
                {
                	throw new SyntaxException("Syntax error in file. File does not syntactically follow the grammar rules" );
                }
			}
		}
		return false;
	}	
}

