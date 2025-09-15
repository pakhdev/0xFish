package dev.pakh.logic.ports;

public interface FishingStatsListener {
    void onPumpingAttempted();

    void onPumpingFailed();

    void onIncorrectPumping();

    void onReelingAttempted();

    void onReelingFailed();

    void onIncorrectReeling();

    void onFishCaught();

    void onOldBoxCaught();

    void onMonsterCaught();
}


