package com.github.reoseah.iconstruct;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.github.reoseah.iconstruct.blocks.WrenchableBlock;
import com.github.reoseah.iconstruct.blocks.entities.ConduitBlockEntity;
import com.github.reoseah.iconstruct.blocks.entities.ConduitBlockEntity.TravellingItem;
import com.github.reoseah.iconstruct.client.models.IConstructModels;
import com.github.reoseah.iconstruct.client.render.AxleBlockEntityRenderer;
import com.github.reoseah.iconstruct.client.render.ConduitBlockEntityRenderer;
import com.github.reoseah.iconstruct.client.render.EngineBlockEntityRenderer;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.fabricmc.fabric.api.network.PacketContext;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class IConstructClientInitializer implements ClientModInitializer {
    @Override
    @Environment(EnvType.CLIENT)
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutoutMipped(),
                IConstruct.CONDUIT,
                IConstruct.EXTRACTOR,

                IConstruct.SCAFFOLDING,
                IConstruct.CONDUIT_IN_SCAFFOLDING,
                IConstruct.CATWALK,
                IConstruct.CATWALK_STAIRS,
                IConstruct.REINFORCED_GLASS,

                IConstruct.ColoredConduits.WHITE.block,
                IConstruct.ColoredConduits.ORANGE.block,
                IConstruct.ColoredConduits.MAGENTA.block,
                IConstruct.ColoredConduits.YELLOW.block,
                IConstruct.ColoredConduits.LIGHT_BLUE.block,
                IConstruct.ColoredConduits.LIME.block,
                IConstruct.ColoredConduits.PINK.block,
                IConstruct.ColoredConduits.LIGHT_GRAY.block,
                IConstruct.ColoredConduits.GRAY.block,
                IConstruct.ColoredConduits.CYAN.block,
                IConstruct.ColoredConduits.PURPLE.block,
                IConstruct.ColoredConduits.BLUE.block,
                IConstruct.ColoredConduits.BROWN.block,
                IConstruct.ColoredConduits.GREEN.block,
                IConstruct.ColoredConduits.RED.block,
                IConstruct.ColoredConduits.BLACK.block,

                IConstruct.ColoredExtractors.WHITE.block,
                IConstruct.ColoredExtractors.ORANGE.block,
                IConstruct.ColoredExtractors.MAGENTA.block,
                IConstruct.ColoredExtractors.YELLOW.block,
                IConstruct.ColoredExtractors.LIGHT_BLUE.block,
                IConstruct.ColoredExtractors.LIME.block,
                IConstruct.ColoredExtractors.PINK.block,
                IConstruct.ColoredExtractors.LIGHT_GRAY.block,
                IConstruct.ColoredExtractors.GRAY.block,
                IConstruct.ColoredExtractors.CYAN.block,
                IConstruct.ColoredExtractors.PURPLE.block,
                IConstruct.ColoredExtractors.BLUE.block,
                IConstruct.ColoredExtractors.BROWN.block,
                IConstruct.ColoredExtractors.GREEN.block,
                IConstruct.ColoredExtractors.RED.block,
                IConstruct.ColoredExtractors.BLACK.block);

        ClientSidePacketRegistry.INSTANCE.register(new Identifier("icon:conduit"),
                (PacketContext context, PacketByteBuf buffer) -> {
                    BlockPos pos = buffer.readBlockPos();

                    int count = buffer.readInt();
                    List<ConduitBlockEntity.TravellingItem> list = new ArrayList<>(count);
                    for (int i = 0; i < count; i++) {
                        ItemStack stack = buffer.readItemStack();
                        long timeStart = buffer.readLong();
                        long timeFinish = buffer.readLong();
                        Direction from = Direction.byId(buffer.readByte());
                        Direction to = buffer.readBoolean() ? Direction.byId(buffer.readByte()) : null;

                        ConduitBlockEntity.TravellingItem item = new TravellingItem(stack, from, timeStart, timeFinish);
                        item.to = to;

                        list.add(item);
                    }

                    context.getTaskQueue().execute(() -> {
                        BlockEntity be = context.getPlayer().getEntityWorld().getBlockEntity(pos);
                        if (be instanceof ConduitBlockEntity) {
                            ((ConduitBlockEntity) be).items.addAll(list);
                        }
                    });

                });

        BlockEntityRendererRegistry.INSTANCE.register(IConstruct.ICBlockEntities.CONDUIT, ConduitBlockEntityRenderer::new);
        BlockEntityRendererRegistry.INSTANCE.register(IConstruct.ICBlockEntities.EXTRACTOR, ConduitBlockEntityRenderer::new);
        BlockEntityRendererRegistry.INSTANCE.register(IConstruct.ICBlockEntities.AXLE, AxleBlockEntityRenderer::new);
        BlockEntityRendererRegistry.INSTANCE.register(IConstruct.ICBlockEntities.REDSTONE_ENGINE, EngineBlockEntityRenderer::new);

        FabricModelPredicateProviderRegistry.register(IConstruct.WRENCH, new Identifier("open"),
                (ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity entity) -> {
                    if (entity instanceof PlayerEntity) {
                        PlayerEntity player = (PlayerEntity) entity;
                        if (player.getMainHandStack() == stack || player.getOffHandStack() == stack) {
                            float reachDistance = MinecraftClient.getInstance().interactionManager.getReachDistance();
                            HitResult hit = player.raycast(reachDistance, 0, false);
                            if (hit instanceof BlockHitResult) {
                                BlockHitResult blockhit = (BlockHitResult) hit;
                                BlockPos pos = blockhit.getBlockPos();
                                if (entity.getEntityWorld().getBlockState(pos).getBlock() instanceof WrenchableBlock) {
                                    return 1;
                                }
                            }
                        }
                    }
                    return 0;
                });

        ModelLoadingRegistry.INSTANCE.registerResourceProvider(resources -> new IConstructModels());
    }
}
