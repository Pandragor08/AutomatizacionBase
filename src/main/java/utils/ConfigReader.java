package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties = new Properties();

    static {
        try (InputStream input = ConfigReader.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("No se encontró config.properties en resources");
            }
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("No se pudo cargar config.properties");
        }
    }

    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("La clave '" + key + "' no existe en config.properties");
        }
        return value;
    }

    public static int getIntProperty(String key) {
        String value = getProperty(key);
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new RuntimeException("La clave '" + key + "' no es un número válido en config.properties");
        }
    }
}