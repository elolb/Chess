import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTextArea;

//when adding menu etc add as a panel to jf. because board panel's top left is where the board starts.

public class Board extends JPanel{
	private ImageIcon boardIcon = new ImageIcon("img/board.png");
	private GridLayout gridLayout;
	public static SquarePanel[][] squareArray;
	public static int check;
	public static boolean isRandom;
	Random rand;
	SquarePanel squarePanel;
	Piece piece, bKing, wKing;
	int randXInt, randYInt;
	Move move;
	Color blue;
	int squareX, squareY;
	
	Board() {
		isRandom = false;	// assume regular board setup
		blue= new Color (47/255f,129/255f,215/255f, 0.5f);
		check=-1;	// not in check
		// this border helps the pieces panel align perfectly with the board 
        this.setBorder(BorderFactory.createEmptyBorder(40, 49, 40, 18));
        // uncomment below line to view the border
        //this.setBorder(BorderFactory.createMatteBorder(40, 49, 40, 18, new Color(47/255f,129/255f,215/255f, 0.5f)));
        this.setVisible(true);
		this.setOpaque(false);
		createNewBoard();
	}
	
	public void paintComponent(Graphics g) {
		int squareSideLength=64;
		int offset=45;
		
		super.paintComponent(g);
		g.drawImage(boardIcon.getImage(), 0, 0, null);
		
		// paint possible moves blue
		Set<Integer> possibleSquares =move.getPossible();
		g.setColor(blue);
		for(int square:possibleSquares) {
			squareX=square/10; squareY=square%10;
			g.fillRoundRect(offset+(squareX*squareSideLength), offset+(squareY*squareSideLength), squareSideLength, squareSideLength, 10, 10);
		}
		
		// paint possible captures blue
		possibleSquares =move.getCapture();
		for(int square:possibleSquares) {
			squareX=square/10; squareY=square%10;
			g.fillRoundRect(offset+(squareX*squareSideLength), offset+(squareY*squareSideLength), squareSideLength, squareSideLength, 10, 10);
		}
		
		// if in check, paint king's square red
		if(check >= 0) {
			g.setColor(Color.red);
			squareX=check/10; squareY=check%10;
			g.fillRoundRect(offset+(squareX*squareSideLength), offset+(squareY*squareSideLength), squareSideLength, squareSideLength, 10, 10);
			g.setColor(blue);
		}
	}
	
	
	public void createNewBoard() {
		this.removeAll();
		move=new Move(this);
		move.initiateMaps();
		squareArray = new SquarePanel[8][8];	// holds a SquarePanel for each square on the board
		gridLayout = new GridLayout(8, 8, 9, -4);
		Game.turn="white";
		this.setLayout(gridLayout);
		Dimension horizontalGap = new Dimension(0,1);
		for (int n = 0; n < 8; n++) {
			for (int m = 0; m < 8; m++) {
				squareArray[m][n] = new SquarePanel(10*m+n);
				squareArray[m][n].setLayout(new FlowLayout());
//				squareArray[m][n].setOpaque(true);
				add(squareArray[m][n]);	// add square panel to board panel's gridLayout
			}
			add(Box.createRigidArea(horizontalGap));	// add space to the right of the board
		}
		if(isRandom) {
			setRandomBoard();
		} else {
	  		setBoard();
		}
		this.revalidate();
		this.repaint();
	}
	
	// the only restriction is that both sides must have one and only one king
	//excluding the kings there will be max 13 pieces in total
	//can randomly be a few pieces less than 13 if the randomly chosen location was already occupied by another piece
	 public void setRandomBoard() {

		rand = new Random();
		randXInt = rand.nextInt(8);
		randYInt = rand.nextInt(8);
		bKing = new Piece("black", "king",move);
		do	{
			randXInt = rand.nextInt(8);
			randYInt = rand.nextInt(8);
		}
		while(!squareArray[randXInt][randYInt].isEmpty(false));
			
		squareArray[randXInt][randYInt].add(bKing);
		bKing.setPosition(randXInt,randYInt);
		squareArray[randXInt][randYInt].occupy(bKing);
			
		wKing = new Piece("white", "king",move);
			
		do	{
			randXInt = rand.nextInt(8);
			randYInt = rand.nextInt(8);
		}
		while(!squareArray[randXInt][randYInt].isEmpty(false));
				
		squareArray[randXInt][randYInt].add(wKing);
		wKing.setPosition(randXInt,randYInt);
		squareArray[randXInt][randYInt].occupy(wKing);
		
		
			
		for (int i = 1; i <= 13; i++) {
			randXInt = rand.nextInt(8);
			randYInt = rand.nextInt(8);
			switch(rand.nextInt(10)) {	// create getRandomPiece method in Piece class and move this whole thing there?
				case 0:
					piece = new Piece("black", "rook",move);
					break;
				case 1:
					piece = new Piece("white", "rook",move);
					break;
				case 2:
					piece = new Piece("black", "knight",move);
					break;
				case 3:
					piece = new Piece("white", "knight",move);
					break;
				case 4:
					piece = new Piece("black", "bishop",move);
					break;
				case 5:
					piece = new Piece("white", "bishop",move);
	
					break;
				case 6:
					piece = new Piece("black", "queen",move);
	
					break;
				case 7:
					piece = new Piece("white", "queen",move);
	
					break;
				
				case 8:
					piece = new Piece("black", "pawn",move);
	
					break;
				case 9:						
					piece = new Piece("white", "pawn",move);

					break;
			}

				if (squareArray[randXInt][randYInt].isEmpty(false)) {
					squareArray[randXInt][randYInt].add(piece);
					//squareArray[randXInt][randYInt].setOpaque(true); 
			//uncomment and watch the opaqueness while moving pieces. it seems inconsistent? is that a problem?
					piece.setPosition(randXInt,randYInt);
					squareArray[randXInt][randYInt].occupy(piece);
				}
		}
	 }

