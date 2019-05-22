/**
 * Author: Emil Vesa
 * Mail: emil.vesa@gmail.com
 */

package codetest.instrument;

public abstract class PercussionInstrument extends Instrument{
	
	private boolean hasHiHat;
	
	protected PercussionInstrument(boolean hasHiHat, String manufacturer) {
		super(manufacturer);
		this.hasHiHat = hasHiHat;
	}
	
	public boolean getHasHiHat() {
		return hasHiHat;
	}

}
