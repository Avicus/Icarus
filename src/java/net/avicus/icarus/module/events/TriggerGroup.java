package net.avicus.icarus.module.events;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class TriggerGroup {

    @Getter List<Trigger> conditions;

    public TriggerGroup(List<Trigger> conditions) {
        this.conditions = conditions;
    }

    public TriggerGroup() {
        this(new ArrayList<Trigger>());
    }

    public void add(Trigger condition) {
        conditions.add(condition);
    }

}
