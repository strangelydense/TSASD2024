class StudentTest {

    @org.junit.jupiter.api.Test
    void getPreference() {
        String[] preferences = {"shaking", "crying", "sobbing"};
        boolean[] history = {true, false, false, true, false};
        Student a = new Student(1, 1, preferences, history, 1);
        assert(a.getPreference(1) == preferences[1]); //what am i doing; i think it runs
    }

    @org.junit.jupiter.api.Test
    void calculateSatisfaction() {
    }

    @org.junit.jupiter.api.Test
    void isEligible() {
    }
}