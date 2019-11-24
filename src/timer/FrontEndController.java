package timer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;

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
        settingsManager.setVolumeOn(volumeMuteVar.isSelected());
        settingsManager.setVolume((int) (volumeSliderVar.getValue() * 100));
        settingsManager.setLibraryType(soundLibraryVar.getValue().toString());
        settingsManager.setAlarmFrequency(alarmFrequencyVar.getValue().toString());
        settingsManager.savePropertiesFile();
    }

    public void setClockVar(String sysTime, String startupTime) {
        startupVar.setText(startupTime);
        systemClockVar.setText(sysTime);
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
    }
}
