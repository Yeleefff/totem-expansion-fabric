package org.refabricators.totemexpansion.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.Attackable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.refabricators.totemexpansion.event.CustomTotemUsedCallback;
import org.refabricators.totemexpansion.item.ModItems;
import org.refabricators.totemexpansion.item.TotemBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(LivingEntity.class)
public abstract class TryUseTotemMixin extends Entity implements Attackable
{
    public TryUseTotemMixin(EntityType<?> type, World world)
    {
        super(type, world);
    }

    @WrapOperation(method = "tryUseTotem", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    private boolean injectCustomTotemCheck(ItemStack stack, Item item, Operation<Boolean> original, DamageSource source)
    {
        return original.call(stack, item) || (stack.getItem() instanceof TotemBase && ((TotemBase)stack.getItem()).validDamageType(source) || stack.isOf(ModItems.TOTEM_ORES));
    }

    @Inject(method = "tryUseTotem", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;setHealth(F)V"), locals = LocalCapture.CAPTURE_FAILEXCEPTION, cancellable = true)
    private void injectCustomTotemEffects(DamageSource source, CallbackInfoReturnable<Boolean> cir, ItemStack itemStack)
    {
        if(itemStack.getItem() instanceof TotemBase && ((TotemBase) itemStack.getItem()).validDamageType(source) || itemStack.isOf(ModItems.TOTEM_ORES))
        {
            CustomTotemUsedCallback.EVENT.invoker().invoke(((LivingEntity) (Object) this), itemStack);
            cir.setReturnValue(itemStack != null);
        }
    }

    @ModifyVariable(method = "tryUseTotem", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/entity/LivingEntity;getStackInHand(Lnet/minecraft/util/Hand;)Lnet/minecraft/item/ItemStack;"))
    private ItemStack setTotemToPop(ItemStack itemStack, DamageSource source)
    {
        if (this.isPlayer())
        {
            PlayerInventory inventory = ((InventoryAccessor) this).getInventory();
            ItemStack totemUndying = Items.TOTEM_OF_UNDYING.getDefaultStack();
            ItemStack totemFalling = ModItems.TOTEM_FALLING.getDefaultStack();
            ItemStack totemFire = ModItems.TOTEM_FIRE.getDefaultStack();
            ItemStack totemBreathing = ModItems.TOTEM_BREATHING.getDefaultStack();
            ItemStack totemExplosion = ModItems.TOTEM_EXPLOSION.getDefaultStack();

            if (inventory.getMainHandStack().isOf(ModItems.TOTEM_ORES))
            {
                return inventory.getMainHandStack();
            }
            if (inventory.contains(totemUndying))
            {
                return inventory.getStack(inventory.getSlotWithStack(totemUndying));
            }
            if (inventory.contains(totemFalling))
            {
                ItemStack totemInInventory = inventory.getStack(inventory.getSlotWithStack(totemFalling));
                if (((TotemBase) totemInInventory.getItem()).validDamageType(source))
                    return totemInInventory;
            }
            if (inventory.contains(totemFire))
            {
                ItemStack totemInInventory = inventory.getStack(inventory.getSlotWithStack(totemFire));
                if (((TotemBase) totemInInventory.getItem()).validDamageType(source))
                    return totemInInventory;
            }
            if (inventory.contains(totemBreathing))
            {
                ItemStack totemInInventory = inventory.getStack(inventory.getSlotWithStack(totemBreathing));
                if (((TotemBase) totemInInventory.getItem()).validDamageType(source))
                    return totemInInventory;
            }
            if (inventory.contains(totemExplosion))
            {
                ItemStack totemInInventory = inventory.getStack(inventory.getSlotWithStack(totemExplosion));
                if (((TotemBase) totemInInventory.getItem()).validDamageType(source))
                    return totemInInventory;
            }
        }
        return itemStack;
    }

}