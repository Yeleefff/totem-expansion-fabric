package org.refabricators.totemexpansion.item.totem;

import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import org.refabricators.totemexpansion.item.TotemBase;

public class TotemExplosion extends TotemBase
{
    @Override
    public void addDamageTypes()
    {
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
