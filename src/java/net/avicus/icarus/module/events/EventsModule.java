package net.avicus.icarus.module.events;

import lombok.Getter;
import lombok.ToString;
import net.avicus.icarus.module.Module;

import java.util.List;

public class EventsModule implements Module {

    @Getter List<GameEvent> events;

    public EventsModule(List<GameEvent> events) {
        this.events = events;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("RegionsModule").append("\n");
        for (GameEvent event : events)
            sb.append("\t").append(event.toString()).append("\n");
        return sb.toString();
    }

}
