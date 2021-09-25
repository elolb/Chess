import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class Move implements MouseListener {
	private ImageIcon possibleIcon = new ImageIcon("img/possible.png");
	public String turn, winner;
	public Piece currentPiece;
	Game game;
	Board board;
	public static Map<Integer, Boolean> markPossible, markCapture, unmarkedPossible, unmarkedCapture, enemyCapture;
	public Move(Board board) {
		game = new Game(this);
		this.board=board;
	}
	
	public void capture(int x, int y) {
		if(!("king".equals(Board.squareArray[x][y].currentPiece.type))) {
			Board.squareArray[x][y].removeAll();
			putPiece(x, y, currentPiece);
			Board.squareArray[x][y].revalidate();
			Board.squareArray[x][y].repaint();
		}
	}
	
	public void setCurrentPiece(Piece piece) {
		currentPiece=piece;
	}
	
	public void nextTurn() {
		clearMarks();
		if (Game.turn.equals("black")){
			Game.turn="white";
			Main.turnText.setForeground(Color.gray);
		}
		else if(Game.turn.equals("white")) {
			Game.turn="black";
			Main.turnText.setForeground(Color.black);
		}
		Main.turnText.setText("turn: "+Game.turn);
		if(game.isCheck(new Piece(Game.turn,"pawn",0))) //the piece's type here doesn't matter as long as it's not king
			{
			Main.turnText.setForeground(Color.red);
			if(game.isCheckmate()) {
				if (Game.turn.equals("black")){
					winner="white";
				} else if(Game.turn.equals("white")) {
						winner="black";
				}
				
				Main.turnText.setText("CHECKMATE. "+ winner.substring(0, 1).toUpperCase() + winner.substring(1) +" wins.");
			}
		}
		else {
			Board.check=-1;
		}
	}
	
	public void putPossible(int x, int y, Piece newPiece) {

		JLabel possibleL=new JLabel(possibleIcon);
		possibleL.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				clearMarks();
				putPiece(x,y, newPiece);
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		Board.squareArray[x][y].add(possibleL);
		Board.squareArray[x][y].setOpaque(false);
	}	
	
	
	public void putPiece(int x, int y, Piece capturer) {
		//capturer or picked piece that's trying to move to empty square
		Board.squareArray[x][y].add(capturer);
		Board.squareArray[x][y].occupy(capturer);
		int oldPosition=capturer.getPosition();
		Board.squareArray[oldPosition/10][oldPosition%10].removeAll();	//remove capturer from old position
		Board.squareArray[oldPosition/10][oldPosition%10].revalidate();
		Board.squareArray[oldPosition/10][oldPosition%10].repaint();
		Board.squareArray[oldPosition/10][oldPosition%10].empty();		
		capturer.setPosition(x, y); //update capturer's position
		board.revalidate();
		board.repaint();
		nextTurn();
	}
	 public void initiateMaps() {

		 	// possible moves
		 	//the corresponding squares will be painted blue and contain the "possible" icon
			markPossible = new HashMap<Integer, Boolean>();	
			for (int n = 0; n < 8; n++) {
				for (int m = 0; m < 8; m++) {
					markPossible.put((10 * m) + n, false);
				}
			}
			
			// possible captures
			//the corresponding squares will be painted blue
			markCapture = new HashMap<Integer, Boolean>();	
			for (int n = 0; n < 8; n++) {
				for (int m = 0; m < 8; m++) {
					markCapture.put((10 * m) + n, false);
				}
			}
			
			// possible moves
			//used for checkmate calculations
			//the corresponding squares will NOT be painted
			unmarkedPossible = new HashMap<Integer, Boolean>();
			for (int n = 0; n < 8; n++) {
				for (int m = 0; m < 8; m++) {
					unmarkedPossible.put((10 * m) + n, false);
				}
			}
			
			// possible captures
			//used for checkmate calculations
			//the corresponding squares will NOT be painted
			unmarkedCapture = new HashMap<Integer, Boolean>();
			for (int n = 0; n < 8; n++) {
				for (int m = 0; m < 8; m++) {
					unmarkedCapture.put((10 * m) + n, false);
				}
			}
			
			// possible captures of enemy (threats)
			//used for check calculations
			//the corresponding squares will NOT be painted
			enemyCapture = new HashMap<Integer, Boolean>();
			for (int n = 0; n < 8; n++) {
				for (int m = 0; m < 8; m++) {
					enemyCapture.put((10 * m) + n, false);
				}
			}
		}
	
	// we mark the square as capturable not the piece. so update the following when the square's status changes
	 public void updateCapture(int x, int y) {	
			markCapture.put(10*x+y, true);	
		}
	 public void updatePossible(int x, int y) {
		 	markPossible.put(10*x+y, true);
	 }
	 public void updateUnmarkedPossible(int x, int y) {
		 	unmarkedPossible.put(10*x+y, true);
		}
	 public void updateUnmarkedCapture(int x, int y) {
		 unmarkedCapture.put(10*x+y, true);
	 }
	 public void updateEnemyCapture(int x, int y) {
		 enemyCapture.put(10*x+y, true);	 
	 }
	 
	 public <K, V> Set<Integer> getCapture() {
		    Set<Integer> keys = new HashSet<>();
		    for (Entry<Integer, Boolean> entry : markCapture.entrySet()) {
		        if (entry.getValue().equals(true)) {
		            keys.add(entry.getKey());
		        }
		    }
		    return keys;
		}

	 public <K, V> Set<Integer> getPossible() {
		 Set<Integer> keys = new HashSet<>();
		 for (Entry<Integer, Boolean> entry : markPossible.entrySet()) {
			 if (entry.getValue().equals(true)) {
				 keys.add(entry.getKey());
			 }
		 }
		 return keys;
	 }

	// remove possible move and capture marks (the blue paint and the "possible" icons)
	 public void clearMarks(){
			ArrayList<Integer> keys= new ArrayList<Integer>(); 
			for(Map.Entry <Integer,Boolean> entry: markPossible.entrySet()) {
				if(entry.getValue()) {
					keys.add(entry.getKey());
				}
			}
			for(int el: keys) {
				int x= el/10;
				int y=el%10;
				Board.squareArray[x][y].removeAll();
				Board.squareArray[x][y].revalidate();
				Board.squareArray[x][y].repaint();
				Board.squareArray[x][y].empty();
	
				markPossible.put((10 * x) + y, false);			
			}
			for(Map.Entry <Integer,Boolean> entry: markCapture.entrySet()) {
				if(entry.getValue()) {
					keys.add(entry.getKey());
				}
			}
			for(int el: keys) {
				int x= el/10;
				int y=el%10;
				markCapture.put((10 * x) + y, false);			
			}
			board.revalidate();
			board.repaint();
		}
	 
	 public void clearUnmarkedPossible(){
		 ArrayList<Integer> keys= new ArrayList<Integer>(); 
		 for(Map.Entry <Integer,Boolean> entry: unmarkedPossible.entrySet()) {
			 if(entry.getValue()) {
				 keys.add(entry.getKey());
			 }
		 }
		 for(int el: keys) {
			 int x= el/10;
			 int y=el%10;
			 unmarkedPossible.put((10 * x) + y, false);			
		 }	 
	 }
	 
	 public void clearEnemyCapture(){
		 ArrayList<Integer> keys= new ArrayList<Integer>(); 
		 for(Map.Entry <Integer,Boolean> entry: enemyCapture.entrySet()) {
			 if(entry.getValue()) {
				 keys.add(entry.getKey());
			 }
		 }
		 for(int el: keys) {
			 int x= el/10;
			 int y=el%10;
			 enemyCapture.put((10 * x) + y, false);			
		 }	 
	 }
	 
	 public void clearUnmarkedCapture(){
		 ArrayList<Integer> keys= new ArrayList<Integer>(); 
		 for(Map.Entry <Integer,Boolean> entry: unmarkedCapture.entrySet()) {
			 if(entry.getValue()) {
				 keys.add(entry.getKey());
			 }
		 }
		 for(int el: keys) {
			 int x= el/10;
			 int y=el%10;
			 unmarkedCapture.put((10 * x) + y, false);			
		 }	 
	 }
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}
	@Override
	public void mousePressed(MouseEvent e) {
	}
	@Override
	public void mouseReleased(MouseEvent e) {
	}
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}

	public void putCapture(int i, int y) {
		board.revalidate();
		board.repaint();
	}

	 public <K, V> Set<Integer> getUnmarkedCapture() {
		    Set<Integer> keys = new HashSet<>();
		    for (Entry<Integer, Boolean> entry : unmarkedCapture.entrySet()) {
		        if (entry.getValue().equals(true)) {
		            keys.add(entry.getKey());
		        }
		    }
		    return keys;
		}
	 public <K, V> Set<Integer> getUnmarkedPossible() {
		 Set<Integer> keys = new HashSet<>();
		 for (Entry<Integer, Boolean> entry : unmarkedPossible.entrySet()) {
			 if (entry.getValue().equals(true)) {
				 keys.add(entry.getKey());
			 }
		 }
		 return keys;
	 }
	 public <K, V> Set<Integer> getEnemyCapture() {
		 Set<Integer> keys = new HashSet<>();
		 for (Entry<Integer, Boolean> entry : enemyCapture.entrySet()) {
			 if (entry.getValue().equals(true)) {
				 keys.add(entry.getKey());
			 }
		 }
		 return keys;
	 }
}