package net.avicus.icarus.module.regions;

import lombok.Getter;
import lombok.ToString;
import org.bukkit.util.Vector;

@ToString
public class RegionPoint implements Region {

    @Getter final Vector location;

    public RegionPoint(Vector vector) {
        this.location = vector;
    }

    @Override
    public boolean contains(Vector vector) {
        return location.getBlockX() == vector.getBlockX() &&
                location.getBlockY() == vector.getBlockY() &&
                location.getBlockZ() == vector.getBlockZ();
    }

}
