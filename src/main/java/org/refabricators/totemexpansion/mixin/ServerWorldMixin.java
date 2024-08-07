package org.refabricators.totemexpansion.mixin;

import net.fabricmc.fabric.api.attachment.v1.AttachmentTarget;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.refabricators.totemexpansion.TotemExpansion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin extends World implements StructureWorldAccess, AttachmentTarget {
    private int timeIncrement = 60;

    @Shadow
    public abstract void setTimeOfDay(long timeOfDay);

    protected ServerWorldMixin(MutableWorldProperties properties, RegistryKey<World> registryRef, DynamicRegistryManager registryManager, RegistryEntry<DimensionType> dimensionEntry, Supplier<Profiler> profiler, boolean isClient, boolean debugWorld, long biomeAccess, int maxChainedNeighborUpdates) {
        super(properties, registryRef, registryManager, dimensionEntry, profiler, isClient, debugWorld, biomeAccess, maxChainedNeighborUpdates);
    }

    @Inject(method = "tick", at = @At(value = "HEAD"))
    public void incrementTime(BooleanSupplier shouldKeepTicking, CallbackInfo info) {
        for (int i = 0; i < TotemExpansion.activeTimeTotems.size(); i++) {

            this.setTimeOfDay(this.getTimeOfDay() + timeIncrement);
            TotemExpansion.activeTimeTotems.set(i, TotemExpansion.activeTimeTotems.get(i) + 1);

            if (TotemExpansion.activeTimeTotems.get(i) >= (int) (12000/timeIncrement)) {
                TotemExpansion.activeTimeTotems.remove(i);
            }
        }
    }
}