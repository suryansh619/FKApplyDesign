
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
	int Rows, Column;
	Grid(int rows, int column)
	{
		this.Rows = rows;
		this.Column = column;
		Box = new char [rows][column];
		for(int i=0;i<rows;i++)
			for(int j=0;j<column;j++)
				Box[i][j] = ' ';
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
				System.out.println("------");
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
	boolean check_win(Grid grid,char symbol,Rules rules);
}


class State implements checkingMethod{
	static int finished;
	//0 is for undecided
	// 1 is for Win
	// 2 is for draw
	{
		finished=0;
	}

	public void check_Status(Grid grid,Player player,Rules rules)
	{
		char temp_symbol_of_current_player = player.getSymbol();
		if(check_win(grid,temp_symbol_of_current_player,rules))
		{
			this.finished = 1;
		}
		else
		{
			int flag = 0;
			for(int i=0;i<grid.Rows;i++)
				for(int j=0;j<grid.Column;j++)
					if(grid.Box[i][j] == ' ')
						flag = 1;

			// flag is 0 means there is no left spaces
			if(flag == 0)
				this.finished = 2;
			else
				this.finished = 0;
		}
	}
	public boolean check_win(Grid grid,char symbol,Rules rules)
	{
		int count =rules.Consecutive_Cells_To_Win;
		int flag = 0;
		// System.out.println("working\n");
		for(int i=0;i<grid.Rows;i++)
		{
			for(int j=0;j<grid.Column;j++)
			{
				for(int direction_x=-1;direction_x<=1;direction_x++)
				{
					for(int direction_y=-1;direction_y<=1;direction_y++)
					{
						if(direction_x == 0 && direction_y == 0)
							continue;
						// System.out.println("working\n");
						boolean tmp_flag = true;
						int temp_count=0;
						for(int k=0;k<count && (j+direction_y*k >= 0 && j+direction_y*k <grid.Column ) && (i+direction_x*k >= 0 && i+direction_x*k <grid.Rows );k++,temp_count++)
						{

							if(grid.Box[i+direction_x*k][j+direction_y*k] != symbol)
							{
								tmp_flag = false;
							}
							if(tmp_flag == false)
								break;
						}
						if(tmp_flag == true && temp_count == count){
							flag=1;
							return tmp_flag;	
						}
					}	
				}
			}
		}
		return false;
	}
}


class project{

    public static void main(String[] args){

    	Scanner scn = new Scanner(System.in);
        System.out.println("Hello World, Lets Start the Game");


        // print rules.....
        System.out.println("Define Your Rules\n Count Of continous squares to win + Row win (1 or 0) + col win (1 or 0) + diagonal win(1 or 0)");
        int temp_count,temp_x,temp_y,temp_z;
        temp_count = scn.nextInt();
        temp_x = scn.nextInt();
        temp_y = scn.nextInt();
        temp_z = scn.nextInt();
        
        Rules rules = new Rules(temp_count,temp_x,temp_y,temp_z);

        System.out.println("Define Your Size of grid\nRows Column");
        temp_x = scn.nextInt();
        temp_y = scn.nextInt();
        
        Grid grid = new Grid(temp_x,temp_y);


        int no_of_players;
        // no of players
        System.out.println("How Many Players are there ?");
        no_of_players = scn.nextInt();

        Player[] players = new Player[no_of_players];

        //name + type + symbol
        for(int i=0;i<=no_of_players;i++)
        {
        	String temp_str;
        	temp_str = scn.nextLine();
        	if(i==0)
        		continue;
        	String[] arr = temp_str.split(" ",3);
        	
        	players[i-1] = new Player(arr[0],arr[1],arr[2].charAt(0));

        }
        
        

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

       		System.out.println(currPlayer.getName() + currPlayer.getType());
       		//wanna confirm ??? 
       		char confirm = 'N';
       		
       		if(currPlayer.getType().equals("Machine"))
       		{
       			confirm = 'Y';
       			// grid = optimal_move(grid,currPlayer.getSymbol());
       		}
       		
       		while(confirm != 'Y')
       		{
       			System.out.println("Type Row no. and Col no.");
	       		int choice_row,choice_column;
	       		choice_row = scn.nextInt();
	       		choice_column = scn.nextInt();

	       		while(grid.isAvailable(choice_row-1,choice_column-1) == false)
	       		{
	       			//Cant Move
	       			System.out.println("Can't Move");
		      		choice_row = scn.nextInt();
		       		choice_column = scn.nextInt();

	       		}

	       		grid.makeMove(choice_row-1,choice_column-1,currPlayer.getSymbol());

	       		//
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
			state.check_Status(grid,currPlayer,rules);
       	}
       	if(state.finished == 2)
       	{
       		System.out.println("Draw");
       	}
       	else
       	{
       		System.out.println("The Winner is " + deque.peekLast().getName() + " " + deque.peekLast().getType());
       	}
    }
}




