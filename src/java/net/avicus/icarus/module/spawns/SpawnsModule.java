package net.avicus.icarus.module.spawns;

import lombok.Getter;
import lombok.ToString;
import net.avicus.icarus.module.Module;

import java.util.List;

@ToString
public class SpawnsModule implements Module {

    @Getter List<Spawn> spawns;

    public SpawnsModule(List<Spawn> spawns) {
        this.spawns = spawns;
    }

}
