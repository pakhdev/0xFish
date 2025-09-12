package dev.pakh.models.chat;

import java.util.ArrayList;
import java.util.List;

public class ChatSnapshot {
    private List<ChatMessageLine> messageLines = new ArrayList<>();

    public void addMessage(ChatMessageLine chatMessageLine) {
        messageLines.add(chatMessageLine);
    }

    public List<ChatMessageLine> getNewMessages(ChatSnapshot newSnapshot) {
        List<ChatMessageLine> oldMessages = this.messageLines;
        List<ChatMessageLine> newMessages = newSnapshot.messageLines;
        int messagesCount = newMessages.size();

        for (int shift = 0; shift < messagesCount - 1; shift++) {
            for (int i = 0; i < messagesCount - shift; i++) {
                ChatMessageLine oldMessage = oldMessages.get(i);
                ChatMessageLine newMessage = newMessages.get(i + shift);
                if (!oldMessage.isEqual(newMessage)) {
                    break;
                }
                if (i == (messagesCount - shift - 1))
                    return newMessages.subList(0, shift);
            }
        }
        return newMessages;
    }

    @Override
    public String toString() {
        String messages = "";
        for (int i = 0; i < messageLines.size(); i++) {
            ChatMessageLine messageLine = messageLines.get(i);
            messages += "Line " + (i + 1) + ": " + messageLine.color() + "," + messageLine.encoding() + "\n";
        }
        return messages;
    }
}
