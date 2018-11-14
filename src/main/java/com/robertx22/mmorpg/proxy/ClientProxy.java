package com.robertx22.mmorpg.proxy;

import com.robertx22.mmorpg.Keybinds;
import com.robertx22.mmorpg.Main;
import com.robertx22.mmorpg.registers.EntityRegisters;
import com.robertx22.network.DamageNumberPackage;
import com.robertx22.network.EntityPackage;
import com.robertx22.network.PlayerPackage;
import com.robertx22.uncommon.gui.mobs.HealthBarRenderer;
import com.robertx22.uncommon.gui.mobs.ToggleKeyBind;
import com.robertx22.uncommon.gui.player_overlays.BarsGUI;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ClientProxy implements IProxy {
	// functionality

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		// DEBUG
		System.out.println("on Client side");

		MinecraftForge.EVENT_BUS.register(new ToggleKeyBind());
		MinecraftForge.EVENT_BUS.register(new HealthBarRenderer());
		EntityRegisters.Register();

		MinecraftForge.EVENT_BUS.register(new PlayerPackage.Handler());
		MinecraftForge.EVENT_BUS.register(new EntityPackage.Handler());
		MinecraftForge.EVENT_BUS.register(new DamageNumberPackage.Handler());
	}

	@Override
	public void init(FMLInitializationEvent event) {
		// DEBUG
		System.out.println("on Client side");

		Keybinds.register();

	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		// DEBUG
		System.out.println("on Client side");

		MinecraftForge.EVENT_BUS.register(new BarsGUI(Minecraft.getMinecraft()));
	}

	@Override
	public EntityPlayer getPlayerEntityFromContext(MessageContext ctx) {
		return (ctx.side.isClient() ? Minecraft.getMinecraft().player : Main.proxy.getPlayerEntityFromContext(ctx));
	}

	@Override
	public void serverStarting(FMLServerStartingEvent event) {
		// This will never get called on client side
	}
}
