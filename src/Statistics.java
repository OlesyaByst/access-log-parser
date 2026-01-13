import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Statistics {
    private long traffic;
    private LocalDateTime minTime;
    private LocalDateTime maxTime;
    private int realVisits;
    public int errorRequests; //ответы с ошибочными кодами
    private final HashSet<String> ipAdresses200 = new HashSet<>();
    private final HashSet<String> ipAdresses404 = new HashSet<>();
    private final HashMap<String, Integer> statisticsOS = new HashMap<>();
    private final HashSet<String> uniqueUserIp = new HashSet<>();

    public Statistics(int totalRealVisits) {
        this.realVisits = 0;
        this.traffic = 0;
        this.maxTime = null;
        this.minTime = null;
    }

    public Statistics() {
    }

    public void addEntry(LogEntry entry) {
        this.traffic += entry.getResponseSize();
        LocalDateTime entryTime = entry.getTime();

        if (minTime == null || entryTime.isBefore(minTime)) minTime = entryTime;
        //сравниваем время
        if (maxTime == null || entryTime.isAfter(maxTime)) maxTime = entryTime;
        if (entry.getResponseCode() == 200) { //существующие
            ipAdresses200.add(entry.getPath());
        } else if (entry.getResponseCode() == 404) {
            ipAdresses404.add(entry.getPath());
        } else if (entry.getResponseCode() >= 400 && entry.getResponseCode() <= 500) {
            this.errorRequests++;
        }

// проверка, есть ли в этом HashMap запись с таким браузером. Если нет, вставляйте такую запись. Если есть, добавляйте к соответствующему значению единицу.
        String os = entry.getUserAgent().getOperatingSystem();
        if (statisticsOS.containsKey(os)) {
            statisticsOS.put(os, statisticsOS.get(os) + 1);
        } else {
            statisticsOS.put(os, 1);
        }
        if (!entry.getUserAgent().isBot()) {
            this.realVisits++;
            this.uniqueUserIp.add(entry.getIpAddr());
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
        if (minTime == null || maxTime == null || minTime.equals(maxTime)) {
            return 0;
        }

        double hours;
        hours = (double) Duration.between(minTime, maxTime).toMillis() / 3600000;
        if (hours < 1) hours = 1;
        return (double) traffic / hours;
    }

    //  подсчёт среднего количества посещений в час
    public double getAvgUserVisitsPerHour() {
        if (minTime == null || maxTime == null || minTime.equals(maxTime)) return 0;
        double hours = Duration.between(minTime, maxTime).toMillis() / 3600000.0;
        return realVisits / hours;
    }

    //метод подсчёта среднего количества ошибочных запросов в час.
    public int getAvgWrongAnswersPerHour() {
        if (minTime == null || maxTime == null || minTime.equals(maxTime)) return 0;
        double hours = Duration.between(minTime, maxTime).toMillis() / 3600000.0;
        return (int) (hours / errorRequests);
    }

    // метод расчёта средней посещаемости одним пользователем.
    public double getAvgVisitsPerUser() {
        if (uniqueUserIp.isEmpty()) {
            return 0.0;
        }
        return (double) realVisits / uniqueUserIp.size();
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