import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;

/**
 * Класс соединения с базой данных и выполнения запросов.
 */
public class ConnectionToDB {

    private final String jdbcURL = "jdbc:postgresql://localhost:5432/Birthdays";
    private final String username = "postgres";
    private final String password = "postgres";

    /**
     * Функция получения данных о всех людях из базы данных
     * @return список объектов Human
     */
    public ArrayList<Human> getAllPeople() {
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);

            String query = "SELECT * FROM birthday";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            ArrayList<Human> people = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                LocalDate date = resultSet.getDate("birth_date").toLocalDate();

                Human human = new Human(id, name, date);
                people.add(human);
            }
            connection.close();
            return people;

        } catch (SQLException e) {
            System.out.println("Error in connection to DB");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Функция добавления нового человека в базу данных
     * @param human - объект, содержащий данные о новом человеке
     * @return результат успешного или неуспешного выполнения операции добавления
     */
    public boolean addNewHuman(Human human) {
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);

            PreparedStatement st = connection.prepareStatement(
                    "INSERT INTO birthday(name, birth_date) VALUES (?, ?)"
            );
            st.setString(1, human.getName());
            st.setDate(2, Date.valueOf(human.getBirthday()));
            int result = st.executeUpdate();
            st.close();
            connection.close();
            return result != 0;

        } catch (SQLException e) {
            System.out.println("Error in connection to DB");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Функция удаления человека из базы данных
     * @param id - идентификатор человека, которого необходимо удалить
     * @return результат успешного или неуспешного выполнения операции удаления
     */
    public boolean deleteHuman(int id) {
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);

            PreparedStatement st = connection.prepareStatement(
                    "DELETE FROM birthday WHERE id = ?"
            );
            st.setInt(1, id);
            int result = st.executeUpdate();
            st.close();
            connection.close();
            return result != 0;

        } catch (SQLException e) {
            System.out.println("Error in connection to DB");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Функция редактирования данных о человеке
     * @param id - идентификатор человека, данные о котором необходимо редактировать
     * @param newHuman - новые данные о человеке
     * @return результат успешного или неуспешного выполнения операции редактирования
     */
    public boolean editHuman(int id, Human newHuman) {
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            PreparedStatement st = connection.prepareStatement(
                    "UPDATE birthday SET name = ?, birth_date = ?  WHERE id = ?  "
            );
            st.setString(1, newHuman.getName());
            st.setDate(2, Date.valueOf(newHuman.getBirthday()));
            st.setInt(3, id);
            int result = st.executeUpdate();
            st.close();
            connection.close();
            return result != 0;

        } catch (SQLException e) {
            System.out.println("Error in connection to DB");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Функция получения данных о текущих и ближайших ДР
     * @return список людей с текущим или ближайшим ДР
     */
    public ArrayList<Human> getCurrentBirthdays() {
        ArrayList<Human> allPeople = getAllPeople();
        ArrayList<Human> resultPeople = new ArrayList<>();

        int todayNumber = LocalDate.now().getDayOfYear();
        int weekAfterTodayNumber = LocalDate.now().plusWeeks(1).getDayOfYear();
        int startOfLastWeekOfTheYearNumber = 358;

        for(Human currentHuman: allPeople) {
            int currentDateNumber = currentHuman.getBirthday().withYear(LocalDate.now().getYear()).getDayOfYear();
            if (currentDateNumber > startOfLastWeekOfTheYearNumber) {
                if (currentDateNumber >= todayNumber || currentDateNumber <= weekAfterTodayNumber) {
                    resultPeople.add(currentHuman);
                }
            } else {
                if (currentDateNumber >= todayNumber && currentDateNumber <= weekAfterTodayNumber) {
                    resultPeople.add(currentHuman);
                }
            }
        }
        return resultPeople;
    }

}
