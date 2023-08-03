import java.util.Arrays;

public class Sem1 {
    public static void main(String[] args) {
//        int[] testArray = {1,2,3,4,5};
//        printMessage(task1(testArray,4,3));
//        printMessage(task1(testArray,7,3));
//        printMessage(task1(testArray,4,8));
//        printMessage(task1(null,4,3));
//        int[][] m = {{0,1},{1,1}};
//        System.out.println("Сумма элементов равна: "+sumOfElements(m));
        Integer[] arr = {1,2,0,3,0};
        checkArr(arr);
    }

    public static int task1(int[] array, int num, int value) {
        if (array == null) return -3;
        if (array.length < num) return -1;
        int result = -2;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) {
                result = i;
                break;
            }
        }
        return result;
    }

    public static void printMessage(int errorCode){
        switch (errorCode){
            case -3:
                System.out.println("Вместо массива пришёл null");
                break;
            case -1:
                System.out.println("Длина массива меньше заданного");
                break;
            case -2:
                System.out.println("Элемент не найден");
                break;
            default:
                System.out.println("Индекс элемента: "+errorCode);
        }
    }

    public static int sumOfElements(int[][] matrix){
        int height = matrix.length;
        int sum =0;
        for (int[] row:
             matrix) {
            if (row.length != height) {
                throw new RuntimeException(String.format("Матрица не квадратная, есть строка длины %d, " +
                        "всего строк %d", row.length, height));
            }
            for (int num:
                 row) {
                if (num !=0 && num != 1) {
                    throw new RuntimeException(String.format("В матрице есть число %d, отличное от 0 и 1", num));
                }
                sum += num;
            }
        }
        return sum;
    }

    public static void checkArr(Integer[] arr){
        StringBuilder nullIndexes = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == null){
                nullIndexes.append(i).append(" ");
            }
        }
        if (nullIndexes.length() != 0){
            throw new RuntimeException("Индексы null-ов: "+nullIndexes);
        }
        System.out.println("null-ов нет");
    }
}
