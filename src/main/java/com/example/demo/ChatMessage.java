package com.example.demo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ChatMessage {
    public enum MessageType{
        ENTER, TALK
    }

    private MessageType type;
    private String roomid;
    private String sender;
    private String message;
}
