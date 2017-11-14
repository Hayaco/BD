package Buffer;

public class Frame {
	private String pagename;
	private int flag; // pour la methode clock
	
	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	public Frame(String pagename, int flag) {
		if (pagename.compareTo("A")<0 || pagename.compareTo("G")>0)
			throw new IllegalArgumentException("Mauvais nom de page ");
		this.pagename = pagename;
		this.flag=flag;
	}
	public Frame(String pagename) {
		this(pagename, -1);
	}

	public String getPagename() {
		return pagename;
	}

	public void setPagename(String pagename) {
		if (pagename.compareTo("A")<0 || pagename.compareTo("G")>0)
			throw new IllegalArgumentException("Mauvais nom de page ");
		this.pagename = pagename;
	}
	public void flagToZero() {
		flag=0;
	}
	public void flagToOne() {
		flag=1;
	}
	@Override
	public String toString() {
		if (flag==-1) {
			return pagename;
		}
		return pagename +"flag: "+ flag;
	}	
}
