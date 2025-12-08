import java.io.*;
import java.util.Scanner;

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

        int lineCount = 0; //счетчик строк
        int minLength = Integer.MAX_VALUE;
        int maxLength = Integer.MIN_VALUE;//символы в строке
            try (
                    FileReader fileReader = new FileReader(path);
                    BufferedReader reader = new BufferedReader(fileReader)
            ) {
                String line;
                while ((line = reader.readLine()) != null) {
                    int length = line.length();
                    lineCount++;
                    if (length < minLength) {
                        minLength = length;
                    }
                    if (length > maxLength) {
                        maxLength = length;
                    }
                    if (length > maxLenghtLine) {
                        throw new RuntimeException("В файле присутствует строка длиннее 1024 символов ");
                    }
                }
            } catch(RuntimeException e){
                    System.err.println("Ошибка строки");
                    e.printStackTrace();
                } catch(IOException e){
                    System.err.println("Произошла ошибка при чтении файла");
                    e.printStackTrace();
                } finally{
                    System.out.println("Длина самой длинной строки в файле: " + maxLength);
                    System.out.println("Длина самой короткой строки в файле: " + minLength);
                    System.out.println("Общее количество строк в файле: " + lineCount);
                }
            }
        }
