package org.refabricators.totemexpansion.item.totem;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.refabricators.totemexpansion.item.TotemBase;
import org.refabricators.totemexpansion.mixin.TotemUseInvoker;
import org.refabricators.totemexpansion.util.StateSaverAndLoader;

public class TotemTime extends TotemBase {
    public TotemTime(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ((TotemUseInvoker) user).useTotem(world.getDamageSources().generic());

        if (!world.isClient) {
            if (world.getServer() == null) throw new AssertionError("world.getServer() was null");

            StateSaverAndLoader serverState = StateSaverAndLoader.getServerState(world.getServer());
            serverState.activeTimeTotems.add(0);
            serverState.markDirty();
        }

        return super.use(world, user, hand);
    }

    @Override
    public void addDamageTypes() {
    }
}
