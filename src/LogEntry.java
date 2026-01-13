import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogEntry {
    private final String ipAddr, path, referer; //IP-адресу, путь
    private final LocalDateTime time;// дата
    private final EHttpMethod method;  //метод запроса
    private final int responseCode, responseSize;//код ответа, размер
    private final UserAgent userAgent;

    private static final String LOG_PATTERN =
            "^(\\d+\\.\\d+\\.\\d+\\.\\d+) .* .* \\[(.*)\\] \"(\\w+) (.*) HTTP/.*\" (\\d+) (\\d+) \"(.*)\" \"(.*)\"";

    public LogEntry(String line) {
        Pattern pattern = Pattern.compile(LOG_PATTERN); // шаблон
        Matcher matcher = pattern.matcher(line); // накладывает шаблон на строку
        if (matcher.find()) {
            this.ipAddr = matcher.group(1);
            String dateStr = matcher.group(2);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);
            this.time = LocalDateTime.parse(dateStr, formatter);
            this.method = EHttpMethod.valueOf(matcher.group(3));
            this.path = matcher.group(4);
            this.responseCode = Integer.parseInt(matcher.group(5));
            this.responseSize = Integer.parseInt(matcher.group(6));
            this.referer = matcher.group(7);
            this.userAgent = new UserAgent(matcher.group(8));
        } else {
            throw new IllegalArgumentException("Строка не соответствует формату лога: " + line);
        }
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public String getPath() {
        return path;
    }

    public String getReferer() {
        return referer;
    }

    public UserAgent getUserAgent() {
        return userAgent;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public EHttpMethod getMethod() {
        return method;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public int getResponseSize() {
        return responseSize;
    }
}