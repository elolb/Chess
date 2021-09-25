public class KingListener extends MoveListener {

	public KingListener(Move move) {
		super(move);
		type="king";
	}

	@Override
	void calculateMoves(int x, int y, Piece newPiece,boolean mark,boolean calculateCheck) {
		for(int i=-1;i<=1;i++) {
			for(int m=-1;m<=1;m++) {
				if((m==0&&i==0)||(x-i<0)||(x-i>7)||(y+m)<0||(y+m)>7) {
					continue;
				}
				if(Board.squareArray[x-i][y+m].isEmpty(calculateCheck)) {	//if adjacent is empty put possible move
					calculatePossibleMoves(x-i,y+m,newPiece,mark,calculateCheck);
				}
				else if(Game.turn.equals(Board.squareArray[x-i][y+m].getCurrentPiece(calculateCheck).color)) {
					continue;
				}
				else {	//if adjacent not empty and not ally, it must be enemy: mark it capturable
					calculatePossibleCaptures(x-i,y+m,newPiece,mark,calculateCheck);
				}
			}
		}
	}
}
