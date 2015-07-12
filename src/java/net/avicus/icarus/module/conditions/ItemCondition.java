package net.avicus.icarus.module.conditions;

import net.avicus.icarus.match.Match;
import net.avicus.icarus.utils.MaterialMatcher;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.material.MaterialData;

public class ItemCondition implements Condition {
    private final MaterialMatcher matcher;

    public ItemCondition(MaterialMatcher matcher) {
        this.matcher = matcher;
    }

    @Override
    public Response query(Match match, Object object) {
        if (object instanceof Block)
            return Response.valueOf(matcher.matches(((Block) object).getType(), ((Block) object).getData()));
        else if (object instanceof BlockState)
            return Response.valueOf(matcher.matches((BlockState) object));
        else if (object instanceof MaterialData)
            return Response.valueOf(matcher.matches((MaterialData) object));
        else if (object instanceof Material)
            return Response.valueOf(matcher.matches((Material) object, (byte) 0));
        return Response.ABSTAIN;
    }
}
