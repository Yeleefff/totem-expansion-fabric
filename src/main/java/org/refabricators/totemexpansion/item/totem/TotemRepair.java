package org.refabricators.totemexpansion.item.totem;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.refabricators.totemexpansion.item.TotemBase;

public class TotemRepair extends TotemBase {

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return null;
    }

    @Override
    public void addDamageTypes() {

    }

    @Override
    public void addEffects() {

    }
}
