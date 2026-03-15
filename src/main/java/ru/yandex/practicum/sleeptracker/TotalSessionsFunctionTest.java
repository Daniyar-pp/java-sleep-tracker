import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class TotalSessionsFunctionTest {

    private TotalSessionsFunction function;

    @BeforeEach
    void setUp() {
        function = new TotalSessionsFunction();
    }

    @Test
    @DisplayName("Тест 1: Пустой список должен вернуть 0")
    void testEmptyList() {
        List<SleepingSession> emptyList = new ArrayList<>();

        SleepAnalysisResult result = function.calculate(emptyList);

        assertEquals("Всего сессий сна", result.getDescription());
        assertEquals(0, result.getValue());
    }

    @Test
    @DisplayName("Тест 2: Список с тремя сессиями должен вернуть 3")
    void testListWithThreeSessions() {
        // Создаем список с тремя сессиями
        List<SleepingSession> sessions = new ArrayList<>();
        sessions.add(new SleepingSession("01.10.25 22:00", "02.10.25 08:00", "GOOD"));
        sessions.add(new SleepingSession("02.10.25 23:00", "03.10.25 08:00", "NORMAL"));
        sessions.add(new SleepingSession("03.10.25 23:30", "04.10.25 06:20", "BAD"));

        SleepAnalysisResult result = function.calculate(sessions);

        assertEquals("Всего сессий сна", result.getDescription());
        assertEquals(3, result.getValue());
    }
}