package org.refabricators.totemexpansion.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.explosion.Explosion;
import org.refabricators.totemexpansion.item.ModItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Explosion.class)
public abstract class ExplosionMixin {
    @Shadow
    private DamageSource damageSource;

    @Inject(method = "collectBlocksAndDamageEntities", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/World;getOtherEntities(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/Box;)Ljava/util/List;"), cancellable = true)
    private void injectExplosionTotemCheck(CallbackInfo callbackInfo, @Local List<Entity> entityList) {
        for (Entity entity : entityList) {
            PlayerEntity player;

            if (!(!(entity instanceof PlayerEntity) || (player = (PlayerEntity)entity).isSpectator() || player.isCreative() && player.getAbilities().flying)) {
                if (player.getInventory().contains(ModItems.TOTEM_EXPLOSION.getDefaultStack())) {
                    System.out.println(this.damageSource);
                    ((TotemUseInvoker) player).useTotem(this.damageSource);
                    ((Explosion)(Object) this).getAffectedBlocks().clear();

                    callbackInfo.cancel();
                }
            }
        }
    }
}
