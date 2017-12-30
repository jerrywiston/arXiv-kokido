import java.awt.Component;
import java.awt.Container;


public class GUIManager 
{
	/*--------------------------------
	Refresh
	--------------------------------*/
	public static void refresh(Component com)
	{
		Container parent = com.getParent();
		if(parent == null)
		{
			com.revalidate();
			com.repaint();
		}
		else 
		{
			com.revalidate();
			com.repaint();
			
			Container ancestor = parent.getParent();
			while(ancestor != null)
			{
				parent.revalidate();
				parent.repaint();
				
				parent = ancestor;
				ancestor = parent.getParent();
			}
		}
	}
}
