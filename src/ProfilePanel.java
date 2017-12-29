import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


class ProfilePanel extends ShadowPanel 
{
	private String id;
	private int heightOffset;
	private OperationManager opManager;

	
	/*--------------------------------
	Constructor
	--------------------------------*/
	public ProfilePanel(OperationManager opm) 
	{
		super();
		opManager = opm;
		
		setBackground(new Color(20, 20, 20, 150));
		setLayout(new BorderLayout());
		
		//Create panels
		createCenterPanel();
		createSouthPanel();
	}
	
	
	/*--------------------------------
	Create center panel
	--------------------------------*/
	private void createCenterPanel()
	{
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(new Color(0, 0, 0, 0));
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

		//Title label-------------------------------
		JLabel titleLabel = new JLabel(
			"<html><font size='7' face='Verdana' color='#00FFFF'>&nbsp;</font></html>"
		);

		//Author label------------------------------
		JLabel authorLabel = new JLabel(
			"<html><font size='5' face='Verdana' color='white'>&nbsp; Authors: </font></html>"
		);

		//Date label---------------------------------
		JLabel dateLabel = new JLabel(
			"<html><font size='5' face='Verdana' color='white'>&nbsp; Date: </font></html>"
		);

		//Abstract label-----------------------------
		JLabel abstractLabel = new JLabel(
			"<html><font size='5' face='Verdana' color='white'>&nbsp; Abstract: </font></html>"
		);

		//Subject label------------------------------
		JLabel subjectLabel = new JLabel(
			"<html><font size='5' face='Verdana' color='white'>&nbsp; Subjects: </font></html>"
		);

		//Tag label-----------------------------------
		JLabel tagLabel = new JLabel(
			"<html><font size='5' face='Verdana' color='white'>&nbsp; Tags: </font></html>"
		);

		centerPanel.add(titleLabel);
		centerPanel.add(Box.createVerticalGlue());
		centerPanel.add(authorLabel);
		centerPanel.add(Box.createVerticalGlue());
		centerPanel.add(dateLabel);
		centerPanel.add(Box.createVerticalGlue());
		centerPanel.add(abstractLabel);
		centerPanel.add(Box.createVerticalGlue());
		centerPanel.add(subjectLabel);
		centerPanel.add(Box.createVerticalGlue());
		centerPanel.add(tagLabel);
		add(centerPanel, BorderLayout.CENTER);
	}
	
	
	/*--------------------------------
	Create south panel
	--------------------------------*/
	private void createSouthPanel()
	{
		JPanel southPanel = new JPanel();
		southPanel.setBackground(new Color(0, 0, 0, 0));
		southPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		//View button---------------------------------
		JButton viewBtn = new JButton("View");
		viewBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				opManager.view(id);
			}
		});
		
		//Download button------------------------------
		JButton downloadBtn = new JButton("Download");
		downloadBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				opManager.startDownload(id);
			}
		});
		
		southPanel.add(viewBtn);
		southPanel.add(downloadBtn);
		add(southPanel, BorderLayout.SOUTH);
	}

	
	/*--------------------------------
	Set panel shape
	--------------------------------*/
	public String getId() 
	{
		return id;
	}

	
	public void setId(String id) 
	{
		this.id = id;
	}

	
	/*--------------------------------
	Set panel shape
	--------------------------------*/
	public void setShape(int width) 
	{
		setMinimumSize(new Dimension(100, 270 + heightOffset * 20));
		setMaximumSize(new Dimension(2048, 270 + heightOffset * 20));
		setPreferredSize(new Dimension(width, 270 + heightOffset * 20));
	}
}