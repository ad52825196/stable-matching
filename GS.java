import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Stable matching problem: Given n men and n women, and their preferences, find
 * a stable matching if one exists.
 * 
 * This class computes the Gale-Shapley matching.
 * 
 * @author Zhen Chen
 *
 */

public class GS {
	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static final int MAX_NUMBER_OF_PAIRS = 2000;
	// number of men and women
	private static int n;
	private static int[][] manPreference = new int[MAX_NUMBER_OF_PAIRS + 1][MAX_NUMBER_OF_PAIRS + 1];
	private static int[][] womanPreference = new int[MAX_NUMBER_OF_PAIRS + 1][MAX_NUMBER_OF_PAIRS + 1];
	private static int[][] womanPreferenceInverse = new int[MAX_NUMBER_OF_PAIRS + 1][MAX_NUMBER_OF_PAIRS + 1];
	private static int[] wife = new int[MAX_NUMBER_OF_PAIRS + 1];
	private static int[] husband = new int[MAX_NUMBER_OF_PAIRS + 1];
	private static int[] count = new int[MAX_NUMBER_OF_PAIRS + 1];
	
	private static void init() {
		for (int i = 1; i <= n; i++) {
			wife[i] = 0;
			husband[i] = 0;
			count[i] = 0;
		}
	}
	
	private static void prep() {
		for (int i = 1; i <= n; i++) {
			inverse(womanPreference[i], womanPreferenceInverse[i]);
		}
	}

	private static void inverse(int[] preference, int[] inverse) {
		for (int i = 1; i <= n; i++) {
			inverse[preference[i]] = i;
		}
	}

	private static void getPreference(int[][] preference) throws IOException {
		String line;
		String[] list;
		for (int i = 1; i <= n; i++) {
			line = reader.readLine();
			list = line.split("\\s");
			int j = 0;
			for (String s : list) {
				preference[i][++j] = Integer.parseInt(s.substring(1));
			}
		}
	}
	
	private static void GaleShapley() {
		// initialize a queue of free men
		Queue<Integer> queue = new LinkedList<Integer>();
		for (int i=1; i<=n; i++) {
			queue.add(i);
		}
		
		while (!queue.isEmpty()) {
			int man = queue.poll();
			count[man]++;
			if (count[man] > n) {
				continue;
			}
			int woman = manPreference[man][count[man]];
			if (husband[woman] == 0) {
				engage(man, woman);
			} else if (womanPreferenceInverse[woman][man] < womanPreferenceInverse[woman][husband[woman]]) {
				queue.add(husband[woman]);
				breakUp(husband[woman], woman);
				engage(man, woman);
			} else {
				queue.add(man);
			}
		}
	}
	
	private static void engage(int man, int woman) {
		wife[man] = woman;
		husband[woman] = man;
	}
	
	private static void breakUp(int man, int woman) {
		wife[man] = 0;
		husband[woman] = 0;
	}
	
	private static void output() {
		StringBuilder sb = new StringBuilder();
		for (int i=1; i<=n; i++) {
			sb.append('W');
			sb.append(wife[i]);
			sb.append(' ');
		}
		System.out.println(sb.deleteCharAt(sb.length() - 1));
	}

	public static void main(String[] args) {
		try {
			int numberOfTestCases = Integer.parseInt(reader.readLine());
			for (int i = 0; i < numberOfTestCases; i++) {
				n = Integer.parseInt(reader.readLine());
				init();
				getPreference(manPreference);
				getPreference(womanPreference);
				prep();
				GaleShapley();
				output();
			}
		} catch (Exception e) {
			System.exit(0);
		}
	}

}
