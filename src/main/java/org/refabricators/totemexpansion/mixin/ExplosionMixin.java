package org.refabricators.totemexpansion.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionImpl;
import org.refabricators.totemexpansion.item.ModItems;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ExplosionImpl.class)
public abstract class ExplosionMixin {
    @Shadow @Final
    private DamageSource damageSource;
    @Shadow @Final @Mutable
    private Explosion.DestructionType destructionType;
    @Shadow
    public abstract ServerWorld getWorld();

    @Inject(method = "damageEntities", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/server/world/ServerWorld;getOtherEntities(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/Box;)Ljava/util/List;"), cancellable = true)
    private void injectExplosionTotemCheck(CallbackInfo callbackInfo, @Local List<Entity> entities) {
        for (Entity entity : entities) {
            PlayerEntity player;

            if (!(!(entity instanceof PlayerEntity) || (player = (PlayerEntity)entity).isSpectator() || player.isCreative() && player.getAbilities().flying)) {
                if (player.getInventory().contains(ModItems.TOTEM_EXPLOSION.getDefaultStack())) {
                    ((TotemUseInvoker) player).useTotem(this.damageSource);
                    this.destructionType = Explosion.DestructionType.KEEP;

                    callbackInfo.cancel();
                }
            }
        }
    }
}
