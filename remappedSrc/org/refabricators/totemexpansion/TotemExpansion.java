package org.refabricators.totemexpansion;

import net.fabricmc.api.ModInitializer;
import org.refabricators.totemexpansion.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TotemExpansion implements ModInitializer
{
	public static final String MOD_ID = "totemexpansion";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize()
	{
		ModItems.registerModItems();
	}
}
