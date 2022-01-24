import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Класс приложения.
 */
public class CongratulationsApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n==========================================");
            System.out.println("~СЕГОДНЯШНИЕ И БЛИЖАЙШИЕ ДР~");
            getCurrentBirthdays();
            System.out.println("~МЕНЮ~");
            System.out.println("1 - Сегодняшние и ближайшие Дни рождения");
            System.out.println("2 - Все Дни рождения");
            System.out.println("3 - Добавить запись");
            System.out.println("4 - Удалить запись");
            System.out.println("5 - Редактировать запись");
            System.out.println("6 - Выход");
            System.out.println("==========================================\n");
            System.out.print("Введите пункт меню:");

            int userAction = scanner.nextInt();

            switch (userAction) {
                case(1):
                    getCurrentBirthdays();
                    break;
                case(2):
                    getAllBirthdays();
                    break;
                case(3):
                    addBirthday();
                    break;
                case(4):
                    deleteBirthday();
                    break;
                case(5):
                    editBirthdays();
                    break;
                case(6):
                    return;
                default:
                    break;
            }
        }
    }

    /**
     * Функция получения данных о текущих и ближайших ДР
     */
    private static void getCurrentBirthdays() {
        ConnectionToDB connectionToDB = new ConnectionToDB();
        ArrayList<Human> allPeople = connectionToDB.getCurrentBirthdays();

        if (!allPeople.isEmpty()) {
            for (Human currentHuman : allPeople) {
                currentHuman.print();
                System.out.println("------------------------------");
            }
        } else {
            System.out.println("Данные о днях рождениях отсутствуют");
        }
    }

    /**
     * Функция получения данных о всех ДР
     */
    private static void getAllBirthdays() {

        ConnectionToDB connectionToDB = new ConnectionToDB();
        ArrayList<Human> allPeople = connectionToDB.getAllPeople();

        if (!allPeople.isEmpty()) {
            for (Human currentHuman : allPeople) {
                currentHuman.print();
                System.out.println("------------------------------");
            }
        } else {
            System.out.println("Данные о днях рождениях отсутствуют");
        }

    }

    /**
     * Функция дабавления новой записи о человеке в базу данных
     */
    private static void addBirthday() {
        Human human = inputHuman();
        ConnectionToDB connectionToDB = new ConnectionToDB();
        boolean isSuccessfully = connectionToDB.addNewHuman(human);
        if (isSuccessfully) {
            System.out.println("Новая запись добавлена");
        } else {
            System.out.println("Ошибка при добавлении записи");
        }
    }

    /**
     * Функция удаления данных о человеке из базы данных
     */
    private static void deleteBirthday() {
        System.out.println("Введите номер человека, информацию о котором надо удалить:");
        int id = inputId();
        ConnectionToDB connectionToDB = new ConnectionToDB();
        boolean isSuccessfully = connectionToDB.deleteHuman(id);
        if (isSuccessfully) {
            System.out.println("Запись удалена");
        } else {
            System.out.println("Такой человек не найден");
        }
    }

    /**
     * Функция редактирования данных о человеке в базе данных
     */
    private static void editBirthdays() {
        System.out.println("Введите номер человека, информацию о котором надо редактировать:");
        int id = inputId();
        System.out.println("Введите новые данные человека:");
        Human newHuman = inputHuman();

        ConnectionToDB connectionToDB = new ConnectionToDB();
        boolean isSuccessfully = connectionToDB.editHuman(id, newHuman);
        if (isSuccessfully) {
            System.out.println("Запись отредактирована");
        } else {
            System.out.println("Такой человек не найден");
        }
    }

    /**
     * Функция ввода данных о человеке
     * @return объект класса Human с введёнными данными
     */
    private static Human inputHuman() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите имя:");
        String name = scanner.nextLine();
        System.out.println("Введите год рождения:");
        int year = scanner.nextInt();
        System.out.println("Введите месяц рождения:");
        int month = scanner.nextInt();
        System.out.println("Введите день рождения:");
        int day = scanner.nextInt();

        LocalDate birthday = LocalDate.of(year, month, day);
        return new Human(name, birthday);
    }

    /**
     * Функция ввода идентификатора
     * @return введённый идентификатор
     */
    private static int inputId() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }


}
