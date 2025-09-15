package dev.pakh.logic.ports;

public interface FishingStateListener {
//    void onIdle();

    void onDetectUI();

    void onReady();

    void onFishingWait();

    void onFishing();

    void onError(String message);
}
