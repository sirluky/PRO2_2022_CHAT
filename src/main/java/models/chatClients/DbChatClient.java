package models.chatClients;

import models.Message;
import models.chatClients.fileOperations.ChatFileOperations;
import models.database.DatabaseOperations;

import javax.xml.crypto.Data;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class DbChatClient implements ChatClient {
    private String loggedUser;
    private List<String> loggedUsers;
    private List<Message> messages;
    private List<ActionListener> loggedUserListeners = new ArrayList<>();
    private List<ActionListener> messageListeners = new ArrayList<>();
    private final DatabaseOperations chatFileOperations;

    public DbChatClient(DatabaseOperations chatFileOperations) {
        loggedUsers = new ArrayList<>();
        messages = chatFileOperations.getMessages();
        this.chatFileOperations = chatFileOperations;
    }

    @Override
    public void sendMessage(String text) {
        if (!isAuthenticated())
            return;
        Message message = new Message(loggedUser, text);
        messages.add(message);
        System.out.printf("%s sent message \"%s\"%n", loggedUser, text);
        raiseMessagesChangedEvent(message);
    }

    @Override
    public void login(String username) {
        loggedUser = username;
        loggedUsers.add(loggedUser);
        Message message = new Message(Message.USER_LOGGED_IN, username);
        messages.add(message);
        raiseLoggedUsersChangedEvent();
        raiseMessagesChangedEvent(message);
    }

    @Override
    public void logout() {
        Message message = new Message(Message.USER_LOGGED_OUT, loggedUser);
        messages.add(message);
        raiseMessagesChangedEvent(message);
        loggedUsers.remove(loggedUser);
        loggedUser = null;
        raiseLoggedUsersChangedEvent();
    }

    @Override
    public boolean isAuthenticated() {
        return loggedUser != null;
    }

    @Override
    public List<String> getLoggedUsers() {
        return loggedUsers;
    }

    @Override
    public List<Message> getMessages() {
        return messages;
    }

    @Override
    public void addLoggedUsersListened(ActionListener listener) {
        loggedUserListeners.add(listener);
    }

    @Override
    public void addMessageListened(ActionListener listener) {
        messageListeners.add(listener);
    }

    private void raiseLoggedUsersChangedEvent() {
        for (ActionListener listener: loggedUserListeners) {
            listener.actionPerformed(new ActionEvent(this, 1, "usersChanged"));
        }
    }

    private void raiseMessagesChangedEvent(Message message) {
        chatFileOperations.addMessage(message);
        for (ActionListener listener: messageListeners) {
            listener.actionPerformed(new ActionEvent(this, 1, "messagesChanged"));
        }
    }
}
