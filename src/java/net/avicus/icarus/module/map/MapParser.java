package net.avicus.icarus.module.map;

import net.avicus.icarus.utils.Author;
import net.avicus.icarus.utils.MapVersion;
import net.avicus.icarus.utils.config.ConfigSection;
import net.avicus.icarus.match.Match;
import net.avicus.icarus.module.Parser;
import net.avicus.icarus.module.ParserException;
import net.avicus.icarus.utils.ParsingUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MapParser implements Parser<MapModule> {

    @Override
    public MapModule build(Match match) throws ParserException {
        ConfigSection config = match.getConfig().getSection("map");

        if (config.empty("name"))
            throw new ParserException(this, "Name variable not set.");

        if (config.empty("version"))
            throw new ParserException(this, "Version variable not set.");

        if (config.empty("authors"))
            throw new ParserException(this, "At least one author is required.");

        String name = config.getString("name");
        MapVersion version = ParsingUtils.parseVersion(config.getString("version"));
        List<Author> authors = parseAuthors(config.getSectionList("authors"));
        List<Author> contributors = parseAuthors(config.getSectionList("contributors"));

        return new MapModule(name, version, authors, contributors);
    }

    public List<Author> parseAuthors(List<ConfigSection> configs) {
        if (configs == null)
            return new ArrayList<Author>();

        List<Author> list = new ArrayList<Author>();
        for (ConfigSection cs : configs) {
            UUID uuid = UUID.fromString(cs.getString("uuid"));
            String role = cs.getString("role");
            list.add(new Author(uuid, role));
        }
        return list;
    }

}
