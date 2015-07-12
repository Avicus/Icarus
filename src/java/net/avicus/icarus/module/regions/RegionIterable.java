package net.avicus.icarus.module.regions;

import javafx.util.Callback;
import org.bukkit.util.Vector;

public interface RegionIterable {

    public boolean iterate(Callback<Vector,Boolean> callback);

}
