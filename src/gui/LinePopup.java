package gui;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ResourceBundle;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import gui.commands.AddBlockCommand;
import gui.manager.NameCounterManager;
import gui.manager.UndoManager;
import gui.object.BlockFD;
import gui.object.CompositeBlockFD;
import gui.object.LineFD;

public class LinePopup extends JPopupMenu{
	private CompositeBlockFD compositeBlock;
	private UndoManager undoManager;
	private NameCounterManager nameManager;
	private LineFD line;
	private JMenuItem declareItem = new JMenuItem("");
	private JMenuItem assignItem = new JMenuItem("");
	private JMenuItem inputItem = new JMenuItem("");
	private JMenuItem outputItem = new JMenuItem("");
	private JMenuItem forItem = new JMenuItem("");
	private JMenuItem whileItem = new JMenuItem("");
	private JMenuItem ifItem = new JMenuItem("");
	
	
	public LinePopup(CompositeBlockFD compositeBlock, LineFD line) {
		super();
		this.setLayout(new GridLayout(4,2));
		this.compositeBlock = compositeBlock;
		this.undoManager = compositeBlock.getUndoManager();
		this.nameManager = compositeBlock.getNameCounterManager();
		this.setLine(line);
		
		ResourceBundle languageBundle = compositeBlock.getLanguageBundle();
		this.declareItem.setText(languageBundle.getString("Declare"));
		this.assignItem.setText(languageBundle.getString("Assign"));
		this.inputItem.setText(languageBundle.getString("Input"));
		this.outputItem.setText(languageBundle.getString("Output"));
		this.forItem.setText(languageBundle.getString("For"));
		this.whileItem.setText(languageBundle.getString("While"));
		this.ifItem.setText(languageBundle.getString("If"));
		
		// Add all the menu items
		this.add(this.declareItem);
		this.add(this.assignItem);
		this.add(this.inputItem);
		this.add(this.outputItem);
		this.add(this.forItem);
		this.add(this.whileItem);
		this.add(this.ifItem);
	}
	
	/** Getters and Setters **/
	public LineFD getLine() {
		return this.line;
	}
	public void setLine(LineFD newLine) {
		this.line = newLine;
		
		// remove previous action listeners
		ActionListener[] listenerList = this.declareItem.getActionListeners();
		for(ActionListener listener : listenerList) {
			this.declareItem.removeActionListener(listener);
		}
		listenerList = this.assignItem.getActionListeners();
		for(ActionListener listener : listenerList) {
			this.assignItem.removeActionListener(listener);
		}
		listenerList = this.inputItem.getActionListeners();
		for(ActionListener listener : listenerList) {
			this.inputItem.removeActionListener(listener);
		}
		listenerList = this.outputItem.getActionListeners();
		for(ActionListener listener : listenerList) {
			this.outputItem.removeActionListener(listener);
		}
		listenerList = this.forItem.getActionListeners();
		for(ActionListener listener : listenerList) {
			this.forItem.removeActionListener(listener);
		}
		listenerList = this.whileItem.getActionListeners();
		for(ActionListener listener : listenerList) {
			this.whileItem.removeActionListener(listener);
		}
		listenerList = this.ifItem.getActionListeners();
		for(ActionListener listener : listenerList) {
			this.ifItem.removeActionListener(listener);
		}
		
		// Add new action listener to menu items
		this.declareItem.addActionListener(e -> addBlockAction(compositeBlock, newLine, "Declare"));
		this.assignItem.addActionListener(e -> addBlockAction( compositeBlock, newLine, "Assign"));
		this.inputItem.addActionListener(e -> addBlockAction( compositeBlock, newLine, "Input"));
		this.outputItem.addActionListener(e -> addBlockAction( compositeBlock, newLine, "Output"));
		this.forItem.addActionListener(e -> addBlockAction( compositeBlock, newLine, "For"));
		this.whileItem.addActionListener(e -> addBlockAction( compositeBlock, newLine, "While"));
		this.ifItem.addActionListener(e -> addBlockAction( compositeBlock, newLine, "If"));
		
	}
	
	
	/** The action that tells undoManager to execute various command **/
	public void addBlockAction(CompositeBlockFD compositeBlock,LineFD line, String type) {
		AddBlockCommand command = new AddBlockCommand(compositeBlock,line, type);
		
		undoManager.execute(command);
	}
}
