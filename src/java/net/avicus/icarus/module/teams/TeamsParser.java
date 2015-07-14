package net.avicus.icarus.module.teams;

import net.avicus.icarus.match.Match;
import net.avicus.icarus.module.Parser;
import net.avicus.icarus.module.ParserException;
import net.avicus.icarus.utils.ParserUtils;
import net.avicus.icarus.utils.config.ConfigSection;

import java.util.HashMap;
import java.util.List;

public class TeamsParser implements Parser<TeamsModule> {

    HashMap<String, Team> teams = new HashMap<String, Team>();

    @Override
    public TeamsModule build(Match match) throws ParserException {
        List<ConfigSection> configs = match.getConfig().getSectionList("teams");

        for (ConfigSection cs : configs) {
            try {
                teams.put(createIdIfAbsent(cs), parseTeam(cs));
            } catch (Exception e) {
                throw new ParserException(this, "Failed to parse team '" + cs.getString("id") + "'.", e);
            }
        }

        return new TeamsModule(teams);
    }

    public String createIdIfAbsent(ConfigSection cs) throws ParserException {
        if (cs.contains("id")  && !this.teams.containsKey(cs.getAsString("id"))) return cs.getAsString("id");
        if (cs.contains("name") && !this.teams.containsKey(cs.getAsString("name"))) return cs.getAsString("name").toLowerCase().split(" ")[0]; // "Red Team" => "red"
        else throw new ParserException(this, "Team name must be specified. This can also happen if a team is declared more than once."); // The team doesn't even have a name, so an error about an id is irrelevant.
    }

    public Team parseTeam(ConfigSection cs) throws ParserException {
        if (cs.empty("name"))
            throw new IllegalArgumentException("Team name must be specified.");
        if (cs.empty("color"))
            throw new IllegalArgumentException("Team color must be specified.");
        if (cs.empty("max"))
            throw new IllegalArgumentException("Team max size must be specified.");
        return new Team(cs.getString("name"), ParserUtils.parseChatColor(cs.getString("color")), cs.getInt("max"));
    }

}
