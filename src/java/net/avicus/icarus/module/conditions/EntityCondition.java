package net.avicus.icarus.module.conditions;

import net.avicus.icarus.match.Match;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class EntityCondition implements Condition {
    private final EntityType entityType;

    public EntityCondition(EntityType entityType) {
        this.entityType = entityType;
    }

    @Override
    public Response query(Match match, Object object) {
        if (object instanceof EntityType)
            return Response.valueOf(object == entityType);
        else if (object instanceof Entity)
            return Response.valueOf(((Entity) object).getType() == entityType);
        return Response.ABSTAIN;
    }
}
