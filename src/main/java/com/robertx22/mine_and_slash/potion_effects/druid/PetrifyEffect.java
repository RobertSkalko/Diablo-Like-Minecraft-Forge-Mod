package com.robertx22.mine_and_slash.potion_effects.druid;

import com.robertx22.mine_and_slash.database.stats.types.generated.ElementalSpellDamage;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.packets.particles.ParticleEnum;
import com.robertx22.mine_and_slash.packets.particles.ParticlePacketData;
import com.robertx22.mine_and_slash.potion_effects.bases.BasePotionEffect;
import com.robertx22.mine_and_slash.potion_effects.bases.IOnBasicAttackedPotion;
import com.robertx22.mine_and_slash.potion_effects.bases.data.ExtraPotionData;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.mine_and_slash.saveclasses.spells.SpellCalcData;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.effectdatas.DamageEffect;
import com.robertx22.mine_and_slash.uncommon.effectdatas.EffectData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.interfaces.WeaponTypes;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.SoundUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.util.ArrayList;
import java.util.List;

public class PetrifyEffect extends BasePotionEffect implements IOnBasicAttackedPotion {

    public static final PetrifyEffect INSTANCE = new PetrifyEffect();

    private PetrifyEffect() {
        super(EffectType.HARMFUL, 4393423);
        this.setRegistryName(new ResourceLocation(Ref.MODID, GUID()));

        this.addAttributesModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "7107DE5E-7CE8-4030-940E-514C1F160892",
                                   (double) -0.95F, AttributeModifier.Operation.MULTIPLY_TOTAL
        );

    }

    @Override
    public int getDurationInSeconds() {
        return 10;
    }

    public static SpellCalcData CALC = SpellCalcData.one(new ElementalSpellDamage(Elements.Nature), 0.5F, 2);

    @Override
    public void onXTicks(LivingEntity entity, ExtraPotionData data, LivingEntity caster) {

        ParticleEnum.sendToClients(
                entity, new ParticlePacketData(entity.getPosition(), ParticleEnum.PETRIFY).radius(1)
                        .type(ParticleTypes.CLOUD)
                        .amount(15));

        SoundUtils.playSound(entity, SoundEvents.BLOCK_STONE_BREAK, 0.5F, 0.5F);

    }

    @Override
    public String GUID() {
        return "petrify";
    }

    @Override
    public int performEachXTicks() {
        return 20;
    }

    @Override
    public String locNameForLangFile() {
        return "Petrify";
    }

    @Override
    public int getMaxStacks() {
        return 1;
    }

    @Override
    public List<ITextComponent> getEffectTooltip(TooltipInfo info) {

        List<ITextComponent> list = new ArrayList<>();

        list.add(new StringTextComponent("Petrifies Enemy."));
        list.add(new StringTextComponent("If Attacked, does extra damage, but stops effect."));

        list.addAll(CALC.GetTooltipString(info));

        return list;
    }

    @Override
    public void onBasicAttacked(LivingEntity source, LivingEntity target) {

        int num = CALC.getCalculatedValue(Load.Unit(source));

        DamageEffect dmg = new DamageEffect(null, source, target, num, EffectData.EffectTypes.SPELL, WeaponTypes.None);
        dmg.element = Elements.Nature;
        dmg.Activate();

        ParticleEnum.sendToClients(
                target, new ParticlePacketData(target.getPosition(), ParticleEnum.PETRIFY).radius(1)
                        .type(ParticleTypes.CLOUD)
                        .amount(20));

        target.playSound(SoundEvents.BLOCK_STONE_BREAK, 1, 1);

        target.removePotionEffect(this);

    }
}