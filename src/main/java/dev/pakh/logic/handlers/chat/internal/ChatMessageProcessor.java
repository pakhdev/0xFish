package dev.pakh.logic.handlers.chat.internal;

import dev.pakh.logic.FishingWorkflow;
import dev.pakh.logic.signatures.ChatSignaturesManager;
import dev.pakh.models.chat.ChatMessageLine;
import dev.pakh.utils.SoundUtils;

import java.util.List;

public class ChatMessageProcessor {
    private final boolean debugMode = true;
    private final FishingWorkflow fishingWorkflow;

    public ChatMessageProcessor(FishingWorkflow fishingWorkflow) {
        this.fishingWorkflow = fishingWorkflow;
    }

    public void processMessages(List<ChatMessageLine> chatMessageLines) throws InterruptedException {
        for (ChatMessageLine line : chatMessageLines)
            handleMessageActions(line);
    }

    private void handleMessageActions(ChatMessageLine line) throws InterruptedException {
        if (isPrivateMessage(line)) {
            if (debugMode) System.out.println("** Private message detected **");
            SoundUtils.message();
            return;
        }

        if (matchesMonsterSignature(line)) {
            if (debugMode) System.out.println("** Monster detected **");
            fishingWorkflow.selectMonster();
            return;
        }

        if (matchesFishingEndedSignature(line)) {
            if (debugMode) System.out.println("** Fishing end detected **");
            fishingWorkflow.restart();
            return;
        }

        if (matchesFishingBiteSignature(line)) {
            if (debugMode) System.out.println("** Fishing bite detected **");
            fishingWorkflow.waitFishingStart();
            return;
        }

        if (matchesObtainedSomethingSignature(line)) {
            System.out.println("** Obtention of something detected **");
            if (matchesObtainedOldBoxSignature(line)) {
                if (debugMode) System.out.println("** Old box detected **");
                fishingWorkflow.incrementOldBoxCounter();
                SoundUtils.success();
            } else {
                if (debugMode) System.out.println("** Fish detected **");
                fishingWorkflow.incrementFishCounter();
            }
            return;
        }


        if (matchesNoBaitSignature(line)) {
            if (debugMode) System.out.println("** No bait detected **");
            fishingWorkflow.stop();
            return;
        }

        if (matchesCantFishHereSignature(line)) {
            if (debugMode) System.out.println("** Can't fish here detected **");
            fishingWorkflow.stop();
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

    private boolean matchesObtainedSomethingSignature(ChatMessageLine line) {
        return ChatSignaturesManager.find("ObtainedSomething").startsWith(line);
    }

    private boolean matchesNoBaitSignature(ChatMessageLine line) {
        return ChatSignaturesManager.find("NoBait").isEqual(line);
    }

    private boolean matchesCantFishHereSignature(ChatMessageLine line) {
        return ChatSignaturesManager.find("CantFishHere").isEqual(line);
    }
}
