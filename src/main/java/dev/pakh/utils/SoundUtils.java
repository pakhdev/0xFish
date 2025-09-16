package dev.pakh.utils;

import javax.sound.sampled.*;

import java.util.ArrayDeque;
import java.util.Queue;

public class SoundUtils {
    private static final float SAMPLE_RATE = 44100f;

    private static final Queue<int[][]> playlist = new ArrayDeque<>();
    private static boolean isPlaying = false;

    public static void danger() {
        enqueue(new int[][]{
                {600, 200}, {1200, 200}
        }, 6);
    }

    public static void success() {
        enqueue(new int[][]{
                {600, 60}, {800, 60}, {1100, 100}
        }, 1);
    }

    public static void message() {
        enqueue(new int[][]{
                {1200, 50}, {1000, 50}, {800, 50}, {400, 150}
        }, 6);
    }

    private static synchronized void enqueue(int[][] baseTones, int repeats) {
        int[][] tones = new int[baseTones.length * repeats][2];
        for (int i = 0; i < repeats; i++) {
            System.arraycopy(baseTones, 0, tones, i * baseTones.length, baseTones.length);
        }

        playlist.add(tones);

        if (!isPlaying) {
            playNext();
        }
    }

    private static synchronized void playNext() {
        int[][] tones = playlist.poll();
        if (tones == null) {
            isPlaying = false;
            return;
        }

        isPlaying = true;

        new Thread(() -> {
            try {
                playSequence(tones);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                playNext();
            }
        }).start();
    }

    private static void playSequence(int[][] tones) throws LineUnavailableException {
        AudioFormat af = new AudioFormat(SAMPLE_RATE, 16, 1, true, false);
        try (SourceDataLine sdl = AudioSystem.getSourceDataLine(af)) {
            sdl.open(af);
            sdl.start();
            for (int[] tone : tones) {
                int freq = tone[0];
                int msecs = tone[1];
                writeTone(sdl, freq, msecs);
            }
            sdl.drain();
            sdl.stop();
        }
    }

    private static void writeTone(SourceDataLine sdl, int freq, int msecs) {
        int totalSamples = (int) (msecs * SAMPLE_RATE / 1000);
        int fadeSamples = (int) (0.005 * SAMPLE_RATE);

        byte[] buffer = new byte[2];

        for (int i = 0; i < totalSamples; i++) {
            double angle = 2.0 * Math.PI * i * freq / SAMPLE_RATE;
            double sample = Math.sin(angle);

            double envelope = 1.0;
            if (i < fadeSamples) {
                envelope = (double) i / fadeSamples;
            } else if (i > totalSamples - fadeSamples) {
                envelope = (double) (totalSamples - i) / fadeSamples;
            }

            short val = (short) (sample * envelope * Short.MAX_VALUE);

            buffer[0] = (byte) (val & 0xff);
            buffer[1] = (byte) ((val >> 8) & 0xff);

            sdl.write(buffer, 0, 2);
        }
    }
}
