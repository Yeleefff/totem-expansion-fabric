package org.refabricators.totemexpansion.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DeathProtectionComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;
import net.minecraft.item.consume.ClearAllEffectsConsumeEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;
import org.refabricators.totemexpansion.TotemExpansion;
import org.refabricators.totemexpansion.item.totem.*;

import java.util.List;

import static org.refabricators.totemexpansion.TotemExpansion.id;

public class ModItems {
    // Make sure item components are defined before the item
    public static final DeathProtectionComponent TOTEM_FALLING_COMPONENT = new DeathProtectionComponent(
            List.of(new ClearAllEffectsConsumeEffect(), new ApplyEffectsConsumeEffect(List.of(
                    new StatusEffectInstance(StatusEffects.LEVITATION, 900, 2)))));
    public static final DeathProtectionComponent TOTEM_FIRE_COMPONENT = new DeathProtectionComponent(
            List.of(new ClearAllEffectsConsumeEffect(), new ApplyEffectsConsumeEffect(List.of(
                    new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 900, 0)))));
    public static final DeathProtectionComponent TOTEM_BREATHING_COMPONENT = new DeathProtectionComponent(
            List.of(new ClearAllEffectsConsumeEffect(), new ApplyEffectsConsumeEffect(List.of(
                    new StatusEffectInstance(StatusEffects.WATER_BREATHING, 900)))));
    public static final DeathProtectionComponent TOTEM_EXPLOSION_COMPONENT = new DeathProtectionComponent(
            List.of(new ApplyEffectsConsumeEffect(List.of())));
    public static final DeathProtectionComponent TOTEM_ORES_COMPONENT = new DeathProtectionComponent(
            List.of(new ApplyEffectsConsumeEffect(List.of(
                    new StatusEffectInstance(TotemExpansion.SPELUNKING_EFFECT, 900)))));
    public static final DeathProtectionComponent TOTEM_REPAIR_COMPONENT = new DeathProtectionComponent(
            List.of(new ApplyEffectsConsumeEffect(List.of())));
    public static final DeathProtectionComponent TOTEM_TIME_COMPONENT = new DeathProtectionComponent(
            List.of( new ApplyEffectsConsumeEffect(List.of())));
    public static final DeathProtectionComponent TOTEM_RECALL_COMPONENT = new DeathProtectionComponent(
            List.of(new ApplyEffectsConsumeEffect(List.of())));

    public static final Item TOTEM_BASE = registerItem("totem_base", new Item(new Item.Settings()
            .registryKey(RegistryKey.of(RegistryKeys.ITEM, id("totem_base")))));
    public static final Item TOTEM_HEAD_UNDYING = registerItem("totem_head_undying", new Item(new Item.Settings()
            .registryKey(RegistryKey.of(RegistryKeys.ITEM, id("totem_head_undying")))));
    public static final Item TOTEM_HEAD_BREATHING = registerItem("totem_head_breathing",new Item(new Item.Settings()
            .registryKey(RegistryKey.of(RegistryKeys.ITEM, id("totem_head_breathing")))));
    public static final Item TOTEM_HEAD_EXPLOSION = registerItem("totem_head_explosion", new Item(new Item.Settings()
            .registryKey(RegistryKey.of(RegistryKeys.ITEM, id("totem_head_explosion")))));
    public static final Item TOTEM_HEAD_FALLING = registerItem("totem_head_falling", new Item(new Item.Settings()
            .registryKey(RegistryKey.of(RegistryKeys.ITEM, id("totem_head_falling")))));
    public static final Item TOTEM_HEAD_FIRE = registerItem("totem_head_fire", new Item(new Item.Settings()
            .registryKey(RegistryKey.of(RegistryKeys.ITEM, id("totem_head_fire")))));
    public static final Item TOTEM_HEAD_ORES = registerItem("totem_head_ores", new Item(new Item.Settings()
            .registryKey(RegistryKey.of(RegistryKeys.ITEM, id("totem_head_ores")))));
    public static final Item TOTEM_HEAD_REPAIR = registerItem("totem_head_repair", new Item(new Item.Settings()
            .registryKey(RegistryKey.of(RegistryKeys.ITEM, id("totem_head_repair")))));
    public static final Item TOTEM_HEAD_TIME = registerItem("totem_head_time", new Item(new Item.Settings()
            .registryKey(RegistryKey.of(RegistryKeys.ITEM, id("totem_head_time")))));
    public static final Item TOTEM_HEAD_RECALL = registerItem("totem_head_recall", new Item(new Item.Settings()
            .registryKey(RegistryKey.of(RegistryKeys.ITEM, id("totem_head_recall")))));

    public static final Item TOTEM_FALLING = registerItem("totem_falling", new TotemFalling());
    public static final Item TOTEM_FIRE = registerItem("totem_fire", new TotemFire());
    public static final Item TOTEM_BREATHING = registerItem("totem_breathing", new TotemBreathing());
    public static final Item TOTEM_EXPLOSION = registerItem("totem_explosion", new TotemExplosion());
    public static final Item TOTEM_ORES = registerItem("totem_ores", new TotemOres());
    public static final Item TOTEM_REPAIR = registerItem("totem_repair", new TotemRepair());
    public static final Item TOTEM_TIME = registerItem("totem_time", new TotemTime());
    public static final Item TOTEM_RECALL = registerItem("totem_recall", new TotemRecall());

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, RegistryKey.of(RegistryKeys.ITEM, id(name)), item);
    }

    private static void addModItemsToModGroup() {
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

    public static void registerModItems() {
        addModItemsToModGroup();
    }
}
