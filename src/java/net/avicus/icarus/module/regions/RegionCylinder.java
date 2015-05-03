package net.avicus.icarus.module.regions;

import lombok.Getter;
import lombok.ToString;
import org.bukkit.util.Vector;

@ToString
public class RegionCylinder implements Region {

    @Getter final Vector base;
    @Getter final int radius;
    @Getter final int radiusSquared;
    @Getter final int height;

    public RegionCylinder(Vector base, int radius, int height) {
        this.base = base;
        this.radius = radius;
        this.radiusSquared = (int) Math.pow(radius, 2);
        this.height = height;
    }

    @Override
    public boolean contains(Vector point) {
        if (point.getY() < base.getY() || point.getY() > base.getY() + height)
            return false;
        return Math.pow(point.getX() - base.getX(), 2) + Math.pow(point.getZ() - base.getZ(), 2) < radiusSquared;
    }

}
