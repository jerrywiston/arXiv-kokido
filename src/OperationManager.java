import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;

public class OperationManager 
{
	private MainWindow win;
	private ProgressWindow progressWin;
	private boolean isSearching;
	PaperInfoManager piManager;
	String classType = "Subjects";
	String sortType = "Recent";
	String orderType = "Decent";
	

	/*---------------------------------
	Get field string
	---------------------------------*/
	public static String fieldStr(String str) {
		String fstr = null;

		switch (str) {
		case "CS":
			fstr = "grp_cs";
			break;
		case "Econ":
			fstr = "grp_econ";
			break;
		case "EESS":
			fstr = "grp_eess";
			break;
		case "Math":
			fstr = "grp_math";
			break;
		case "Phy":
			fstr = "grp_physics";
			break;
		case "Bio":
			fstr = "grp_q-bio";
			break;
		case "Fin":
			fstr = "grp_q-fin";
			break;
		case "Stat":
			fstr = "grp_stat";
			break;
		default:
			fstr = "all";
		}

		return fstr;
	}
	
	
	/*---------------------------------
	Search thread
	---------------------------------*/
	public class searchThread extends Thread {
		int total;
		int skip;
		String type;
		String field;
		String str;

		public searchThread(String s, String tp, String f, int t, int sk) {
			str = s;
			type = tp;
			field = f;
			total = t;
			skip = sk;
		}

