import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public abstract class MoveListener implements MouseListener {
	Piece piece;
	SquarePanel squarePanel;
	Move move;
	Board board;
	String color, type;
	boolean isCheck, isValid;
	int x, y, validLocation;

	public MoveListener(Move move) {
		this.move=move;
	}
	
	public MoveListener(Move move, Board board, String color) {
		this.move=move;
		this.board=board;
		this.color=color;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		piece=(Piece)e.getComponent();
		squarePanel=(SquarePanel)piece.getParent();
		board=(Board)squarePanel.getParent();
		System.out.println("-------------------");
		x=squarePanel.getX()/64;
		y=squarePanel.getY()/64;
		if (Game.turn.equals(piece.color)) {
			move.clearMarks();
			board.revalidate();
			board.repaint();
			move.setCurrentPiece(piece);
			calculateMoves(x, y, piece,true,true);
			squarePanel.revalidate();
			squarePanel.repaint();
		}
		else if(board.isSquareCapturable(squarePanel.position)) {
				move.capture(x,y);
				move.clearMarks();
		}
	}

	public void calculatePossibleMoves(int x, int y, Piece newPiece, boolean mark, boolean calculateCheck) {
		System.out.println("checking move "+ (x+1)+ " "+(y+1)+" for "+newPiece);
		if (calculateCheck) {
			isValid=move.game.validateMove(x, y, newPiece);
		}
		else {
			isValid=false;
			if(Board.squareArray[x][y].isEmpty(calculateCheck)) {
				isValid=true;
			}
		}
		if(mark) {
			if(isValid) {
				move.updatePossible(x,y);
				move.putPossible(x, y, newPiece);
			}
		}
		if(!mark&&!calculateCheck) {
			// do nothing
		}
		else {
			if(isValid) {
				System.out.println("add to unmarked move"+(10*x+y));
				move.updateUnmarkedPossible(x, y);
			}
		}
	}
	
	public void calculatePossibleCaptures(int x, int y, Piece newPiece, boolean mark, boolean check) {
		System.out.println("checking capture "+ (x+1)+ " "+(y+1)+" for "+newPiece);
		System.out.println("old piece"+ Board.squareArray[x][y].currentPiece);
		if (check) {
			isValid=move.game.validateMove(x, y, newPiece);
		}
		else {
			isValid=true;
		}
		if(mark) {
			if(isValid) {
				move.updateCapture(x,y);
				move.putCapture(x, y);
			}
		}
		if(!mark&&!check) {
			move.updateEnemyCapture(x,y);
		}
		else {
			if(isValid) {
				System.out.println("add to unmarked capture"+(10*x+y));
				move.updateUnmarkedCapture(x, y);
			}
		}
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

	public void setColor(String color) {
		this.color=color;
	}

	// to be overridden by the subclasses
	abstract void calculateMoves(int x, int y, Piece newPiece, boolean mark,boolean check);
}
