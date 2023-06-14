package org.refabricators.totemexpansion.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

@FunctionalInterface
public interface TotemUsedCallback {
    void invoke(LivingEntity entity, ItemStack stack);

      Event<TotemUsedCallback> EVENT = EventFactory.createArrayBacked(TotemUsedCallback.class,
            (listeners) -> (LivingEntity entity, ItemStack stack) -> {
                for (TotemUsedCallback listener : listeners) {
                    // Invoke all event listeners with the provided player and death message.
                    entity.setHealth(1.0f);
                    entity.clearStatusEffects();
                    listener.invoke(entity, stack);
                    entity.getWorld().sendEntityStatus(entity, EntityStatuses.USE_TOTEM_OF_UNDYING);
                }
            });
}