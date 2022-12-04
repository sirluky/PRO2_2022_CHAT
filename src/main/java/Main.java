import models.chatClients.ChatClient;
import models.chatClients.FileChatClient;
import models.chatClients.InMemoryChatClient;
import models.chatClients.api.ApiChatClient;
import models.chatClients.fileOperations.ChatFileOperations;
import models.chatClients.fileOperations.JsonChatFileOperations;
import models.database.DbInitializer;
import models.gui.MainFrame;

public class Main {
    public static void main(String[] args) {
//        initDb();

        ChatFileOperations chatFileOperations = new JsonChatFileOperations();
        ChatClient chatClient = new ApiChatClient();

        MainFrame window = new MainFrame(700, 500, chatClient);
        //test();
    }

    private static void initDb() {
        String databaseDriver = "org.apache.derby.jdbc.EmbeddedDriver";
        String databaseUrl = "jdbc:derby:ChatClient";

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