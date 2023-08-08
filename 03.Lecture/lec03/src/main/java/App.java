import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
}
