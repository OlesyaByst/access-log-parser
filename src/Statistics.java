import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Statistics {
    private long totalTraffic;
    private LocalDateTime minTime;
    private LocalDateTime maxTime;
    private final HashSet<String> ipAdresses200 = new HashSet<>();
    private final HashSet<String> ipAdresses404 = new HashSet<>();
    private final HashMap<String, Integer> statisticsOS = new HashMap<>();

    public Statistics() {
        this.totalTraffic = 0;
        this.maxTime = LocalDateTime.MIN;
        this.minTime = LocalDateTime.MAX;
    }

    public void addEntry(LogEntry entry) {
        this.totalTraffic += entry.getResponseSize();
        if (entry.getTime().isBefore(minTime)) minTime = entry.getTime();//сравниваем время
        if (entry.getTime().isAfter(maxTime)) maxTime = entry.getTime();
        if (entry.getResponseCode() == 200) { //существующие
            ipAdresses200.add(entry.getPath());
        } else if (entry.getResponseCode() == 404) {
            ipAdresses404.add(entry.getPath());
        }
// проверка, есть ли в этом HashMap запись с таким браузером. Если нет, вставляйте такую запись. Если есть, добавляйте к соответствующему значению единицу.
        String os = entry.getUserAgent().getOperatingSystem();
        if (statisticsOS.containsKey(os)) {
            statisticsOS.put(os, statisticsOS.get(os) + 1);
        } else {
            statisticsOS.put(os, 1);
        }
    }

    //HAshMap новая+ расчет каждой ос
    public HashMap<String, Double> getOsProportions() {
        HashMap<String, Double> proportionOS = new HashMap<>();
        int totalOSCount = 0; // общее кол-во ос

        for (Integer count : statisticsOS.values()) {
            totalOSCount += count;
        }

        if (totalOSCount == 0) return proportionOS;
        // расчет каждой ос
        for (Map.Entry<String, Integer> pair : statisticsOS.entrySet()) {
            String osName = pair.getKey();
            Integer count = pair.getValue();
            double fraction = (double) count / totalOSCount;
            proportionOS.put(osName, fraction);
        }
        return proportionOS;
    }

    // Средний объём трафика сайта за час
    public double getTrafficRate() {
        if (minTime == LocalDateTime.MAX || maxTime == LocalDateTime.MIN || minTime.equals(maxTime)) {
            return 0;
        }

        long hours = Duration.between(minTime, maxTime).toHours();
        if (hours < 1) hours = 1;
        return (double) totalTraffic / hours;
    }

    public HashSet<String> getIpAdresses200() {
        return ipAdresses200;
    }

    public HashMap<String, Integer> getStatisticsOS() {
        return statisticsOS;
    }

    public HashSet<String> getIpAdresses404() {
        return ipAdresses404;
    }
}