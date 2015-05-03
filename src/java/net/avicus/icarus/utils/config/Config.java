package net.avicus.icarus.utils.config;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.Map;

/**
 * A wrapper for a YAML configuration file.
 */
public class Config extends ConfigSection {

    private static Yaml yaml = new Yaml();

    /**
     * Load a Config file from a stream.
     * @param inputStream Stream to load YAML.
     */
    public Config(InputStream inputStream) {
        super((Map) yaml.load(inputStream));

    }
    /**
     * Load a Config file from a string.
     * @param text String to load YAML.
     */
    public Config(String text) {
        super((Map) yaml.load(text));
    }

    /**
     * Saves data to a file.
     * @param file The file to write the data to.
     * @throws Exception If an issue occurs with file operations.
     */
    public void save(File file) throws Exception {
        FileWriter writer = new FileWriter(file);
        yaml.dump(data, writer);
    }

}
