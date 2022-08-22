package application;
	
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.Element;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import javax.swing.border.Border;
import javax.swing.BorderFactory;

import javax.swing.UIManager;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.Color;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.awt.event.WindowAdapter;

public final class Main extends JFrame implements ActionListener, ItemListener, WindowStateListener {
	static UIManager UIManager = new UIManager();
	JFileChooser fileChooser_Java = new JFileChooser(System.getProperty("user.dir"));
	JFileChooser fileChooser_CSharp = new JFileChooser(System.getProperty("user.dir"));
	
	// JPanel mainPanel1 = new JPanel(new GridLayout(1, 2));	//For the Node and Segment Lists (And Now, Search Bar)
	JSplitPane mainPanel1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    // JPanel sub1PanelLeft = new JPanel();
    // sub1PanelLeft.setLayout(new BoxLayout(sub1PanelLeft, BoxLayout.Y_AXIS));
    // JSplitPane sub1PanelRight = new JSplitPane(JSplitPane.VERTICAL_SPLIT); 
    
    // JPanel sub2PanelLeft = new JPanel(new GridLayout(1, 2));
    JSplitPane sub2PanelLeft = new JSplitPane(JSplitPane.VERTICAL_SPLIT); // Holds the Sub 3s
    JSplitPane sub3PanelLeft1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT); // Holds TextArea and Char Count
    JSplitPane sub3PanelLeft2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT); // Holds Label and Buttons
    JSplitPane sub2PanelRight = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    JSplitPane sub3PanelRight1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    JSplitPane sub3PanelRight2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT); 
    
    JPanel buttonPanelLeft = new JPanel();
    JPanel buttonPanelRight = new JPanel();
    JPanel labelPanelLeft = new JPanel();
    JPanel labelPanelRight = new JPanel(); 
    
    JPanel subP_JavaTextArea = new JPanel();
    JPanel subP_CSharpTextArea = new JPanel();
    JPanel subP_LeftButtons = new JPanel();
    JPanel subP_RightButtons = new JPanel();
    
    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("File");
	JMenuItem fileOpen = new JMenuItem("Open");
	JMenuItem fileSaveJava = new JMenuItem("Save Java File");	
	JMenuItem fileSaveCSharp = new JMenuItem("Save CSharp File");
	JMenuItem fileConfig = new JMenuItem("Configure");
	
	JLabel label_Java = new JLabel("Java");
	JTextArea textArea_Java = new JTextArea(12, 27);
	JTextArea margin_Java = new JTextArea("1");
	JTextField textField_Java = new JTextField("Lines: ; Characters: ", 27);
	JScrollPane jsp_Java = new JScrollPane();
	JButton button_JavaSave = new JButton("Save");
	JButton button_JavaLoad = new JButton("Load");
	
	JLabel label_CSharp = new JLabel("C#");
	JTextArea textArea_CSharp = new JTextArea(12, 27);
	JTextArea margin_CSharp = new JTextArea("1");
	JTextField textField_CSharp = new JTextField("Lines: ; Characters: ", 27);
	JScrollPane jsp_CSharp = new JScrollPane();
	JButton button_CSharpTrans = new JButton("Translate"); // Translate Java to C#
	JButton button_CSharpSave = new JButton("Save");
	
	Config configMenu;
	
	public Main() {
		setTitle("Java to C# Cross-Compiler");
		setSize(650,540);
		//setResizable(false);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	    addWindowListener(new WindowAdapter() {
	    	@Override
	    	public void windowClosing(WindowEvent e) {
	    		if(JOptionPane.showConfirmDialog(null, "Would you like to close the program?", "Closing Cross-Compiler", 0, 0, null)==0) {
	    			setVisible(false);
	    			dispose();
					System.exit(0);
	    		}
	    	}	    	
	    });
	    
	    fileChooser_Java.setFileFilter(new FileNameExtensionFilter("Java File (.java)", "java"));
		fileChooser_Java.setMultiSelectionEnabled(false);
		
	    fileChooser_CSharp.setFileFilter(new FileNameExtensionFilter("C# File (.cs)", "cs"));
		fileChooser_CSharp.setMultiSelectionEnabled(false);
	    
	    label_Java.setFont(new Font("Calibri", Font.PLAIN, 40));
	    label_CSharp.setFont(new Font("Calibri", Font.PLAIN, 40));
	    
	    // --
	    
	    configMenu = new Config(this);
	    
	    // --
	    
	    button_JavaLoad.addActionListener(this);
	    button_JavaLoad.setActionCommand("LoadJava");
	    
	    button_JavaSave.addActionListener(this);
	    button_JavaSave.setActionCommand("SaveJava");
	    
	    button_CSharpSave.addActionListener(this);
	    button_CSharpSave.setActionCommand("SaveCSharp");
	    
	    button_CSharpTrans.addActionListener(this);
	    button_CSharpTrans.setActionCommand("Translate");
	    
	    //list_SegSelect.addListSelectionListener(listSeleMod_SegSelect);
	    
		textArea_Java.getDocument().addDocumentListener(new DocumentListener() {
			public String getText() {
				int docLength = textArea_Java.getDocument().getLength();
				Element root = textArea_Java.getDocument().getDefaultRootElement();
				String text = "1" + System.getProperty("line.separator");
				for (int i=2;i<root.getElementIndex(docLength)+2;i++) {
					text += i + System.getProperty("line.separator");
				}
				return text;
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				updateCharCount('J');
				margin_Java.setText(getText());
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				updateCharCount('J');	
				margin_Java.setText(getText());
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				updateCharCount('J');	
				margin_Java.setText(getText());
			}
		});		
		jsp_Java.getViewport().add(textArea_Java);
		jsp_Java.setRowHeaderView(margin_Java);
		
		textArea_CSharp.getDocument().addDocumentListener(new DocumentListener() {
			public String getText() {
				int docLength = textArea_CSharp.getDocument().getLength();
				Element root = textArea_CSharp.getDocument().getDefaultRootElement();
				String text = "1" + System.getProperty("line.separator");
				for (int i=2;i<root.getElementIndex(docLength)+2;i++) {
					text += i + System.getProperty("line.separator");
				}
				return text;
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				updateCharCount('C');
				margin_CSharp.setText(getText());
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				updateCharCount('C');	
				margin_CSharp.setText(getText());
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				updateCharCount('C');	
				margin_CSharp.setText(getText());
			}
		});
		jsp_CSharp.getViewport().add(textArea_CSharp);
		jsp_CSharp.setRowHeaderView(margin_CSharp);
	    
	    // --
	    
	    mainPanel1.resetToPreferredSizes();
	    mainPanel1.setDividerLocation(325);
	    mainPanel1.setDividerSize(-1);
	    
	    sub2PanelLeft.resetToPreferredSizes();
	    sub2PanelLeft.setDividerLocation(460);
	    sub2PanelLeft.setDividerSize(-1);
	    
	    sub3PanelLeft1.resetToPreferredSizes();
	    sub3PanelLeft1.setDividerLocation(440);
	    sub3PanelLeft1.setDividerSize(-1);
	    
	    sub3PanelLeft2.resetToPreferredSizes();
	    sub3PanelLeft2.setDividerLocation(90);
	    sub3PanelLeft2.setDividerSize(-1);
	    
	    sub2PanelRight.resetToPreferredSizes();
	    sub2PanelRight.setDividerLocation(460);
	    sub2PanelRight.setDividerSize(-1);
	    
	    sub3PanelRight1.resetToPreferredSizes();
	    sub3PanelRight1.setDividerLocation(440);
	    sub3PanelRight1.setDividerSize(-1);
	    
	    sub3PanelRight2.resetToPreferredSizes();
	    sub3PanelRight2.setDividerLocation(90);
	    sub3PanelRight2.setDividerSize(-1);
	    
	    textArea_CSharp.setEditable(false);
	    margin_Java.setEditable(false);
	    margin_CSharp.setEditable(false);
	    textField_Java.setEditable(false);
	    textField_CSharp.setEditable(false);
	    
	    // --
	    
	    fileMenu.add(fileOpen);
	    fileOpen.addActionListener(this);
	    fileOpen.setActionCommand("LoadJava");
	    fileMenu.add(fileSaveJava);
	    fileSaveJava.addActionListener(this);
	    fileSaveJava.setActionCommand("SaveJava");	    
	    fileMenu.add(fileSaveCSharp);
	    fileSaveCSharp.addActionListener(this);
	    fileSaveCSharp.setActionCommand("SaveCSharp");    
	    fileMenu.add(fileConfig);
	    fileConfig.addActionListener(this);
	    fileConfig.setActionCommand("OpenConfig");  
	    menuBar.add(fileMenu);
	    
	    
	    // --
	    
	    buttonPanelLeft.add(button_JavaSave);
	    buttonPanelLeft.add(button_JavaLoad);
	    
	    labelPanelLeft.add(label_Java);
	    
	    sub3PanelLeft1.add(jsp_Java);
	    sub3PanelLeft1.add(textField_Java);
	    sub3PanelLeft2.add(labelPanelLeft);
	    sub3PanelLeft2.add(buttonPanelLeft);
	    
	    sub2PanelLeft.add(sub3PanelLeft1);
	    sub2PanelLeft.add(sub3PanelLeft2);
	    
	    // --
	    
	    buttonPanelRight.add(button_CSharpTrans);
	    buttonPanelRight.add(button_CSharpSave);
	    
	    labelPanelRight.add(label_CSharp);
	    
	    sub3PanelRight1.add(jsp_CSharp);
	    sub3PanelRight1.add(textField_CSharp);
	    sub3PanelRight2.add(labelPanelRight);
	    sub3PanelRight2.add(buttonPanelRight);
	    
	    sub2PanelRight.add(sub3PanelRight1);
	    sub2PanelRight.add(sub3PanelRight2);
	    
	    // --
	    
	    mainPanel1.add(sub2PanelLeft);
	    mainPanel1.add(sub2PanelRight);
	    
	    colorLIGHT();
	    
		setJMenuBar(menuBar);
	    add(mainPanel1);
	    addComponentListener(new ComponentAdapter() {
	    	public void componentResized(ComponentEvent e) {
	    		resizeComponents(getSize().getHeight(), getSize().getWidth());	    	    
	    	}
	    });
	    addWindowStateListener(this);
		setVisible(true);
	}
	
	//Checks Actions
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		int returnVal;
			if (cmd.equals("LoadJava")) {
	            returnVal = fileChooser_Java.showOpenDialog(this);
	            if (returnVal==0) {
	            	String currentFile = fileChooser_Java.getSelectedFile().getAbsolutePath();
	            	String fetchedCode = getTextFrom("", currentFile);
	            	if (fetchedCode != null) {
	            		textArea_Java.setText(fetchedCode);
	            	}	            	
	            }
			} else if (cmd.equals("SaveJava")) {			
				returnVal = fileChooser_Java.showSaveDialog(this);
	        	if (returnVal==0) {
	        		String currentFile = fileChooser_Java.getSelectedFile().getAbsolutePath();
	        		String fileName = fileChooser_Java.getSelectedFile().getName();
	            	String extension = currentFile.substring(currentFile.length()-5, currentFile.length());
	            	if (!(extension.equals(".java"))) {
	            		currentFile += ".java";
	            		fileName += ".java";
	            	}
	            	writeTextTo(textArea_Java.getText(), currentFile);
	        	}
			} else if (cmd.equals("SaveCSharp")) {				
				returnVal = fileChooser_CSharp.showSaveDialog(this);
	        	if (returnVal==0) {
	        		String currentFile = fileChooser_CSharp.getSelectedFile().getAbsolutePath();
	        		String fileName = fileChooser_CSharp.getSelectedFile().getName();
	            	String extension = currentFile.substring(currentFile.length()-3, currentFile.length());
	            	if (!(extension.equals(".cs"))) {
	            		currentFile += ".cs";
	            		fileName += ".cs";
	            	}
	            	writeTextTo(textArea_CSharp.getText(), currentFile);
	        	}
			} else if (cmd.equals("Translate")) {
				translate(textArea_Java, textArea_CSharp);				
			} else if (cmd.equals("UpdateSize")) {
				resizeComponents(getSize().getHeight(), getSize().getWidth());
			} else if (cmd.equals("OpenConfig")) {
				configMenu.setVisible(false);
	    		configMenu.setVisible(true);
			}
	}
	
	// Checks All State Changes
	public void itemStateChanged(ItemEvent e) {
		
	}
	
	/*
	@Override
	public void windowStateChanged(WindowEvent e) {		
		System.out.println("aaaa");
		resizeComponents();
	}
	
	@Override
	public void windowOpened(WindowEvent e) {		
		System.out.println("opened");
		resizeComponents();
	}
	
	@Override
	public void windowClosing(WindowEvent e) {		
		System.out.println("closing");
		resizeComponents();
	}
	
	@Override
	public void windowClosed(WindowEvent e) {		
		System.out.println("closed");
		resizeComponents();
	}
	
	@Override
	public void windowActivated(WindowEvent e) {		
		System.out.println("activated");
		resizeComponents();
	}
	
	@Override
	public void windowDeactivated(WindowEvent e) {		
		System.out.println("deactivated");
		resizeComponents();
	}
	
	@Override
	public void windowIconified(WindowEvent e) {		
		System.out.println("iconified");
		resizeComponents();
	}
	
	@Override
	public void windowDeiconified(WindowEvent e) {		
		System.out.println("deiconified");
		resizeComponents();
	}
	*/
	
	@Override
	public void windowStateChanged(WindowEvent e) {				
		Timer count = new Timer(50, this);
		count.setActionCommand("UpdateSize");
		count.setRepeats(false);
		count.start();
	}
	
	private void resizeComponents(double height, double width) {
		int nWidth = (int) (width * .5);
		int nHeight1 = (int) (height - 190);
		int nHeight2 = (int) (nHeight1 - 20);
		
	    mainPanel1.setDividerLocation(nWidth);	    	    
	    sub2PanelLeft.setDividerLocation(nHeight1);	    	    
	    sub3PanelLeft1.setDividerLocation(nHeight2);	    	     	    
	    sub2PanelRight.setDividerLocation(nHeight1);	    	    
	    sub3PanelRight1.setDividerLocation(nHeight2);
	}
	
	private void updateCharCount(char key) {
		if (key == 'J') {
			int charCount = 0;
			int lineCount = 1;
			String text = textArea_Java.getText();
			
			for (int i=0;i<text.length();i++) {
				if (text.substring(i, i+1).equals("\n")) {
					lineCount++;
					charCount--;
				}
				charCount++;
			}
			
			textField_Java.setText("Lines: " + lineCount + "; Characters: " + charCount);
		} else if (key == 'C') {
			int charCount = 0;
			int lineCount = 1;
			String text = textArea_CSharp.getText();
			
			for (int i=0;i<text.length();i++) {
				if (text.substring(i, i+1).equals("\n")) {
					lineCount++;
					charCount--;
				}
				charCount++;
			}
			
			textField_CSharp.setText("Lines: " + lineCount + "; Characters: " + charCount);
		}
	}
	
	public void colorLIGHT() {
		Color backgroundCol = new Color(240, 240, 240);
		Color textAreaLCol = new Color(255, 255, 255);
		Color textAreaRCol = new Color(210, 210, 210);
		Color marginCol = new Color(190, 190, 190);
		Color textCol = new Color(0, 0, 0);
		Color buttonCol = new Color(103, 108, 119);
		Color textButtonCol = new Color(0, 0, 0);
		
		this.getContentPane().setForeground(backgroundCol);
		this.getContentPane().setBackground(backgroundCol);
		
		mainPanel1.setForeground(backgroundCol);
		mainPanel1.setBackground(backgroundCol);
		
		label_Java.setBackground(textCol);
		label_Java.setForeground(textCol);
		
		textField_Java.setForeground(textCol);
		textField_Java.setBackground(backgroundCol);
		
		button_JavaSave.setForeground(textButtonCol);
		button_JavaSave.setBackground(buttonCol);
		button_JavaLoad.setForeground(textButtonCol);
		button_JavaLoad.setBackground(buttonCol);
		
		buttonPanelLeft.setForeground(backgroundCol);
		buttonPanelLeft.setBackground(backgroundCol);
		
		labelPanelLeft.setForeground(backgroundCol);
		labelPanelLeft.setBackground(backgroundCol);
		
		sub2PanelLeft.setForeground(backgroundCol);
		sub2PanelLeft.setBackground(backgroundCol);
		sub3PanelLeft1.setForeground(backgroundCol);
		sub3PanelLeft1.setBackground(backgroundCol);
		sub3PanelLeft2.setForeground(backgroundCol);
		sub3PanelLeft2.setBackground(backgroundCol);
		
		margin_Java.setBackground(marginCol);
		textArea_Java.setForeground(textCol);
		textArea_Java.setBackground(textAreaLCol);		
		// ---------------------------------------------------------------------
		label_CSharp.setBackground(textCol);
		label_CSharp.setForeground(textCol);
		
		textField_CSharp.setForeground(textCol);
		textField_CSharp.setBackground(backgroundCol);
		
		button_CSharpSave.setForeground(textButtonCol);
		button_CSharpSave.setBackground(buttonCol);
		button_CSharpTrans.setForeground(textButtonCol);
		button_CSharpTrans.setBackground(buttonCol);
		
		buttonPanelRight.setForeground(backgroundCol);
		buttonPanelRight.setBackground(backgroundCol);
		
		labelPanelRight.setForeground(backgroundCol);
		labelPanelRight.setBackground(backgroundCol);
		
		sub2PanelRight.setForeground(backgroundCol);
		sub2PanelRight.setBackground(backgroundCol);
		sub3PanelRight1.setForeground(backgroundCol);
		sub3PanelRight1.setBackground(backgroundCol);
		sub3PanelRight2.setForeground(backgroundCol);
		sub3PanelRight2.setBackground(backgroundCol);
		
		margin_CSharp.setBackground(marginCol);
		textArea_CSharp.setForeground(textCol);
		textArea_CSharp.setBackground(textAreaRCol);	
	}
	
	public void colorDARK() {
		Color backgroundCol = new Color(54, 57, 63);
		Color textAreaLCol = new Color(64, 68, 75);
		Color textAreaRCol = new Color(107, 110, 105);
		Color marginCol = new Color(190, 190, 190);
		Color textCol = new Color(240, 240, 255);
		Color buttonCol = new Color(103, 108, 119);
		Color textButtonCol = new Color(0, 0, 0);
		
		this.getContentPane().setForeground(backgroundCol);
		this.getContentPane().setBackground(backgroundCol);
		
		mainPanel1.setForeground(backgroundCol);
		mainPanel1.setBackground(backgroundCol);
		
		label_Java.setBackground(textCol);
		label_Java.setForeground(textCol);
		
		textField_Java.setForeground(textCol);
		textField_Java.setBackground(backgroundCol);
		
		button_JavaSave.setForeground(textButtonCol);
		button_JavaSave.setBackground(buttonCol);
		button_JavaLoad.setForeground(textButtonCol);
		button_JavaLoad.setBackground(buttonCol);
		
		buttonPanelLeft.setForeground(backgroundCol);
		buttonPanelLeft.setBackground(backgroundCol);
		
		labelPanelLeft.setForeground(backgroundCol);
		labelPanelLeft.setBackground(backgroundCol);
		
		sub2PanelLeft.setForeground(backgroundCol);
		sub2PanelLeft.setBackground(backgroundCol);
		sub3PanelLeft1.setForeground(backgroundCol);
		sub3PanelLeft1.setBackground(backgroundCol);
		sub3PanelLeft2.setForeground(backgroundCol);
		sub3PanelLeft2.setBackground(backgroundCol);
		
		margin_Java.setBackground(marginCol);
		textArea_Java.setForeground(textCol);
		textArea_Java.setBackground(textAreaLCol);		
		// ---------------------------------------------------------------------
		label_CSharp.setBackground(textCol);
		label_CSharp.setForeground(textCol);
		
		textField_CSharp.setForeground(textCol);
		textField_CSharp.setBackground(backgroundCol);
		
		button_CSharpSave.setForeground(textButtonCol);
		button_CSharpSave.setBackground(buttonCol);
		button_CSharpTrans.setForeground(textButtonCol);
		button_CSharpTrans.setBackground(buttonCol);
		
		buttonPanelRight.setForeground(backgroundCol);
		buttonPanelRight.setBackground(backgroundCol);
		
		labelPanelRight.setForeground(backgroundCol);
		labelPanelRight.setBackground(backgroundCol);
		
		sub2PanelRight.setForeground(backgroundCol);
		sub2PanelRight.setBackground(backgroundCol);
		sub3PanelRight1.setForeground(backgroundCol);
		sub3PanelRight1.setBackground(backgroundCol);
		sub3PanelRight2.setForeground(backgroundCol);
		sub3PanelRight2.setBackground(backgroundCol);
		
		margin_CSharp.setBackground(marginCol);
		textArea_CSharp.setForeground(textCol);
		textArea_CSharp.setBackground(textAreaRCol);	
	}
	
	public void colorNIGHT() {
		Color backgroundCol = new Color(54, 57, 63);
		Color textAreaLCol = new Color(64, 68, 75);
		Color textAreaRCol = new Color(107, 110, 105);
		Color marginCol = new Color(190, 190, 190);
		Color textCol = new Color(255, 131, 48);
		Color buttonCol = new Color(103, 108, 119);
		Color textButtonCol = new Color(0, 0, 0);
		
		this.getContentPane().setForeground(backgroundCol);
		this.getContentPane().setBackground(backgroundCol);
		
		mainPanel1.setForeground(backgroundCol);
		mainPanel1.setBackground(backgroundCol);
		
		label_Java.setBackground(textCol);
		label_Java.setForeground(textCol);
		
		textField_Java.setForeground(textCol);
		textField_Java.setBackground(backgroundCol);
		
		button_JavaSave.setForeground(textButtonCol);
		button_JavaSave.setBackground(buttonCol);
		button_JavaLoad.setForeground(textButtonCol);
		button_JavaLoad.setBackground(buttonCol);
		
		buttonPanelLeft.setForeground(backgroundCol);
		buttonPanelLeft.setBackground(backgroundCol);
		
		labelPanelLeft.setForeground(backgroundCol);
		labelPanelLeft.setBackground(backgroundCol);
		
		sub2PanelLeft.setForeground(backgroundCol);
		sub2PanelLeft.setBackground(backgroundCol);
		sub3PanelLeft1.setForeground(backgroundCol);
		sub3PanelLeft1.setBackground(backgroundCol);
		sub3PanelLeft2.setForeground(backgroundCol);
		sub3PanelLeft2.setBackground(backgroundCol);
		
		margin_Java.setBackground(marginCol);
		textArea_Java.setForeground(textCol);
		textArea_Java.setBackground(textAreaLCol);		
		// ---------------------------------------------------------------------
		label_CSharp.setBackground(textCol);
		label_CSharp.setForeground(textCol);
		
		textField_CSharp.setForeground(textCol);
		textField_CSharp.setBackground(backgroundCol);
		
		button_CSharpSave.setForeground(textButtonCol);
		button_CSharpSave.setBackground(buttonCol);
		button_CSharpTrans.setForeground(textButtonCol);
		button_CSharpTrans.setBackground(buttonCol);
		
		buttonPanelRight.setForeground(backgroundCol);
		buttonPanelRight.setBackground(backgroundCol);
		
		labelPanelRight.setForeground(backgroundCol);
		labelPanelRight.setBackground(backgroundCol);
		
		sub2PanelRight.setForeground(backgroundCol);
		sub2PanelRight.setBackground(backgroundCol);
		sub3PanelRight1.setForeground(backgroundCol);
		sub3PanelRight1.setBackground(backgroundCol);
		sub3PanelRight2.setForeground(backgroundCol);
		sub3PanelRight2.setBackground(backgroundCol);
		
		margin_CSharp.setBackground(marginCol);
		textArea_CSharp.setForeground(textCol);
		textArea_CSharp.setBackground(textAreaLCol);	
	}
	
	// Starts-Up the App	
	public static void main(String args[]) {
		int fontSize = 12;
		String[] keys = new String[]{"Label.font",
									 "TextField.font",
									 "TextArea.font",
									 "Button.font",
									 "CheckBox.font",
									 "ComboBox.font",
									 "MenuBar.font",
									 "Menu.font",
									 "MenuItem.font",
									 "ScrollPane.font",
									 "Frame.font",
									 "Panel.font",
									 "OptionPane.font",
									 "FileChooser.font",
									 "CheckBox.font",
									 "CheckBoxMenuItem.font",
									 "List.font",
									 "SplitPane.font",
									 "TabbedPane.font"};
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			for (int i=0;i<keys.length;i++) {
				UIManager.put(keys[i], new Font("Sans_Serif",Font.PLAIN,fontSize));
			}        
		} catch(Exception e) {}
		Main app = new Main();
	}
	
	public String getTextFrom(String header, String loca) {
		String newText = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(loca));
			String s;
			while ((s = br.readLine()) != null) {
				newText += s + "\n";
			}
			if ((newText.length()<header.length())||(!newText.substring(0, header.length()).equals(header))) {
				//System.out.println("Header Mismatch in " + loca);
				newText = null;
			}
			br.close();
		} catch(Exception e) {
			//System.out.println("Error in " + loca);
			newText = null;
		}
		return(newText);
	}
	
	public void writeTextTo(String text, String loca) {
		System.out.println("Starting Write");
		try {
			FileWriter fr = new FileWriter(loca);
			fr.write(text);
			fr.close();
		} catch(Exception e) {
			System.out.println("Error");
		}
	}
	
	@SuppressWarnings("deprecation")
	static public boolean translate(JTextArea ta, JTextArea tc) {
		System.out.println("Starting Translation");
		boolean success = false;
		
		String content = ta.getText();
		
		String translation = "";
		
		StringBuilder sb = new StringBuilder();
        
        ANTLRInputStream input = new ANTLRInputStream(content);
        
        antlr.JavaLexer lexer = new antlr.JavaLexer(input);
        
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        
        antlr.JavaParser parser = new antlr.JavaParser(tokens);
        
        //antlr.JavaTranslator translator = new antlr.JavaTranslator();
        
        antlr.JavaListener listener = new antlr.JavaListener();
        for(int i=0;i<listener.tokens.size();i++) {
        	System.out.println(listener.tokens.get(i));
        }
        
        ParseTree tree = parser.compilationUnit();
        
        //System.out.println(tree.toStringTree(parser));
        ParseTreeWalker.DEFAULT.walk(listener, tree);
        int indent = 0, forDepth = 0;
        boolean forLoop = false;
        boolean arrayInit = false;
        for(int i=0;i<listener.tokens.size();i++) {
        	System.out.println(listener.tokens.get(i));
        	if((listener.tokens.get(i).contentEquals("=")
        			||listener.tokens.get(i).contentEquals("+")
        			||listener.tokens.get(i).contentEquals("-")
        			||listener.tokens.get(i).contentEquals("*")
        			||listener.tokens.get(i).contentEquals("/")
        			||listener.tokens.get(i).contentEquals(";")
        			||listener.tokens.get(i).contentEquals(")"))
        			&&listener.tokens.get(i-2).contentEquals("]")
        			&&!listener.tokens.get(i-4).contentEquals("int")
        			&&!listener.tokens.get(i-4).contentEquals("String")
        			&&!listener.tokens.get(i-4).contentEquals("double")
        			&&!listener.tokens.get(i-4).contentEquals("float")
        			&&!listener.tokens.get(i-4).contentEquals("boolean")
        			&&!listener.tokens.get(i-4).contentEquals("short")
        			&&!listener.tokens.get(i-4).contentEquals("long")
        			&&!listener.tokens.get(i-4).contentEquals("char")) {
        		Collections.swap(listener.tokens,i-2 , i-1);
        	}if(listener.tokens.get(i).contentEquals(",")&&
        			listener.tokens.get(i+1).contentEquals("}")) {
        		listener.tokens.remove(i);
        	}if(listener.tokens.get(i).contentEquals("]")
        			&&listener.tokens.get(i+3).contentEquals("length")) {
        		Collections.swap(listener.tokens,i,i+1);
        	}if(listener.tokens.get(i).contentEquals("]")
        			&&listener.tokens.get(i+2).contentEquals("[")) {
        		Collections.swap(listener.tokens,i,i+1);
        	}if(listener.tokens.get(i).contentEquals("=")
        			&&listener.tokens.get(i-1).contentEquals("]")
        			&&listener.tokens.get(i-3).contentEquals("[")
        			&&(listener.tokens.get(i-6).contentEquals("int")
        			||listener.tokens.get(i-6).contentEquals("String")
        			||listener.tokens.get(i-6).contentEquals("double")
        			||listener.tokens.get(i-6).contentEquals("float")
        			||listener.tokens.get(i-6).contentEquals("boolean")
        			||listener.tokens.get(i-6).contentEquals("short")
        			||listener.tokens.get(i-6).contentEquals("long")
        			||listener.tokens.get(i-6).contentEquals("char"))) {
        		Collections.swap(listener.tokens,i-2,i-1);
        	}if(listener.tokens.get(i).contentEquals("==")
        			&&listener.tokens.get(i-2).contentEquals("]")
        			&&listener.tokens.get(i-3).contentEquals("[")) {
        		Collections.swap(listener.tokens,i-2,i-1);
        	}if(listener.tokens.get(i).contentEquals(")")
        			&&listener.tokens.get(i+1).contentEquals("{")
        			&&listener.tokens.get(i-1).contentEquals("]")
        			&&(listener.tokens.get(i-6).contentEquals("int")
        			||listener.tokens.get(i-6).contentEquals("String")
        			||listener.tokens.get(i-6).contentEquals("double")
        			||listener.tokens.get(i-6).contentEquals("float")
        			||listener.tokens.get(i-6).contentEquals("boolean")
        			||listener.tokens.get(i-6).contentEquals("short")
        			||listener.tokens.get(i-6).contentEquals("long")
        			||listener.tokens.get(i-6).contentEquals("char"))) {
        		Collections.swap(listener.tokens,i-2,i-1);
        	}if(listener.tokens.get(i).contentEquals("]")
        			&&listener.tokens.get(i-3).contentEquals("(")
        			&&listener.tokens.get(i+6).contentEquals("{")) {
        		Collections.swap(listener.tokens,i-2,i-1);
        	}
        	if(listener.tokens.get(i).contentEquals(")")
        			&&listener.tokens.get(i+1).contentEquals("{")
        			&&listener.tokens.get(i-1).contentEquals("]")
        			&&(listener.tokens.get(i-8).contentEquals("int")
        			||listener.tokens.get(i-8).contentEquals("String")
        			||listener.tokens.get(i-8).contentEquals("double")
        			||listener.tokens.get(i-8).contentEquals("float")
        			||listener.tokens.get(i-8).contentEquals("boolean")
        			||listener.tokens.get(i-8).contentEquals("short")
        			||listener.tokens.get(i-8).contentEquals("long")
        			||listener.tokens.get(i-8).contentEquals("char"))) {
        		Collections.swap(listener.tokens,i-2,i-1);
        	}
        	if(listener.tokens.get(i).contentEquals("=")
        			&&listener.tokens.get(i-1).contentEquals("]")
        			&&listener.tokens.get(i-3).contentEquals("[")
        			&&(listener.tokens.get(i-8).contentEquals("int")
        			||listener.tokens.get(i-8).contentEquals("String")
        			||listener.tokens.get(i-8).contentEquals("double")
        			||listener.tokens.get(i-8).contentEquals("float")
        			||listener.tokens.get(i-8).contentEquals("boolean")
        			||listener.tokens.get(i-8).contentEquals("short")
        			||listener.tokens.get(i-8).contentEquals("long")
        			||listener.tokens.get(i-8).contentEquals("char"))) {
        		Collections.swap(listener.tokens,i-1,i-2);
        	}if(listener.tokens.get(i).contentEquals("length")
        			&&listener.tokens.get(i-1).contentEquals(".")
        			&&listener.tokens.get(i-2).contentEquals("]")
        			&&listener.tokens.get(i-20).contentEquals("GetLength(1)")) {
        		listener.tokens.set(i, "GetLength(2)");
        		listener.tokens.remove(i-2);
        		listener.tokens.remove(i-3);
        		listener.tokens.remove(i-4);
        	}if(listener.tokens.get(i).contentEquals("length")
        			&&listener.tokens.get(i-1).contentEquals(".")
        			&&listener.tokens.get(i-2).contentEquals("]")
        			&&listener.tokens.get(i-20).contentEquals("GetLength(0)")) {
        		listener.tokens.set(i, "GetLength(1)");
        		listener.tokens.remove(i-2);
        		listener.tokens.remove(i-3);
        		listener.tokens.remove(i-4);
        	}if(listener.tokens.get(i).contentEquals("length")
        			&&listener.tokens.get(i+4).contentEquals(")")
        			&&listener.tokens.get(i+20).contentEquals("length")) {
        		listener.tokens.set(i,"GetLength(0)");
        	}
        	
        		
        }
        translation +=("using System;\n");
        
        for (int i = 0; i < listener.tokens.size() - 1; i++) {
        	if (listener.tokens.get(i).contentEquals("for")) {
        		forLoop = true;
        	}
        	if (listener.tokens.get(i).contentEquals("(") && forLoop) {
        		forDepth++;
        	}
        	else if (listener.tokens.get(i).contentEquals(")") && forLoop) {
        		forDepth--;
        		if (forDepth == 0) {
        			forLoop = false;
        		}
        	}
        	
        	if (listener.tokens.get(i).contentEquals("=") && listener.tokens.get(i + 1).contentEquals("{")) {
        		arrayInit = true;
        		
        	}
        	if (listener.tokens.get(i).contentEquals("}") && arrayInit) {
        		arrayInit = false;
        	}
        	
        	translation +=(listener.tokens.get(i));
        	if (!listener.tokens.get(i).contentEquals(";") 
        			&& !listener.tokens.get(i).contentEquals(".") 
        			&& !listener.tokens.get(i + 1).contentEquals(".")
        			&& !listener.tokens.get(i).contentEquals("(")
        			&& !listener.tokens.get(i + 1).contentEquals("(")
        			&& !listener.tokens.get(i + 1).contentEquals(")")
        			&& !listener.tokens.get(i + 1).contentEquals(";")) {
        		translation +=(" ");
        	}
        	
        	
        	
        	if (listener.tokens.get(i).contentEquals(";")) {
        		if (!forLoop) {
        			translation +=("\n");
        		}
        		if (!listener.tokens.get(i + 1).contentEquals("}") && !forLoop) {
        			for (int y = 0; y < indent; y++) {
            			translation += ("    ");
            		}
        		}
        		else if (!forLoop) {
        			for (int y = 0; y < indent - 1; y++) {
            			translation += ("    ");
            		}
        		}
        		else if (!listener.tokens.get(i + 1).contentEquals("}") && forLoop) {
        			for (int y = 0; y < indent; y++) {
            			translation += (" ");
            		}
        		}
        		else if (forLoop) {
        			for (int y = 0; y < indent - 1; y++) {
            			translation += (" ");
            		}
        		}
        	}
        	else if (listener.tokens.get(i).contentEquals("{") && !arrayInit) {
        		translation += ("\n");
        		indent++;
        		for (int y = 0; y < indent; y++) {
        			translation += ("    ");
        		}
        	}
        	else if (listener.tokens.get(i).contentEquals("}") && !listener.tokens.get(i + 1).contentEquals(";")) {
        		translation += ("\n");
        		indent--;
        		if (!listener.tokens.get(i + 1).contentEquals("}")) {
        			for (int y = 0; y < indent; y++) {
            			translation += ("    ");
            		}
        		}
        		else {
        			for (int y = 0; y < indent - 1; y++) {
            			translation += ("    ");
            		}
        		}
        	}
        }
        
        translation += (listener.tokens.get(listener.tokens.size() - 1));
        translation = translation.replace("System.out.println", "Console.WriteLine");
        translation = translation.replace("System.out.print", "Console.WriteLine");
        translation = translation.replace("main", "Main");
        translation = translation.replace(".length()", ".Length");
        translation = translation.replace(".length", ".Length");
        translation = translation.replace(".charAt(i)", "[i]");
        translation = translation.replaceAll("Scanner.*.*;","");
        translation = translation.replaceAll("=.*nextInt().*;","= Convert.ToInt32(Console.ReadLine());");
        translation = translation.replaceAll("=.*nextLine().*;","= Console.ReadLine();");
        translation = translation.replaceAll("=.*nextDouble().*;","= Convert.ToDouble(Console.ReadLine());");
        translation = translation.replaceAll("=.*nextFloat().*;","= float.Parse(Console.ReadLine());");
        translation = translation.replaceAll("=.*nextChar().*;","= Console.ReadLine()[0];");
        translation = translation.replaceAll("=.*nextShort().*;","= (short)Convert.ToInt32(Console.ReadLine());");
        translation = translation.replaceAll("=.*nextLong().*;","= (long)Convert.ToInt32(Console.ReadLine());");
        translation = translation.replaceAll("boolean","bool");
        translation = translation.replaceAll("=.*nextBoolean().*;","= bool.Parse(Console.ReadLine());");
        translation = translation.replaceAll("=.*nextByte().*;","= byte.Parse(Console.ReadLine());");
        translation = translation.replaceAll("\\] \\[",",");
        translation = translation.replaceAll("\\]\\[",",");
        translation = translation.replaceAll("extends",":");
        translation = translation.replaceAll("implements",":");
        
        tc.setText(translation);
        translation = "";
        
        System.out.println("Translation Sucessful");
		return success;
	}
}
