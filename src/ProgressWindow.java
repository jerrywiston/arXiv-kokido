import javax.swing.*;


public class ProgressWindow extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JLabel progressLabel;
	private JProgressBar progressBar;
	
	
	/*---------------------------------
	Constructor
	---------------------------------*/
	public ProgressWindow(String title)
	{
		// Create the main frame--------------------
		setTitle(title);
		setSize(512, 128);
		setIconImage(new ImageIcon("./kokido.png").getImage());
		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		
		progressLabel = new JLabel("");
		
		progressBar = new JProgressBar();
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		
		add(progressLabel);
		add(Box.createVerticalGlue());
		add(progressBar);
		add(Box.createVerticalGlue());
	}
	
	
	/*---------------------------------
	Set progress text
	---------------------------------*/
	public void setProgressText(String str)
	{
		progressLabel.setText(str);
	}
	
	
	/*---------------------------------
	Set progress value
	---------------------------------*/
	public void setProgressValue(int val)
	{
		progressBar.setValue(val);
	}
}
