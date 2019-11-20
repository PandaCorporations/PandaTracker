package timer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SettingsManager {
    private static String version;
    private static boolean volumeOn;
    private static int volume;
    private static String libraryType;
    private static String alarmFrequency;

    public SettingsManager(){
        this.version = "0.0.1";
        this.volumeOn = false;
        this.volume = 100;
        this.libraryType = "Verniy";
        this.alarmFrequency = "Hourly";
    }

    /**
     * sets up settings
     * @return true if successful
     * @throws IOException
     */
    public boolean setupSettings() throws IOException {
        Properties prop = loadPropertiesFile();
        this.version = prop.getProperty("version");
        this.volumeOn = Boolean.parseBoolean(prop.getProperty("volumeOn"));
        this.volume = Integer.parseInt(prop.getProperty("volume"));
        this.libraryType = prop.getProperty("libraryType");
        this.alarmFrequency = prop.getProperty("alarmFrequency");
        return true;
    }

    /**
     * load the properties file if there is one.
     * @return true if successful.
     * @throws IOException
     */
    private Properties loadPropertiesFile() throws IOException {
        Properties prop = new Properties();
        String filePath = "config.properties";

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
        if (inputStream != null){
            prop.load(inputStream);
        } else {
            throw new FileNotFoundException("config file not found");
        }
        return prop;
    }

    public static String getVersion() {
        return version;
    }

    public static void setVersion(String version) {
        SettingsManager.version = version;
    }

    public static boolean isVolumeOn() {
        return volumeOn;
    }

    public static void setVolumeOn(boolean volumeOn) {
        SettingsManager.volumeOn = volumeOn;
    }

    public static int getVolume() {
        return volume;
    }

    public static void setVolume(int volume) {
        SettingsManager.volume = volume;
    }

    public static String getLibraryType() {
        return libraryType;
    }

    public static void setLibraryType(String libraryType) {
        SettingsManager.libraryType = libraryType;
    }

    public static String getAlarmFrequency() {
        return alarmFrequency;
    }

    public static void setAlarmFrequency(String alarmFrequency) {
        SettingsManager.alarmFrequency = alarmFrequency;
    }
}
