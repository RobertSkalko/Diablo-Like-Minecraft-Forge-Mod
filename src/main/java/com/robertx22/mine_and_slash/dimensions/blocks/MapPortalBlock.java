package com.robertx22.mine_and_slash.dimensions.blocks;

import com.robertx22.mine_and_slash.dimensions.MapManager;
import com.robertx22.mine_and_slash.mmorpg.MMORPG;
import com.robertx22.mine_and_slash.uncommon.capability.PlayerMapCap;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.localization.Chats;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.PlayerUtils;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.WorldUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.EndPortalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;

public class MapPortalBlock extends EndPortalBlock {

    public MapPortalBlock() {

        super(Block.Properties.create(Material.PORTAL, MaterialColor.BLACK)
                      .doesNotBlockMovement()
                      .lightValue(15)
                      .hardnessAndResistance(2, 2));

    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        try {
            if (world.isRemote == false && entity instanceof PlayerEntity) {
                if (!entity.isBeingRidden() && entity.isNonBoss()) {

                    TileEntity en = world.getTileEntity(pos);

                    if (en instanceof TileMapPortal) {
                        TileMapPortal portal = (TileMapPortal) en;

                        portal.ontick();

                        if (portal.readyToTeleport()) {

                            Iterable<ServerWorld> test = MapManager.getServer().getWorlds();

                            ResourceLocation loc = MapManager.getResourceLocation(
                                    entity.world.getDimension().getType());

                            PlayerEntity player = (PlayerEntity) entity;

                            PlayerMapCap.IPlayerMapData data = Load.playerMapData(player);

                            if (data.hasTimeForMap()) {

                                DimensionType type = MapManager.getOrRegister(data.getMap());

                                World mapworld = MapManager.getWorld(type);

                                if (mapworld == null) {
                                    return;
                                }

                                if (WorldUtils.isMapWorld(mapworld)) {

                                    MMORPG.devToolsLog(
                                            "trying to teleport to portal id:  " + MapManager.getResourceLocation(type)
                                                    .toString() + " world id: " + MapManager.getId(mapworld));

                                    entity.sendMessage(Chats.Teleport_started.locName());

                                    BlockPos pos1 = WorldUtils.getSurface(mapworld, mapworld.getSpawnPoint());

                                    Entity tped = PlayerUtils.changeDimension((ServerPlayerEntity) player, type, pos1);

                                    MMORPG.devToolsLog("tp to map succeeded");

                                } else {
                                    entity.sendMessage(Chats.Not_enough_time.locName());
                                }

                            }

                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new TileMapPortal();
    }

}
