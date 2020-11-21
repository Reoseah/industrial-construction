package reoseah.velvet;

import java.util.ArrayList;
import java.util.List;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.fabricmc.fabric.api.network.PacketConsumer;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import reoseah.velvet.blocks.entities.ConduitBlockEntity;
import reoseah.velvet.blocks.entities.ConduitBlockEntity.TravellingItem;
import reoseah.velvet.client.render.ConduitBlockEntityRenderer;

public class VelvetClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutoutMipped(),
                Velvet.Blocks.CONDUIT,
                Velvet.Blocks.EXTRACTOR,
                Velvet.Blocks.FRAME,
                Velvet.Blocks.FRAMED_CONDUIT,
                Velvet.Blocks.CATWALK);

        ClientSidePacketRegistry.INSTANCE.register(new Identifier("velvet:conduit"), new PacketConsumer() {
            @Override
            public void accept(PacketContext context, PacketByteBuf buffer) {
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
            }
        });

        BlockEntityRendererRegistry.INSTANCE.register(Velvet.BlockEntityTypes.CONDUIT, ConduitBlockEntityRenderer::new);
        BlockEntityRendererRegistry.INSTANCE.register(Velvet.BlockEntityTypes.EXTRACTOR, ConduitBlockEntityRenderer::new);
    }

}
