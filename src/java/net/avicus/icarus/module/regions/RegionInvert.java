package net.avicus.icarus.module.regions;

import lombok.Getter;
import lombok.ToString;
import org.bukkit.util.Vector;

import java.util.List;

@ToString
public class RegionInvert implements Region {

    @Getter final List<Region> inverted;

    public RegionInvert(List<Region> inverted) {
        this.inverted = inverted;
    }

    @Override
    public boolean contains(Vector point) {
        for (Region region : inverted)
            if (region.contains(point))
                return false;
        return true;
    }
}
