import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.List;
import java.util.ArrayList;


public class MainWindow
{
	private final String[] searchTypeOptions = {
		"Titles", "Authors", "Abstracts"
	};
	
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
	private OperationManager opManager;
	private int width;
	private int height;


	/*---------------------------------
	Constructor
	---------------------------------*/
	public MainWindow(OperationManager opm)
	{
		//Initialize parameters--------------------
		itemList = new ArrayList<ShadowPanel>();
		opManager = opm;
		opManager.setWindow(this);
		width = 1024;
		height = 768;
		layoutScale = 0.5f;
		
		//Create the main frame--------------------
		mainFrame = new JFrame();
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setTitle("arXiv-kokido");
		mainFrame.setSize(width, height);
		mainFrame.setIconImage((new ImageIcon("./kokido.png")).getImage());
		mainFrame.setLocationRelativeTo(null);
		
		//Create panels----------------------------
		createMenuBar();
		createInspectorPanel();
		createSearchPanel();
		createContentPanel();
		createStatePanel();
		createScalePanel();

		//Create the background panel--------------
		backPanel = new JPanel();
		windowLayout = new GridBagLayout();
		backPanel.setLayout(windowLayout);
		backPanel.setBackground(new Color(100, 100, 100, 255));
		
		//Set window layout------------------------
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
		mainFrame.setVisible(true);
	}
	
	
	/*---------------------------------
	Create the menu bar
	---------------------------------*/
	private void createMenuBar()
	{
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
				opManager.startSearch(searchText.getText(), 5);
			}
		});

		fileMenu.add(exitMenuItem);
		toolMenu.add(searchMenuItem);
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(toolMenu);
		mainFrame.setJMenuBar(menuBar);
	}
	
	
	/*---------------------------------
	Create the inspector panel
	---------------------------------*/
	private void createInspectorPanel()
	{
		inspectorPanel = new ShadowPanel();
		inspectorPanel.setBackground(new Color(200, 200, 200, 200));
	}
	
	
	/*---------------------------------
	Create the search panel
	---------------------------------*/
	private void createSearchPanel()
	{
		searchPanel = new ShadowPanel();
		searchPanel.setBackground(new Color(50, 50, 50, 200));
		searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

		//Search text
		searchText = new JTextField(24);
		searchPanel.add(searchText);

		//Search type label
		JLabel searchTypeLabel = new JLabel(
			"<html><font size='3' face='Verdana' color='white'>Type:</font></html>"
		);
		searchPanel.add(searchTypeLabel);

		//Search type combo box
		JComboBox<String> searchTypeComboBox = new JComboBox<String>(searchTypeOptions);
		searchPanel.add(searchTypeComboBox);

		//Search button
		JButton searchBtn = new JButton("Search");
		searchBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				opManager.startSearch(searchText.getText(), 5);
			}
		});
		searchPanel.add(searchBtn);
	}
	
	
	/*---------------------------------
	Create the content panel
	---------------------------------*/
	private void createContentPanel()
	{
		contentPanel = new ShadowPanel();
		contentPanel.setBackground(new Color(150, 150, 150, 150));
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

		contentScrollPane = new JScrollPane(contentPanel);
		contentScrollPane.setBackground(new Color(0, 0, 0, 0));
		contentScrollPane.getViewport().setBackground(new Color(64, 64, 64, 255));
	}
	
	
	/*---------------------------------
	Create the state panel
	---------------------------------*/
	private void createStatePanel()
	{
		statPanel = new JPanel();
		statPanel.setBackground(new Color(200, 200, 200, 255));
	}
	
	
	/*---------------------------------
	Create the scale panel
	---------------------------------*/
	private void createScalePanel()
	{
		scalePanel = new JPanel();
		scalePanel.setBackground(new Color(0, 0, 0, 0));
		scalePanel.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
		scalePanel.addMouseListener(new MouseAdapter()
		{
			private Timer timer = null;
			private int prevX = 0;

			//Mouse pressed event---------------------
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

			//Mouse released event---------------------
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
		ItemPanel item = new ItemPanel(info, opManager);
		item.setShape((int)((float)width * 0.75));
		itemList.add(item);

		int height = 100;
		for(ShadowPanel i : itemList)
			height += i.getPreferredSize().getHeight();

		contentPanel.add(item);
		contentPanel.setMinimumSize(new Dimension(100, height));
		contentPanel.setMaximumSize(new Dimension(2048, height));
		contentPanel.setPreferredSize(new Dimension((int)((float)width * 0.75), height));
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
}