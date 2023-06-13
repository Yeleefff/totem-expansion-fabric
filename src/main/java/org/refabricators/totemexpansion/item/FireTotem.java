package org.refabricators.totemexpansion.item;

import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;


public class FireTotem extends BaseTotem {

    @Override
    public void addDamageSources() {
        this.damageTypes.add(DamageTypes.IN_FIRE);
    }

    @Override
    public void addEffects() {
       this.effects.add(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 100));
    }
     
}
