package net.avicus.icarus.utils;

import lombok.Getter;
import lombok.ToString;

import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.UUID;

@ToString
public class Author {
    @Getter String username;
    @Getter String role;

    @Deprecated
    public Author(String username) {
        this.username = username;
        this.role = null;
    }

    public Author(UUID uuid, String role) {
        // http://mcuuid.com/api/[username|uuid]/raw
        try {
            this.username = username(uuid);
        } catch (Exception e) {
            this.username = "[unknown]";
        }
        this.role = role;
    }

    @Deprecated
    public Author(String username, String role) {
        this.username = username;
        this.role = role;
    }

    private String username(UUID uuid) throws Exception {
        InputStream input = new URL("http://mcuuid.com/api/" + uuid + "/raw").openStream();
        Scanner scan = new Scanner(input);
        return scan.nextLine();
    }
}
