import java.io.*;
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
            throw new RuntimeException(ex);
        }

        System.out.println("Количество строк в файле: " + parser.getTotalLines());
        System.out.println("Запросов от Googlebot: " + parser.getGoogleBotCount());
        System.out.println("Запросов от YandexBot: " + parser.getYandexBotCount());

        System.out.printf("Доля Googlebot: %.2f%%\n", parser.calculateGoogleShare());
        System.out.printf("Доля YandexBot: %.2f%%\n", parser.calculateYandexShare());
        System.out.println("Средний объём трафика сайта за час: " + stats.getTrafficRate());
    }
}