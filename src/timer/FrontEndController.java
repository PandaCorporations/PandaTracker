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
    SoundLibrary soundLibrary;

    private boolean initializeFrontEndFromConfig(){
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
        for (String str : directories) {
            libraryOptions.add(str);
        }
        soundLibraryVar.setItems(libraryOptions);
        soundLibraryVar.setValue(SettingsManager.getLibraryType());
        if (soundLibraryVar.getValue() != ""){
            soundLibrary = new SoundLibrary(SettingsManager.getLibraryType());
        }
        return true;
    }

    @FXML void onPlayTestSound(ActionEvent event) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        soundLibrary.playTestSound();
    }


    @FXML
    public void initialize() throws IOException {
        SettingsManager settingsManager = new SettingsManager();
        settingsManager.setupSettings();
        initializeFrontEndFromConfig();
    }
}
