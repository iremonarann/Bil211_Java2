package hw1;


/*
Irem Onaran
201101045
*/



public class Scheduler{
	
	private int numberOfResources;	//toplam kaynak sayisi
	
	//listeler
	private LinkedList waitlist = new LinkedList();
	private LinkedList highPQueue = new LinkedList();
	private LinkedList midPQueue = new LinkedList();
	private LinkedList lowPQueue = new LinkedList();
	
	private kaynak[] arr;
	private int time;
	
	
	/*waitliste olusturulan job'lari ekler
	  siralanmasi icin sort metodunu cagirir*/
	public void add(job j) {
		
		if(this.waitlist.size() == 0) {
			this.waitlist.addAtHead(j);
		}
		else {
			this.waitlist.add(j);
		}

		sort(waitlist);
		
	}
	
	
	//highPQueue,midPQueue,LowPQueue listelerine sirali atamalari yapar
	public void setHML() {
		for(int i = 0; i < waitlist.size(); i++) {
			if(waitlist.get(i).getJob().priority.equals("H")) {
				this.highPQueue.addAtHead(waitlist.get(i).getJob());
			}
			if(waitlist.get(i).getJob().priority.equals("M")) {
				this.midPQueue.addAtHead(waitlist.get(i).getJob());
			}
			if(waitlist.get(i).getJob().priority.equals("L")) {
				this.lowPQueue.addAtHead(waitlist.get(i).getJob());
			}
		}
	}
	
	
	//waitlistteki job'lari duzenler
	public void sort(LinkedList link) {
		
		if(link.size() > 1) {
			
			//watlistteki joblari arrival time'a gore siralar
			for(int i = 0; i < link.size() - 2; i++) {
				for(int y = i+1; y < link.size()-1; y++) {
					if(link.get(y).getJob().arrivalTime < link.get(i).getJob().arrivalTime) {
						LinkedList.Node n;
						n = link.get(i);
						link.delete(i);
						link.add(i,link.get(y).getJob());
						link.delete(y);
						link.add(y,n.getJob());
					}
				}
			}
			
			//waitlistteki arrival time'a gore siralanmis joblari priority'e gore siralar
			for(int y = 0; y < link.size() -1; y++) {
				for(int x = 0; x < link.size() -1; x++) {
					if(link.get(x).getJob().arrivalTime == link.get(x+1).getJob().arrivalTime) {
						if((link.get(x).getJob().priority).equals("M") && (link.get(x+1).getJob().priority).equals("H")) {
							link.switchNext(x);
						}
						else if((link.get(x).getJob().priority).equals("L") && (link.get(x+1).getJob().priority).equals("H")) {
							link.switchNext(x);
						}
						else if((link.get(x).getJob().priority).equals("L") && (link.get(x+1).getJob().priority).equals("M")) {
							link.switchNext(x);
						}
					}
				}
			}
			
		}
		
	}
	
	
	//toplam kaynak sayisini olusturur
	public void setResourcesCount(int resourceCount) {
		
		this.numberOfResources = resourceCount;
		
	}
	
	
	//kaynaktaki verimi hesaplar ve ekrana yazdirir
	public void utilization(int kaynakNo) {
		
		int calismaSuresi = 0;
		int gecenSure = 0;
		double verim = 0;
		calismaSuresi = this.arr[kaynakNo-1].isler.length;
		gecenSure = this.time;
		verim = (double)calismaSuresi/gecenSure;
		System.out.println("R" + kaynakNo + " verim: " + verim);
		
	}
	
	
	//kaynaktaki joblarin sirayla id'lerini, baslangic zamanlarini, gecikmelerini hesaplar
	//bunlari her job icin sirayla bastirir
	public void resourceExplorer(int kaynakNo) {
		
		System.out.print("R" + kaynakNo + ": ");
		int holder = arr[kaynakNo-1].isler[0].id;
		int kontrol = holder;
		int a_t = 0;
		int a = 1;
		while(kontrol == holder) {
			
			System.out.print("(" + holder + ",");
			
			int sure = 1;
			int i = 0;
			for(i = a; i < arr[kaynakNo-1].isler.length; i++) {
				if(arr[kaynakNo-1].isler[i].id == holder) {
					sure = sure+1;
				}
				else {
					holder = arr[kaynakNo-1].isler[i].id;
					break;
				}
			}
			a_t = arr[kaynakNo-1].isler[i-1].arrivalTime;
			kontrol = holder;
			System.out.print(i-1 + ",");
			System.out.print(i-sure-a_t + ")");
			a = i+1;
			if(i-1 != arr[kaynakNo-1].isler.length-1) {
				System.out.print(",");
			}
			if(i-1 == arr[kaynakNo-1].isler.length-1){
				kontrol = kontrol -1;
				break;
			}
		}
		System.out.println();
		
	}
	
	
	/*parametre olarak aldigi job'in id'sini, hangi kaynakta oldugunu, baslangic ve bitis zamanlarini,
	  gecikme suresini hesaplar ve bunlari sirayla ekrana yazdirir*/
	public void jobExplorer(job j) {
		
		System.out.println("islemno    kaynak    baslangic    bitis    gecikme");
		System.out.print(j.id + "          "); //job'in id'sini ekrana yazdirir
		
		int rc = 0;
		for(int x = 0; x < this.numberOfResources; x++) {
			for(int y = 0; y < arr[x].isler.length; y++) {
				if(arr[x].isler[y].id == j.id) {
					rc = x+1;
					break;
				}
			}
		}
		System.out.print("R" + rc + "        "); //job'in hangi kaynakta oldugunu ekrana yazdirir
		
		int bas = 0;
		for(int a = 0; a < arr[rc-1].isler.length; a++) {
			if(arr[rc-1].isler[a].id == j.id) {
				bas = a;
				break;
			}
		}
		System.out.print(bas + "            "); //job'in baslangic zamanini ekrana yazdirir
		
		int son = 0;
		for(int a = 0; a < arr[rc-1].isler.length; a++) {
			if(arr[rc-1].isler[a].id == j.id && a == arr[rc-1].isler.length-1) {
				son = a;
				
			}
			else if(arr[rc-1].isler[a].id == j.id && arr[rc-1].isler[a+1].id != j.id) {
				son = a;
				break;
			}
		}
		System.out.print(son + "        "); //job'in bitis zamanini ekrana yazdirir
		
		int gec = bas - j.arrivalTime ;
		System.out.println(gec); //job'in gecikme suresini ekrana yazdirir
		
	}
	
	
	
