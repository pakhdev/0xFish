package dev.pakh.logic.handlers.chat;

import dev.pakh.logic.FishingWorkflow;
import dev.pakh.logic.handlers.chat.internal.ChatImageReader;
import dev.pakh.logic.handlers.chat.internal.ChatMessageProcessor;
import dev.pakh.models.capture.CaptureProcessor;
import dev.pakh.models.chat.ChatMessageLine;
import dev.pakh.models.chat.ChatSnapshot;
import dev.pakh.models.game.GameLayout;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ChatHandler extends CaptureProcessor {
    private final boolean debugMode = true;
    private final ChatImageReader chatImageReader;
    private final ChatMessageProcessor chatMessageProcessor;
    private ChatSnapshot lastChatSnapshot = null;
    private final AtomicBoolean isProcessing = new AtomicBoolean(false);

    public ChatHandler(GameLayout gameLayout, FishingWorkflow fishingWorkflow) {
        this.chatImageReader = new ChatImageReader(gameLayout);
        this.chatMessageProcessor = new ChatMessageProcessor(fishingWorkflow);
        this.ticksSinceLastRun = getTimeoutTicks();
    }

    @Override
    public void process(BufferedImage image) throws InterruptedException {
        if (!isProcessing.compareAndSet(false, true)) {
            if (debugMode)
                System.out.println("[ChatHandler] process() IGNORED");
            return;
        }

        try {
            ChatSnapshot newChatSnapshot = chatImageReader.readChatSnapshot(image);
            if (lastChatSnapshot == null) {
                lastChatSnapshot = newChatSnapshot;
                if (debugMode)
                    System.out.println("# First read initialized with: \n" + newChatSnapshot);
                return;
            }
            List<ChatMessageLine> newMessages = lastChatSnapshot.getNewMessages(newChatSnapshot);
            lastChatSnapshot = newChatSnapshot;
            chatMessageProcessor.processMessages(newMessages);
        } finally {
            isProcessing.set(false);
        }
    }
}
