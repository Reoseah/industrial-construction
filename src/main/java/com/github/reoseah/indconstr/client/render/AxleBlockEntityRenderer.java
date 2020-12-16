package com.github.reoseah.indconstr.client.render;

import com.github.reoseah.indconstr.blocks.AxleBlock;
import com.github.reoseah.indconstr.blocks.entities.AxleBlockEntity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction.Axis;

public class AxleBlockEntityRenderer extends BlockEntityRenderer<AxleBlockEntity> {
    private static final ModelIdentifier MODEL_ID_Y = new ModelIdentifier(new Identifier("indconstr:axle"), "axis=y");
    private static final ModelIdentifier MODEL_ID_X = new ModelIdentifier(new Identifier("indconstr:axle"), "axis=x");
    private static final ModelIdentifier MODEL_ID_Z = new ModelIdentifier(new Identifier("indconstr:axle"), "axis=z");

    public AxleBlockEntityRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(AxleBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        MinecraftClient mc = MinecraftClient.getInstance();
        Axis axis = entity.getCachedState().get(AxleBlock.AXIS);
        BakedModel model = mc.getBakedModelManager().getModel(axis == Axis.Y ? MODEL_ID_Y : axis == Axis.X ? MODEL_ID_X : MODEL_ID_Z);
        BlockPos pos = entity.getPos();
        matrices.push();
        matrices.translate(0.5F, 0.5F, 0.5F);
        Vector3f rotAxis = axis == Axis.Y ? Vector3f.NEGATIVE_Y : axis == Axis.X ? Vector3f.NEGATIVE_X : Vector3f.NEGATIVE_Z;
        matrices.multiply(rotAxis.getRadialQuaternion(
                entity.angle + (entity.isRotating ? (float) (3.6 * tickDelta / 180F * Math.PI) : 0)));

        matrices.translate(-0.5F, -0.5F, -0.5F);
        mc.getBlockRenderManager()
                .getModelRenderer()
                .render(entity.getWorld(), model, entity.getCachedState(), pos, matrices, vertexConsumers.getBuffer(RenderLayer.getCutout()), false, entity.getWorld().getRandom(), 1, 0xFFFFFF);
        matrices.pop();
    }

}
