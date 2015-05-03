package net.avicus.icarus.utils;

import lombok.Getter;

public class MapVersion {

    @Getter final int major;
    @Getter final int minor;
    @Getter int patch;

    public MapVersion(int major, int minor, int patch) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
    }

    @Override
    public String toString() {
        return major + "." + minor + "." + patch;
    }

}
