/**
 * Experimenting with genetic algorithms.
 */
package ga;

import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.Random;

/**
 * Playr follows the Genetic Algorithm.
 * Mutation of a single slot has been implemented and is crucial to prevent early convergence.
 * 
 * Possibly can improve by: preventing repeats,customizing initial spread and modify mutation method
 * 
 * GA is some kind of a bashing method which is rather limited.
 * 
 * Playr class holds the main method; game is created and player is created to play on game.
 * @author WEN HONG
 */
public class Playr {
	PriorityQueue<Guess> pq;
	NumberGame game;
	Random random;
	int guesscount, capacity;
	
	
	class Guess implements Comparable<Guess> {
		String test;
		int score;
		NumberGame game;
		Guess(String test1, NumberGame gamee) {
			game = gamee;
			test = test1;
			score = game.check(test);
		}
		
		public int compareTo(Guess lol) {
			return -score + lol.score;
		}
		
	}
	
	
	
	public Playr(NumberGame gamee) {
		guesscount = 0;
		pq = new PriorityQueue<Guess>();
		game = gamee;
		random = new Random();
		capacity = game.capacity;
		
		//Randomly produce 10 numbers to test
		for(int i=0; i<30; i+=1) {
			String guess = "";
			for(int j=0;j<capacity;j+=1) {
				guess += Integer.toString(random.nextInt(10));
			}
			Guess x = new Guess(guess,game);
			pq.add(x);
			//System.out.println(guess + "-" + x.score);
		}
		guesscount += 1;
		
		System.out.println("Generation " + guesscount + " best fitness: " + pq.peek().score + " by sequence " + pq.peek().test);
		
		while(pq.peek().score!=capacity) {
			mutation();
			crossover();
		}
	}
	
	
	void crossover() {
		PriorityQueue<Guess> neew = new PriorityQueue<Guess>();
		
		String one = pq.peek().test;
		neew.add(pq.poll());
		boolean repeat = true;
		String two = "";
		while(repeat) {
			two = pq.peek().test;
			if(one.equals(two)) {
				pq.poll();
				//System.out.println("Clearing repeat");
			} else {
				repeat = false;
			}
		}
		neew.add(pq.poll());
		
		String nextone = "";
		String nexttwo = "";
		int cleave = random.nextInt(4);
		
		for(int i=0; i<=cleave; i+=1) {
			nextone += Character.toString(one.charAt(i));
			nexttwo += Character.toString(two.charAt(i));
		}
		for(int i=cleave+1; i<capacity; i+=1) {
			nextone += Character.toString(two.charAt(i));
			nexttwo += Character.toString(one.charAt(i));
		}
		
		Guess first = new Guess(nextone, game);
		Guess second = new Guess(nexttwo, game);
		guesscount += 1;
		int diff = first.score - second.score;
		
		// Adds first if the score is same.
		if(diff>=0) {
			neew.add(first);
		} else if(diff<0){
			neew.add(second);
		} 
		
		//At present, keeping only 10
		for(int i=0; i<7; i+=1) {
			if(pq.size()>0) {
				neew.add(pq.poll());
			} else {
				break;
			}
		}
		pq = neew;
		System.out.println("Generation " + guesscount + " best fitness: " + pq.peek().score + " by sequence " + pq.peek().test);
		/*
		for(Guess x : pq) {
			System.out.println(x.test + "-" + x.score);
		}
		*/
		
		
	}
	
	/**
	 * Mutation is necessary to break out of minimas. low probability of successful mutation however likely results in many generations.
	 */
	void mutation() {
		String x = pq.peek().test;
		String[] arr = x.split("");
		arr[random.nextInt(capacity)] = Integer.toString(random.nextInt(10));
		
		String y="";
		for(String chr: arr) {
			y += chr;
		}
		pq.add(new Guess(y,game));
	}
	
	
	public static void main(String[] args) {
		NumberGame game = new NumberGame(100);
		new Playr(game);
	}
}
