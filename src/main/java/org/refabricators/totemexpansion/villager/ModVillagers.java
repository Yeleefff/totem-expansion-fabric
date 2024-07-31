package org.refabricators.totemexpansion.villager;

import com.google.common.collect.ImmutableSet;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvents;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;
import org.refabricators.totemexpansion.TotemExpansion;

import static org.refabricators.totemexpansion.TotemExpansion.id;

public class ModVillagers {
    public static final RegistryKey<PointOfInterestType> WITCH_DOCTOR_POI_KEY = poiKey("witch_doctor");
    public static final PointOfInterestType WITCH_DOCTOR_POI = registerPoi("witch_doctor", Blocks.BAMBOO_BLOCK);

    public static final VillagerProfession WITCH_DOCTOR = registerProfession("witch_doctor", WITCH_DOCTOR_POI_KEY);

    private static VillagerProfession registerProfession(String name, RegistryKey<PointOfInterestType> type) {
        return Registry.register(Registries.VILLAGER_PROFESSION, id(name),
                new VillagerProfession(name, entry -> entry.matchesKey(type), entry -> entry.matchesKey(type),
                        ImmutableSet.of(), ImmutableSet.of(), SoundEvents.ENTITY_VILLAGER_WORK_CLERIC));
    }

    private static PointOfInterestType registerPoi(String name, Block block) {
        return PointOfInterestHelper.register(id(name), 1, 1, block);
    }

    private static RegistryKey<PointOfInterestType> poiKey(String name) {
        return RegistryKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE, id(name));
    }

    public static void registerVillagers() {
        TotemExpansion.LOGGER.info("Registering villagers for " + TotemExpansion.MOD_ID);
    }
}
