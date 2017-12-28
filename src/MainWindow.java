import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;


public class MainWindow
{
	private GridBagLayout windowLayout;
	private JFrame mainFrame;
	private JPanel backPanel;
	private JPanel inspectorPanel;
	private JPanel searchPanel;
	private JScrollPane contentScrollPane;
	private JPanel contentPanel;
	private JPanel statPanel;
	private JPanel scalePanel;
	private float layoutScale;
	private JTextField searchText;
	private List<ShadowPanel> itemList;


	/*---------------------------------
	Constructor
	---------------------------------*/
	public MainWindow()
	{
		itemList = new ArrayList<ShadowPanel>();

		//Create the main frame--------------------
		mainFrame = new JFrame();
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setTitle("arXiv-kokido");
		mainFrame.setSize(1024, 768);
		mainFrame.setIconImage((new ImageIcon("./kokido.png")).getImage());
		mainFrame.setLocationRelativeTo(null);
		

		//Create a menu bar------------------------
		final JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenu editMenu = new JMenu("Edit");
		JMenu toolMenu = new JMenu("Tools");


		//MenuItem: Exit---------------------------
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				System.exit(0);
			}
		});


		//MenuItem: Search--------------------------
		JMenuItem searchMenuItem = new JMenuItem("Search");
		searchMenuItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				Thread t1 = new searchThread(5);
		        t1.start();
			}
		});

		fileMenu.add(exitMenuItem);
		toolMenu.add(searchMenuItem);
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(toolMenu);


		//Create the inspector panel--------------------------------
		inspectorPanel = new ShadowPanel();
		inspectorPanel.setBackground(new Color(200, 200, 200, 200));


		//Create the search panel-----------------------------------
		searchPanel = new ShadowPanel();
		searchPanel.setBackground(new Color(50, 50, 50, 200));
		searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

		searchText = new JTextField(24);
		searchPanel.add(searchText);

		JLabel searchTypeLabel = new JLabel(
			"<html><font size='3' face='Verdana' color='white'>Type:</font></html>"
		);
		searchPanel.add(searchTypeLabel);

		String[] searchTypeOptions = {
			"most recent", "top recent", "top hype"
		};
		JComboBox searchTypeComboBox = new JComboBox(searchTypeOptions);
		searchPanel.add(searchTypeComboBox);

		JButton searchBtn = new JButton("Search");
		searchBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				Thread t1 = new searchThread(5);
		        t1.start();
			}
		});
		searchPanel.add(searchBtn);


		//Create the content panel-------------------------
		contentPanel = new ShadowPanel();
		contentPanel.setBackground(new Color(150, 150, 150, 150));
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

		contentScrollPane = new JScrollPane(contentPanel);
		contentScrollPane.setBackground(new Color(0, 0, 0, 0));
		contentScrollPane.getViewport().setBackground(new Color(64, 64, 64, 255));


		//Create the state panel----------------------------------
		statPanel = new JPanel();
		statPanel.setBackground(new Color(200, 200, 200, 255));


		//Create the scale panel----------------------------------
		scalePanel = new JPanel();
		scalePanel.setBackground(new Color(0, 0, 0, 0));
		scalePanel.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
		scalePanel.addMouseListener(new MouseAdapter()
		{
			private Timer timer = null;
			private int prevX = 0;

			public void mousePressed(MouseEvent me)
			{
				backPanel.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));

				if(timer == null)
				{
					timer = new Timer();
					prevX = MouseInfo.getPointerInfo().getLocation().x;
				}

				timer.schedule(new TimerTask()
				{
					public void run()
					{
						int curX = MouseInfo.getPointerInfo().getLocation().x;
						setLayoutScale((float)(prevX - curX) / 800);
						prevX = curX;
					}
				}, 0, 50);
			}

			public void mouseReleased(MouseEvent me)
			{
				backPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

				if(timer != null)
				{
					timer.cancel();
					timer = null;
				}
			}
		});


		//Create the background panel-----------------------------
		backPanel = new JPanel();
		windowLayout = new GridBagLayout();
		backPanel.setLayout(windowLayout);
		backPanel.setBackground(new Color(100, 100, 100, 255));

		layoutScale = 0.5f;
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1 - layoutScale;
		gbc.weighty = 0.98;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = 2;
		backPanel.add(inspectorPanel, gbc);

		gbc.weightx = 0;
		gbc.weighty = 0.98;
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridheight = 2;
		backPanel.add(scalePanel, gbc);

		gbc.weightx = layoutScale;
		gbc.weighty = 0.02;
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		backPanel.add(searchPanel, gbc);

		gbc.weightx = layoutScale;
		gbc.weighty = 0.96;
		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.gridheight = 1;
		backPanel.add(contentScrollPane, gbc);

		gbc.weightx = 1.0;
		gbc.weighty = 0.02;
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 3;
		backPanel.add(statPanel, gbc);

		mainFrame.add(backPanel);
		mainFrame.setJMenuBar(menuBar);
		mainFrame.setVisible(true);
	}


	/*---------------------------------
	Set layout scale
	---------------------------------*/
	public void setLayoutScale(float deltaScale)
	{
		layoutScale += deltaScale;
		if(layoutScale < 0) layoutScale = 0;
		if(layoutScale > 1) layoutScale = 1;

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1 - layoutScale;
		gbc.weighty = 0.98;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = 2;
		windowLayout.setConstraints(inspectorPanel, gbc);

		gbc.weightx = layoutScale;
		gbc.weighty = 0.02;
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		windowLayout.setConstraints(searchPanel, gbc);

		gbc.weightx = layoutScale;
		gbc.weighty = 0.96;
		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.gridheight = 1;
		windowLayout.setConstraints(contentScrollPane, gbc);

		backPanel.setLayout(windowLayout);
		backPanel.revalidate();
		backPanel.repaint();
	}


	/*---------------------------------
	Add an item
	---------------------------------*/
	public void addItem(PaperInfo info)
	{
		ItemPanel item = new ItemPanel(info);
		item.setId(info.id);
		item.setShape(768);
		itemList.add(item);

		int height = 100;
		for(ShadowPanel i : itemList)
			height += i.getPreferredSize().getHeight();

		contentPanel.add(item);
		contentPanel.setMinimumSize(new Dimension(100, height));
		contentPanel.setMaximumSize(new Dimension(2048, height));
		contentPanel.setPreferredSize(new Dimension(768, height));
		contentScrollPane.updateUI();
	}
	
	
	/*---------------------------------
	clear all item
	---------------------------------*/
	public void clearItem()
	{
		itemList.clear();
		contentPanel.removeAll();
		contentPanel.setPreferredSize(new Dimension(100, 100));
		contentPanel.revalidate();
		contentPanel.repaint();
		contentScrollPane.updateUI();
	}
	
	
	/*---------------------------------
	Search items
	---------------------------------*/
	public void search(int total) 
	{
		String str = searchText.getText();
		String[] id_list = ArxivParser.SearchResult(ArxivParser.BuildSearchURL(str, 10));
		if(total < id_list.length)
			id_list = Arrays.copyOfRange(id_list, 0, total);

		for(String id : id_list) {
			PaperInfo info_temp = ArxivParser.GetPaperInfo(ArxivParser.BuildURL(id, "abs"));
			addItem(info_temp);
		}
	}
	
	
	class searchThread extends Thread 
	{
		int total;
		public searchThread(int t) {
			total = t;
		}
	    public void run(){
	    	search(total);
	    }
	}
	
}