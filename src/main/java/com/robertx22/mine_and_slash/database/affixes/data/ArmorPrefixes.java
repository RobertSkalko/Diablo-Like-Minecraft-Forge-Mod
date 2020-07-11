package com.robertx22.mine_and_slash.database.affixes.data;

import com.robertx22.mine_and_slash.database.StatModifier;
import com.robertx22.mine_and_slash.database.affixes.AffixBuilder;
import com.robertx22.mine_and_slash.database.gearitemslots.bases.GearItemSlot;
import com.robertx22.mine_and_slash.database.requirements.SlotRequirement;
import com.robertx22.mine_and_slash.database.stats.types.defense.Armor;
import com.robertx22.mine_and_slash.database.stats.types.resources.Health;
import com.robertx22.mine_and_slash.registry.ISlashRegistryInit;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;

public class ArmorPrefixes implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        AffixBuilder.Normal("protective")
            .Named("Protective")
            .tier(1, new StatModifier(30, 40, Armor.getInstance(), ModType.FLAT))
            .tier(2, new StatModifier(20, 30, Armor.getInstance(), ModType.FLAT))
            .tier(3, new StatModifier(10, 20, Armor.getInstance(), ModType.FLAT))
            .tier(4, new StatModifier(5, 10, Armor.getInstance(), ModType.FLAT))
            .Req(SlotRequirement.hasBaseStat(Armor.getInstance()))
            .Prefix()
            .Build();

        AffixBuilder.Normal("scaled")
            .Named("Scaled")
            .tier(1, new StatModifier(4, 6, Armor.getInstance(), ModType.FLAT), new StatModifier(3, 5, Health.getInstance(), ModType.FLAT))
            .tier(2, new StatModifier(3, 4, Armor.getInstance(), ModType.FLAT), new StatModifier(2, 3, Health.getInstance(), ModType.FLAT))
            .tier(3, new StatModifier(2, 3, Armor.getInstance(), ModType.FLAT), new StatModifier(1, 2, Health.getInstance(), ModType.FLAT))
            .Req(SlotRequirement.hasBaseStat(Armor.getInstance()))
            .Prefix()
            .Build();

        AffixBuilder.Normal("reinforced")
            .Named("Reinforced")
            .tier(1, new StatModifier(25F, 50F, Armor.getInstance(), ModType.LOCAL_INCREASE))
            .tier(2, new StatModifier(20F, 25F, Armor.getInstance(), ModType.LOCAL_INCREASE))
            .tier(3, new StatModifier(10, 20F, Armor.getInstance(), ModType.LOCAL_INCREASE))
            .tier(3, new StatModifier(5, 10, Armor.getInstance(), ModType.LOCAL_INCREASE))
            .Req(SlotRequirement.hasBaseStat(Armor.getInstance()))
            .Prefix()
            .Build();

        AffixBuilder.Normal("virile")
            .Named("Virile")
            .tier(1, new StatModifier(8, 12, Health.getInstance(), ModType.FLAT))
            .tier(2, new StatModifier(6, 8, Health.getInstance(), ModType.FLAT))
            .tier(3, new StatModifier(4, 6, Health.getInstance(), ModType.FLAT))
            .tier(4, new StatModifier(2, 4, Health.getInstance(), ModType.FLAT))
            .Req(SlotRequirement.Of(GearItemSlot.SlotFamily.Armor))
            .Prefix()
            .Build();

    }
}