package reoseah.velvet;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.Nullable;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvironmentInterface;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
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
import reoseah.velvet.blocks.Wrenchable;
import reoseah.velvet.blocks.entities.ConduitBlockEntity;
import reoseah.velvet.blocks.entities.ConduitBlockEntity.TravellingItem;
import reoseah.velvet.client.render.ConduitBlockEntityRenderer;

@EnvironmentInterface(itf = ClientModInitializer.class, value = EnvType.CLIENT)
public class VelvetInitializer implements ModInitializer, ClientModInitializer {
    @Override
    public void onInitialize() {
        Velvet.GROUP.getClass();

        Velvet.Blocks.CONDUIT.getClass();
        Velvet.Items.CONDUIT.getClass();
        Velvet.BlockEntityTypes.CONDUIT.getClass();
        Velvet.RecipeSerializers.PAINTROLLER_DYE.getClass();
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutoutMipped(),
                Velvet.Blocks.CONDUIT,
                Velvet.Blocks.EXTRACTOR,
                Velvet.Blocks.NEW_EXTRACTOR,
                Velvet.Blocks.INSERTER,
                Velvet.Blocks.WHITE_CONDUIT,
                Velvet.Blocks.ORANGE_CONDUIT,
                Velvet.Blocks.MAGENTA_CONDUIT,
                Velvet.Blocks.YELLOW_CONDUIT,
                Velvet.Blocks.LIGHT_BLUE_CONDUIT,
                Velvet.Blocks.LIME_CONDUIT,
                Velvet.Blocks.PINK_CONDUIT,
                Velvet.Blocks.LIGHT_GRAY_CONDUIT,
                Velvet.Blocks.GRAY_CONDUIT,
                Velvet.Blocks.CYAN_CONDUIT,
                Velvet.Blocks.PURPLE_CONDUIT,
                Velvet.Blocks.BLUE_CONDUIT,
                Velvet.Blocks.BROWN_CONDUIT,
                Velvet.Blocks.GREEN_CONDUIT,
                Velvet.Blocks.RED_CONDUIT,
                Velvet.Blocks.BLACK_CONDUIT,
                Velvet.Blocks.FRAME,
                Velvet.Blocks.FRAMED_CONDUIT,
                Velvet.Blocks.CATWALK,
                Velvet.Blocks.REINFORCED_GLASS);

        ClientSidePacketRegistry.INSTANCE.register(new Identifier("velvet:conduit"),
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

        BlockEntityRendererRegistry.INSTANCE.register(Velvet.BlockEntityTypes.CONDUIT, ConduitBlockEntityRenderer::new);
        BlockEntityRendererRegistry.INSTANCE.register(Velvet.BlockEntityTypes.EXTRACTOR, ConduitBlockEntityRenderer::new);

        FabricModelPredicateProviderRegistry.register(Velvet.Items.WRENCH, new Identifier("open"),
                (ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity entity) -> {
                    if (entity instanceof PlayerEntity) {
                        PlayerEntity player = (PlayerEntity) entity;
                        if (player.getMainHandStack() == stack || player.getOffHandStack() == stack) {
                            float reachDistance = MinecraftClient.getInstance().interactionManager.getReachDistance();
                            HitResult hit = player.raycast(reachDistance, 0, false);
                            if (hit instanceof BlockHitResult) {
                                BlockHitResult blockhit = (BlockHitResult) hit;
                                BlockPos pos = blockhit.getBlockPos();
                                if (entity.getEntityWorld().getBlockState(pos).getBlock() instanceof Wrenchable) {
                                    return 1;
                                }
                            }
                        }
                    }
                    return 0;
                });
    }
}
