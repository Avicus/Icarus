package net.avicus.icarus.module.regions;

import lombok.ToString;
import org.bukkit.util.Vector;

@ToString
public class RegionGlobal implements Region {
    @Override
    public boolean contains(Vector vector) {
        return true;
    }

}
