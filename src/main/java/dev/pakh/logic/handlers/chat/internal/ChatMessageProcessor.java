package dev.pakh.logic.handlers.chat.internal;

import dev.pakh.logic.signatures.ChatSignaturesManager;
import dev.pakh.models.chat.ChatMessageLine;
import dev.pakh.utils.SoundUtils;

import java.util.List;

// TODO: No lure message
// TODO: Check monster message
public class ChatMessageProcessor {
    private final boolean debugMode = true;
    private final Runnable restartFishing;
    private final Runnable startFishing;

    public ChatMessageProcessor(Runnable restartFishing, Runnable startFishing) {
        this.restartFishing = restartFishing;
        this.startFishing = startFishing;
    }

    public void processMessages(List<ChatMessageLine> chatMessageLines) {
        for (ChatMessageLine line : chatMessageLines)
            handleMessageActions(line);
    }

    private void handleMessageActions(ChatMessageLine line) {
        if (isPrivateMessage(line)) {
            if (debugMode) System.out.println("** Private message detected **");
            SoundUtils.message();
            return;
        }

        if (matchesMonsterSignature(line)) {
            if (debugMode) System.out.println("** Monster detected **");
            // TODO: Call monster handler
            return;
        }

        if (matchesFishingEndedSignature(line)) {
            if (debugMode) System.out.println("** Fishing end detected **");
            restartFishing.run();
            return;
        }

        if (matchesFishingBiteSignature(line)) {
            if (debugMode) System.out.println("** Fishing bite detected **");
            startFishing.run();
            return;
        }

        if (matchesObtainedOldBoxSignature(line)) {
            if (debugMode) System.out.println("** Old box detected **");
            SoundUtils.success();
        }
    }

    private boolean matchesFishingBiteSignature(ChatMessageLine line) {
        return ChatSignaturesManager.find("FishingBite").isEqual(line);
    }

    private boolean isPrivateMessage(ChatMessageLine line) {
        return line.color() != null && "Purple".equals(line.color().getName());
    }

    private boolean matchesMonsterSignature(ChatMessageLine line) {
        return ChatSignaturesManager.find("Monster").isEqual(line);
    }

    private boolean matchesFishingEndedSignature(ChatMessageLine line) {
        return ChatSignaturesManager.find("FishingEnded").startsWith(line);
    }

    private boolean matchesObtainedOldBoxSignature(ChatMessageLine line) {
        return ChatSignaturesManager.find("ObtainedOldBox").isEqual(line);
    }
}
