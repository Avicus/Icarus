package net.avicus.icarus.module.conditions;

import net.avicus.icarus.match.Match;
import net.avicus.icarus.module.teams.Team;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.material.MaterialData;

public class TeamCondition implements Condition {
    private final Team team;

    public TeamCondition(Team team) {
        this.team = team;
    }

    @Override
    public Response query(Match match, Object object) {
        if (object instanceof Team)
            return Response.valueOf(object == team);
        return Response.ABSTAIN;
    }
}
