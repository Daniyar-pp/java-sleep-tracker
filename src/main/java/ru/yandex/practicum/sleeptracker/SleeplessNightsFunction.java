import java.util.List;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class SleeplessNightsFunction {

    public SleepAnalysisResult calculate(List<SleepingSession> sessions) {
        if (sessions == null || sessions.isEmpty()) {
            return new SleepAnalysisResult("Количество бессонных ночей", 0);
        }

        int sleeplessNights = countSleeplessNights(sessions);
        return new SleepAnalysisResult("Количество бессонных ночей", sleeplessNights);
    }

    private int countSleeplessNights(List<SleepingSession> sessions) {
        LocalDateTime firstDate = sessions.get(0).getStartTime();
        LocalDateTime lastDate = sessions.get(sessions.size() - 1).getEndTime();
        LocalDateTime nightStart = firstDate.toLocalDate().atTime(0, 0);
        LocalDateTime nightEnd = nightStart.plusHours(6); // до 6:00

        int sleeplessCount = 0;

        while (nightStart.isBefore(lastDate)) {
            boolean hasSleepInThisNight = false;

            for (SleepingSession session : sessions) {
                if (sleepsInNight(session, nightStart, nightEnd)) {
                    hasSleepInThisNight = true;
                    break;
                }
            }

            if (!hasSleepInThisNight) {
                sleeplessCount++;
            }

            nightStart = nightStart.plusDays(1);
            nightEnd = nightEnd.plusDays(1);
        }

        return sleeplessCount;
    }

    private boolean sleepsInNight(SleepingSession session, LocalDateTime nightStart, LocalDateTime nightEnd) {
        LocalDateTime sessionStart = session.getStartTime();
        LocalDateTime sessionEnd = session.getEndTime();

        return sessionStart.isBefore(nightEnd) && sessionEnd.isAfter(nightStart);
    }
}