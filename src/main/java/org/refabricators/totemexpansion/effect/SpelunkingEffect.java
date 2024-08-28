package org.refabricators.totemexpansion.effect;

import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;


public class SpelunkingEffect extends StatusEffect {
    public SpelunkingEffect() {
        super(StatusEffectCategory.NEUTRAL, 0xf4d942);
    }

    @Override
    public void onApplied(AttributeContainer attributes, int amplifier) {
        super.onApplied(attributes, amplifier);
    }

    @Override
    public void onRemoved(AttributeContainer attributes) {
        super.onRemoved(attributes);
    }
}