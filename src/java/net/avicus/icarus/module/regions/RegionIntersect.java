package net.avicus.icarus.module.regions;

import lombok.Getter;
import lombok.ToString;
import org.bukkit.util.Vector;

import java.util.List;

@ToString
public class RegionIntersect implements Region {

    @Getter final List<Region> regions;

    public RegionIntersect(List<Region> regions) {
        this.regions = regions;
    }

    @Override
    public boolean contains(Vector point) {
        for (Region region : regions)
            if (!region.contains(point))
                return false;
        return true;
    }
}
