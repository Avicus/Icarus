package net.avicus.icarus.utils;

import net.avicus.icarus.match.Match;
import net.avicus.icarus.module.conditions.Condition;
import net.avicus.icarus.module.regions.*;
import net.avicus.icarus.utils.config.ConfigSection;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.*;

public class ParserUtils {

    private static List<String> trues = Arrays.asList("true", "yes", "allow", "yeah", "totally");
    private static List<String> falses = Arrays.asList("false", "no", "deny", "nope", "nay");

    public static boolean parseBoolean(String text) {
        text = text.replace(" ", "").toLowerCase();
        if (trues.contains(text))
            return true;
        else if (falses.contains(text))
            return false;
        throw new IllegalArgumentException("Unknown boolean value '" + text + "'.");
    }

    public static <T extends Enum<T>> T parseEnum(Class<T> type, String input) {
        try {
            return Enum.valueOf(type, input.toUpperCase().replace(" ", "_").replace("-", "_"));
        } catch (Exception e) {
            throw new IllegalArgumentException("Unknown type of " + type.getSimpleName() + " '" + input + "'.");
        }
    }

    public static MapVersion parseVersion(String text) {
        String[] parts = text.split("\\.", 3);
        if (parts.length < 2 || parts.length > 3)
            throw new IllegalArgumentException("Version is not in semantic format.");

        int major = Integer.parseInt(parts[0]);
        int minor = Integer.parseInt(parts[1]);
        int patch = 0;
        if (parts.length == 3)
            patch = Integer.parseInt(parts[2]);
        return new MapVersion(major, minor, patch);
    }

    public static ChatColor parseChatColor(String color) {
        return parseEnum(ChatColor.class, color);
    }

    public static Color parseColor(String text) {
        List<Integer> rgb = parseIntList(text);
        if (rgb.size() == 3)
            return Color.fromRGB(rgb.get(0), rgb.get(1), rgb.get(2));
        throw new IllegalArgumentException("Unknown color '" + text + "'.");
    }

    public static List<Double> parseDoubleList(String text) {
        String[] parts = text.replace(" ", "").split(",");
        List<Double> doubles = new ArrayList<Double>();
        for (String s : parts)
            doubles.add(Double.parseDouble(s));
        return doubles;
    }

    public static List<Integer> parseIntList(String text) {
        String[] parts = text.replace(" ", "").split(",");
        List<Integer> doubles = new ArrayList<Integer>();
        for (String s : parts)
            doubles.add(Integer.parseInt(s));
        return doubles;
    }

    public static Vector parseVector(String text) {
        List<Double> coords = parseDoubleList(text);
        if (coords.size() != 3)
            throw new IllegalArgumentException("X, Y and Z must be provided for locations.");
        return new Vector(coords.get(0), coords.get(1), coords.get(2));
    }

