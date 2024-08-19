package org.refabricators.totemexpansion;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.BufferAllocator;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import org.refabricators.totemexpansion.effect.SpelunkingEffect;
import org.refabricators.totemexpansion.event.CustomTotemUsedCallback;
import org.refabricators.totemexpansion.item.ModItems;
import org.refabricators.totemexpansion.util.ModLootTableModifiers;
import org.refabricators.totemexpansion.villager.ModCustomTrades;
import org.refabricators.totemexpansion.villager.ModVillagers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class TotemExpansion implements ModInitializer {
	public static final String MOD_ID = "totemexpansion";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final RegistryEntry<StatusEffect> SPELUNKING_EFFECT = register("spelunking_effect", new SpelunkingEffect());
	public static ArrayList<List<Object>> activeRecallTotems = new ArrayList<>();
	public static ArrayList<Integer> activeTimeTotems = new ArrayList<>();
	public static final Identifier TEXTURE = id("textures/item/totem_spelunking");

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}

	private static RegistryEntry<StatusEffect> register(String id, StatusEffect statusEffect) {
		return Registry.registerReference(Registries.STATUS_EFFECT, id(id), statusEffect);
	}

	@Override
	public void onInitialize() {
		ModLootTableModifiers.modifyLootTables();
		ModItems.registerModItems();
		ModVillagers.registerVillagers();
		ModCustomTrades.registerCustomTrades();

		CustomTotemUsedCallback.EVENT.register((entity, stack) -> {});
	}
}
