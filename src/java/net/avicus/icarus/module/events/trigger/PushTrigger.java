package net.avicus.icarus.module.events.trigger;

import net.avicus.icarus.module.events.Trigger;
import net.avicus.icarus.module.teams.Team;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class PushTrigger extends Trigger {

    private double speed;

    public PushTrigger(Object value) throws IllegalArgumentException {
        String text = value + "";
        this.speed = Double.valueOf(text);
    }

    @Override
    public boolean handle(Object object) {
        return false;
    }

    public boolean handle(Player player) {
        Vector v = player.getLocation().getDirection();
        v.multiply(speed);
        player.setVelocity(v);
        return true;
    }

    public boolean handle(Team team) {
        for (Player t : team.getMembers())
            handle(t);
        return true;
    }

}
