import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PaperInfoManager {
	private Map<String, PaperInfo> paperInfoMap;
	private static String kidoStr = "kido_file/myPaper.kido";

	public PaperInfoManager() {
		File kidoFile = new File(kidoStr);
		if (kidoFile.exists() && kidoFile.length() > 0) {
			paperInfoMap = KidoFileHandle.LoadKido(kidoStr);
		} else {
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


	public boolean hasKey(String id) {
		return paperInfoMap.containsKey(id);
	}
	
	public void AddTag(String id, String tid) {
		paperInfoMap.get(id).tags.add(tid);
	}
	
	public void RemoveTag(String id, String tid) {
		int index = paperInfoMap.get(id).tags.indexOf(tid);
		paperInfoMap.get(id).tags.remove(index);
	}
	
	public Map<String, PaperInfo> getPaperInfoMap() {
		return paperInfoMap;
	}

}
