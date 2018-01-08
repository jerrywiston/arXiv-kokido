import javax.swing.UIManager;

public class Kokido 
{
	PaperInfoManager pinfoManager;
	OperationManager opManager;
	MainWindow mainWin;
	ProgressWindow progressWin;
	
	
	public Kokido()
	{
		try{
    		UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
    	}
    	catch(Exception e){
    		System.out.println(e.toString());
    	}
		
		pinfoManager = new PaperInfoManager();
		opManager = new OperationManager(pinfoManager);
		mainWin = new MainWindow(opManager);
		progressWin = new ProgressWindow("");
		
		opManager.setMainWindow(mainWin);
		opManager.refreshNode();
		mainWin.synchronize(opManager);
		mainWin.setVisible(true);
	}
	
	
	public static void main(String[] args) 
	{
		Kokido kokido = new Kokido();
	}
}
