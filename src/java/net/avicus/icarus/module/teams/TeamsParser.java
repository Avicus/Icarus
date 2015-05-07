package net.avicus.icarus.module.teams;

import net.avicus.icarus.match.Match;
import net.avicus.icarus.module.Parser;
import net.avicus.icarus.module.ParserException;
import net.avicus.icarus.utils.ParserUtils;
import net.avicus.icarus.utils.config.ConfigSection;

import java.util.HashMap;
import java.util.List;

public class TeamsParser implements Parser<TeamsModule> {

    @Override
    public TeamsModule build(Match match) throws ParserException {
        List<ConfigSection> configs = match.getConfig().getSectionList("teams");

        HashMap<String, Team> teams = new HashMap<String, Team>();

        for (ConfigSection cs : configs) {
            try {
                teams.put(cs.getString("id"), parseTeam(cs));
            } catch (Exception e) {
                throw new ParserException(this, "Failed to parse team \"" + cs.getString("id") + "\".", e);
            }
        }

        return new TeamsModule(teams);
    }

    public Team parseTeam(ConfigSection cs) throws ParserException {
        if (cs.empty("name"))
            throw new IllegalArgumentException("Team name must be specified.");
        if (cs.empty("color"))
            throw new IllegalArgumentException("Team color must be specified.");
        if (cs.empty("max"))
            throw new IllegalArgumentException("Team max size must be specified.");
        return new Team(cs.getString("name"), ParserUtils.parseColor(cs.getString("color")), cs.getInt("max"));
    }

}
