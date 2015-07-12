package net.avicus.icarus.match;

import lombok.Getter;
import net.avicus.icarus.module.Module;
import net.avicus.icarus.module.Parser;
import net.avicus.icarus.module.events.EventsParser;
import net.avicus.icarus.module.map.MapParser;
import net.avicus.icarus.module.regions.RegionsParser;
import net.avicus.icarus.module.spawns.SpawnsParser;
import net.avicus.icarus.module.teams.TeamsParser;
import net.avicus.icarus.utils.config.Config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Match {

    private static int last_id = 0;

    @Getter int id;
    @Getter final Config config;
    @Getter final List<Module> modules;

    public Match(Config config) {
        this.id = last_id++;
        this.config = config;
        this.modules = new ArrayList<Module>();
    }

    public void init() throws Exception {
        init(MapParser.class);
        init(RegionsParser.class);
        init(TeamsParser.class);
        init(SpawnsParser.class);
        init(EventsParser.class);
    }

    @SuppressWarnings("unchecked")
    public <T extends Module> T init(Class<? extends Parser<T>> parser) throws Exception {
        Module module = ((Parser) parser.newInstance()).build(this);
        if (module == null)
            return null;
        modules.add(module);
        return (T) module;
    }

    @SuppressWarnings("unchecked")
    public <T extends Module> T getModule(Class<T> type) {
        for (Module module : modules)
            if (module.getClass() == type)
                return (T) module;
        return null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Match #").append(id).append(" (");
        for (Module module : modules)
            sb.append("\n").append("» ").append(module.toString());
        sb.append(")");
        return sb.toString();
    }

}
