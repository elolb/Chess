public class KnightListener extends MoveListener {

	public KnightListener( Move move) {
		super(move);
		type="knight";
	}

	@Override
	void calculateMoves(int x, int y, Piece newPiece,boolean mark,boolean calculateCheck) {
		for(int i=-1; i<=1;i++) {
			if(i==0) {continue;}
			int m=2;
			if((x+i>=0)&&(y-m>=0)&&(x+i<=7)&&(y-m<=7)) {
				if(Board.squareArray[x+i][y-m].isEmpty(calculateCheck)) {
					calculatePossibleMoves(x+i,y-m,newPiece,mark,calculateCheck);
				} 
				else if(!Board.squareArray[x+i][y-m].getCurrentPiece(calculateCheck).color.equals(newPiece.color)){	//enemy
					calculatePossibleCaptures(x+i,y-m,newPiece,mark,calculateCheck);
				}
			}
			
			if((x+i>=0)&&(y+m>=0)&&(x+i<=7)&&(y+m<=7)) {
				if(Board.squareArray[x+i][y+m].isEmpty(calculateCheck)) {
					calculatePossibleMoves(x+i,y+m,newPiece,mark,calculateCheck);
				} 
				else if(!Board.squareArray[x+i][y+m].getCurrentPiece(calculateCheck).color.equals(newPiece.color)){	//enemy
					calculatePossibleCaptures(x+i,y+m,newPiece,mark,calculateCheck);
				}
			}
			
			if((x+m>=0)&&(y+i>=0)&&(x+m<=7)&&(y+i<=7)) {
				if(Board.squareArray[x+m][y+i].isEmpty(calculateCheck)) {
					calculatePossibleMoves(x+m,y+i,newPiece,mark,calculateCheck);
				} 
				else if(!Board.squareArray[x+m][y+i].getCurrentPiece(calculateCheck).color.equals(newPiece.color)){	//enemy
					calculatePossibleCaptures(x+m,y+i,newPiece,mark,calculateCheck);
				}
			}
			
			if((x-m>=0)&&(y+i>=0)&&(x-m<=7)&&(y+i<=7)) {
				if(Board.squareArray[x-m][y+i].isEmpty(calculateCheck)) {
					calculatePossibleMoves(x-m,y+i,newPiece,mark,calculateCheck);
				} 
				else if(!Board.squareArray[x-m][y+i].getCurrentPiece(calculateCheck).color.equals(newPiece.color)){	//enemy
					calculatePossibleCaptures(x-m,y+i,newPiece,mark,calculateCheck);
				}
			}	
		}
	}
}

