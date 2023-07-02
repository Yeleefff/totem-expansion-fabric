package org.refabricators.totemexpansion;

import net.fabricmc.api.ModInitializer;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.refabricators.totemexpansion.effect.SpelunkingEffect;
import org.refabricators.totemexpansion.event.CustomTotemUsedCallback;
import org.refabricators.totemexpansion.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TotemExpansion implements ModInitializer
{
	public static final String MOD_ID = "totemexpansion";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final StatusEffect SPELUNKING_EFFECT = new SpelunkingEffect();

	@Override
	public void onInitialize()
	{
		ModItems.registerModItems();
		CustomTotemUsedCallback.EVENT.register((entity, stack) -> {});
		Registry.register(Registries.STATUS_EFFECT,
				new Identifier(MOD_ID, "spelunking_effect"), SPELUNKING_EFFECT);
	}
}
