package application;
	
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

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

import javax.swing.UIManager;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowAdapter;

public final class Main extends JFrame implements ActionListener, ItemListener {
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
    
    JPanel subP_JavaTextArea = new JPanel();
    JPanel subP_CSharpTextArea = new JPanel();
    JPanel subP_LeftButtons = new JPanel();
    JPanel subP_RightButtons = new JPanel();
    
    JMenuBar menuBar = new JMenuBar();
	
	JLabel label_Java = new JLabel("Java:");
	JTextArea textArea_Java = new JTextArea(12, 27);
	JTextField textField_Java = new JTextField("Lines: ; Characters: ", 27);
	JScrollPane jsp_Java = new JScrollPane(textArea_Java);
	JButton button_JavaSave = new JButton("Save");
	JButton button_JavaLoad = new JButton("Load");
	
	JLabel label_CSharp = new JLabel("C#:");
	JTextArea textArea_CSharp = new JTextArea(12, 27);
	JTextField textField_CSharp = new JTextField("Lines: ; Characters: ", 27);
	JScrollPane jsp_CSharp = new JScrollPane(textArea_CSharp);
	JButton button_CSharpTrans = new JButton("Translate"); // Translate Java to C#
	JButton button_CSharpSave = new JButton("Save");
	
	FileOperators fileOp = new FileOperators();
	
	public Main() {
		setTitle("Java to C# Cross-Compiler");
		setSize(650,540);
		setResizable(false);
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
	    
	    button_JavaLoad.addActionListener(this);
	    button_JavaLoad.setActionCommand("LoadJava");
	    
	    button_JavaSave.addActionListener(this);
	    button_JavaSave.setActionCommand("SaveJava");
	    
	    button_CSharpSave.addActionListener(this);
	    button_CSharpSave.setActionCommand("SaveCSharp");
	    
	    button_CSharpTrans.addActionListener(this);
	    button_CSharpTrans.setActionCommand("Translate");
	    
	    //list_SegSelect.addListSelectionListener(listSeleMod_SegSelect);
	    
	    // --
	    
	    mainPanel1.resetToPreferredSizes();
	    mainPanel1.setDividerLocation(325);
	    mainPanel1.setDividerSize(-1);
	    
	    sub2PanelLeft.resetToPreferredSizes();
	    sub2PanelLeft.setDividerLocation(360);
	    sub2PanelLeft.setDividerSize(-1);
	    
	    sub3PanelLeft1.resetToPreferredSizes();
	    sub3PanelLeft1.setDividerLocation(340);
	    sub3PanelLeft1.setDividerSize(-1);
	    
	    sub3PanelLeft2.resetToPreferredSizes();
	    sub3PanelLeft2.setDividerLocation(90);
	    sub3PanelLeft2.setDividerSize(-1);
	    
	    sub2PanelRight.resetToPreferredSizes();
	    sub2PanelRight.setDividerLocation(360);
	    sub2PanelRight.setDividerSize(-1);
	    
	    sub3PanelRight1.resetToPreferredSizes();
	    sub3PanelRight1.setDividerLocation(340);
	    sub3PanelRight1.setDividerSize(-1);
	    
	    sub3PanelRight2.resetToPreferredSizes();
	    sub3PanelRight2.setDividerLocation(90);
	    sub3PanelRight2.setDividerSize(-1);
	    
	    textArea_CSharp.setEditable(false);
	    textField_Java.setEditable(false);
	    textField_CSharp.setEditable(false);
	    
	    // --
	    
	    buttonPanelLeft.add(button_JavaSave);
	    buttonPanelLeft.add(button_JavaLoad);
	    
	    sub3PanelLeft1.add(jsp_Java);
	    sub3PanelLeft1.add(textField_Java);
	    sub3PanelLeft2.add(label_Java);
	    sub3PanelLeft2.add(buttonPanelLeft);
	    
	    sub2PanelLeft.add(sub3PanelLeft1);
	    sub2PanelLeft.add(sub3PanelLeft2);
	    
	    // --
	    
	    buttonPanelRight.add(button_CSharpTrans);
	    buttonPanelRight.add(button_CSharpSave);
	    
	    sub3PanelRight1.add(jsp_CSharp);
	    sub3PanelRight1.add(textField_CSharp);
	    sub3PanelRight2.add(label_CSharp);
	    sub3PanelRight2.add(buttonPanelRight);
	    
	    sub2PanelRight.add(sub3PanelRight1);
	    sub2PanelRight.add(sub3PanelRight2);
	    
	    // --
	    
	    mainPanel1.add(sub2PanelLeft);
	    mainPanel1.add(sub2PanelRight);
	    
		setJMenuBar(menuBar);
	    add(mainPanel1);
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
				// fileOp.translate(textArea_Java, textArea_CSharp);				
			}
	}
	
	// Checks All State Changes
	public void itemStateChanged(ItemEvent e) {
		
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
}





























































/* Original

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setOnCloseRequest(evt -> {
			if (!Controller.javaSaved || !Controller.cSaved) {
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You have unsaved changes, are you sure you want to quit?", ButtonType.YES, ButtonType.CANCEL);
				ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
				if (ButtonType.CANCEL.equals(result)) {
					evt.consume();
				}
			}
		});
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("GUI.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setMinHeight(400);
			primaryStage.setMinWidth(700);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
*/