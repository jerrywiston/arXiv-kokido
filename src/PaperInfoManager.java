import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PaperInfoManager {
	private Map<String, PaperInfo> paperInfoMap;
	private static String kidoStr = "kido_file/myPaper.kido";
	
	public PaperInfoManager() {
		File kidoFile = new File(kidoStr);
		if(kidoFile.exists() && kidoFile.length()>0) {
			paperInfoMap = KidoFileHandle.LoadKido(kidoStr);
		}
		else {
			paperInfoMap = new LinkedHashMap<>();
		}
	}
	
	public void AddInfo(PaperInfo p) {
		paperInfoMap.put(p.id, p);
	}
	
	public void RemoveInfo(String id) {
		paperInfoMap.remove(id);
	}
	
	public void SaveInfo() {
		KidoFileHandle.SaveKido(kidoStr, paperInfoMap);
	}
	
	public void CheckInfo() {
		
	}
	
	public boolean hasKey(String id) {
		return paperInfoMap.containsKey(id);
	}
	
	public Map<String, PaperInfo> getPaperInfoMap() {
		return paperInfoMap;
	}
	
	public void RefreshNode(MainWindow m) {
		m.ClearTreeNode();
		ClassSortList cs = new ClassSortList(paperInfoMap);
		Map<String, List<String>> result = cs.Result("subjects", "date", true);
		for (Map.Entry<String, List<String>> idClass : result.entrySet()) {
			String className = idClass.getKey();
			m.addTreeNode("Root", className);
			for(String id: idClass.getValue()) {
				m.addTreeNode(className, "[" + id  + "] " + paperInfoMap.get(id).title);
			}
		}	
	}
	
	public void AddNode(MainWindow m, String str) {
		m.addTreeNode("Root", str);
	}
}
