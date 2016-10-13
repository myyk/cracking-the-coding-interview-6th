package com.github.myyk.cracking.chapter7;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Explain how you'd design a chat server.
 *
 * This could go on forever. I could add user friending features. I could add login
 *   and log out. I think I answered private and group messaging adequately.
 */
public class ChatServer {
  public Map<Set<User>,Chat> chats;
  public ConnectionManager connectionManager;

  synchronized public Chat findOrCreateChat(Set<User> users) {
    if (!chats.containsKey(users)) {
      return chats.put(users, new Chat(users));
    } else {
      return chats.get(users);
    }
  }

  synchronized public Chat updateChatUsers(Set<User> oldUsers, Set<User> newUsers) {
    Chat chat = chats.remove(oldUsers);
    chat.users = newUsers;
    chats.put(newUsers, chat);
    return chat;
  }

  public class User {
    String name;
    Set<User> friends;

    public void sendMessage(Message message, Chat chat) {
      chat.addMessage(message);
    }
    public void deliver(Message message) {}
  }

  public class Message {
    int id;
    String text;
  }

  public class Chat {
    Set<User> users;
    List<Message> messages;

    public Chat(Set<User> users) {
      this.users = users;
      this.messages = new LinkedList<Message>();
    }

    public void addMessage(Message message) {
      messages.add(message);
      for (User user : users) {
        user.deliver(message);
      }
    }
  }

  public class ConnectionManager {
    Map<User, Connection> connections;
  }

  public interface Connection {
    public void send();
  }
}
