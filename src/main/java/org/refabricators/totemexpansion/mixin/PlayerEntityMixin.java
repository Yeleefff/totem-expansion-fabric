package org.refabricators.totemexpansion.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import org.refabricators.totemexpansion.TotemExpansion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Set;

import static org.refabricators.totemexpansion.TotemExpansion.activeRecallTotems;


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
        try {
            for (int i = 0; i < activeRecallTotems.size(); i++) {
                World world = (World) activeRecallTotems.get(i).get(0);
                PlayerEntity player = (PlayerEntity) activeRecallTotems.get(i).get(1);
                Integer direction = (Integer) activeRecallTotems.get(i).get(2);

                if (this.getUuidAsString().equals(player.getUuidAsString())) {
                    this.isSpaceEmpty = world.isBlockSpaceEmpty(null, new Box(this.getX(), this.getY(), this.getZ(), this.getX(), this.getY() + stepSize * direction, this.getZ()));

                    this.setInvulnerable(true);
                    this.setNoGravity(true);
                    if (this.isSpaceEmpty) this.setPos(this.getX(), this.getY() + stepSize * direction, this.getZ());

                    if ((!this.isSpaceEmpty && direction == 1) || this.getY() >= 600) {
                        if (player instanceof ServerPlayerEntity serverPlayerEntity) {
                            System.out.println("Reached y=600 or the space above is obstructed");
                            direction = -1;
                            activeRecallTotems.set(i, List.of(world, player, direction));

                            this.spawnTarget = serverPlayerEntity.getRespawnTarget(true, entity -> {});
                            if (world instanceof ServerWorld serverWorld) this.teleport(serverWorld, this.spawnTarget.pos().getX(), this.getY(), this.spawnTarget.pos().getZ(), Set.of(), this.getYaw(), this.getPitch());
                        }
                    }

                    this.isSpaceEmpty = world.isBlockSpaceEmpty(null, new Box(this.getX(), this.getY(), this.getZ(), this.getX(), this.getY() + stepSize * direction, this.getZ()));

                    if (!this.isSpaceEmpty && direction == -1) {
                        if (player instanceof ServerPlayerEntity serverPlayerEntity) {
                            System.out.println("Reached the ground or the space below is obstructed");

                            this.spawnTarget = serverPlayerEntity.getRespawnTarget(true, entity -> {});
                            this.teleportTo(this.spawnTarget);

                            this.fallDistance = 0;
                            this.setNoGravity(false);
                            this.setInvulnerable(false);
                            activeRecallTotems.remove(i);
                            System.out.println("Totem removed from recall list");
                        }
                    }

                    break;
                }
            }

        } catch (IndexOutOfBoundsException e) {
            TotemExpansion.LOGGER.warn("Error in PlayerEntityMixin: " + e.getMessage());
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
