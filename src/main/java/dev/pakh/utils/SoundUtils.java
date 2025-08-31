package dev.pakh.utils;

import javax.sound.sampled.*;

public class SoundUtils {
    public static void warning() throws Exception {
        playTone(800, 200);
        Thread.sleep(100);
        playTone(800, 200);
        Thread.sleep(100);
        playTone(800, 200);
        Thread.sleep(300);

        playTone(800, 600);
        Thread.sleep(100);
        playTone(800, 600);
        Thread.sleep(100);
        playTone(800, 600);
        Thread.sleep(300);

        playTone(800, 200);
        Thread.sleep(100);
        playTone(800, 200);
        Thread.sleep(100);
        playTone(800, 200);
    }

    private static void playTone(int hz, int msecs) throws LineUnavailableException {
        float SAMPLE_RATE = 44100;
        byte[] buf = new byte[1];
        AudioFormat af = new AudioFormat(SAMPLE_RATE, 8, 1, true, false);
        try (SourceDataLine sdl = AudioSystem.getSourceDataLine(af)) {
            sdl.open(af);
            sdl.start();
            for (int i = 0; i < msecs * SAMPLE_RATE / 1000; i++) {
                double angle = i / (SAMPLE_RATE / hz) * 2.0 * Math.PI;
                buf[0] = (byte) (Math.sin(angle) * 127);
                sdl.write(buf, 0, 1);
            }
            sdl.drain();
            sdl.stop();
        }
    }
}

