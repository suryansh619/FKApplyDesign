
package learnJava;

import java.util.*;
import javafx.util.Pair; 
class Rules{
	int Consecutive_Cells_To_Win;
	int Row_Rule , Column_Rule , Diagonal_Rule;
	
	{
		Row_Rule = 0;
		Column_Rule = 0;
		Diagonal_Rule = 0;
		Consecutive_Cells_To_Win = 0;
	}
	Rules(int Consecutive_Cells_To_Win,int row,int col,int diagonal)
	{
		this.Consecutive_Cells_To_Win=Consecutive_Cells_To_Win;
		this.Row_Rule=row;
		this.Column_Rule=col;
		this.Diagonal_Rule=diagonal;
	}
}

interface board_type{
	void display();
	boolean isAvailable(int x,int y);
	void makeMove(int x,int y,char z);
	int getRow();
	int getColumn();
	char getBoxChar(int i,int j);
	char[][] getBox();
	char[] getSymbolsUsed();
}
class Grid implements board_type{
	char[][] Box;
	int Rows, Column,no_of_symbols_used;
	char[] Symbols_used;
	Grid(int rows, int column,char[] symbols)
	{
		this.Rows = rows;
		this.Column = column;
		Box = new char [rows][column];
		for(int i=0;i<rows;i++)
			for(int j=0;j<column;j++)
				Box[i][j] = ' ';
		Symbols_used = symbols;
	}

	public void display()
	{
		for(int i=0;i<Rows;i++){
			for(int j=0;j<Column;j++){
				System.out.print(Box[i][j]);
				if(j != (Column-1))
					System.out.print('|');
			}
			System.out.println();
			if(i != Rows-1)
			{
				for(int k=0;k<Rows*2;k++)
					System.out.print('-');
				System.out.println();
			}
		}
		System.out.println();
	}

	public boolean isAvailable(int x,int y){
		if(Box[x][y] == ' ')
			return true;
		else
			return false;
	}

	public void makeMove(int x,int y,char symbol){
		this.Box[x][y] = symbol;
	}

	public int getRow(){
		return this.Rows;
	}

	public int getColumn(){
		return this.Column;
	}

	public char getBoxChar(int i,int j){
		return this.Box[i][j];
	}

	public char[][] getBox(){
		return this.Box;
	}

	public char[] getSymbolsUsed(){
		return this.Symbols_used;
	}


}
class Hex implements board_type{
	char[][] Box;
	int Rows, Column,no_of_symbols_used;
	int levels;
	char[] Symbols_used;
	Hex(int rows, int column,char[] symbols,int levels)
	{
		this.Rows = rows;
		this.Column = column;
		this.levels = levels;
		Box = new char [rows][column];
		for(int i=0;i<rows;i++)
			for(int j=0;j<column;j++)
				Box[i][j] = ' ';
		Symbols_used = symbols;
	}
	public void display(){
		int temp_row = Rows;
		int temp_column = Column;
		for(int i=0;i<temp_row;i++)
		{
			if(i%2!=0)
				System.out.print(' ');
			for(int j=0;j<temp_column;j++)
			{
				Pair<Integer,Integer>pair = new Pair<Integer,Integer>(i,j);
				// System.out.println(i + " " + j +" " + project.hashMap.get(pair));
				if(project.hashMap.get(pair) == null || project.hashMap.get(pair)>this.levels)
				{
						System.out.print("   ");
				}
				else
				{
					if(Box[i][j] == ' ')
					{

						System.out.print("--|");
					}
					else
					{

						System.out.print(Box[i][j]);System.out.print(Box[i][j] +"|");
					}
				}
			}
			System.out.println();

		}
		for(int i=0;i<temp_row;i++)
		{
			if(i%2!=0)
				System.out.print(' ');
			for(int j=0;j<temp_column;j++)
			{
				Pair<Integer,Integer>pair = new Pair<Integer,Integer>(i,j);
				// System.out.println(i + " " + j +" " + project.hashMap.get(pair));
				if(project.hashMap.get(pair) == null || project.hashMap.get(pair)>this.levels)
				{
						System.out.print("   ");
				}
				else
				{
					System.out.print("(" + (i+1) +",");System.out.print((j+1) + ")");System.out.print(" ");	
				}
			}
			System.out.println();

		}

	}

