
public class PaperInfo {
	public String id;
	public String title;
	public String [] authors = new String[0];
	public int [] date = new int[0];
	public String abs;
	public String [] subjects = new String[0];
	public String [] tags = new String[0];
	
	public String Out() {
		String str = "";
		str = str + "ID: " + id + "\n";
		str = str + "Title: " + title + "\n";
		str = str + "Authors: ";
		for(String name: authors)
			str = str + "[" + name + "]";
		str = str + "\nDate: ";
		str = str + date[0] + "/" + date[1] + "/" + date[2] + "\n";
		str = str + "Abstract: ";
		str = str + abs + "\n";
		str = str + "Subjects: ";
		for(String sub: subjects)
			str = str + "[" + sub + "]";
		return str;
	}
}
