import java.io.IOException;
import java.util.Arrays;


public class OperationManager 
{
	private MainWindow window;
	private boolean isSearching;
	
	
	/*---------------------------------
	Constructor
	---------------------------------*/
	public OperationManager()
	{
		isSearching = false;
	}
	
	
	/*---------------------------------
	Set window
	---------------------------------*/
	public void setWindow(MainWindow w)
	{
		window = w;
	}
		
	
	/*---------------------------------
	Search items
	---------------------------------*/
	public void search(String str, String type, int total) 
	{
		String[] id_list = ArxivParser.SearchResult(ArxivParser.BuildSearchURL(str, type, 0));
		if(total < id_list.length)
			id_list = Arrays.copyOfRange(id_list, 0, total);

		for(String id : id_list) {
			PaperInfo info_temp = ArxivParser.GetPaperInfo(ArxivParser.BuildURL(id, "abs"));
			window.addItem(info_temp);
		}
		
		isSearching = false;
	}
	
	
	/*---------------------------------
	View items
	---------------------------------*/
	public void view(String id)
	{
		String str = "start " + ArxivParser.BuildURL(id, "pdf");
		str = "a.exe " + "\"" + str + "\"";

		try {
			Runtime.getRuntime().exec(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/*---------------------------------
	Start search
	---------------------------------*/
	public void startSearch(String str, String type, int total)
	{
		window.clearItem();
		
		if(!isSearching) 
		{
			isSearching = true;
			Thread t1 = new searchThread(str, type, total);
			t1.start();
		}
	}
	
	
	/*---------------------------------
	Start download
	---------------------------------*/
	public void startDownload(String id)
	{
		Thread t1 = new downloadThread(id);
		t1.start();
	}
	
	
	/*---------------------------------
	Search thread
	---------------------------------*/
	public class searchThread extends Thread 
	{
		int total;
		String type;
		String str;
		
		public searchThread(String s, String tp, int t) 
		{
			str = s;
			total = t;
			type = tp;
		}
		
		
	    public void run()
	    {
	    	search(str, type, total);
	    }
	}
	
	
	/*---------------------------------
	Download thread
	---------------------------------*/
	public class downloadThread extends Thread 
	{
		String id;
		
		public downloadThread(String id)
		{
			this.id = id;
		}
		
		
		public void run() 
		{
			ArxivParser.Download("paper_file", ArxivParser.BuildURL(id, "pdf"), false);
		}
	}
}
