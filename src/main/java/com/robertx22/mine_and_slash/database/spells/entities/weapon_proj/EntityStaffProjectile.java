package com.robertx22.mine_and_slash.database.spells.entities.weapon_proj;

import com.robertx22.mine_and_slash.database.spells.entities.bases.EntityBaseProjectile;
import com.robertx22.mine_and_slash.items.gearitems.weapon_mechanics.StaffWeaponMechanic;
import com.robertx22.mine_and_slash.mmorpg.registers.common.EntityRegister;
import com.robertx22.mine_and_slash.uncommon.capability.EntityCap.UnitData;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.FMLPlayMessages;

public class EntityStaffProjectile extends EntityBaseProjectile {

    public EntityStaffProjectile(EntityType<? extends EntityStaffProjectile> type, World world) {
        super(type, world);
    }

    public EntityStaffProjectile(World worldIn) {
        super(EntityRegister.STAFFPROJECTILE, worldIn);

    }

    public EntityStaffProjectile(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
        super(EntityRegister.STAFFPROJECTILE, world);
    }

    @Override
    public int durationInSeconds() {
        return 20;
    }

    @Override
    public double radius() {
        return 0.5D;
    }

    @Override
    protected void onImpact(RayTraceResult result) {

        LivingEntity entity = getEntityHit(result, 0.5D);

        if (entity != null) {

            if (!world.isRemote) {
                try {

                    UnitData sourcedata = Load.Unit(this.getCaster());
                    UnitData targetdata = Load.Unit(entity);

                    StaffWeaponMechanic.INSTANCE.powerAttack(
                            null, this.getCaster(), entity, sourcedata, targetdata, getSpellData().charge);

                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        }

        if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte) 3);
            this.remove();
        }

    }

    @Override
    public void onTick() {

        if (world.isRemote) {
            for (int i = 0; i < 5; i++) {

                this.world.addParticle(ParticleTypes.CRIT, true, this.posX + rand.nextFloat() * 0.2 - 0.1,
                                       this.posY + this.getHeight() / 2 + rand.nextFloat() * 0.2 - 0.1,
                                       this.posZ + rand.nextFloat() * 0.2 - 0.1, 0, 0, 0
                );

            }
        }

        if (this.ticksExisted > 20) {
            this.remove();
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public ItemStack getItem() {
        return new ItemStack(Items.ENDER_PEARL);
    }
}