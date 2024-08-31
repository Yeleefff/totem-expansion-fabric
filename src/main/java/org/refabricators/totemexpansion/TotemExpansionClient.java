package org.refabricators.totemexpansion;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
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

    private void genOreBlocksArray() {
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
        // gen from c:ores tag instead
    }

    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(SyncPlayerDataS2C.ID, (payload, context) -> {
            playerState.usedRecallTotem = payload.usedRecallTotem();
            playerState.recallDirection = payload.recallDirection();
        });

        this.genOreBlocksArray();

        WorldRenderEvents.BEFORE_DEBUG_RENDER.register((context) -> {
            MinecraftClient client = MinecraftClient.getInstance();

            if (client.player != null && client.world != null && client.player.hasStatusEffect(TotemExpansion.SPELUNKING_EFFECT)) {

                if (client.world.getTime() % 15 == 0 || oreBlockPoses.isEmpty()) {
                    oreBlockPoses.clear();
                    BlockPos playerPos = client.player.getBlockPos();

                    for (int x = -range; x <= range; x++) {
                        for (int y = -range; y <= range; y++) {
                            for (int z = -range; z <= range; z++) {
                                BlockPos blockPos = new BlockPos(playerPos.getX() + x, playerPos.getY() + y, playerPos.getZ() + z);
                                if (oreBlocks.contains(client.world.getBlockState(blockPos).getBlock())) {
                                    oreBlockPoses.add(new BlockPos(blockPos));
                                }
                            }
                        }
                    }
                }

                RenderSystem.setShader(GameRenderer::getPositionTexProgram);
                RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
                RenderSystem.setShaderTexture(0, TEXTURE);

                for (BlockPos blockPos : oreBlockPoses) {
                    BufferBuilder buffer = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
                    MatrixStack matrices = context.matrixStack();
                    Vec3d cameraPos = context.camera().getPos();

                    matrices.push();
                    matrices.translate(blockPos.getX() - cameraPos.x + 0.5, blockPos.getY() - cameraPos.y + 0.5, blockPos.getZ() - cameraPos.z + 0.5);
                    matrices.scale(-1 / 32f, -1 / 32f, 1 / 32f);
                    matrices.translate(0, Math.sin((client.world.getTime() + blockPos.getX() * (size/2) + blockPos.getY() * (size/2) + blockPos.getZ() * (size/2)) / size) * 0.9f, 0);
                    matrices.multiply(RotationAxis.POSITIVE_Y.rotation((client.world.getTime() + blockPos.getX() * (size/2) + blockPos.getY() * (size/2) + blockPos.getZ() * (size/2)) / 20.0f));
//                    matrices.multiply(RotationAxis.POSITIVE_Y.rotation((float) Math.atan2(cameraPos.z - blockPos.getZ(), cameraPos.x - blockPos.getX())));
                    Matrix4f tMatrix = matrices.peek().getPositionMatrix();

                    buffer.vertex(tMatrix, 0f, -size, -size).texture(0f, 0f);
                    buffer.vertex(tMatrix, 0f, size, -size).texture(0f, 1f);
                    buffer.vertex(tMatrix, 0f, size, size).texture(1f, 1f);
                    buffer.vertex(tMatrix, 0f, -size, size).texture(1f, 0f);
                    buffer.vertex(tMatrix, 0f, -size, size).texture(1f, 0f);
                    buffer.vertex(tMatrix, 0f, size, size).texture(1f, 1f);
                    buffer.vertex(tMatrix, 0f, size, -size).texture(0f, 1f);
                    buffer.vertex(tMatrix, 0f, -size, -size).texture(0f, 0f);

                    matrices.pop();
                    BufferRenderer.drawWithGlobalProgram(buffer.end());
                }
            }
        });
    }
}