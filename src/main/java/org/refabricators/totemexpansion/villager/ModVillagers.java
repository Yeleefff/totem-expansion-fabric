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
import net.minecraft.text.Text;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;
import org.refabricators.totemexpansion.TotemExpansion;

import static org.refabricators.totemexpansion.TotemExpansion.id;

public class ModVillagers {
    public static final RegistryKey<PointOfInterestType> WITCH_DOCTOR_POI_KEY = RegistryKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE, id("witch_doctor"));
    public static final PointOfInterestType WITCH_DOCTOR_POI = PointOfInterestHelper.register(
            id("witch_doctor"),
            1,
            1,
            Blocks.WITHER_SKELETON_SKULL);

    public static final RegistryKey<VillagerProfession> WITCH_DOCTOR_KEY = RegistryKey.of(RegistryKeys.VILLAGER_PROFESSION, id("witch_doctor"));
    public static final VillagerProfession WITCH_DOCTOR = Registry.register(Registries.VILLAGER_PROFESSION, WITCH_DOCTOR_KEY,
            new VillagerProfession(
                    Text.translatable("entity." + WITCH_DOCTOR_KEY.getValue().getNamespace() + ".villager." + WITCH_DOCTOR_KEY.getValue().getPath()),
                    entry -> entry.matchesKey(WITCH_DOCTOR_POI_KEY),
                    entry -> entry.matchesKey(WITCH_DOCTOR_POI_KEY),
                    ImmutableSet.of(),
                    ImmutableSet.of(),
                    SoundEvents.ENTITY_VILLAGER_WORK_CLERIC));

    public static void registerVillagers() {
        TotemExpansion.LOGGER.info("Registering villagers for " + TotemExpansion.MOD_ID);
    }
}
