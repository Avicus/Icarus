package net.avicus.icarus.module.map;

import lombok.Getter;
import lombok.ToString;
import net.avicus.icarus.utils.Author;
import net.avicus.icarus.utils.MapVersion;
import net.avicus.icarus.module.Module;

import java.util.List;

@ToString
public class MapModule implements Module {

    @Getter final String name;
    @Getter final MapVersion version;
    @Getter final List<Author> authors;
    @Getter final List<Author> contributors;

    public MapModule(String name, MapVersion version, List<Author> authors, List<Author> contributors) {
        this.name = name;
        this.version = version;
        this.authors = authors;
        this.contributors = contributors;
    }

}
