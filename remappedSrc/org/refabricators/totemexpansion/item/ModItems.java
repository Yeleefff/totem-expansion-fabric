package org.refabricators.totemexpansion.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.refabricators.totemexpansion.TotemExpansion;

public class ModItems
{
    public static final Item TOTEM_FALLING = registerItem("totem_falling",
            new Item(new FabricItemSettings().group(ItemGroup.COMBAT)));

    private static Item registerItem(String name, Item item)
    {
        return Registry.register(Registry.ITEM, new Identifier(TotemExpansion.MOD_ID, name), item);
    }
    public static void registerModItems() {
    }
}
