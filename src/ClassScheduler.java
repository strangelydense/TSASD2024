// TODO cry
// TODO RUN TESTS ON EXISTING CODE
// TODO figure out an actual way to sort subjects
    // how to i handle multiple of the same subject in one period?  new parameter?  arraylist of _schedule and just fill with period numbers instead of booleans?
    // do i have a big master schedule or keep schedule information in each SchoolClass object?  i could do both, actually
// and then maybe how to create a schedule (dubious)(highly unlikely)(fools errand)(stupid)(could break everything)(haven't even run tests)

public class ClassScheduler {

    public static void main(String[] args) {
        // *magically get the data and know how to read it*
        // go through every class and make a list of its applicants,
            // maybe other way around, go through students and add into their classes
        // sort all classes from greatest to least availability
        // provide teachers, students, classes, maximum, minimum upon start
        // classes connected to numbers or codes?

        sortSubjectsByAvailability();
        calculateDemand();
        assignStudents();
        // createSchedule();
        // assignTeachers();  might.,... just... use a ... random number generator.... for even dispersal... remove from pool once >4 or 5 classes assigned.... yeah...
        if(!checkerLOL())
            poop();
        // print schedules?  idk have to see how far i get
    }

    // use the resulting list in order to start filling classes
    public static void sortSubjectsByAvailability() {
        //sort _allSubjects?
        // problem with a hashmap is that i don't know how to traverse one, and making a separate list of "numbers" (read: keys) sounds ugly
        // fucking quicksort, low to high, then go through, fill, and desperately hope for the best
    }

    // goes through each student's preferences, adding one "demand" to each subject listed
    public static void calculateDemand() {
        for(int i = 0; i < _allStudents.length; i++) {
            Student currentStudent = _allStudents[i];
            for(int j = 0; j < 8; j++) {
                // hash map?  whatever that is?  HashMap<String, SchoolClass> _allSchoolClasses = new HashMap<String, SchoolClass>;
                // if(_allSchoolClasses.get(currentStudent.getPreference(j)).isEligible(currentStudent))
                    // _allSchoolClasses.get(currentStudent.getPreference(j)).addDemand();
                // god i hope this isn't too long this isn't even the start
            }
        }
    }

    // _allSchoolClasses should be sorted at this point
    public static void assignStudents() {
        for(int i = 0; i < _allSchoolClasses.length; i++) {
            _allSchoolClasses[i].addStudents();
        }
    }
    /*
    something to go from classCode to className
    something about assigning to teachers
    something about preferredClasses object type + eligibility object
        type? (don't want to recompute every time)
     */

    public static void createSchedule() {
        //should i use that genetic algorithm thing?  then i can associate types of generation with satisfaction.  don't know how long it will take.
    }

    public static void assignTeachers() {

    }

    public static void reset() {
        for(Student i : _allStudents)
            i.reset();
        for(SchoolClass i : _allSchoolClasses)
            i.reset();
        for(Teacher i : _allTeachers)
            i.reset();
    }

    // switch case?
    public static boolean checkerLOL() { // too many students, prerequisites, overlap?, grad reqs,
        for(int i = 0; i < _allSchoolClasses.length; i++) {
            SchoolClass currentClass = _allSchoolClasses[i]; //terrible name
            if (currentClass.getEnrolledStudents() > _maximum)
                return false;
        }
        return false;
    }

    // most valuable function
    public static void poop(){}

    protected static SchoolClass[] _allSchoolClasses;
    protected static Student[] _allStudents;
    protected static Teacher[] _allTeachers;
    protected static int _maximum;
}

/*
uhhhhhh
1. get all data
2. create student, class, teacher objects, and set physical restrictions (legal, size, etc.)
3. go through array of all students and their preference (primary?), and count demand
4. your mom

still debating a stages process to make things easier
 */