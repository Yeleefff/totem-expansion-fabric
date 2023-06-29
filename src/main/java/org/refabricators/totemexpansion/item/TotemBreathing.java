package org.refabricators.totemexpansion.item.totem;

import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import org.refabricators.totemexpansion.item.TotemBase;

public class TotemBreathing extends TotemBase
{
    @Override
    public void addDamageTypes()
    {
        damageTypes.add(DamageTypes.DROWN);
    }

    @Override
    public void addEffects()
    {
        effects.add(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 900));
    }
}
