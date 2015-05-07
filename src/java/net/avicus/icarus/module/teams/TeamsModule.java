package net.avicus.icarus.module.teams;

import lombok.ToString;
import net.avicus.icarus.module.Module;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@ToString
public class TeamsModule implements Module {

    private final HashMap<String, Team> teams;

    public TeamsModule(HashMap<String, Team> teams) {
        this.teams = teams;
        this.teams.put("spectators", new Team("Spectators", ChatColor.AQUA, Integer.MAX_VALUE));
    }

    public Collection<Team> getTeams() {
        return teams.values();
    }

    public Team getTeamById(String id) {
        return teams.get(id);
    }

    public Team getTeamByPlayer(Player player) {
        for (Team team : teams.values())
            if (team.getMembers().contains(player))
                return team;
        return null;
    }
}
