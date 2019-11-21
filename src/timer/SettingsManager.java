package timer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

class SettingsManager {
    private static String version;
    private static boolean volumeOn;
    private static int volume;
    private static String libraryType;
    private static String alarmFrequency;

    SettingsManager(){
    }

    /**
     * sets up settings
     * @return true if successful
     * @throws IOException
     */
    boolean setupSettings() throws IOException {
        Properties prop = loadPropertiesFile();
        version = prop.getProperty("version");
        volumeOn = Boolean.parseBoolean(prop.getProperty("volumeOn"));
        volume = Integer.parseInt(prop.getProperty("volume"));
        libraryType = prop.getProperty("libraryType");
        alarmFrequency = prop.getProperty("alarmFrequency");
        return true;
    }

    /**
     * load the properties file if there is one.
     * @return true if successful.
     * @throws IOException if file not found
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

    static String getVersion() {
        return version;
    }

    static void setVersion(String version) {
        SettingsManager.version = version;
    }

    static boolean isVolumeOn() {
        return volumeOn;
    }

    static void setVolumeOn(boolean volumeOn) {
        SettingsManager.volumeOn = volumeOn;
    }

    static int getVolume() {
        return volume;
    }

    static void setVolume(int volume) {
        SettingsManager.volume = volume;
    }

    static String getLibraryType() {
        return libraryType;
    }

    static void setLibraryType(String libraryType) {
        SettingsManager.libraryType = libraryType;
    }

    static String getAlarmFrequency() {
        return alarmFrequency;
    }

    static void setAlarmFrequency(String alarmFrequency) {
        SettingsManager.alarmFrequency = alarmFrequency;
    }
}
