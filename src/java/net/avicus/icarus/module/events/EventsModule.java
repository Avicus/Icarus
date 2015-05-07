package net.avicus.icarus.module.events;

import lombok.Getter;
import net.avicus.icarus.module.Module;

import java.util.List;

public class EventsModule implements Module {

    @Getter List<GameEvent> events;

    public EventsModule(List<GameEvent> events) {
        this.events = events;
    }

}
