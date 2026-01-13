import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String path;
        File file;
        int quantity = 0;

        System.out.println("Введите путь до файла:");
        while (true) {
            path = sc.nextLine(); //читаем путь
            file = new File(path);
            boolean fileExists = file.exists();
            boolean isDirectory = file.isDirectory();

            if (fileExists) {
                System.out.println("Путь указан верно");
            } else {
                System.out.println("Путь указан не верно");
            }

            if (isDirectory) {
                System.out.println("Указанный путь введет к папке");
            } else {
                quantity++;
                System.out.println("Указанный путь введет к файлу." + " " + "Это файл номер " + quantity);
                break;
            }
        }


        LogParser parser = new LogParser();
        Statistics stats = new Statistics();


        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                parser.parseLine(line);
                try {
                    LogEntry entry = new LogEntry(line);
                    stats.addEntry(entry);

                } catch (Exception e) {
                }
            }
        } catch (IOException ex) {
            System.out.println("Ошибка доступа");
        }

        System.out.println("Количество строк в файле: " + parser.getTotalLines());
        System.out.println("Запросов от Googlebot: " + parser.getGoogleBotCount());
        System.out.println("Запросов от YandexBot: " + parser.getYandexBotCount());

        System.out.printf("Доля Googlebot: %.2f%%\n", parser.calculateGoogleShare());
        System.out.printf("Доля YandexBot: %.2f%%\n", parser.calculateYandexShare());
        System.out.println("Средний объём трафика сайта за час: " + stats.getTrafficRate());
        System.out.println(stats.getStatisticsOS());
        System.out.println("Средний трафик в час: " + stats.getTrafficRate());

        System.out.println("Статистика ОС:");
        Map<String, Double> osProportions = stats.getOsProportions();
        for (Map.Entry<String, Double> entry : osProportions.entrySet()) { //проходим по мапе и достаем ключ+значение
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
/*System.out.println("\nСписок страниц с кодом 200:");
        for (String page1 : stats.getIpAdresses200()) {
            System.out.println(page1);
        }
        System.out.println("\nСписок страниц с кодом 404:");
        for (String page2 : stats.getIpAdresses404()) {
            System.out.println(page2);
      }
*/
        System.out.println("Среднее кол-во посещений в час: " + stats.getAvgUserVisitsPerHour());
        System.out.println("Среднее кол-во ошибочных запросов в час: " + stats.getAvgWrongAnswersPerHour());
        System.out.println("Средняя посещаемость одним пользователем: " + stats.getAvgUserVisitsPerHour());
    }
}