import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class CourseDBManager implements CourseDBManagerInterface {

    private CourseDBStructure structure;

    public CourseDBManager() {
        structure = new CourseDBStructure(20);
    }
    public CourseDBManager(int size) {
        structure = new CourseDBStructure(size);
    }

    @Override
    public void add(String id, int crn, int credits, String roomNum, String instructor) {
        CourseDBElement element = new CourseDBElement(id, crn, credits, roomNum, instructor);
        structure.add(element);
    }

    public void add(CourseDBElement element) {
        structure.add(element);
    }

    @Override
    public CourseDBElement get(int crn) {
        try {
            return structure.get(crn);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void readFile(File input) throws FileNotFoundException {
        FileReader inFile = new FileReader(input);
        BufferedReader br = new BufferedReader(inFile);
        String[] params;
        CourseDBElement toAdd;
        try {
            String line = br.readLine();
            while (line != null) {
                params = line.split(" ", 5);
                toAdd = new CourseDBElement(params[0], Integer.parseInt(params[1]), Integer.parseInt(params[2]), params[3], params[4]);
                structure.add(toAdd);
                line = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }

    @Override
    public ArrayList<String> showAll() {
        ArrayList<String> output = new ArrayList<>();
        LinkedList<CourseDBElement>[] hashTable = structure.getHashTable();
        LinkedList<CourseDBElement> list;
        for (int i = 0; i < structure.tableSize; i++) {
            if (hashTable[i] != null) {
                list = hashTable[i];
                for (CourseDBElement element : list) {
                    output.add("\n" + element.toString());
                }
            }
        }

        return output;
    }
}
