package com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases;

import net.minecraft.util.text.ITextComponent;

import java.util.List;

public interface ITooltipList {

    public default List<ITextComponent> GetTooltipStringWithNoExtraSpellInfo(TooltipInfo info) {
        info.showAbilityExtraInfo = false;
        List<ITextComponent> list = GetTooltipString(info);
        info.showAbilityExtraInfo = true;
        return list;
    }

    public abstract List<ITextComponent> GetTooltipString(TooltipInfo info);
}


