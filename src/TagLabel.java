import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JLabel;


public class TagLabel extends JLabel
{
	private static final long serialVersionUID = 1L;
	private boolean isSelected;
	
	
	/*--------------------------------
	Constructor
	--------------------------------*/
	public TagLabel()
	{
		super();
		init();
	}
	
	
	public TagLabel(String txt)
	{
		super(txt);
		init();
	}
	
	
	/*--------------------------------
	Initialization
	--------------------------------*/
	private void init()
	{
		isSelected = false;
		
		setForeground(Color.white);
		setBorder(BorderFactory.createLineBorder(Color.white));
		setFont(new Font("Verdana", Font.PLAIN, 18));
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				if(e.getButton() == MouseEvent.BUTTON1)
					setSelected(!isSelected);
			}
		});
	}
	
	
	/*--------------------------------
	Access isSelected
	--------------------------------*/
	public void setSelected(boolean s)
	{
		isSelected = s;
		if(isSelected)
		{
			setForeground(Color.orange);
			setBorder(BorderFactory.createLineBorder(Color.orange));
		}
		else
		{
			setForeground(Color.white);
			setBorder(BorderFactory.createLineBorder(Color.white));
		}
		
		GUIManager.refresh(this);
	}
	
	
	public boolean getSelected()
	{
		return isSelected;
	}
}
