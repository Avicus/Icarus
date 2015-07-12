package net.avicus.icarus.module.regions;

import lombok.Getter;
import lombok.ToString;
import org.bukkit.util.Vector;

@ToString
public class RegionTranslate implements Region {

    @Getter final Region child;
    @Getter final Vector translation;

    public RegionTranslate(Region child, Vector translation) {
        this.child = child;
        this.translation = translation;
    }

    @Override
    public boolean contains(Vector vector) {
        return child.contains(vector.clone().subtract(translation));
    }
}
