package com.github.reoseah.iconstruct;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.github.reoseah.iconstruct.api.blocks.WrenchableBlock;
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
                IConstruct.Blocks.CONDUIT,
                IConstruct.Blocks.EXTRACTOR,
                IConstruct.Blocks.WHITE_CONDUIT,
                IConstruct.Blocks.ORANGE_CONDUIT,
                IConstruct.Blocks.MAGENTA_CONDUIT,
                IConstruct.Blocks.YELLOW_CONDUIT,
                IConstruct.Blocks.LIGHT_BLUE_CONDUIT,
                IConstruct.Blocks.LIME_CONDUIT,
                IConstruct.Blocks.PINK_CONDUIT,
                IConstruct.Blocks.LIGHT_GRAY_CONDUIT,
                IConstruct.Blocks.GRAY_CONDUIT,
                IConstruct.Blocks.CYAN_CONDUIT,
                IConstruct.Blocks.PURPLE_CONDUIT,
                IConstruct.Blocks.BLUE_CONDUIT,
                IConstruct.Blocks.BROWN_CONDUIT,
                IConstruct.Blocks.GREEN_CONDUIT,
                IConstruct.Blocks.RED_CONDUIT,
                IConstruct.Blocks.BLACK_CONDUIT,

                IConstruct.Blocks.WHITE_EXTRACTOR,
                IConstruct.Blocks.ORANGE_EXTRACTOR,
                IConstruct.Blocks.MAGENTA_EXTRACTOR,
                IConstruct.Blocks.YELLOW_EXTRACTOR,
                IConstruct.Blocks.LIGHT_BLUE_EXTRACTOR,
                IConstruct.Blocks.LIME_EXTRACTOR,
                IConstruct.Blocks.PINK_EXTRACTOR,
                IConstruct.Blocks.LIGHT_GRAY_EXTRACTOR,
                IConstruct.Blocks.GRAY_EXTRACTOR,
                IConstruct.Blocks.CYAN_EXTRACTOR,
                IConstruct.Blocks.PURPLE_EXTRACTOR,
                IConstruct.Blocks.BLUE_EXTRACTOR,
                IConstruct.Blocks.BROWN_EXTRACTOR,
                IConstruct.Blocks.GREEN_EXTRACTOR,
                IConstruct.Blocks.RED_EXTRACTOR,
                IConstruct.Blocks.BLACK_EXTRACTOR,

                IConstruct.Blocks.SCAFFOLDING,
                IConstruct.Blocks.CONDUIT_IN_SCAFFOLDING,
                IConstruct.Blocks.CATWALK,
                IConstruct.Blocks.CATWALK_STAIRS,
                IConstruct.Blocks.REINFORCED_GLASS);

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

        BlockEntityRendererRegistry.INSTANCE.register(IConstruct.BlockEntityTypes.CONDUIT, ConduitBlockEntityRenderer::new);
        BlockEntityRendererRegistry.INSTANCE.register(IConstruct.BlockEntityTypes.EXTRACTOR, ConduitBlockEntityRenderer::new);
        BlockEntityRendererRegistry.INSTANCE.register(IConstruct.BlockEntityTypes.AXLE, AxleBlockEntityRenderer::new);
        BlockEntityRendererRegistry.INSTANCE.register(IConstruct.BlockEntityTypes.REDSTONE_ENGINE, EngineBlockEntityRenderer::new);

        FabricModelPredicateProviderRegistry.register(IConstruct.Items.WRENCH, new Identifier("open"),
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
