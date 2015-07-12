package net.avicus.icarus.module.regions;

import javafx.util.Callback;
import lombok.Getter;
import lombok.ToString;
import org.bukkit.util.Vector;

import java.util.Random;

@ToString
public class RegionSphere implements Region,RegionIterable,RegionContainer {

    @Getter final Vector origin;
    @Getter final int radius;
    @Getter final int radiusSquared;
    @Getter final boolean hollow;

    public RegionSphere(Vector origin, int radius, boolean hollow) {
        this.origin = origin;
        this.radius = radius;
        this.radiusSquared = (int) Math.pow(radius, 2);
        this.hollow = hollow;
    }

    @Override
    public boolean contains(Vector point) {
        if (hollow) {
            double distance = origin.distance(point);
            return distance <= radius && distance >= radius - 1;
        }
        return origin.distanceSquared(point) <= radiusSquared;
    }

    @Override
    public Vector getRandom(Random random) {
        // http://mathworld.wolfram.com/SpherePointPicking.html
        double u = random.nextDouble();
        double v = random.nextDouble();
        double theta = 2 * Math.PI * u;
        double phi = Math.acos(2 * v - 1);
        double x = origin.getX() + (radius * Math.sin(phi) * Math.cos(theta));
        double y = origin.getY() + (radius * Math.sin(phi) * Math.sin(theta));
        double z = origin.getZ() + radius * Math.cos(phi);
        return new Vector(x, y, z);
    }

    @Override
    public boolean iterate(Callback<Vector, Boolean> callback) {
        double cx = origin.getX();
        double cy = origin.getY();
        double cz = origin.getZ();
        int r = radius;

        // Form cube around center
        for (double x = cx - r; x <= cx + r; x++) {
            for (double z = cz - r; z <= cz + r; z++) {
                for (double y = cy - r; y < cy + r; y++) {
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (cy - y) * (cy - y);
                    if (dist < r * r && !(hollow && dist < (r - 1) * (r - 1))) {
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
