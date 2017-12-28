import java.io.File;
import java.util.TreeMap;
import java.util.List;
import java.util.Map;

public class PaperInfoManager {
	private Map<String, PaperInfo> pinfoMap;
	private String kidoStr = "kido_file/myPaper.kido";
	
	public PaperInfoManager() {
		File kidoFile = new File(kidoStr);
		if(kidoFile.exists())
			pinfoMap = KidoFileHandle.LoadKido(kidoStr);
		else
			pinfoMap = new TreeMap<>();
	}
	
	public void AddInfo(PaperInfo p) {
		pinfoMap.put(p.id, p);
	}
	
	public void SaveInfo() {
		KidoFileHandle.SaveKido(kidoStr, pinfoMap);
	}
	
	public void CheckPaperInfo() {
		
	}
	
	public Map<String, PaperInfo> getPaperInfoMap() {
		return pinfoMap;
	}
	
	public void RefreshNode(MainWindow m) {
		/*
		for (Map.Entry<String, PaperInfo> pinfo : pinfoMap.entrySet()) {
			m.addTreeNode("Root", pinfo.getValue().title);
		}
		*/
		m.ClearTreeNode();
		Map<String, List<String>> result = ClassSortList.ResultList(pinfoMap, "subjects");
		for (Map.Entry<String, List<String>> idClass : result.entrySet()) {
			String className = idClass.getKey();
			m.addTreeNode("Root", className);
			for(String id: idClass.getValue()) {
				m.addTreeNode(className, pinfoMap.get(id).title);
			}
		}
		
	}
	
	public void AddNode(MainWindow m, String str) {
		m.addTreeNode("Root", str);
	}
}
