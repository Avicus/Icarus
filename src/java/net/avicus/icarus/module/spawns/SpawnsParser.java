package net.avicus.icarus.module.spawns;

import net.avicus.icarus.match.Match;
import net.avicus.icarus.module.Parser;
import net.avicus.icarus.module.ParserException;
import net.avicus.icarus.module.regions.Region;
import net.avicus.icarus.module.teams.Team;
import net.avicus.icarus.module.teams.TeamsModule;
import net.avicus.icarus.utils.ParserUtils;
import net.avicus.icarus.utils.config.ConfigSection;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class SpawnsParser implements Parser<SpawnsModule> {

    @Override
    public SpawnsModule build(Match match) throws ParserException {
        List<ConfigSection> configs = match.getConfig().getSectionList("spawns");
        List<Spawn> spawns = new ArrayList<Spawn>();

        for (ConfigSection config : configs) {
            try {
                spawns.add(parseSpawn(match, config));
            } catch (Exception ex) {
                throw new ParserException(this, "Unable to parse spawn \"" + config.getString("id") + "\".", ex);
            }
        }

        return new SpawnsModule(spawns);
    }

    public Spawn parseSpawn(Match match, ConfigSection config) {
        if (config.empty("id"))
            throw new IllegalArgumentException("Spawn ID must be specified.");
        if (config.empty("regions"))
            throw new IllegalArgumentException("Spawns must have at least one region specified.");

        String id = config.getString("id");
        Team team = match.getModule(TeamsModule.class).getTeamById(config.getString("team", "spectators"));

        if (team == null)
            throw new IllegalArgumentException("Unknown team \"" + config.getString("team") + "\".");

        List<Region> regions = new ArrayList<Region>();

        for (ConfigSection cs : config.getSectionList("regions"))
            regions.add(ParserUtils.parseRegion(match, cs));

        if (config.contains("look")) {
            Vector to = ParserUtils.parseVector(config.getString("look"));
            return new Spawn(id, team, regions, to);
        }
        else {
            float yaw = (float) config.getDouble("yaw", 0);
            float pitch = (float) config.getDouble("pitch", 0);
            return new Spawn(id, team, regions, yaw, pitch);
        }
    }

}
