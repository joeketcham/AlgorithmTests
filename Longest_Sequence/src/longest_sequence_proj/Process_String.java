package longest_sequence_proj;

import java.util.StringTokenizer;
import java.util.ArrayList;

/**
 * Class for processing strings of integers separated by commas. Takes in a 
 * string and returns the longest increasing sequence of numbers from that
 * string
 * 
 * @author Joseph Ketcham
 * @author Michael Eby
 * @author Nironjali Karunadasa
 * @version 1.0
 *
 */

public class Process_String {

	/**
	 * Returns the longest increasing sequence of numbers 
	 * 
	 * @param inString	Long unsorted string of integers separated by commas
	 * 
	 * @return String containing longest increasing sequence of numbers 
	 */
	public static String execute(String inString) {
		//String inString = "11,17,5,8,6,4,7,12";
		if (inString.indexOf(",") == -1 && inString.indexOf(" ") > -1) { return "Possibly missing commas between numbers, file contains: " + inString; }
		
		inString = inString.replaceAll(" ", "");
		
		String arr[];
		StringTokenizer st = new StringTokenizer(inString, ",");
		arr = new String[st.countTokens()];
		if (st.countTokens() <= 0) { return "";}		
		if (st.countTokens() <= 1) { return st.nextToken();}
		
		int ii = 0;
	        while(st.hasMoreTokens()){
	        	arr[ii] = st.nextToken();
	            ii++;
	        }
		
		
		int[] S = new int[arr.length ];
		
		for (int i = 0; i < arr.length; i++){
			S[i] = Integer.parseInt(arr[i]);
		}
		
		int[] L = new int[S.length];		
		String[] P = new String[S.length];
		L[0] = 1;		
		int maxLength = S.length;
		
		for(int i = 1; i < maxLength; i++){
			for (int j = 0; j < i; j++){
				
				if (S[j] < S[i]  ){
					
					if (P[i] != null && L[j] > L [ Integer.parseInt(P[i])]){
								P[i] = Integer.toString(j);
								L[i] = L[j] + 1;
					}
					else if (P[i] == null){
						P[i] = Integer.toString(j);
						L[i] = L[j] + 1;
					}					
				}
				if (L[i] == 0){					
					L[i] = 1;
					}
			}
		}
		int largestSeq = 0;
		int startingPosition = 0;
		int nextPosition = 0;
		for(int i = 0; i < S.length; i++){
			if (L[i] > largestSeq){				
				largestSeq = L[i];	
				startingPosition = i;
			}			
		}
		
		//iterate across and print sequence
		String SequenceOutput = "";
		SequenceOutput = Integer.toString(S[startingPosition]);
		if (P[startingPosition] != null) {
				nextPosition = Integer.parseInt(P[startingPosition]);
		}
		
		
		Boolean flag = true;
		if (P[nextPosition] == null) { 
			flag = false; 
			}
		else {
		SequenceOutput =  Integer.toString(S[nextPosition]) + ", " + SequenceOutput;				
		}
		while (flag == true) {			
			nextPosition = Integer.parseInt(P[nextPosition]);
			SequenceOutput =  Integer.toString(S[nextPosition]) + ", " + SequenceOutput;			
			if (P[nextPosition] == null) { 
				flag = false; 
			}
			
		}
		return SequenceOutput;
	}
	/**
	 * Static main method not used in this application 
	 * 
	 * @param args
	 *  
	 */
	public static void main(String[] args) {
		//String results = execute("11, 17,5,8,6,4,7,12,");
		String results = execute("1.1,1.2,1.3");
		System.out.println(results);
	}
}
