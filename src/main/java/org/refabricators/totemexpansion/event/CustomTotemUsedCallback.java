package org.refabricators.totemexpansion.event;

import org.refabricators.totemexpansion.item.ModItems;
import org.refabricators.totemexpansion.item.TotemBase;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

@FunctionalInterface
public interface CustomTotemUsedCallback {
    void invoke(LivingEntity entity, ItemStack stack);

      Event<CustomTotemUsedCallback> EVENT = EventFactory.createArrayBacked(CustomTotemUsedCallback.class,
            (listeners) -> (LivingEntity entity, ItemStack stack) -> {

                if(!(stack.getItem() instanceof TotemBase)) return;

                for (CustomTotemUsedCallback listener : listeners) {
                    // Invoke all event listeners with the provided player and death message.
                    listener.invoke(entity, stack);

                    if (!stack.isOf(ModItems.TOTEM_ORES) && !stack.isOf(ModItems.TOTEM_TIME) && !stack.isOf(ModItems.TOTEM_RECALL)) {
                        entity.setHealth(1.0f);
                        entity.clearStatusEffects();
                    }
                    ((TotemBase) stack.getItem()).onTotemUse(entity);
                    entity.getWorld().sendEntityStatus(entity, EntityStatuses.USE_TOTEM_OF_UNDYING);
                }
             });
}