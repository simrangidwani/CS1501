//Simran Gidwani
import java.util.*;
import java.io.*;


public class Crossword {
    
    public static DictInterface dictionary;
    public static int boardSize;
    public static char [][] board;
    public static int numSol;
    public static boolean avail = false;
    public static char [] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    public static StringBuilder [] colStr;
    public static StringBuilder [] rowStr;

    
    public static void main(String[] args) throws IOException {   
        Scanner inputFile = new Scanner(new FileInputStream("dict8.txt")); 
    
	try
	{
		if(args[1].equals("dlb"))
		{
                    dictionary = new DLB();
		}
		else   
                    dictionary = new MyDictionary();
	}
        
        catch(IndexOutOfBoundsException e)
	{
            System.out.println("Invalid dictInterface arguement");
            System.exit(0);
        }

        File file = new File(args[0]);
	Scanner fileScan = new Scanner(file);
	boardSize = fileScan.nextInt();
        
        colStr = new StringBuilder [boardSize];
        rowStr = new StringBuilder [boardSize];
        for (int i = 0; i < boardSize; i++)
        {
            rowStr[i]= new StringBuilder("");
            colStr[i] = new StringBuilder("");
        }
        board = new char[boardSize][boardSize];

        //reads in from file and creates gameboard
        for (int row = 0; row < boardSize; row++)
            {
                String ch = fileScan.next();
                for (int col = 0; col < boardSize; col++)
                {
                    board [row][col]= ch.charAt(col); 
                    
                }
            }
	String word;
        //store everything in the inputfile to the DictInterface object D
        while (inputFile.hasNext())
        {
            word = inputFile.nextLine();
            dictionary.add(word);
        }   
        
        if (args[1].equals("dlb"))
        {
            recurseCross(0);
            System.out.println("Number solutions: " + numSol);
        }
        else
        {
            recurseMYDICT(0);
            for (int i = 0; i < boardSize; i++)
            {
                System.out.println(rowStr[i]);
            }
            

        }
      
    }
        
        //this method checks whether or not the letters at the given 
        //row and column are a valid prefix/word
        public static boolean WorP(int row, int col)
	{
		int colVal = dictionary.searchPrefix(colStr[col]);
		int rowVal = dictionary.searchPrefix(rowStr[row]);             
		if ( row<boardSize-1 && col<boardSize-1 && (colVal == 1 || colVal == 3) && (rowVal == 1 || rowVal ==3) )
			return true;
		if ( col == boardSize-1 && row<boardSize-1 && (colVal == 1 || colVal ==3) && (rowVal == 2 || rowVal == 3) )
			return true;
		if ( row == boardSize-1 && col<boardSize-1 && (colVal == 2 || colVal == 3) && (rowVal == 1 || rowVal == 3) )
			return true;
		if ( row == boardSize-1 && col == boardSize-1 && (colVal == 2 || colVal == 3) && (rowVal == 2 || rowVal == 3) )
			return true;
		else
			return false;
	}
        
