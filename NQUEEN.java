/********************************
 *@author Utkarsh Kumar
 *ID: 2017B2A71008P
 *******************************/
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class NQUEEN {
	public static void main(String[] args) throws IOException {
		Scanner input = new Scanner(System.in);
		System.out.print("provide the size(N):> ");
		int N = input.nextInt();
		//System.out.println();
		System.out.print("Choice\n1. Get All Possible Solution\n2. Get only one solution\nYour Choice: ");
		int choice = input.nextInt();
		boolean comp = false;
		input.close();
		if(choice == 2)
		{
			Solve tmt = new Solve(N, comp, choice);
			tmt.solveNQueens();
		}

		else {
			if(N > 7)
			{
				System.out.println("Large N. Only row no. at which queen is placed in each column will be printed in File.");
				comp = true;
					
			}
			FileWriter out = new FileWriter("NQoutput.txt");
			System.out.println("Computing.... Please wait");
			Solve tmt = new Solve(N, comp, out);
			System.out.println("\nOutput printed in NQoutput.txt\n");
			tmt.solveNQueens();
			out.close();
		}
		
	}
}
class Solve {
    private final int N;
    private int[] QPos;
    int solCount;
    int nodesTrav;
    long totalTime;
    boolean printSol;
    private int[][] ThreatArray;
    FileWriter out;
    boolean choice;

    Solve(int N, boolean compressedSol, FileWriter out) {
        this.N = N;
        QPos = new int[N];
        ThreatArray = new int[N][N];
        printSol = compressedSol;
        this.out = out;
        choice = false;

    }
    Solve(int N, boolean compressedSol, int choice) {
        this.N = N;
        QPos = new int[N];
        ThreatArray = new int[N][N];
        printSol = compressedSol;
        //this.out = out;
        this.choice = true;
    }
    public void solveNQueens() throws IOException {
        long startTime = System.currentTimeMillis();
        backtrack(0);
        totalTime = System.currentTimeMillis() - startTime;
        if(solCount == 0)
        {
        	System.out.println("No possible solution found");
        }
        if(choice && solCount != 0)
        {
        	for(int i = 0; i< N; i++)
			{
				for(int j = 0; j< N; j++)
				{
					if(QPos[j] == i)
        				System.out.print("1 ");
        			else
        				System.out.print("0 ");
				}
				System.out.println();
			}
        	System.out.println("Performance stats:\n" + "Total Time taken: "+totalTime + " ms"+"\nExpanded nodes : "+ nodesTrav);
        	
        	return;
        }
       
        System.out.println("Performance stats:\n" + "Total Solutions found: "+  solCount + 
        		"\nTotal Time taken: "+totalTime + " ms"+"\nExpanded nodes : "+ nodesTrav);
       return;
    }
    private boolean ForwardCheck(int i, int columnNumber){
    	boolean possible = false;
    	if (ThreatArray[i][columnNumber] == 0) 
        {    
        	markPlace(i, columnNumber, 1);
        	possible = isSolutionPossible(columnNumber);
        	if(!possible)
        		markPlace(i, columnNumber, -1);
        }
    	return possible;
    }
    
	private boolean backtrack(int columnNumber) throws IOException{
		if (columnNumber == N) {
            solCount++;
            if(!choice)printSolutions();
            return true;
        }
		for (int i = 0; i < N; i++) {
            if (ForwardCheck(i, columnNumber)) 
            {   nodesTrav++;
            	QPos[columnNumber] = i;
            	boolean done = backtrack(columnNumber + 1);
            	if(done && choice)
            	{
            		return true;
            	}
            	markPlace(i, columnNumber, -1);
            	
            }
        }
        return false;
	 }

    private void markPlace(int R, int C, int validity) {	
        for (int j= 1; j< N-C; j++) {	
            ThreatArray[R][C+j] += validity;
            if (R+j < N)
                ThreatArray[R+j][C+j] += validity;
            
            if (R-j >= 0)
                ThreatArray[R-j][C+j] += validity;
        }
    }
    
    private boolean isSolutionPossible(int C) {	
        //lookahead in each column
    	for (int i = C+1; i < N; i++) {
            boolean possible = false;
            
            for (int row= 0; row < N && !possible; row++) {
                if (ThreatArray[row][i] == 0)
                    possible = true;
            }
            if (!possible) return false;
        }
        return true;
    }
    
	private void printSolutions() throws IOException {
    	if(printSol){
        	for(int i : QPos)
        	{
        		out.write((i+1)+" ");
        	}
        	out.write("\n\n");
        }
        else{
        	for(int i = 0; i< N; i++){
        		for(int j = 0; j< N; j++){
        			if(QPos[j] == i)
        				out.write("1 ");
        			else
        				out.write("0 ");
        		}
        		out.write("\n");
        	}
        	out.write("\n");
        }
    }
}
