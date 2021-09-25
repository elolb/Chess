public class RookListener extends MoveListener {

	public RookListener(Move move) {
		super( move);
		type="rook";
	}
	
	public RookListener(Move move, Board board,String color) {
		super( move, board,color);
		type="rook";
	}
	
	@Override
	void calculateMoves(int x, int y, Piece newPiece, boolean mark,boolean calculateCheck) {
	
		//look left
		for (int i = 1; i <= x; i++) {
			if(x-i<0) {
				break;
			}	//don't look off the board 
			
			else if(Board.squareArray[x-i][y].isEmpty(calculateCheck)) {	//if adjacent is empty put possible move
				calculatePossibleMoves(x-i,y, newPiece, mark,calculateCheck);
				continue;
			}
			else if(newPiece.color.equals(Board.squareArray[x-i][y].getCurrentPiece(calculateCheck).color)) {
				break;
			}
			else {	//if adjacent not empty and not ally, it must be enemy: mark it capturable
				calculatePossibleCaptures(x-i, y, newPiece, mark,calculateCheck);
				break;
			}
		}
		
		//look right
		for (int i = 1; i <= 7 - x; i++) {
			if(x+i>7) {
				break;
			}
			else if(Board.squareArray[x+i][y].isEmpty(calculateCheck)) {
				calculatePossibleMoves(x+i, y, newPiece, mark,calculateCheck);
				continue;
			}
			else if(newPiece.color.equals(Board.squareArray[x+i][y].getCurrentPiece(calculateCheck).color)){
				break;
			}
			else {
				calculatePossibleCaptures(x+i, y, newPiece, mark,calculateCheck);
				break;
			}
		}
		
		//look up
		for (int i = 1; i <= y; i++) {
			if(y-i<0) {
				break;
			}
			else if(Board.squareArray[x][y-i].isEmpty(calculateCheck)) {
				calculatePossibleMoves(x, y-i, newPiece, mark,calculateCheck);
			}
			else if(newPiece.color.equals(Board.squareArray[x][y-i].getCurrentPiece(calculateCheck).color)) {
				break;
			}
			else {
				calculatePossibleCaptures(x, y-i, newPiece, mark,calculateCheck);
				break;
			}
		}
		
		//look down
		for (int i = 1; i <= 7 - y; i++) {
			if(y+i>7) {
				break;
			}
			else if(Board.squareArray[x][y+i].isEmpty(calculateCheck)) {
				calculatePossibleMoves(x, y+i, newPiece, mark,calculateCheck);
			}		
			else if(newPiece.color.equals(Board.squareArray[x][y+i].getCurrentPiece(calculateCheck).color)) {
				break;
			}
			else {
				calculatePossibleCaptures(x, y+i, newPiece, mark,calculateCheck);				
				break;
			}
		}		
	}	
}
