import java.util.ArrayList;

public class Town implements Comparable<Town> {
    private String name;
    private ArrayList<Town> adjacentTowns;

    public Town(String name) {
        this.name = name;
        adjacentTowns = new ArrayList<>();
    }

    public Town(Town copy) {
        this.name = copy.name;
        adjacentTowns = new ArrayList<>(copy.adjacentTowns);
    }

    @Override
    public int compareTo(Town o) {
        return name.compareTo(o.name);
    }

    public boolean equals(Town o) {
        return name.equals(o.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Town> getAdjacentTowns() {
        return new ArrayList<>(adjacentTowns);
    }

    @Override
    public String toString() {
        return name;
    }
}
