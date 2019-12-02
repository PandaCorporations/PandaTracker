package timer;

import java.io.*;
import java.util.Properties;

class SettingsManager {
    private static Properties prop;
    private static String version;
    private static boolean volumeOn;
    private static int volume;
    private static String libraryType;
    private static String alarmFrequency;

    SettingsManager(){
    }

    /**
     * sets up settings
     * @throws IOException failed to open file
     */
    void setupSettings() throws IOException {
        prop = loadPropertiesFile();
        version = prop.getProperty("version");
        volumeOn = Boolean.parseBoolean(prop.getProperty("volumeOn"));
        volume = Integer.parseInt(prop.getProperty("volume"));
        libraryType = prop.getProperty("libraryType");
        alarmFrequency = prop.getProperty("alarmFrequency");
    }

    /**
     * load the properties file if there is one.
     * @return true if successful.
     * @throws IOException if file not found
     */
    private Properties loadPropertiesFile() throws IOException {
        Properties prop = new Properties();

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties");
        if (inputStream != null){
            prop.load(inputStream);
        } else {
            File file = new File("config.properties");
            Properties newProp = new Properties();
            newProp.setProperty("version","0.0.0");
            newProp.setProperty("volumeOn", "1");
            newProp.setProperty("volume", "100");
            newProp.setProperty("libraryType","Verniy");
            newProp.setProperty("alarmFrequency","Hourly");
            newProp.store(new FileOutputStream("config.properties"), null);
            loadPropertiesFile();
        }
        return prop;
    }

    void savePropertiesFile() throws IOException {
        prop.setProperty("version",version);
        prop.setProperty("volumeOn", String.valueOf(volumeOn));
        prop.setProperty("volume", String.valueOf(volume));
        prop.setProperty("libraryType",libraryType);
        prop.setProperty("alarmFrequency",alarmFrequency);
        prop.store(new FileOutputStream("config.properties"), null);
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
