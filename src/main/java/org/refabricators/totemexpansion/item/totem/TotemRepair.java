package org.refabricators.totemexpansion.item.totem;

import net.minecraft.item.Item;
import net.minecraft.util.Rarity;

public class TotemRepair extends Item {
    public TotemRepair() {
        super(new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON));
    }
}
