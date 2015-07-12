package net.avicus.icarus.module.regions;

import lombok.Getter;
import lombok.ToString;
import net.avicus.icarus.utils.MaterialMatcher;
import org.bukkit.World;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ToString
public class RegionFill implements Region,RegionDynamic {

    private List<Vector> points;

    @Getter final Vector min;
    @Getter final Vector max;
    @Getter final Vector base;
    @Getter final int height;
    @Getter final MaterialMatcher matcher;

    public RegionFill(Vector a, Vector b, Vector base, int height, MaterialMatcher matcher) {
        this.min = Vector.getMinimum(a, b);
        this.max = Vector.getMaximum(a, b);
        this.base = base;
        this.height = height;
        this.matcher = matcher;
    }

    @Override
    public void preload(World world) {
        points = new ArrayList<Vector>();
        for (int y = base.getBlockY(); y < base.getBlockY() + height - 1; y++) {
            Vector pos = base.clone().setY(y);
            search(world, pos);
        }
    }

    private void search(World world, Vector point) {
        List<Vector> around = Arrays.asList(
                point.clone().add(new Vector(1, 0, 0)),
                point.clone().add(new Vector(-1, 0, 0)),
                point.clone().add(new Vector(0, 0, 1)),
                point.clone().add(new Vector(0, 0, -1))
        );

        for (Vector v : around) {
            if (!isInsideBounds(v))
                continue;
            if (!matcher.matches(v.toLocation(world).getBlock().getState()))
                continue;
            if (points.contains(v))
                continue;
            search(world, v);
        }
    }

    private boolean isInsideBounds(Vector vector) {
        return vector.isInAABB(min, max);
    }

    @Override
    public boolean contains(Vector vector) {
        if (!isInsideBounds(vector))
            return false;

        return points.contains(vector);
    }

}
