package dev.pakh.logic.handlers.chat.internal;

import dev.pakh.logic.FishingWorkflow;
import dev.pakh.logic.ports.FishingStateListener;
import dev.pakh.logic.ports.FishingStatsListener;
import dev.pakh.logic.signatures.ChatSignaturesManager;
import dev.pakh.models.chat.ChatMessageLine;
import dev.pakh.utils.SoundUtils;

import java.util.List;

public class ChatMessageProcessor {
    private final FishingWorkflow fishingWorkflow;
    private EventType lastUsedSkill = null;

    private final FishingStatsListener fishingStatsListener;
    private final FishingStateListener fishingStateListener;

    public ChatMessageProcessor(FishingWorkflow fishingWorkflow) {
        this.fishingWorkflow = fishingWorkflow;
        this.fishingStatsListener = fishingWorkflow.fishingStatsListener;
        this.fishingStateListener = fishingWorkflow.fishingStateListener;
    }

    public void processMessages(List<ChatMessageLine> chatMessageLines) throws InterruptedException {
        boolean monsterDetectedInBatch = false;
        boolean fishingEndDetectedInBatch = false;

        for (int i = chatMessageLines.size() - 1; i >= 0; i--) {
            ChatMessageLine line = chatMessageLines.get(i);
            EventType event = identifyEvent(line);

            if (event == EventType.MONSTER && monsterDetectedInBatch)
                continue;

            if (event == EventType.FISHING_END && fishingEndDetectedInBatch)
                continue;

            if (event == EventType.SKILL_FAILED && lastUsedSkill == EventType.PUMPING)
                event = EventType.PUMPING_FAILED;
            if (event == EventType.SKILL_FAILED && lastUsedSkill == EventType.REELING)
                event = EventType.REELING_FAILED;

            executeEvent(event);

            if (event == EventType.MONSTER) {
                monsterDetectedInBatch = true;
            } else if (event == EventType.FISHING_END) {
                fishingEndDetectedInBatch = true;
            } else if (event == EventType.PUMPING) {
                lastUsedSkill = EventType.PUMPING;
            } else if (event == EventType.REELING) {
                lastUsedSkill = EventType.REELING;
            }
        }
    }

    private EventType identifyEvent(ChatMessageLine line) {
        if (matchesNoRodSignature(line)) return EventType.NO_ROD;
        if (matchesPumpingSignature(line)) return EventType.PUMPING;
        if (matchesIncorrectPumpingSignature(line)) return EventType.INCORRECT_PUMPING;
        if (matchesReelingSignature(line)) return EventType.REELING;
        if (matchesIncorrectReelingSignature(line)) return EventType.INCORRECT_PUMPING;
        if (matchesSkillFailedSignature(line)) return EventType.SKILL_FAILED;
        if (isPrivateMessage(line)) return EventType.PRIVATE_MESSAGE;
        if (matchesMonsterFirstLineSignature(line) || matchesMonsterSecondLineSignature(line)) return EventType.MONSTER;
        if (matchesFishingEndedSignature(line) || matchesCaughtSomethingSignature(line)) return EventType.FISHING_END;
        if (matchesFishingBiteSignature(line)) return EventType.FISHING_BITE;
        if (matchesObtainedSomethingSignature(line)) {
            if (matchesObtainedOldBoxSignature(line)) {
                return EventType.OBTAINED_OLD_BOX;
            }
            return EventType.OBTAINED_FISH;
        }
        if (matchesNoBaitSignature(line)) return EventType.NO_BAIT;
        if (matchesCantFishHereSignature(line)) return EventType.CANT_FISH_HERE;

        return EventType.NONE;
    }

