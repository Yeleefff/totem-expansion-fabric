package org.refabricators.totemexpansion.item;

import java.util.ArrayList;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Rarity;

public abstract class TotemBase extends Item {
    public TotemBase(Item.Settings settings) {
        super(settings.maxCount(1).rarity(Rarity.UNCOMMON));
        addDamageTypes();
    }

    protected ArrayList<RegistryKey<DamageType>> damageTypes = new ArrayList<>();

    public abstract void addDamageTypes();

    public boolean validDamageType(DamageSource source) {
        for (RegistryKey<DamageType> key : damageTypes) {
            if (source.isOf(key)) {
                return true;
            }
        }
        return false;
    }
}