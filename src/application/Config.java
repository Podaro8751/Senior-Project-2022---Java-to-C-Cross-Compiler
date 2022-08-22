package application;

import java.io.File;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowAdapter;

public final class Config extends JFrame implements ActionListener, ItemListener {   
	JFileChooser fileChooser_Java;
	JFileChooser fileChooser_CSharp;
	
	JSplitPane containerPanel1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

	JPanel mainPanel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel mainPanel2 = new JPanel();
	
	JPanel sub1Panel1 = new JPanel();
	
	JPanel sub2Panel1 = new JPanel();
	
	JLabel label_colorTheme = new JLabel("Color Theme: ");	
	String[] array_colorTheme = {"Light", "Dark"};
	JComboBox combobox_colorTheme = new JComboBox(array_colorTheme);
	
	JButton button_closeWin = new JButton("Close");
	
	Main parent = null;
	boolean changeReady = true;
	
	public Config(Main parent) {
		this.parent = parent;
		setTitle("Java to C# Cross-Compiler - Configure");
		setSize(200,150);
		setResizable(false);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	    addWindowListener(new WindowAdapter() {
	    	@Override
	    	public void windowClosing(WindowEvent e) {
	    	    setVisible(false);
	    	    // textfield_openDFolder.setText(parent.defaultDir);
	    	}
	    });
	    
	    combobox_colorTheme.addItemListener(this);
	    
	    button_closeWin.addActionListener(this);
	    button_closeWin.setActionCommand("Close");
	    //--------------------------------------------------------------------------------------
	    containerPanel1.resetToPreferredSizes();
	    containerPanel1.setDividerLocation(375);
	    containerPanel1.setDividerSize(-1);
	    
	    containerPanel1.add(mainPanel1);
	    containerPanel1.add(mainPanel2);
	    
	    mainPanel1.add(sub1Panel1);
	    sub1Panel1.add(label_colorTheme);
	    sub1Panel1.add(combobox_colorTheme);
	    
	    mainPanel2.add(sub2Panel1);
	    sub2Panel1.add(button_closeWin);	    
	        
	    add(containerPanel1);
	    //--------------------------------------------------------------------------------------
    }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		/* if (cmd.equals("Save")) {
			System.out.println("Starting Config Save");
			parent.defaultDir = textfield_openDFolder.getText();
			parent.configToString();
			parent.conv.writeTextTo(parent.configData, parent.configFile);
			setVisible(false);
		} */
		if (cmd.equals("Close")) {
			setVisible(false);
		}
	}
	
	public void itemStateChanged(ItemEvent e) {
		if (combobox_colorTheme.getSelectedItem().equals("Light")) {
			parent.colorLIGHT();
		} else if(combobox_colorTheme.getSelectedItem().equals("Dark")) {
			parent.colorDARK();
		} else if(combobox_colorTheme.getSelectedItem().equals("Night")) {
			parent.colorNIGHT();
		}
	}
}
