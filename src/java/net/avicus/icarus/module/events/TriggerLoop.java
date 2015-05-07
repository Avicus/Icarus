package net.avicus.icarus.module.events;

import net.avicus.icarus.match.Match;
import net.avicus.icarus.module.teams.Team;
import net.avicus.icarus.module.teams.TeamsModule;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

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
                    trigger.handle(target);
                return true;
            }
        };
    }

    public static TriggerLoop newLoopTeam(Match match, Trigger trigger, final Team team) {
        return new TriggerLoop(match, trigger) {
            @Override
            public boolean handle(Object object) {
                for (Player target : team.getMembers())
                    trigger.handle(target);
                return true;
            }
        };
    }

    public static TriggerLoop newLoopAll(Match match, Trigger trigger) {
        return new TriggerLoop(match, trigger) {
            @Override
            public boolean handle(Object object) {
                for (Player player : Bukkit.getOnlinePlayers())
                    trigger.handle(player);
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
                for (Team team : match.getModule(TeamsModule.class).getTeams())
                    for (Player target : team.getMembers())
                        trigger.handle(target);
                return false;
            }
        };
    }

    protected Match match;
    protected Trigger trigger;

    public TriggerLoop(Match match, Trigger trigger) {
        this.match = match;
        this.trigger = trigger;
    }


}
