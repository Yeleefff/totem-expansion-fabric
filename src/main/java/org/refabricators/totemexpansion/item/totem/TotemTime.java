package org.refabricators.totemexpansion.item.totem;

import net.minecraft.item.Item;
import net.minecraft.util.Rarity;

public class TotemTime extends Item {
    public TotemTime() {
        super(new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON));
    }
}
