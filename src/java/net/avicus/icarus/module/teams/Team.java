package net.avicus.icarus.module.teams;

import lombok.Getter;
import lombok.ToString;
import org.bukkit.ChatColor;

@ToString
public class Team {

    @Getter final String name;
    @Getter final ChatColor color;
    @Getter final int max;

    public Team(String name, ChatColor color, int max) {
        this.name = name;
        this.color = color;
        this.max = max;
    }
    
}
