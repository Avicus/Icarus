package net.avicus.icarus.module.loadouts;

import net.avicus.icarus.match.Match;
import net.avicus.icarus.module.Parser;
import net.avicus.icarus.module.ParserException;
import net.avicus.icarus.utils.ParsingUtils;
import net.avicus.icarus.utils.config.ConfigSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoadoutParser implements Parser<LoadoutModule> {

    public LoadoutModule build(Match match) throws ParserException {
        List<ConfigSection> configs = match.getConfig().getSectionList("loadouts");
        Map<String, Loadout> loadouts = new HashMap<String, Loadout>();
        for (ConfigSection cs : configs) {
            try {
                loadouts.put(cs.getString("id"), parseLoadout(match, cs));
            } catch (Exception e) {
                throw new ParserException(this, "Failed to parse loadout \"" + cs.getString("id") + "\".", e);
            }
        }
        return new LoadoutModule(loadouts);
    }

    private Loadout parseLoadout(Match match, ConfigSection cs) throws ParserException {
        List<ConfigSection> configItems = cs.getSectionList("items");
        List<String> configEffects = cs.getStringList("effects");
        Map<String, ItemStack> items = new HashMap<String, ItemStack>();
        List<PotionEffect> effects = new ArrayList<PotionEffect>();

        for (ConfigSection config : configItems) {
            if (config.empty("slot"))
                throw new ParserException(this, "Loadout slot must be specified");
            if (config.empty("item"))
                throw new ParserException(this, "Loadout item must be specified");

            items.put(config.getString("slot"), ParsingUtils.parseItem(match, config));
        }

        for (String effect : configEffects) {
            PotionEffectType type = PotionEffectType.getByName(effect.split(":")[0].toUpperCase().replace(" ", "_"));
            int time = Integer.parseInt(effect.split(":")[1]);
            int power = 1;
            if (effect.contains(",")) {
                power = Integer.parseInt(effect.split(",")[1]);
            }
            effects.add(new PotionEffect(type, time, power));
        }
        return new Loadout(items, effects);
    }

}
