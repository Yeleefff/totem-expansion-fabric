package org.refabricators.totemexpansion.item.totem;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.refabricators.totemexpansion.TotemExpansion;
import org.refabricators.totemexpansion.item.TotemBase;
import org.refabricators.totemexpansion.mixin.TotemUseInvoker;

import java.util.ArrayList;
import java.util.List;

public class TotemRecall extends TotemBase {
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ((TotemUseInvoker) user).useTotem(world.getDamageSources().generic());

        TotemExpansion.activeTotems.add(List.of(world, user));

        return super.use(world, user, hand);
    }

    @Override
    public void addDamageTypes() {
    }

    @Override
    public void addEffects() {
    }
}
