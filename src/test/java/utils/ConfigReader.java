package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;

    // Sınıf yüklendiğinde konfigürasyon dosyasını okuyup Properties nesnesine yüklüyoruz.
    static {
        try (FileInputStream fis = new FileInputStream("src/test/resources/config.properties")) {
            properties = new Properties();
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Configuration properties file cannot be loaded!");
        }
    }

    // Belirtilen anahtarın değerini döndürür.
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
