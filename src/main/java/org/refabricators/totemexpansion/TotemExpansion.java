package org.refabricators.totemexpansion;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRenderEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRenderers;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.tick.WorldTickScheduler;
import org.refabricators.totemexpansion.effect.SpelunkingEffect;
import org.refabricators.totemexpansion.event.CustomTotemUsedCallback;
import org.refabricators.totemexpansion.event.ModEventCallbacks;
import org.refabricators.totemexpansion.item.ModItems;
import org.refabricators.totemexpansion.network.SyncPlayerDataS2C;
import org.refabricators.totemexpansion.util.ModLootTableModifiers;
import org.refabricators.totemexpansion.villager.ModCustomTrades;
import org.refabricators.totemexpansion.villager.ModVillagers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TotemExpansion implements ModInitializer {
	public static final String MOD_ID = "totemexpansion";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final RegistryEntry<StatusEffect> SPELUNKING_EFFECT = register("spelunking_effect", new SpelunkingEffect());
	public static ArrayList<List<Object>> activeRecallTotems = new ArrayList<>();

	public static final byte USE_TOTEM_FALLING = 75;
	public static final byte USE_TOTEM_FIRE = 76;
	public static final byte USE_TOTEM_BREATHING = 77;
	public static final byte USE_TOTEM_EXPLOSION = 78;
	public static final byte USE_TOTEM_ORES = 79;
	public static final byte USE_TOTEM_REPAIR = 80;
	public static final byte USE_TOTEM_TIME = 81;
	public static final byte USE_TOTEM_RECALL = 82;

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}

	private static RegistryEntry<StatusEffect> register(String id, StatusEffect statusEffect) {
		return Registry.registerReference(Registries.STATUS_EFFECT, id(id), statusEffect);
	}

	public static ArrayList<Object> bob = new ArrayList<>();

	@Override
	public void onInitialize() {
		PayloadTypeRegistry.playS2C().register(SyncPlayerDataS2C.ID, SyncPlayerDataS2C.CODEC);

		ModLootTableModifiers.modifyLootTables();
		ModItems.registerModItems();
		ModVillagers.registerVillagers();
		ModCustomTrades.registerCustomTrades();
		ModEventCallbacks.registerEventCallbacks();
	}
}
