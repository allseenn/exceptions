# Исключения и их обработка

## Типы исключений
<table>
<tr><th>Тип исключения</th><th>Описание</th></tr>
<tr><td>ArithmeticException</td><td>Арифметическая ошибка</td></tr>
<tr><td>ArrayIndexOutOfВoundsException</td><td>Выход индекса за пределы массива</td></tr>
<tr><td>ClassCastException</td><td>Неверное приведение типов</td></tr>
<tr><td>IllegalArgumentException</td><td>Употребление недопустимого аргумента при вызове метода</td></tr>
<tr><td>IndexOutOfВoundsException</td><td>Выход индекса некоторого типа за допустимые пределы</td></tr>
<tr><td>NullPointerException</td><td>Обращение к несуществующему объекту</td></tr>
<tr><td>NumberFormatException</td><td>Неверное преобразование символьной строки в числовой формат</td></tr>
<tr><td>IOException</td><td>Ошибка при работе с потоками ввода/вывода</td></tr>
<tr><td>FileNotFoundException</td><td>Файл не найден</td></tr>
<tr><td>ClassNotFoundException</td><td>Класс не найден</td></tr>
<tr><td>UnsupportedOperationException</td><td>Попытка выполнения нереализованного метода</td></tr>
</table>

```
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
```

## Иерархия исключений

<img src=pics/exeptions.png>

- **Throwable** - указывает на возможность бросания исключений.
- **Exception** - с помощью него можно создавать новые исключения. Этот класс и его подклассы можно перехватывать, что позволяет продолжить работу приложения в штатном режиме
- **Error** - указывает на ошибку в работе JVM, что в любом случае приводи к завершению приложения. Например ошибка OutOfMemoryError указывает на нехватку памяти.

Все исключения (класс Exception) делятся на две группы:
1. Checked: класс Exception и его подклассы — исключения, которые вы обязаны обработать в собственном коде. Если этого не сделать, возникнет ошибка на этапе компиляции.
2. Unchecked: класс RuntimeException и его подклассы — исключения, охватывающие такие ситуации, как деление на ноль или ошибочная индексация массивов. Их можно обрабатывать, если есть вероятность возникновения. А можно и не обрабатывать, поскольку предполагается, что при правильном поведении программы такие исключения вовсе не должны возникать. Действительно, если массив состоит из 8 элементов, код не должен обращаться к десятому. Или при любом целочисленном делении надо проверять делитель.

## Обработка исключений

С Error разработчик ничего не должен делать, а Exception, нужны, чтобы
программист мог контролировать их появление. Для этого есть понятие «Обработка исключений». Обработка исключений производится одним из двух способов:

1. Поместить код, бросающий исключение, в блок try-catch.
2. Пробросить исключение методу на уровень выше, то есть методу, который вызывает текущий метод. Для этого используется ключевое слово throws.


### try-catch

Оператору try требуется хотя бы один оператор catch или finally

```
public static void main(String[] args) {
    try {
        int a = 0;
        int b = 10 / a;
        System.out.println("Это сообщение не будет выведено
        в консоль");
    } catch (ArithmeticException e) {
        System.out.println("Деление на ноль");
    }
    System.out.println("Завершение работы");
}
```

В классе Throwable определён метод printStackTrace(), который выводит полную информацию об исключении в консоль, что бывает полезным на этапе отладки программы
```
public static void main(String args[]) {
    System.out.println("Начало");
    try {
        int а = 0;
        int b = 42 / а;
    } catch (ArithmeticException e) {
        e.printStackTrace();
    }
    System.out.println("Конец");
}
```

Выведется полная информация об исключении, и программа продолжила свою работу.

#### Нескольких операторов catch

```
public static void main(String args[]) {
    try {
        int a = 10;
        a -= 10;
        int b = 42 / a;
        int[] с = {1, 2, 3};
        с[42] = 99;
    } catch (ArithmeticException e) {
        System.out.println("Деление на ноль: " + e);
    } catch (ArrayIndexOutOfBoundsException e){
        System.out.println("Ошибка индексации массива: " +e);
    }
    System.out.println("Пocлe блока операторов try/catch");
}
```

Применяя несколько операторов catch, помните, что перехват исключений из подклассов должен следовать до перехвата исключений из суперклассов. Оператор catch, перехватывая исключение из суперкласса, будет перехватывать все исключения этого суперкласса, а также все его подклассы. Это означает, что исключения из подкласса вообще не будут обработаны, если попытаться перехватить их после исключений из его суперкласса. Недостижимый код считается Java-ошибкой.

```
public static void main(String args[]) {
    try {
        int а = 0;
        int b = 42 / а;
    } catch (Exception е) {
        System.out.println("Exception");
    } catch (ArithmeticException е) { // ошибка компиляции: недостижимый код !
        System.out.println("Этот код недостижим");
    }
}
```

Класс исключения типа ArithmeticException считается производным от класса Exception, и поэтому первый оператор catch обработает все ошибки, относящиеся к классу Exception, включая и класс ArithmeticException.

### throws

Если метод способен вызвать checked-исключение, которое сам не обрабатывает или просто не может корректно обработать, он
должен задать своё поведение таким образом, чтобы вызывающий его код мог обезопасить себя от такого исключения. Пробросив исключение или список исключений через запятую с помощью оператора throws:
```
public static void main(String args[]) {
    try {
        createReport();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
public static void createReport() throws IOException {
    PrintWriter writer = new PrintWriter("report.txt");
    writer.close();
}
```

### finally

Оператор finally образует блок кода, который выполняется по завершении блока операторов try/catch, но перед следующим за ним кодом. Он выполняется независимо от того, было ли сгенерировано исключение или нет, было ли оно перехвачено блоком catch или нет. 
Есть возможность перехвата и обработки сразу нескольких исключений в одном и том же операторе catch посредством логического оператора ИЛИ.