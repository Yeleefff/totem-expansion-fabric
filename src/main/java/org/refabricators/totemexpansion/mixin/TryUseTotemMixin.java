package org.refabricators.totemexpansion.mixin;

import org.refabricators.totemexpansion.TotemExpansion;
import org.refabricators.totemexpansion.event.CustomTotemUsedCallback;
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

    @WrapOperation(method = "tryUseTotem", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    private boolean injectCustomTotemCheck(ItemStack stack, Item item, Operation<Boolean> original, DamageSource source) {
        TotemExpansion.LOGGER.info("works before itemstack copy");
        return original.call(stack, item) || (stack.getItem() instanceof BaseTotem && ((BaseTotem)stack.getItem()).validDamageType(source));
    }

    @Inject(method = "tryUseTotem", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;setHealth(F)V"), locals = LocalCapture.CAPTURE_FAILEXCEPTION, cancellable = true)
    private void injectCustomTotemEffects(DamageSource source, CallbackInfoReturnable<Boolean> cir, ItemStack itemStack) {
        TotemExpansion.LOGGER.info("works before sethealth");

        if(itemStack.getItem() instanceof BaseTotem && ((BaseTotem)itemStack.getItem()).validDamageType(source)) {
            CustomTotemUsedCallback.EVENT.invoker().invoke(((LivingEntity) (Object) this), itemStack);
            cir.setReturnValue(itemStack != null);
        }
        
    }
}