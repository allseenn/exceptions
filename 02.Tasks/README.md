## Задание 2

1. Реализуйте метод, который запрашивает у пользователя ввод дробного числа (типа float), и возвращает введенное значение. Ввод текста вместо числа не должно приводить к падению приложения, вместо этого, необходимо повторно запросить у пользователя ввод данных.
2. Если необходимо, исправьте данный код (задание 2 https://docs.google.com/document/d/17EaA1lDxzD5YigQ5OAal60fOFKVoCbEJqooB9XfhT7w/edit)
3. Дан следующий код, исправьте его там, где требуется (задание 3 https://docs.google.com/document/d/17EaA1lDxzD5YigQ5OAal60fOFKVoCbEJqooB9XfhT7w/edit)
4. Разработайте программу, которая выбросит Exception, когда пользователь вводит пустую строку. Пользователю должно показаться сообщение, что пустые строки вводить нельзя.

## Решение

Рабочий код с комментариями к нему для **всех 4 задач** расположен в [исходном файле](https://github.com/allseenn/exceptions/blob/main/02.Tasks/Task02/src/main/java/App.java)

На всякий случай приведу решение для 2 и 3 задач:

#### Задача 2

Ошибка в типе данных у переменной catchedRes1 - необходимо выставить int, т.е. иначе будет возвращаться тип NaN
```
try {
   int d = 0;
   int catchedRes1 = intArray[8] / d;
   System.out.println("catchedRes1 = " + catchedRes1);
} catch (ArithmeticException e) {
   System.out.println("Catching exception: " + e);
}
```

#### Задача 3

1. Throwable вляется суперклассом для всех исключений, после него исполнятся ничего не будет, необходимо его разместить последним

2. В методе printSum() throws FileNotFoundException лишнее, т.к. мы не обращаемся к файлам

```
public static void main(String[] args) throws Exception {
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
   } catch (Throwable ex) {
       System.out.println("Что-то пошло не так...");
   } 
}
public static void printSum(Integer a, Integer b){
   System.out.println(a + b);
}

```