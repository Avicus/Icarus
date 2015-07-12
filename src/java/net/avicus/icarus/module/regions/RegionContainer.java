package net.avicus.icarus.module.regions;

import org.bukkit.util.Vector;

import java.util.Random;

public interface RegionContainer {

    /**
     * Select a point randomly from the region.
     * @param random The random generator to use.
     * @return A random point in the region.
     */
    public Vector getRandom(Random random);

}
