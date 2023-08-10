import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*; 

public class Game extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener{

	private BufferedImage back; 
	private int key;
	private int SCREEN_WIDTH = 720, SCREEN_HEIGHT = 720;
	private int BOX = 6;
	private int BOX_SIZE = SCREEN_WIDTH/BOX;
	private ImageIcon menu, menu2, game, xWin, oWin, tie, player_o, player_x;
	private char screen, backgrd;
	private int turn, amount_Win;
	private int x_Cor = 320, y_Cor = 320;
	private Board board;
	private int random_Int1 = 255, random_Int2 = 255, random_Int3 = 255;
	public boolean reset; 
	
	public Game() {
		new Thread(this).start();	
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		key = -1;
		board = new Board();
		menu = new ImageIcon("menu.png");
		menu2 = new ImageIcon("menu2.png");
		game = new ImageIcon("background.jpg");
		player_o = new ImageIcon("player_o.png");
		player_x = new ImageIcon("player_x.png");
		tie = new ImageIcon("tie.png");
		xWin = new ImageIcon("x_wins.png");
		oWin = new ImageIcon("y_wins.png");
		screen = 'M';
		backgrd = 'A';
		turn = 0;
		reset = false;
		amount_Win = 3;
	} 

	public void run(){
		try{
			while(true) {
				Thread.currentThread().sleep(30);
				repaint();
			}
		}catch(Exception e){
			
		}
	}

	public void paint(Graphics g){

		Graphics2D twoDgraph = (Graphics2D) g; 
		if(back ==null)
			back=(BufferedImage)( (createImage(getWidth(), getHeight()))); 


		Graphics g2d = back.createGraphics();

		g2d.clearRect(0,0,getSize().width, getSize().height);
		drawPanel(g2d);
		twoDgraph.drawImage(back, null, 0, 0);

	}
	
	public void backGround(Graphics g){
		g.drawImage(game.getImage(), 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, this);
	}
	
	public void drawPanel(Graphics g){
		switch(screen){
		case 'M':
			g.drawImage(menu.getImage(), 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, this);
			break;
		case 'P':
			g.drawImage(menu2.getImage(), 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, this);
			break;
		case 'G': 
			backGround(g);
			drawGridLines(g);
			drawArrow(g);
			drawChar(g);
			results();
			break;
		case 'O': 
			g.drawImage(xWin.getImage(), 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, this);
			restart();
			break;
		case 'S':
			g.drawImage(oWin.getImage(), 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, this);
			restart();
			break;
		case 'T': 
			g.drawImage(tie.getImage(), 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, this);
			restart();
			break;
		}
	}

	public void drawGridLines(Graphics g){
		Color random_Color = new Color(random_Int1, random_Int2, random_Int3);
		g.setColor(random_Color);
		Graphics2D g2 = (Graphics2D) g;
	    g2.setStroke(new BasicStroke(4));
		for (int i = 0; i <SCREEN_HEIGHT/BOX_SIZE; i++){
			g.drawLine(i*BOX_SIZE, 0, i*BOX_SIZE, SCREEN_HEIGHT);
			g.drawLine(0, i*BOX_SIZE, SCREEN_WIDTH, i*BOX_SIZE);
		}
	}
	
	public void drawArrow(Graphics g){
		if (turn %2 == 0)
			g.drawImage(player_o.getImage(), x_Cor, y_Cor, BOX_SIZE/3, BOX_SIZE/2, this);
		else g.drawImage(player_x.getImage(), x_Cor, y_Cor, 50, 75, this);
	}
	
	public void drawValue(int col, int row, Graphics g){
		if (board.getValues(col, row).equals("~")){
			
		}
		if (board.getValues(col, row).equals("X"))
			g.drawImage(player_o.getImage(), BOX_SIZE*col + BOX_SIZE/3 , BOX_SIZE*row + BOX_SIZE/4, BOX_SIZE/3, BOX_SIZE/2, this);
		if (board.getValues(col, row).equals("O"))
			g.drawImage(player_x.getImage(), BOX_SIZE*col + BOX_SIZE/3 , BOX_SIZE*row + BOX_SIZE/4, BOX_SIZE/3, BOX_SIZE/2, this);
	}
	
	public void drawChar(Graphics g){
		for (int i = 0; i < BOX; i++){
			for (int j = 0; j < BOX; j++){
				drawValue(i, j, g);
			}
		}
	}
	
	public void randomColor(){
		random_Int1 = (int)(Math.random()*((255 - 0 )+ 1) + 0);
		random_Int2 = (int)(Math.random()*((255 - 0 )+ 1) + 0);
		random_Int3 = (int)(Math.random()*((255 - 0 )+ 1) + 0);
	}
	
	public boolean checkTie(){
		if (!(turn < BOX*BOX))
			return true;
		else return false;
	}
	
