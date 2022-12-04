import models.chatClients.ChatClient;
import models.chatClients.DbChatClient;
import models.chatClients.FileChatClient;
import models.chatClients.InMemoryChatClient;
import models.chatClients.api.ApiChatClient;
import models.chatClients.fileOperations.ChatFileOperations;
import models.chatClients.fileOperations.JsonChatFileOperations;
import models.database.DatabaseOperations;
import models.database.DbInitializer;
import models.database.JdbcDatabaseOperations;
import models.gui.MainFrame;

import javax.xml.crypto.Data;
import java.io.File;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        String databaseDriver = "org.apache.derby.jdbc.EmbeddedDriver";
        String databaseUrl = "jdbc:derby:ChatClient";
        // initDb(databaseDriver, databaseUrl);

        DatabaseOperations chatDbOperations = new JdbcDatabaseOperations(databaseDriver, databaseUrl);
//        ChatFileOperations chatFileOperations = new JsonChatFileOperations();
        ChatClient chatClient = new DbChatClient(chatDbOperations);

        MainFrame window = new MainFrame(700, 500, chatClient);
        //test();
    }

    private static void initDb(String databaseDriver, String databaseUrl) {


        DbInitializer initializer = new DbInitializer(databaseDriver, databaseUrl);
        initializer.init();

    }

    private static void test() {
        ChatClient client = new InMemoryChatClient();

        client.login("debug man");

        client.sendMessage("Hello there");
        client.sendMessage("Just trying to send a message");

        client.logout();
    };
}