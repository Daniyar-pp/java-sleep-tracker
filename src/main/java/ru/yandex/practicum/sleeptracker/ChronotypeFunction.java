import java.util.List;
import java.time.LocalDateTime;


public class ChronotypeFunction {

    public SleepAnalysisResult calculate(List<SleepingSession> sessions) {
        if (sessions == null || sessions.isEmpty()) {
            return new SleepAnalysisResult("Хронотип пользователя", "Недостаточно данных");
        }

        String chronotype = determineUserChronotype(sessions);
        return new SleepAnalysisResult("Хронотип пользователя", chronotype);
    }

    private String determineUserChronotype(List<SleepingSession> sessions) {
        int owlCount = 0;
        int larkCount = 0;
        int doveCount = 0;

        for (SleepingSession session : sessions) {
            LocalDateTime start = session.getStartTime();
            LocalDateTime end = session.getEndTime();

            if (isNightSession(start, end)) {
                int sleepHour = start.getHour();
                int wakeHour = end.getHour();

                String nightType = Chronotype.determineChronotype(sleepHour, wakeHour);

                if (nightType.equals(Chronotype.OWL)) {
                    owlCount++;
                } else if (nightType.equals(Chronotype.LARK)) {
                    larkCount++;
                } else {
                    doveCount++;
                }
            }
        }

        return getMostFrequentChronotype(owlCount, larkCount, doveCount);
    }

    private boolean isNightSession(LocalDateTime start, LocalDateTime end) {
        long duration = java.time.Duration.between(start, end).toHours();

        int startHour = start.getHour();
        int endHour = end.getHour();

        boolean isLongEnough = duration >= 4;
        boolean isNightTime = (startHour >= 18 || startHour <= 4) && (endHour <= 12);

        return isLongEnough && isNightTime;
    }

    private String getMostFrequentChronotype(int owlCount, int larkCount, int doveCount) {
        if (owlCount == 0 && larkCount == 0 && doveCount == 0) {
            return "Недостаточно ночных сессий для определения";
        }

        int maxCount = Math.max(owlCount, Math.max(larkCount, doveCount));

        if (owlCount == maxCount && owlCount > larkCount && owlCount > doveCount) {
            return Chronotype.OWL;
        } else if (larkCount == maxCount && larkCount > owlCount && larkCount > doveCount) {
            return Chronotype.LARK;
        } else {
            return Chronotype.DOVE;
        }
    }
}