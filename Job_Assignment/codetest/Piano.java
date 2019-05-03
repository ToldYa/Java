/**
 * Author: Emil Vesa
 * Mail: emil.vesa@gmail.com
 */

package codetest;

public class Piano extends KeyedInstrument {
	
	private boolean isAcoustic;
	
	public Piano(int numberOfKeys, String manufacturer, boolean isAcoustic) {
		super(numberOfKeys, manufacturer);
		this.isAcoustic = isAcoustic;
	}
	
	public boolean getIsAcoustic() {
		return isAcoustic;
	}

	@Override
	public String toString() {
		StringBuffer output = new StringBuffer();
		output.append("Piano: ");
		output.append("NumOfKeys: " + getNumberOfKeys() + ", ");
		output.append("Manufacturer: " + getManufacturer() + ", ");
		output.append("Is acoustic: " + isAcoustic);
		
		return output.toString();
	}
	
	@Override
	public boolean equals(Instrument other) {
		boolean equals = false;
		if(other instanceof Piano) {
			Piano temp = (Piano) other;
			equals = getNumberOfKeys() == temp.getNumberOfKeys() &&
					isAcoustic == temp.isAcoustic && getManufacturer().equals(temp.getManufacturer());
		}
		return equals;
	}

}
