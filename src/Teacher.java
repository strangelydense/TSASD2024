public class Teacher {
    public Teacher(String name, int id, boolean[] availability, boolean[] qualifications) {
        _name = name;
        _id = id;
        _availability = availability;
        _qualifications = qualifications;
    }

//    public void assignClass(SchoolClass class, int period) {
//        _schedule[period] = class.getCode();
//    }

    public void assignClass(String classCode, int period) {
        // qualifications?  oh wait, figure out students -> figure out classes -> toss in teachers
        _schedule[period] = classCode;
    }

    public void reset() {
        _schedule = new String[7];
    }

    public void print() {
        System.out.println(_name);
        System.out.println(_id);
        System.out.println(_availability);
        System.out.println(_qualifications);
        System.out.println(_schedule);
    }

    protected final String _name;
    protected final int _id;
    protected final boolean[] _availability;
    protected final boolean[] _qualifications; // should change the type
    protected String[] _schedule; //class code or class object?


}
