import java.util.Arrays;
public class Board{ 
	private String[][] gameBoard;
	private int col, row;

	public Board(){
		col = 6;
		row = 6;
		gameBoard = new String[col][row];
		setBoard();
	}

	public void setBoard(){
		for (int i = 0; i < col; i++){
			for (int j = 0; j < row; j++){
				gameBoard[i][j] = "~";
			}
		}
	}


	public String getValues(int co, int ro){
		return gameBoard[co][ro];
	}
	
	public void setValue(int co, int ro, String a){
		gameBoard[co][ro] = a;
	}


	public boolean rowColString(int v, String value){
		String n = "", c = "";
		boolean q = false;
		for (int i = 0; i < row; i++){
				n += gameBoard[v][i];
				c+= gameBoard[i][v];
		}
		if (n.contains(value) || c.contains(value))
			q = true;
		return q;
		
	}
	
	public boolean diagonalString1(int x, int y, String value){
		String d ="";
		boolean p = false;
		while(x < row && y < row){
			d+=gameBoard[x][y];
			x++;
			y++;
		}
		if (d.contains(value))
			p = true;
		return p;
	}
	
	public boolean diagonalString2(int x, int y, String value){
		String e ="";
		boolean r = false;
		while(x < row && x > -1 && y < row ){
			e+=gameBoard[x][y];
			x--;
			y++;
		}
		if (e.contains(value))
			r = true;
		return r;
	}
	
	public void restart(){
		setBoard();
	}
}