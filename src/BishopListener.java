public class BishopListener extends MoveListener {

	public BishopListener(Move move) {
		super(move);
		type="bishop";
	}
	
	public BishopListener(Move move, Board board, String color) {
		super(move, board, color);
		type="bishop";
	}
	
	@Override
	void calculateMoves(int x, int y, Piece newPiece, boolean mark,boolean calculateCheck) {
		// look down and left
		for (int i = 1; (i <= x)&&(i <= 7 - y); i++) {
			if(x-i<0||y+i>7) {
				break;
			}	
			// dont look off the board or on the direction that is blocked by ally
			else if(Board.squareArray[x-i][y+i].isEmpty(calculateCheck)) {	//if adjacent is empty put possible move
				calculatePossibleMoves(x-i,y+i,newPiece,mark,calculateCheck);
			} 
			else if(newPiece.color.equals(Board.squareArray[x-i][y+i].getCurrentPiece(calculateCheck).color)) {
				break;
			}
			else {	//if adjacent not empty and not ally, it must be enemy: mark it capturable
				calculatePossibleCaptures(x-i,y+i,newPiece, mark,calculateCheck);
				break;
			}
		}
		
		//look down and right
		for (int i = 1; (i <= 7 - y)&&(i <= 7 - x); i++) {
			if((y+i>7)||(x+i>7)) {
				break;
			}
			else if(Board.squareArray[x+i][y+i].isEmpty(calculateCheck)) {
				calculatePossibleMoves(x+i,y+i,newPiece,mark,calculateCheck);
			}		
			else if(newPiece.color.equals(Board.squareArray[x+i][y+i].getCurrentPiece(calculateCheck).color)) {
				break;
			}
			else {
				calculatePossibleCaptures(x+i,y+i,newPiece,mark,calculateCheck);
				break;
			}
		}
		
		//look up and left
		for (int i = 1; (i <= y)&&(i <= x); i++) {
			if((y-i<0)||(x-i<0)) {
				break;
			}
			else if(Board.squareArray[x-i][y-i].isEmpty(calculateCheck)) {
				calculatePossibleMoves(x-i,y-i,newPiece,mark,calculateCheck);
			}
			else if (newPiece.color.equals(Board.squareArray[x-i][y-i].getCurrentPiece(calculateCheck).color)) {
				break;
			}
			else {
				calculatePossibleCaptures(x-i,y-i,newPiece,mark,calculateCheck);
				break;
			}
		}
		
		//look up and right
		for (int i = 1; (i <= 7 - x)&&(i <= y); i++) {
			if((x+i>7)||(y-i<0)) {
				break;
			}
			else if(Board.squareArray[x+i][y-i].isEmpty(calculateCheck)) {
				calculatePossibleMoves(x+i,y-i,newPiece,mark,calculateCheck);
			}
			else if(newPiece.color.equals(Board.squareArray[x+i][y-i].getCurrentPiece(calculateCheck).color)) {
				break;
			}
			else {
				calculatePossibleCaptures(x+i,y-i,newPiece,mark,calculateCheck);
				break;
			}
		}
	}
}