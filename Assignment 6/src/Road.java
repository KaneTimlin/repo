public class Road implements Comparable<Road> {
    Town source;
    Town destination;
    int weight;
    String name;

    public Road(Town source, Town destination, int weight, String name) {
        this.source = new Town(source);
        this.destination = new Town(destination);
        this.weight = weight;
        this.name = name;
    }

    public Road(Town source, Town destination, String name) {
        this.source = new Town(source);
        this.destination = new Town(destination);
        weight = 1;
        this.name = name;
    }

    @Override
    public int compareTo(Road o) {
        return name.compareTo(o.name);
    }

    public boolean contains(Town town) {
        return source.equals(town) || destination.equals(town);
    }

    public boolean equals(Road o) {
        return source.equals(o.source) && destination.equals(o.destination) ||
                source.equals(o.destination) && destination.equals(o.source);
    }

    public Town getDestination() {
        return new Town(destination);
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
