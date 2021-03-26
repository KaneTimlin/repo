import java.io.IOException;
import java.time.chrono.ChronoPeriod;
import java.util.Iterator;
import java.util.LinkedList;

public class CourseDBStructure implements CourseDBStructureInterface {

    public LinkedList<CourseDBElement>[] hashTable;
    int tableSize;


    public CourseDBStructure(String testing, int size) {
        hashTable = new LinkedList[size];
        tableSize = size;
    }

    public CourseDBStructure(int size) {
        hashTable = new LinkedList[size];
        tableSize = size;
    }

    @Override
    public void add(CourseDBElement element) {
        int hashCode = element.hashCode();
        hashCode = hashCode % hashTable.length;
        if (hashTable[hashCode] == null) {
            hashTable[hashCode] = new LinkedList<>();
            hashTable[hashCode].add(element);
        } else {
            hashTable[hashCode].add(element);
        }
    }

    @Override
    public CourseDBElement get(int crn) throws IOException {
        int hashCode = crn % hashTable.length;
        if (hashTable[hashCode] == null) {
            throw new IOException();
        } else {
            for (CourseDBElement element : hashTable[hashCode]) {
                if (element.getCRN() == crn) {
                    return element;
                }
            }
        }
        throw new IOException();
    }

    @Override
    public int getTableSize() {
        return tableSize;
    }

    protected LinkedList<CourseDBElement>[] getHashTable() {
        return hashTable;
    }
}
