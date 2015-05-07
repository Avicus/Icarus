package net.avicus.icarus.module.events;

import lombok.Getter;
import net.avicus.icarus.module.conditions.ConditionGroup;

public class GameEvent {

    @Getter final GameEventType type;
    @Getter final ConditionGroup conditions;
    @Getter final TriggerGroup triggers;

    public GameEvent(GameEventType type, ConditionGroup conditions, TriggerGroup triggers) {
        this.type = type;
        this.conditions = conditions;
        this.triggers = triggers;
    }

}
