package rwt.device.sound;

import javax.sound.sampled.Clip;

public class Sound {

    Clip clip;

    public void set(Clip c) {

        clip = c;

    }

    public void singlePlay() {

        clip.loop(0);//开始

    }

    public void loopPlay() {

        clip.loop(-1);

    }

    public void stop() {

        clip.stop();

    }

    public int getLength() {

        return clip.getFrameLength() / 1000;

    }

}
