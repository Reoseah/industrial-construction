package com.github.reoseah.indconstr;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.github.reoseah.indconstr.api.blocks.WrenchableBlock;
import com.github.reoseah.indconstr.blocks.entities.ConduitBlockEntity;
import com.github.reoseah.indconstr.blocks.entities.ConduitBlockEntity.TravellingItem;
import com.github.reoseah.indconstr.client.models.IndrConstrModelProvider;
import com.github.reoseah.indconstr.client.render.TransparentConduitBlockEntityRenderer;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvironmentInterface;
import net.fabricmc.api.ModInitializer;
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

@EnvironmentInterface(itf = ClientModInitializer.class, value = EnvType.CLIENT)
public class IndConstrInitializer implements ModInitializer, ClientModInitializer {
    @Override
    public void onInitialize() {
        IndConstr.ITEMGROUP.getClass();

        IndConstr.Blocks.CONDUIT.getClass();
        IndConstr.Items.CONDUIT.getClass();
        IndConstr.BlockEntityTypes.CONDUIT.getClass();
        IndConstr.RecipeSerializers.PAINTROLLER_FILLING.getClass();
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutoutMipped(),
                IndConstr.Blocks.CONDUIT,
                IndConstr.Blocks.EXTRACTOR,
                IndConstr.Blocks.WHITE_CONDUIT,
                IndConstr.Blocks.ORANGE_CONDUIT,
                IndConstr.Blocks.MAGENTA_CONDUIT,
                IndConstr.Blocks.YELLOW_CONDUIT,
                IndConstr.Blocks.LIGHT_BLUE_CONDUIT,
                IndConstr.Blocks.LIME_CONDUIT,
                IndConstr.Blocks.PINK_CONDUIT,
                IndConstr.Blocks.LIGHT_GRAY_CONDUIT,
                IndConstr.Blocks.GRAY_CONDUIT,
                IndConstr.Blocks.CYAN_CONDUIT,
                IndConstr.Blocks.PURPLE_CONDUIT,
                IndConstr.Blocks.BLUE_CONDUIT,
                IndConstr.Blocks.BROWN_CONDUIT,
                IndConstr.Blocks.GREEN_CONDUIT,
                IndConstr.Blocks.RED_CONDUIT,
                IndConstr.Blocks.BLACK_CONDUIT,

                IndConstr.Blocks.WHITE_EXTRACTOR,
                IndConstr.Blocks.ORANGE_EXTRACTOR,
                IndConstr.Blocks.MAGENTA_EXTRACTOR,
                IndConstr.Blocks.YELLOW_EXTRACTOR,
                IndConstr.Blocks.LIGHT_BLUE_EXTRACTOR,
                IndConstr.Blocks.LIME_EXTRACTOR,
                IndConstr.Blocks.PINK_EXTRACTOR,
                IndConstr.Blocks.LIGHT_GRAY_EXTRACTOR,
                IndConstr.Blocks.GRAY_EXTRACTOR,
                IndConstr.Blocks.CYAN_EXTRACTOR,
                IndConstr.Blocks.PURPLE_EXTRACTOR,
                IndConstr.Blocks.BLUE_EXTRACTOR,
                IndConstr.Blocks.BROWN_EXTRACTOR,
                IndConstr.Blocks.GREEN_EXTRACTOR,
                IndConstr.Blocks.RED_EXTRACTOR,
                IndConstr.Blocks.BLACK_EXTRACTOR,

                IndConstr.Blocks.SCAFFOLDING,
                IndConstr.Blocks.CONDUIT_IN_SCAFFOLDING,
                IndConstr.Blocks.CATWALK,
                IndConstr.Blocks.CATWALK_STAIRS,
                IndConstr.Blocks.REINFORCED_GLASS);

        ClientSidePacketRegistry.INSTANCE.register(new Identifier("indconstr:conduit"),
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

        BlockEntityRendererRegistry.INSTANCE.register(IndConstr.BlockEntityTypes.CONDUIT, TransparentConduitBlockEntityRenderer::new);
        BlockEntityRendererRegistry.INSTANCE.register(IndConstr.BlockEntityTypes.EXTRACTOR, TransparentConduitBlockEntityRenderer::new);

        FabricModelPredicateProviderRegistry.register(IndConstr.Items.WRENCH, new Identifier("open"),
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
