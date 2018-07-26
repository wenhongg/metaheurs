/**
 * Experimenting with genetic algorithms.
 */
package ga;

import java.util.Random;

/**
 * Game designed to produce a random number to be guessed by a player. 
 * The length of the number can be decided; provided as an argument to the constructor.
 * @author WEN HONG
 */
public class NumberGame {
	Random random;
	final char[] arr;
	String answer;
	int capacity;
	
	/**
	 * A random number is produced.
	 * @param capac Length of the number to be produced.
	 */
	public NumberGame(int capac) {
		capacity = capac;
		random = new Random();
		arr = new char[capac];
		answer = "";
		for(int i=0; i<capac; i+=1) {
			arr[i] = Integer.toString(random.nextInt(10)).charAt(0);
			answer += arr[i];
		}
		
		System.out.println("Number is " + answer);
	}
	
	/**
	 * Check the score / fitness of the number 
	 * @param test the number to be tested (in string form)
	 * @return the score of the number tested
	 */
	public int check(String test) {
		int cx = 0;
		char[] check = test.toCharArray();
		for(int i=0;i<capacity;i+=1) {
			if(check[i] == arr[i]) {
				cx+=1;
			}
		}
		return cx;
	}
	

}
