package dev.pakh.utils;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundUtils {

    public static void playSound(String path) {
        try (AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(path))) {
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private static void playTone(int[] freqs, int msecs) throws LineUnavailableException {
        float SAMPLE_RATE = 44100;
        byte[] buf = new byte[1];
        AudioFormat af = new AudioFormat(SAMPLE_RATE, 8, 1, true, false);
        try (SourceDataLine sdl = AudioSystem.getSourceDataLine(af)) {
            sdl.open(af);
            sdl.start();
            for (int i = 0; i < msecs * SAMPLE_RATE / 1000; i++) {
                double sample = 0;
                for (int f : freqs) {
                    sample += Math.sin(i / (SAMPLE_RATE / f) * 2.0 * Math.PI);
                }
                buf[0] = (byte) (sample / freqs.length * 127);
                sdl.write(buf, 0, 1);
            }
            sdl.drain();
            sdl.stop();
        }
    }


    public static void danger() throws Exception {
        for (int i = 0; i < 6; i++) {
            playTone(new int[]{600}, 200);
            playTone(new int[]{1200}, 200);
        }
    }

    public static void message() throws Exception {
        playTone(new int[]{1500}, 120);
        Thread.sleep(80);
        playTone(new int[]{1700}, 120);
    }
}
