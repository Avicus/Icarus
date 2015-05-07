package net.avicus.icarus.module.conditions;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class ConditionGroup {

    @Getter List<Condition> conditions;

    public ConditionGroup(List<Condition> conditions) {
        this.conditions = conditions;
    }

    public ConditionGroup() {
        this(new ArrayList<Condition>());
    }

    public void add(Condition condition) {
        conditions.add(condition);
    }

}
