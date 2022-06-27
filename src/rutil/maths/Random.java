package rutil.maths;

import rutil.bsck.Platform;

/**
 * 和Replay的协调问题调试了一下午。
 * 哎~
 */
public class Random {

    public int seed;
    public int startSeed;

    int M = 197;
    int A = 484;
    int C = 103;

    public Random() {

        this((int) (Platform.getTickNano()));

    }

    public Random(int seed) {

        setSeed(seed);

    }

    public void setSeed(int s) {

        seed = s;
        startSeed = s;

    }

    /**
     * set seed to start
     */
    public void rtStartSeed() {

        setSeed(startSeed);

    }

    public int newSeed() {

        int gen = 1;
        int tryTimes = 0;
        int oldSeed = seed;

        do {
            tryTimes++;
            gen = (M * seed * gen + A) % C;//生成
            seed = (seed * M + A) / C - A;//新种子生成
        }
        while(seed == oldSeed && tryTimes < 5);

        if(gen < -100 || gen > 100) {
            gen = 0;//如果gen超出界限就设为0
        }

        return gen;

    }

    public double genPercent() {

        int i = Maths.abs(newSeed());//获取-100~100的随机数字
        return i * 0.01;

    }

    public double random() {

        return random(0, 1);

    }

    public double random(double min, double max) {

        return genPercent() * (max - min) + min;

    }

    public int randomInt(int min, int max) {

        return Maths.toInt(random(min, max));

    }

}
