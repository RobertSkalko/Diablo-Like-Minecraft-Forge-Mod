package com.robertx22.mine_and_slash.database.runes.base;

import com.google.gson.JsonObject;
import com.robertx22.mine_and_slash.data_generation.JsonUtils;
import com.robertx22.mine_and_slash.data_generation.runes.SerializedRune;
import com.robertx22.mine_and_slash.database.rarities.RuneRarity;
import com.robertx22.mine_and_slash.database.stats.StatMod;
import com.robertx22.mine_and_slash.database.stats.mods.generated.*;
import com.robertx22.mine_and_slash.db_lists.Rarities;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.onevent.data_gen.ISerializable;
import com.robertx22.mine_and_slash.onevent.data_gen.ISerializedRegistryEntry;
import com.robertx22.mine_and_slash.registry.SlashRegistryType;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.Rarity;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.interfaces.IWeighted;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public abstract class BaseRune implements IWeighted,
    ISerializedRegistryEntry<BaseRune>, ISerializable<BaseRune> {

    public boolean isUnique = false;
    public HashMap<Integer, RuneItem> itemMap = new HashMap<>();

    public BaseRune(int rarity) {

        if (this instanceof BaseUniqueRune) {
            itemMap.put(IRarity.Unique, genItemForRegistration(IRarity.Unique));
        } else {
            for (RuneRarity rar : Rarities.Runes.getNormalRarities()) {
                itemMap.put(rar.Rank(), genItemForRegistration(rar.Rank()));
            }
        }
    }

    public BaseRune(boolean isUnique) {
        this.isUnique = isUnique;
    }

    @Override
    public boolean isUnique() {
        return this.isUnique;
    }

    @Override
    public boolean isFromDatapack() {
        return true;
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = getDefaultJson();

        json.addProperty("is_unique", isUnique);

        JsonUtils.addStatMods(weaponStat(), json, "possible_weapon_stats");
        JsonUtils.addStatMods(armorStat(), json, "possible_armor_stats");
        JsonUtils.addStatMods(jewerlyStat(), json, "possible_jewerly_stats");

        JsonObject map = new JsonObject();
        itemMap.entrySet()
            .forEach(x -> {
                map.addProperty(x.getKey() + "", x.getValue()
                    .getRegistryName()
                    .toString());
            });

        json.add("item_id_per_rarity", map);

        return json;
    }

    @Override
    public SerializedRune fromJson(JsonObject json) {
        String guid = getGUIDFromJson(json);
        int tier = getTierFromJson(json);
        int weight = getWeightFromJson(json);
        int rarity = getRarityFromJson(json);

        Boolean isUnique = json.get("is_unique")
            .getAsBoolean();

        List<StatMod> weapon = JsonUtils.getStatMods(json, "possible_weapon_stats");
        List<StatMod> armor = JsonUtils.getStatMods(json, "possible_armor_stats");
        List<StatMod> jewerly = JsonUtils.getStatMods(json, "possible_jewerly_stats");

        HashMap<Integer, RuneItem> itemMap = new HashMap<>();
        JsonObject map = json.getAsJsonObject("item_id_per_rarity");
        for (RuneRarity rar : Rarities.Runes.getAllRarities()) {
            String id = rar.Rank() + "";
            if (map.has(id)) {

                String itemid = map.get(id)
                    .getAsString();
                Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemid));

                itemMap.put(rar.Rank(), (RuneItem) item);

            }
        }

        return new SerializedRune(isUnique, guid, weight, tier, weapon, armor, jewerly, itemMap);
    }

    public RuneItem genItemForRegistration(int rarity) {
        return (RuneItem) new RuneItem(this, rarity).setRegistryName(Ref.MODID, genRegistryName(rarity));
    }

    public RuneItem getItemFromRegistry(int rarity) {
        return itemMap.get(rarity);
    }

    public final RuneItem byRarityItem(int rar) {
        return (RuneItem) itemMap.get(rar);
    }

    public String genRegistryName(int rarity) {
        return "runes/" + GUID() + "/" + rarity;
    }

    @Override
    public SlashRegistryType getSlashRegistryType() {
        return SlashRegistryType.RUNE;
    }

    @Override
    public int getRarityRank() {
        return 0;
    }

    @Override
    public Rarity getRarity() {
        return Rarities.Runes.get(getRarityRank());
    }

    @Override
    public int Tier() {
        return 0;
    }

    @Override
    public String GUID() {
        return name().toLowerCase(Locale.ROOT);
    }

    @Override
    public int Weight() {
        return 1000;
    }

    public abstract String name();

    public abstract List<StatMod> weaponStat();

    public abstract List<StatMod> armorStat();

    public abstract List<StatMod> jewerlyStat();

    public List<StatMod> spellDamageFlats() {
        return new ElementalSpellDamageFlat(Elements.Physical).allSingleElementVariations();
    }

    public List<StatMod> spellDamageMultis() {
        return new ElementalSpellDamageMulti(Elements.Physical).allSingleElementVariations();
    }

    public List<StatMod> resistFlats() {
        return new ElementalResistFlat(Elements.Physical).allSingleElementVariations();
    }

    public List<StatMod> peneFlats() {
        return new ElementalPeneFlat(Elements.Physical).allSingleElementVariations();
    }

    public List<StatMod> penePercents() {
        return new ElementalPenePercent(Elements.Physical).allSingleElementVariations();
    }

    public List<StatMod> spellDamagePercents() {
        return new ElementalSpellDamagePercent(Elements.Physical).allSingleElementVariations();
    }

}
