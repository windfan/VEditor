import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;


public class VNotePadUI extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JMenuItem menuOpen;
    private JMenuItem menuSave;
    private JMenuItem menuSaveAs;
    private JMenuItem menuQuit;

    private JMenu editMenu;
    private JMenuItem menuCut;
    private JMenuItem menuCopy;
    private JMenuItem menuPaste;

    private JMenuItem menuAbout;
    
    private JTextArea textArea;
    
    private JPopupMenu popUpMenu;
	
    private JLabel stateBar;
    
    private JFileChooser fileChooser;
    
	public VNotePadUI() {
		super("New Text File");
		setUpUIComponent();
		setUpEventListener();
		setVisible(true);
	}
	
	private void setUpUIComponent() {
		setSize(640, 480);
		//menu bar
		JMenuBar menuBar = new JMenuBar();
		
		/*File menu*/
		//file button
		JMenu fileMenu = new JMenu("File");		
		//open file & shortcut
		menuOpen = new JMenuItem("Open File...");
		menuOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.META_DOWN_MASK));
		//save file & shortcut
		menuSave = new JMenuItem("Save");
		menuSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.META_DOWN_MASK));
		//save file as...
		menuSaveAs = new JMenuItem("Save as...");
		//quit
		menuQuit = new JMenuItem("Quit");
		menuQuit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.META_DOWN_MASK));
		
		fileMenu.add(menuOpen);
		fileMenu.addSeparator();
		fileMenu.add(menuSave);
		fileMenu.add(menuSaveAs);
		fileMenu.addSeparator();
		fileMenu.add(menuQuit);
		
		/*Edit menu*/
		//Edit button
		editMenu = new JMenu("Edit");
		//cut & shortcut
		menuCut = new JMenuItem("Cut");
		menuCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.META_DOWN_MASK));
		//copy & shortcut
		menuCopy = new JMenuItem("Copy");
		menuCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.META_DOWN_MASK));
		//paste & shortcut
		menuPaste = new JMenuItem("Paste");
		menuPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.META_DOWN_MASK));
		
		editMenu.add(menuCut);
		editMenu.add(menuCopy);
		editMenu.add(menuPaste);
		
		/*Help menu*/
		//Help button
		JMenu helpMenu = new JMenu("Help");
		//about
		menuAbout = new JMenuItem("About VEditor");
		helpMenu.add(menuAbout);
		
		//Text area
		textArea = new JTextArea();
		textArea.setFont(new Font("Helvetica", Font.PLAIN, 16));
		textArea.setLineWrap(true);
		JScrollPane panel = new JScrollPane(textArea, 
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		Container contentPane = getContentPane();
		contentPane.add(panel, BorderLayout.CENTER);
		
		//State bar
		stateBar = new JLabel("No Change");
		stateBar.setHorizontalAlignment(SwingConstants.LEFT);
		stateBar.setBorder(BorderFactory.createEtchedBorder());
		contentPane.add(stateBar, BorderLayout.SOUTH);
		
		
		
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(helpMenu);
		setJMenuBar(menuBar);
		
		popUpMenu = editMenu.getPopupMenu();
		
		fileChooser = new JFileChooser();
	}
	
	private void setUpEventListener() {
		
		//event listener for clicking close button
		addWindowListener(
			new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					closeFile();
				}
			}
		);
		
		//file item -> open files
		menuOpen.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					openFile();
				}
			}
		);
		
		//file item -> save file
		menuSave.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					saveFile();
				}
			}
		);
		
		//file item -> save as 
		menuSaveAs.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					saveFileAs();
				}
			}
		);
		
		//file item -> quit
		menuQuit.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					closeFile();
				}
			}
		);
		
		//edit item -> cut
		menuCut.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cut();
				}
			}
		);
		
		//edit item -> copy
		menuCopy.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					copy();
				}
			}
		);
		
		//edit item -> paste
		menuPaste.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					paste();
				}
			}
		);
		
		//help item -> about
		menuAbout.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//show a dialog
					JOptionPane.showOptionDialog(null,
							"Program name:\n VEditor \n" +
							"Creator:\n Tzu-Hao Huang(Victor)\n" +
							"Personal Website:\n" + 
							"http://www.victorportfolio.net",
							"About VEditor",
							JOptionPane.DEFAULT_OPTION,
							JOptionPane.INFORMATION_MESSAGE,
							null, null, null);
				}
			}
		);
		
		//text area keyboard event
		textArea.addKeyListener(
			new KeyAdapter() {
				public void keyTyped(KeyEvent e) {
					processTextArea();
				}
			}
		);
		
		//text area mouse event
		textArea.addMouseListener(
			new MouseAdapter() {
				public void mouseReleased(MouseEvent e) {
					if(e.getButton() == MouseEvent.BUTTON3) {
						popUpMenu.show(editMenu, e.getX(), e.getY());
					}
				}
				public void mouseClicked(MouseEvent e) {
					if(e.getButton() == MouseEvent.BUTTON1) {
						popUpMenu.setVisible(false);
					}
				}
			});
		
	}

	private void processTextArea() {
		stateBar.setText("Edited");
	}

	private void paste() {
		textArea.paste();
		stateBar.setText("Edited");
		popUpMenu.setVisible(false);
	}

	private void copy() {
		textArea.copy();
		popUpMenu.setVisible(false);		
	}

	private void cut() {
		textArea.cut();
		stateBar.setText("Edited");
		popUpMenu.setVisible(false);
	}

	private void saveFileAs() {
		int option = fileChooser.showSaveDialog(null);
		
		if(option == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			setTitle(file.toString());
			
			try {
				file.createNewFile();
				saveFile();
			}
			catch(IOException e) {
				JOptionPane.showMessageDialog(null, e.toString(), "Couldn't create new file", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void saveFile() {
		File file = new File(getTitle());
		
		//if non-exist
		if(!file.exists()) {
			saveFileAs();
		}
		else {
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(file));
				writer.write(textArea.getText());
				writer.close();
				stateBar.setText("No Change");
			}
			catch(IOException e) {
				JOptionPane.showMessageDialog(null, e.toString(), "Fail to write the file", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void openFile() {
		if(isCurrentFileSaved()) {
			open();
		}
		else {
			//show confirm dialog
			int option = JOptionPane.showConfirmDialog(
					null, "Unsaved file. Save?",
					"Save File?", JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE, null);
			switch(option) {
				case JOptionPane.YES_OPTION:
					saveFile();
					break;
				case JOptionPane.NO_OPTION:
					open();
					break;
			}
		}
	}

	private void open() {
		int option = fileChooser.showDialog(null, null);
		
		//clicking yes button
		if(option == JFileChooser.APPROVE_OPTION) {
			try {
				//open chosen file
				BufferedReader reader = new BufferedReader(new FileReader(fileChooser.getSelectedFile()));
				//setting the title based on the file chosen
				setTitle(fileChooser.getSelectedFile().toString());
				//clear previous file
				textArea.setText("");
				//change the state
				stateBar.setText("No Change");
				
				String lineSeparator = System.getProperty("line.separator");
				String text;
				while((text = reader.readLine()) != null) {
					textArea.append(text);
					textArea.append(lineSeparator);
				}
				
				reader.close();
			}
			catch(IOException e) {
				JOptionPane.showMessageDialog(null, e.toString(), "Failed!", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private boolean isCurrentFileSaved() {
		if(stateBar.getText().equals("Edited")) {
			return false;
		}
		else {
			return true;
		}
	}

	private void closeFile() {
		if(isCurrentFileSaved()) {
			dispose();
		}
		else {
			int option = JOptionPane.showConfirmDialog(
					null,
					"File edited, save?",
					"Save File?",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE);
			switch(option) {
				case JOptionPane.YES_OPTION:
					saveFile();
					break;
				case JOptionPane.NO_OPTION:
					dispose();
			}
		}
	}

	public static void main(String[] args) {
		new VNotePadUI();
	}
	
}