    public static Region parseRegion(Match match, ConfigSection config) {
        if (config.contains("translate")) {
            Vector translation = parseVector(config.getAsString("translate"));
            config.remove("translate");
            return new RegionTranslate(parseRegion(match, config), translation);
        }
        else if (config.contains("fill")) {
            Vector base = parseVector("fill");
            Vector a = parseVector(config.getStringList("bounds").get(0));
            Vector b = parseVector(config.getStringList("bounds").get(1));
            int height = config.getInt("height", 1);
            return new RegionFill(a, b, base, height, null);
        }
        else if (config.contains("point")) {
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
            boolean hollow = config.getBoolean("hollow", false);
            return new RegionSphere(origin, radius, hollow);
        }
        else if (config.contains("cylinder")) {
            Vector origin = parseVector(config.getString("cylinder"));
            int radius = config.getInt("radius");
            int height = config.getInt("height");
            boolean hollow = config.getBoolean("hollow", false);
            return new RegionCylinder(origin, radius, height, hollow);
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
        else if (config.contains("invert")) {
            List<ConfigSection> list = config.getSectionList("invert");
            List<Region> inverted = new ArrayList<Region>();
            for (ConfigSection cs : list)
                inverted.add(parseRegion(match, cs));
            return new RegionInvert(inverted);
        }
        else if (config.contains("intersect")) {
            List<ConfigSection> list = config.getSectionList("intersect");
            List<Region> regions = new ArrayList<Region>();
            for (ConfigSection cs : list)
                regions.add(parseRegion(match, cs));
            return new RegionIntersect(regions);
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

    public static Duration parseDuration(String text) {
        if (text.equals("oo"))
            return new Duration(Integer.MAX_VALUE);
        else if (text.equals("-oo"))
            return new Duration(Integer.MIN_VALUE);

        return new Duration(text);
    }

    public static int parseSlot(String text) {
        try {
            return Integer.parseInt(text);
        } catch (Exception e) {
            text = text.toLowerCase();
            if (text.equals("helmet") || text.equals("hat"))
                return 110;
            else if (text.equals("chestplate"))
                return 111;
            else if (text.equals("leggings") || text.equals("pants"))
                return 112;
            else if (text.equals("boots"))
                return 113;
            else
                throw new IllegalArgumentException("Unknown slot '" + text + "'.");
        }
    }

    public static Map<Enchantment,Integer> parseEnchantments(List<String> lines) {
        HashMap<Enchantment,Integer> map = new HashMap<Enchantment,Integer>();
        for (String text : lines) {
            String[] arr = text.split(":");
            Enchantment enchantment = Enchantment.getByName(arr[0].toUpperCase().replace(" ", "_"));
            int level = 0;
            if (arr.length > 0)
                level = Integer.parseInt(arr[1]) - 1;
            map.put(enchantment, level);
        }
        return map;
    }

    public static ItemStack parseItemStack(ConfigSection config) {
        Material material = parseEnum(Material.class, config.getAsString("item"));
        int amount = config.getInt("amount", 1);
        short data = (short) config.getInt("data", 0);

        String title = config.getString("title", null);
        List<String> lore = config.getStringList("lore");

        Color color = parseColor(config.getAsString("color"));

        Map<Enchantment, Integer> enchantments = parseEnchantments(config.getStringList("enchantments"));

        List<PotionEffect> potions = new ArrayList<PotionEffect>();
        for (ConfigSection cs : config.getSectionList("potions"))
            potions.add(parsePotion(cs));

        {
            ItemStack itemStack = new ItemStack(material, amount, (short) data);

            ItemMeta meta = itemStack.getItemMeta();

            if (title != null)
                meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', title));

            meta.setLore(lore);

            if (material.name().contains("LEATHER")) {
                LeatherArmorMeta armorMeta = (LeatherArmorMeta) meta;
                armorMeta.setColor(color);
            }

            if (material == Material.POTION) {
                PotionMeta potionMeta = (PotionMeta) meta;
                for (PotionEffect potion : potions)
                    potionMeta.addCustomEffect(potion, true);
            }

            itemStack.setItemMeta(meta);

            itemStack.addEnchantments(enchantments);

            return itemStack;
        }
    }

    public static PotionEffect parsePotion(ConfigSection config) {
        PotionEffectType type = PotionEffectType.getByName(config.getString("type").toUpperCase().replace(" ", "_"));
        int amplifier = config.getInt("amplifier", 1) - 1;
        Duration duration = parseDuration(config.getAsString("duration", "oo"));
        return new PotionEffect(type, duration.getTicks(), amplifier);
    }

//    public static Loadout parseLoadout(Match match, ConfigSection config) {
//        String id = config.getString("id");
//
//        if (config.getKeys(false).size() == 1) {
//            if (match.getModule(LoadoutsModule.class) == null)
//                throw new IllegalArgumentException("Unknown region type.");
//            return match.getModule(LoadoutsModule.class).getById(id);
//        }
//
//        Loadout loadout = new Loadout(id);
//        for (ConfigSection cs : config.getSectionList("items")) {
//            int slot = parseSlot(cs.getAsString("slot"));
//            ItemStack item = parseItemStack(cs);
//            loadout.addItem(item, slot);
//        }
//
//        for (ConfigSection cs : config.getSectionList("effects")) {
//            PotionEffect effect = parsePotion(cs);
//            loadout.addEffect(effect);
//        }
//
//        return loadout;
//    }

    public static MaterialMatcher parseMaterialMatcher(String text) {
        String[] arr = text.split(":");
        Material material = Material.matchMaterial(arr[0]);
        if (material == null)
            throw new IllegalArgumentException("Unknown material '" + arr[0] + "'");
        if (arr.length > 0) {
            try {
                return new MaterialMatcher(material, Byte.parseByte(arr[1]));
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid damage value '" + arr[1] + "'", e);
            }
        }
        return new MaterialMatcher(material);
    }

    public static Condition parseCondition(Match match, ConfigSection cs) {
        return null;
    }
}
