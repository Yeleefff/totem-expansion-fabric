package org.refabricators.totemexpansion.item.totem;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.refabricators.totemexpansion.item.ModItems;
import org.refabricators.totemexpansion.item.TotemBase;

import java.util.List;

import static org.refabricators.totemexpansion.TotemExpansion.id;

public class TotemExplosion extends TotemBase {
    public TotemExplosion() {
        super(new Item.Settings().component(DataComponentTypes.DEATH_PROTECTION, ModItems.TOTEM_EXPLOSION_COMPONENT)
                .registryKey(RegistryKey.of(RegistryKeys.ITEM, id("totem_explosion"))));
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("tooltip.totemexpansion.totem_explosion.description").formatted(Formatting.GRAY));
        super.appendTooltip(stack, context, tooltip, type);
    }

    @Override
    public void addDamageTypes() {
        damageTypes.add(DamageTypes.EXPLOSION);
        damageTypes.add(DamageTypes.FIREBALL);
        damageTypes.add(DamageTypes.FIREWORKS);
        damageTypes.add(DamageTypes.PLAYER_EXPLOSION);
    }
}
