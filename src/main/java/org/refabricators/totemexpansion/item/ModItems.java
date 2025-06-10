package org.refabricators.totemexpansion.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.Util;
import org.refabricators.totemexpansion.item.totem.*;

import java.util.function.Function;

import static org.refabricators.totemexpansion.TotemExpansion.id;

public class ModItems {
    public static final Item TOTEM_BASE = registerItem("totem_base", Item::new, new Item.Settings());

    public static final Item TOTEM_HEAD_UNDYING = registerItem("totem_head_undying", Item::new, new Item.Settings());
    public static final Item TOTEM_HEAD_BREATHING = registerItem("totem_head_breathing",Item::new, new Item.Settings());
    public static final Item TOTEM_HEAD_EXPLOSION = registerItem("totem_head_explosion", Item::new, new Item.Settings());
    public static final Item TOTEM_HEAD_FALLING = registerItem("totem_head_falling", Item::new, new Item.Settings());
    public static final Item TOTEM_HEAD_FIRE = registerItem("totem_head_fire", Item::new, new Item.Settings());
    public static final Item TOTEM_HEAD_ORES = registerItem("totem_head_ores", Item::new, new Item.Settings());
    public static final Item TOTEM_HEAD_REPAIR = registerItem("totem_head_repair", Item::new, new Item.Settings());
    public static final Item TOTEM_HEAD_TIME = registerItem("totem_head_time", Item::new, new Item.Settings());
    public static final Item TOTEM_HEAD_RECALL = registerItem("totem_head_recall", Item::new, new Item.Settings());

    public static final Item TOTEM_FALLING = registerItem("totem_falling", TotemFalling::new, new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON));
    public static final Item TOTEM_FIRE = registerItem("totem_fire", TotemFire::new, new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON));
    public static final Item TOTEM_BREATHING = registerItem("totem_breathing", TotemBreathing::new, new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON));
    public static final Item TOTEM_EXPLOSION = registerItem("totem_explosion", TotemExplosion::new, new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON));
    public static final Item TOTEM_ORES = registerItem("totem_ores", TotemOres::new, new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON));
    public static final Item TOTEM_REPAIR = registerItem("totem_repair", TotemRepair::new, new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON));
    public static final Item TOTEM_TIME = registerItem("totem_time", TotemTime::new, new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON));
    public static final Item TOTEM_RECALL = registerItem("totem_recall", TotemRecall::new, new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON));

    private static RegistryKey<Item> keyOf(String name) {
        return RegistryKey.of(RegistryKeys.ITEM, id(name));
    }
    
    private static Item registerItem(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = factory.apply(settings.registryKey(keyOf(name)));
        return Registry.register(Registries.ITEM, id(name), item);
    }

    public static void registerModItems() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
            entries.add(TOTEM_BASE);
            entries.add(TOTEM_HEAD_UNDYING);
            entries.add(TOTEM_FALLING);
            entries.add(TOTEM_HEAD_FALLING);
            entries.add(TOTEM_FIRE);
            entries.add(TOTEM_HEAD_FIRE);
            entries.add(TOTEM_BREATHING);
            entries.add(TOTEM_HEAD_BREATHING);
            entries.add(TOTEM_EXPLOSION);
            entries.add(TOTEM_HEAD_EXPLOSION);
            entries.add(TOTEM_ORES);
            entries.add(TOTEM_HEAD_ORES);
            entries.add(TOTEM_TIME);
            entries.add(TOTEM_HEAD_TIME);
            entries.add(TOTEM_RECALL);
            entries.add(TOTEM_HEAD_RECALL);
            entries.add(TOTEM_REPAIR);
            entries.add(TOTEM_HEAD_REPAIR);
        });
    }
}