		public void setBoard(){
			// WRITING THIS PART WITH FOR LOOPS DOES NOT REDUCE LINE COUNT
			//AND MAKES THE CODE LESS READABLE
			//THEREFORE I LEAVE IT AS IT IS
			Piece blackPawn;
			Piece whitePawn;
			for (int i = 0; i <= 7; i++) {
				blackPawn = new Piece("black", "pawn",move);
				whitePawn= new Piece("white","pawn", move);
				squareArray[i][1].add(blackPawn);
				squareArray[i][6].add(whitePawn);
				blackPawn.setPosition(i,1);
				whitePawn.setPosition(i,6);
				squareArray[i][1].occupy(blackPawn);	
				squareArray[i][6].occupy(whitePawn);		
			}
			
			Piece rook = new Piece("black", "rook",move);  
			Piece knight = new Piece("black","knight",move);
			Piece bishop = new Piece("black","bishop",move);
			Piece queen = new Piece("black","queen",move);  
			Piece king = new Piece("black","king",move);    
			
			squareArray[0][0].add(rook);
			squareArray[1][0].add(knight);
			squareArray[2][0].add(bishop);
			squareArray[3][0].add(queen);
			squareArray[4][0].add(king);
			rook.setPosition(0,0);
			knight.setPosition(1,0);
			bishop.setPosition(2,0);
			queen.setPosition(3,0);
			king.setPosition(4,0);
			squareArray[0][0].occupy(rook);	
			squareArray[1][0].occupy(knight);	
			squareArray[2][0].occupy(bishop);	
			squareArray[3][0].occupy(queen);	
			squareArray[4][0].occupy(king);	

			rook = new Piece("white", "rook",move);
			knight= new Piece("white","knight",move);
			bishop= new Piece("white","bishop",move);
			queen= new Piece("white","queen",move);
			king= new Piece("white","king",move);
			squareArray[0][7].add(rook);
			squareArray[1][7].add(knight);
			squareArray[2][7].add(bishop);
			squareArray[3][7].add(queen);
			squareArray[4][7].add(king);
			rook.setPosition(0,7);
			knight.setPosition(1,7);
			bishop.setPosition(2,7);
			queen.setPosition(3,7);
			king.setPosition(4,7);
			squareArray[0][7].occupy(rook);	
			squareArray[1][7].occupy(knight);	
			squareArray[2][7].occupy(bishop);	
			squareArray[3][7].occupy(queen);	
			squareArray[4][7].occupy(king);	
			bishop= new Piece("black","bishop",move);
			squareArray[5][0].add(bishop);
			bishop.setPosition(5,0);
			squareArray[5][0].occupy(bishop);	
			bishop= new Piece("white","bishop",move);
			squareArray[5][7].add(bishop);
			bishop.setPosition(5,7);
			squareArray[5][7].occupy(bishop);	
				
			knight= new Piece("black","knight",move);
			squareArray[6][0].add(knight);
			knight.setPosition(6,0);
			squareArray[6][0].occupy(knight);	
			knight= new Piece("white","knight",move);
			squareArray[6][7].add(knight);
			knight.setPosition(6,7);
			squareArray[6][7].occupy(knight);	
			
			rook= new Piece("black","rook",move);
			squareArray[7][0].add(rook);
			rook.setPosition(7,0);
			squareArray[7][0].occupy(rook);	
			rook= new Piece("white","rook",move);
			squareArray[7][7].add(rook);
			rook.setPosition(7,7);
			squareArray[7][7].occupy(rook);	
		}
		
		public boolean isSquareCapturable(int position) {
			return Move.markCapture.get(position);
		}
}