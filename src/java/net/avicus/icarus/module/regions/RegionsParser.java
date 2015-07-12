package net.avicus.icarus.module.regions;

import net.avicus.icarus.utils.ParserUtils;
import net.avicus.icarus.utils.config.ConfigSection;
import net.avicus.icarus.match.Match;
import net.avicus.icarus.module.Parser;
import net.avicus.icarus.module.ParserException;

import java.util.HashMap;

public class RegionsParser implements Parser<RegionsModule> {

    @Override
    public RegionsModule build(Match match) throws ParserException {
        HashMap<String, Region> regions = new HashMap<String, Region>();

        for (ConfigSection cs : match.getConfig().getSectionList("regions")) {
            if (cs.empty("id"))
                throw new ParserException(this, "Region ID must be specified.");

            String id = cs.getString("id");

            if (regions.containsKey(id))
                throw new ParserException(this, "Duplicate region ID used '" + id + "'.");

            try {
                regions.put(id, ParserUtils.parseRegion(match, cs));
            } catch (Exception e) {
                throw new ParserException(this, "Failed to parse region '" + id + "'.", e);
            }
        }

        return new RegionsModule(regions);
    }

}