	public boolean isAvailable(int x,int y){
		if(Box[x][y] == ' ')
			return true;
		else
			return false;
	}

	public void makeMove(int x,int y,char symbol){
		System.out.println(symbol);
		this.Box[x][y] = symbol;
	}

	public char[][] getBox(){
		return this.Box;
	}


	public char[] getSymbolsUsed(){
		return this.Symbols_used;
	}

	public int getRow(){
		return this.Rows;
	}

	public int getColumn(){
		return this.Column;
	}

	public char getBoxChar(int i,int j){
		return this.Box[i][j];
	}
}

class Blocked_state{
	int[][] block_cells;
	Blocked_state(board_type board,int row,int col,int level){
		block_cells = new int[row][col];
		for(int i=0;i<row;i++){
			for(int j=0;j<col;j++){
				if(board instanceof Hex){
					Pair<Integer,Integer>pair = new Pair<Integer,Integer>(i,j);
					if(project.hashMap.get(pair) == null || project.hashMap.get(pair)>level)
						block_cells[i][j] = 1;
					else
						block_cells[i][j] = 0;
				}
				else
				{
					block_cells[i][j] = 0;	
				}
			}
		}
	}
	public void block_one_cell(int x,int y){
		block_cells[x][y] = 1;
	}
	public void block_one_grid(int x,int y){
		int a = x/3 * 3 , b = y/3 * 3;
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				block_cells[i+a][j+b] = 1;
			}
		}
	}
	public int status(int x,int y){
		return block_cells[x][y];
	}
}
interface Human {
	String getType();
	String getName();
	char getSymbol();
}

class Player implements Human{
	private String Name, Type;
	private char Symbol;

	Player(String name,String type,char symbol){
		this.Name = name;
		this.Type = type;
		this.Symbol = symbol;
	}

	public String getType(){ return this.Type; }
	public String getName(){ return this.Name; }
	public char getSymbol(){ return this.Symbol; }
}


interface checkingMethod{
	boolean check_win(board_type board,char symbol,Rules rules,Blocked_state blo);
}


class State implements checkingMethod{
	static int finished;
	//0 is for undecided
	// 1 is for Win
	// 2 is for draw
	{
		finished=0;
	}

