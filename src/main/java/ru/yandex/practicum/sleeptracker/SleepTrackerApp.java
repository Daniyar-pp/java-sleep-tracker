import java.io.*;
import java.util.*;

public class SleepTrackerApp {

    private List<Object> functions;

    public SleepTrackerApp() {
        functions = new ArrayList<>();
        functions.add(new TotalSessionsFunction());
        functions.add(new SleeplessNightsFunction());
        functions.add(new ChronotypeFunction());
    }

    public void addFunction(Object function) {
        functions.add(function);
    }

    public List<SleepingSession> readSleepFile(String fileName) {
        List<SleepingSession> sessions = new ArrayList<>();

        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file, "UTF-8");

            int lineNumber = 0;

            while (scanner.hasNextLine()) {
                lineNumber++;
                String line = scanner.nextLine().trim();

                if (line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split(";");

                if (parts.length != 3) {
                    System.out.println("Строка " + lineNumber + " пропущена - неверный формат");
                    continue;
                }

                String start = parts[0].trim();
                String end = parts[1].trim();
                String quality = parts[2].trim();

                if (!quality.equals("GOOD") && !quality.equals("NORMAL") && !quality.equals("BAD")) {
                    System.out.println("Строка " + lineNumber + " пропущена - неизвестное качество: " + quality);
                    continue;
                }

                SleepingSession session = new SleepingSession(start, end, quality);
                sessions.add(session);
            }

            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("Ошибка: файл '" + fileName + "' не найден!");
            System.out.println("Положите файл в папку: " + System.getProperty("user.dir"));
        }

        return sessions;
    }

    public List<SleepAnalysisResult> runAllAnalyses(List<SleepingSession> sessions) {
        List<SleepAnalysisResult> results = new ArrayList<>();

        for (Object func : functions) {

            if (func instanceof TotalSessionsFunction) {
                TotalSessionsFunction f = (TotalSessionsFunction) func;
                results.add(f.calculate(sessions));
            } else if (func instanceof SleeplessNightsFunction) {
                SleeplessNightsFunction f = (SleeplessNightsFunction) func;
                results.add(f.calculate(sessions));
            } else if (func instanceof ChronotypeFunction) {
                ChronotypeFunction f = (ChronotypeFunction) func;
                results.add(f.calculate(sessions));
            }
        }

        return results;
    }

    public static void main(String[] args) {
        String fileName;

        if (args.length >= 1) {
            fileName = args[0];
        } else {
            fileName = "sleep_log.txt";
        }

        SleepTrackerApp app = new SleepTrackerApp();
        List<SleepingSession> sessions = app.readSleepFile(fileName);

        if (sessions.isEmpty()) {
            System.out.println("Нет данных для анализа");
            return;
        }

        System.out.println("Загружено сессий: " + sessions.size());
        System.out.println("\nСодержимое файла:");
        for (SleepingSession session : sessions) {
            System.out.println(session);
        }

        System.out.println("\n=== РЕЗУЛЬТАТЫ АНАЛИЗА ===");

        List<SleepAnalysisResult> results = app.runAllAnalyses(sessions);

        for (SleepAnalysisResult result : results) {
            result.print();
        }
    }
}