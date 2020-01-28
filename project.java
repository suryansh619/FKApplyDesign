
package learnJava;

import java.util.*;

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


class Grid {
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
		Box[x][y] = symbol;
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

	public void setBoxChar(int i,int j,char x)
	{
		this.Box[i][j] = x;
	}
}
class Blocked_state{
	int[][] block_cells;
	Blocked_state(int row,int col){
		block_cells = new int[row][col];
		for(int i=0;i<row;i++)
			for(int j=0;j<col;j++)
				block_cells[i][j] = 0;
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
	boolean check_win(Grid grid,char symbol,Rules rules,Blocked_state blo);
}


class State implements checkingMethod{
	static int finished;
	//0 is for undecided
	// 1 is for Win
	// 2 is for draw
	{
		finished=0;
	}

	public void check_Status(Grid grid,Player player,Rules rules,Blocked_state blo)
	{
		char temp_symbol_of_current_player = player.getSymbol();
		if(check_win(grid,temp_symbol_of_current_player,rules,blo))
		{
			this.finished = 1;
		}
		else
		{
			int flag = 0;
			for(int i=0;i<grid.Rows;i++)
				for(int j=0;j<grid.Column;j++)
					if(blo.status(i,j) == 0)
						flag = 1;

			// flag is 0 means there is no left spaces
			if(flag == 0)
				this.finished = 2;
			else
				this.finished = 0;
		}
	}
	public boolean check_win(Grid grid,char symbol,Rules rules,Blocked_state blo)
	{


		int curr_dimension = 3;
		char[][] last_dimension_status = grid.Box;
		
		while(curr_dimension <= grid.getRow())
		{
			int temp_row = grid.getRow()/curr_dimension,temp_column=grid.getColumn()/curr_dimension;
			char[][] curr_Status = new char[temp_row][temp_column];
			for(int i=0;i<temp_row;i++)
			{
				for(int j=0;j<temp_column;j++)
				{
					int count =rules.Consecutive_Cells_To_Win;
					int flag = 0;
					for(int sym_ind = 0;sym_ind<grid.Symbols_used.length&& flag!=1;sym_ind++)
					{
						char Curr_symbol = grid.Symbols_used[sym_ind];
						

						int lx = i*3 + 3;
						int ly = j*3 + 3;
						for(int ii=0;ii<3&& flag!=1;ii++)
						{
							for(int jj=0;jj<3 && flag!=1;jj++)
							{
								int iii = i*3 + ii;
								int jjj = j*3 + jj;
								for(int direction_x=-1;direction_x<=1&& flag!=1;direction_x++)
								{
									for(int direction_y=-1;direction_y<=1&& flag!=1;direction_y++)
									{
										
										if(direction_x == 0 && direction_y == 0)
											continue;

										boolean tmp_flag = true;
										int temp_count=0;
										for(int k=0;k<count && (jjj+direction_y*k >= 0 && (jjj+direction_y*k) < lx ) && (iii+direction_x*k >= 0 && (iii+direction_x*k) <ly );k++,temp_count++)
										{

											if(last_dimension_status[iii+direction_x*k][jjj+direction_y*k] != Curr_symbol)
											{
												tmp_flag = false;
											}
											if(tmp_flag == false)
												break;
										}
										if(tmp_flag == true && temp_count == count){
											flag=1;
											break;
										}
									}	
								}
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
}


class project{

	public static Grid optimal_move(Grid temp,char x,Blocked_state blo)
	{
		int xx = new Random().nextInt(temp.getRow());
		int yy = new Random().nextInt(temp.getColumn());
		while( blo.status(xx,yy) == 1)
		{
			xx = new Random().nextInt(temp.getRow());
			yy = new Random().nextInt(temp.getColumn());
			
		}
		temp.setBoxChar(xx,yy,x);
		blo.block_one_cell(xx,yy);
		return temp;
	}
    public static void main(String[] args){

    	Scanner scn = new Scanner(System.in);
        System.out.println("Hello World, Lets Start the Game");
        System.out.println();

        // print rules.....
        System.out.println("Define Your Rules\n Count Of continous squares to win  +  Row win (1 or 0)  + col win (1 or 0)  + diagonal win(1 or 0)");
        System.out.println("Eg. 3 1 1 1");
        int temp_count,temp_x,temp_y,temp_z;
        temp_count = scn.nextInt();
        temp_x = scn.nextInt();
        temp_y = scn.nextInt();
        temp_z = scn.nextInt();
        
        Rules rules = new Rules(temp_count,temp_x,temp_y,temp_z);

        System.out.println("Define Your Size of grid\nRows Column");
        System.out.println("Eg. 3 3");
        temp_x = scn.nextInt();
        temp_y = scn.nextInt();
        
        Blocked_state blo = new Blocked_state(temp_x,temp_y);


        int no_of_players;
        // no of players
        System.out.println("How Many Players are there ?");
        System.out.println("Eg. 1");
        System.out.println("Eg. Suryansh Human X");
        no_of_players = scn.nextInt();

        Player[] players = new Player[no_of_players];
        char[] Symbols_selected = new char[no_of_players];
        //name + type + symbol
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
        
        Grid grid = new Grid(temp_x,temp_y,Symbols_selected);
		        

        //Order of Players
        Deque<Player> deque = new ArrayDeque<Player>();
        for(int i=0;i<no_of_players;i++)
        {
        	deque.addFirst(players[i]);
        }
        State state = new State();
       	System.out.println(deque.size());
        while( state.finished == 0 )
       	{
       		System.out.println();
       		Player currPlayer = deque.peekFirst();
       		deque.removeFirst();
       		grid.display();

       		System.out.println(currPlayer.getName()+ " " + currPlayer.getType());
       		//wanna confirm ??? 
       		char confirm = 'N';
       		
       		if(currPlayer.getType().equals("Machine"))
       		{
       			confirm = 'Y';
       			grid = optimal_move(grid,currPlayer.getSymbol(),blo);
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

	       		grid.makeMove(choice_row-1,choice_column-1,currPlayer.getSymbol());

	       		
	       		grid.display();
	       		
	       		System.out.println("wanna Confirm: Press Y or N");
	       		confirm = scn.next().charAt(0);
	       		if(confirm == 'Y'){
	       			System.out.println("Changes Made");
	       			break;
	       		}
				grid.makeMove(choice_row-1,choice_column-1,' ');
			}
			
			// move done 
			deque.addLast(currPlayer);
			state.check_Status(grid,currPlayer,rules,blo);
       	}
       	if(state.finished == 2)
       	{
       		System.out.println("Draw");
       	}
       	else
       	{
       		System.out.println("The Winner is " + deque.peekLast().getName() + " " + deque.peekLast().getType() + " " + deque.peekLast().getSymbol());
       	}
    }
}




