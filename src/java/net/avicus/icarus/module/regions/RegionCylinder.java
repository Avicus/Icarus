package net.avicus.icarus.module.regions;

import javafx.util.Callback;
import lombok.Getter;
import lombok.ToString;
import org.bukkit.util.Vector;

import java.util.Random;

@ToString
public class RegionCylinder implements Region,RegionIterable,RegionContainer {

    @Getter final Vector base;
    @Getter final int radius;
    @Getter final int radiusSquared;
    @Getter final int height;
    @Getter final boolean hollow;

    public RegionCylinder(Vector base, int radius, int height, boolean hollow) {
        this.base = base;
        this.radius = radius;
        this.radiusSquared = (int) Math.pow(radius, 2);
        this.height = height;
        this.hollow = hollow;
    }

    @Override
    public boolean contains(Vector point) {
        if (point.getY() < base.getY() || point.getY() > base.getY() + height)
            return false;
        return Math.pow(point.getX() - base.getX(), 2) + Math.pow(point.getZ() - base.getZ(), 2) < radiusSquared;
    }

    @Override
    public Vector getRandom(Random random) {
        double angle = 2 * Math.PI * random.nextDouble();
        double hypotenuse = random.nextDouble() * radius;
        double x = Math.cos(angle) * hypotenuse + base.getX();
        double z = Math.sin(angle) * hypotenuse + base.getZ();
        double y = base.getY() + height * random.nextDouble();
        return new Vector(x, y, z);
    }

    @Override
    public boolean iterate(Callback<Vector, Boolean> callback) {
        double cx = base.getX();
        double cy = base.getY();
        double cz = base.getZ();

        // Form cube around center
        for (double x = cx - radius; x <= cx + radius; x++) {
            for (double z = cz - radius; z <= cz + radius; z++) {
                for (double y = cy; y < cy + height; y++) {
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z);
                    if (dist < radius * radius && !(hollow && dist < (radius - 1) * (radius - 1))) {
                        if (y > 256 || y < 0)
                            continue;

                        if (!callback.call(new Vector(x, y, z)))
                            return false;
                    }
                }
            }
        }
        return true;
    }
}
