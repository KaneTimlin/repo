import java.util.Comparator;

public class CourseDBElement {

    private String courseID;
    private int CRN;
    private int numCredits;
    private String roomNumber;
    private String instructor;

    public CourseDBElement() {

    }

    public CourseDBElement(String courseID, int CRN, int numCredits, String roomNumber, String instructor) {
        this.courseID = courseID;
        this.CRN = CRN;
        this.numCredits = numCredits;
        this.roomNumber = roomNumber;
        this.instructor = instructor;
    }

    public CourseDBElement(CourseDBElement dbElement) {
        this.courseID = dbElement.getCourseID();
        this.CRN = dbElement.getCRN();
        this.numCredits = dbElement.getNumCredits();
        this.roomNumber = dbElement.getRoomNumber();
        this.instructor = dbElement.getInstructor();
    }

    public int hashCode() {
        return CRN;
    }

    @Override
    public String toString() {
        return String.format("Course:%s CRN:%d Credits:%d Instructor:%s Room:%s", courseID, CRN, numCredits, instructor, roomNumber);
    }

    public int getCRN() {
        return CRN;
    }

    public String getCourseID() {
        return courseID;
    }

    public int getNumCredits() {
        return numCredits;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public void setCRN(int CRN) {
        this.CRN = CRN;
    }

    public void setNumCredits(int numCredits) {
        this.numCredits = numCredits;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

}
