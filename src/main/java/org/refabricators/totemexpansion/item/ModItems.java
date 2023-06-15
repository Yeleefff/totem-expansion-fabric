package org.refabricators.totemexpansion.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import org.refabricators.totemexpansion.TotemExpansion;

public class ModItems
{
    public static final Item FIRE_TOTEM = registerItem("fire_totem", new FireTotem());

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(TotemExpansion.MOD_ID, name), item);
    }

    private static void addModItemsToModGroup() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
            entries.add(FIRE_TOTEM);
        });
    }

    public static void registerModItems() {
        addModItemsToModGroup();
        TotemExpansion.LOGGER.debug("Registering mod Items for" + TotemExpansion.MOD_ID);
    }
}
