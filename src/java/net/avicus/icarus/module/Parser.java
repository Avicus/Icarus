package net.avicus.icarus.module;

import net.avicus.icarus.match.Match;

public interface Parser<M extends Module> {

    public M build(Match match) throws ParserException;

}
