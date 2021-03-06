package gui.commands;

import gui.interfaces.Command;
import gui.object.BlockFD;
import gui.object.CompositeBlockFD;

public class TranslateLocationCommand implements Command {
	private BlockFD block;
	private int dx;
	private int dy;
	
	private double oldZoomRatio;

	public TranslateLocationCommand(BlockFD block, int dx, int dy) {
		this.block = block;
		this.dx = dx;
		this.dy = dy;
		
		this.oldZoomRatio = block.getCurrentZoomRatio();
	}
	
	/** Getters and Setters **/
	public BlockFD getBlock() {
		return block;
	}
	public int getdx() {
		return this.dx;
	}
	public int getdy() {
		return this.dy;
	}
	
	@Override
	public void execute() {
		// Testing
		//System.out.println("In execution of TranslateLocationCommand:");
		
		block.translateLocation((int)Math.round(dx*this.block.getCurrentZoomRatio()/oldZoomRatio),
								(int)Math.round(dy*this.block.getCurrentZoomRatio()/oldZoomRatio));
		
		((CompositeBlockFD)block.getParent()).setAppropriateBounds();
		
		//Testing
		//System.out.println("this print line is executed after getParent(). ");
		
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		block.translateLocation((int)Math.round(-dx*this.block.getCurrentZoomRatio()/oldZoomRatio),
								(int)Math.round(-dy*this.block.getCurrentZoomRatio()/oldZoomRatio));
		((CompositeBlockFD)block.getParent()).setAppropriateBounds();
	}

	@Override
	public void redo() {
		// TODO Auto-generated method stub
		execute();
	}

	@Override
	public boolean isCollapsible(Command command) {
		// TODO Auto-generated method stub
		
		boolean sameClass = this.getClass().equals(command.getClass());
		if(sameClass) {
			if(this.block.equals(((TranslateLocationCommand)command).getBlock())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void collapse(Command command) {
		// TODO Auto-generated method stub
		this.dx = this.dx + ((TranslateLocationCommand)command).getdx();
		this.dy = this.dy + ((TranslateLocationCommand)command).getdy();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
