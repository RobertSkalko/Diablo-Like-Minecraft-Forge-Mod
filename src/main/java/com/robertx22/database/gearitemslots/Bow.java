package com.robertx22.database.gearitemslots;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.robertx22.customitems.gearitems.weapons.ItemBow;
import com.robertx22.database.gearitemslots.bases.BaseWeapon;
import com.robertx22.database.stat_mods.flat.ArmorPeneFlat;
import com.robertx22.database.stat_mods.percent.ArmorPenePercent;
import com.robertx22.stats.StatMod;

import net.minecraft.item.Item;

public class Bow extends BaseWeapon {

    @Override
    public String Name() {
	return "Bow";
    }

    @Override
    public Item DefaultItem() {
	return ItemBow.Items.get(0);
    }

    @Override
    public List<StatMod> slotTypeStats() {
	return Arrays.asList(new ArmorPenePercent(), new ArmorPeneFlat());
    }

    @Override
    public HashMap<Integer, Item> ItemsForRarities() {
	return ItemBow.Items;
    }

}
