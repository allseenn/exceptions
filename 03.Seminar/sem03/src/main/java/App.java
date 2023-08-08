import java.io.*;
import java.nio.file.Files;

public class App {
    public static void main(String[] args) {
        try (FileReader reader = new FileReader("file1.txt");
             FileWriter writer = new FileWriter("file2.txt"))
        {
            while (reader.ready()){
                writer.write(reader.read());
            }
        } catch (RuntimeException | IOException e) {
            System.out.println("catch caption: " + e.getClass().getSimpleName());
        }
        System.out.println("Copy successfully");
    }
    public void rwLine(Path pathRead, Path pathWrite) throws IOException{
        BufferedReader in = null;
        BufferedWriter out = null;
        try{
                in = Files.newBufferedReader(pathRead);
                out = Files.newBufferedWriter((athWrite);
        }
        finally {
            try{
                if(in!=null) in.close();

            }catch (IOException e){
        }
            try {
                if(out != null) out.close();
            } catch (IOException e){

            }
            }
    }

    public void rwLine(Path pathRead, Path pathWrite) throws IOException {
// Используем try-with-resources для автоматического закрытия потоков
        try (BufferedReader in = Files.newBufferedReader(pathRead);
             BufferedWriter out = Files.newBufferedWriter(pathWrite)) {
// Здесь можно читать и писать данные
        } catch (IOException e) {
// Здесь можно обработать исключение
        }


    }

