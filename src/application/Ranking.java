package application;

/**
 * Defines a Ranking object.
 */
public class Ranking implements Comparable<Ranking> {

    private final int rank;
    private final String name;
    private final int points;

    /**
     * Creates a new Ranking.
     * @param rank the player's rank
     * @param name the player's name
     * @param points the player's points
     */
    public Ranking(int rank, String name, int points) {
        this.rank = rank;
        this.name = name;
        this.points = points;
    }

    /**
     * Gets the player's name from a ranking object.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the rank from a ranking object.
     * @return the rank
     */
    public int getRank() {
        return rank;
    }

    /**
     * Gets the points from a ranking object.
     * @return the points
     */
    public int getPoints() {
        return points;
    }

    /**
     * Sorts rankings descending by points.
     * @param r The ranking object to compare.
     * @return 1, 0, oder -1
     */
    @Override
    public int compareTo(Ranking r) {
        return Integer.compare(r.getPoints(), getPoints());
    }
}
