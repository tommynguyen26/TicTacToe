import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame{
	private static final int WIDTH =720;
	private static final int HEIGHT=720;
	private Game game;
	
	public Main () {
		super("TicTacToe");
		setSize(WIDTH, HEIGHT);
		Game play = new Game();
		((Component) play).setFocusable(true);
		
		setBackground(Color.black);
		
		getContentPane().add(play);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);
	
	}
	
	public static void main(String[] args) {
		Main run = new Main();
		
	}

}
