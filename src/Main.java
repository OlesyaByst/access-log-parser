import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Введите первое число:");
        int number1 = new Scanner(System.in).nextInt();
        System.out.println("Введите второе число:");
        int number2 = new Scanner(System.in).nextInt();

        System.out.println("Сумма чисел:");
        int sum=number1+number2;
        System.out.println(sum);

        System.out.println("Разность чисел:");
        int difference= number1-number2;
        System.out.println(difference);

        System.out.println("Произведение чисел:");
        int mult=number1*number2;
        System.out.println(mult);

        System.out.println("Частное чисел:");
        double quotient=(double) number1/number2;
        System.out.println(quotient);
    }
}
