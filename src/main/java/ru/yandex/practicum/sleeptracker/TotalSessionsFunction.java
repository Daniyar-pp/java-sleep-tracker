import java.util.List;

public class TotalSessionsFunction {

    public SleepAnalysisResult calculate(List<SleepingSession> sessions) {
        int count = sessions.size();
        return new SleepAnalysisResult("Всего сессий сна", count);
    }
}