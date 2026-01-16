import java.util.Collection;

public class UserAgent {
    private final String operatingSystem;
    private final String browser;
    private final String rawString; //  строка для проверки на бота

    public UserAgent(String userAgentString) {
        this.rawString = userAgentString; // Запоминаем входящую строку

        // Определение ОС через современный switch (Java 21+)
        this.operatingSystem = switch (userAgentString) {
            case String s when s.contains("Windows") -> "Windows";
            case String s when s.contains("Macintosh") -> "macOS";
            case String s when s.contains("Linux") -> "Linux";
            default -> "Unknown OS";
        };

        // Определение Браузера
        this.browser = switch (userAgentString) {
            case String s when s.contains("Edge") -> "Edge";
            case String s when s.contains("Firefox") -> "Firefox";
            case String s when s.contains("Chrome") -> "Chrome";
            case String s when s.contains("Opera") -> "Opera";
            default -> "Unknown Browser";
        };
    }

    // проверка на бота
    public boolean isBot() {
        if (rawString == null) return false;
        return rawString.toLowerCase().contains("bot");
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public String getBrowser() {
        return browser;
    }

    public Collection<Object> toLowerCase() {
        return java.util.List.of();
    }
}