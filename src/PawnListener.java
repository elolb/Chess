public class PawnListener extends MoveListener {

	public PawnListener(Move move) {
		super(move);
		type="pawn";
	}

	@Override
	void calculateMoves(int x, int y, Piece newPiece, boolean mark, boolean calculateCheck) {
		// in random mode it can move 2 spaces if in "initial" square even if it moved before
		//and only can move 2 spaces if there. moves once if not there even if moving for the first time.

		if(newPiece.color.equals("black")) {
			//look down
			if((y==7)) {;
				//TODO stub
				//opposite end of the board.
				//piece change code here
			}
			else if((Board.squareArray[x][y+1].isEmpty(calculateCheck))) {
				if((y==1)&&(Board.squareArray[x][3].isEmpty(calculateCheck))&&newPiece.firstMove) {
					calculatePossibleMoves(x,3,newPiece,mark,calculateCheck);
				}
				calculatePossibleMoves(x,y+1,newPiece,mark,calculateCheck);
			}
			
			//look down and left
				if((x-1<0)||(y+1>7)||Board.squareArray[x-1][y+1].isEmpty(calculateCheck)) {
					; // do nothing
				}
				else if(Board.squareArray[x-1][y+1].getCurrentPiece(calculateCheck).color.equals("white"))
				{
					calculatePossibleCaptures(x-1,y+1,newPiece,mark,calculateCheck);
				}
			//look down and right
				if(x+1>7||(y+1>7)||Board.squareArray[x+1][y+1].isEmpty(calculateCheck)) {
					; // do nothing
				}
				else if(Board.squareArray[x+1][y+1].getCurrentPiece(calculateCheck).color.equals("white"))
				{
					calculatePossibleCaptures(x+1,y+1,newPiece,mark,calculateCheck);
				}
			
		} 
		
		else if(newPiece.color.equals("white")) {
			//look up
			if((y==0)) {;
				//TODO stub
				//opposite end of the board.
				//piece change code here
			}
			else if((Board.squareArray[x][y-1].isEmpty(calculateCheck))) {
				if((y==6)&&(Board.squareArray[x][4].isEmpty(calculateCheck))&&newPiece.firstMove) {
					calculatePossibleMoves(x,4,newPiece,mark,calculateCheck);
				}
				calculatePossibleMoves(x,y-1,newPiece,mark,calculateCheck);
			}
			
			//look up and left
			if((x-1<0)||(y-1<0)||Board.squareArray[x-1][y-1].isEmpty(calculateCheck)) {
				; // do nothing
			}
			else if(Board.squareArray[x-1][y-1].getCurrentPiece(calculateCheck).color.equals("black"))
			{
				calculatePossibleCaptures(x-1,y-1,newPiece,mark,calculateCheck);
			}
			
			//look down and right
			if((x+1>7||(y-1<0))||Board.squareArray[x+1][y-1].isEmpty(calculateCheck)) {
				;	// do nothing
				}
			else if(Board.squareArray[x+1][y-1].getCurrentPiece(calculateCheck).color.equals("black"))
			{
				calculatePossibleCaptures(x+1,y-1,newPiece,mark,calculateCheck);
			}
		}
	}
}