	public void check_Status(board_type board,Player player,Rules rules,Blocked_state blo)
	{
		char temp_symbol_of_current_player = player.getSymbol();
		if(check_win(board,temp_symbol_of_current_player,rules,blo))
		{
			this.finished = 1;
		}
		else
		{
			int flag = 0;
			for(int i=0;i<board.getRow();i++){
				for(int j=0;j<board.getColumn();j++){
					if(blo.status(i,j) == 0){
						flag = 1;
					}
				}
			}

			// flag is 0 means there is no left spaces
			if(flag == 0)
				this.finished = 2;
			else
				this.finished = 0;
		}
	}
	public boolean Compute_for_Grid_board(board_type grid,char symbol,Rules rules,Blocked_state blo)
	{	
		int curr_dimension = 3;
		char[][] last_dimension_status = grid.getBox();
		
		while(curr_dimension <= grid.getRow())
		{

			int temp_row = grid.getRow()/curr_dimension,temp_column=grid.getColumn()/curr_dimension;
			char[][] curr_Status = new char[temp_row][temp_column];
			char[] Symbols_used = grid.getSymbolsUsed();
			for(int i=0;i<temp_row;i++)
			{
				for(int j=0;j<temp_column;j++)
				{
					int count =rules.Consecutive_Cells_To_Win;
					int flag = 0;
					for(int sym_ind = 0;sym_ind<Symbols_used.length&& flag!=1;sym_ind++)
					{
						char Curr_symbol = Symbols_used[sym_ind];
						

						int lx = i*3 + 3;
						int ly = j*3 + 3;
						for(int ii=0;ii<3&& flag!=1;ii++)
						{
							for(int jj=0;jj<3 && flag!=1;jj++)
							{
								int iii = i*3 + ii;
								int jjj = j*3 + jj;
								if(checkDiagonalHorizontalVertical(1,1,1,grid,iii,jjj,Curr_symbol,rules,blo,last_dimension_status,1,lx,ly))
									flag=1;
							}
						}

						if(flag == 1)
						{
							curr_Status[i][j] = Curr_symbol;
							blo.block_one_grid(curr_dimension*i,curr_dimension*j);
						
							break;
						}
					}
					if(flag == 0){
						curr_Status[i][j] = ' ';
					}
					
				}
				
			}
			

			last_dimension_status = curr_Status;
			curr_dimension = curr_dimension * 3;
		}
		if(last_dimension_status[0][0] == ' ')
			return false;
		else
			return true;

	}
	public boolean checkDiagonalHorizontalVertical(int flagDiagonal,int flagHorizontal,int flagVertical,board_type hex,int x,int y,char symbol,Rules rules,Blocked_state blo,char[][] last_dimension_status,int flag,int lx,int ly)
	{
		for(int direction_x=-1;direction_x<=1;direction_x++)
		{
			for(int direction_y=-1;direction_y<=1;direction_y++)
			{
				
				if(direction_x == 0 && direction_y == 0)
					continue;
				if(flagDiagonal == 0 && direction_x!=0 && direction_y!=0)
					continue;
				if(flagVertical == 0 && direction_y==0)
					continue;
				if(flagHorizontal == 0 && direction_x==0)
					continue;
				boolean tmp_flag = true;
				int temp_count=0;
				for(int k=0;k<rules.Consecutive_Cells_To_Win && (y+direction_y*k >= 0 && (y+direction_y*k) < lx ) && (x+direction_x*k >= 0 && (x+direction_x*k) <ly );k++,temp_count++)
				{

					if(flag==0)
					{
						if(hex.getBoxChar(x+direction_x*k,y+direction_y*k) != symbol)
						{
							tmp_flag = false;
						}
					}
					else
					{
						if(last_dimension_status[x+direction_x*k][y+direction_y*k] != symbol)
						{
							tmp_flag = false;
						}
					}
					if(tmp_flag == false)
						break;

				}
				if(tmp_flag == true && temp_count == rules.Consecutive_Cells_To_Win){
					return true;
				}
			}	
		}
		return false;
	}

	public boolean Compute_for_Hex_board(board_type hex,char symbol,Rules rules,Blocked_state blo)
	{	
		for(int i=0;i<hex.getRow();i++)
		{
			for(int j=0;j<hex.getColumn();j++)
			{
				char[][] temp = new char[1][1];
				if(checkDiagonalHorizontalVertical(1,1,0,hex,i,j,symbol,rules,blo,temp,0,hex.getRow(),hex.getColumn()))
					return true;
			}
		}
		return false;
	}
	public boolean check_win(board_type board,char symbol,Rules rules,Blocked_state blo)
	{
		if(board instanceof Grid ){
			return Compute_for_Grid_board(board,symbol,rules,blo);
		}
		else
		{
			return Compute_for_Hex_board(board,symbol,rules,blo);
		}
	}
}


