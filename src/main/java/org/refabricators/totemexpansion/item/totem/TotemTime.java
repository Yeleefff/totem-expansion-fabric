package org.refabricators.totemexpansion.item.totem;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.refabricators.totemexpansion.item.TotemBase;
import org.refabricators.totemexpansion.mixin.TotemUseInvoker;

public class TotemTime extends TotemBase {
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ((TotemUseInvoker) user).useTotem(world.getDamageSources().generic());
        int timeIncrement = 120;

        if (!world.getDimension().hasFixedTime() && world instanceof ServerWorld serverWorld) {
            for (int i = 0; i < (int) (12000 / timeIncrement); i++) {
                serverWorld.setTimeOfDay(world.getTimeOfDay() + timeIncrement);
            }
        }

        return super.use(world, user, hand);
    }

    @Override
    public void addDamageTypes() {

    }

    @Override
    public void addEffects() {
    }
}
