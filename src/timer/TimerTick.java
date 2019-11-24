package timer;

import java.util.TimerTask;

public class TimerTick extends TimerTask {
    private FrontEndController frontEndController;
    public TimerTick(FrontEndController frontEndController){
        this.frontEndController = frontEndController;
    }

    @Override
    public void run() {

    }
}
