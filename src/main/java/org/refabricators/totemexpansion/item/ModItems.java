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
import net.minecraft.util.Rarity;
import org.refabricators.totemexpansion.TotemExpansion;
import org.refabricators.totemexpansion.item.totem.*;
import org.refabricators.totemexpansion.util.TotemToolTipComponent;

import java.util.List;
import java.util.function.Function;

import static org.refabricators.totemexpansion.TotemExpansion.id;

public class ModItems {
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

    public static final Item TOTEM_FALLING = registerItem("totem_falling", TotemFalling::new, new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON)
            .component(TotemExpansion.TOTEM_TOOL_TIP_COMPONENT, new TotemToolTipComponent("totem_falling"))
            .component(DataComponentTypes.DEATH_PROTECTION, TOTEM_FALLING_COMPONENT));
    public static final Item TOTEM_FIRE = registerItem("totem_fire", TotemFire::new, new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON)
            .component(TotemExpansion.TOTEM_TOOL_TIP_COMPONENT, new TotemToolTipComponent("totem_fire"))
            .component(DataComponentTypes.DEATH_PROTECTION, TOTEM_FIRE_COMPONENT));
    public static final Item TOTEM_BREATHING = registerItem("totem_breathing", TotemBreathing::new, new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON)
            .component(TotemExpansion.TOTEM_TOOL_TIP_COMPONENT, new TotemToolTipComponent("totem_breathing"))
            .component(DataComponentTypes.DEATH_PROTECTION, TOTEM_BREATHING_COMPONENT));
    public static final Item TOTEM_EXPLOSION = registerItem("totem_explosion", TotemExplosion::new, new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON)
            .component(TotemExpansion.TOTEM_TOOL_TIP_COMPONENT, new TotemToolTipComponent("totem_explosion"))
            .component(DataComponentTypes.DEATH_PROTECTION, TOTEM_EXPLOSION_COMPONENT));
    public static final Item TOTEM_ORES = registerItem("totem_ores", TotemOres::new, new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON)
            .component(TotemExpansion.TOTEM_TOOL_TIP_COMPONENT, new TotemToolTipComponent("totem_ores"))
            .component(DataComponentTypes.DEATH_PROTECTION, TOTEM_ORES_COMPONENT));
    public static final Item TOTEM_REPAIR = registerItem("totem_repair", TotemRepair::new, new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON)
            .component(TotemExpansion.TOTEM_TOOL_TIP_COMPONENT, new TotemToolTipComponent("totem_repair"))
            .component(DataComponentTypes.DEATH_PROTECTION, TOTEM_REPAIR_COMPONENT));
    public static final Item TOTEM_TIME = registerItem("totem_time", TotemTime::new, new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON)
            .component(TotemExpansion.TOTEM_TOOL_TIP_COMPONENT, new TotemToolTipComponent("totem_time"))
            .component(DataComponentTypes.DEATH_PROTECTION, TOTEM_TIME_COMPONENT));
    public static final Item TOTEM_RECALL = registerItem("totem_recall", TotemRecall::new, new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON)
            .component(TotemExpansion.TOTEM_TOOL_TIP_COMPONENT, new TotemToolTipComponent("totem_recall"))
            .component(DataComponentTypes.DEATH_PROTECTION, TOTEM_RECALL_COMPONENT));

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
