package org.refabricators.totemexpansion.item.totem;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.apache.logging.log4j.core.jmx.Server;
import org.refabricators.totemexpansion.item.TotemBase;
import org.refabricators.totemexpansion.mixin.ServerWorldMixin;
import org.refabricators.totemexpansion.mixin.TotemUseInvoker;

public class TotemTime extends TotemBase {
    public static boolean triggered = false;

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ((TotemUseInvoker) user).useTotem(world.getDamageSources().generic());
        triggered = true;

        return super.use(world, user, hand);
    }

    @Override
    public void addDamageTypes() {

    }

    @Override
    public void addEffects() {
    }
}
