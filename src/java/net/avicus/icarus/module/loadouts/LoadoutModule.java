package net.avicus.icarus.module.loadouts;

import lombok.Getter;
import net.avicus.icarus.module.Module;

import java.util.Map;

public class LoadoutModule implements Module {

    @Getter Map<String, Loadout> loadouts;

    public LoadoutModule(Map<String, Loadout> loadouts) {
        this.loadouts = loadouts;
    }

}
