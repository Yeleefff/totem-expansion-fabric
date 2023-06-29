package org.refabricators.totemexpansion.item.totem;

import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import org.refabricators.totemexpansion.item.TotemBase;

public class TotemFire extends TotemBase
{
    @Override
    public void addDamageTypes()
    {
        damageTypes.add(DamageTypes.ON_FIRE);
        damageTypes.add(DamageTypes.IN_FIRE);
        damageTypes.add(DamageTypes.LAVA);
        damageTypes.add(DamageTypes.HOT_FLOOR);
    }

    @Override
    public void addEffects()
    {
        effects.add(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 900, 0));
    }
}
