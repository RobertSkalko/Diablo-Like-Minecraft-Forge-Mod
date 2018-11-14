package com.robertx22.onevent.combat;

import com.robertx22.mmorpg.ModConfig;
import com.robertx22.onevent.ontick.EntityUpdate;
import com.robertx22.saveclasses.Unit;
import com.robertx22.uncommon.capability.EntityData;
import com.robertx22.uncommon.datasaving.UnitSaving;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class OnMobSpawn {

	@SubscribeEvent
	public static void onMobSpawn(EntityJoinWorldEvent event) {

		if (!(event.getEntity() instanceof EntityLivingBase)) {
			return;
		}

		EntityLivingBase entity = (EntityLivingBase) event.getEntity();

		if (entity.world.isRemote) {
			return;
		}

		if (!entity.hasCapability(EntityData.Data, null)) {
			return;
		}

		if (!(entity instanceof EntityPlayer)) {
			if (entity instanceof IMob || entity instanceof EntityMob) {

				Unit check = UnitSaving.Load(entity);

				if (check == null) {
					int level = GetMobLevelByDistanceFromSpawn(entity);
					Unit unit = Unit.Mob(entity, level);
					unit.Save(entity);

					if (unit.rarity == 5 && ModConfig.Client.ANNOUNCE_WORLD_BOSS_SPAWN) {
						AnnounceWorldBossSpawn(entity, unit);
					}

					if (unit != null) {
						EntityUpdate.syncEntityToClient(entity);
					}
				}
			}
		}

	}

	private static void AnnounceWorldBossSpawn(EntityLivingBase entity, Unit unit) {

		for (EntityPlayer player : entity.world.playerEntities) {
			if (player.getDistance(entity) < 150) {

				player.world.playSound(null, player.getPosition(), SoundEvents.ENTITY_ENDERDRAGON_GROWL,
						SoundCategory.AMBIENT, 0.5F, 1);

			}

		}

	}

	public static int GetMobLevelByDistanceFromSpawn(Entity entity) {

		double distance = entity.world.getSpawnPoint().distanceSq(entity.posX, entity.posY, entity.posZ);

		int lvl = 1;

		if (distance < ModConfig.Server.MOB_LEVEL_ONE_AREA) {
			lvl = 1;
		} else {
			lvl = (int) (1 + (distance / ModConfig.Server.MOB_LEVEL_PER_DISTANCE));
		}

		return lvl;

	}

}
