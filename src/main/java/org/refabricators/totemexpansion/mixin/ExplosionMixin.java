package org.refabricators.totemexpansion.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.explosion.ExplosionImpl;
import org.refabricators.totemexpansion.item.ModItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ExplosionImpl.class)
public abstract class ExplosionMixin {

    @Shadow
    private DamageSource damageSource;
    @Shadow
    private Vec3d pos;
    @Shadow
    private ServerWorld world;
    @Shadow
    private float power;

    @Inject(method = "explode", at = @At(value = "HEAD"), cancellable = true)
    private void injectExplosionTotemCheck(CallbackInfo callbackInfo) {
        float f = this.power * 2.0F;
        int i = MathHelper.floor(this.pos.x - (double)f - 1.0);
        int j = MathHelper.floor(this.pos.x + (double)f + 1.0);
        int k = MathHelper.floor(this.pos.y - (double)f - 1.0);
        int l = MathHelper.floor(this.pos.y + (double)f + 1.0);
        int m = MathHelper.floor(this.pos.z - (double)f - 1.0);
        int n = MathHelper.floor(this.pos.z + (double)f + 1.0);

        for (Entity entity : this.world.getOtherEntities(null, new Box(i, k, m, j, l, n))) {
            PlayerEntity player;

            if (entity instanceof PlayerEntity && !(player = (PlayerEntity) entity).isCreative() ) {
                if (player.getInventory().contains(ModItems.TOTEM_EXPLOSION.getDefaultStack())) {
                    ((TotemUseInvoker) player).useTotem(this.damageSource);
                    callbackInfo.cancel();
                }
            }
        }
    }
}
