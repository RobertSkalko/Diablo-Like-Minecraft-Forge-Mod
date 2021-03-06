package com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases;

import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases.data.ExtraPotionData;
import com.robertx22.mine_and_slash.uncommon.datasaving.base.LoadSave;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;

import javax.annotation.Nullable;
import java.util.Arrays;

public class PotionDataSaving {

    private static String LOC = Ref.MODID + ":pot_data";

    @Nullable
    public static ExtraPotionData getData(EffectInstance instance) {

        if (instance.getCurativeItems().size() > 0) {
            ItemStack stack = instance.getCurativeItems().get(0);

            ExtraPotionData data = LoadSave.Load(ExtraPotionData.class, new ExtraPotionData(), stack.getTag(), LOC);

            if (data != null) {
                return data;
            }

        }
        return null;
    }

    public static void saveData(EffectInstance instance, ExtraPotionData data) {
        ItemStack stack = new ItemStack(Items.AIR);

        //instance.amplifier = data.getStacks(); amp isnt shown above 9, so its useless for my use case :/

        CompoundNBT nbt = new CompoundNBT();
        LoadSave.Save(data, nbt, LOC);

        stack.setTag(nbt);
        instance.setCurativeItems(Arrays.asList(stack));
    }

}
