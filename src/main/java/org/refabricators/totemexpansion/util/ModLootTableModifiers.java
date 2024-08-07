package org.refabricators.totemexpansion.util;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.EnchantedCountIncreaseLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;
import org.refabricators.totemexpansion.item.ModItems;

import java.util.ArrayList;

public class ModLootTableModifiers {
    private static final Identifier ZOMBIE_ID = Identifier.of("minecraft", "entities/zombie");
    private static final Identifier SKELETON_ID = Identifier.of("minecraft", "entities/skeleton");
    private static final Identifier SPIDER_ID = Identifier.of("minecraft", "entities/spider");
    private static final Identifier CAVE_SPIDER_ID = Identifier.of("minecraft", "entities/cave_spider");
    private static final Identifier CREEPER_ID = Identifier.of("minecraft", "entities/creeper");
    private static final Identifier BLAZE_ID = Identifier.of("minecraft", "entities/blaze");
    private static final Identifier BOGGED_ID = Identifier.of("minecraft", "entities/bogged");
    private static final Identifier BREEZE_ID = Identifier.of("minecraft", "entities/breeze");
    private static final Identifier DROWNED_ID = Identifier.of("minecraft", "entities/drowned");
    private static final Identifier ENDERMAN_ID = Identifier.of("minecraft", "entities/enderman");
    private static final Identifier ENDERMITE_ID = Identifier.of("minecraft", "entities/endermite");
    private static final Identifier EVOKER_ID = Identifier.of("minecraft", "entities/evoker");
    private static final Identifier GHAST_ID = Identifier.of("minecraft", "entities/ghast");
    private static final Identifier GIANT_ID = Identifier.of("minecraft", "entities/giant");
    private static final Identifier GUARDIAN_ID = Identifier.of("minecraft", "entities/guardian");
    private static final Identifier HOGLIN_ID = Identifier.of("minecraft", "entities/hoglin");
    private static final Identifier HUSK_ID = Identifier.of("minecraft", "entities/husk");
    private static final Identifier ILLUSIONER_ID = Identifier.of("minecraft", "entities/illusioner");
    private static final Identifier MAGMA_CUBE_ID = Identifier.of("minecraft", "entities/magma_cube");
    private static final Identifier PHANTOM_ID = Identifier.of("minecraft", "entities/phantom");
    private static final Identifier PIGLIN_ID = Identifier.of("minecraft", "entities/piglin");
    private static final Identifier PIGLIN_BRUTE_ID = Identifier.of("minecraft", "entities/piglin_brute");
    private static final Identifier PILLAGER_ID = Identifier.of("minecraft", "entities/pillager");
    private static final Identifier RAVAGER_ID = Identifier.of("minecraft", "entities/ravager");
    private static final Identifier SHULKER_ID = Identifier.of("minecraft", "entities/shulker");
    private static final Identifier SILVERFISH_ID = Identifier.of("minecraft", "entities/silverfish");
    private static final Identifier SLIME_ID = Identifier.of("minecraft", "entities/slime");
    private static final Identifier STRAY_ID = Identifier.of("minecraft", "entities/stray");
    private static final Identifier VINDICATOR_ID = Identifier.of("minecraft", "entities/vindicator");
    private static final Identifier WARDEN_ID = Identifier.of("minecraft", "entities/warden");
    private static final Identifier WITCH_ID = Identifier.of("minecraft", "entities/witch");
    private static final Identifier WITHER_SKELETON_ID = Identifier.of("minecraft", "entities/wither_skeleton");
    private static final Identifier ZOGLIN_ID = Identifier.of("minecraft", "entities/zoglin");
    private static final Identifier ZOMBIE_VILLAGER_ID = Identifier.of("minecraft", "entities/zombie_villager");
    private static final Identifier ZOMBIFIED_PIGLIN_ID = Identifier.of("minecraft", "entities_zombified_piglin");

    private static ArrayList<Identifier> ids = new ArrayList<>();

    public static void modifyLootTables() {
        ids.add(ZOMBIE_ID);
        ids.add(SKELETON_ID);
        ids.add(SPIDER_ID);
        ids.add(CAVE_SPIDER_ID);
        ids.add(CREEPER_ID);
        ids.add(BLAZE_ID);
        ids.add(BOGGED_ID);
        ids.add(BREEZE_ID);
        ids.add(DROWNED_ID);
        ids.add(ENDERMAN_ID);
        ids.add(ENDERMITE_ID);
        ids.add(EVOKER_ID);
        ids.add(GHAST_ID);
        ids.add(GIANT_ID);
        ids.add(GUARDIAN_ID);
        ids.add(HOGLIN_ID);
        ids.add(HUSK_ID);
        ids.add(ILLUSIONER_ID);
        ids.add(MAGMA_CUBE_ID);
        ids.add(PHANTOM_ID);
        ids.add(PIGLIN_ID);
        ids.add(PIGLIN_BRUTE_ID);
        ids.add(PILLAGER_ID);
        ids.add(RAVAGER_ID);
        ids.add(SHULKER_ID);
        ids.add(SILVERFISH_ID);
        ids.add(SLIME_ID);
        ids.add(STRAY_ID);
        ids.add(VINDICATOR_ID);
        ids.add(WARDEN_ID);
        ids.add(WITCH_ID);
        ids.add(WITHER_SKELETON_ID);
        ids.add(ZOGLIN_ID);
        ids.add(ZOMBIE_VILLAGER_ID);
        ids.add(ZOMBIFIED_PIGLIN_ID);

        LootTableEvents.MODIFY.register((key, tableBuilder, source, registryLookup) -> {
            if (ids.contains(key.getValue())) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1.0f))
                        .conditionally(RandomChanceLootCondition.builder(0.04f))
                        .apply(EnchantedCountIncreaseLootFunction.builder(registryLookup, UniformLootNumberProvider.create(0.0f, 1.0f)))
                        .with(ItemEntry.builder(ModItems.TOTEM_HEAD_UNDYING))
                        .with(ItemEntry.builder(ModItems.TOTEM_HEAD_BREATHING))
                        .with(ItemEntry.builder(ModItems.TOTEM_HEAD_EXPLOSION))
                        .with(ItemEntry.builder(ModItems.TOTEM_HEAD_FALLING))
                        .with(ItemEntry.builder(ModItems.TOTEM_HEAD_ORES))
                        .with(ItemEntry.builder(ModItems.TOTEM_HEAD_FIRE))
                        .with(ItemEntry.builder(ModItems.TOTEM_HEAD_RECALL))
                        .with(ItemEntry.builder(ModItems.TOTEM_HEAD_TIME))
                        .with(ItemEntry.builder(ModItems.TOTEM_HEAD_REPAIR));
                tableBuilder.pool(poolBuilder.build());
            }
        });
    }
}