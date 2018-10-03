package gamedevelopers.funcandi.hiddenobject;
import android.graphics.Canvas;


public class GameLoopThreadSoul extends Thread {

    static final long FPS = 10;
    Canvas c;

    private GameViewSoul view;

    private boolean running = false;

    public GameLoopThreadSoul(GameViewSoul view) {

        this.view = view;
    }

    public void setRunning(boolean run) {

        running = run;
    }

    public void run() {

        //long ticksPS = 1000 / FPS;
        long ticksPS = 4500 / FPS;

        long startTime;

        long sleepTime;

        while (running) {

            c = null;

            startTime = System.currentTimeMillis();

            try {

                c = view.getHolder().lockCanvas();

                synchronized (view.getHolder()) {

                    view.onDraw(c);
                    //view.update();

                }

            } finally {

                if (c != null) {

                    view.getHolder().unlockCanvasAndPost(c);

                }

            }

            sleepTime = ticksPS-(System.currentTimeMillis() - startTime);

            try {

                if (sleepTime > 0)

                    sleep(sleepTime);

                else

                    sleep(10);

            } catch (Exception e) {}
        }
    }
}