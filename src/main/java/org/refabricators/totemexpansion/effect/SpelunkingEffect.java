package org.refabricators.totemexpansion.effect;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.util.math.BlockPos;
import java.util.ArrayList;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;


public class SpelunkingEffect extends StatusEffect {
    public SpelunkingEffect() {
        super(StatusEffectCategory.NEUTRAL, 0xf4d942);
    }

    ArrayList<ChickenEntity> chickenList = new ArrayList<>();

    @Override
    public void onApplied(AttributeContainer attributes, int amplifier) {
        super.onApplied(attributes, amplifier);
    }

    @Override
    public void onApplied(LivingEntity entity, int amplifier) {
        ArrayList<Block> oreBlocks = new ArrayList<>();
        oreBlocks.add(Blocks.COAL_ORE);
        oreBlocks.add(Blocks.DEEPSLATE_COAL_ORE);
        oreBlocks.add(Blocks.COPPER_ORE);
        oreBlocks.add(Blocks.DEEPSLATE_COPPER_ORE);
        oreBlocks.add(Blocks.RAW_COPPER_BLOCK);
        oreBlocks.add(Blocks.IRON_ORE);
        oreBlocks.add(Blocks.DEEPSLATE_IRON_ORE);
        oreBlocks.add(Blocks.RAW_IRON_BLOCK);
        oreBlocks.add(Blocks.GOLD_ORE);
        oreBlocks.add(Blocks.DEEPSLATE_GOLD_ORE);
        oreBlocks.add(Blocks.RAW_GOLD_BLOCK);
        oreBlocks.add(Blocks.DIAMOND_ORE);
        oreBlocks.add(Blocks.DEEPSLATE_DIAMOND_ORE);
        oreBlocks.add(Blocks.EMERALD_ORE);
        oreBlocks.add(Blocks.DEEPSLATE_EMERALD_ORE);
        oreBlocks.add(Blocks.NETHER_GOLD_ORE);
        oreBlocks.add(Blocks.NETHER_QUARTZ_ORE);
        oreBlocks.add(Blocks.ANCIENT_DEBRIS);
//        for (Block block : ConventionalBlockTags.ORES)

        ArrayList<BlockPos> oreBlocksPos = new ArrayList<>();

        int range = 30;
        BlockPos playerPos = entity.getBlockPos();

        for (int x = -range; x <= range; x++)
            for (int y = -range; y <= range; y++)
                for (int z = -range; z <= range; z++) {

                    BlockPos blockPos = new BlockPos(playerPos.getX() + x, playerPos.getY() + y, playerPos.getZ() + z);
                    if (oreBlocks.contains(entity.getWorld().getBlockState(blockPos).getBlock())) {
                        System.out.println(blockPos);
                        oreBlocksPos.add(new BlockPos(blockPos));
                    }
                }

        for (BlockPos oreBlockPos : oreBlocksPos)
        {
            ChickenEntity chickenEntity = EntityType.CHICKEN.create(entity.getWorld());
            chickenEntity.setPos(oreBlockPos.getX(), oreBlockPos.getY(), oreBlockPos.getZ());
            chickenEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, Integer.MAX_VALUE));
            chickenEntity.setInvulnerable(true);
            entity.getWorld().spawnEntity(chickenEntity);
            chickenList.add(chickenEntity);
        }
    }

    @Override
    public void onRemoved(AttributeContainer attributes)
    {
        super.onRemoved(attributes);
        for (ChickenEntity chicken : chickenList) {
            chicken.kill();
        }
    }
}