        //recursive method for returning multiple solutions.
        //iterates through the array of the alphabet
	public static void recurseCross(int pos)
        {
            int r = pos/boardSize;
            int c = pos%boardSize;
            
            //if a solution is found 
            if (pos == (boardSize*boardSize) || (colStr[boardSize-1].length()==boardSize && rowStr[boardSize-1].length()==boardSize))
                    return;          
            
            else
            {
                //checks whether the spot is a + or not, if so continue through
                if (isAvailable(r, c))
                {                   		
                    for (int letVal = 0; letVal < alphabet.length; letVal++)
                    {
			r = pos/boardSize;
			c = pos%boardSize;
			colStr[c].append(alphabet[letVal]);                        
			rowStr[r].append(alphabet[letVal]);
                        
                        //if it's valid
			if (WorP(r, c))
			{                       
                            recurseCross(pos + 1);            
                            //allows for the multiple solutions to be found
                            if ((colStr[boardSize-1].length()==boardSize && rowStr[boardSize-1].length()==boardSize))
                            {
                                
                                //commented out below is the print statement that prints out all the legal solutions
				//for (int i=0; i< boardSize; i++)                             
                                    //System.out.println(rowStr[i]); 
                                
                                numSol++;
                                //prints out the first solution found before the rest of the solutions are found
            			if (numSol == 1 && (rowStr[boardSize-1].length() == boardSize && colStr[boardSize-1].length()==boardSize))
            			{
                			System.out.println("First Solution is");
                			for (int i = 0; i< boardSize; i++)
                			{
                    				System.out.println(rowStr[i]);
                
                			}
	    
            			}
                                //prints out every 10,000th solution
                                else if (numSol % 10000 == 0)
                                {
                                    System.out.println("\n" + "Solution Number: " + numSol);    
                                    for (int i =0; i < boardSize; i++)
                                    {
                                        System.out.println(rowStr[i]);
                                    }
                                }
                            }
                            colStr[c].deleteCharAt(r);
                            rowStr[r].deleteCharAt(c);
                            
                        }
                        //otherwise delete the character and go to the next letter
                        else
                        {
                            colStr[c].deleteCharAt(r);
                            rowStr[r].deleteCharAt(c);
                        }
                    }
                }       
                //if the spot is not available, add the letter and go to next position
                else
                {
                        colStr[c].append(board[r][c]);
                        rowStr[r].append(board[r][c]);   
                        if (WorP(r, c))
                        {
                            recurseCross(pos+1);
                            
                            //if ((colStr[boardSize-1].length()==boardSize && rowStr[boardSize-1].length()==boardSize))
				//return;
                            colStr[c].deleteCharAt(r);
                            rowStr[r].deleteCharAt(c);
                        }
                        else
                        {                                        
                            colStr[c].deleteCharAt(r);
                            rowStr[r].deleteCharAt(c);                                                                    
                        }                   
                }
                
            }
        }
        
        //recursive method that only prints out one solution
        public static void recurseMYDICT(int pos)
        {
            int r = pos/boardSize;
            int c = pos%boardSize;
            
            if (pos == (boardSize*boardSize) || (colStr[boardSize-1].length()==boardSize && rowStr[boardSize-1].length()==boardSize))
			return; 
            else
            {
                //checks whether the spot is a + or not, if so continue through
                if (isAvailable(r, c))
                {                   		
                    for (int letVal = 0; letVal < alphabet.length; letVal++)
                    {
			r = pos/boardSize;
			c = pos%boardSize;
			colStr[c].append(alphabet[letVal]);                        
			rowStr[r].append(alphabet[letVal]);
                        //if it's valid
			if (WorP(r, c))
			{                       
                            recurseMYDICT(pos + 1);            
                            if ((colStr[boardSize-1].length()==boardSize && rowStr[boardSize-1].length()==boardSize))
                                return;
                            colStr[c].deleteCharAt(r);
                            rowStr[r].deleteCharAt(c);
                            
                            
                        }
                        //otherwise delete the character and go to the next letter
                        else
                        {
                            colStr[c].deleteCharAt(r);
                            rowStr[r].deleteCharAt(c);
                        }
                    }
                }       
                //if the spot is not available, add the letter and go to next position
                else
                {
                        colStr[c].append(board[r][c]);
                        rowStr[r].append(board[r][c]);   
                        if (WorP(r, c))
                        {
                            recurseMYDICT(pos+1);
                            if ((colStr[boardSize-1].length()==boardSize && rowStr[boardSize-1].length()==boardSize))
				return;
                            colStr[c].deleteCharAt(r);
                            rowStr[r].deleteCharAt(c);
                        }
                        else
                        {                                        
                            colStr[c].deleteCharAt(r);
                            rowStr[r].deleteCharAt(c);                                                                    
                        }                   
                }
                
            }
        }
        
        //method that checks availability for the spot on the board
        public static boolean isAvailable(int r, int c)
        {    
            if (board[r][c] == '+')
            {
                avail = true;             
            }
            else
            {
                avail = false;                
            }      
            return avail;
        }     
        
     
        
}

