/**
 * Author: Emil Vesa
 * Mail: emil.vesa@gmail.com
 */

package codetest.instrument;

public class Banjo extends StringedInstrument {

	public Banjo(int numOfStrings, double stringSize, String manufacturer) {
		super(numOfStrings, stringSize, manufacturer);
	}
	
	@Override
	public String toString() {
		StringBuilder output = new StringBuilder();
		output.append("Banjo: ");
		output.append("NumOfStrings: " + getNumOfStrings() + ", ");
		output.append("Manufacturer: " + getManufacturer() + ", ");
		output.append("String size: " + getStringSize());
		
		return output.toString();
	}
	
	@Override
	public boolean equals(Instrument other) {
		boolean equals = false;
		if(other instanceof Banjo) {
			Banjo temp = (Banjo) other;
			equals = getNumOfStrings() == temp.getNumOfStrings() && getStringSize() == temp.getStringSize()
					&& getManufacturer().equals(temp.getManufacturer());
		}
		return equals;
	}
}
