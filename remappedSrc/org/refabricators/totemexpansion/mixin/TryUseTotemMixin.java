package org.refabricators.totemexpansion.mixin;

import org.refabricators.totemexpansion.TotemExpansion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;

@Mixin(LivingEntity.class)
public abstract class TryUseTotemMixin {
    @Inject(method = "tryUseTotem", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;copy()Lnet/minecraft/item/ItemStack;"))
    private void injectCustomTotems(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        TotemExpansion.LOGGER.info("works");
    }
}
