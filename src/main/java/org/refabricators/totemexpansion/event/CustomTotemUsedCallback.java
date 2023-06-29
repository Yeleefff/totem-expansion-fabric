package org.refabricators.totemexpansion.event;

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

//                if(!(stack.getItem() instanceof TotemBase)) return;

                for (CustomTotemUsedCallback listener : listeners) {
                    // Invoke all event listeners with the provided player and death message.
                    listener.invoke(entity, stack);

                    entity.setHealth(1.0f);
                    entity.clearStatusEffects();
//                    ((TotemBase) stack.getItem()).onTotemUse(entity);
                    entity.getWorld().sendEntityStatus(entity, EntityStatuses.USE_TOTEM_OF_UNDYING);
                }
            });
}