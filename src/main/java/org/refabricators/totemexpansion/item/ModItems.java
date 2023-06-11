package org.refabricators.totemexpansion.item;


import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import org.refabricators.totemexpansion.TotemExpansion;

public class ModItems {

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(TotemExpansion.MOD_ID, name), item);
    }
    
    public static void registerModItems() {
        
    }
}
