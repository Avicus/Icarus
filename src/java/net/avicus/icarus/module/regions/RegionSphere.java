package net.avicus.icarus.module.regions;

import lombok.Getter;
import lombok.ToString;
import org.bukkit.util.Vector;

@ToString
public class RegionSphere implements Region {

    @Getter final Vector origin;
    @Getter final int radius;
    @Getter final int radiusSquared;

    public RegionSphere(Vector origin, int radius) {
        this.origin = origin;
        this.radius = radius;
        this.radiusSquared = (int) Math.pow(radius, 2);
    }

    @Override
    public boolean contains(Vector point) {
        return origin.distanceSquared(point) <= radiusSquared;
    }

}
