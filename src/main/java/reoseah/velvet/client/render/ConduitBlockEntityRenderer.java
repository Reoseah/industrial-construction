package reoseah.velvet.client.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import reoseah.velvet.blocks.entities.ConduitBlockEntity;
import reoseah.velvet.blocks.entities.ConduitBlockEntity.TravellingItem;

public class ConduitBlockEntityRenderer extends BlockEntityRenderer<ConduitBlockEntity> {
    public ConduitBlockEntityRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(ConduitBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        for (TravellingItem item : entity.items) {
            ItemStack stack = item.stack;

            if (!stack.isEmpty()) {
                matrices.push();
                Vec3d offset = item.getRenderPos(entity.getWorld().getTime(), tickDelta);
                matrices.translate(offset.x, offset.y, offset.z);
                matrices.multiply(Vector3f.POSITIVE_Y.getRadialQuaternion((entity.getWorld().getTime() + tickDelta) / 20));
                matrices.scale(0.5f, 0.5f, 0.5f);
                MinecraftClient.getInstance().getItemRenderer()
                        .renderItem(stack, ModelTransformation.Mode.FIXED, light, overlay, matrices, vertexConsumers);
                matrices.pop();
            }
        }
    }
}
