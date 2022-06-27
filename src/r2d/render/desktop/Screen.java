package r2d.render.desktop;

import rutil.bsck.Platform;
import rwt.device.input.Input;
import rwt.thread.ThreadManager;
import rwt.thread.ThreadTask;

public class Screen {

    public int nowFpsUpdate;
    public int nowFpsRender;
    public int defaultFps;
    private double fpsTime;

    public Canvas canvas;

    private int updateFrame;
    private int renderFrame;

    Ticker ticker;

    public Screen() {

        setDefaultFps(60);

        ticker = new Ticker();

        ThreadManager.join(ticker);

    }

    private static double nanoSec = 1000000000.0;

    /**
     * 设置刷新率（默认60）
     * @param fps 越大刷新越快，时间加速、缓慢也许可以用
     */
    public void setDefaultFps(int fps) {

        defaultFps = fps;
        nowFpsUpdate = fps;
        fpsTime = nanoSec / fps;


    }

    public void loadFromCanvas(Canvas load) {

        canvas = load;
        canvas.setPause(false);
        init = false;//it's very necessary

        if(!ThreadManager.isStarted(ticker)) {
            ThreadManager.start(ticker);//make sure canvas != null
        }

    }

    private boolean init;

    private class Ticker extends ThreadTask {

        public void update() {

            long lastTick = Platform.getTickNano();
            long timerFps = Platform.getTickNs();

            int loopTicks;
            int maxLoop = 2;

            while(ThreadManager.notDisposed(this)) {

                loopTicks = 0;

                while(Platform.getTickNano() > lastTick && loopTicks < maxLoop) {
                    Input.tick();//tick input for timer
                    canvas.update();//tick canvas in
                    init = true;
                    updateFrame++;
                    lastTick += fpsTime;
                    loopTicks++;

                    if(loopTicks >= maxLoop && Platform.getTickNano() - lastTick > fpsTime) {
                        lastTick = Platform.getTickNano();//如果补帧一次仍然差距16ms以上就放弃补帧
                    }
                }

                if(init) {
                    canvas.render();
                    renderFrame++;
                }

                if(Platform.getTickNs() - timerFps > 1000) {//fps count
                    nowFpsUpdate = updateFrame;
                    nowFpsRender = renderFrame;
                    updateFrame = 0;
                    renderFrame = 0;
                    timerFps += 1000;
                }

                init = true;

            }

        }

    }

}
