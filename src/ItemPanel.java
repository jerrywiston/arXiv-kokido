import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


class ItemPanel extends ShadowPanel 
{
	private static final long serialVersionUID = 1L;
	private String id;
	private int heightOffset;
	private JButton viewBtn;
	private JButton downloadBtn;

	
	/*--------------------------------
	Constructor
	--------------------------------*/
	public ItemPanel(PaperInfo info, final OperationManager opm) 
	{
		super();
		id = info.id;
		heightOffset = info.abs.length() / 60 + 1;
		
		setBackground(new Color(20, 20, 20, 150));
		setLayout(new BorderLayout());
		
		//Create panels
		createWestPanel();
		createCenterPanel(info);
		createSouthPanel(opm);
	}
	
	
	/*--------------------------------
	Create west panel
	--------------------------------*/
	private void createWestPanel()
	{
		JPanel westPanel = new JPanel();
		westPanel.setBackground(new Color(0, 0, 0, 0));
		westPanel.setLayout(new BorderLayout());

		//Check box
		final JCheckBox checkBox = new JCheckBox();
		checkBox.setBackground(new Color(0, 0, 0, 0));
		checkBox.addMouseListener(new MouseAdapter()
		{
			public void mouseExited(MouseEvent e)
			{
				GUIManager.refresh(checkBox);
			}
			
			public void mouseEntered(MouseEvent e)
			{
				GUIManager.refresh(checkBox);
			}
			
			public void mouseClicked(MouseEvent e)
			{
				GUIManager.refresh(checkBox);
			}
		});
		
		westPanel.add(checkBox, BorderLayout.NORTH);
		add(westPanel, BorderLayout.WEST);
	}
	
	
	/*--------------------------------
	Create center panel
	--------------------------------*/
	private void createCenterPanel(PaperInfo info)
	{
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(new Color(0, 0, 0, 0));
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

		//Title label-------------------------------
		JLabel titleLabel = new JLabel(
			"<html><font size='7' face='Verdana' color='#00FFFF'>&nbsp; " + info.title + "</font></html>"
		);

		//Author label------------------------------
		String authors = "";
		for (int i = 0; i < info.authors.size() - 1; ++i)
			authors += info.authors.get(i) + ", ";
		authors += info.authors.get(info.authors.size() - 1);

		JLabel authorLabel = new JLabel(
			"<html><font size='5' face='Verdana' color='white'>&nbsp; Authors: " + authors + "</font></html>"
		);

		//Date label---------------------------------
		JLabel dateLabel = new JLabel(
			"<html><font size='5' face='Verdana' color='white'>&nbsp; Date: " 
			+ info.date[0] + "/" + info.date[1] + "/" + info.date[2] + "</font></html>"
		);

		//Abstract label-----------------------------
		JLabel abstractLabel = new JLabel(
			"<html><font size='5' face='Verdana' color='white'>&nbsp; Abstract:\n" + info.abs + "</font></html>"
		);

		//Subject label------------------------------
		String subs = "";
		for (int i = 0; i < info.subjects.size() - 1; ++i)
			subs += info.subjects.get(i) + ", ";
		subs += info.subjects.get(info.subjects.size() - 1);
		JLabel subjectLabel = new JLabel(
			"<html><font size='5' face='Verdana' color='white'>&nbsp; Subjects: " + subs + "</font></html>"
		);

		//Tag label-----------------------------------
		String tags = "";
		if (info.tags != null && info.tags.size() > 0) 
		{
			for (int i = 0; i < info.tags.size() - 1; ++i)
				tags += info.tags.get(i) + ", ";
			tags += info.tags.get(info.tags.size() - 1);
		}
		
		JLabel tagLabel = new JLabel(
			"<html><font size='5' face='Verdana' color='white'>&nbsp; Tags: " + tags + "</font></html>"
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
	private void createSouthPanel(final OperationManager opm)
	{
		JPanel southPanel = new JPanel();
		southPanel.setBackground(new Color(0, 0, 0, 0));
		southPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		//View button---------------------------------
		viewBtn = new JButton("View");
		viewBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				opm.view(id, true);
			}
		});
		
		//Download button------------------------------
		downloadBtn = new JButton("Download");
		downloadBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				opm.startDownload(id, downloadBtn);
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
	
	
	/*--------------------------------
	Set download button enable/disable
	--------------------------------*/
	public void setDownloadBtn(boolean b) {
		downloadBtn.setEnabled(b);
	}
}