import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogParser {
    private static final Pattern FIRST_BRACKETS_PATTERN = Pattern.compile("\\((.*?)\\)");// текст внутри скобок

    private int totalLines = 0; //счетчик строк
    private int googleBotCount = 0; // счетчик бота гугл
    private int yandexBotCount = 0;

    public void parseLine(String line) {
        if (line == null || line.isEmpty()) return; //!! строка пустая, конец

        totalLines++;

        Matcher matcher = FIRST_BRACKETS_PATTERN.matcher(line); //!!
        if (matcher.find()) { //проверка на присутствие круглых скобок
            String firstBrackets = matcher.group(1); // Текст внутри  скобок
            String[] parts = firstBrackets.split(";"); //разбиваем текст на массив

            if (parts.length >= 2) { // проверка на наличие 2х частей в скобках
                String fragment = parts[1].trim(); //берем второй фрагмент и удаляем пробелы
                String botName = fragment.split("/")[0].trim(); // Отделяем часть до слэша и удаляем пробелы

                if ("Googlebot".equalsIgnoreCase(botName)) {
                    googleBotCount++;
                } else if ("YandexBot".equalsIgnoreCase(botName)) {
                    yandexBotCount++;
                }
            }
        }
    }

    public int getTotalLines() {
        return totalLines;
    }

    public int getGoogleBotCount() {
        return googleBotCount;
    }

    public int getYandexBotCount() {
        return yandexBotCount;
    }


    public double calculateGoogleShare() {
        if (totalLines == 0) {
            return 0;
        }
        return (double) googleBotCount / totalLines * 100;
    }

    public double calculateYandexShare() {
        if (totalLines == 0) {
            return 0;
        }
        return (double) yandexBotCount / totalLines * 100;
    }
}