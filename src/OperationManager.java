import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;

public class OperationManager {
	private MainWindow window;
	private boolean isSearching;
	PaperInfoManager pinfoManager;

	/*---------------------------------
	Constructor
	---------------------------------*/
	public OperationManager(PaperInfoManager pm) {
		isSearching = false;
		pinfoManager = pm;
	}

	/*---------------------------------
	Set window
	---------------------------------*/
	public void setWindow(MainWindow w) {
		window = w;
	}

	/*---------------------------------
	Save PaperInfoMap
	---------------------------------*/
	public void SaveInfo() {
		pinfoManager.SaveInfo();
	}

	/*---------------------------------
	Save PaperInfoMap
	---------------------------------*/
	public void removeInfo(String id) {
		pinfoManager.RemoveInfo(id);
		pinfoManager.SaveInfo();
		refreshNode();
		removeFile(id);
	}

	public void removeFile(String id) {
		try {
			File file = new File("paper_file/" + id + ".pdf");
			if (file.delete()) {
				window.setState(file.getName() + " is deleted!");
			} else {
				window.setState("Delete operation is failed.");
			}
		} catch (Exception e) {

			e.printStackTrace();

		}
	}

	
	/*---------------------------------
	Set window
	---------------------------------*/
	public PaperInfo getInfo(String id) {
		return pinfoManager.getPaperInfoMap().get(id);
	}

	/*---------------------------------
	Set profile visible
	---------------------------------*/
	public void setProfileVisible(boolean b) {
		window.setProfileVisible(false);
	}

	/*---------------------------------
	Search items
	---------------------------------*/
	public void search(String str, String type, int total, int skip) {
		if (str.length() <= 0) {
			isSearching = false;
			window.setState("");
			return;
		}

		String req = ArxivParser.BuildSearchURL(str, type, skip);
		window.setState("Searching... URL: " + req);

		String[] id_list = ArxivParser.SearchResult(req);
		if (total < id_list.length)
			id_list = Arrays.copyOfRange(id_list, 0, total);

		for (String id : id_list) {
			PaperInfo info_temp = ArxivParser.GetPaperInfo(ArxivParser.BuildURL(id, "abs"));
			ItemPanel p = window.addItem(info_temp);
			if (pinfoManager.hasKey(id))
				p.setDownloadBtn(false);
		}

		isSearching = false;
		window.setState("");
	}

	/*---------------------------------
	View items
	---------------------------------*/
	public void view(String id, boolean showInBrowser) {
		String str;
		if (showInBrowser)
			str = "start " + ArxivParser.BuildURL(id, "pdf");
		else
			str = "start " + "paper_file/" + id + ".pdf";
		str = "a.exe " + "\"" + str + "\"";

		try {
			Runtime.getRuntime().exec(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*---------------------------------
	Refresh Map
	---------------------------------*/
	public void refreshNode() {
		Map<String, PaperInfo> paperInfoMap = pinfoManager.getPaperInfoMap();
		window.ClearTreeNode();
		ClassSortList cs = new ClassSortList(paperInfoMap);
		Map<String, List<String>> result = cs.Result("subjects", "date", true);
		for (Map.Entry<String, List<String>> idClass : result.entrySet()) {
			String className = idClass.getKey();
			window.addTreeNode("Root", className);
			for(String id: idClass.getValue()) {
				window.addTreeNode(className, "[" + id  + "] " + paperInfoMap.get(id).title);
			}
		}	
	}
	
	/*---------------------------------
	Start search
	---------------------------------*/
	public void startSearch(String str, String type, int total, int skip) {
		if (!isSearching) {
			if (skip <= 0)
				window.clearItem();

			isSearching = true;
			Thread t1 = new searchThread(str, type, total, skip);
			t1.start();
			window.repaintScreen();
		}
	}

	/*---------------------------------
	Start download
	---------------------------------*/
	public void startDownload(String id, JButton b) {
		Thread t1 = new downloadThread(id, b);
		t1.start();
		window.repaintScreen();
	}

	/*---------------------------------
	Search thread
	---------------------------------*/
	public class searchThread extends Thread {
		int total;
		int skip;
		String type;
		String str;

		public searchThread(String s, String tp, int t, int sk) {
			str = s;
			type = tp;
			total = t;
			skip = sk;
		}

		public void run() {
			search(str, type, total, skip);
			window.repaintScreen();
		}
	}

	/*---------------------------------
	Download thread
	---------------------------------*/
	public class downloadThread extends Thread {
		String id;
		JButton btn;

		public downloadThread(String id, JButton b) {
			this.id = id;
			this.btn = b;
		}

		public void run() {
			if (pinfoManager.getPaperInfoMap().containsKey(id) == false) {
				String req = ArxivParser.BuildURL(id, "pdf");
				window.setState("Downloading... URL: " + req);

				ArxivParser.Download("paper_file", req, false);
				PaperInfo info_temp = ArxivParser.GetPaperInfo(ArxivParser.BuildURL(id, "abs"));
				pinfoManager.AddInfo(info_temp);
				pinfoManager.SaveInfo();
				refreshNode();
				// System.out.println(info_temp.Out());
				window.setState("");
				btn.setEnabled(false);
				window.repaintScreen();
			}
		}
	}
}
