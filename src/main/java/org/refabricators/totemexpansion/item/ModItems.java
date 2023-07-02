package org.refabricators.totemexpansion.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import org.refabricators.totemexpansion.TotemExpansion;
import org.refabricators.totemexpansion.item.totem.*;

public class ModItems
{
    public static final Item TOTEM_FALLING = registerItem("totem_falling", new TotemFalling());
    public static final Item TOTEM_FIRE = registerItem("totem_fire", new TotemFire());
    public static final Item TOTEM_BREATHING = registerItem("totem_breathing", new TotemBreathing());
    public static final Item TOTEM_EXPLOSION = registerItem("totem_explosion", new TotemExplosion());
    public static final Item TOTEM_ORES = registerItem("totem_ores", new TotemOres());

    private static Item registerItem(String name, Item item)
    {
        return Registry.register(Registries.ITEM, new Identifier(TotemExpansion.MOD_ID, name), item);
    }

    private static void addModItemsToModGroup()
    {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
            entries.add(TOTEM_FALLING);
            entries.add(TOTEM_FIRE);
            entries.add(TOTEM_BREATHING);
            entries.add(TOTEM_EXPLOSION);
            entries.add(TOTEM_ORES);
        });
    }

    public static void registerModItems()
    {
        addModItemsToModGroup();
    }
}
