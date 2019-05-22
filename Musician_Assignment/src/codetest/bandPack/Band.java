/**
 * Author: Emil Vesa
 * Mail: emil.vesa@gmail.com
 */

package codetest.bandPack;

import java.util.ArrayList;
import java.util.Iterator;

public class Band {
	
	private String name;
	private ArrayList<Musician> members;
	
	public Band(String name) {
		members = new ArrayList<>();
		this.name = name;
	}
	
	public boolean addMember(Musician member) {
		return members.add(member);
	}
	
	public Musician removeMember(int index) {
		return members.remove(index);
	}
	
	public String getName() {
		return name;
	}
	
	public boolean equals(Band other) {
		return name.equals(other.name);
	}
	
	public boolean hasMember(Musician musician) {
		return members.contains(musician);
	}
	
	public int size() {
		return members.size();
	}
	
	public String toString() {
		StringBuilder output = new StringBuilder();
		Iterator<Musician> ite = members.iterator();
		
		while(ite.hasNext()) {
			output.append("\n");
			output.append(ite.next().toString());
		}
		
		return name + output.toString();
	}
	
}
