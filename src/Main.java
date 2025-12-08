import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {


    private static final int maxLenghtLine = 1024;
    private static final Pattern FIRST_BRACKETS_PATTERN = Pattern.compile("\\((.*?)\\)");

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String path;
        File file;
        int quantity = 0;
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

        int lineCount = 0; //текст строки
        String minLength = null;
        String maxLength = null;//символы в строке
        List<String> allLines = new ArrayList<>(); //хранение всех строк
        try (
                    FileReader fileReader = new FileReader(path);
                    BufferedReader reader = new BufferedReader(fileReader)
            ) {
                String line; //содержимое строки
            List<String> fragment1 = new ArrayList<>();
            List<String> fragment2 = new ArrayList<>();

                while ((line = reader.readLine()) != null) {
                    allLines.add(line);//сами строки
                    lineCount++;
                    Matcher matcher = FIRST_BRACKETS_PATTERN.matcher(line); //хранит текст
                    if (matcher.find()) {
                        String contentInsideBrackets = matcher.group(1); // 1. Извлекаем текст из скобок
                        String[] parts = contentInsideBrackets.split(";");  // текст до ;

                        if (parts.length >= 2) {
                            fragment1.add(parts[0].trim()); // левая - первая часть
                            String botName = parts[0].trim().split("/")[0]; // правая-вторая часть
                            fragment2.add(botName); // Результат: "SemrushBot"
                        }
                    }
                    if (minLength == null || line.length() < minLength.length()) {
                        minLength = line;
                    }
                    if (maxLength == null || line.length() > maxLength.length()) {
                        maxLength = line;
                    }
                    if (line.length() > maxLenghtLine) {
                        throw new RuntimeException("В файле присутствует строка длиннее 1024 символов ");
                    }
                }
            List<String> filteredLines = new ArrayList<>(allLines);
                 filteredLines.remove(minLength);
                 filteredLines.remove(maxLength);
            } catch(RuntimeException e){
                    System.err.println("Ошибка строки");
                    e.printStackTrace();
                } catch(IOException e){
                    System.err.println("Произошла ошибка при чтении файла");
                    e.printStackTrace();
                }
        }
            }
