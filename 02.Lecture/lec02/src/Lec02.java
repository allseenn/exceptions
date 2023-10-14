public class Lec02 {
    public static void main(String[] args) throws Exception {
        // ArithmeticException
        int a = 0;
        int b = 10;
        int c = b / a;

        //ArrayIndexOutOfВoundsException
        int[] array = new int[10];
        array[100] = 5;

        // ClassCastException
        Animal animal = new Cat();
        Dog dog = (Dog)animal;

        // NullPointerException
        String str = null;
        System.out.println(str.length());

        // NumberFormatException
        int value = Integer.parseInt("100a0");
    }
}