		public void run() {
			search(str, type, field, total, skip);
		}
	}

	
	/*---------------------------------
	Constructor
	---------------------------------*/
	public OperationManager(PaperInfoManager pim) {
		isSearching = false;
		piManager = pim;
		createFolder("paper_file");
		createFolder("kido_file");
	}
	
	
	/*---------------------------------
	Set MainWindow
	---------------------------------*/
	public void setMainWindow(MainWindow w)
	{
		win = w;
	}
	
	
	/*---------------------------------
	set PaperInfoManager
	---------------------------------*/
	public void setPaperInfoManager(PaperInfoManager pim)
	{
		piManager = pim;
	}
	
	
	/*---------------------------------
	set ProgressWindow
	---------------------------------*/
	public void setProgressWindow(ProgressWindow w)
	{
		progressWin = w;
	}

	
	/*---------------------------------
	Create folder
	---------------------------------*/
	public void createFolder(String folderName) {
		File theDir = new File(folderName);

		// if the directory does not exist, create it
		if (!theDir.exists()) {
			System.out.println("creating directory: " + theDir.getName());
			boolean result = false;

			try {
				theDir.mkdir();
				result = true;
			} catch (SecurityException se) {
				// handle it
			}
			if (result) {
				System.out.println("DIR created");
			}
		}
	}

	
	/*---------------------------------
	Save PaperInfoMap
	---------------------------------*/
	public void SaveInfo() {
		piManager.SaveInfo();
	}

	
	/*---------------------------------
	Save PaperInfoMap
	---------------------------------*/
	public void removeInfo(String id) {
		piManager.RemoveInfo(id);
		piManager.SaveInfo();
		refreshNode();
		removeFile(id);
	}

	
	/*---------------------------------
	Remove a file
	---------------------------------*/
	public void removeFile(String id) {
		try {
			File file = new File("paper_file/" + id + ".pdf");
			if (file.delete()) {
				win.setState(file.getName() + " is deleted!");
			} else {
				win.setState("Delete operation is failed.");
			}
		} catch (Exception e) {

			e.printStackTrace();

		}
	}

	
	/*---------------------------------
	Set window
	---------------------------------*/
	public PaperInfo getInfo(String id) {
		return piManager.getPaperInfoMap().get(id);
	}

	
	/*---------------------------------
	Set profile visible
	---------------------------------*/
	public void setProfileVisible(boolean b) {
		win.setProfileVisible(false);
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

		runCommand(str);
	}

	
	/*---------------------------------
	Run a command
	---------------------------------*/
	public void runCommand(String command) {
		ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", command);
		try {
			pb.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	/*---------------------------------
	Start search
	---------------------------------*/
	public void startSearch(String str, String type, String field, int total, int skip) {
		if (!isSearching) {
			if (skip <= 0)
				win.clearItem();

			isSearching = true;
			Thread t1 = new searchThread(str, type, field, total, skip);
			t1.start();
		}
	}


	/*---------------------------------
	Search items
	---------------------------------*/
	public void search(String str, String type, String field, int total, int skip) {
		if (str.length() <= 0) {
			isSearching = false;
			win.setState("");
			return;
		}

		String req = ArxivParser.BuildSearchURL(str, type, fieldStr(field), skip);
		win.setState("Searching... URL: " + req);

		String[] id_list = ArxivParser.SearchResult(req);
		if (total < id_list.length)
			id_list = Arrays.copyOfRange(id_list, 0, total);

		for (String id : id_list) {
			PaperInfo info_temp = ArxivParser.GetPaperInfo(ArxivParser.BuildURL(id, "abs"));
			if (info_temp == null)
				continue;
			
			ItemPanel item = new ItemPanel(info_temp, this);
			win.addItem(item);
			if (piManager.hasKey(id))
				item.setDownloadBtn(false);
		}

		isSearching = false;
		win.setState("");
	}

	
	/*---------------------------------
	Start download
	---------------------------------*/
	public void startDownload(String id, JButton b) {
		Thread t1 = new downloadThread(id, b);
		t1.start();
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
			if (piManager.getPaperInfoMap().containsKey(id) == false) {
				String req = ArxivParser.BuildURL(id, "pdf");
				win.setState("Downloading... URL: " + req);

				ArxivParser.Download("paper_file", req, false);
				PaperInfo info_temp = ArxivParser.GetPaperInfo(ArxivParser.BuildURL(id, "abs"));
				piManager.AddInfo(info_temp);
				piManager.SaveInfo();
				refreshNode();
				// System.out.println(info_temp.Out());
				win.setState("");
				btn.setEnabled(false);
			}
		}
	}

	
	/*---------------------------------
	Find missing file
	---------------------------------*/
	public List<String> findMissingFile() {
		List<String> id_list = new ArrayList<>();

		for (Map.Entry<String, PaperInfo> ptuple : piManager.getPaperInfoMap().entrySet()) {
			File f = new File("paper_file/" + ptuple.getKey() + ".pdf");
			if (f.exists() == false)
				id_list.add(ptuple.getKey());
		}

		return id_list;
	}

	
	/*---------------------------------
	Find missing info
	---------------------------------*/
	public List<String> findMissingInfo() {
		List<String> id_list = new ArrayList<>();
		File f = new File("paper_file");
		File[] f1 = f.listFiles();
		for (File fn : f1) {
			String filename = fn.getName();
			if (filename.contains(".pdf")) {
				String id = filename.split(".pdf")[0];
				if (piManager.hasKey(id) == false)
					id_list.add(id);
			}
		}

		return id_list;
	}

	
	/*---------------------------------
	Synchronize file
	---------------------------------*/
	public void fileSynchronize(List<String> id_list, boolean b) {
		if (b == true) {
			win.setState("Find " + id_list.size() + " missing pdf, downloading the file ...");
			progressWin.setTitle("Download PDFs");
			progressWin.setVisible(true);
			progressWin.setProgressText("Downloading ...");
			
			int count = 0;
			for (String id : id_list) {
				progressWin.setProgressValue(count * 100 / id_list.size());
				ArxivParser.Download("paper_file", ArxivParser.BuildURL(id, "pdf"), false);
				++count;
			}
			progressWin.setVisible(false);
		} else {
			win.setState("Find " + id_list.size() + " missing pdf, removing the info ...");
			for (String id : id_list)
				piManager.RemoveInfo(id);
		}

		win.setState("");
	}

	
	/*---------------------------------
	Synchronize info
	---------------------------------*/
	public void infoSynchronize(List<String> id_list, boolean b) {
		if (b == true) {
			win.setState("Find " + id_list.size() + " missing info, searching the info ...");
			progressWin.setTitle("Add Infos");
			progressWin.setVisible(true);
			progressWin.setProgressText("Adding ...");
			
			int count = 0;
			for (String id : id_list) {
				progressWin.setProgressValue(count * 100 / id_list.size());
				PaperInfo pinfo = ArxivParser.GetPaperInfo(ArxivParser.BuildURL(id, "abs"));
				if (pinfo != null)
					piManager.AddInfo(pinfo);
				++count;
			}
			progressWin.setVisible(false);
		} else {
			win.setState("Find " + id_list.size() + " missing info, removing the file ...");
			for (String id : id_list) {
				try {
					File file = new File("paper_file/" + id + ".pdf");
					if (file.delete()) {
						System.out.println(file.getName() + " is deleted!");
					} else {
						System.out.println("Delete operation is failed.");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		piManager.SaveInfo();
		refreshNode();
		win.setState("");
	}

	
	/*---------------------------------
	Tag Handle
	---------------------------------*/
	public void addTag(String id, String tid) {
		piManager.AddTag(id, tid);
		refreshNode();
		piManager.SaveInfo();
	}

	public void removeTag(String id, List<String> tlist) {
		for (String tid : tlist)
			piManager.RemoveTag(id, tid);
		refreshNode();
		piManager.SaveInfo();
	}

	
	/*---------------------------------
	Class Sort
	---------------------------------*/
	public void setClassSort(String c, String s, String o) {
		classType = c;
		sortType = s;
		orderType = o;
	}

	
	/*---------------------------------
	Refresh Node
	---------------------------------*/
	public void refresh(Map<String, PaperInfo> paperInfoMap) 
	{
		win.ClearTreeNode();
		ClassSortList cs = new ClassSortList(paperInfoMap);

		boolean b = (orderType == "Decent") ? true : false;

		Map<String, List<String>> result = cs.Result(classType, sortType, b);
		for (Map.Entry<String, List<String>> idClass : result.entrySet()) {
			String className = idClass.getKey();
			win.addTreeNode("Root", className);
			for (String id : idClass.getValue()) {
				win.addTreeNode(className, "[" + id + "] " + paperInfoMap.get(id).title);
			}
		}
	}

	
	public void refreshNode() 
	{
		refresh(piManager.getPaperInfoMap());
	}
	

	/*---------------------------------
	Local Search
	---------------------------------*/
	public void localSearch(String str) {
		String[] slist = str.split(" ");
		Map<String, PaperInfo> paperInfoMap = piManager.getPaperInfoMap();
		Map<String, PaperInfo> searchInfoMap = new LinkedHashMap<>();
		for (Map.Entry<String, PaperInfo> ptuple : paperInfoMap.entrySet()) {
			boolean find = false;
			for (String s : slist) {
				if (ptuple.getValue().title.toLowerCase().contains(s) == true)
					find = true;
				if (ptuple.getValue().abs.toLowerCase().contains(s) == true)
					find = true;
				if (find == true)
					break;
			}
			if (find == true)
				searchInfoMap.put(ptuple.getKey(), ptuple.getValue());
		}
		refresh(searchInfoMap);
	}
}
