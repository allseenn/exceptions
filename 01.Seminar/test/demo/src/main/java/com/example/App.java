package com.example;

public class App 
{

    public static void main( String[] args )
    {
        // int[] testArray = {1,2,3,4,5};
        // printMessage(task1(testArray, 4));
        // printMessage(task1(testArray, 4, 3));
        // printMessage(task1(testArray, 4, 3));
        // printMessage(task1(testArray, 4, 3));

        // int[][] m = {{0,1,0},{1,1}};
        // System.out.println(task2(m));

        // Integer[] arr = {1,2,null,3,null};
        // task3(arr);

        int[][] n = {{0,1,0},{1,1}};
        System.out.println(task4(n));
    }
     //Реализуйте метод, принимающий в качестве аргумента целочисленный массив. 
        // Если длина массива меньше некоторого заданного минимума, метод возвращает -1, в качестве кода ошибки, иначе - длину массива.

        public static int task0(int[] arr, int num) {
            if(arr.length < num) return -1;
            return arr.length;
        }
    

        // Реализуйте метод, принимающий в качестве аргумента целочисленный массив и некоторое значение. Метод ищет в массиве заданное значение и возвращает его индекс. При этом, например:
        // если длина массива меньше некоторого заданного минимума, метод возвращает -1, в качестве кода ошибки.
        // если искомый элемент не найден, метод вернет -2 в качестве кода ошибки.
        // если вместо массива пришел null, метод вернет -3
        // придумайте свои варианты исключительных ситуаций и верните соответствующие коды ошибок.
        // Напишите метод, в котором реализуйте взаимодействие с пользователем. То есть, этот метод запросит искомое число у пользователя, вызовет первый, обработает возвращенное значение и покажет читаемый результат пользователю. Например, если вернулся -2, пользователю выведется сообщение: “Искомый элемент не найден”.
        public static int task1(int[] arr, int num, int value) {
            if(arr == null) return -3;
            if(arr.length < num) return -1;
            int res = -2;
            for (int i = 0; i < arr.length; i++) {
                if(arr[i]==num){
                    res = i;
                    break;
                }
            }
            return res;
        }

        public static void printMessage(int errorCode){
            switch(errorCode){
                case -3:
                    System.out.println("Вместо массива пришел null");
                    break;
                case -1:
                    System.out.println("Длинна массива меньше заданного");
                    break;
                case -2:
                    System.out.println("Элемент не найден");
                    break;
                default:
                System.out.println(errorCode);
            }
        }
        // Реализуйте метод, принимающий в качестве аргумента целочисленный двумерный массив. 
        // Необходимо посчитать и вернуть сумму элементов этого массива. 
        // При этом накладываем на метод 2 ограничения: метод может работать только с квадратными массивами (кол-во строк = кол-ву столбцов), и в каждой ячейке может лежать только значение 0 или 1. 
        // Если нарушается одно из условий, метод должен бросить RuntimeException с сообщением об ошибке.
        public static int task2(int[][] matrix){
            int height = matrix.length;
            int sum = 0;
            for (int[] row : matrix) {
                if(row.length != height){
                    throw new RuntimeException(String.format("Матрица не квадрат строка длины %d"
                     + "всего строк %d", + row.length, height));
                }
                for (int[] num : row) {
                    if(num != 0 && num != 1){
                        throw new RuntimeException(String.format("В матрице есть число %d отличное от 0 и 1"
                        , num));
                    }
                    sum += num;
                }
                
            }
            return sum;
        }
        // Реализуйте метод checkArray(Integer[] arr), принимающий в качестве аргумента целочисленный одномерный массив. 
        // Метод должен пройтись по каждому элементу и проверить что он не равен null. 
        // А теперь реализуйте следующую логику:
        // Если в какой-то ячейке встретился null, то необходимо “оповестить” об этом пользователя
        // Если null’ы встретились в нескольких ячейках, то идеально было бы все их “подсветить”
        public static viod task3(Integer[] arr){
            StringBuilder nullIndexes = new StringBuilder();
            for (int i = 0; i < arr.length; i++) {
                if(arr[i] == null){
                    nullIndexes.append(i).append(" ");
                }
            }
            if (nullIndexes.length() != 0){
                throw new RuntimeException("индексы наллов "+nullIndexes)ж
            }
            System.out.println("наллов нет");
        }

        // Реализуйте метод, принимающий в качестве аргументов двумерный массив. 
        // Метод должен проверить что длина строк и столбцов с одинаковым индексом одинакова,
        // детализировать какие строки со столбцами не требуется. Как бы вы реализовали подобный метод.
        public static boolean task4(int[][] matrix){
            int[] num = getCountColumn(matrix);
            for (int i = 0; i < matrix.length; i++) {
                if(matrix[i].length != num[i]){
                    return false;
                }
            }
            return true;
        }
        public static int[] getCountColumn(int[][] arr){
            StringBuilder str = new StringBuilder();
            int max = 0;
            for (int[] row : arr) {
                if(row.length > max){
                    max = row.length;
                }
            }
            int[] maxElements = new int[max];
            for (int[] row : arr) {
                for (int i = 0; i < row.length; i++) {
                    maxElements[i]+=1;
                }
            }
            return maxElements;
        }
}


