package org.refabricators.totemexpansion.item.totem;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionTypes;
import org.refabricators.totemexpansion.item.TotemBase;
import org.refabricators.totemexpansion.mixin.TotemUseInvoker;
import org.refabricators.totemexpansion.network.SyncPlayerDataS2C;
import org.refabricators.totemexpansion.util.PlayerData;
import org.refabricators.totemexpansion.util.StateSaverAndLoader;

public class TotemRecall extends TotemBase {
    public TotemRecall(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (world.getDimensionEntry().matchesKey(DimensionTypes.OVERWORLD)) { // world.getDimensionEntry().matchesKey(DimensionTypes.OVERWORLD_CAVES)
            ((TotemUseInvoker) user).useTotem(world.getDamageSources().generic());

            if (!world.isClient) {
                StateSaverAndLoader serverState = StateSaverAndLoader.getServerState(world.getServer());
                PlayerData playerState = StateSaverAndLoader.getOrCreatePlayerData(user);

                if (playerState.usedRecallTotem) playerState.recallDirection = 1;
                else playerState.usedRecallTotem = true;
                serverState.markDirty();

                ServerPlayNetworking.send((ServerPlayerEntity) user, new SyncPlayerDataS2C(playerState.usedRecallTotem, playerState.recallDirection));
            }
        }

        return super.use(world, user, hand);
    }

    @Override
    public void addDamageTypes() {
    }
}
