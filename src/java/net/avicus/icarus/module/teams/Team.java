package net.avicus.icarus.module.teams;

import lombok.Getter;
import lombok.ToString;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@ToString
public class Team {

    @Getter final List<Player> members = new ArrayList<Player>();
    @Getter final String name;
    @Getter final ChatColor color;
    @Getter final int max;

    public Team(String name, ChatColor color, int max) {
        this.name = name;
        this.color = color;
        this.max = max;
    }
    
}
