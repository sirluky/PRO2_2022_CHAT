package models.database;

import models.Message;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcDatabaseOperations implements DatabaseOperations {
    private final Connection connection;

    public JdbcDatabaseOperations(String driver, String url) throws SQLException, ClassNotFoundException {
        Class.forName(driver);
        connection = DriverManager.getConnection(url);
    }

    @Override
    public void addMessage(Message message) {
        String sql =
                "INSERT INTO ChatMessages(author, text, created)" +
                " VALUES (?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, message.getAuthor());
            statement.setString(2, message.getText());
            statement.setTimestamp(3, Timestamp.valueOf(message.getCreated()));
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Message> getMessages() {

        String sql = "SELECT * FROM ChatMessages";

        ArrayList<Message> messages = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String author = resultSet.getString("author");
                String text = resultSet.getString("text");
                Timestamp created = resultSet.getTimestamp("created");
                System.out.println(author + " " + text + " " + created);
                messages.add(new Message(author, text));
            }
            statement.close();



        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }
}
