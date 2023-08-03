//Запишите в файл следующие строки:
//Анна=4
//Елена=5
//Марина=6
//Владимир=?
//Константин=?
//Иван=4
// Реализуйте метод, который считывает данные из файла и сохраняет в двумерный массив
// (либо HashMap, если студенты с ним знакомы).
// В отдельном методе нужно будет пройти по структуре данных, если сохранено значение ?,
// аменить его на соответствующее число.Если на каком-то месте встречается символ,
// отличный от числа или ?, бросить подходящее исключение.Записать в тот же файл данные с замененными символами ?.


package ru.gb;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {
        String path = "/media/Rostislav/64/github/exeptions/02.Seminar/Sem02/src/main/java/ru/gb/name.txt";
        writeToFile(modify(getNames(path)), path);
    }
    public static List<String[]> getNames(String path){
        List<String[]> names = new ArrayList<>();
        try{
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);

            String line;
            while ( (line = br.readLine()) != null){
                names.add(line.split("="));
            }
            return names;
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static List<String[]> modify(List<String[]> names){
        for (String[] row:
             names) {
            if (!isValid(row[1]) && (!row[1].equals("?"))){
                throw new RuntimeException("Некорректные данные" + row[0]);
            }
            row[1] = String.valueOf(row[0].length());
        }
        return names;
    }

    public static boolean isValid(String testString){
        try{
            Integer.parseInt(testString);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }
    public static void writeToFile(List<String[]> names, String path){
        PrintWriter fw = null;
        try {
            fw = new PrintWriter(new File(path));
            for (String[] row:
                 names) {
                fw.println(row[0]+"="+row[1]);
            }
        } catch (IOException e){
            System.out.println(e.getMessage());
        } finally{
            if(fw != null) fw.close();
        }
    }
}
