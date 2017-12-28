import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class ShadowPanel extends JPanel
{
	protected int strokeSize = 1;
	protected Color shadowColor = Color.black;
	protected Dimension arcs = new Dimension(20, 20);
	protected int shadowGap = 5;
	protected int shadowOffset = 4;
	protected int shadowAlpha = 100;


	public ShadowPanel()
	{
		super();
		setOpaque(false);
	}


	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		int width = getWidth();
		int height = getHeight();
		Graphics2D graphics = (Graphics2D) g;

		//Set antialiasing
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		//Draw shadow borders
		graphics.setColor(new Color(
			shadowColor.getRed(), 
			shadowColor.getGreen(), 
			shadowColor.getBlue(), 
			shadowAlpha 
		));
		graphics.fillRoundRect(
			shadowOffset, 
			shadowOffset, 
			width - strokeSize - shadowOffset,
			height - strokeSize - shadowOffset,
			arcs.width,
			arcs.height
		);

		//Draw rounded opaque panel with
		graphics.setColor(getBackground());
		graphics.fillRoundRect(0, 0, width-shadowGap, height-shadowGap, arcs.width, arcs.height);
		graphics.setStroke(new BasicStroke());
	}
}