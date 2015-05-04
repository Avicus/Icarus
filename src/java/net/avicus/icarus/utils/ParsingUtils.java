package net.avicus.icarus.utils;

import net.avicus.icarus.utils.MapVersion;
import net.avicus.icarus.utils.config.ConfigSection;
import net.avicus.icarus.match.Match;
import net.avicus.icarus.module.regions.*;
import org.bukkit.ChatColor;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParsingUtils {

    private static List<String> trues = Arrays.asList("true", "yes", "allow", "yeah", "totally");
    private static List<String> falses = Arrays.asList("false", "no", "deny", "nope", "nay");

    public static boolean parseBoolean(String text) {
        text = text.replace(" ", "").toLowerCase();
        if (trues.contains(text))
            return true;
        else if (falses.contains(text))
            return false;
        throw new IllegalArgumentException("Unknown boolean value \"" + text + "\".");
    }

    public static MapVersion parseVersion(String text) {
        String[] parts = text.split("\\.", 3);
        if (parts.length < 3)
            throw new IllegalArgumentException("Version is not in semantic format.");

        int major = Integer.parseInt(parts[0]);
        int minor = Integer.parseInt(parts[1]);
        int patch = Integer.parseInt(parts[2]);
        return new MapVersion(major, minor, patch);
    }

    public static ChatColor parseColor(String color) {
        return ChatColor.valueOf(color.replace(" ", "_").toUpperCase());
    }

    public static List<Double> parseDoubleList(String text) {
        String[] parts = text.replace(" ", "").split(",");
        List<Double> doubles = new ArrayList<Double>();
        for (String s : parts)
            doubles.add(Double.parseDouble(s));
        return doubles;
    }

    public static Vector parseVector(String text) {
        List<Double> coords = parseDoubleList(text);
        if (coords.size() != 3)
            throw new IllegalArgumentException("X, Y and Z must be provided for locations.");
        return new Vector(coords.get(0), coords.get(1), coords.get(2));
    }


    public static Region parseRegion(Match match, ConfigSection config) {
        if (config.contains("point")) {
            Vector point = parseVector(config.getString("point"));
            return new RegionPoint(point);
        }
        else if (config.contains("cuboid")) {
            Vector a = parseVector(config.getStringList("cuboid").get(0));
            Vector b = parseVector(config.getStringList("cuboid").get(1));
            return new RegionCuboid(a, b);
        }
        else if (config.contains("sphere")) {
            Vector origin = parseVector(config.getString("sphere"));
            int radius = config.getInt("radius");
            return new RegionSphere(origin, radius);
        }
        else if (config.contains("cylinder")) {
            Vector origin = parseVector(config.getString("cylinder"));
            int radius = config.getInt("radius");
            int height = config.getInt("height");
            return new RegionCylinder(origin, radius, height);
        }
        else if (config.contains("cylinder")) {
            Vector origin = parseVector(config.getString("cylinder"));
            int radius = config.getInt("radius");
            int height = config.getInt("height");
            return new RegionCylinder(origin, radius, height);
        }
        else if (config.contains("global")) {
            return new RegionGlobal();
        }
        else if (config.contains("join")) {
            List<ConfigSection> list = config.getSectionList("join");
            List<Region> joined = new ArrayList<Region>();
            for (ConfigSection cs : list)
                joined.add(parseRegion(match, cs));
            return new RegionJoin(joined);
        }
        else if (config.contains("id")) {
            RegionsModule regions = match.getModule(RegionsModule.class);
            if (regions == null)
                throw new IllegalArgumentException("Unknown region type.");
            return regions.getRegionById(config.getString("id"));
        }
        else
            throw new IllegalArgumentException("Unknown region type.");
    }

}
