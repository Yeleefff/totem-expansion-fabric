package org.refabricators.totemexpansion.item;

import java.util.ArrayList;

import org.refabricators.totemexpansion.TotemExpansion;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Rarity;

public abstract class BaseTotem extends Item {

    public BaseTotem() {
        super(new FabricItemSettings().maxCount(1).rarity(Rarity.UNCOMMON));
        addDamageTypes();
        addEffects();
    }

    protected ArrayList<RegistryKey<DamageType>> damageTypes = new ArrayList<RegistryKey<DamageType>>();
    protected ArrayList<StatusEffectInstance> effects = new ArrayList<StatusEffectInstance>();

    public abstract void addDamageTypes();

    public abstract void addEffects();

    public void onTotemUse(LivingEntity entity) {
        for (StatusEffectInstance effect : effects) entity.addStatusEffect(effect);
    }

    public boolean validDamageType(DamageSource source) {
        for (RegistryKey<DamageType> key : damageTypes) if(source.isOf(key)) {
            TotemExpansion.LOGGER.info("valid damage type");
            return true;
        } 
        return false;
    }
}
