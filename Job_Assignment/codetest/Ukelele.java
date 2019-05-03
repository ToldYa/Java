/**
 * Author: Emil Vesa
 * Mail: emil.vesa@gmail.com
 */

package codetest;

public class Ukelele extends StringedInstrument {

	public Ukelele(int numOfStrings, double stringSize, String manufacturer) {
		super(numOfStrings, stringSize, manufacturer);
	}
	
	@Override
	public String toString() {
		StringBuffer output = new StringBuffer();
		output.append("Ukelele: ");
		output.append("NumOfStrings: " + getNumOfStrings() + ", ");
		output.append("Manufacturer: " + getManufacturer() + ", ");
		output.append("String size: " + getStringSize());
		
		return output.toString();
	}
	
	@Override
	public boolean equals(Instrument other) {
		boolean equals = false;
		if(other instanceof Ukelele) {
			Ukelele temp = (Ukelele) other;
			equals = getNumOfStrings() == temp.getNumOfStrings() && getStringSize() == temp.getStringSize()
					&& getManufacturer().equals(temp.getManufacturer());
		}
		return equals;
	}
	
}
