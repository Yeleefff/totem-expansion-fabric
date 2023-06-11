package org.refabricators.totemexpansion.event;

import org.refabricators.totemexpansion.item.BaseTotem;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;



@FunctionalInterface
public interface TotemUsedCallback {
    void invoke(LivingEntity entity, ItemStack stack);

    Event<TotemUsedCallback> EVENT = EventFactory.createArrayBacked(TotemUsedCallback.class,
            (listeners) -> (LivingEntity entity, ItemStack stack) -> {
                Item item = stack.getItem();
                if(!(item instanceof BaseTotem)) return;

                entity.setHealth(1.0f);
                entity.clearStatusEffects();
                ((BaseTotem)item).onTotemUse(entity);
                entity.getWorld().sendEntityStatus(entity, EntityStatuses.USE_TOTEM_OF_UNDYING);

                for (TotemUsedCallback listener : listeners) listener.invoke(entity, stack);
            });
}
