import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;


public class test {

	public static Map<String, PaperInfo> paperInfoMap = new HashMap<>();

	public static void DownloadTest() {
			//String[] id_list = ArxivParser.SearchResult(ArxivParser.BuildSearchURL("RNN", 10));
			String[] id_list = ArxivParser.SearchResult(ArxivParser.BuildRecentURL("cs.AI", 10, 5));
			for (String id : id_list) {
				PaperInfo info_temp = ArxivParser.GetPaperInfo(ArxivParser.BuildURL(id, "abs"));
				System.out.println(info_temp.Out());
				paperInfoMap.put(info_temp.id, info_temp);
				ArxivParser.Download("paper_file", ArxivParser.BuildURL(id, "pdf"), false);
				System.out.println("====================");
			}
			System.out.println("Mission Complete !!");
	}

	public static void SaveKidoTest() {
		KidoFileHandle.SaveKido("test.kido", paperInfoMap);
	}

	public static void LoadKidoTest() {
		Map<String, PaperInfo> paperInfoMap = KidoFileHandle.LoadKido("test.kido");
		for (Map.Entry<String, PaperInfo> pinfo : paperInfoMap.entrySet()) {
			System.out.println(pinfo.getValue().Out());
			System.out.println("====================");
		}
	}

	public static void ClassTest() {
		Map<String, PaperInfo> paperInfoMap = KidoFileHandle.LoadKido("test.kido");
		ClassSortList c = new ClassSortList();
		Map<String, List<String>> rlist = c.ResultList(paperInfoMap, "subjects");
		for (Map.Entry<String, List<String>> r : rlist.entrySet()) {
			System.out.println("<" + r.getKey() + ">");
			for (String id : r.getValue()) {
				//System.out.println(paperInfoMap.get(id).Out());
				//System.out.println("--------------------");
				System.out.println(id);
			}
			//System.out.println("====================");
		}
	}

	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
			//UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
			//UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
    		//UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
    	}
    	catch(Exception e){
    		System.out.println(e.toString());
    	}
		
		OperationManager opManager = new OperationManager();
		MainWindow m = new MainWindow(opManager);
		
		m.addTreeNode("A");
		m.addTreeNode("B");
		m.addTreeNode("C");
		/*
		DownloadTest(); 
		System.out.println("< Download Test End >");
		  
		SaveKidoTest(); 
		System.out.println("< SaveKido Test End >");
		 
		LoadKidoTest(); 
		System.out.println("< LoadKido Test End >");

		ClassTest();
		System.out.println("< Class Test End >");*/
	}

}
