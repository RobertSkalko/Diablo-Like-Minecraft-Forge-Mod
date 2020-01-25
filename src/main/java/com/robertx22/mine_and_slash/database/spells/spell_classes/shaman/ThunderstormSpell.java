package com.robertx22.mine_and_slash.database.spells.spell_classes.shaman;

import com.robertx22.mine_and_slash.database.spells.SpellUtils;
import com.robertx22.mine_and_slash.database.spells.entities.cloud.ThunderstormEntity;
import com.robertx22.mine_and_slash.database.spells.spell_classes.bases.BaseSpell;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.mine_and_slash.saveclasses.spells.SpellCalcData;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.enumclasses.SpellSchools;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class ThunderstormSpell extends BaseSpell {

    public ThunderstormSpell() {

    }

    public Elements element = Elements.Thunder;

    @Override
    public SpellSchools getSchool() {
        return SpellSchools.SHAMAN;
    }

    @Override
    public int getCooldownInSeconds() {
        return 20;
    }

    @Override
    public BaseSpell.SpellType getSpellType() {
        return SpellType.LASTING_AOE;
    }

    @Override
    public String GUID() {
        return "thunder_storm";
    }

    @Override
    public int getManaCost() {
        return 80;
    }

    @Override
    public int useTimeTicks() {
        return 40;
    }

    @Override
    public SpellCalcData getCalculation() {
        return SpellCalcData.one(dmgStat(), 0.5F, 5);
    }

    @Override
    public Elements getElement() {
        return element;
    }

    @Override
    public List<ITextComponent> GetDescription(TooltipInfo info) {

        List<ITextComponent> list = new ArrayList<>();

        list.add(new StringTextComponent("Summons a cloud of lightning, damaging all enemies inside"));

        list.addAll(getCalculation().GetTooltipString(info));

        return list;

    }

    @Override
    public Words getName() {
        return Words.Thunderstorm;
    }

    @Override
    public boolean cast(PlayerEntity caster, int ticksInUse) {
        World world = caster.world;

        RayTraceResult ray = caster.pick(10D, 0.0F, false);

        Vec3d pos = ray.getHitVec();

        Entity en = SpellUtils.getSpellEntity(new ThunderstormEntity(world), this, caster);

        en.setPosition(pos.x, pos.y, pos.z);

        caster.world.addEntity(en);

        return true;
    }

}