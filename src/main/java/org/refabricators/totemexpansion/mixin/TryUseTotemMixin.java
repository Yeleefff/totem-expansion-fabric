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

import java.util.ArrayList;

@Mixin(LivingEntity.class)
public abstract class TryUseTotemMixin extends Entity implements Attackable {
    public TryUseTotemMixin(EntityType<?> type, World world)
    {
        super(type, world);
    }

    @WrapOperation(method = "tryUseTotem", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    private boolean injectCustomTotemCheck(ItemStack stack, Item item, Operation<Boolean> original, DamageSource source) {
        return original.call(stack, item) || (stack.getItem() instanceof TotemBase && ((TotemBase)stack.getItem()).validDamageType(source) || stack.isOf(ModItems.TOTEM_ORES) || stack.isOf(ModItems.TOTEM_TIME) || stack.isOf(ModItems.TOTEM_RECALL) || stack.isOf(ModItems.TOTEM_REPAIR));
    }

    // Calls the totemUse event if supposed to, skipping vanilla setHealth stuff
    @Inject(method = "tryUseTotem", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;setHealth(F)V"), locals = LocalCapture.CAPTURE_FAILEXCEPTION, cancellable = true)
    private void injectCustomTotemEffects(DamageSource source, CallbackInfoReturnable<Boolean> cir, ItemStack itemStack) {

        if(itemStack.getItem() instanceof TotemBase && ((TotemBase) itemStack.getItem()).validDamageType(source) || itemStack.isOf(ModItems.TOTEM_ORES) || itemStack.isOf(ModItems.TOTEM_TIME) || itemStack.isOf(ModItems.TOTEM_RECALL) || itemStack.isOf(ModItems.TOTEM_REPAIR)) {
            CustomTotemUsedCallback.EVENT.invoker().invoke(((LivingEntity) (Object) this), itemStack);
            cir.setReturnValue(itemStack != null);
        }
    }

    // Gets what totem should be used
    @ModifyVariable(method = "tryUseTotem", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/entity/LivingEntity;getStackInHand(Lnet/minecraft/util/Hand;)Lnet/minecraft/item/ItemStack;"))
    private ItemStack setTotemToPop(ItemStack itemStack, DamageSource source) {
        if (this.isPlayer()) {
            PlayerInventory inventory = ((InventoryAccessor) this).getInventory();
            ItemStack totemUndying = Items.TOTEM_OF_UNDYING.getDefaultStack();
            ItemStack totemFalling = ModItems.TOTEM_FALLING.getDefaultStack();
            ItemStack totemFire = ModItems.TOTEM_FIRE.getDefaultStack();
            ItemStack totemBreathing = ModItems.TOTEM_BREATHING.getDefaultStack();
            ItemStack totemExplosion = ModItems.TOTEM_EXPLOSION.getDefaultStack();
            ItemStack totemRepair = ModItems.TOTEM_REPAIR.getDefaultStack();

            if (inventory.contains(totemRepair)) {

                ArrayList<ItemStack> inventorySlots = new ArrayList<>();
                inventorySlots.addAll(inventory.main);
                inventorySlots.addAll(inventory.armor);
                inventorySlots.addAll(inventory.offHand);

                // NOTE: getSlotWithStack() only searches main, include a check for if the method returns -1
                // If the totem is known to be in the inventory, then return the offhand since that is the only other possible slot
                for (ItemStack item : inventorySlots) {
                    if (item.isDamageable() && item.getDamage() >= item.getMaxDamage()) {
                        item.setDamage(0);
                        return inventory.getSlotWithStack(totemRepair) != -1 ? inventory.getStack(inventory.getSlotWithStack(totemRepair)) : inventory.offHand.get(0);
                    }
                }
            }

            if (inventory.getMainHandStack().isOf(ModItems.TOTEM_ORES) || inventory.getMainHandStack().isOf(ModItems.TOTEM_TIME) || inventory.getMainHandStack().isOf(ModItems.TOTEM_RECALL)) {
                return inventory.getMainHandStack();
            }

            if (inventory.contains(totemUndying)) {
                return inventory.getStack(inventory.getSlotWithStack(totemUndying));
//                return inventory.getSlotWithStack(totemUndying) != -1 ? inventory.getStack(inventory.getSlotWithStack(totemUndying)) : inventory.offHand.get(0);
            }

            if (inventory.contains(totemFalling)) {
                ItemStack totemInInventory = inventory.getStack(inventory.getSlotWithStack(totemFalling));
//                ItemStack totemInInventory = inventory.getSlotWithStack(totemFalling) != -1 ? inventory.getStack(inventory.getSlotWithStack(totemFalling)) : inventory.offHand.get(0);
                if (((TotemBase) totemInInventory.getItem()).validDamageType(source))
                    return totemInInventory;
            }

            if (inventory.contains(totemFire)) {
                ItemStack totemInInventory = inventory.getStack(inventory.getSlotWithStack(totemFire));
//                ItemStack totemInInventory = inventory.getSlotWithStack(totemFire) != -1 ? inventory.getStack(inventory.getSlotWithStack(totemFire)) : inventory.offHand.get(0);
                if (((TotemBase) totemInInventory.getItem()).validDamageType(source))
                    return totemInInventory;
            }

            if (inventory.contains(totemBreathing)) {
                ItemStack totemInInventory = inventory.getStack(inventory.getSlotWithStack(totemBreathing));
//                ItemStack totemInInventory = inventory.getSlotWithStack(totemBreathing) != -1 ? inventory.getStack(inventory.getSlotWithStack(totemBreathing)) : inventory.offHand.get(0);
                if (((TotemBase) totemInInventory.getItem()).validDamageType(source))
                    return totemInInventory;
            }

            if (inventory.contains(totemExplosion)) {
//                ItemStack totemInInventory = inventory.getStack(inventory.getSlotWithStack(totemExplosion));
                ItemStack totemInInventory = inventory.getSlotWithStack(totemExplosion) != -1 ? inventory.getStack(inventory.getSlotWithStack(totemExplosion)) : inventory.offHand.get(0);
                if (((TotemBase) totemInInventory.getItem()).validDamageType(source))
                    return totemInInventory;
            }
        }
        return itemStack;
    }

}