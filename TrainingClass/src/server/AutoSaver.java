package server;

import java.io.IOException;

public class AutoSaver extends Thread {
    private int delay;
    Server myBoss;
    public AutoSaver(Server myBoss, int delay) {
        this.delay = delay;
        this.myBoss = myBoss;
    }

    @Override
    public void run() {
        while (true)
        {
            try {
                Thread.sleep(this.delay);
                myBoss.SaveAllData();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
