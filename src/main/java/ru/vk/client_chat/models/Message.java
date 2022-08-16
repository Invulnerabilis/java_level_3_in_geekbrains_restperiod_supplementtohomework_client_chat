/*
package ru.vk.client_chat.models;

import lombok.Data;
import ru.vk.client_chat.enumeration.Commands;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Message implements Serializable {
    private LocalDateTime localDateTime;
    private String username;
    private Commands commands;
    private String message;

    public Message(String message) {
        localDateTime = LocalDateTime.now();
        String[] parts = message.split("\\s+");
        commands = Commands.valueOf(parts[0]);
        if (parts.length > 1) {
            this.message = parts[1];
        }
    }
}
*/
