import java.util.ArrayList;
import java.util.List;

public class PaperInfo {
	public String id;
	public String title;
	public String[] authors = new String[0];
	public int[] date = new int[0];
	public String abs;
	public String[] subjects = new String[0];
	public List<String> tags = new ArrayList<>();

	public String Out() {
		String str = "";
		str += "ID: " + id + "\n";
		str += "Title: " + title + "\n";

		str += "Authors: ";
		for (int i = 0; i < authors.length - 1; ++i)
			str += authors[i] + ", ";
		str += authors[authors.length - 1] + "\n";

		str += "Date: ";
		str += date[0] + "/" + date[1] + "/" + date[2] + "\n";
		str += "Abstract: ";
		str += abs + "\n";

		str += "Subjects: ";
		for (int i = 0; i < subjects.length - 1; ++i)
			str += subjects[i] + ", ";
		str += subjects[subjects.length - 1] + "\n";

		str += "Tags: ";
		if (tags.size() > 0) {
			for (int i = 0; i < tags.size() - 1; ++i)
				str += tags.get(i) + ", ";
			str += tags.get(tags.size() - 1);
		}
		return str;
	}
}
