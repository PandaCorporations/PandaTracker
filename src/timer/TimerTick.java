package timer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimerTask;

public class TimerTick extends TimerTask {
    private FrontEndController frontEndController;
    private LocalDateTime startTime;

    public TimerTick(FrontEndController frontEndController){
        this.frontEndController = frontEndController;
        this.startTime = LocalDateTime.now();
    }

    @Override
    public void run() {
        DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
        Duration difference = Duration.between(startTime,LocalDateTime.now());
        long seconds = difference.getSeconds();
        String sinceStartup = String.format("%02d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, (seconds % 60));
        String currentTime = LocalDateTime.now().format(dtFormat);
        frontEndController.currentTime = currentTime;
        frontEndController.sinceStartup = sinceStartup;
    }
}
