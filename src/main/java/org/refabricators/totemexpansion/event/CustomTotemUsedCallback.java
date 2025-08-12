package org.refabricators.totemexpansion.event;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DeathProtectionComponent;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.Item;
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

      Event<CustomTotemUsedCallback> EVENT = EventFactory.createArrayBacked(CustomTotemUsedCallback.class, (listeners) -> (LivingEntity entity, ItemStack stack, DamageSource source) -> {
            DeathProtectionComponent component;
            if ((component = stack.get(DataComponentTypes.DEATH_PROTECTION)) == null) return;

            for (CustomTotemUsedCallback listener : listeners) {
                listener.invoke(entity, stack, source);

                if (stack.isOf(Items.TOTEM_OF_UNDYING) || stack.isOf(ModItems.TOTEM_FALLING) || stack.isOf(ModItems.TOTEM_FIRE) || stack.isOf(ModItems.TOTEM_BREATHING)) {
                    entity.setHealth(1.0f);
                    entity.clearStatusEffects();
                    component.applyDeathEffects(stack, entity);
                } else if (stack.isOf(ModItems.TOTEM_ORES)) {
                    component.applyDeathEffects(stack, entity);
                }

                if (stack.isOf(Items.TOTEM_OF_UNDYING)) {
                    entity.getWorld().sendEntityStatus(entity, EntityStatuses.USE_TOTEM_OF_UNDYING);
                } else if (stack.isOf(ModItems.TOTEM_FALLING)) {
                    entity.getWorld().sendEntityStatus(entity, TotemExpansion.USE_TOTEM_FALLING);
                } else if (stack.isOf(ModItems.TOTEM_FIRE)) {
                    entity.getWorld().sendEntityStatus(entity, TotemExpansion.USE_TOTEM_FIRE);
                } else if (stack.isOf(ModItems.TOTEM_BREATHING)) {
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