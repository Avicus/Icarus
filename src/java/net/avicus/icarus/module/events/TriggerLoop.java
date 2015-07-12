package net.avicus.icarus.module.events;

import lombok.ToString;
import net.avicus.icarus.match.Match;
import net.avicus.icarus.module.teams.Team;
import net.avicus.icarus.module.teams.TeamsModule;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@ToString(exclude = "match")
public abstract class TriggerLoop extends Trigger {

    public static TriggerLoop newLoopTeam(Match match, Trigger trigger) {
        return new TriggerLoop(match, trigger) {
            @Override
            public boolean handle(Object object) {
                return false;
            }

            public boolean handle(Player player) {
                Team team = match.getModule(TeamsModule.class).getTeamByPlayer(player);
                for (Player target : team.getMembers())
                    child.handle(target);
                return true;
            }
        };
    }

    public static TriggerLoop newLoopTeam(Match match, Trigger trigger, final Team team) {
        return new TriggerLoop(match, trigger) {
            @Override
            public boolean handle(Object object) {
                for (Player target : team.getMembers())
                    child.handle(target);
                return true;
            }
        };
    }

    public static TriggerLoop newLoopAll(Match match, Trigger trigger) {
        return new TriggerLoop(match, trigger) {
            @Override
            public boolean handle(Object object) {
                for (Player player : Bukkit.getOnlinePlayers())
                    child.handle(player);
                return true;
            }
        };
    }

    public static Trigger newLoopRadius(Match match, Trigger trigger, int radius) {
        final int radiusSq = (int) Math.pow(radius, 2);
        return new TriggerLoop(match, trigger) {
            @Override
            public boolean handle(Object object) {
                return false;
            }
            public boolean handle(Player player) {
                for (Player target : Bukkit.getOnlinePlayers())
                    if (target.getLocation().distanceSquared(player.getLocation()) < radiusSq)
                        return true;
                    child.handle(player);
                return false;
            }
        };
    }

    protected Match match;
    protected Trigger child;

    public TriggerLoop(Match match, Trigger child) {
        this.match = match;
        this.child = child;
    }


}