	public void run() {
		
		int totalDuration = 0;
		
		//toplam duration'i hesaplar
		for(int i = 0; i < this.waitlist.size(); i++) {
			totalDuration += waitlist.get(i).getJob().duration;
		}
		
		//toplam ne kadar zaman harcanmasi gerektigini hesaplar
		this.time = totalDuration/numberOfResources +1;
		
		
		System.out.print("Zaman	");
		
		//kaynak sayisi kadar R'yi ekrana yazdirir
		for(int a = 1; a < this.numberOfResources+1; a++) {
			System.out.print("R" + a + "	");
		}
		System.out.println();	
		
		
		//kaynak'lari olusturur
		this.arr = new kaynak[this.numberOfResources];
		for(int b = 0; b < this.numberOfResources; b++) {
			arr[b] = new kaynak(b+1);
		}
			
		
		//job'lari siraya uygun sekilde kaynaklara atar
		for(int x = 0; x < this.waitlist.size(); x++) {
			if(x < this.numberOfResources) {
				for(int y = 0; y < waitlist.get(x).getJob().duration; y++) {
					arr[x].isler = uzat(arr[x].isler, waitlist.get(x).getJob());
				}
			}
			else {
				int counter = x;
				while(counter >= this.numberOfResources) {
					counter = counter - this.numberOfResources;
				}

				counter = 0;
				for (int z = 0; z < this.numberOfResources; z++){
					if(arr[counter].isler.length > arr[z].isler.length) {
						counter = z;
					}
				}
				
				for(int y = 0; y < waitlist.get(x).getJob().duration; y++) {
					arr[counter].isler = uzat(arr[counter].isler, waitlist.get(x).getJob());
				}
			}
		}
		
		//job'lari ekranda kaynaklarinin oldugu yere dogru sirayla yazdirir
		for(int c = 0; c < this.time; c++) {
			System.out.print(c + "	");
			for(int d = 0; d < this.numberOfResources; d++) {
				if(arr[d].isler.length > c) {
					System.out.print("J" + arr[d].isler[c].id + "	");
				}
				else {
					break;
				}
			}
			System.out.println();
		}
			
	}
	
	
	//kaynaklara her job atamasi yapildiginda kaynak uzunlugunu artirir
	public job[] uzat(job[] j, job o) {
		
		job isler[] = new job[j.length+1];
		for(int i = 0; i < j.length; i++) {
			isler[i] = j[i];
		}
		isler[j.length] = o;
		return isler;
	}
	
	
	
