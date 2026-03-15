public class Chronotype {
    public static final String OWL = "Сова";
    public static final String LARK = "Жаворонок";
    public static final String DOVE = "Голубь";

    public static String determineChronotype(int sleepHour, int wakeHour) {
        if (sleepHour >= 23 && wakeHour >= 9) {
            return OWL;
        } else if (sleepHour < 22 && wakeHour < 7) {
            return LARK;
        } else {
            return DOVE;
        }
    }
}