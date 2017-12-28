import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;


class ItemPanel extends ShadowPanel 
{
	private String id;
	private int heightOffset;
	private OperationManager opManager;

	
	public ItemPanel(PaperInfo info, OperationManager opm) 
	{
		super();
		id = info.id;
		opManager = opm;
		
		setBackground(new Color(20, 20, 20, 150));
		setLayout(new BorderLayout());

		// Add west panel
		JPanel westPanel = new JPanel();
		westPanel.setBackground(new Color(0, 0, 0, 0));
		westPanel.setLayout(new BorderLayout());

		JCheckBox checkBox = new JCheckBox();
		checkBox.setBackground(new Color(0, 0, 0, 0));
		westPanel.add(checkBox, BorderLayout.NORTH);

		// Add center panel
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(new Color(0, 0, 0, 0));
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

		JLabel titleLabel = new JLabel(
				"<html><font size='7' face='Verdana' color='white'>&nbsp; " + info.title + "</font></html>");

		String authors = "";
		for (int i = 0; i < info.authors.length - 1; ++i)
			authors += info.authors[i] + ", ";
		authors += info.authors[info.authors.length - 1];

		JLabel authorLabel = new JLabel(
				"<html><font size='5' face='Verdana' color='white'>&nbsp; Authors: " + authors + "</font></html>");

		JLabel dateLabel = new JLabel("<html><font size='5' face='Verdana' color='white'>&nbsp; Date: " + info.date[0]
				+ "/" + info.date[1] + "/" + info.date[2] + "</font></html>");

		heightOffset = info.abs.length() / 60 + 1;
		JLabel abstractLabel = new JLabel(
				"<html><font size='5' face='Verdana' color='white'>&nbsp; Abstract:\n" + info.abs + "</font></html>");

		String subs = "";
		for (int i = 0; i < info.subjects.length - 1; ++i)
			subs += info.subjects[i] + ", ";
		subs += info.subjects[info.subjects.length - 1];
		JLabel subjectLabel = new JLabel(
				"<html><font size='5' face='Verdana' color='white'>&nbsp; Subjects: " + subs + "</font></html>");

		String tags = "";
		if (info.tags != null && info.tags.length > 0) {
			for (int i = 0; i < info.tags.length - 1; ++i)
				tags += info.tags[i] + ", ";
			tags += info.tags[info.tags.length - 1];
		}
		JLabel tagLabel = new JLabel(
				"<html><font size='5' face='Verdana' color='white'>&nbsp; Tags: " + tags + "</font></html>");

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

		// Add south panel
		JPanel southPanel = new JPanel();
		southPanel.setBackground(new Color(0, 0, 0, 0));
		southPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		JButton viewBtn = new JButton("View");
		viewBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				opManager.view(id);
			}
		});
		southPanel.add(viewBtn);

		JButton downloadBtn = new JButton("Download");
		downloadBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				opManager.startDownload(id);
			}
		});
		southPanel.add(downloadBtn);

		add(westPanel, BorderLayout.WEST);
		add(centerPanel, BorderLayout.CENTER);
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