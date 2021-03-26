import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CourseDBManager_STUDENT_Test {

    CourseDBManager manager;
    CourseDBElement element1;
    CourseDBElement element2;

    @BeforeEach
    void setUp() {
        manager = new CourseDBManager(31);
        element1 = new CourseDBElement("CMSC222", 39939, 4, "SCI335", "Daniel Longbottom");
        element2 = new CourseDBElement("ENEE227", 55239, 3, "ELE345", "Harry Styles");
    }

    @AfterEach
    void tearDown() {
        manager = null;
        element1 = null;
        element2 = null;
    }

    @Test
    void add() {
        try {
            manager.add("CMSC222", 39939, 4, "SCI335", "Daniel Longbottom");
        } catch (Exception e) {
            fail("This should not have thrown an exception");
        }
        assertEquals("\nCourse:CMSC222 CRN:39939 Credits:4 Instructor:Daniel Longbottom Room:SCI335",
                manager.showAll().get(0));

    }

    @Test
    void get() {
        manager.add(element1);
        manager.add(element2);
        assertEquals(element1.toString(), manager.get(39939).toString());
    }

    @Test
    void readFile() {
        File file = new File("C:\\Users\\Kane Timlin\\OneDrive\\Spring 2021 School\\CMSC 204\\Workspace\\Assignment 4\\src\\courses.txt");
        try {
            manager.readFile(file);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Should not have thrown an exception");
        }

    }

    @Test
    void showAll() {
        manager.add(element1);
        manager.add(element2);
        ArrayList<String> list = manager.showAll();

        assertEquals("\nCourse:CMSC222 CRN:39939 Credits:4 Instructor:Daniel Longbottom Room:SCI335",
                list.get(0));
        assertEquals("\nCourse:ENEE227 CRN:55239 Credits:3 Instructor:Harry Styles Room:ELE345",
                list.get(1));
    }
}