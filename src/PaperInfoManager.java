import java.io.File;
import java.util.ArrayList;
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
	
	public List<String> FindMissingFile() {
		List<String> id_list = new ArrayList<>();

		for (Map.Entry<String, PaperInfo> ptuple : paperInfoMap.entrySet()) {
			File f = new File("paper_file/" + ptuple.getKey() + ".pdf");
			if (f.exists() == false)
				id_list.add(ptuple.getKey());
		}
		
		return id_list;
	}
	
	public List<String> FindMissingInfo(){
		List<String> id_list = new ArrayList<>();
		File f = new File("paper_file");
		File [] f1 = f.listFiles();
		for(File fn: f1) {
			String filename = fn.getName();
			if(filename.contains(".pdf")) {
				String id = filename.split(".pdf")[0];
				if(paperInfoMap.containsKey(id)==false)
					id_list.add(id);
			}
		}
		
		return id_list;
	}

}
