package org.refabricators.totemexpansion.item.totem;

import net.minecraft.entity.damage.DamageTypes;
import org.refabricators.totemexpansion.item.TotemBase;

public class TotemBreathing extends TotemBase {
    public TotemBreathing(Settings settings) {
        super(settings);
    }

    @Override
    public void addDamageTypes() {
        damageTypes.add(DamageTypes.DROWN);
    }
}
