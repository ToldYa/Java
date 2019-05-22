/**
 * Author: Emil Vesa
 * Mail: emil.vesa@gmail.com
 */

package codetest.instrument;

public abstract class Instrument {
	
	private String manufacturer;
	
	protected Instrument(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	
	public String getManufacturer() {
		return manufacturer;
	}
	
	public abstract boolean equals(Instrument other);
	
	public abstract String toString();
}
