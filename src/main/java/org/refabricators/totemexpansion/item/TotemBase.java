package org.refabricators.totemexpansion.item;

import java.util.ArrayList;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Rarity;

public abstract class TotemBase extends Item {
    public TotemBase() {
        super(new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON));
        addDamageTypes();
        addEffects();
    }

    protected ArrayList<RegistryKey<DamageType>> damageTypes = new ArrayList<>();
    protected ArrayList<StatusEffectInstance> effects = new ArrayList<>();

    public abstract void addDamageTypes();

    public abstract void addEffects();

    public void onTotemUse(LivingEntity entity) {
        for (StatusEffectInstance effect : effects) entity.addStatusEffect(new StatusEffectInstance(effect));
    }

    public boolean validDamageType(DamageSource source) {
        for (RegistryKey<DamageType> key : damageTypes) {
            if (source.isOf(key)) {
                return true;
            }
        }
        return false;
    }
}