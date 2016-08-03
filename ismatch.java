import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * Stable matching problem: Given n men and n women, and their preferences, find
 * a stable matching if one exists.
 * 
 * This class tests whether the proposed matching is stable or not.
 * 
 * @author Zhen Chen
 *
 */

public class ismatch {
	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static final int MAX_NUMBER_OF_PAIRS = 2000;
	// number of men and women
	private static int n;
	private static int[][] manPreference = new int[MAX_NUMBER_OF_PAIRS + 1][MAX_NUMBER_OF_PAIRS + 1];
	private static int[][] womanPreference = new int[MAX_NUMBER_OF_PAIRS + 1][MAX_NUMBER_OF_PAIRS + 1];
	private static int[][] womanPreferenceInverse = new int[MAX_NUMBER_OF_PAIRS + 1][MAX_NUMBER_OF_PAIRS + 1];
	// man i is matched with woman pairs[i]
	private static int[] pairs = new int[MAX_NUMBER_OF_PAIRS + 1];
	private static int[] husband = new int[MAX_NUMBER_OF_PAIRS + 1];

	private static void init() {
		for (int i = 1; i <= n; i++) {
			pairs[i] = 0;
			husband[i] = 0;
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

	private static void getMatch(int[] match) throws IOException {
		String line;
		String[] list;
		line = reader.readLine();
		list = line.split("\\s");
		int i = 0;
		for (String s : list) {
			match[++i] = Integer.parseInt(s.substring(1));
		}
	}

	private static boolean isPerfect(final int[] pairs) {
		for (int i = 1; i <= n; i++) {
			if (pairs[i] == 0) {
				return false;
			}
			if (husband[pairs[i]] == 0) {
				husband[pairs[i]] = i;
			} else {
				return false;
			}
		}
		return true;
	}

	private static boolean isStable(final int[] pairs) {
		int woman;
		for (int i = 1; i <= n; i++) {
			int j = 0;
			while ((woman = manPreference[i][++j]) != pairs[i]) {
				if (womanPreferenceInverse[woman][i] < womanPreferenceInverse[woman][husband[woman]]) {
					return false;
				}
			}
		}
		return true;
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
				getMatch(pairs);
				if (isPerfect(pairs) && isStable(pairs)) {
					System.out.println("Yes");
				} else {
					System.out.println("No");
				}
			}
		} catch (Exception e) {
			System.exit(0);
		}
	}

}
