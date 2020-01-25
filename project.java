
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
        for(int i=1;i<no_of_players;i++)
        {
        	deque.addFirst(players[i]);
        }
    }
}




