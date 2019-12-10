package timer;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Duration;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Timer;

public class FrontEndController {
    @FXML private Label versionVar;
    @FXML private Slider volumeSliderVar;
    @FXML private ChoiceBox alarmFrequencyVar;
    @FXML private CheckBox volumeMuteVar;
    @FXML private Button saveSessionData;
    @FXML private Button testSound;
    @FXML private Label systemClockVar;
    @FXML private Label startupVar;
    @FXML private ScrollPane sessionTrackerPane;
    @FXML private ChoiceBox soundLibraryVar;
    @FXML private Button saveCurrentSettings;
    private SettingsManager settingsManager;
    private SoundLibrary soundLibrary;

    public static String currentTime;
    public static String sinceStartup;
    private static boolean rangThisHour;
    private static int rangCounter;

    /**
     * gets all the settings from config
     */
    private void initializeFrontEndFromConfig(){
        volumeMuteVar.setSelected(SettingsManager.isVolumeOn());
        versionVar.setText(SettingsManager.getVersion());
        volumeSliderVar.adjustValue(SettingsManager.getVolume());

        //Alarm options settings
        ObservableList<String> alarmOptions = FXCollections.observableArrayList("Hourly","Bi-Hourly","Every 3 Hours", "Every 6 Hours");
        alarmFrequencyVar.setItems(alarmOptions);
        alarmFrequencyVar.setValue(SettingsManager.getAlarmFrequency());

        //Get different avail libraries
        File file = new File("resources");
        System.out.println(file.getAbsoluteFile().toString());
        String[] directories = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });
        ObservableList<String> libraryOptions = FXCollections.observableArrayList();
        libraryOptions.addAll(Arrays.asList(directories));
        soundLibraryVar.setItems(libraryOptions);
        soundLibraryVar.setValue(SettingsManager.getLibraryType());
        if (soundLibraryVar.getValue() != ""){
            soundLibrary = new SoundLibrary(SettingsManager.getLibraryType(), SettingsManager.getVolume());
        }
        rangThisHour = false;
        rangCounter = 0;
    }

    /**
     * Plays the test sound
     * @param event jfx
     * @throws UnsupportedAudioFileException file not supported
     * @throws IOException file read error
     * @throws LineUnavailableException i have no idea
     */
    @FXML void onPlayTestSound(ActionEvent event) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if (!volumeMuteVar.isSelected()){
            soundLibrary.playTestSound();
        }
    }

    @FXML void onSaveSettings(ActionEvent event) throws IOException {
        SettingsManager.setVolumeOn(volumeMuteVar.isSelected());
        SettingsManager.setVolume((int) (volumeSliderVar.getValue() * 100));
        SettingsManager.setLibraryType(soundLibraryVar.getValue().toString());
        SettingsManager.setAlarmFrequency(alarmFrequencyVar.getValue().toString());
        SettingsManager.savePropertiesFile();
    }

    @FXML
    private void setClockVar(){
        startupVar.setText(sinceStartup);
        systemClockVar.setText(currentTime);
    }

    private void handlePlayHourlySound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        LocalDateTime currentTime = LocalDateTime.now();
        if (!rangThisHour){
            rangCounter = 0;
            int minute = currentTime.getMinute();
            if (minute == 0){
                int hour = currentTime.getHour();
                rangThisHour = true;
                soundLibrary.playHourlySound(hour);
            }
        } else if (rangCounter > 60){
            rangThisHour = false;
        } else {
            rangCounter++;
        }
    }


    /**
     * FXML init
     * @throws IOException bad file read
     */
    @FXML
    public void initialize() throws IOException {
        settingsManager = new SettingsManager();
        settingsManager.setupSettings();
        initializeFrontEndFromConfig();

        //handle volume slider changes
        volumeSliderVar.valueProperty().addListener((observable, oldValue, newValue) -> {
            soundLibrary.setVolume(newValue.intValue());
        });

        //handle mute changes
        volumeMuteVar.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue){
                soundLibrary.setVolume(0);
            } else {
                soundLibrary.setVolume((int)volumeSliderVar.getValue());
            }
        });

        //tick timer
        // not in fx application thread
        TimerTick timerTick = new TimerTick(this);
        Timer clockTimer = new Timer(true);
        clockTimer.schedule(timerTick,0,1000);

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            setClockVar();
            //play hourly
            try {
                handlePlayHourlySound();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                ex.printStackTrace();
            }
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
}
