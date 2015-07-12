package net.avicus.icarus.module.regions;

import javafx.util.Callback;
import lombok.Getter;
import lombok.ToString;
import org.bukkit.util.Vector;

import java.util.Random;

@ToString
public class RegionCuboid implements Region,RegionIterable,RegionContainer{

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

    @Override
    public boolean iterate(Callback<Vector, Boolean> callback) {
        for (double x = min.getX(); x <= max.getX(); x++) {
            for (double y = min.getY(); y <= max.getY(); y++) {
                for (double z = min.getZ(); z <= max.getZ(); z++) {
                    if (!callback.call(new Vector(x, y, z)))
                        return false;
                }
            }
        }
        return true;
    }

    @Override
    public Vector getRandom(Random random) {
        int x = between(random, min.getBlockX(), max.getBlockX());
        int y = between(random, min.getBlockY(), max.getBlockY());
        int z = between(random, min.getBlockZ(), max.getBlockZ());
        return new Vector(x, y, z);
    }

    private int between(Random random, int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }
}
