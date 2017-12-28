import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ClassSortList {
	public static Map<String, List<String>> ResultList(Map<String, PaperInfo> paperInfoMap, String classType) {
		Map<String, List<String> > rlist = new HashMap<>();
		
		for (Map.Entry<String, PaperInfo> pinfo : paperInfoMap.entrySet()) {
			String [] classList = null;
			if(classType == "tags")
				classList = pinfo.getValue().tags;
			else
				classList = pinfo.getValue().subjects;
			
			for (String sub : classList) {
				if (rlist.containsKey(sub)) 
					rlist.get(sub).add(pinfo.getKey());
				else {
					List<String> ltemp = new ArrayList<>();
					ltemp.add(pinfo.getKey());
					rlist.put(sub, ltemp);
				}
			}
		}

		return rlist;
	}

}
