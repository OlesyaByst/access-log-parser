import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogParser {
    private static final Pattern FIRST_BRACKETS_PATTERN = Pattern.compile("\\((.*?)\\)");// текст внутри скобок

    private int totalLines = 0;
    private int googleBotCount = 0;
    private int yandexBotCount = 0;

    public void parseLine(String line) {
        if (line == null || line.isEmpty()) return;

        totalLines++;
        String lowerLine = line.toLowerCase();
        if (lowerLine.contains("googlebot")) {
            googleBotCount++;
        } else if (lowerLine.contains("yandexbot")) {
            yandexBotCount++;
        }
        Matcher matcher = FIRST_BRACKETS_PATTERN.matcher(line); //!!
        if (matcher.find()) {
            String firstBrackets = matcher.group(1);
            String[] parts = firstBrackets.split(";");

            if (parts.length >= 2) {
                String fragment = parts[1].trim();
                String botName = fragment.split("/")[0].trim();
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