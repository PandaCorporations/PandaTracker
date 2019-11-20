package timer;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class FrontEndController {
    SettingsManager settingsManager;
    @FXML private Label versionVar;
    @FXML private Slider volumeSliderVar;
    @FXML private ChoiceBox alarmFrequencyVar;
    @FXML private CheckBox volumeMuteVar;
    @FXML private Button saveSessionData;
    @FXML private Button testSound;
    @FXML private Label systemClockVar;
    @FXML private Label startupVar;
    @FXML private ScrollPane sessionTrackerPane;

    public boolean initializeFrontEndFromConfig(){
        String ver = settingsManager.getVersion();
        versionVar.setText(ver);
        return true;
    }

    @FXML
    public void initialize() throws IOException {
        settingsManager = new SettingsManager();
        settingsManager.setupSettings();
        initializeFrontEndFromConfig();
    }
}
