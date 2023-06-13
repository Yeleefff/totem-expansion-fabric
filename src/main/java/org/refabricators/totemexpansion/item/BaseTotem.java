package org.refabricators.totemexpansion.item;

import java.util.ArrayList;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.LivingEntity;

import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Rarity;

public abstract class BaseTotem extends Item {
    public ArrayList<RegistryKey<DamageType>> damageTypes = new ArrayList<RegistryKey<DamageType>>();
    public ArrayList<StatusEffectInstance> effects = new ArrayList<StatusEffectInstance>();

    public BaseTotem() {
        super(new FabricItemSettings().maxCount(1).rarity(Rarity.UNCOMMON));
        addDamageSources();
    }

    public abstract void addDamageSources();
    //this is where you add the required damage sources

    public abstract void addEffects();
    //this is where you add the given effects

    public boolean validDamageSource(RegistryKey<DamageType> source) {
        return damageTypes.contains(source);
    }

    public void onTotemUse(LivingEntity entity) {
        for (StatusEffectInstance effect : effects) entity.addStatusEffect(effect);
    }
    
    
}
