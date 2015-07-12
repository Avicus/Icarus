package net.avicus.icarus.module.regions;

import lombok.Getter;
import lombok.ToString;
import net.avicus.icarus.module.Module;

import java.util.HashMap;

@ToString
public class RegionsModule implements Module {
    @Getter final HashMap<String, Region> regions;

    public RegionsModule(HashMap<String, Region> regions) {
        this.regions = regions;
    }

    public Region getRegionById(String id) {
        return regions.get(id);
    }
}