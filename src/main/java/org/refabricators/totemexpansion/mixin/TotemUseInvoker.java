package org.refabricators.totemexpansion.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LivingEntity.class)
public interface TotemUseInvoker {
    @Invoker("tryUseTotem")
    boolean useTotem(DamageSource source);
}