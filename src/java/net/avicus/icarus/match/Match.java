package net.avicus.icarus.match;

import lombok.Getter;
import lombok.ToString;
import net.avicus.icarus.module.Parser;
import net.avicus.icarus.module.Module;
import net.avicus.icarus.module.loadouts.LoadoutParser;
import net.avicus.icarus.module.map.MapParser;
import net.avicus.icarus.module.regions.RegionsParser;
import net.avicus.icarus.module.spawns.SpawnsParser;
import net.avicus.icarus.module.teams.TeamsParser;
import net.avicus.icarus.utils.config.Config;

import java.util.HashMap;

@ToString
public class Match {

    @Getter final Config config;
    @Getter final HashMap<Class<? extends Parser>,Module> modules;

    public Match(Config config) {
        this.config = config;
        this.modules = new HashMap<Class<? extends Parser>, Module>();
    }

    public void init() throws Exception {
        init(MapParser.class);
        init(RegionsParser.class);
        init(TeamsParser.class);
        init(SpawnsParser.class);
        init(LoadoutParser.class);
    }

    @SuppressWarnings("unchecked")
    public <T extends Module> T init(Class<? extends Parser<T>> parser) throws Exception {
        Module module = ((Parser) parser.newInstance()).build(this);
        if (module == null)
            return null;
        return (T) modules.put(parser, module);
    }

    public <T extends Module> T getModule(Class<T> type) {
        for (Module module : modules.values())
            if (module.getClass() == type)
                return (T) module;
        return null;
    }

}
