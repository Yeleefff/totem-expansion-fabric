package org.refabricators.totemexpansion.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;

@FunctionalInterface
public interface TotemUsedCallback {
    void invoke(LivingEntity entity, DamageSource damageSource, ItemStack stack);

    Event<TotemUsedCallback> EVENT = EventFactory.createArrayBacked(TotemUsedCallback.class,
            (listeners) -> (LivingEntity entity, DamageSource damageSource, ItemStack stack) -> {
                for (TotemUsedCallback listener : listeners) {
                    // Invoke all event listeners with the provided entity and damage source
                    listener.invoke(entity, damageSource, stack);

                    if (stack != null && entity instanceof ServerPlayerEntity) {

                        ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)entity;
                        serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(Items.TOTEM_OF_UNDYING));
                        Criteria.USED_TOTEM.trigger(serverPlayerEntity, stack);

                    }
                }
            });
}
