package org.refabricators.totemexpansion;

import net.fabricmc.api.ModInitializer;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import org.refabricators.totemexpansion.effect.SpelunkingEffect;
import org.refabricators.totemexpansion.event.CustomTotemUsedCallback;
import org.refabricators.totemexpansion.item.ModItems;
import org.refabricators.totemexpansion.villager.ModCustomTrades;
import org.refabricators.totemexpansion.villager.ModVillagers;
import org.refabricators.totemexpansion.loot.ModLootTableModifiers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class TotemExpansion implements ModInitializer {
	public static final String MOD_ID = "totemexpansion";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final RegistryEntry<StatusEffect> SPELUNKING_EFFECT = register("spelunking_effect", new SpelunkingEffect());
	public static ArrayList<List> activeRecallTotems = new ArrayList<>();
	public static ArrayList<Integer> activeTimeTotems = new ArrayList<>();

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}

	private static RegistryEntry<StatusEffect> register(String id, StatusEffect statusEffect) {
		return Registry.registerReference(Registries.STATUS_EFFECT, id(id), statusEffect);
	}

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModVillagers.registerVillagers();
		ModCustomTrades.registerCustomTrades();
		CustomTotemUsedCallback.EVENT.register((entity, stack) -> {});
		ModLootTableModifiers.modifyLootTables();
	}
}
