package org.refabricators.totemexpansion;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
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
    private static final float size = 1f;
    private static final int range = 12;

    private static final RenderPipeline SPELUNKING_ICON = RenderPipelines.register(RenderPipeline.builder(RenderPipelines.POSITION_TEX_COLOR_SNIPPET)
            .withLocation(id("pipeline/spelunking_icon"))
            .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
            .build());
    private static final ByteBufferBuilder



//    public static final RenderPipeline SPELUNKING_EFFECT = RenderPipeline.builder(RenderPipelines.TRANSFORMS_AND_PROJECTION_SNIPPET)
//            .withLocation(id("pipeline/spelunking_effect"))
//            .withVertexFormat(VertexFormats.POSITION_TEXTURE_COLOR, VertexFormat.DrawMode.QUADS)
//            .withVertexShader(Identifier.ofVanilla("core/position_tex"))
//            .withFragmentShader(Identifier.ofVanilla("core/position_tex"))
//            .withBlend(BlendFunction.OVERLAY)
//            .withCull(false)
//            .build();
//    public static final RenderLayer SPELUNKING_EFFECT_LAYER = RenderLayer.of("spelunking_effect",
//            1536, true, false,
//            SPELUNKING_EFFECT,
//            RenderLayer.MultiPhaseParameters.builder()
//                    .lightmap(RenderPhase.ENABLE_LIGHTMAP)
//                    .texture(new RenderPhase.Texture(TEXTURE, false))
//                    .build(true));

    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(SyncPlayerDataS2C.ID, (payload, context) -> {
            playerState.usedRecallTotem = payload.usedRecallTotem();
            playerState.recallDirection = payload.recallDirection();
        });

        oreBlocks = genOreBlocksList();
        WorldRenderEvents.BEFORE_ENTITIES.register(TotemExpansionClient::renderSpelunkingEffect);
    }

    private static void renderSpelunkingEffect(WorldRenderContext context) {
        MinecraftClient client = MinecraftClient.getInstance();
    }

    private static void renderSpelunkingEffectAlt(WorldRenderContext context) {
        MinecraftClient client = MinecraftClient.getInstance();

//            if (client.player != null && client.world != null && client.player.hasStatusEffect(TotemExpansion.SPELUNKING_EFFECT)) {
//                if (client.world.getTime() % 15 == 0 || oreBlockPoses.isEmpty()) {
//                    oreBlockPoses.clear();
//                    oreBlockPoses = genOrePosList(client);
//                }
//
//                VertexConsumerProvider vertexConsumerProvider = context.consumers();
//                MatrixStack matrices = context.matrixStack();
//                Vec3d cameraPos = context.camera().getPos();
//
//                for (BlockPos blockPos : oreBlockPoses) {
//                    matrices.push();
//                    matrices.translate(blockPos.getX() - cameraPos.getX() + 0.5, blockPos.getY() - cameraPos.getY() + 0.5 + 1, blockPos.getZ() - cameraPos.getZ() + 0.5);
//                    matrices.scale(1/2f, 1/2f, 1/2f);
//                    matrices.translate(0, Math.sin(client.world.getTime() / size) * 0.1, 0);
//                    matrices.multiply(RotationAxis.POSITIVE_Y.rotation(client.world.getTime() / 20.0f));
//
//                    client.getItemRenderer().renderItem(ModItems.TOTEM_HEAD_ORES.getDefaultStack(), ItemDisplayContext.FIXED, 15728880, OverlayTexture.DEFAULT_UV, matrices, vertexConsumerProvider, client.world, 0);
//                    matrices.pop();
//                }
//            }

        if (client.player != null && client.world != null && client.player.hasStatusEffect(TotemExpansion.SPELUNKING_EFFECT)) {
            if (client.world.getTime() % 15 == 0 || oreBlockPoses.isEmpty()) {
                oreBlockPoses.clear();
                oreBlockPoses = genOrePosList(client);
            }

            VertexConsumerProvider vertexConsumerProvider = context.consumers();
            VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityCutoutNoCull(TEXTURE));
            MatrixStack matrices = context.matrixStack();
            Vec3d cameraPos = context.camera().getPos();

            for (BlockPos blockPos : oreBlockPoses) {
                matrices.push();
                matrices.translate(blockPos.getX() - cameraPos.getX() + 0.5, blockPos.getY() - cameraPos.getY() + 0.5 + 1, blockPos.getZ() - cameraPos.getZ() + 0.5);
                matrices.scale(1/2f, 1/2f, 1/2f);
//                    matrices.translate(0, Math.sin((client.world.getTime() + blockPos.getX() * (size/2) + blockPos.getY() * (size/2) + blockPos.getZ() * (size/2)) / size) * 0.9f, 0);
//                    matrices.multiply(RotationAxis.POSITIVE_Y.rotation((client.world.getTime() + blockPos.getX() * (size/2) + blockPos.getY() * (size/2) + blockPos.getZ() * (size/2)) / 20.0f));
                matrices.translate(0, Math.sin(client.world.getTime() / 8f) * 0.10, 0); //8f:bob speed, 0.15:bob amplitude
                matrices.multiply(RotationAxis.POSITIVE_Y.rotation(client.world.getTime() / 20.0f));

                Matrix4f tMatrix = matrices.peek().getPositionMatrix();

//                    vertexConsumer.vertex(tMatrix, 0f, -size, -size).texture(0f, 0f);
//                    vertexConsumer.vertex(tMatrix, 0f, size, -size).texture(0f, 1f);
//                    vertexConsumer.vertex(tMatrix, 0f, size, size).texture(1f, 1f);
//                    vertexConsumer.vertex(tMatrix, 0f, -size, size).texture(1f, 0f);
//                    vertexConsumer.vertex(tMatrix, 0f, -size, size).texture(1f, 0f);
//                    vertexConsumer.vertex(tMatrix, 0f, size, size).texture(1f, 1f);
//                    vertexConsumer.vertex(tMatrix, 0f, size, -size).texture(0f, 1f);
//                    vertexConsumer.vertex(tMatrix, 0f, -size, -size).texture(0f, 0f);

//                GpuTexture gpuTexture = client.getTextureManager().getTexture(TEXTURE).getGlTextureView().texture();
//                sprite.upload(gpuTexture);
//                vertexConsumer.quad(matrices, new BakedQuad(new int[]{1, 1, 1, 1}, -1, Direction.EAST, new Sprite(TEXTURE, SpriteContents)), 0f, 0f, 0f, 255f, 15728880, 0);

                vertexConsumer.vertex(tMatrix, 0f, -size, -size)
                        .color(0,0, 0, 255)
                        .texture(0f, 0f)
                        .normal(0,0,0)
                        .overlay(OverlayTexture.DEFAULT_UV)
                        .light(15728880);
                vertexConsumer.vertex(tMatrix, 0f, size, -size)
                        .color(0,0, 0, 255)
                        .texture(0f, 1f)
                        .normal(0,0,0)
                        .overlay(OverlayTexture.DEFAULT_UV)
                        .light(15728880);
                vertexConsumer.vertex(tMatrix, 0f, size, size)
                        .color(0,0, 0, 255)
                        .texture(1f, 1f)
                        .normal(0,0,0)
                        .overlay(OverlayTexture.DEFAULT_UV)
                        .light(15728880);
                vertexConsumer.vertex(tMatrix, 0f, -size, size)
                        .color(0,0, 0, 255)
                        .texture(1f, 0f)
                        .normal(0,0,0)
                        .overlay(OverlayTexture.DEFAULT_UV)
                        .light(15728880);
//                vertexConsumer.vertex(tMatrix, 0f, -size, size)
//                        .color(0,0, 0, 255)
//                        .texture(1f, 0f)
//                        .normal(0,0,0)
//                        .overlay(OverlayTexture.DEFAULT_UV)
//                        .light(15728880);
//                vertexConsumer.vertex(tMatrix, 0f, size, size)
//                        .color(0,0, 0, 255)
//                        .texture(1f, 1f)
//                        .normal(0,0,0)
//                        .overlay(OverlayTexture.DEFAULT_UV)
//                        .light(15728880);
//                vertexConsumer.vertex(tMatrix, 0f, size, -size)
//                        .color(0,0, 0, 255)
//                        .texture(0f, 1f)
//                        .normal(0,0,0)
//                        .overlay(OverlayTexture.DEFAULT_UV)
//                        .light(15728880);
//                vertexConsumer.vertex(tMatrix, 0f, -size, -size)
//                        .color(0, 0, 0, 255)
//                        .texture(0f, 0f)
//                        .normal(0,0,0)
//                        .overlay(OverlayTexture.DEFAULT_UV)
//                        .light(15728880);

                matrices.pop();
            }
        }
    }

    private static ArrayList<Block> genOreBlocksList() {
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

    private static ArrayList<BlockPos> genOrePosList(MinecraftClient client) {
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
}