public class project{

	
	public final static  int MAX_SIZE = 20;
	public static HashMap<Pair<Integer,Integer>,Integer> hashMap;
	public static board_type optimal_move(board_type board,char x,Blocked_state blo)
	{
		int xx = new Random().nextInt(board.getRow());
		int yy = new Random().nextInt(board.getColumn());
		while( blo.status(xx,yy) == 1)
		{
			xx = new Random().nextInt(board.getRow());
			yy = new Random().nextInt(board.getColumn());
			
		}
		board.makeMove(xx,yy,x);
		blo.block_one_cell(xx,yy);
		return board;
	}
	public static void calculateLevel(int level,int x,int y){
		Pair<Integer,Integer> pair = new Pair<Integer,Integer>(x,y);
		Pair<Pair<Integer,Integer>,Integer> temp_pair = new Pair<Pair<Integer,Integer>,Integer>(pair,level);
		Queue< Pair<Pair<Integer,Integer>,Integer> > que = new LinkedList<>();
		que.add(temp_pair);
		while(que.size()!=0)
		{
			Pair<Integer,Integer> temp_x = que.peek().getKey();
			Integer temp_y = que.peek().getValue();
			que.remove();

			if(x<0 || y<0 || x>=MAX_SIZE || y>=MAX_SIZE || (hashMap.containsKey(temp_x))){
				continue;
			}
			hashMap.put(temp_x,temp_y);
			x = temp_x.getKey();
			y = temp_x.getValue();
			level = temp_y;
			if(x%2==0){
				pair = new Pair<Integer,Integer>(x-1,y-1);
				temp_pair = new Pair<Pair<Integer,Integer>,Integer>(pair,level+1);
				que.add(temp_pair);
				pair = new Pair<Integer,Integer>(x-1,y);
				temp_pair = new Pair<Pair<Integer,Integer>,Integer>(pair,level+1);
				que.add(temp_pair);
				pair = new Pair<Integer,Integer>(x,y-1);
				temp_pair = new Pair<Pair<Integer,Integer>,Integer>(pair,level+1);
				que.add(temp_pair);
				pair = new Pair<Integer,Integer>(x,y+1);
				temp_pair = new Pair<Pair<Integer,Integer>,Integer>(pair,level+1);
				que.add(temp_pair);
				pair = new Pair<Integer,Integer>(x+1,y-1);
				temp_pair = new Pair<Pair<Integer,Integer>,Integer>(pair,level+1);
				que.add(temp_pair);
				pair = new Pair<Integer,Integer>(x+1,y);
				temp_pair = new Pair<Pair<Integer,Integer>,Integer>(pair,level+1);
				que.add(temp_pair);
			}
			else
			{
				pair = new Pair<Integer,Integer>(x-1,y+1);
				temp_pair = new Pair<Pair<Integer,Integer>,Integer>(pair,level+1);
				que.add(temp_pair);
				pair = new Pair<Integer,Integer>(x-1,y);
				temp_pair = new Pair<Pair<Integer,Integer>,Integer>(pair,level+1);
				que.add(temp_pair);
				pair = new Pair<Integer,Integer>(x,y-1);
				temp_pair = new Pair<Pair<Integer,Integer>,Integer>(pair,level+1);
				que.add(temp_pair);
				pair = new Pair<Integer,Integer>(x,y+1);
				temp_pair = new Pair<Pair<Integer,Integer>,Integer>(pair,level+1);
				que.add(temp_pair);
				pair = new Pair<Integer,Integer>(x+1,y+1);
				temp_pair = new Pair<Pair<Integer,Integer>,Integer>(pair,level+1);
				que.add(temp_pair);
				pair = new Pair<Integer,Integer>(x+1,y);
				temp_pair = new Pair<Pair<Integer,Integer>,Integer>(pair,level+1);
				que.add(temp_pair);
			}
		}
	}
	public static void Start_Game(board_type board,Player[] players,Rules rules,Blocked_state blo){

    	Scanner scn = new Scanner(System.in); 
		 //Order of Players
        Deque<Player> deque = new ArrayDeque<Player>();
        for(int i=0;i<players.length;i++)
        {
        	deque.addFirst(players[i]);
        }
        State state = new State();
        while( state.finished == 0 )
       	{
       		System.out.println();
       		Player currPlayer = deque.peekFirst();
       		deque.removeFirst();
       		board.display();

       		System.out.println(currPlayer.getName()+ " " + currPlayer.getType());
       		//wanna confirm ??? 
       		char confirm = 'N';
       		
       		if(currPlayer.getType().equals("Machine"))
       		{
       			confirm = 'Y';
       			board = optimal_move(board,currPlayer.getSymbol(),blo);
       		}
       		
       		while(confirm != 'Y')
       		{
       			System.out.println("Type Row no. and Col no. Eg. (2 2)");

	       		int choice_row,choice_column;
	       		choice_row = scn.nextInt();
	       		choice_column = scn.nextInt();

	       		while(blo.status(choice_row-1,choice_column-1) == 1)
	       		{
	       			//Cant Move
	       			System.out.println("Can't Move");
		      		choice_row = scn.nextInt();
		       		choice_column = scn.nextInt();

	       		}

	       		board.makeMove(choice_row-1,choice_column-1,currPlayer.getSymbol());

	       		
	       		board.display();
	       		
	       		System.out.println("wanna Confirm: Press Y or N");
	       		confirm = scn.next().charAt(0);
	       		if(confirm == 'Y'){
	       			System.out.println("Changes Made");
	       			break;
	       		}
				board.makeMove(choice_row-1,choice_column-1,' ');
			}
			
			// move done 
			deque.addLast(currPlayer);
			state.check_Status(board,currPlayer,rules,blo);
       	}
       	board.display();
       	if(state.finished == 2)
       	{
       		System.out.println("Draw");
       	}
       	else
       	{
       		System.out.println("The Winner is " + deque.peekLast().getName() + " " + deque.peekLast().getType() + " " + deque.peekLast().getSymbol());
       	}

	}
    public static void main(String[] args){

    	Scanner scn = new Scanner(System.in); 
        System.out.println("Hello World, Lets Start the Game");
        // System.out.println();

        Rules rules = new Rules(3,1,1,1);
        int temp_x,temp_y;
        int no_of_players;
        // no of players
        System.out.println("How Many Players are there ?");
        System.out.println("Eg. 1");
        System.out.println("Eg. Suryansh Human X");
        no_of_players = 2;//scn.nextInt();

        Player[] players = new Player[no_of_players];
        char[] Symbols_selected = new char[no_of_players];
        // name + type + symbol
        for(int i=0;i<=no_of_players;i++)
        {
        	String temp_str;
        	temp_str = scn.nextLine();
        	if(i==0)
        		continue;
        	String[] arr = temp_str.split(" ",3);
        	
        	players[i-1] = new Player(arr[0],arr[1],arr[2].charAt(0));
        	Symbols_selected[i-1] = arr[2].charAt(0);

        }
        System.out.println("Do you want to play super tic tac toe? (eg. yes/no)");
        // System.out.println();
       	String superTicTacToe = scn.nextLine();
       	if(superTicTacToe.equals("yes"))
       	{
       		System.out.println("Which level SuperTicTacToe you want to play? (eg. 3)");
       		temp_x = scn.nextInt();
       		hashMap = new HashMap<>();
       		calculateLevel(1,10,10);
       		Hex hex = new Hex(MAX_SIZE,MAX_SIZE,Symbols_selected,temp_x);

			Blocked_state blo = new Blocked_state(hex,hex.getRow(),hex.getColumn(),temp_x);
       		Start_Game(hex,players,rules,blo);
       	}
        else
        {

	        System.out.println("Define Your Size of grid\nRows Column");
	        System.out.println("Eg. 3 3");
	        temp_x = scn.nextInt();
	        temp_y = scn.nextInt();
	        Grid grid = new Grid(temp_x,temp_y,Symbols_selected);
	        Blocked_state blo = new Blocked_state(grid,grid.getRow(),grid.getColumn(),temp_x);
	        Start_Game(grid,players,rules,blo);
	    }	   
    }
}




