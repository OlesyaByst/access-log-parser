public class UserAgent {
    private final String operatingSystem;
    private final String browser;

    public UserAgent(String userAgentString) {
        this.operatingSystem = switch (userAgentString) {
            case String s when s.contains("Windows") -> "Windows";
            case String s when s.contains("Macintosh") -> "macOS";
            case String s when s.contains("Linux") -> "Linux";
            default -> "Unknown OS";
        };

        this.browser = switch (userAgentString) {
            case String s when s.contains("Edge") -> "Edge";
            case String s when s.contains("Firefox") -> "Firefox";
            case String s when s.contains("Chrome") -> "Chrome";
            case String s when s.contains("Opera") -> "Opera";
            default -> "Unknown Browser";
        };
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public String getBrowser() {
        return browser;
    }
}