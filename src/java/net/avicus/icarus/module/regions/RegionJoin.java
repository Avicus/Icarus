package net.avicus.icarus.module.regions;

import lombok.Getter;
import lombok.ToString;
import org.bukkit.util.Vector;

import java.util.List;

@ToString
public class RegionJoin implements Region {

    @Getter final List<Region> joined;

    public RegionJoin(List<Region> joined) {
        this.joined = joined;
    }

    @Override
    public boolean contains(Vector point) {
        for (Region region : joined)
            if (region.contains(point))
                return true;
        return false;
    }

}
