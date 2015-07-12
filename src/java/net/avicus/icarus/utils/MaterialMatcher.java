package net.avicus.icarus.utils;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.material.MaterialData;

public class MaterialMatcher {

    @Getter final Material material;
    @Getter final byte data;
    @Getter final boolean dataRelevant;

    public MaterialMatcher(Material material) {
        this(material, (byte) 0, false);
    }

    public MaterialMatcher(Material material, byte data) {
        this(material, data, true);
    }

    private MaterialMatcher(Material material, byte data, boolean dataRelevant) {
        this.material = material;
        this.data = data;
        this.dataRelevant = dataRelevant;
    }

    public boolean matches(Material material, byte data) {
        if (this.material != material)
            return false;
        if (this.dataRelevant && this.data != data)
            return false;
        return true;
    }

    public boolean matches(MaterialData materialData) {
        return matches(materialData.getItemType(), materialData.getData());
    }

    public boolean matches(BlockState state) {
        return matches(state.getData());
    }

}
