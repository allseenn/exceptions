# Продвинутая работа с исключениями

## try-with-resources

При использовании внешних для JVM ресурсов, таких как файлы, сетевые соединения, соединения с базами данных и прочие, требуется обязательно закрывать их в блоке finally, тогда JVM сама почистит используемую память и освободит все свои служебные
файлы:
```
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MainApp {
    public static void main(String args[]) {
        FileReader reader = null;
        try {
            reader = new FileReader(new File("file.txt"));
            // Полезная работа, связанная с чтением файла..
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        try {
            if (reader != null) {
                reader.close();
            }
        } catch(IOException e) {
            e.printStackTrace();
            }
        }
    }
}
```

Сократим код с помощью try-with-resources:

```
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MainApp {
    public static void main(String args[]) {
        try (FileReader reader = new FileReader(new File("file.txt"))) {
            // Полезная работа, связанная с чтением файла..
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

Рядом с try в круглых скобках указывается создаваемый ресурс, реализующий интерфейс AutoClosable, который при выходе из блока try должен быть освобождён, и не важно будет ли брошено какое-то исключение или нет. То есть вся работа по написанию блока finally выполняется автоматически без нашего участия.

```
public class MainApp {
    public static void main(String[] args) {
            try (Box box = new Box()) {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```
Получаем ошибку на этапе компиляции, достаточно применить к нашему объекту Box интерфейс AutoCloseable:

```
public class MainApp {
    public static void main(String[] args) {
    try (Box box = new Box()) {

    } catch (Exception e) {
        e.printStackTrace();
        }
    }
}
public class Box implements AutoCloseable {
    @Override
    public void close() throws Exception {
        // Закрываем коробку
    }
}
```

### Несколько ресурсов с try-with-resources

Объекты (ресурсов) отделяются точкой запятой:
```
public class MainApp {
    public static void main(String[] args) {
        try (Box box1 = new Box();
        Box box2 = new Box()) {
    } catch (Exception e) {
        e.printStackTrace();
        }
    }
}
```

При работе с внешними ресурсами в коде предпочтительно использовать try-with-resources

## throws - Обработка исключений выше по стеку

Checked-исключения обязательно обрабатываются либо посредством блока try-catch, либо через проброс на уровень выше.

Рассмотрим приложение, которое с помощью графического интерфейса сохраняет некий отчет в файл.
Код по сохранению отчёта по нажатию на кнопку Save выглядит так:

```
package com.geekbrains.app;
import com.xlsreport.maker.ReportExporter;
import javax.swing.*;
public class MainAppWindow extends JFrame {
    private ReportExporter reportExporter = new ReportExporter();
    public void onSaveReportButtonClick() {
        String path = generateOutputReportPath();
        String outputData = "Очень важные данные для отчета";
        reportExporter.saveReportToFile(path, outputData);
    }
    public String generateOutputReportPath() {
        return "D:/reports/repository/1/" + (int)(Math.random() * 10000000) + ".txt";
    }
    // ...
}
```

1. onSaveReportButtonClick() - метод, вызываемый при нажатии кнопки Save
2. generateOutputReportPath() - метод, генерирующий путь для сохранения файла
3. saveReportToFile() - метод из класса ReportExporter, написан другим разработчиком.

Класс ReportExporter для формирования и сохранения отчетов:

```
package com.xlsreport.maker;
import java.io.IOException;
import java.io.PrintWriter;

public class ReportExporter {
    public void saveReportToFile(String path, String data) {
        String modifiedOutputData = data; // Представим, что здесь форматируются входные данные
        try (PrintWriter writer = new PrintWriter(path))
        {
            writer.println(modifiedOutputData);
        } catch (IOException e) {
            // Просто погасили исключение
        }
    }
}
```

В блоке catch разработчик решил не мусорить сообщениями в консоль и просто ничего не сделал.

Если диск пользователя будет C:/, и при попытке выполнить сохранение в библиотеке возникнет исключение FileNotFoundException, которое не обрабатывается. Программа вылетит не уведомив о проблеме. 

Исправим класс ReportExporter:

```
package com.xlsreport.maker;
import java.io.IOException;
import java.io.PrintWriter;

public class ReportExporter {
    public void saveReportToFile(String path, String data) {
        String modifiedOutputData = data; // Представим, что здесь форматируются входные данные
        try (PrintWriter writer = new PrintWriter(path))
        {
            writer.println(modifiedOutputData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

В данном случае исключение увидит, только разработчик в консоли, пользователь в графическом интерфейсе ничего не увидит.

Т.к. мы не известно в каком окружении будет использоваться данная библиотека (в консоли, окне, веб-приложении), то нельзя просто вывести сообщение в графическое окно. Но, можно передать исключение выше по стеку с помощью throws:

```
package com.xlsreport.maker;
import java.io.IOException;
import java.io.PrintWriter;

public class ReportExporter {
    public void saveReportToFile(String path, String data) throws IOException{
        String modifiedOutputData = data; // Представим, что здесь форматируются входные данные
        try (PrintWriter writer = new PrintWriter(path))
        {
            writer.println(modifiedOutputData);
        } 
    }
}
```
В данном коде помимо проброса исключения (throws IOException) на уровень выше, вызывающему методу и классу. Необходимо отказаться от блока catch, чтобы он не перехватывал исключения.
Теперь в коде графического окна для сохранения файла строка reportExporter.saveReportToFile(path,
outputData), обращающаяся к классу reportExporter и методу saveReportToFile выдаст ошибку еще на этапе исключения, т.к. IOException является checked исключением. Теперь мы можем обработать ошибку в графическом приложении и показать сообщение об ошибке пользователю:

```
public class MainAppWindow extends JFrame {
    // ...
    public void onSaveReportButtonClick() {
        String path = generateOutputReportPath();
        String outputData = "Очень важные данные для отчета";
        ReportExporter reportExporter = new ReportExporter();
        try {
            reportExporter.saveReportToFile(path, outputData);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Ошибка! Невозможно сохранить отчет", "Ошибка!", JOptionPane.ERROR_MESSAGE);
        }
    }
// ...
}
```

**JOptionPane.showMessageDialog()** - покажет окно с сообщением об ошибке.

Можно одновременно обработать исключение и в классе ReportExporter и пробросив его выше в MainAppWindow обработать там:

```
package com.xlsreport.maker;
import java.io.IOException;
import java.io.PrintWriter;

public class ReportExporter {
    public void saveReportToFile(String path, String data) throws IOException{
        String modifiedOutputData = data; // Представим, что здесь форматируются входные данные
        try (PrintWriter writer = new PrintWriter(path))
        {
            writer.println(modifiedOutputData);
        } catch (IOException e) {
            // Что-то полезное делаем
            throw e;
        }
    }
}
```

С помощью throw e выбрасываем ошибку из блока catch

## Собственные типы исключений

Чтобы создать новое исключение, надо всего лишь создать новый класс и унаследовать его от одного из существующих типов исключений.

```
public class MyException extends RuntimeException {
}

public class MainApp {
    public static void main(String[] args) {
        throw new MyException();
    }
}
```

Если унаследовать класс от Checked-исключения, то и исключение будет Checked, в противном случае — Unchecked.

Пример собственного исключения о загрузке изображения в неподдерживаемом формате:

```
public class IllegalImageFormatException extends
RuntimeException {
}
```

Тут даже название типа исключения помогает разработчику понять, что пошло не так.

Еще пример когда в фильтр изображения передались некорректные параметры:

```
public class IllegalFilterParametersException extends
RuntimeException {
}
```

Более того, весь набор новых исключений можно сгруппировать в общий класс, например в выдуманный JavaCVException (Java Computer Vision):

```
public class JavaCVException extends RuntimeException {
}
public class IllegalImageFormatException extends
JavaCVException {
}
public class IllegalFilterParametersException extends
JavaCVException {
}
```

### Оборачивание исключений

Ещё один интересный подход при работе с собственными типами исключений — «оборачивание» одного исключения в другое. Например, мы реализуем метод размытия изображения, и где-то в логике очень редко вылетает исключение, которое мы не учли.

```
public class JavaCVLibFilters {
    public void blur(CvImage img, int kernelSize) {
    try {
        // Вычисления
        // Вычисления
        // Вычисления
    } catch (Exception e) {
        throw new IllegalFilterParametersException();
        }
    }
}
```

#### Собственные сообщения в исключениях 

Допустим, мы решили написать метод, преобразующий массив строк в массив целых чисел. 
Если из метода было выброшено ArrayIndexOutOfBoundsException или ArithmeticException, пользователю библиотеки было бы неясно, откуда именно взялись эти стандартные исключения и из-за чего.

```
import java.util.Arrays;

public class MainApp {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(transform(new
            String[]{"1", "2", "3", "4"})));
    }
    public static int[] transform(String[] input) {
        int[] output = new int[input.length];
        for (int i = 0; i < input.length; i++) {
            output[i] = Integer.parseInt(input[i]);
        }
        return output;
    }
}
```

Если попадётся строка, которую невозможно преобразовать к целому числу, получим исключение NumberFormatException.

Мы можем создать свое исключение хранящее информацию (сообщение) об индексе некорректного элемента:

```
public class ArrayTransformationException extends RuntimeException {
    private int illegalElementIndex;
    private String illegalElementValue;
    public int getIllegalElementIndex() {
        return illegalElementIndex;
    }
    public String getIllegalElementValue() {
        return illegalElementValue;
    }
    public ArrayTransformationException(int illegalElementIndex, String illegalElementValue) {
    super(String.format("Во входном массиве на позиции: %d находится некорректный элемент: %s", illegalElementIndex illegalElementValue));
        this.illegalElementIndex = illegalElementIndex;
        this.illegalElementValue = illegalElementValue;
    }
}
```

Пример использования вышенаписанного исключения в коде:

```
public class MainApp {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(transform(new String[]{"1", "2", "3", "4"})));
    }
    public static int[] transform(String[] input) {
        int[] output = new int[input.length];
        for (int i = 0; i < input.length; i++) {
            try {
            output[i] = Integer.parseInt(input[i]);
            } catch (NumberFormatException e) {
            throw new ArrayTransformationException(i, input[i]);
            }
        }
    return output;
    }
}
```

Если в процессе преобразования вылетит стандартный NumberFormatException, мы его перехватим и обернём в собственный тип исключения, передав в его конструктор детализацию об ошибке.
