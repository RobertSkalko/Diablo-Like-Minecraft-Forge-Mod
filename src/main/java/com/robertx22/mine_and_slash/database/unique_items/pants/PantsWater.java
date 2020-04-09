package com.robertx22.mine_and_slash.database.unique_items.pants;

import com.robertx22.mine_and_slash.database.gearitemslots.bases.GearItemSlot;
import com.robertx22.mine_and_slash.database.gearitemslots.plate.PlatePants;
import com.robertx22.mine_and_slash.database.stats.StatMod;
import com.robertx22.mine_and_slash.database.stats.mods.flat.defense.ArmorFlat;
import com.robertx22.mine_and_slash.database.stats.mods.flat.resources.HealthFlat;
import com.robertx22.mine_and_slash.database.stats.mods.flat.resources.MagicShieldFlat;
import com.robertx22.mine_and_slash.database.stats.mods.generated.ElementalResistFlat;
import com.robertx22.mine_and_slash.database.stats.mods.generated.ElementalSpellDamageFlat;
import com.robertx22.mine_and_slash.database.unique_items.IUnique;
import com.robertx22.mine_and_slash.database.unique_items.StatReq;
import com.robertx22.mine_and_slash.saveclasses.player_stat_points.LvlPointStat;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.localization.Styles;

import java.util.Arrays;
import java.util.List;

public class PantsWater implements IUnique {

    public PantsWater() {

    }

    static StatReq req = new StatReq(
        LvlPointStat.STAMINA, StatReq.Size.MEDIUM, LvlPointStat.INTELLIGENCE, StatReq.Size.SMALL);

    @Override
    public GearItemSlot getGearSlot() {
        return PlatePants.INSTANCE;
    }

    @Override
    public StatReq getRequirements() {
        return req;
    }

    @Override
    public int getTier() {
        return 3;
    }

    @Override
    public String GUID() {
        return "pantswater0";
    }

    @Override
    public List<StatMod> uniqueStats() {
        return Arrays.asList(new ArmorFlat().size(StatMod.Size.TRIPLE),
            new ElementalResistFlat(Elements.Water).size(StatMod.Size.HALF_MORE),
            new ElementalSpellDamageFlat(Elements.Water)
        );

    }

    @Override
    public List<StatMod> primaryStats() {
        return Arrays.asList(new HealthFlat(), new MagicShieldFlat());
    }

    @Override
    public String locNameForLangFile() {
        return Styles.YELLOW + "Leggings of the Glacier";
    }

    @Override
    public String locDescForLangFile() {
        return "Try to move me, I dare you.";
    }
}
