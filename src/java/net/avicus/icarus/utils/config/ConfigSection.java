package net.avicus.icarus.utils.config;

import com.google.common.collect.ImmutableList;
import lombok.Getter;
import lombok.ToString;

import java.util.*;

/**
 * A wrapper for a YAML configuration data.
 */
@ToString(exclude = "data")
@SuppressWarnings("unchecked")
public class ConfigSection {

    @Getter Map<String, Object> data;
    @Getter String name;

    /**
     * Load from a Map of values.
     * @param map Values to load.
     * @param name The name of the section.
     */
    public ConfigSection(Map<Object, Object> map, String name) {
        this.data = new LinkedHashMap<String, Object>();
        for (Map.Entry<Object, Object> entry : map.entrySet()) {
            String key = entry.getKey() + "";
            Object val = entry.getValue();

            // Convert map objects to nested config sections...
            if (val instanceof List) {
                List list = (List) val;
                if (list.size() > 0 && list.get(0) instanceof Map<?, ?>) {
                    List<ConfigSection> sections = new ArrayList<ConfigSection>();
                    for (Object obj : list)
                        sections.add(new ConfigSection((Map<?, ?>) obj));
                    val = sections;
                }
            } else if (val instanceof Map<?, ?>) {
                val = new ConfigSection((Map<Object, Object>) val, null);
            }

            data.put(key, val);
        }
        this.name = name;
    }

    /**
     * Load from a Map of values.
     * @param map Values to load.
     */
    public ConfigSection(Map map) {
        this(map, null);
    }

    /**
     * Modify a value and overwrite any existing value.
     * @param key The key of the value.
     * @param value The data to store.
     */
    public void set(String key, Object value) {
        data.put(key, value);
    }

    /**
     * Remove a value.
     * @param key
     */
    public void remove(String key) {
        data.remove(key);
    }

    /**
     * Checks if a value is set.
     * @param key The path to the value.
     * @return If the key exists and is set to any value.
     */
    public boolean contains(String key) {
        return data.containsKey(key);
    }

    /**
     * Checks if a value is not set.
     * @param key The path to the value.
     * @return If the key is not set.
     */
    public boolean empty(String key) {
        return !contains(key);
    }

    /**
     * Retrieve a list of keys.
     * @return Set of all keys under this section.
     */
    public ImmutableList<String> getKeys() {
        ImmutableList<String> list = ImmutableList.copyOf(data.keySet());
        return list;
    }

    /**
     * Retrieve data stored in the config.
     * @param key Path to the data.
     * @return Value of the key.
     */
    public Object get(String key) {
        return data.get(key);
    }

    /**
     * Retrieve data stored in the config.
     * @param key Path to the data.
     * @param def Default to value if null.
     * @return Value of the key.
     */
    public Object get(String key, Object def) {
        Object val = data.get(key);
        return val == null ? def : val;
    }

    /**
     * Retrieve an integer stored in the config.
     * @param key Path to the data.
     * @return Value of the key.
     */
    public int getInt(String key) {
        return (Integer) get(key);
    }

    /**
     * Retrieve an integer stored in the config.
     * @param key Path to the data.
     * @param def Default to value if null.
     * @return Value of the key.
     */
    public int getInt(String key, int def) {
        Object val = get(key);
        return (Integer) (val == null ? def : val);
    }

    /**
     * Retrieve any value stored in the config as a string.
     * @param key Path to the data.
     * @return Value of the key.
     */
    public String getAsString(String key) {
        return get(key, null) + "";
    }

    /**
     * Retrieve any value stored in the config as a string.
     * @param key Path to the data.
     * @param def Default to value if null.
     * @return Value of the key.
     */
    public String getAsString(String key, String def) {
        return get(key, def) + "";
    }

    /**
     * Retrieve a string stored in the config.
     * @param key Path to the data.
     * @return Value of the key.
     */
    public String getString(String key) {
        return (String) get(key);
    }

    /**
     * Retrieve a string stored in the config.
     * @param key Path to the data.
     * @param def Default to value if null.
     * @return Value of the key.
     */
    public String getString(String key, String def) {
        Object val = get(key);
        return (String) (val == null ? def : val);
    }

    /**
     * Retrieve a boolean stored in the config.
     * @param key Path to the data.
     * @return Value of the key.
     */
    public boolean getBoolean(String key) {
        return (Boolean) get(key);
    }

    /**
     * Retrieve a boolean stored in the config.
     * @param key Path to the data.
     * @param def Default to value if null.
     * @return Value of the key.
     */
    public boolean getBoolean(String key, boolean def) {
        Object val = get(key);
        return (Boolean) (val == null ? def : val);
    }

    /**
     * Retrieve a double stored in the config.
     * @param key Path to the data.
     * @return Value of the key.
     */
    public double getDouble(String key, double def) {
        return Double.valueOf(get(key, def) + "");
    }

    /**
     * Retrieve a double stored in the config.
     * @param key Path to the data.
     * @param def Default to value if null.
     * @return Value of the key.
     */
    public double getBoolean(String key, double def) {
        Object val = get(key);
        return (Double) (val == null ? def : val);
    }

    /**
     * Retrieve a child section of the config.
     * @param key Path to the data.
     * @return Value of the key.
     */
    public ConfigSection getSection(String key) {
        return (ConfigSection) get(key);
    }

    /**
     * Retrieve an integer list stored in the config.
     * @param key Path to the data.
     * @return Value of the key.
     */
    public List<Integer> getIntegerList(String key) {
        return (List) get(key);
    }

    /**
     * Retrieve a string list stored in the config.
     * @param key Path to the data.
     * @return Value of the key.
     */
    public List<String> getStringList(String key) {
        return (List) get(key);
    }

    /**
     * Retrieve a ConfigSection list stored in the config.
     * @param key Path to the data.
     * @return Value of the key.
     */
    public List<ConfigSection> getSectionList(String key) {
        return (List<ConfigSection>) get(key);
    }
}
