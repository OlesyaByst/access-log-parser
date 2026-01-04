import java.time.Duration;
import java.time.LocalDateTime;

public class Statistics {
    private long totalTraffic;
    private LocalDateTime minTime;
    private LocalDateTime maxTime;

    public Statistics() {
        this.totalTraffic = 0;
        this.maxTime = LocalDateTime.MIN;
        this.minTime = LocalDateTime.MAX;
    }

    public void addEntry(LogEntry entry) {
        this.totalTraffic += entry.getResponseSize();
        if (entry.getTime().isBefore(minTime)) minTime = entry.getTime();//сравниваем время
        if (entry.getTime().isAfter(maxTime)) maxTime = entry.getTime();
    }

    public double getTrafficRate() {
        if (minTime == LocalDateTime.MAX || maxTime == LocalDateTime.MIN || minTime.equals(maxTime)) {
            return 0;
        }

        long hours = Duration.between(minTime, maxTime).toHours();
        if (hours < 1) hours = 1;
        return (double) totalTraffic / hours;
    }
}
