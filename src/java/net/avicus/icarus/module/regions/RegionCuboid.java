package net.avicus.icarus.module.regions;

import lombok.Getter;
import org.bukkit.util.Vector;

public class RegionCuboid implements Region {

    @Getter final Vector min;
    @Getter final Vector max;

    public RegionCuboid(Vector a, Vector b) {
        this.min = Vector.getMinimum(a, b);
        this.max = Vector.getMaximum(a, b);
    }

    @Override
    public boolean contains(Vector vector) {
        return vector.isInAABB(min, max);
    }
}
