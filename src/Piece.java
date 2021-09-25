import java.util.Map;
import java.awt.event.MouseListener;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Piece extends JLabel {
	public boolean firstMove;
	public int position, imaginaryPosition;
	public String color, type;
	public Move move;
	private static String imageDirectory;
	private static Map<String, ImageIcon> icons;
    public static Map<String, MouseListener> mouseListeners;

    public Piece(String color,String type, int position) {
    	this.color=color;
    	this.type=type;
    	this.position=position;
    }
    
	public Piece(String color, String type, Move move) {
		imageDirectory="img/";
		
		icons= new HashMap<String, ImageIcon>(){{
	        put("black rook", new ImageIcon(imageDirectory+"b_rook.png"));
	        put("white rook", new ImageIcon(imageDirectory+"w_rook.png"));
	        put("black knight", new ImageIcon(imageDirectory+"b_knight.png"));
	        put("white knight", new ImageIcon(imageDirectory+"w_knight.png"));
	        put("white bishop", new ImageIcon(imageDirectory+"w_bishop.png"));
	        put("black bishop", new ImageIcon(imageDirectory+"b_bishop.png"));
	        put("black queen", new ImageIcon(imageDirectory+"b_queen.png"));
	        put("white queen", new ImageIcon(imageDirectory+"w_queen.png"));
	        put("white king", new ImageIcon(imageDirectory+"w_king.png"));
	        put("black king", new ImageIcon(imageDirectory+"b_king.png"));
	        put("black pawn", new ImageIcon(imageDirectory+"b_pawn.png"));
	        put("white pawn", new ImageIcon(imageDirectory+"w_pawn.png"));
	    }};
	    
	    mouseListeners= new HashMap<String, MouseListener>(){{
	    	put("rook", new RookListener(move));
	    	put("knight",new KnightListener(move));
	    	put("bishop",new BishopListener(move));
	    	put("queen",new QueenListener(move));
	    	put("king",new KingListener(move));
	    	put("pawn",new PawnListener(move));
	    }};

		this.color=color;
		this.type=type;
		this.move=move;
		setupPiece(color,type);
	}

	private void setupPiece(String color, String type) {
		this.setIcon(icons.get(color+" "+type));
		this.addMouseListener(mouseListeners.get(type));
		firstMove=true;
	}

	public void setPosition(int randXInt, int randYInt) {
		this.position=10*randXInt+randYInt;
	}
	
	public int getPosition() {
		return position;
	}
	
	@Override
	 public String toString() {
		 return String.format(color+" "+type+" ");        
	 }
}
