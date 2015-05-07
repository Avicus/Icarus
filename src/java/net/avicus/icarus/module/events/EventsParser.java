package net.avicus.icarus.module.events;

import net.avicus.icarus.match.Match;
import net.avicus.icarus.module.Parser;
import net.avicus.icarus.module.ParserException;
import net.avicus.icarus.module.conditions.ConditionGroup;
import net.avicus.icarus.module.events.trigger.IcarusTrigger;
import net.avicus.icarus.module.events.trigger.PushTrigger;
import net.avicus.icarus.module.teams.Team;
import net.avicus.icarus.module.teams.TeamsModule;
import net.avicus.icarus.utils.ParserUtils;
import net.avicus.icarus.utils.config.ConfigSection;

import java.util.ArrayList;
import java.util.List;

public class EventsParser implements Parser<EventsModule> {

    @Override
    public EventsModule build(Match match) throws ParserException {
        List<ConfigSection> configs = match.getConfig().getSectionList("events");
        List<GameEvent> events = new ArrayList<GameEvent>();

        for (ConfigSection config : configs) {
            try {
                events.add(parseEvent(match, config));
            } catch (Exception ex) {
                throw new ParserException(this, "Failed to parse event.", ex);
            }
        }

        return new EventsModule(events);
    }

    public GameEvent parseEvent(Match match,ConfigSection config) throws Exception {
        GameEventType type = GameEventType.valueOf(config.getString("type").replace(" ", "_").replace("-", "_").toUpperCase());
        ConditionGroup conditions = new ConditionGroup();

        if (config.contains("conditions"))
            for (ConfigSection cs : config.getSectionList("conditions"))
                conditions.add(ParserUtils.parseCondition(match, cs));

        TriggerGroup triggers = new TriggerGroup();
        for (ConfigSection cs : config.getSectionList("trigger"))
            triggers.add(parseTrigger(match, cs));

        return new GameEvent(type, conditions, triggers);
    }

    public Trigger parseTrigger(Match match, ConfigSection config) throws Exception {
        String[] keys = config.getKeys().toArray(new String[config.getKeys().size()]);

        String type = keys[0];
        boolean loop = false;
        Trigger trigger = null;

        if (type.equals("loop")) {
            type = keys[1];
            loop = true;
        }

        Object value = config.get(type);

        if (type.equals("push"))
            trigger = new PushTrigger(value);
        else if (type.equals("icarus"))
            trigger = new IcarusTrigger(value);

        if (loop) {
            String loopType = config.getString("loop").toLowerCase();
            if (loopType.equals("team"))
                return TriggerLoop.newLoopTeam(match, trigger);
            else if (loopType.equals("all"))
                return TriggerLoop.newLoopAll(match, trigger);
            else {
                try {
                    int radius = Integer.parseInt(loopType);
                    return TriggerLoop.newLoopRadius(match, trigger, radius);
                } catch (Exception ex) {
                    // fail, what is this loop type?
                }
            }
            Team testTeam = match.getModule(TeamsModule.class).getTeamById(loopType);
            if (testTeam != null)
                return TriggerLoop.newLoopTeam(match, trigger, testTeam);
            throw new IllegalArgumentException("Unknown loop type \"" + loopType + "\".");
        }

        return trigger;
    }

}
