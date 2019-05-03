/**
 * Author: Emil Vesa
 * Mail: emil.vesa@gmail.com
 */

package codetest;

import java.util.Iterator;

public class Band {
	
	private String name;
	private MyLinkedList<Musician> members;
	
	public Band(String name) {
		members = new MyLinkedList<>();
		this.name = name;
	}
	
	public boolean addMember(Musician member) {
		member.setIsInBand(true);
		return members.add(member);
	}
	
	public boolean removeMember(Musician member) {
		return members.remove(member);
	}
	
	public String getName() {
		return name;
	}
	
	public MyLinkedList<Musician> getMemberList() {
		return members;
	}
	
	public boolean equals(Band other) {
		return name.equals(other.name);
	}
	
	public String toString() {
		StringBuffer output = new StringBuffer();
		Iterator<Musician> ite = members.iterator();
		
		while(ite.hasNext()) {
			output.append("\n");
			output.append(ite.next().toString());
		}
		
		return name + output.toString();
	}
	
}
