package net.avicus.icarus.module.teams;

import lombok.Getter;
import lombok.ToString;
import net.avicus.icarus.module.Module;
import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.List;

@ToString
public class TeamsModule implements Module {

    @Getter HashMap<String, Team> teams;

    public TeamsModule(HashMap<String, Team> teams) {
        this.teams = teams;
        this.teams.put("spectators", new Team("Spectators", ChatColor.AQUA, Integer.MAX_VALUE));
    }

    public Team getTeamById(String id) {
        return teams.get(id);
    }

}
