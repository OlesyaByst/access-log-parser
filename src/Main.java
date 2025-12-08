import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {


    private static final int maxLenghtLine = 1024;

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
        List<String> all = new ArrayList<>();
        try (
                    FileReader fileReader = new FileReader(path);
                    BufferedReader reader = new BufferedReader(fileReader)
            ) {
                String line = ""; //содержимое строки
                String firstBrackets = Arrays.toString("\\((.*?)\\)".split(line)); // взяли часть между скобок
            List<String> fragment = new ArrayList<>();
                while ((line = reader.readLine()) != null) {
                    all.add(line);//сами строки
                    lineCount++;
                    String[] parts = firstBrackets.split(";"); //разделили по точке с запятой
                    if (parts.length >= 2) {
                         fragment.add(parts[0].trim());//добавляем фрагменты в массив и удаляем пробелы
                         fragment.add(parts[1].trim().split("/")); // часть до слеша без пробелов
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
            String finalMaxLength = maxLength;
            List<String> filteredLines = all.stream()
                    .filter(line -> !line.equals(minLength) && !line.equals(maxLength)).collect(Collectors.toList());
            } catch(RuntimeException e){
                    System.err.println("Ошибка строки");
                    e.printStackTrace();
                } catch(IOException e){
                    System.err.println("Произошла ошибка при чтении файла");
                    e.printStackTrace();
                }
        }
            }
