/*
 * authors: 
 * 	Emil Vesa, emil.vesa@gmail.com
 * 	Aboud Malki, aboud-malki@hotmail.com
 * 
 */

package prop.assignment0;

public class Evaluator {
	String str;
	
	//-----------------------constructors------------------------
	
	public Evaluator() {
		
	}
	
	//-----------------------methods-----------------------------
	
	
	public String calculate(Object[] args) {
		String formel = convertToString(args);
		str = formel;
		
		return "" + parse();
	}
	
	private String convertToString(Object[] args) {
		String temp = "";
		Lexeme lex;
		for(int i = 2; i < args.length; i++) {
			lex = (Lexeme)args[i];
			if(args[i] != null && lex.token() != Token.SEMICOLON) {
				temp += lex.value().toString();
			}else {
				i = args.length;
			}
		}
		return temp;
	}
	
	int pos = -1, ch;

    void nextChar() {
        ch = (++pos < str.length()) ? str.charAt(pos) : -1;
    }

    boolean eat(int charToEat) {
        while (ch == ' ') nextChar();
        if (ch == charToEat) {
            nextChar();
            return true;
        }
        return false;
    }

    double parse() {
        nextChar();
        double x = parseExpression();
        if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
        return x;
    }
    
    double parseExpression() {
        double x = parseTerm();
        for (;;) {
            if      (eat('+')) x += parseTerm(); // addition
            else if (eat('-')) x -= parseTerm(); // subtraction
            else return x;
        }
    }

    double parseTerm() {
        double x = parseFactor();
        for (;;) {
            if      (eat('*')) x *= parseFactor(); // multiplication
            else if (eat('/')) x /= parseFactor(); // division
            else return x;
        }
    }

    double parseFactor() {
        if (eat('+')) return parseFactor(); // unary plus
        if (eat('-')) return -parseFactor(); // unary minus

        double x;
        int startPos = this.pos;
        if (eat('(')) { // parentheses
            x = parseExpression();
            eat(')');
        } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
            while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
            x = Double.parseDouble(str.substring(startPos, this.pos));
        } else if (ch >= 'a' && ch <= 'z') { // functions
            while (ch >= 'a' && ch <= 'z') nextChar();
            x = parseFactor();
        } else {
            throw new RuntimeException("Unexpected: " + (char)ch);
        }

        return x;
    }
}

