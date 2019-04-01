/*
 * authors: 
 * 	Emil Vesa, emil.vesa@gmail.com
 * 	Aboud Malki, aboud-malki@hotmail.com
 * 
 */

package prop.assignment0;

import java.io.IOException;

public class ExpressionNode implements INode{
	
	//expr = term , [ ( ’+’ | ’-’ ) , expr ] ;
	//a = 1 * 2 + (3 - 4) / 5;
	TermNode term;
	Lexeme lexeme;
	ExpressionNode expr;	
	
	//-----------------------constructors------------------------
	
	public ExpressionNode(Tokenizer t) throws IOException, TokenizerException {
		this.term = new TermNode(t);
		if(t.current().token() == Token.ADD_OP || t.current().token() == Token.SUB_OP){
			this.lexeme = t.current();
			t.moveNext();
			this.expr = new ExpressionNode(t);
		}
	}
	
	//-----------------------methods-----------------------------
	
	
	@Override
	public Object evaluate(Object[] args) throws Exception {
		term.evaluate(args);
		
		if(lexeme != null) {
			for(int i = 0; i < args.length; i++) {
				if(args[i] == null) {
					args[i] = lexeme;
					i = args.length;
				}
			}
			expr.evaluate(args);
		}
		
		return null;
	}

	@Override
	public void buildString(StringBuilder builder, int tabs) {
		String output = "";
		String tab = "	";
		for(int i = 0; i < tabs; i++) {
			output += tab;
		}
		builder.append(output + "ExpressionNode\n");
		tabs++;
		
		term.buildString(builder, tabs);
		

		output += tab;
		
		if(lexeme != null) {
			builder.append(output + lexeme.toString() + "\n");
			expr.buildString(builder, tabs);
		}	
	}

}
