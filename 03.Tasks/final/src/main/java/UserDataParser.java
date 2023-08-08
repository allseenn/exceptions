import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

class InvalidDataException extends Exception {
    public InvalidDataException(String message) {
        super(message);
    }
}

class InvalidGenderException extends InvalidDataException {
    public InvalidGenderException(String message) {
        super(message);
    }
}

class InvalidPhoneException extends InvalidDataException {
    public InvalidPhoneException(String message) {
        super(message);
    }
}

class UserData {
    String lastName;
    String firstName;
    String middleName;
    Date birthDate;
    long phoneNumber;
    char gender;

    public UserData(String lastName, String firstName, String middleName, Date birthDate, long phoneNumber, char gender) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }

    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        return lastName + " " + firstName + " " + middleName + " "
                + sdf.format(birthDate) + " " + phoneNumber + " " + gender;
    }
}

public class UserDataParser {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Шаблон ввода с пробелами: Иванов Иван Иванович 01.01.1970 79161234567 m");
        while (true) {
            System.out.printf("Пустая строка для выхода: ");
            String input = scanner.nextLine();

            if (input.isEmpty()) {
                System.out.println("До новых встреч!");
                break;
            }

            int validationCode = validateData(input);
            if (validationCode == -1) {
                System.err.println("Ошибка: введено меньше данных.");
            } else if (validationCode == 1) {
                System.err.println("Ошибка: введено больше данных.");
            } else {
                try {
                    UserData userData = parseUserData(input);
                    writeUserDataToFile(userData);
                    System.out.println("Данные успешно сохранены в файл " + userData.lastName + ".txt");
                } catch (InvalidDataException e) {
                    System.err.println("Ошибка: " + e.getMessage());
                } catch (IOException e) {
                    System.err.println("Ошибка при записи в файл: ");
                    e.printStackTrace();
                }
            }
        }
    }

    private static int validateData(String input) {
        String[] data = input.split("\\s+");
        if (data.length < 6) {
            return -1; 
        } else if (data.length > 6) {
            return 1;  
        } else {
            return 0;  
        }
    }

    private static UserData parseUserData(String input) throws InvalidDataException {
        String[] data = input.split("\\s+");
        String lastName = data[0];
        String firstName = data[1];
        String middleName = data[2];
        Date birthDate = parseDate(data[3]);
        long phoneNumber = parsePhoneNumber(data[4]);
        char gender = parseGender(data[5]);

        return new UserData(lastName, firstName, middleName, birthDate, phoneNumber, gender);
    }

    private static Date parseDate(String date) throws InvalidDataException {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            sdf.setLenient(false);
            return sdf.parse(date);
        } catch (ParseException e) {
            throw new InvalidDataException("Неверный формат даты рождения.");
        }
    }

    private static long parsePhoneNumber(String phoneNumber) throws InvalidPhoneException {
        try {
            return Long.parseLong(phoneNumber);
        } catch (NumberFormatException e) {
            throw new InvalidPhoneException("Неверный формат номера телефона.");
        }
    }

    private static char parseGender(String gender) throws InvalidGenderException {
        if (gender.equalsIgnoreCase("f") || gender.equalsIgnoreCase("ж")) {
            return 'f';
        } else if (gender.equalsIgnoreCase("m") || gender.equalsIgnoreCase("м")) {
            return 'm';
        } else {
            throw new InvalidGenderException("Неверный формат пола.");
        }
    }

    private static void writeUserDataToFile(UserData userData) throws IOException {
        String fileName = userData.lastName + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(userData.toString() + "\n");
        }
    }
}
