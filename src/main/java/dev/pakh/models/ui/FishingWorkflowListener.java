package dev.pakh.models.ui;

public interface FishingWorkflowListener {
    void onFishingStarted();

    void onFishingStopped();

    void onFishObtention();

    void onOldBoxObtention();
}

