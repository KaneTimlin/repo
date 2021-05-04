import javax.xml.transform.Source;

/**
 * @author Kane Timlin
 */
public class Road implements Comparable<Road> {
    private Town source;
    private Town destination;
    private int weight;
    private String name;
    private boolean empty = false;

    /**
     * A full constructor for the Road class
     * @param source the source of the road
     * @param destination the destination of the road
     * @param weight the length of the road
     * @param name the name of the road
     */
    public Road(Town source, Town destination, int weight, String name) {
        this.source = new Town(source);
        this.destination = new Town(destination);
        this.weight = weight;
        this.name = name;
    }

    /**
     * A constructor for the road class that does not take weight
     * @param source the source of the road
     * @param destination the destination of the road
     * @param name the name of the road
     */
    public Road(Town source, Town destination, String name) {
        this.source = new Town(source);
        this.destination = new Town(destination);
        weight = 1;
        this.name = name;
    }

    /**
     * A deep copy constructor for road
     * @param road the road to be copied
     */
    public Road(Road road) {
        this.source = new Town(road.source);
        this.destination = new Town(road.destination);
        this.weight = road.weight;
        this.name = road.name;
        this.empty = road.empty;
    }

    /**
     * An empty constructor for road. only used to denote if a road is empty
     */
    public Road() {
        empty = true;
        name = "Empty";
    }


    /**
     * A method that compares two roads based on their names
     * @param o the road to compare against
     * @return -1 if current name is less, 0 if equal, 1 if more
     */
    @Override
    public int compareTo(Road o) {
        return name.compareTo(o.name);
    }

    /**
     * A method that checks if the road connects to the given town
     * @param town the town to check for
     * @return true if the road connects to the town, false otherwise
     */
    public boolean contains(Town town) {
        return source.equals(town) || destination.equals(town);
    }

    /**
     * A method to check if the current road is equal to the given one
     * @param object the road to check against
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object object) {
        Road o = (Road) object;
        return source.equals(o.source) && destination.equals(o.destination) ||
                source.equals(o.destination) && destination.equals(o.source);
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public Town getDestination() {
        return new Town(destination);
    }

    public Town getSource() {
        return new Town(source);
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public String toString() {
        return name;
    }
}
