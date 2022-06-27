package rwt.device.sound;

import rwt.thread.ThreadManager;

public class SoundDevice {

    static SoundPlayer device;
    static Sound loop;

    public static void createSoundDevice() {

        device = new SoundPlayer();

        ThreadManager.join(device);
        ThreadManager.start(device);

    }

    public static void playLoop(Sound sound) {

        if(loop != null) {
            loop.stop();
        }

        loop = sound;
        loop.loopPlay();

    }

    //normal sound
    public static void play(String sound) {

        device.submit(sound);

    }

    //play an important sound as an effect.
    //it must be played.
    public static void playRush(String sound) {

        device.submitLock(sound);

    }

    public static void stopLoop() {

        loop.stop();

    }

}
