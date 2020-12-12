package com.github.reoseah.indconstr.client.render;

import com.github.reoseah.indconstr.blocks.AbstractConduitConnectingBlock;
import com.github.reoseah.indconstr.blocks.entities.ConduitBlockEntity;
import com.github.reoseah.indconstr.blocks.entities.ConduitBlockEntity.TravellingItem;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;

public class ConduitBlockEntityRenderer<T extends ConduitBlockEntity> extends BlockEntityRenderer<T> {
    public ConduitBlockEntityRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(ConduitBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (entity.items.isEmpty()) {
            return;
        }
        boolean up = entity.getCachedState().get(AbstractConduitConnectingBlock.UP);
        boolean down = entity.getCachedState().get(AbstractConduitConnectingBlock.DOWN);
        boolean north = entity.getCachedState().get(AbstractConduitConnectingBlock.NORTH);
        boolean south = entity.getCachedState().get(AbstractConduitConnectingBlock.SOUTH);
        boolean west = entity.getCachedState().get(AbstractConduitConnectingBlock.WEST);
        boolean east = entity.getCachedState().get(AbstractConduitConnectingBlock.EAST);

        boolean straight = up && down && !north && !south && !west && !east
                || north && south && !up && !down && !west && !south
                || west && east && !up && !down && !north && !south;
        for (TravellingItem item : entity.items) {
            ItemStack stack = item.stack;

            if (!stack.isEmpty()) {
                matrices.push();
                Vec3d offset = item.interpolatePosition(entity.getWorld().getTime(), tickDelta, straight);
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