	//kaynaklari olusturmak ve kaynaklar icindeki islemleri yapmak icin kaynak sinifi
	class kaynak{
		
		private int sira;
		private job isler[];
		
		public kaynak() {
			
		}
		
		public kaynak(int sira) {
			this.sira = sira;
			this.isler = new job[0];
		}
		
		public int getSira() {
			return sira;
		}
		
		public void setSira(int sira) {
			this.sira = sira;
		}
		
		public job[] getIsler() {
			return isler;
		}
		
		public void setIsler(job[] isler) {
			this.isler = isler;
		}
		
		
	}
	
	
	
	//olusturdugum LinkedList sinifi
	class LinkedList{
		
		private static Node head;
		private static int numberOfNodes;
		
		
		public LinkedList(){
		}
		
		
		public LinkedList(job j){
			head = new Node(j);
		}
		
		
		//LinkedList basina element ekler
		public void addAtHead(job j){
			Node temp = head;
			head = new Node(j);
			head.next = temp;
			numberOfNodes++;
		}
		
		
		//LinkedList kuyruguna element ekler
		public void add(job j){
			Node temp = head;
			while(temp.next != null){
				temp = temp.next;
			}
			
			temp.next = new Node(j);
			numberOfNodes++;
		}
		
		
		//parametre olarak aldigi indexe element ekler 
		public void add(int index, job j){
			Node temp = head;
			if(temp == null) {
				head = new Node(j);
				head.next = temp;
				numberOfNodes++;
			}
			else {
				Node holder;
				for(int i=0; i < index-1 && temp.next != null; i++){
					temp = temp.next;
				}
				holder = temp.next;
				temp.next = new Node(j);
				temp.next.next = holder;
				numberOfNodes++;
			}
		}
		
		
		//parametre olarak aldigi indexteki elementle yanindakinin yerini degistirir
		public void switchNext(int index){
			if(index != 0){
				Node bOne = get(index - 1);
				Node one = get(index);
				Node two = one.next;
				
				one.next = two.next;
				bOne.next = two;
				two.next = one;
			}
			else{
				Node one = get(index);
				Node two = one.next;
				
				head = two;
				one.next = two.next;
				two.next = one;
			}
		}	
		
		
		//parametre olarak aldigi indexteki elementi siler
		public void delete(int index){
			Node temp = head;
			for(int i=0; i< index - 1 && temp.next != null; i++)
			{
				temp = temp.next;
			}
			temp.next = temp.next.next;
			numberOfNodes--;
		}
		
		
		//parametre olarak aldigi indexteki node'u return eder
		public Node get(int index){
			Node temp=head;
			for(int i=0; i<index; i++){
				temp = temp.next;
			}
			return temp;
		}
		
		
		//LinkedList'in uzunlugunu return eder
		public int size(){
			return numberOfNodes;
		}
		
		
		//Node sinifi
		class Node {
            private Node next;
            private job j;
            public Node(job j) {
                this.j = j;
            }
            public job getJob() {
                return j;
            }
        }
	}
	
	
	
}