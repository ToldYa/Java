/*
 * authors: 
 * 	Emil Vesa, emil.vesa@gmail.com
 * 	Aboud Malki, aboud-malki@hotmail.com
 * 
 */

package prop.assignment0;

import java.io.IOException;

public class FactorNode implements INode{
	
	//factor = int | ’(’ , expr , ’)’ ;
	//a = 1 * 2 + (3 - 4) / 5;
	Lexeme intLex;
	// OR
	Lexeme leftParantheses;
	ExpressionNode expr;
	Lexeme rightParantheses;	
	
	//-----------------------constructors------------------------
	
	public FactorNode(Tokenizer t) throws IOException, TokenizerException {
		if(t.current().token() == Token.INT_LIT) {
			intLex = t.current();	
		}else if(t.current().token() == Token.LEFT_PAREN){
			this.leftParantheses = t.current();
			t.moveNext();
			this.expr = new ExpressionNode(t);
			this.rightParantheses = t.current();
		}
		t.moveNext();
	}
	
	//-----------------------methods-----------------------------
	
	@Override
	public Object evaluate(Object[] args) throws Exception {
		if(intLex != null) {
			for(int i = 0; i < args.length; i++) {
				if(args[i] == null) {
					args[i] = intLex;
					i = args.length;
				}
			}
		} else {
			
			for(int i = 0; i < args.length; i++) {
				if(args[i] == null) {
					args[i] = leftParantheses;
					i = args.length;
				}
			}
			
			expr.evaluate(args);
			
			for(int i = 0; i < args.length; i++) {
				if(args[i] == null) {
					args[i] = rightParantheses;
					i = args.length;
				}
			}
		}
		return null;
	}

	@Override
	public void buildString(StringBuilder builder, int tabs) {
		String tab = "	";
		String output = "";
		for(int i = 0; i < tabs; i++) {
			output += tab;
		}
		
		builder.append(output + "FactorNode\n");
		
		tabs++;
		output += tab;		
		
		if(intLex != null) {
			builder.append(output + intLex.toString() + ".0\n");
		} else {
			builder.append(output  + leftParantheses.toString() + "\n");
			expr.buildString(builder, tabs);
			builder.append(output + rightParantheses.toString() + "\n");
		}
	}

}
