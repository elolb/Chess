import java.util.Set;

public class Game {
	public static String turn;
	boolean isCheck, isCheckmate, isValid;
	Move move;
	int oldLocation, allyLocation;
	Piece currentPiece, oldPiece, enemy, ally;
	MoveListener listener;
	String enemyColor;
	
	Game(Move move){
		this.move=move;
	}
	
	private int findKing(String color) {
		int location=0;
	
		for(SquarePanel[] panelArray:Board.squareArray) {
			for(SquarePanel piece:panelArray) {
					currentPiece=piece.currentPiece;

				if(currentPiece==null) {continue;}
				if(currentPiece.type.equals("king")&&currentPiece.color.equals(color)) {
					location=currentPiece.position;
				}
			}
		}
		return location;
	}

	// check if move is legal
	public boolean validateMove(int locationX, int locationY, Piece newPiece) {
		
		System.out.println("validating move "+(locationX+1)+" "+(locationY+1));
		
		// do the move on imaginary board (put the piece in new location)
		oldLocation=newPiece.position;
		System.out.println("old "+(oldLocation+11));
		oldPiece=Board.squareArray[locationX][locationY].currentPiece;
//		System.out.println("old piece is "+oldPiece);
		Board.squareArray[locationX][locationY].empty();
		Board.squareArray[locationX][locationY].occupy(newPiece);
		Board.squareArray[oldLocation/10][oldLocation%10].empty();
		newPiece.position=locationX*10+locationY;
		System.out.println("GOING INTO CHECK CONTROL");
		System.out.println((locationX+1)+" "+(locationY+1)+" has piece " +Board.squareArray[locationX][locationY].currentPiece+ ". Should have "+ newPiece);
		isValid=!(isCheck(newPiece));
		
		// revert the imaginary move
		Board.squareArray[oldLocation/10][oldLocation%10].occupy(newPiece);
		Board.squareArray[locationX][locationY].empty();
		if(oldPiece!=null) {
			Board.squareArray[locationX][locationY].occupy(oldPiece);
		}
		newPiece.position=oldLocation;
		if(!isValid) {
			System.out.println("invalid move~~~~~~~~~~ ");
		}
		return isValid;
	}

	public boolean isCheck(Piece movedPiece) {
		System.out.println("IN CHECK. ");
		System.out.println("searching for "+movedPiece.color+" king...");
		int kingLocation=findKing(movedPiece.color);
		int enemyLocation=-1;
		if (Game.turn.equals("white")){
			enemyColor="black";
		}
		else {enemyColor="white";}
		System.out.println(movedPiece.color+" king: "+(kingLocation+11));
		boolean isCheck=false;
		if(kingLocation>=0)	// if finding king was successful
		{	
			outerloop:
				for(SquarePanel[] panelArray:Board.squareArray) {
				for(SquarePanel panel:panelArray) {
					currentPiece=panel.currentPiece;
					if(currentPiece==null) {continue;}
					
					if(currentPiece.color.equals(enemyColor)) {
							enemyLocation=currentPiece.position;	
					}
			if(enemyLocation<0){continue;}
			enemy=Board.squareArray[enemyLocation/10][enemyLocation%10].currentPiece;
			if(enemy==null) {continue;}
			System.out.println("enemy "+ enemy+" at "+(enemyLocation+11));
			listener=((MoveListener)Piece.mouseListeners.get(enemy.type));
			listener.setColor(enemy.color);
			listener.calculateMoves(enemyLocation/10, enemyLocation%10,new Piece(enemy.color,enemy.type,enemyLocation) ,false, false);
			Set<Integer> isThisKing=listener.move.getEnemyCapture();
			for(Integer loc:isThisKing) {
				System.out.println("and it can capture "+Board.squareArray[loc/10][loc%10].currentPiece+" at "+((loc/10)+1)+", "+((loc%10)+1));
				if(loc==kingLocation) {
					System.out.println("and since it can capture the king it is check.");
					isCheck=true;
					if(!movedPiece.type.equals("king")) {
						Board.check=kingLocation;
					}
					break outerloop;
				}
			}
			move.clearEnemyCapture();
		}

			}
		} 
		else {
			System.out.println("couldn't find king");
		}
		move.clearEnemyCapture();
		return isCheck;
	}

	public boolean isCheckmate() {
		move.clearUnmarkedCapture();
		move.clearUnmarkedPossible();
		isCheckmate=true;
		allyLocation=-1;
		for(SquarePanel[] panelArray:Board.squareArray) {
			for(SquarePanel panel:panelArray) {
				currentPiece=panel.currentPiece;
				if(currentPiece==null) {continue;}
				if(currentPiece.color.equals(turn)) {
					allyLocation=currentPiece.position;	
				}
		if(allyLocation<0){continue;}
		ally=Board.squareArray[allyLocation/10][allyLocation%10].currentPiece;
		if(ally==null) {continue;}
		System.out.println("ally "+ ally+" at "+(allyLocation+11));
		listener=((MoveListener)Piece.mouseListeners.get(ally.type));
		listener.setColor(ally.color);
		listener.calculateMoves(allyLocation/10, allyLocation%10,new Piece(ally.color,ally.type,allyLocation) ,false, true);
		Set<Integer> captures=listener.move.getUnmarkedCapture();
		Set<Integer> moves=listener.move.getUnmarkedPossible();
		
		System.out.println("captures empty "+captures.isEmpty());
		System.out.println(ally+" " +captures);
		System.out.println("moves empty "+moves.isEmpty());
		System.out.println(moves);
		
		// if there is no possible legal capture or move, it is checkmate
		if(!captures.isEmpty()||!moves.isEmpty()) {
				isCheckmate=false;
				break;
			}
		move.clearUnmarkedCapture();
		move.clearUnmarkedPossible();
		}	
		}
		return isCheckmate;
	}
}
