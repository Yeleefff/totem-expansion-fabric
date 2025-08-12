package org.refabricators.totemexpansion;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.refabricators.totemexpansion.item.ModItems;
import org.refabricators.totemexpansion.network.SyncPlayerDataS2C;
import org.refabricators.totemexpansion.util.PlayerData;

import java.util.ArrayList;

import static org.refabricators.totemexpansion.TotemExpansion.id;


public class TotemExpansionClient implements ClientModInitializer {
    public static PlayerData playerState = new PlayerData();
    private static final Identifier TEXTURE = id("textures/item/totem_head_ores.png");
    private static ArrayList<BlockPos> oreBlockPoses = new ArrayList<>();
    private static ArrayList<Block> oreBlocks = new ArrayList<>();
    private static final float size = 8f;
    private static final int range = 12;

    private ArrayList<Block> genOreBlocksList() {
        ArrayList<Block> list = new ArrayList<>();
        list.add(Blocks.COAL_ORE);
        list.add(Blocks.DEEPSLATE_COAL_ORE);
        list.add(Blocks.COPPER_ORE);
        list.add(Blocks.DEEPSLATE_COPPER_ORE);
        list.add(Blocks.RAW_COPPER_BLOCK);
        list.add(Blocks.IRON_ORE);
        list.add(Blocks.DEEPSLATE_IRON_ORE);
        list.add(Blocks.RAW_IRON_BLOCK);
        list.add(Blocks.GOLD_ORE);
        list.add(Blocks.DEEPSLATE_GOLD_ORE);
        list.add(Blocks.RAW_GOLD_BLOCK);
        list.add(Blocks.DIAMOND_ORE);
        list.add(Blocks.DEEPSLATE_DIAMOND_ORE);
        list.add(Blocks.EMERALD_ORE);
        list.add(Blocks.DEEPSLATE_EMERALD_ORE);
        list.add(Blocks.NETHER_GOLD_ORE);
        list.add(Blocks.NETHER_QUARTZ_ORE);
        list.add(Blocks.ANCIENT_DEBRIS);
//        Registries.BLOCK.forEach(block -> {
//            if (block.getDefaultState().isIn(TagKey.of(RegistryKeys.BLOCK, Identifier.of("c", "ores")))) list.add(block);
//        });

        return list;
    }

    private ArrayList<BlockPos> genOrePosList(MinecraftClient client) {
        ArrayList<BlockPos> list = new ArrayList<>();
        BlockPos playerPos = client.player.getBlockPos();

        for (int x = -range; x <= range; x++) {
            for (int y = -range; y <= range; y++) {
                for (int z = -range; z <= range; z++) {
                    BlockPos blockPos = new BlockPos(playerPos.getX() + x, playerPos.getY() + y, playerPos.getZ() + z);
                    if (oreBlocks.contains(client.world.getBlockState(blockPos).getBlock())) {
                        list.add(new BlockPos(blockPos));
                    }
                }
            }
        }

        return list;
    }

    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(SyncPlayerDataS2C.ID, (payload, context) -> {
            playerState.usedRecallTotem = payload.usedRecallTotem();
            playerState.recallDirection = payload.recallDirection();
        });

        oreBlocks = genOreBlocksList();

        WorldRenderEvents.BEFORE_ENTITIES.register((context) -> {
            MinecraftClient client = MinecraftClient.getInstance();

            if (client.player != null && client.world != null && client.player.hasStatusEffect(TotemExpansion.SPELUNKING_EFFECT)) {
                if (client.world.getTime() % 15 == 0 || oreBlockPoses.isEmpty()) {
                    oreBlockPoses.clear();
                    oreBlockPoses = genOrePosList(client);
                }

                VertexConsumerProvider vertexConsumerProvider = context.consumers();
                MatrixStack matrices = context.matrixStack();
                Vec3d cameraPos = context.camera().getPos();

                for (BlockPos blockPos : oreBlockPoses) {
                    matrices.push();
                    matrices.translate(blockPos.getX() - cameraPos.getX() + 0.5, blockPos.getY() - cameraPos.getY() + 0.5 + 1, blockPos.getZ() - cameraPos.getZ() + 0.5);
                    matrices.scale(1/2f, 1/2f, 1/2f);
                    matrices.translate(0, Math.sin(client.world.getTime() / size) * 0.1, 0);
                    matrices.multiply(RotationAxis.POSITIVE_Y.rotation(client.world.getTime() / 20.0f));

                    client.getItemRenderer().renderItem(ModItems.TOTEM_HEAD_ORES.getDefaultStack(), ItemDisplayContext.FIXED, 15728880, OverlayTexture.DEFAULT_UV, matrices, vertexConsumerProvider, client.world, 0);
                    matrices.pop();
                }
            }

//                    matrices.translate(0, Math.sin((client.world.getTime() + blockPos.getX() * (size/2) + blockPos.getY() * (size/2) + blockPos.getZ() * (size/2)) / size) * 0.9f, 0);
//                    matrices.multiply(RotationAxis.POSITIVE_Y.rotation((client.world.getTime() + blockPos.getX() * (size/2) + blockPos.getY() * (size/2) + blockPos.getZ() * (size/2)) / 20.0f));

//                    vertexConsumer.vertex(tMatrix, 0f, -size, -size).texture(0f, 0f);
//                    vertexConsumer.vertex(tMatrix, 0f, size, -size).texture(0f, 1f);
//                    vertexConsumer.vertex(tMatrix, 0f, size, size).texture(1f, 1f);
//                    vertexConsumer.vertex(tMatrix, 0f, -size, size).texture(1f, 0f);
//                    vertexConsumer.vertex(tMatrix, 0f, -size, size).texture(1f, 0f);
//                    vertexConsumer.vertex(tMatrix, 0f, size, size).texture(1f, 1f);
//                    vertexConsumer.vertex(tMatrix, 0f, size, -size).texture(0f, 1f);
//                    vertexConsumer.vertex(tMatrix, 0f, -size, -size).texture(0f, 0f);
        });
    }
}