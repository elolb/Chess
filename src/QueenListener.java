public class QueenListener extends MoveListener {

	public QueenListener(Move move) {
		super(move);
		type="queen";
	}
	
	public QueenListener(Move move, Board board, String color) {
		super(move, board, color);
		type="queen";
	}

	@Override
	void calculateMoves(int x, int y, Piece piece, boolean mark,boolean calculateCheck) {
		System.out.println("calculating for queen start");
		RookListener rookListener=new RookListener(move, board, color);
		BishopListener bishopListener=new BishopListener(move, board, color);
		rookListener.calculateMoves(x, y, piece, mark,calculateCheck);
		bishopListener.calculateMoves(x, y, piece, mark, calculateCheck);
		System.out.println("calculating for queen over");
	}
}
