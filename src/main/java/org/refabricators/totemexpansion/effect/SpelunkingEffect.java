package org.refabricators.totemexpansion.effect;

import net.fabricmc.fabric.api.renderer.v1.Renderer;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;


public class SpelunkingEffect extends StatusEffect {
    private final int range = 30;
    ArrayList<Block> oreBlocks = new ArrayList<>();
    ArrayList<BlockPos> oreBlocksPos = new ArrayList<>();
    ArrayList<ItemEntity> totemList = new ArrayList<>();

    public SpelunkingEffect() {
        super(StatusEffectCategory.NEUTRAL, 0xf4d942);
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity.getWorld().getTime() % 20 == 0) {
            BlockPos playerPos = entity.getBlockPos();

            for (int x = -range; x <= range; x++) {
                for (int y = -range; y <= range; y++) {
                    for (int z = -range; z <= range; z++) {

                        BlockPos blockPos = new BlockPos(playerPos.getX() + x, playerPos.getY() + y, playerPos.getZ() + z);
                        if (oreBlocks.contains(entity.getWorld().getBlockState(blockPos).getBlock())) {
                            oreBlocksPos.add(new BlockPos(blockPos));
                        }
                    }
                }
            }

            for (BlockPos blockPos : oreBlocksPos) {
            }
        }

        return true;
    }

    @Override
    public void onApplied(AttributeContainer attributes, int amplifier) {
        super.onApplied(attributes, amplifier);
    }

    @Override
    public void onApplied(LivingEntity entity, int amplifier) {
        this.oreBlocks.add(Blocks.COAL_ORE);
        this.oreBlocks.add(Blocks.DEEPSLATE_COAL_ORE);
        this.oreBlocks.add(Blocks.COPPER_ORE);
        this.oreBlocks.add(Blocks.DEEPSLATE_COPPER_ORE);
        this.oreBlocks.add(Blocks.RAW_COPPER_BLOCK);
        this.oreBlocks.add(Blocks.IRON_ORE);
        this.oreBlocks.add(Blocks.DEEPSLATE_IRON_ORE);
        this.oreBlocks.add(Blocks.RAW_IRON_BLOCK);
        this.oreBlocks.add(Blocks.GOLD_ORE);
        this.oreBlocks.add(Blocks.DEEPSLATE_GOLD_ORE);
        this.oreBlocks.add(Blocks.RAW_GOLD_BLOCK);
        this.oreBlocks.add(Blocks.DIAMOND_ORE);
        this.oreBlocks.add(Blocks.DEEPSLATE_DIAMOND_ORE);
        this.oreBlocks.add(Blocks.EMERALD_ORE);
        this.oreBlocks.add(Blocks.DEEPSLATE_EMERALD_ORE);
        this.oreBlocks.add(Blocks.NETHER_GOLD_ORE);
        this.oreBlocks.add(Blocks.NETHER_QUARTZ_ORE);
        this.oreBlocks.add(Blocks.ANCIENT_DEBRIS);
//        for (Block block : ConventionalBlockTags.ORES)

//        this.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public void onRemoved(AttributeContainer attributes) {
        super.onRemoved(attributes);
    }
}