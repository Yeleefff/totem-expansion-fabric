package org.refabricators.totemexpansion.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import org.refabricators.totemexpansion.TotemExpansion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    private final int stepSize = 5;
    private int direction = 1;
    private boolean isSpaceEmpty;
    private TeleportTarget spawnTarget;

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "tick", at = @At(value = "HEAD"))
    private void injectRecall(CallbackInfo info) {
        for (int i = 0; i < TotemExpansion.activeRecallTotems.size(); i++) {
            System.out.println("i: " + i + "   Array size: " + TotemExpansion.activeRecallTotems.size());
            World world = (World) TotemExpansion.activeRecallTotems.get(i).get(0);
            PlayerEntity player = (PlayerEntity) TotemExpansion.activeRecallTotems.get(i).get(1);

            this.isSpaceEmpty = world.isBlockSpaceEmpty(null, new Box(this.getX(), this.getY(), this.getZ(), this.getX(), this.getY() + stepSize * direction, this.getZ()));

            this.setInvulnerable(true);
            if (this.isSpaceEmpty) this.setPos(this.getX(), this.getY() + stepSize * direction, this.getZ());

            if ((!this.isSpaceEmpty && direction == 1) || this.getY() >= 600) {
                if (player instanceof ServerPlayerEntity serverPlayerEntity) {
                    this.direction = -1;

                    this.spawnTarget = serverPlayerEntity.getRespawnTarget(true, entity -> {});
                    this.setPos(this.spawnTarget.pos().getX(), this.getY(), this.spawnTarget.pos().getZ());
                }
            }

            if (!this.isSpaceEmpty && direction == -1) {
                if (player instanceof ServerPlayerEntity serverPlayerEntity) {
                    this.direction = 1;

                    this.spawnTarget = serverPlayerEntity.getRespawnTarget(true, entity -> {});
                    this.teleportTo(this.spawnTarget);

                    this.setInvulnerable(false);
                    TotemExpansion.activeRecallTotems.remove(i);
                    i--;
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
