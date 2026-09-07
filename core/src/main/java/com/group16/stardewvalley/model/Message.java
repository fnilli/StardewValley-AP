package com.group16.stardewvalley.model;

import com.group16.stardewvalley.model.user.Player;

public class Message implements notification{
    private Player sender;
    private String message;
    public Message(Player sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    public String getMessage() {
        return sender.getName()  + " sent you " + message;
    }
}
