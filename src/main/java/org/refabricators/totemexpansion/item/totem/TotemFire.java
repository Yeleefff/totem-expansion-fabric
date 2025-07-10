package org.refabricators.totemexpansion.item.totem;

import net.minecraft.entity.damage.DamageTypes;
import org.refabricators.totemexpansion.item.TotemBase;

public class TotemFire extends TotemBase {
    public TotemFire(Settings settings) {
        super(settings);
    }

    @Override
    public void addDamageTypes() {
        damageTypes.add(DamageTypes.ON_FIRE);
        damageTypes.add(DamageTypes.IN_FIRE);
        damageTypes.add(DamageTypes.LAVA);
        damageTypes.add(DamageTypes.HOT_FLOOR);
    }
}