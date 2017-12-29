import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


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
	private JLabel tagLeadingLabel;
	private JPanel tagPanel;
	private ArrayList<TagLabel> tagLabels;
	private JButton addTagBtn;
	private ImageIcon icon;
	JPopupMenu popup;
	
	
	/*--------------------------------
	Constructor
	--------------------------------*/
	public ProfilePanel(OperationManager opm) 
	{
		super();
		opManager = opm;
		tagLabels = new ArrayList<TagLabel>();
		icon = new ImageIcon("./kokido.png");
		icon = new ImageIcon(icon.getImage().getScaledInstance(64, 64, 0));
		
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
		westPanel.setMinimumSize(new Dimension(10, 10));
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
		add(centerPanel, BorderLayout.CENTER);
	}
	
	
	/*--------------------------------
	Create south panel
	--------------------------------*/
	private void createSouthPanel()
	{
		JPanel southPanel = new JPanel();
		southPanel.setBackground(new Color(0, 0, 0, 0));
		southPanel.setLayout(new GridLayout(1, 2));
		
		
		//Tag panel-----------------------------------
		tagPanel = new JPanel();
		tagPanel.setBackground(new Color(0, 0, 0, 0));
		tagPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 0));
		
		tagLeadingLabel = new JLabel(
			"<html><font size='5' face='Verdana' color='white'>&nbsp;&nbsp; Tags:</font></html>"
		);
		
		//Add tag button-----------------------------
		addTagBtn = new JButton("+");
		addTagBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) 
			{
				String tagStr = (String)JOptionPane.showInputDialog(
					tagPanel, 
					"Please input a tag", 
					"Add Tag", 
					JOptionPane.PLAIN_MESSAGE, 
					icon, 
					null, 
					null
				);
				
				if(tagStr.length() > 0)
				{
					addTag(tagStr);
					opManager.addTag(id, tagStr);
				}
			};
		});
		
		//Add popup menu------------------------------
		popup = new JPopupMenu();
		
		JMenuItem deleteMenuItem = new JMenuItem("Delete Tag");
		deleteMenuItem.addActionListener(new ActionListener()
		{ 
			public void actionPerformed(ActionEvent event) 
			{
				deleteSelectedTag();
			}
		});
		popup.add(deleteMenuItem);
		
		tagPanel.addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent e)
			{
				if(e.isPopupTrigger())
					popup.show(e.getComponent(), e.getX(), e.getY());
			}
			
			public void mouseReleased(MouseEvent e)
			{
				if(e.isPopupTrigger())
					popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});

		tagPanel.add(tagLeadingLabel);
		tagPanel.add(addTagBtn);
		
		
		//Button panel--------------------------------
		JPanel btnPanel = new JPanel();
		btnPanel.setBackground(new Color(0, 0, 0, 0));
		btnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
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
		
		btnPanel.add(viewBtn);
		btnPanel.add(downloadBtn);
		
		southPanel.add(tagPanel);
		southPanel.add(btnPanel);
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
		tagPanel.removeAll();
		tagPanel.add(tagLeadingLabel);
		
		tagLabels.clear();
		if (info.tags != null && info.tags.size() > 0) 
		{
			for (int i = 0; i < info.tags.size(); ++i)
			{
				TagLabel tagLabel = new TagLabel(info.tags.get(i));
				tagLabels.add(tagLabel);
				tagPanel.add(tagLabel);
			}
		}
		
		tagPanel.add(addTagBtn);
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
	
	
	/*--------------------------------
	Add a tag
	--------------------------------*/
	public void addTag(String tagStr)
	{
		TagLabel tagLabel = new TagLabel(tagStr);
		tagLabels.add(tagLabel);
		
		tagPanel.remove(addTagBtn);
		tagPanel.add(tagLabel);
		tagPanel.add(addTagBtn);
		tagPanel.revalidate();
		tagPanel.repaint();
	}
	
	
	/*--------------------------------
	Delete a tag
	--------------------------------*/
	public void deleteTag(String tagStr)
	{
		int idx = findTag(tagStr);
		if(idx > -1)
		{
			tagPanel.remove(tagLabels.get(idx));
			tagLabels.remove(idx);
			tagPanel.revalidate();
			tagPanel.repaint();
		}
	}
	
	
	/*--------------------------------
	Delete the selected tags
	--------------------------------*/
	public void deleteSelectedTag()
	{
		int idx = 0;
		
		while(idx != -1)
		{
			idx = -1;
			
			for(int i = 0; i < tagLabels.size(); ++i)
			{
				if(tagLabels.get(i).getSelected())
				{
					idx = i;
					break;
				}
			}
			tagPanel.remove(tagLabels.get(idx));
			tagLabels.remove(idx);
		}
		
		refresh(tagPanel);
	}
	
	
	/*--------------------------------
	Find a tag
	--------------------------------*/
	public int findTag(String tagStr)
	{
		for(int i = 0; i < tagLabels.size(); ++i)
		{
			if(tagLabels.get(i).getText().equals(tagStr))
				return i;
		}
	
		return -1;
	}
	
	
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
			Container ancestor = parent.getParent();
			while(ancestor != null)
			{
				parent = ancestor;
				ancestor = parent.getParent();
			}
			parent.revalidate();
			parent.repaint();
		}
	}
}