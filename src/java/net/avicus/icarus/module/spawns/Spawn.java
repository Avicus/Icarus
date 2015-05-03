package net.avicus.icarus.module.spawns;

import lombok.Getter;
import lombok.ToString;
import net.avicus.icarus.module.regions.Region;
import net.avicus.icarus.module.teams.Team;
import org.bukkit.util.Vector;

import java.util.List;

@ToString
public class Spawn {

    @Getter final String id;
    @Getter final Team team;
    @Getter final List<Region> regions;
    @Getter int yaw = 0;
    @Getter int pitch = 0;
    @Getter Vector look;

    public Spawn(String id, Team team, List<Region> regions, int yaw, int pitch) {
        this.id = id;
        this.team = team;
        this.regions = regions;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public Spawn(String id, Team team, List<Region> regions, Vector look) {
        this.id = id;
        this.team = team;
        this.regions = regions;
        this.look = look;
    }

    public float getYaw(Vector from) {
        if (look == null)
            return yaw;

        double dx = look.getX() - from.getX();
        double dz = look.getZ() - from.getZ();
        return (float) Math.toDegrees(Math.atan2(-dx, dz));
    }

    public double getPitch(Vector from) {
        if (look == null)
            return pitch;

        double dx = look.getX() - from.getX();
        double dz = look.getZ() - from.getZ();
        double dy = look.getY() - from.getY() - 1.62;

        double distance = Math.sqrt(dx * dx + dz * dz);
        return (float) Math.toDegrees(Math.atan2(-dy, distance));
    }

}
