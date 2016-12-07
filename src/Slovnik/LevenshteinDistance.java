package Slovnik;

/**
 * Implementace Levenshteinovy metriky.
 */
public class LevenshteinDistance {

    /**
     * Metoda pro výpočet Levenshteinovy metriky dvou slov.
     * @param a zadané slovo.
     * @param b slovo ze slovníku.
     * @return vrací distanci.
     */

    public static int distance(String a, String b) {
        String aLowerCase = a.toLowerCase();
        String bLowerCase = b.toLowerCase();
        // i == 0
        int [] costs = new int [bLowerCase.length() + 1];
        for (int j = 0; j < costs.length; j++){
            costs[j] = j;
        }
        for (int i = 1; i <= aLowerCase.length(); i++) {
            // j == 0; nw = lev(i - 1, j)
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= bLowerCase.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), aLowerCase.charAt(i - 1) == bLowerCase.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[b.length()];
    }

}
