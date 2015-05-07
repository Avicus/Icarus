package net.avicus.icarus.module.loadouts;

import lombok.Getter;
import org.bukkit.potion.PotionEffect;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

public class Loadout {

    @Getter final Map<String, ItemStack> items;
    @Getter final List<PotionEffect> effects;

    public Loadout(Map<String, ItemStack> items, List<PotionEffect> effects) {
        this.items = items;
        this.effects = effects;
    }

}