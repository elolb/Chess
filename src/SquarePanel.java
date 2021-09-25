import javax.swing.JPanel;

public class SquarePanel extends JPanel {
	
	public Piece currentPiece;	// the piece that occupies the square
	public int position;		// the location of the square
	private boolean isEmpty;
	
	public SquarePanel(int position) {
			setOpaque(false);
			this.position=position;
			isEmpty=true;
	}
	
	public void occupy(Piece piece) {
		isEmpty=false;
		currentPiece=piece;
	}
	
	public void empty() { 
		isEmpty=true;
		currentPiece=null;
	}
	
	public boolean isEmpty(boolean check) {	
		return isEmpty;
	}
	
	public Piece getCurrentPiece(boolean check) {
		return currentPiece;
	}
}