package graph.object;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import org.json.JSONObject;

import model.object.ComponentWHILE;

public class BlockWHILE extends OrdinaryBlockFD {
	
	public BlockWHILE(JSONObject model){
		super(model);
		
		this.setOpaque(false); // we should always see through this while panel.
		
		// Set the specific outport for while
		this.setOutport( new Point( Math.round( this.getWidth()/4), this.getHeight() ) );
		
		//Temporary
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
	}
	
	/** Getters and Setters **/
	
	
	/** Utility Functions **/
	
	
}