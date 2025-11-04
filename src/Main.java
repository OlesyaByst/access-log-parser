import java.io.File;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
      Scanner sc=new Scanner(System.in);
        int quantity =0;
        while (true) {
        String path = sc.nextLine();
        File file = new File(path);
        boolean fileExists = file.exists();
        boolean isDirectory = file.isDirectory();
           if (fileExists == true) {
                System.out.println("Путь указан верно");
            } else {
                System.out.println("Путь указан не верно");
                continue;
            }
            if (isDirectory == true) {
                System.out.println("Указанный путь введет к папке");
            } else {
                quantity++;
                System.out.println("Указанный путь введет к файлу."+" "+"Это файл номер"+quantity);
                continue;
            }
        }
    }
}


