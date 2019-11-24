package timer;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * This is a sound library that will return the correct sound file on request
 */
public class SoundLibrary {
    private String resourceFolder = "resources/";
    private String libraryName;
    private float volume;
    private AudioInputStream audioInputStream;
    private Clip clip;
    private FloatControl gainControl;

    SoundLibrary(String libraryName, int volume){
        this.libraryName = libraryName;
        this.volume = (float) volume /100;
    }

    void setVolume(int volume){
        this.volume = (float) volume /100;
        if (clip != null && gainControl != null){
            float range = gainControl.getMaximum() - gainControl.getMinimum();
            float gain = (range * this.volume) + gainControl.getMinimum();
            gainControl.setValue(gain);
        }
    }

    public boolean playHourlySound(int hour) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        if (clip != null){
            clip.close();
        }
        String hourString = "";
        if (hour < 10){
            hourString = "0";
        }
        hourString = hourString + hour;
        String filePath = resourceFolder + libraryName + "/" + libraryName + "-" + hourString + ".wav";
        audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);

        gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        float range = gainControl.getMaximum() - gainControl.getMinimum();
        float gain = (range * this.volume) + gainControl.getMinimum();
        gainControl.setValue(gain);
        //play sound
        clip.start();
        return true;
    }

    void playTestSound() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        if (clip != null){
            clip.close();
        }
        String filePath = resourceFolder + libraryName + "/" + libraryName + "-Library.wav";
        audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);

        gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        float range = gainControl.getMaximum() - gainControl.getMinimum();
        float gain = (range * this.volume) + gainControl.getMinimum();
        gainControl.setValue(gain);
        //play sound
        clip.start();
    }
}
