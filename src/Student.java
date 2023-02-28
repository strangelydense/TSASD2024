public class Student {

    public Student(int id, int grade, String[] preferences, boolean[] history, int type) {
        _id = id;
        _grade = grade;
        _history = history;
        _preferences = preferences;
        _type = type;
    }

    public String getPreference(int index) {
        return _preferences[index];
    }

    public double calculateSatisfaction() {
        double satisfaction = 0;
        for(int i = 0; i < _schedule.length; i++) {
            for (int j = 0; j < _preferences.length; j++) {
                if (_preferences[i].equals(_preferences[j])) {
                    satisfaction++;
                }
            }
        }
        return satisfaction;
    }

        // might completely turn inputted class codes into numbers
    public static boolean isEligible(boolean[] history, String classCode) {
        return false;
    }

    public void reset() {
        _schedule = new String[7];
    }

    public void print() {
        System.out.println(_id);
        System.out.println(_grade);
        System.out.println(_history);
        System.out.println(_preferences);
        System.out.println(_schedule);
        System.out.println(_type);
    }

    protected final int _id;
    protected final int _grade;
    protected final boolean[] _history;
    protected final String[] _preferences;
    protected String[] _schedule;
    protected final int _type;  //camb, running start, late/early release,

}
