package org.refabricators.totemexpansion.item.totem;

import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.refabricators.totemexpansion.item.TotemBase;

import java.util.List;

public class TotemExplosion extends TotemBase {
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

    @Override
    public void addEffects()
    {
        effects.add(new StatusEffectInstance(StatusEffects.REGENERATION, 200, 2));
    }
}
