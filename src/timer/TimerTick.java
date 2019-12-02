package timer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;

public class TimerTick extends TimerTask {
    private FrontEndController frontEndController;
    private Date startTime;

    public TimerTick(FrontEndController frontEndController){
        this.frontEndController = frontEndController;
        this.startTime = new Date();
    }

    @Override
    public void run() {
        DateFormat format = new SimpleDateFormat("HH:mm:ss");
        String sinceStartup = format.format(startTime.getTime());
        String currentTime = format.format(new Date().getTime());
        frontEndController.currentTime = currentTime;
        frontEndController.sinceStartup = sinceStartup;
    }
}
