package org.refabricators.totemexpansion.villager;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradedItem;
import org.refabricators.totemexpansion.item.ModItems;

public class ModCustomTrades {
    public static void registerCustomTrades() {
        TradeOfferHelper.registerVillagerOffers(ModVillagers.WITCH_DOCTOR, 1, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 4),
                    new ItemStack(ModItems.TOTEM_BASE, 1),
                    4, 5, 0.15f));
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 15),
                    new ItemStack(ModItems.TOTEM_HEAD_FALLING, 1),
                    2, 10, 0.15f));
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 15),
                    new ItemStack(ModItems.TOTEM_HEAD_FIRE, 1),
                    2, 10, 0.15f));
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 15),
                    new ItemStack(ModItems.TOTEM_HEAD_BREATHING, 1),
                    2, 10, 0.15f));
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 15),
                    new ItemStack(ModItems.TOTEM_HEAD_EXPLOSION, 1),
                    2, 10, 0.15f));
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 15),
                    new ItemStack(ModItems.TOTEM_HEAD_REPAIR, 1),
                    2, 10, 0.15f));
                });

        TradeOfferHelper.registerVillagerOffers(ModVillagers.WITCH_DOCTOR, 3, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 21),
                    new ItemStack(ModItems.TOTEM_HEAD_UNDYING, 1),
                    2, 15, 0.2f));
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 17),
                    new ItemStack(Items.ZOMBIE_HEAD, 1),
                    2, 15, 0.3f));
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 17),
                    new ItemStack(Items.SKELETON_SKULL, 1),
                    2, 15, 0.3f));
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 30),
                    new ItemStack(Items.CREEPER_HEAD, 1),
                    2, 15, 0.3f));
        });

        TradeOfferHelper.registerVillagerOffers(ModVillagers.WITCH_DOCTOR, 5, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 23),
                    new ItemStack(ModItems.TOTEM_HEAD_ORES, 1),
                    1, 20, 0.2f));
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 23),
                    new ItemStack(ModItems.TOTEM_HEAD_TIME, 1),
                    1, 20, 0.2f));
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 30),
                    new ItemStack(ModItems.TOTEM_RECALL, 1),
                    1, 20, 0.2f));
        });
    }
}
