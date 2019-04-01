/*
 * authors: 
 * 	Emil Vesa, emil.vesa@gmail.com
 * 	Aboud Malki, aboud-malki@hotmail.com
 * 
 */

package prop.assignment0;

import java.io.IOException;

public class AssignmentNode implements INode{
	
	//assign = id , ’=’ , expr , ’;’ ;
	//a = 1 * 2 + (3 - 4) / 5;
	Lexeme id = null;
	Lexeme assignOp = null;
	ExpressionNode expr = null;
	Lexeme semicolon = null;
	
	//-----------------------constructors------------------------
	
	public AssignmentNode(Tokenizer t) throws IOException, TokenizerException {
		this.id = t.current();
		t.moveNext();
		this.assignOp = t.current();
		t.moveNext();
		this.expr = new ExpressionNode(t);
		this.semicolon = t.current();
		t.moveNext();
	}
	
	//-----------------------methods-----------------------------
	
	@Override
	public Object evaluate(Object[] args) throws Exception {
		args = new Object[100];
		args[0] = id;
		args[1] = assignOp;
		expr.evaluate(args);
		for(int i = 0; i < args.length; i++) {
			if(args[i] == null) {
				args[i] = semicolon;
				i = args.length;
			}
		}
		
		Evaluator eval = new Evaluator();
		String output = eval.calculate(args);
		
		return output;
	}

	@Override
	public void buildString(StringBuilder builder, int tabs) {
		builder.append("AssignmentNode\n");
		tabs++;
		String tab = "	";
		String output = "";
		
		for(int i = 0; i < tabs; i++) {
			output += tab;
		}
		
		builder.append(output + id.toString() + "\n");
		builder.append(output + assignOp.toString() + "\n");
		expr.buildString(builder, tabs);
		builder.append(output + semicolon.toString() + "\n");
	}

}
