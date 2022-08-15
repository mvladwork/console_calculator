import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) {
        try {
            Scanner s = new Scanner(System.in);
            System.out.println("КАЛЬКУЛЯТОР");
            System.out.println("Введите арифметическую задачу! (Примеры: 1+2, 4-3, 5*6, 8/7, X-IX)");
            while(true) {
                String query = s.nextLine();
                String result = Main.calc(query);
                System.out.println(result);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }

    }
}
