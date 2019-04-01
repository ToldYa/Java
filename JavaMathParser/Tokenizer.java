/*
 * authors: 
 * 	Emil Vesa, emil.vesa@gmail.com
 * 	Aboud Malki, aboud-malki@hotmail.com
 * 
 */

package prop.assignment0;

import java.io.IOException;

public class Tokenizer implements ITokenizer {
	
	Scanner scanner;
	
	//------------------------constructors-------------------------
	
	public Tokenizer() {
		scanner = new Scanner();
	}
	
	//------------------------methods------------------------------
	
	public void open(String fileName) throws IOException, TokenizerException{
		scanner.open(fileName);
	}
	
	public Lexeme current() {
		char current = scanner.current();
		while(assignToken(current) == Token.NULL) {
			try {
				scanner.moveNext();
				current = scanner.current();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return new Lexeme(current, assignToken(current));
	}
	
	//NULL, IDENT, INT_LIT, ADD_OP, SUB_OP, MULT_OP, DIV_OP, ASSIGN_OP, 
	//LEFT_PAREN, RIGHT_PAREN, SEMICOLON, LEFT_CURLY, RIGHT_CURLY, EOF
	private Token assignToken(char character) {
		Token token = Token.NULL;
		
		switch(character) { //implementation IDENT = a...z? & INT_LIT = 0...9?
		case'+':
			token = Token.ADD_OP;
			break;
		case'-':
			token = Token.SUB_OP;
			break;
		case'*':
			token = Token.MULT_OP;
			break;
		case'/':
			token = Token.DIV_OP;
			break;
		case'=':
			token = Token.ASSIGN_OP;
			break;
		case'(':
			token = Token.LEFT_PAREN;
			break;
		case')':
			token = Token.RIGHT_PAREN;
			break;
		case';':
			token = Token.SEMICOLON;
			break;
		case'{':
			token = Token.LEFT_CURLY;
			break;
		case'}':
			token = Token.RIGHT_CURLY;
			break;
		case (char) -1:
			token = Token.EOF;
			break;
		default:
			if((int)character > 96 && (int)character < 123) {		//97 - 122 CHAR a...z
				token = Token.IDENT;
			} else if((int)character > 47 && (int)character < 58) {	//48 - 57 INT 0...9
				token = Token.INT_LIT;
			}
			break;
		}
		return token;
	}
	
	public void moveNext() throws IOException, TokenizerException{
		scanner.moveNext();
	}
	
	public void close() throws IOException{
		scanner.close();
	}
	
	
}
