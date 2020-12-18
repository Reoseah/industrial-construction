package com.github.reoseah.icon;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.github.reoseah.icon.api.blocks.WrenchableBlock;
import com.github.reoseah.icon.blocks.entities.ConduitBlockEntity;
import com.github.reoseah.icon.blocks.entities.ConduitBlockEntity.TravellingItem;
import com.github.reoseah.icon.client.models.IndrConstrModelProvider;
import com.github.reoseah.icon.client.render.AxleBlockEntityRenderer;
import com.github.reoseah.icon.client.render.ConduitBlockEntityRenderer;

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

public class IConClientInitializer implements ClientModInitializer {
    @Override
    @Environment(EnvType.CLIENT)
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutoutMipped(),
                ICon.Blocks.CONDUIT,
                ICon.Blocks.EXTRACTOR,
                ICon.Blocks.WHITE_CONDUIT,
                ICon.Blocks.ORANGE_CONDUIT,
                ICon.Blocks.MAGENTA_CONDUIT,
                ICon.Blocks.YELLOW_CONDUIT,
                ICon.Blocks.LIGHT_BLUE_CONDUIT,
                ICon.Blocks.LIME_CONDUIT,
                ICon.Blocks.PINK_CONDUIT,
                ICon.Blocks.LIGHT_GRAY_CONDUIT,
                ICon.Blocks.GRAY_CONDUIT,
                ICon.Blocks.CYAN_CONDUIT,
                ICon.Blocks.PURPLE_CONDUIT,
                ICon.Blocks.BLUE_CONDUIT,
                ICon.Blocks.BROWN_CONDUIT,
                ICon.Blocks.GREEN_CONDUIT,
                ICon.Blocks.RED_CONDUIT,
                ICon.Blocks.BLACK_CONDUIT,

                ICon.Blocks.WHITE_EXTRACTOR,
                ICon.Blocks.ORANGE_EXTRACTOR,
                ICon.Blocks.MAGENTA_EXTRACTOR,
                ICon.Blocks.YELLOW_EXTRACTOR,
                ICon.Blocks.LIGHT_BLUE_EXTRACTOR,
                ICon.Blocks.LIME_EXTRACTOR,
                ICon.Blocks.PINK_EXTRACTOR,
                ICon.Blocks.LIGHT_GRAY_EXTRACTOR,
                ICon.Blocks.GRAY_EXTRACTOR,
                ICon.Blocks.CYAN_EXTRACTOR,
                ICon.Blocks.PURPLE_EXTRACTOR,
                ICon.Blocks.BLUE_EXTRACTOR,
                ICon.Blocks.BROWN_EXTRACTOR,
                ICon.Blocks.GREEN_EXTRACTOR,
                ICon.Blocks.RED_EXTRACTOR,
                ICon.Blocks.BLACK_EXTRACTOR,

                ICon.Blocks.SCAFFOLDING,
                ICon.Blocks.CONDUIT_IN_SCAFFOLDING,
                ICon.Blocks.CATWALK,
                ICon.Blocks.CATWALK_STAIRS,
                ICon.Blocks.REINFORCED_GLASS);

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

        BlockEntityRendererRegistry.INSTANCE.register(ICon.BlockEntityTypes.CONDUIT, ConduitBlockEntityRenderer::new);
        BlockEntityRendererRegistry.INSTANCE.register(ICon.BlockEntityTypes.EXTRACTOR, ConduitBlockEntityRenderer::new);
        BlockEntityRendererRegistry.INSTANCE.register(ICon.BlockEntityTypes.AXLE, AxleBlockEntityRenderer::new);

        FabricModelPredicateProviderRegistry.register(ICon.Items.WRENCH, new Identifier("open"),
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

        ModelLoadingRegistry.INSTANCE.registerResourceProvider(resources -> new IndrConstrModelProvider());
    }
}
