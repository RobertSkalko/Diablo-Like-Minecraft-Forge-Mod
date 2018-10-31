package com.robertx22.spells.aoe_projectile.FrostExplosion;

import com.robertx22.effectdatas.DamageEffect;
import com.robertx22.spells.bases.BaseSpellEffect;
import com.robertx22.spells.bases.DamageData;
import com.robertx22.uncommon.enumclasses.Elements;

import net.minecraft.entity.EntityLivingBase;

public class EffectFrostExplosion extends BaseSpellEffect {

	public EffectFrostExplosion() {
		super();

	}

	@Override
	public String Name() {
		return "Frost Explosion Damage";
	}

	@Override
	public void Activate(DamageData dmgdata, EntityLivingBase target) {
		DamageEffect dmg = new DamageEffect(dmgdata.caster, target, dmgdata.spellItem.GetDamage(dmgdata.casterUnit));
		dmg.Element = Elements.Water;
		dmg.Activate();

	}

}