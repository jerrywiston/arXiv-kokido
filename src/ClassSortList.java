import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class ClassSortList {
	private Map<String, PaperInfo> paperInfoMap;
	
	public ClassSortList(Map<String, PaperInfo> pm) {
		paperInfoMap = pm;
	}
	
	public Map<String, List<String>> Result(String classType, String sortType) {
		Map<String, List<String> > rlist = new TreeMap<>();
		
		//Class the id list
		for (Map.Entry<String, PaperInfo> ptuple : paperInfoMap.entrySet()) {
			String [] classArr = BuildClassArr(classType, ptuple.getValue());
			for (String cstr : classArr) {
				if (rlist.containsKey(cstr)) 
					rlist.get(cstr).add(ptuple.getKey());
				else {
					List<String> ltemp = new ArrayList<>();
					ltemp.add(ptuple.getKey());
					rlist.put(cstr, ltemp);
				}
			}
		}
		
		//TODO Sort the id list

		return rlist;
	}
	
	public String [] BuildClassArr(String classType, PaperInfo pinfo){
		String [] classArr = null;
		if(classType == "tags") {
			classArr = new String[pinfo.tags.size()];
			classArr = pinfo.tags.toArray(classArr);
		}
		else
			classArr = pinfo.subjects;
		return classArr;
	}
	
	public List<String> SortList() {
		return null;
	}

}
