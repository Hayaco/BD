package Buffer;

import java.util.LinkedList;

public class FrameList extends LinkedList{
	private LinkedList<Frame> buffer;
	private static final int TAILLE=4;
	private int pointeur =0;
	private int nbr_Access_Disk;
	
	public FrameList() {
		this.buffer = new LinkedList<Frame>();
		this.nbr_Access_Disk=0;
	}

	public int getNbr_Access_Disk() {
		return nbr_Access_Disk;
	}

	public void setNbr_Access_Disk(int nbr_Access_Disk) {
		this.nbr_Access_Disk = nbr_Access_Disk;
	}

	public LinkedList<Frame> getBuffer() {
		return buffer;
	}
	
	public void setBuffer(LinkedList<Frame> buffer) {
		this.buffer = buffer;  
	}

	@Override
	public String toString() {
		String buf="";
		for(int i=0; i<TAILLE ; i++) {
			buf+=("(P"+(i+1)+": "+ buffer.get(i)+") ");
		}
		return "buffer=[" + buf + "], nbr_Access_Disk=" + nbr_Access_Disk + "]";
	}
	
	public Frame isInBuffer (String page) { //return the position in buffer
		for (int i=0 ; i<TAILLE && i<buffer.size(); i++) {
			if (buffer.get(i).getPagename().equals(page))
				return buffer.get(i);
		}
		return null;
	}
	
	public boolean addPageLRU(String page) { 
		//System.out.println("bien avant");
		// return true if the page is add successfully
		Frame j=isInBuffer(page);
		if (j!= null) {
			//System.out.println("avant");
			reorganize(j);
			return false;
		}
		Frame f=new Frame(page);
		nbr_Access_Disk++;
		System.out.println("one access disk");
		if (buffer.size()<TAILLE ) {
			buffer.addFirst(f);
			//System.out.println("ici"); 
			return true;
		}
		//System.out.println("la"); 
		buffer.removeLast();
		buffer.addFirst(f);
		return true;
		
	}
	public void reorganize(Frame f) {
		buffer.removeFirstOccurrence(f);
		buffer.addFirst(f);
	}
	
	private void run (String [] tab, String type) {
		System.out.println(" ");
		System.out.println(type+ " method is running");
		for (int i= 0; i<tab.length ;i++) {
			if (type.equals("LRU"))
				addPageLRU(tab[i]);
			else if (type.equals("FIFO"))
				addPageFIFO(tab[i]);
			else if (type.equals("CLOCK"))
				//addPageClock(tab[i]);
				;
			else 
				throw new IllegalArgumentException("Type not recognize ");
			System.out.println("T"+(i+1)+buffer);
			
		}
	} 
	
	public boolean addPageFIFO(String page) { 
		// return true if the page is add successfully
		Frame j=isInBuffer(page);
		if (j!= null) {
			return false;
		}
		Frame f=new Frame(page);
		nbr_Access_Disk++;
		System.out.println("one access disk");
		if (buffer.size()<TAILLE ) {
			buffer.addFirst(f);
			return true;
		}
		buffer.removeLast();
		buffer.addFirst(f);
		return true;
		
	}
	public boolean addPageCLOCK(String page) { 
		// return true if the page is add successfully
		Frame j=isInBuffer(page);
		if (j!= null) {
			j.flagToOne();
			return false;
		}
		Frame f=new Frame(page, 0);
		nbr_Access_Disk++;
		System.out.println("one access disk");
		if (buffer.size()<TAILLE ) {
			buffer.addLast(f);
			return true;
		}
		while (buffer.get(pointeur).getFlag()!=0) {
			buffer.get(pointeur).flagToZero();
			if (pointeur ==3) {
				pointeur=0;
			}
			else pointeur++;
		}
		buffer.remove(pointeur);
		buffer.add(pointeur, f);
		return true;
	}

	public static void main(String[] args) {
		
		String[] tab= { "A", "B", "C", "A", "E", "A", "B", "A", "D", "E"};
		String tabstring="[";
		for (int i=0; i<tab.length; i++) {
			tabstring+=tab[i]+ " ";
		}
		tabstring+="]";
		System.out.println(tabstring);
		FrameList fFIFO=new FrameList();
		FrameList fLRU=new FrameList();
		fFIFO.run(tab, "FIFO");
		System.out.println("Etat final:"+ fFIFO);
		fLRU.run (tab, "LRU");
		System.out.println("Etat final:"+ fLRU);
	}
	//TODO passer run en static 
	//methode qui genère un tableau de maniere aleatoire
	// arranger l'affichage
	
	
	
}
                                             