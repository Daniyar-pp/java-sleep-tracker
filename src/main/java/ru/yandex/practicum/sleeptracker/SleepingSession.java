import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Duration;

public class SleepingSession {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String quality;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    public SleepingSession(String startTimeStr, String endTimeStr, String quality) {
        this.startTime = LocalDateTime.parse(startTimeStr, FORMATTER);
        this.endTime = LocalDateTime.parse(endTimeStr, FORMATTER);
        this.quality = quality;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getQuality() {
        return quality;
    }

    public long getDurationMinutes() {
        return Duration.between(startTime, endTime).toMinutes();
    }

    @Override
    public String toString() {
        return String.format("Сон: %s - %s (%s)",
                startTime.format(FORMATTER),
                endTime.format(FORMATTER),
                quality);
    }
}