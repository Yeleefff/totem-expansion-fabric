package org.refabricators.totemexpansion.event;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.Items;
import org.refabricators.totemexpansion.TotemExpansion;
import org.refabricators.totemexpansion.item.ModItems;
import org.refabricators.totemexpansion.item.TotemBase;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

@FunctionalInterface
public interface CustomTotemUsedCallback {
    void invoke(LivingEntity entity, ItemStack stack, DamageSource source);

      Event<CustomTotemUsedCallback> EVENT = EventFactory.createArrayBacked(CustomTotemUsedCallback.class,
            (listeners) -> (LivingEntity entity, ItemStack stack, DamageSource source) -> {
                if(!(stack.getItem() instanceof TotemBase)) return;

                for (CustomTotemUsedCallback listener : listeners) {
                    listener.invoke(entity, stack, source);

                    if (stack.isOf(ModItems.TOTEM_FALLING)) {
                        entity.setHealth(1.0f);
                        entity.getWorld().sendEntityStatus(entity, TotemExpansion.USE_TOTEM_FALLING);
                    } else if (stack.isOf(ModItems.TOTEM_FIRE)) {
                        entity.setHealth(1.0f);
                        entity.getWorld().sendEntityStatus(entity, TotemExpansion.USE_TOTEM_FIRE);
                    } else if (stack.isOf(ModItems.TOTEM_BREATHING)) {
                        entity.setHealth(1.0f);
                        entity.getWorld().sendEntityStatus(entity, TotemExpansion.USE_TOTEM_BREATHING);
                    } else if (stack.isOf(ModItems.TOTEM_EXPLOSION)) {
                        entity.getWorld().sendEntityStatus(entity, TotemExpansion.USE_TOTEM_EXPLOSION);
                    } else if (stack.isOf(ModItems.TOTEM_ORES)) {
                        entity.getWorld().sendEntityStatus(entity, TotemExpansion.USE_TOTEM_ORES);
                    } else if (stack.isOf(ModItems.TOTEM_REPAIR)) {
                        entity.getWorld().sendEntityStatus(entity, TotemExpansion.USE_TOTEM_REPAIR);
                    } else if (stack.isOf(ModItems.TOTEM_TIME)) {
                        entity.getWorld().sendEntityStatus(entity, TotemExpansion.USE_TOTEM_TIME);
                    } else if (stack.isOf(ModItems.TOTEM_RECALL)) {
                        entity.getWorld().sendEntityStatus(entity, TotemExpansion.USE_TOTEM_RECALL);
                    }
                }
             });
}