
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


    }
}




