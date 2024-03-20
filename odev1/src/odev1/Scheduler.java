package odev1;

import java.util.LinkedList;

public class Schedular{
	
	private int numberOfResources;
	
	private LinkedList<job> waitlist = new LinkedList<>();
	private LinkedList<job> highPQueue = new LinkedList<>();
	private LinkedList<job> midPQueue = new LinkedList<>();
	private LinkedList<job> lowPQueue = new LinkedList<>();
	
	
	public void add(job j) {
		
		this.waitlist.add(j);
		
		if(j.priority.equals("H")) {
			this.highPQueue.add(j);
		}
		
		if(j.priority.equals("M")) {
			this.midPQueue.add(j);
		}
		
		if(j.priority.equals("L")) {
			this.lowPQueue.add(j);
		}
		
		
		sort(waitlist);
		sort(highPQueue);
		sort(midPQueue);
		sort(lowPQueue);
		
	}
	
	public LinkedList<job> sort(LinkedList<job> link) {
		
		for(int i = 1; i < link.size(); i++) {
			if(link.get(i).arrivalTime < link.get(i-1).arrivalTime) {
				job holder1;
				holder1 = link.get(i);
				job holder2;
				holder2 = link.get(i-1);
				link.add(i,holder2);
				link.add(i-1,holder1);
			}
		}
		
		return link;
	}

	public void yazdir() {
		for(int i = 0; i < highPQueue.size(); i++) {
			System.out.println(highPQueue.get(i).priority);
			System.out.println(highPQueue.get(i).id);
		}
		
		System.out.println("^^^^^^");
		
		for(int i = 0; i < midPQueue.size(); i++) {
			System.out.println(midPQueue.get(i).priority);
			System.out.println(midPQueue.get(i).id);
		}
		
		System.out.println("^^^^^^");
		
		for(int i = 0; i < lowPQueue.size(); i++) {
			System.out.println(lowPQueue.get(i).priority);
			System.out.println(lowPQueue.get(i).id);
		}
		
		System.out.println("^^^^^^");
		
		for(int i = 0; i < waitlist.size(); i++) {
			System.out.println(waitlist.get(i).id);
	}
	
	
	}

}
