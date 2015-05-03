package net.avicus.icarus.utils;

import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@ToString
public class Author {

    @Getter final UUID uuid;
    @Getter final String role;

    public Author(UUID uuid, String role) {
        this.uuid = uuid;
        this.role = role;
    }

}
