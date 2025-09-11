package dev.pakh.utils;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class SoundUtils {

    private static final AtomicBoolean isPlaying = new AtomicBoolean(false);

    public static void playSound(String path) {
        new Thread(() -> {
            try (AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(path))) {
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);
                clip.start();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }).start();
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

    public static void danger() {
        if (isPlaying.compareAndSet(false, true)) {
            new Thread(() -> {
                try {
                    System.out.println("------ Danger called");
                    for (int i = 0; i < 6; i++) {
                        playTone(new int[]{600}, 200);
                        playTone(new int[]{1200}, 200);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    isPlaying.set(false); // liberar flag
                }
            }).start();
        } else {
            System.out.println("------ Danger skipped (already playing)");
        }
    }

    public static void message() {
        if (isPlaying.compareAndSet(false, true)) {
            new Thread(() -> {
                try {
                    playTone(new int[]{500}, 120);
                    Thread.sleep(80);
                    playTone(new int[]{1000}, 120);
                    Thread.sleep(80);
                    playTone(new int[]{1000}, 120);
                    Thread.sleep(80);
                    playTone(new int[]{1000}, 120);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    isPlaying.set(false);
                }
            }).start();
        } else {
            System.out.println("------ Message skipped (already playing)");
        }
    }

    public static void success() {
        if (isPlaying.compareAndSet(false, true)) {
            new Thread(() -> {
                try {
                    System.out.println("------ Success called");
                    playTone(new int[]{1000}, 50);
                    playTone(new int[]{1300}, 50);
                    playTone(new int[]{1600}, 50);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    isPlaying.set(false);
                }
            }).start();
        } else {
            System.out.println("------ Success skipped (already playing)");
        }
    }
}
