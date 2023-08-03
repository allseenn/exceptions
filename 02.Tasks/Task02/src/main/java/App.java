import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        // 1. метод inputFloat()
        System.out.println("1. Реализуйте метод, который запрашивает у пользователя " +
                "ввод дробного числа (типа float), и возвращает введенное значение.\n " +
                "Ввод текста вместо числа не должно приводить к падению приложения, " +
                "вместо этого, необходимо повторно запросить у пользователя ввод данных.");
        System.out.println("Вы ввели: " + inputFloat());
        // 2. Ошибка в типе данных
        System.out.println("2. Тип переменной catchedRes1, нужно поменять на int,\n" +
                "т.к деление дробного числа на ноль приводит к специальным значениям NaN");
        int [] intArray = new int[]{0,0,0,0,0,0,0,0,0};
        try {
            int d = 0;
            int catchedRes1 = intArray[8] / d; // меняем double на int catchedRes1
            System.out.println("catchedRes1 = " + catchedRes1);
        } catch (ArithmeticException e) {
            System.out.println("Catching exception: " + e);
        }
        // 3.  Порядок размещения Throwable и лишнее throws FileNotFoundException
        System.out.println("3. Throwable вляется суперклассом для всех исключений,\n" +
                "после него исполнятся ничего не будет, необходимо его разместить последним,\n" +
                "в методе printSum() throws FileNotFoundException лишнее, т.к. мы не обращаемся к файлам");
        try {
            int a = 90;
            int b = 3;
            System.out.println(a / b);
            printSum(23, 234);
            int[] abc = { 1, 2 };
            abc[3] = 9;
        } catch (NullPointerException ex) {
            System.out.println("Указатель не может указывать на null!");
        } catch (IndexOutOfBoundsException ex) {
            System.out.println("Массив выходит за пределы своего размера!");
        } catch (Throwable ex) { // Ставим последним
            System.out.println("Что-то пошло не так...");
        }
        // 4. Метод emptyString()
        System.out.println("4. Разработайте программу, которая выбросит Exception,\n" +
                "когда пользователь вводит пустую строку. Пользователю должно показаться сообщение,\n" +
                "что пустые строки вводить нельзя.");

        emptyString();
    }
    public static void printSum(Integer a, Integer b)  {
        System.out.println(a + b);
    }
    public static void emptyString(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите не пустую строку: ");
        String input = scanner.nextLine();
        if(input.isEmpty()){
            throw new RuntimeException("Пустые строки вводить нельзя!");
        }
        System.out.println("Вы ввели: " + input);
    }
    public static float inputFloat(){
        Scanner scanner = new Scanner(System.in);
        float number = 0;
        System.out.print("Введите дробное число: ");
        try{
            number = Float.parseFloat(scanner.next());
        } catch (NumberFormatException e){
            number = inputFloat();
        }
        return number;
    }

// Вариант функции поиска дробного с циклом
//    public static float inputFloat() {
//        Scanner scanner = new Scanner(System.in);
//        float number = 0;
//        boolean valid = false;
//        while (!valid) {
//            System.out.print("Введите дробное число: ");
//            if (scanner.hasNextFloat()) {
//                number = scanner.nextFloat();
//                valid = true;
//            } else {
//                System.out.println("Неверный формат ввода. Попробуйте еще раз.");
//                scanner.next();
//            }
//        }
//        return number;
//    }

}
