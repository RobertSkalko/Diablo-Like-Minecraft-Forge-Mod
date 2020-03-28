package com.robertx22.mine_and_slash.database.gearitemslots.curios;

import com.robertx22.mine_and_slash.database.gearitemslots.bases.BaseCurio;
import com.robertx22.mine_and_slash.database.gearitemslots.bases.GearItemSlot;
import com.robertx22.mine_and_slash.database.gearitemslots.bases.PosStats;
import com.robertx22.mine_and_slash.database.stats.StatMod;
import com.robertx22.mine_and_slash.database.stats.mods.flat.resources.EnergyRegenFlat;
import com.robertx22.mine_and_slash.database.stats.mods.flat.resources.ManaFlat;
import com.robertx22.mine_and_slash.database.stats.mods.flat.resources.ManaRegenFlat;
import com.robertx22.mine_and_slash.database.unique_items.StatReq;
import com.robertx22.mine_and_slash.items.gearitems.baubles.ItemRing;
import net.minecraft.item.Item;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Ring extends BaseCurio {

    public static GearItemSlot INSTANCE = new Ring();

    private Ring() {

    }

    @Override
    public StatReq getRequirements() {
        return noReq;
    }

    @Override
    public PlayStyle getPlayStyle() {
        return PlayStyle.NONE;
    }

    @Override
    public String resourceID() {
        return "ring";
    }

    @Override
    public String GUID() {
        return "ring";
    }

    @Override
    public List<PosStats> getPossiblePrimaryStats() {
        return eleDmgs();
    }

    @Override
    public List<StatMod> getPossibleSecondaryStats() {
        return Arrays.asList(new EnergyRegenFlat(), new ManaRegenFlat(), new ManaFlat());
    }

    @Override
    public Item getDefaultItem() {
        return ItemRing.Items.get(0);
    }

    @Override
    public HashMap<Integer, Item> getItemsForRaritiesMap() {
        return ItemRing.Items;
    }

    @Override
    public int Weight() {
        return super.Weight() * 2;
    }

    @Override
    public GearSlotType slotType() {
        return GearSlotType.Jewerly;
    }

    @Override
    public String locNameForLangFile() {
        return "Ring";
    }
}
