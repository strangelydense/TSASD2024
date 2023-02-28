import java.util.ArrayList;

public class SchoolClass {

    // new SchoolClass = SchoolClass [classCode](..., ..., ..., ...);  <-- will probably not really do this
    public SchoolClass(String classCode, boolean[] periods, String[] prerequisites, int maximumSize, Student[] applicants) {
//        _availability = applicants.length / maximumSize;
        _classCode = classCode;
        _periods = periods;
        _prerequisites = prerequisites;
        _maximumSize = maximumSize;

    }

    public void addApplicant(Student student){
        _applicants.add(student);
//        for(int i = 0; i < _applicants.size(); i++)
//            if(_applicants.get(i) == null) // dom't know if can compare like this
//                _applicants[i] = student; // IF IT RUNS, IT RUNS
    }

    public void calculateAvailability() {
        _availability = (double) _applicants.size() / _maximumSize;
    }

    public boolean isEligible(Student student) {
        // if(!prerequisite || !grade || !exception? || !gradreqs || !somethingElse)
            // return false;
        return true;
    }

    // need to add period parameter, assuming set schedule first
    public void addStudents() {
        int limit = (_applicants.size() < _maximumSize) ? _applicants.size() : _maximumSize;  // TODO double check how this works
        for(int i = 0; i < limit; i++) {
            _enrolledStudents[i] = _applicants.get(i);
        }
    }

// don't really need this?  if i have addStudents()?  not sure, will keep for fun
//    public void enroll(Student studentId, SchoolClass classCode) {
//        // add studentId or student? to enrolledStudents list
//        // _enrolledStudents.add(studentId);
//        // what if there is already a class there?  oh then skip
//        // remove from pool
//        // can make arrays at maximum size, when classes fill up the array will break and then.... move on???
//    }

    public double getAvailability() {
        return _availability;
    }

    public String getCode() {
        return _classCode;
    }

    public int getEnrolledStudents() {
        return _enrolledStudents.length;
    }

    public void reset() {
        _enrolledStudents = new Student[_maximumSize];
        // _periods = new boolean[7];
    }

    public void print() {
        System.out.println(_classCode);
        System.out.println(_periods);
        System.out.println(_prerequisites);
        System.out.println(_maximumSize);
        System.out.println(_applicants);
        System.out.println(_availability);
        System.out.println(_enrolledStudents);
    }

    protected final String _classCode;
    protected final boolean[] _periods;
    protected final String[] _prerequisites;
    protected final int _maximumSize;
    protected ArrayList<Student> _applicants; // maybe this one should be an arraylist actually and the other one should be fixed
    protected double _availability;
    protected Student[] _enrolledStudents; // = new Student[_maximumSize]; //arraylist?  no, don't think so, set a limit. will probably keep size at maxSize.  is wonky, might also just store IDs.

}
