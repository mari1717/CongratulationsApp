import java.time.LocalDate;

/**
 * Класс человека со свойствами идентификатор, имя и дата рождения.
 */
public class Human {

    private long id;
    private final String name;
    private final LocalDate birthday;

    public Human(long id, String name, LocalDate birthday) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
    }
    public Human(String name, LocalDate birthday) {
        this.name = name;
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void print() {
        System.out.println("Номер: " + id);
        System.out.println("Имя: " + name);
        System.out.println("Дата рождения: " + birthday);
    }

}
