import java.util.regex.Pattern;
import java.util.regex.Matcher;

class Main {

    public static String calc(String input) throws Exception {

        // Проверка полученной строки на наличие более одного арифметического действия (1+2+3)
        if (input.matches("^([0-9]+||[IVX]+)\\s?([+-/*/])\\s?([0-9]+||[IVX]+)(\\s?[+-/*/]\\s?([0-9]+||[IVX]+))+$")) {
            throw new Exception("Формат математической операции не удовлетворяет заданию");
        }

        // Вывод ошибки, если в строке только цифры и символы римского алфавита (до 10)
        if (input.matches("^[\\wIXV]+$")) {
            throw new Exception("Cтрока не является математической операцией");
        }

        // Разделили строку с задачей на части и сохранили в соответсвующих переменных
        // Задачи можно писать как с пробелом, так и без (a+b, a + b)
        String value1 = "";
        String value2 = "";
        String action = "";
        int one = 0;
        int two = 0;
        String outputType = "decimal";

        try {
            Pattern pattern = Pattern.compile("^([0-9]+||[IVX]+)\\s?([+-/*/])\\s?([0-9]+||[IVX]+)$");
            Matcher m = pattern.matcher(input);
            m.find();
            value1 = m.group(1);
            value2 = m.group(3);
            action = m.group(2);

        } catch (Exception e) {
            System.out.println("Неправильно введена арифметическая задача!");
            System.exit(0);
        }

        // Проверяем цифры римские или арабские
        String value1Type = isDecimalOrRoman(value1);
        String value2Type = isDecimalOrRoman(value2);

        if ((value1Type == "decimal" && value2Type == "roman") || (value1Type == "roman" && value2Type == "decimal")) {
            throw new Exception("Калькулятор умеет работать только с арабскими или римскими цифрами одновременно");
        }

        // Преобразуем строки в тип int и переводим в десятичную систему при необходимости
        if (value1Type == "decimal") {
            one = Integer.parseInt(value1);
            if (one > 10) throw new Exception("Калькулятор может работать только с цифрами от 1 до 10");
        } else {
            one = getDecFromRom(value1);
            if ((one == 0)||(one > 10)) throw new Exception("Калькулятор может работать только с цифрами от 1 до 10");
        }

        if (value2Type == "decimal") {
            two = Integer.parseInt(value2);
            if (two > 10) throw new Exception("Калькулятор может работать только с цифрами от 1 до 10");
        } else {
            two = getDecFromRom(value2);
            if ((two == 0)||two > 10) throw new Exception("Калькулятор может работать только с цифрами от 1 до 10");
        }

        // Арифметические дейстаия выполняет метод action. Передаем все параметры, метод возвращает результат
        int result = action(one, two, action);
        if (value1Type == "roman" && value2Type == "roman") outputType = "roman";
        if (outputType == "roman" && result < 1) {
            throw new Exception("Римскими числами могут быть только положительные числа");
        }

        if (outputType == "roman") return getRomFromDec(result);
        else return Integer.toString(result);

    }

    // Метод, выполняющий все арифметические действия
    static int action(int one, int two, String action) throws Exception {
        switch (action) {
            case ("+"): return one+two;
            case ("-"): return one-two;
            case ("*"): return one*two;
            case ("/"): {
                if(two==0) throw new Exception("На ноль делить нельзя!");
                return one/two;
            }
            default: throw new Exception("Произошла ошибка во время выполнения арифметического действия");
        }
    }

    // Метод проверяет является число римским или арабским
    static String isDecimalOrRoman (String number) {
        if(number.matches("^[0-9]+$")) return "decimal";
        else return "roman";
    }

    // Метод преобразует римские цифры в арабские (от 1 до 10)
    static int getDecFromRom (String number) {
        switch (number) {
            case ("I"): return 1;
            case ("II"): return 2;
            case ("III"): return 3;
            case ("IV"): return 4;
            case ("V"): return 5;
            case ("VI"): return 6;
            case ("VII"): return 7;
            case ("VIII"): return 8;
            case ("IX"): return 9;
            case ("X"): return 10;
            default: return 0;
        }
    }

    // Метод преобразует арабские цифры в римские (от 1 до 100)
    static String getRomFromDec (int number) {
        String result = "";
        while (number!=0) {
            if(number==100) {
                number-=100;
                result=result + "C";
            } else if(number>=90 && number<100) {
                number -= 90;
                result = result + "XC";
            } else if(number>=50 && number<90) {
                number-=50;
                result=result + "L";
            } else if(number>=40 && number<50) {
                number-=40;
                result=result + "XL";
            } else if(number>=10 && number<40) {
                number-=10;
                result=result + "X";
            } else if(number==9) {
                number-=9;
                result=result + "IX";
            } else if(number>=5 && number<9) {
                number-=5;
                result=result + "V";
            } else if(number==4) {
                number-=4;
                result=result + "IV";
            } else {
                number -= 1;
                result = result + "I";
            }
        }
        return result;
    }

}