	public boolean checkX(){
		boolean x1= false, x2 = false;
		for (int i = 0; i < BOX; i++){
			if(board.rowColString(i, getValues("X", amount_Win)))
					x1 = true;
			if(x1)
				break;
		}
		if(!x1){
			for (int h = 0; h < BOX ; h++){
				for (int i = 0; i < BOX; i++){ 
					if(board.diagonalString1(0 + h, i, getValues("X", amount_Win)) || board.diagonalString2(5 - h, i, getValues("X", amount_Win)))
						x2 = true;
				}	
				if(x2)
					break;
			}
		}
		if(x1||x2)
			return true;
		else return false;
	}
	
	public boolean checkO(){
		boolean o1= false, o2 = false;
		for (int i = 0; i < BOX; i++){
			if(board.rowColString(i, getValues("O", amount_Win)))
					o1 = true;
			if(o1)
				break;
		}
		if(!o1){
			for (int h = 0; h < BOX ; h++){
				for (int i = 0; i < BOX; i++){ //BOX - 1 - h
					if(board.diagonalString1(0 + h, i, getValues("O", amount_Win)) || board.diagonalString2(5 - h, i, getValues("O", amount_Win)))
						o2 = true;
				}	
				if(o2)
					break;
			}
		}
		if(o1||o2)
			return true;
		else return false;
	}
	
	public String getValues (String a, int x){
		String c ="";
		for (int i = 0; i <x; i++){
			c += a;
		}
		return c;
	}
	
	public void results(){
		if(checkX())
			screen = 'O';
		else if(checkO())
			screen = 'S';
		else if(screen != 'O' && screen !='S' && checkTie()){
			screen = 'T';
		}
	}
	
	public void resetColor(){
		random_Int1 = 255; 
		random_Int2 = 255; 
		random_Int3 = 255;
	}
	
	public void restart(){
		if (reset){
			board.restart();
			screen = 'M';
			backgrd = 'A';
			turn = 0;
			resetColor();
			reset = false;
		}
	}
		
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void keyReleased(KeyEvent e) {
		key = e.getKeyCode();
		if (screen == 'G'){
			if (key == 38)
				backgrd = 'A';	
			if (key == 40)
				resetColor();
			if(key == 32){
				screen = 'M';
				reset = true;
				restart();
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		switch (screen){
		case 'G': 
			if (arg0.getX() > 0 && arg0.getX() < 680){
				if(arg0.getY()> 0 && arg0.getY() < 625){
					x_Cor = arg0.getX();	
					y_Cor = arg0.getY();	
				}
			}
			if(arg0.getX() < 15){
				backgrd = 'B';
				randomColor();
			}
			if(arg0.getX() > 665){
				backgrd = 'C';
				randomColor();
			}
			if(arg0.getY() < 15){
				backgrd = 'D';
				randomColor();
			}
			if(arg0.getY() > 610){
				backgrd = 'E';
			randomColor();
			}
		break;
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		x_Cor = arg0.getX();
		y_Cor = arg0.getY();
		switch (screen){
		case 'M': 
			if (arg0.getY()>460 && arg0.getY()<560){
				if (arg0.getX()>380 && arg0.getX()<666)
					screen = 'P'; 
			}
			if (arg0.getY()>586 && arg0.getY()<686 &&
					arg0.getX()>218 && arg0.getX()<504)
				System.exit(0);
			break;
		case 'P':
			if(arg0.getY()>498 && arg0.getY()<568){
				if(arg0.getX()>42&&arg0.getX()<240){
					amount_Win = 3;
					screen = 'G';
				}
				if(arg0.getX()>260&&arg0.getX()<458){
					amount_Win = 4;
					screen = 'G';
				}
				if(arg0.getX()>482&&arg0.getX()<680){
					amount_Win = 5;
					screen = 'G';
				}
			}
			if(arg0.getY()>614 && arg0.getY()<684 && 
					arg0.getX()>260&&arg0.getX()<458)
				screen = 'M';
			break;
		case 'G':
			for (int i = 0; i < BOX; i++){
				for (int j = 0; j < BOX; j++){
					if (arg0.getX() > i*BOX_SIZE && arg0.getX() < (i + 1)*BOX_SIZE 
							 && arg0.getY() > j*BOX_SIZE && arg0.getY() < (j + 1)*BOX_SIZE){
						if (board.getValues(i,j).equals("~")){
							turn++;
							if (turn%2 != 0)
								board.setValue(i, j, "X");
							else board.setValue(i, j, "O");
						}
					}	
				}
			}
			break;
		case 'O': 
			if (arg0.getY()>548 && arg0.getY()<648){
				if (arg0.getX()>328 && arg0.getX()<614)
					reset = true;
			}
			break;
		case 'S':
			if (arg0.getY()>548 && arg0.getY()<648){
				if (arg0.getX()>328 && arg0.getX()<614)
					reset = true;
			}
			break;
		case 'T':
			if (arg0.getY()>548 && arg0.getY()<648){
				if (arg0.getX()>328 && arg0.getX()<614)
					reset = true;
			}
			break;
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}
}
