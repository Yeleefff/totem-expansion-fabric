package org.refabricators.totemexpansion.mixin;

import org.refabricators.totemexpansion.TotemExpansion;
import org.refabricators.totemexpansion.event.TotemUsedCallback;
import org.refabricators.totemexpansion.item.BaseTotem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;


@Mixin(LivingEntity.class)
public abstract class TryUseTotemMixin {

    @WrapOperation(method = "tryUseTotem", at = @At(value = "INVOKE", target = "\tLnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    private boolean injectCustomTotemCheck(LivingEntity instance, Item item, Operation<Boolean> original) {
        return (original.call(instance, item) || item instanceof BaseTotem);
    }

    @Inject(method = "tryUseTotem", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;setHealth(F)V"), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    private void injectCustomTotemEffects(DamageSource source, CallbackInfoReturnable<Boolean> cir, ItemStack itemStack, ItemStack itemStack2) {
        TotemExpansion.LOGGER.info("works before sethealth");
        if(itemStack2.getItem() instanceof BaseTotem) {
            TotemUsedCallback.EVENT.invoker().invoke(((LivingEntity) (Object) this), itemStack2);
            cir.setReturnValue(itemStack != null);
        }
        
    }
}
