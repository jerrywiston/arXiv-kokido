import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

public class ClassSortList {
	private Map<String, PaperInfo> paperInfoMap;

	public ClassSortList(Map<String, PaperInfo> pm) {
		paperInfoMap = pm;
	}

	public Map<String, List<String>> Result(String classType, String sortType) {
		Map<String, List<String>> rlist = new LinkedHashMap<>();

		// Class the id list
		for (Map.Entry<String, PaperInfo> ptuple : paperInfoMap.entrySet()) {
			List<String> classArr = BuildClassArr(classType, ptuple.getValue());
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

		// TODO Sort the id list
		rlist = ReverseList(rlist);
		return rlist;
	}

	public List<String> BuildClassArr(String classType, PaperInfo pinfo) {
		List<String> classArr = null;
		switch (classType) {
		case "tags":
			classArr = pinfo.tags;
			break;
			
		case "subjects":
			classArr = pinfo.subjects;
			break;
		
		default:
			classArr = new ArrayList<>();
			classArr.add("All");
		}

		return classArr;
	}

	public List<String> SortList(int[] slist) {
		
		return null;
	}
	
	public Map<String, List<String>> ReverseList(Map<String, List<String>> rlist){
		for (Map.Entry<String, List<String>> rtuple : rlist.entrySet()) 
			Collections.reverse(rtuple.getValue());
		return rlist;
	}
}
