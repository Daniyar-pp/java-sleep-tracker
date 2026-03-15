import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class ChronotypeFunctionTest {

    private ChronotypeFunction function;

    @BeforeEach
    void setUp() {
        function = new ChronotypeFunction();
    }

    @Test
    @DisplayName("Тест 1: Пустой список - недостаточно данных")
    void testEmptyList() {
        List<SleepingSession> emptyList = new ArrayList<>();
        SleepAnalysisResult result = function.calculate(emptyList);

        assertEquals("Хронотип пользователя", result.getDescription());
        assertEquals("Недостаточно данных", result.getValue());
    }

    @Test
    @DisplayName("Тест 2: Только дневные сессии - недостаточно ночных сессий")
    void testOnlyDaySessions() {
        List<SleepingSession> sessions = new ArrayList<>();
        // Дневные сессии (не должны учитываться)
        sessions.add(new SleepingSession("01.10.25 14:00", "01.10.25 16:00", "NORMAL"));
        sessions.add(new SleepingSession("02.10.25 10:00", "02.10.25 12:00", "GOOD"));

        SleepAnalysisResult result = function.calculate(sessions);

        assertEquals("Недостаточно ночных сессий для определения", result.getValue());
    }

    @Test
    @DisplayName("Тест 3: Все ночи - совы")
    void testAllOwlNights() {
        List<SleepingSession> sessions = new ArrayList<>();

        // Совы: засыпание после 23:00, пробуждение после 9:00
        sessions.add(new SleepingSession("01.10.25 23:30", "02.10.25 09:30", "GOOD"));
        sessions.add(new SleepingSession("02.10.25 23:45", "03.10.25 10:00", "NORMAL"));
        sessions.add(new SleepingSession("03.10.25 23:15", "04.10.25 09:15", "GOOD"));

        SleepAnalysisResult result = function.calculate(sessions);

        assertEquals("Сова", result.getValue());
    }

    @Test
    @DisplayName("Тест 4: Все ночи - жаворонки")
    void testAllLarkNights() {
        List<SleepingSession> sessions = new ArrayList<>();

        // Жаворонки: засыпание до 22:00, пробуждение до 7:00
        sessions.add(new SleepingSession("01.10.25 21:30", "02.10.25 06:30", "GOOD"));
        sessions.add(new SleepingSession("02.10.25 21:15", "03.10.25 06:45", "NORMAL"));
        sessions.add(new SleepingSession("03.10.25 21:45", "04.10.25 06:15", "GOOD"));

        SleepAnalysisResult result = function.calculate(sessions);

        assertEquals("Жаворонок", result.getValue());
    }
}