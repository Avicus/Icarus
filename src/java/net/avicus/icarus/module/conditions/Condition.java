package net.avicus.icarus.module.conditions;

import net.avicus.icarus.match.Match;

public interface Condition {

    public Response query(Match match, Object object);

}
