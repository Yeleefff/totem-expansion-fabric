package org.refabricators.totemexpansion.item;

import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class FireTotem extends BaseTotem {

    @Override
    public void addDamageTypes() {
        damageTypes.add(DamageTypes.IN_FIRE);
        damageTypes.add(DamageTypes.ON_FIRE);
        damageTypes.add(DamageTypes.LAVA);
    }

    @Override
    public void addEffects() {
        effects.add(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 1000, 0));
    }
    
}
