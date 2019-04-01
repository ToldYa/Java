/*
 * authors: 
 * 	Emil Vesa, emil.vesa@gmail.com
 * 	Aboud Malki, aboud-malki@hotmail.com
 * 
 */

package prop.assignment0;

import java.io.IOException;

public class Parser implements IParser {
	
	Tokenizer tokenizer;
	
	//------------------------constructors-------------------------
	
	public Parser() {
		tokenizer = new Tokenizer();
	}
	
	//------------------------methods------------------------------
	
	@Override
	public void open(String fileName) throws IOException, TokenizerException {
		tokenizer.open(fileName);
		tokenizer.moveNext();
	}

	@Override
	public AssignmentNode parse() throws IOException, TokenizerException, ParserException {
		return new AssignmentNode(tokenizer);
	}
	
	//AssignmentNode, ExpressionNode, TermNode and FactorNode
	//NULL, IDENT, INT_LIT, ADD_OP, SUB_OP, MULT_OP, DIV_OP, ASSIGN_OP, 
	//LEFT_PAREN, RIGHT_PAREN, SEMICOLON, LEFT_CURLY, RIGHT_CURLY, EOF

	@Override
	public void close() throws IOException {
		tokenizer.close();
	}
	
	
}
