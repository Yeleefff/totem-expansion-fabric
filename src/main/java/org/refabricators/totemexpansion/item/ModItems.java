package org.refabricators.totemexpansion.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import org.refabricators.totemexpansion.item.totem.*;

import static org.refabricators.totemexpansion.TotemExpansion.id;

public class ModItems {
    public static final Item TOTEM_BASE = registerItem("totem_base", new Item(new Item.Settings()));

    public static final Item TOTEM_HEAD_UNDYING = registerItem("totem_head_undying", new Item(new Item.Settings()));
    public static final Item TOTEM_HEAD_BREATHING = registerItem("totem_head_breathing",new Item(new Item.Settings()));
    public static final Item TOTEM_HEAD_EXPLOSION = registerItem("totem_head_explosion", new Item(new Item.Settings()));
    public static final Item TOTEM_HEAD_FALLING = registerItem("totem_head_falling", new Item(new Item.Settings()));
    public static final Item TOTEM_HEAD_FIRE = registerItem("totem_head_fire", new Item(new Item.Settings()));
    public static final Item TOTEM_HEAD_ORES = registerItem("totem_head_ores", new Item(new Item.Settings()));
    public static final Item TOTEM_HEAD_REPAIR = registerItem("totem_head_repair", new Item(new Item.Settings()));
    public static final Item TOTEM_HEAD_TIME = registerItem("totem_head_time", new Item(new Item.Settings()));
    public static final Item TOTEM_HEAD_RECALL = registerItem("totem_head_", new Item(new Item.Settings()));

    public static final Item TOTEM_FALLING = registerItem("totem_falling", new TotemFalling());
    public static final Item TOTEM_FIRE = registerItem("totem_fire", new TotemFire());
    public static final Item TOTEM_BREATHING = registerItem("totem_breathing", new TotemBreathing());
    public static final Item TOTEM_EXPLOSION = registerItem("totem_explosion", new TotemExplosion());
    public static final Item TOTEM_ORES = registerItem("totem_ores", new TotemOres());
    public static final Item TOTEM_REPAIR = registerItem("totem_repair", new TotemRepair());
    public static final Item TOTEM_TIME = registerItem("totem_time", new TotemTime());
    public static final Item TOTEM_RECALL = registerItem("totem_recall", new TotemRecall());

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, id(name), item);
    }

    private static void addModItemsToModGroup() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
            entries.add(TOTEM_FALLING);
            entries.add(TOTEM_FIRE);
            entries.add(TOTEM_BREATHING);
            entries.add(TOTEM_EXPLOSION);
            entries.add(TOTEM_ORES);
            entries.add(TOTEM_TIME);
            entries.add(TOTEM_RECALL);
            entries.add(TOTEM_REPAIR);
        });
    }

    public static void registerModItems() {
        addModItemsToModGroup();
    }
}
