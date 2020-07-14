package com.robertx22.mine_and_slash.loot.gens;

import com.robertx22.mine_and_slash.config.forge.ModConfig;
import com.robertx22.mine_and_slash.loot.LootInfo;
import com.robertx22.mine_and_slash.loot.blueprints.UniqueGearBlueprint;
import com.robertx22.mine_and_slash.registry.SlashRegistry;
import com.robertx22.mine_and_slash.uncommon.enumclasses.LootType;
import net.minecraft.item.ItemStack;

public class UniqueGearLootGen extends BaseLootGen<UniqueGearBlueprint> {

    public UniqueGearLootGen(LootInfo info) {
        super(info);
    }

    @Override
    public float baseDropChance() {

        float drop = ModConfig.INSTANCE.DropRates.UNIQUE_DROPRATE.get()
            .floatValue();

        if (info.world != null) {
            drop *= SlashRegistry.getDimensionConfig(info.world).unique_gear_drop_multi;
        } else {
            drop = 0;
        }

        if (info.isChestLoot) {
            if (drop <= 0) {
                drop = ModConfig.INSTANCE.DropRates.UNIQUE_DROPRATE.get()
                    .floatValue();
            }
            drop *= 2; // encourage exploring to find chests
        }

        return drop;
    }

    @Override
    public LootType lootType() {
        return LootType.UniqueItem;
    }

    @Override
    public boolean condition() {
        return true;
    }

    @Override
    public ItemStack generateOne() {

        UniqueGearBlueprint blueprint = new UniqueGearBlueprint(info.level, info.tier, true);

        ItemStack stack = blueprint.createStack();

        return stack;

    }

}
