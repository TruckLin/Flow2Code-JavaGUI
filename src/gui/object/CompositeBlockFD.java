package gui.object;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;

import org.json.JSONObject;

import gui.LinePopup;
import gui.manager.NameCounterManager;
import gui.manager.UndoManager;
import gui.mouselistener.LineRightClickListener;

public abstract class CompositeBlockFD extends BlockFD{

	protected ArrayList<LineFD> lineList = new ArrayList<LineFD>();
	protected PropertyChangeListener repaintListener = 
			e -> {generateLineSegmentsForAllLines();
				// Testing
				//System.out.println("line changes detected by parent block");
														
				repaint(); // repaint the lines
			};
	
	public CompositeBlockFD(JSONObject model) {
		super(model);
		
		this.setLayout(null);
		
		// Add right clisk listener for line
		LineRightClickListener lineListener = new LineRightClickListener(this);
		this.addMouseListener(lineListener);
		this.addMouseMotionListener(lineListener);
		
		// Temporary
		this.setOpaque(false);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
	}
	/** Getters and Setters **/
	public ArrayList<LineFD> getLineList() {
		return this.lineList;
	}
	
	/** Add line and remove line**/
	public void removeLineFD(LineFD line) {
		line.removePropertyChangeListener(repaintListener);
		this.lineList.remove(line);
		this.repaint();
	}
	public void addLineFD(LineFD line) {
		line.addPropertyChangeListener(repaintListener);
		this.lineList.add(line);
		this.generateLineSegments(line);
		this.repaint();
	}
	
	/** Paint Component function**/
	// This function is responsible for painting the lines.
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		
		for(int i = 0; i < lineList.size(); i++) {
			LineFD currentLine = this.lineList.get(i);
			
			for(int j = 0; j < lineList.get(i).getLineSegments().size(); j++) {
				ArrayList<Line2D> segments = lineList.get(i).getLineSegments();
				Line2D segment = segments.get(j);
				
				if(currentLine.getHasBorder()) {
					g2.setColor(currentLine.getLineBorderColor());
					BasicStroke myStroke = new BasicStroke((float)currentLine.getTriggerRagius(),
													BasicStroke.CAP_BUTT,BasicStroke.JOIN_ROUND, 10.0f);
					g2.setStroke(myStroke);
					g2.draw(segment);
				}
				g2.setColor(currentLine.getLineColor());
				g2.setStroke(new BasicStroke());
				g2.draw(segment);
				
				
			}
		}
	}
	
	/** Generate Line segments for one LineFD **/
	public static String findWhichSide(BlockFD block, Point p) {
		/*				  top
		 * 			-----------------
		 * 			|				|
		 * 	left 	|				| right 
		 * 			|				|
		 * 			-----------------
		 * 				 bottom
		*/ 
		Rectangle rec = block.getBounds();
		double left = rec.getMinX();
		double right = rec.getMaxX();
		double top = rec.getMinY();
		double bottom = rec.getMaxY();

		double x = p.getX();
		double y = p.getY();
		
		if(left == x)  return "left";
		else if(right == x) return "right";
		else if(bottom == y) return "bottom";
		else if(top == y) return "top";
		else return null;
	}
	 
	public void generateLineSegments(LineFD line) {
		ArrayList<Line2D> segments = line.getLineSegments();
		segments.clear();
		BlockFD source = line.getSource();
		BlockFD terminal = line.getTerminal();
		Point startPoint = line.getStartPoint();
		Point endPoint = line.getEndPoint();
		// Testing
		//System.out.println("In generateLineSegments(LineFD lineFD) : ");
		//System.out.println("lineFD : \n Source = " + lineFD.getSource().toString());
		//System.out.println(" startPoint = " + lineFD.getStartPoint().toString());
		//System.out.println(" Terminal = " + lineFD.getTerminal().toString());
		//System.out.println(" endPoint = " + lineFD.getEndPoint().toString());
		
		// We use straight line for the moment
		segments.add(new Line2D.Double(line.getStartPoint(),line.getEndPoint()));
		
		
		
		
	}
	public void generateLineSegmentsForAllLines() {
		for(LineFD line :lineList) {
			generateLineSegments(line);
		}
	}
	
	/** override abstract method **/
	@Override
	public void setUndoManager(UndoManager undoManager) {
		this.undoManager = undoManager;
		Component[] componentList = this.getComponents();
		for(Component comp:componentList ) {
			if(comp instanceof BlockFD) {
				((BlockFD)comp).setUndoManager(undoManager);
			}
		}
	}
	@Override
	public void setNameCounterManager(NameCounterManager nameManager) {
		// TODO Auto-generated method stub
		this.nameManager = nameManager;
		Component[] componentList = this.getComponents();
		for(Component comp:componentList ) {
			if(comp instanceof BlockFD) {
				((BlockFD)comp).setNameCounterManager(nameManager);
			}
		}
	}
	@Override
	protected boolean shouldAddBlockDrag() {
		return false;
	}
	
	@Override
	protected boolean isCompositeBlockFD() {
		return true;
	}
	
	@Override
	protected boolean shouldAddLoopDrag() {
		return false;
	}
	@Override
	protected boolean shouldAddEndLoopDrag() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean shouldAddBlockRightClick() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isEditable() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean representCompositeBlock() {
		// TODO Auto-generated method stub
		return false;
	}
}
