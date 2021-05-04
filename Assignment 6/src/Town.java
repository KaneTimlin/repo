import java.util.ArrayList;
import java.util.HashSet;

/**
 * @author Kane Timlin
 */
public class Town implements Comparable<Town> {
    private String name;
    private HashSet<Town> adjacentTowns;
    private boolean empty = false;

    /**
     * A basic constructor for Town
     * @param name the name of the town
     */
    public Town(String name) {
        this.name = name;
        adjacentTowns = new HashSet<>();
    }

    /**
     * A deep copy constructor for Town
     * @param copy the town to copy
     */
    public Town(Town copy) {
        this.name = copy.name;
        adjacentTowns = new HashSet<>(copy.adjacentTowns);
        this.empty = copy.empty;
    }

    /**
     * An empty constructor for Town
     */
    public Town() {
        empty = true;
        name = "Empty";
        adjacentTowns = new HashSet<>();
    }

    /**
     * A method that will add the given town to the adjacent towns list
     * Will not function if town is empty, or already in list
     * @param town the town to add
     */
    public void addAdjacentTown(Town town) {
        if (town == null || town.isEmpty()) {
            return;
        }
        if (adjacentTowns.contains(town)) {
            return;
        }
        adjacentTowns.add(town);
    }

    /**
     * A method to remove given town from adjacent towns list.
     * Will not function if town is not in list already
     * @param town the town to remove
     */
    public void removeAdjacentTown(Town town) {
        if (town == null || town.isEmpty()) {
            return;
        }
        adjacentTowns.remove(town);
    }

    /**
     * A method that compares two towns by name
     * @param o the town to compare againts
     * @return -1 if current towns name is smaller, 0 if names are the same, 1 if current name is larger
     */
    @Override
    public int compareTo(Town o) {
        return name.compareTo(o.name);
    }

    /**
     * A method to check if two towns are equal
     * @param o the town to check
     * @return true if equal, false if not
     */
    public boolean equals(Town o) {
        return name.equals(o.name);
    }

    /**
     * A method to get the hashCode of the current town
     * @return the hashCode of the name of the town
     */
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

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }
}
