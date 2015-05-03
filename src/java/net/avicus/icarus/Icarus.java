package net.avicus.icarus;

import net.avicus.icarus.utils.config.Config;
import net.avicus.icarus.match.Match;
import org.bukkit.plugin.java.JavaPlugin;

public class Icarus extends JavaPlugin {

    public static void main(String[] args) {
        Match match = new Match(new Config(Icarus.class.getResourceAsStream("map.yml")));
        try {
            match.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(match);
    }

    public void onEnable() {

    }

    public void onDisable() {

    }

}
