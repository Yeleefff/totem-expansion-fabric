package org.refabricators.totemexpansion.item.totem;

import net.minecraft.entity.damage.DamageTypes;
import org.refabricators.totemexpansion.item.TotemBase;

public class TotemFalling extends TotemBase {
    public TotemFalling(Settings settings) {
        super(settings);
    }

    @Override
    public void addDamageTypes() {
        damageTypes.add(DamageTypes.FALL);
        damageTypes.add(DamageTypes.OUT_OF_WORLD);
    }
}
