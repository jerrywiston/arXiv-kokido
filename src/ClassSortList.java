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

	public Map<String, List<String>> Result(String classType, String sortType, boolean reverse) {
		Map<String, List<String>> rlist = new LinkedHashMap<>();

		// Class the id list
		for (Map.Entry<String, PaperInfo> ptuple : paperInfoMap.entrySet()) {
			List<String> classArr = BuildClassArr(classType, ptuple.getValue());
			for (String cstr : classArr) {
				if (rlist.containsKey(cstr))
					rlist.get(cstr).add(ptuple.getKey());
				else {
					List<String> ltemp  = new ArrayList<>();
					ltemp.add(ptuple.getKey());
					rlist.put(cstr, ltemp);
				}
			}
		}

		// TODO Sort the id list
		if (sortType != "none") {
			for (Map.Entry<String, List<String>> stuple : rlist.entrySet()) {
				if (sortType == "date") {
					int[] arr = null;
					arr = DateSortList(stuple.getValue());
					Sort(arr, stuple.getValue());
				} else {
					String[] arr = null;
					arr = AhphabatSortList(stuple.getValue());
					Sort(arr, stuple.getValue());
				}
			}
		}

		if (reverse)
			ReverseList(rlist);
		
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

	public void Sort(int[] arr, List<String> slist) {
		int temp;
		String stemp;
		for (int i = 0; i < arr.length; ++i) {
			for (int j = 0; j < arr.length - 1 - i; ++j) {
				if (arr[j] > arr[j + 1]) {
					temp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;

					stemp = slist.get(j);
					slist.set(j, slist.get(j + 1));
					slist.set(j + 1, stemp);
				}
			}
		}
	}

	public void Sort(String[] arr, List<String> slist) {
		String temp;
		String stemp;
		for (int i = 0; i < arr.length; ++i) {
			for (int j = 0; j < arr.length - 1 - i; ++j) {
				if (arr[j].compareTo(arr[j + 1]) > 0) {
					temp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;

					stemp = slist.get(j);
					slist.set(j, slist.get(j + 1));
					slist.set(j + 1, stemp);
				}
			}
		}
	}

	public void ReverseList(Map<String, List<String>> rlist) {
		for (Map.Entry<String, List<String>> rtuple : rlist.entrySet())
			Collections.reverse(rtuple.getValue());
	}

	public String[] AhphabatSortList(List<String> slist) {
		String[] arr = new String[slist.size()];
		for (int i = 0; i < slist.size(); ++i) {
			arr[i] = paperInfoMap.get(slist.get(i)).title.toLowerCase();
		}
		return arr;
	}

	public int[] DateSortList(List<String> slist) {
		int[] arr = new int[slist.size()];
		for (int i = 0; i < slist.size(); ++i) {
			int time = 0;
			time += paperInfoMap.get(slist.get(i)).date[0] * 365;
			time += paperInfoMap.get(slist.get(i)).date[1] * 31;
			time += paperInfoMap.get(slist.get(i)).date[2];
			arr[i] = time;
		}
		return arr;
	}
}
