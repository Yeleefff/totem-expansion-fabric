package org.refabricators.totemexpansion.item.totem;

import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import org.refabricators.totemexpansion.item.TotemBase;

public class TotemFalling extends TotemBase
{

    @Override
    public void addDamageTypes()
    {
        damageTypes.add(DamageTypes.FALL);
        damageTypes.add(DamageTypes.OUT_OF_WORLD);
    }

    @Override
    public void addEffects()
    {
        effects.add(new StatusEffectInstance(StatusEffects.LEVITATION, 900, 2));
    }
    
}
