package com.robertx22.dimensions;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ITeleporter;

public class MyTeleporter implements ITeleporter {
	private final BlockPos targetPos;

	public MyTeleporter(BlockPos targetPos) {
		this.targetPos = targetPos;
	}

	@Override
	public void placeEntity(World world, Entity entity, float yaw) {
		entity.moveToBlockPosAndAngles(targetPos, yaw, entity.rotationPitch);
	}

}