    private void executeEvent(EventType event) throws InterruptedException {
        switch (event) {
            case PRIVATE_MESSAGE:
                SoundUtils.message();
                break;

            case MONSTER:
                fishingStatsListener.onMonsterCaught();
                fishingWorkflow.selectMonster();
                break;

            case FISHING_END:
                fishingWorkflow.restart();
                break;

            case FISHING_BITE:
                fishingWorkflow.waitFishingStart();
                break;

            case OBTAINED_OLD_BOX:
                fishingStatsListener.onOldBoxCaught();
                SoundUtils.success();
                break;

            case OBTAINED_FISH:
                fishingStatsListener.onFishCaught();
                break;

            case NO_BAIT:
                fishingStateListener.onError("No bait");
                fishingWorkflow.stop();
                break;

            case CANT_FISH_HERE:
                fishingStateListener.onError("Can't fish here");
                fishingWorkflow.stop();
                break;

            case NO_ROD:
                fishingStateListener.onError("No rod");
                fishingWorkflow.stop();
                break;

            case PUMPING:
                fishingStatsListener.onPumpingAttempted();
                break;

            case PUMPING_FAILED:
                fishingStatsListener.onPumpingFailed();
                break;

            case INCORRECT_PUMPING:
                fishingStatsListener.onIncorrectPumping();

            case REELING:
                fishingStatsListener.onReelingAttempted();
                break;

            case REELING_FAILED:
                fishingStatsListener.onReelingFailed();
                break;

            case INCORRECT_REELING:
                fishingStatsListener.onIncorrectReeling();
                break;

            case NONE:
            default:
                break;
        }
    }

    private boolean matchesPumpingSignature(ChatMessageLine line) {
        return ChatSignaturesManager.find("Pumping").isEqual(line);
    }

    private boolean matchesIncorrectPumpingSignature(ChatMessageLine line) {
        return ChatSignaturesManager.find("IncorrectPumping").isEqual(line);
    }

    private boolean matchesReelingSignature(ChatMessageLine line) {
        return ChatSignaturesManager.find("Reeling").isEqual(line);
    }

    private boolean matchesIncorrectReelingSignature(ChatMessageLine line) {
        return ChatSignaturesManager.find("IncorrectReeling").isEqual(line);
    }

    private boolean matchesSkillFailedSignature(ChatMessageLine line) {
        return ChatSignaturesManager.find("SkillFailed").isEqual(line);
    }

    private boolean matchesFishingBiteSignature(ChatMessageLine line) {
        return ChatSignaturesManager.find("FishingBite").isEqual(line);
    }

    private boolean isPrivateMessage(ChatMessageLine line) {
        return line.color() != null && "Purple".equals(line.color().getName());
    }

    private boolean matchesMonsterFirstLineSignature(ChatMessageLine line) {
        return ChatSignaturesManager.find("MonsterFirstLine").isEqual(line);
    }

    private boolean matchesMonsterSecondLineSignature(ChatMessageLine line) {
        return ChatSignaturesManager.find("MonsterSecondLine").isEqual(line);
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

    private boolean matchesCaughtSomethingSignature(ChatMessageLine line) {
        return ChatSignaturesManager.find("CaughtSomething").isEqual(line);
    }

    private boolean matchesNoBaitSignature(ChatMessageLine line) {
        return ChatSignaturesManager.find("NoBait").isEqual(line);
    }

    private boolean matchesNoRodSignature(ChatMessageLine line) {
        return ChatSignaturesManager.find("NoRod").isEqual(line);
    }

    private boolean matchesCantFishHereSignature(ChatMessageLine line) {
        return ChatSignaturesManager.find("CantFishHere").isEqual(line);
    }

    private enum EventType {
        NONE,
        PUMPING,
        INCORRECT_PUMPING,
        PUMPING_FAILED,
        REELING,
        INCORRECT_REELING,
        REELING_FAILED,
        SKILL_FAILED,
        PRIVATE_MESSAGE,
        MONSTER,
        FISHING_END,
        FISHING_BITE,
        OBTAINED_OLD_BOX,
        OBTAINED_FISH,
        NO_BAIT,
        NO_ROD,
        CANT_FISH_HERE
    }
}
