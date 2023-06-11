package org.refabricators.totemexpansion.item;

import java.util.ArrayList;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Rarity;

public abstract class BaseTotem extends Item {
    public ArrayList<DamageSource> damageSources = new ArrayList<DamageSource>();

    public BaseTotem() {
        super(new FabricItemSettings().maxCount(1).rarity(Rarity.UNCOMMON));
        addDamageSources();
    }

    public abstract void addDamageSources();

    public abstract void onTotemUse(LivingEntity entity, DamageSource source, ItemStack stack);
    
}
