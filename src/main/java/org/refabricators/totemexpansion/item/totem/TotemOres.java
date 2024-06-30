package org.refabricators.totemexpansion.item.totem;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.refabricators.totemexpansion.TotemExpansion;
import org.refabricators.totemexpansion.item.TotemBase;
import org.refabricators.totemexpansion.mixin.TotemUseInvoker;

public class TotemOres extends TotemBase {
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ((TotemUseInvoker) user).useTotem(world.getDamageSources().generic());
        return super.use(world, user, hand);
    }

    @Override
    public void addDamageTypes() {
    }

    @Override
    public void addEffects()
    {
        effects.add(new StatusEffectInstance(TotemExpansion.SPELUNKING_EFFECT, 900));
    }
}