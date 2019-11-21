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
    private int volume;
    private AudioInputStream audioInputStream;
    private Clip clip;

    public SoundLibrary(String libraryName){
        this.libraryName = libraryName;
    }

    public boolean playHourlySound(int hour) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        String hourString = "";
        if (hour < 10){
            hourString = "0";
        }
        hourString = hourString + hour;
        String filePath = resourceFolder + libraryName + "/" + libraryName + "-" + hourString + ".wav";
        audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        //play sound
        clip.start();
        return true;
    }

    public boolean playTestSound() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        String filePath = resourceFolder + libraryName + "/" + libraryName + "-Library.wav";
        audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        //play sound
        clip.start();
        return true;
    }

    public boolean setVolume(int volume){
        this.volume = volume;
        return true;
    }
}
