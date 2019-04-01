/*
 * authors: 
 * 	Emil Vesa, emil.vesa@gmail.com
 * 	Aboud Malki, aboud-malki@hotmail.com
 * 
 */

package prop.assignment0;

import java.io.IOException;

public class TermNode implements INode{
	
	//term = factor , [ ( ’*’ | ’/’) , term] ;
	//a = 1 * 2 + (3 - 4) / 5;
	FactorNode factor;
	Lexeme lexeme;
	TermNode term;
	
	//-----------------------constructors------------------------
	
	public TermNode(Tokenizer t) throws IOException, TokenizerException {
		this.factor = new FactorNode(t);
		if(t.current().token() == Token.MULT_OP || t.current().token() == Token.DIV_OP){
			this.lexeme = t.current();
			t.moveNext();
			this.term = new TermNode(t);
		}
		
	}
	
	//-----------------------methods-----------------------------
	
	@Override
	public Object evaluate(Object[] args) throws Exception {
		factor.evaluate(args);
		
		if(lexeme != null) {
			for(int i = 0; i < args.length; i++) {
				if(args[i] == null) {
					args[i] = lexeme;
					i = args.length;
				}
			}
			term.evaluate(args);
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
		builder.append(output + "TermNode\n");
		
		tabs++;
		factor.buildString(builder, tabs);
		output += tab;
		
		if(lexeme != null) {
			builder.append(output + lexeme.toString() + "\n");
			term.buildString(builder, tabs);
		}	
	}

}
