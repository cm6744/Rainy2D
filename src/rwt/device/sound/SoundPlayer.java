package rwt.device.sound;

import rwt.thread.ThreadTask;
import rwt.resource.Reader;
import rwt.thread.ThreadManager;

public class SoundPlayer extends ThreadTask {

    String nowPlay;
    String lockedPlay;
    boolean lock;

    public static int DEF_SLEEP = 10;

    public void submit(String aul) {

        nowPlay = aul;

    }

    public void submitLock(String aul) {

        if(!lock) {
            lockedPlay = aul;
            lock = true;
        }

    }

    public void update() {

        while(ThreadManager.notDisposed(this)) {

            Sound sound = null;

            if(nowPlay != null) {
                sound = Reader.sound(nowPlay);
                nowPlay = null;
            }

            if(lockedPlay != null) {
                sound = Reader.sound(lockedPlay);
                lockedPlay = null;
                lock = false;
            }

            if(sound != null) {
                sound.singlePlay();
                ThreadManager.sleep(this, sound.getLength());
            }
            else {
                ThreadManager.sleep(this, DEF_SLEEP);
            }

        }

    }

}
