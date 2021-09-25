import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatIntelliJLaf;

public class Main {
	
	public static JTextField turnText = new JTextField("turn: white");
	
	public static void main(String[] args) {
		try {
		    UIManager.setLookAndFeel( new FlatIntelliJLaf() );
		} catch( Exception ex ) {
		    System.err.println( "Failed to initialize LaF" );
		}

		Board board=new Board();
		JFrame jf = new JFrame("Chess");
		JButton restartButton= new JButton("New game");
		restartButton.setFont(new Font(restartButton.getFont().getName(), restartButton.getFont().getStyle(),22));
		restartButton.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == restartButton)
				{
					board.createNewBoard();
					turnText.setText("turn: white");
					turnText.setForeground(Color.gray);
					Board.check=-1;
				}
			}
		});
		JCheckBox randomMode = new JCheckBox("Random mode");
		randomMode.addItemListener(new ItemListener() {    
             public void itemStateChanged(ItemEvent e) {                 
            	 Board.isRandom=!(Board.isRandom);
             }    
        });    
		
		randomMode.setFont(new Font(randomMode.getFont().getName(), randomMode.getFont().getStyle(),20));
		randomMode.setBackground(Color.WHITE);
		randomMode.setBorder(null);
		// panel to contain the board panel, the restart button, and the turn text
		JPanel jp=new JPanel();
		jp.setLayout(new GridBagLayout());
		GridBagConstraints con = new GridBagConstraints();
		con.gridx = 0;
		con.gridy = 0;
		jp.add(board, con);
		JPanel center=new JPanel();
		center.add(Box.createRigidArea(new Dimension(70,0)));
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.add(randomMode);
        center.add(Box.createRigidArea(new Dimension(0,30)));
		center.add(restartButton);
		center.setBackground(Color.WHITE);
		con.gridx = 1;
		con.gridy = 0;
		con.anchor = GridBagConstraints.LINE_END;
//		jp.add(random, con);
//		jp.add(restartButton, con);
		jp.add(center, con);
		jp.setBackground(Color.white);
//		jp.setOpaque(true);
		turnText.setFont(new Font("Serif", Font.BOLD, 22));
		turnText.setForeground(Color.gray);
		turnText.setCaretColor(Color.WHITE);
		turnText.setBorder(null);
		
		con.gridx = 1;
		con.anchor= GridBagConstraints.FIRST_LINE_END;
		jp.add(turnText, con);
		jf.add(jp);
		
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setPreferredSize(new Dimension(1000, 700));
		jf.pack();
		
		
		// uncomment lines below to position the frame at top right 
		//to make it easier to see the console during debugging
		
//		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//	    GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
//	    Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
//	    int x = (int) rect.getMaxX() - jf.getWidth();
//	    int y = (int) rect.getMinY();
//	    jf.setLocation(x, y);

		jf.setLocationRelativeTo(null);		//center frame
		jf.setVisible(true);
	   }	
}
