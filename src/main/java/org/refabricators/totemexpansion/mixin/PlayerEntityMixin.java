package org.refabricators.totemexpansion.mixin;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import org.refabricators.totemexpansion.TotemExpansionClient;
import org.refabricators.totemexpansion.network.SyncPlayerDataS2C;
import org.refabricators.totemexpansion.util.PlayerData;
import org.refabricators.totemexpansion.util.StateSaverAndLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;


@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    private final int stepSize = 6;
    private boolean isSpaceEmpty;
    private TeleportTarget spawnTarget;

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "tick", at = @At(value = "HEAD"))
    private void injectRecall(CallbackInfo info) {
        PlayerEntity player = (PlayerEntity)(Object) this;
        PlayerData playerState;

        if (this.getWorld().isClient) {
            playerState = TotemExpansionClient.playerState;
        } else {
            playerState = StateSaverAndLoader.getPlayerState(player);
        }

        if (playerState.usedRecallTotem) {
            this.isSpaceEmpty = this.getWorld().isBlockSpaceEmpty(null, new Box(this.getX(), this.getY(), this.getZ(), this.getX(), this.getY() + stepSize * playerState.recallDirection, this.getZ()));

            this.setInvulnerable(true);
            this.setNoGravity(true);
            if (this.isSpaceEmpty)
                this.setPos(this.getX(), this.getY() + stepSize * playerState.recallDirection, this.getZ());

            if ((!this.isSpaceEmpty && playerState.recallDirection == 1) || this.getY() >= 600) {
                if (player instanceof ServerPlayerEntity serverPlayerEntity) {
                    playerState.recallDirection = -1;
                    ServerPlayNetworking.send(serverPlayerEntity, new SyncPlayerDataS2C(playerState.usedRecallTotem, playerState.recallDirection));

                    this.spawnTarget = serverPlayerEntity.getRespawnTarget(true, entity -> {});
                    if (this.getWorld() instanceof ServerWorld serverWorld)
                        this.teleport(serverWorld, this.spawnTarget.pos().getX(), this.getY(), this.spawnTarget.pos().getZ(), Set.of(), this.getYaw(), this.getPitch());
                }
            }

            this.isSpaceEmpty = this.getWorld().isBlockSpaceEmpty(null, new Box(this.getX(), this.getY(), this.getZ(), this.getX(), this.getY() + stepSize * playerState.recallDirection, this.getZ()));

            if (!this.isSpaceEmpty && playerState.recallDirection == -1) {
                if (player instanceof ServerPlayerEntity serverPlayerEntity) {
                    this.spawnTarget = serverPlayerEntity.getRespawnTarget(true, entity -> {});
                    this.teleportTo(this.spawnTarget);

                    this.fallDistance = 0;
                    this.setNoGravity(false);
                    this.setInvulnerable(false);

                    playerState.usedRecallTotem = false;
                    playerState.recallDirection = 1;
                    ServerPlayNetworking.send(serverPlayerEntity, new SyncPlayerDataS2C(playerState.usedRecallTotem, playerState.recallDirection));
                }
            }
        }
    }

    @Inject(method = "tick", at = @At(value = "HEAD"))
    private void injectDelevitationCode (CallbackInfo info){
        if (this.isSneaking() && this.hasStatusEffect(StatusEffects.LEVITATION)) {
            this.setPos(this.getX(), this.getY() - 0.25, this.getZ());
        }

        if (this.getY() < 5) {
            ((TotemUseInvoker) this).useTotem(this.getDamageSources().outOfWorld());
        }
    }
}
