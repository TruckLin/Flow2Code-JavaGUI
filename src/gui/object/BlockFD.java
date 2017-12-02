package gui.object;

import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.*;

import org.json.JSONObject;

import gui.BlockEditDialog;
import gui.manager.NameCounterManager;
import gui.manager.UndoManager;
import gui.mouselistener.BlockDragListener;
import gui.mouselistener.BlockRightClickListener;
import gui.mouselistener.EndLoopDragListener;
import gui.mouselistener.LoopDragListener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public abstract class BlockFD extends JPanel{
	
	private JSONObject model;
	protected UndoManager undoManager;
	protected NameCounterManager nameManager;
	
	protected double currentZoomRatio = 1;
	
	protected JLabel blockLabel = new JLabel("");
	
	protected PropertyChangeListener updatePortsListener = e -> updatePorts();
	
	/** Constructors **/
	public BlockFD(JSONObject model) {
		super();
		this.model = model;
		this.setLayout(null);
		
		this.setOpaque(false);
		
		this.addPropertyChangeListener(updatePortsListener);
		
	}
	
	
	/** Getters and Setters **/
	public JSONObject getModel() {
		return this.model;
	}
	public void setModel(JSONObject model) {
		this.model = model;
	}
	public void setLocation(int x, int y) {
		Point oldValue = this.getLocation();
		super.setLocation(x, y);
		this.firePropertyChange("Location", oldValue, new Point(x,y));
	}
	public void setLocation(Point p) {
		Point oldValue = this.getLocation();
		super.setLocation(p);
		this.firePropertyChange("Location", oldValue, p);
	}
	
	public void setBounds(int x, int y, int width, int height) {
		Rectangle oldValue = this.getBounds();
		super.setBounds(x, y, width, height);
		this.firePropertyChange("Bounds", oldValue, this.getBounds());
	}
	public void setBounds(Rectangle rec) {
		Rectangle oldValue = this.getBounds();
		super.setBounds(rec);
		this.firePropertyChange("Bounds", oldValue, rec);
	}
	
	public UndoManager getUndoManager() {
		return this.undoManager;
	}
	
	public JLabel getBlockLabel() {
		return this.blockLabel;
	}
	public void setBlockLabel(JLabel temp) {
		if(temp != null) {
			this.remove(this.blockLabel);
			this.blockLabel = temp;
			this.add(temp);
		}
	}
	/* Abstract method : updatePorts()
	 * This should be implemented by all BlockFD, either do nothing, or update port location.
	 */
	protected abstract void updatePorts();
	
	public abstract void setUndoManager(UndoManager undoManager);
	
	public NameCounterManager getNameCounterManager() {
		return this.nameManager;
	}
	public abstract void setNameCounterManager(NameCounterManager nameManager);
	
	public double getCurrentZoomRatio() {
		return this.currentZoomRatio;
	}
	protected void setCurrentZoomRatio(double zr) {
		this.currentZoomRatio = zr;
	}
	
	/** Event handling functions **/
	public BlockEditDialog getBlockEditDialog(UndoManager undoManager) {
		// this function should be implemented by various block like BlockIF, BlockWHILE and many more.
		return null;
	}
	
	/** Utility functions **/
	public abstract void updateBlockContent() ;
	
	
	public Point toContainerCoordinate(Point coordWRTblock) {
		int x = (int)(this.getLocation().getX() + coordWRTblock.getX());
		int y = (int)(this.getLocation().getY() + coordWRTblock.getY());
		return new Point(x,y);
	}
	
	
	/** Move the block by displacement **/
	public void translateLocation(int dx, int dy) {
		int x = (int)this.getLocation().getX();
		int y = (int)this.getLocation().getY();
		
		x = x + dx;
		y = y + dy;
		
		this.setLocation(x, y);
	}
	
	
	/** Methods that attach listeners to Blocks **/
	public void addVariousMouseListeners() {
		//Testing
		//System.out.println("addVariousMouseListeners() is called by : " + this.getClass());
		
		if(isCompositeBlockFD()) {
			Component[] componentList = this.getComponents();
			for(Component comp:componentList ) {
				if(comp instanceof BlockFD) {
					((BlockFD)comp).addVariousMouseListeners();
				}
			}
		}
		
		if(this.shouldAddBlockDrag()) {
			BlockDragListener lis = new BlockDragListener(this.undoManager, this);
			this.addMouseMotionListener(lis);
			this.addMouseListener(lis);
		}
		
		if(this.shouldAddBlockRightClick()) {
			BlockRightClickListener lis = new BlockRightClickListener(this.undoManager,this);
			this.addMouseListener(lis);
		}
		
		if(this.shouldAddLoopDrag()) {
			LoopDragListener lis = new LoopDragListener(this.undoManager, this);
			this.addMouseMotionListener(lis);
			this.addMouseListener(lis);
		}
		
		if(this.shouldAddEndLoopDrag()) {
			EndLoopDragListener lis = new EndLoopDragListener(this.undoManager, this);
			this.addMouseMotionListener(lis);
			this.addMouseListener(lis);
		}
		
		
		
		// Testing
		//System.out.println("end by : " + this.getClass());
	}
	protected abstract boolean isCompositeBlockFD();
	protected abstract boolean shouldAddBlockDrag();
	protected abstract boolean shouldAddBlockRightClick();
	protected abstract boolean shouldAddLoopDrag();
	protected abstract boolean shouldAddEndLoopDrag();
	
	public abstract boolean isEditable();
	public abstract boolean representCompositeBlock(); // indicate whether a block represents a larger, composite blocks.
													   // BlockStartLOOP and BlockStartIF are the only two block that returns true.	
	
	
	/** Zoom Function **/
	public void zoom(double newRatio) {
		Rectangle oldRec = this.getBounds();
		int x = (int) Math.round(oldRec.getX()*newRatio/currentZoomRatio);
		int y = (int) Math.round(oldRec.getY()*newRatio/currentZoomRatio);
		int width = (int) Math.round(oldRec.getWidth()*newRatio/currentZoomRatio);
		int height = (int) Math.round(oldRec.getHeight()*newRatio/currentZoomRatio);
		Rectangle newRec = new Rectangle(x,y,width,height);
		this.setBounds(newRec);
		
		
		
		if(this.isCompositeBlockFD()) {
			Component[] comps = this.getComponents();
			for(Component comp : comps) {
				((BlockFD)comp).zoom(newRatio);
			}
		}
		
		this.zoomLabel(newRatio);
		// After all the blocks have been zoomed, update currentZoomRatio.
		this.setCurrentZoomRatio(newRatio);
	}
	
	protected void zoomLabel(double newRatio) {
		if(!blockLabel.getText().equals("")) {
			Font myFont = this.blockLabel.getFont();
			this.blockLabel.setFont(new Font(myFont.getFontName(), Font.PLAIN, 
										(int)Math.round(myFont.getSize()*newRatio/this.currentZoomRatio)));
			this.adjustLabelBounds();
		}
		
	}
	
	public void adjustLabelBounds() {
		int width = this.getWidth();
		int height = this.getHeight();
		
		Dimension labelDimension = this.blockLabel.getPreferredSize();
		Point newLocation = new Point( (int)Math.round(width/2 - labelDimension.getWidth()/2), 
										(int)Math.round(height/2 - labelDimension.getHeight()/2));
		this.blockLabel.setSize(labelDimension);
		this.blockLabel.setLocation(newLocation);
	}
	

}
