import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


class ProfilePanel extends ShadowPanel 
{
	private static final long serialVersionUID = 1L;
	private String id;
	private OperationManager opManager;
	private JLabel titleLabel;
	private JLabel authorLabel;
	private JLabel dateLabel;
	private JLabel subjectLabel;
	private JLabel abstractLabel;
	private JLabel tagLabel;

	
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
		createWestPanel();
		createCenterPanel();
		createSouthPanel();
	}
	
	
	/*--------------------------------
	Create west panel
	--------------------------------*/
	private void createWestPanel()
	{
		JPanel westPanel = new JPanel();
		westPanel.setBackground(new Color(0, 0, 0, 0));
		westPanel.setPreferredSize(new Dimension(10, 10));
		
		add(westPanel, BorderLayout.WEST);
	}
	
	
	/*--------------------------------
	Create center panel
	--------------------------------*/
	private void createCenterPanel()
	{
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(new Color(0, 0, 0, 0));
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

		titleLabel = new JLabel();
		authorLabel = new JLabel();
		dateLabel = new JLabel();
		abstractLabel = new JLabel();
		subjectLabel = new JLabel();
		tagLabel = new JLabel();

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
				opManager.view(id, false);
			}
		});
		
		//Download button------------------------------
		JButton downloadBtn = new JButton("Delete");
		downloadBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				opManager.removeInfo(id);
				opManager.setProfileVisible(false);
			}
		});
		
		southPanel.add(viewBtn);
		southPanel.add(downloadBtn);
		add(southPanel, BorderLayout.SOUTH);
	}
	
	
	/*--------------------------------
	Set paper info
	--------------------------------*/
	public void setPaperInfo(PaperInfo info)
	{
		id = info.id;
		
		//Title label-------------------------------
		titleLabel.setText(
			"<html><font size='7' face='Verdana' color='#00FFFF'>" + info.title + "</font></html>"
		);

		//Author label------------------------------
		String authors = "";
		for (int i = 0; i < info.authors.size() - 1; ++i)
			authors += info.authors.get(i) + ", ";
		authors += info.authors.get(info.authors.size() - 1);

		authorLabel.setText(
			"<html><font size='5' face='Verdana' color='white'>&nbsp; Authors: " + authors + "</font></html>"
		);

		//Date label---------------------------------
		dateLabel.setText(
			"<html><font size='5' face='Verdana' color='white'>&nbsp; Date: " 
			+ info.date[0] + "/" + info.date[1] + "/" + info.date[2] + "</font></html>"
		);

		//Abstract label-----------------------------
		abstractLabel.setText(
			"<html><font size='5' face='Verdana' color='white'>&nbsp; Abstract:<br>" + info.abs + "</font></html>"
		);

		//Subject label------------------------------
		String subs = "";
		for (int i = 0; i < info.subjects.size() - 1; ++i)
			subs += info.subjects.get(i) + ", ";
		subs += info.subjects.get(info.subjects.size() - 1);
		subjectLabel.setText(
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
				
		tagLabel.setText(
			"<html><font size='5' face='Verdana' color='white'>&nbsp; Tags: " + tags + "</font></html>"
		);
	}

	
	/*--------------------------------
	Access id
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
		setMinimumSize(new Dimension(100, 270));
		setMaximumSize(new Dimension(2048, 1024));
		setPreferredSize(new Dimension(width, 1024));
	}
}