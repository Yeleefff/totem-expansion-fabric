package org.refabricators.totemexpansion.item.totem;

import net.minecraft.entity.damage.DamageTypes;
import org.refabricators.totemexpansion.item.TotemBase;

public class TotemExplosion extends TotemBase {
    public TotemExplosion(Settings settings) {
        super(settings);
    }

    @Override
    public void addDamageTypes() {
        damageTypes.add(DamageTypes.EXPLOSION);
        damageTypes.add(DamageTypes.FIREBALL);
        damageTypes.add(DamageTypes.FIREWORKS);
        damageTypes.add(DamageTypes.PLAYER_EXPLOSION);
    }
}
