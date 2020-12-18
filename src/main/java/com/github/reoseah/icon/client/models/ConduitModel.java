package com.github.reoseah.icon.client.models;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.jetbrains.annotations.Nullable;

import com.github.reoseah.icon.blocks.SimpleConduitBlock;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockRenderView;

@Environment(EnvType.CLIENT)
public class ConduitModel implements UnbakedModel {
    private final IndrConstrModelProvider provider;
    private final @Nullable DyeColor color;

    public ConduitModel(IndrConstrModelProvider provider, @Nullable DyeColor color) {
        this.provider = provider;
        this.color = color;
    }

    @Override
    public Collection<Identifier> getModelDependencies() {
        return ImmutableSet.of(
                new Identifier("icon:block/" + (this.color == null ? "" : this.color.asString() + "_") + "conduit_side"),
                new Identifier("icon:block/" + (this.color == null ? "" : this.color.asString() + "_") + "conduit_top"),
                new Identifier("icon:block/" + (this.color == null ? "" : this.color.asString() + "_") + "conduit_connection"),
                new Identifier("icon:block/" + (this.color == null ? "" : this.color.asString() + "_") + "conduit_vertical_connection"),
                new Identifier("icon:block/conduit_joint"));
    }

    @Override
    public Collection<SpriteIdentifier> getTextureDependencies(Function<Identifier, UnbakedModel> unbakedModelGetter, Set<Pair<String, String>> unresolvedTextureReferences) {
        return this.getModelDependencies().stream().map(id -> unbakedModelGetter.apply(id)).flatMap((UnbakedModel model) -> model.getTextureDependencies(unbakedModelGetter, unresolvedTextureReferences).stream()).collect(Collectors.toSet());
    }

    @Override
    public BakedModel bake(ModelLoader loader, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
        BakedModel[] sides = this.provider.getOrLoadSides(this.color, loader, textureGetter, rotationContainer, modelId);
        BakedModel[] connections = this.provider.getOrLoadConnections(this.color, loader, textureGetter, rotationContainer, modelId);
        BakedModel[] joints = this.provider.getOrLoadJoints(loader, textureGetter, rotationContainer, modelId);

        return new BakedConduitModel(sides, connections, joints);
    }

    public static class BakedConduitModel implements BakedModel, FabricBakedModel {
        private final BakedModel[] sides;
        private final BakedModel[] connections;
        private final BakedModel[] joints;

        public BakedConduitModel(BakedModel[] sides, BakedModel[] connections, BakedModel[] joints) {
            this.sides = sides;
            this.connections = connections;
            this.joints = joints;
        }

        @Override
        public boolean isVanillaAdapter() {
            return false;
        }

        @Override
        public void emitBlockQuads(BlockRenderView blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context) {
            boolean down = state.get(SimpleConduitBlock.DOWN);
            boolean up = state.get(SimpleConduitBlock.UP);
            boolean north = state.get(SimpleConduitBlock.NORTH);
            boolean south = state.get(SimpleConduitBlock.SOUTH);
            boolean west = state.get(SimpleConduitBlock.WEST);
            boolean east = state.get(SimpleConduitBlock.EAST);

            context.fallbackConsumer().accept(down ? this.connections[0] : this.sides[0]);
            context.fallbackConsumer().accept(up ? this.connections[1] : this.sides[1]);
            context.fallbackConsumer().accept(north ? this.connections[2] : this.sides[2]);
            context.fallbackConsumer().accept(south ? this.connections[3] : this.sides[3]);
            context.fallbackConsumer().accept(west ? this.connections[4] : this.sides[4]);
            context.fallbackConsumer().accept(east ? this.connections[5] : this.sides[5]);
            if (north && west) {
                context.fallbackConsumer().accept(this.joints[0]);
            }
            if (north && east) {
                context.fallbackConsumer().accept(this.joints[1]);
            }
            if (south && east) {
                context.fallbackConsumer().accept(this.joints[2]);
            }
            if (south && west) {
                context.fallbackConsumer().accept(this.joints[3]);
            }
            if (up && west) {
                context.fallbackConsumer().accept(this.joints[4]);
            }
            if (up && north) {
                context.fallbackConsumer().accept(this.joints[5]);
            }
            if (up && east) {
                context.fallbackConsumer().accept(this.joints[6]);
            }
            if (up && south) {
                context.fallbackConsumer().accept(this.joints[7]);
            }
            if (down && west) {
                context.fallbackConsumer().accept(this.joints[8]);
            }
            if (down && north) {
                context.fallbackConsumer().accept(this.joints[9]);
            }
            if (down && east) {
                context.fallbackConsumer().accept(this.joints[10]);
            }
            if (down && south) {
                context.fallbackConsumer().accept(this.joints[11]);
            }
        }

        @Override
        public void emitItemQuads(ItemStack stack, Supplier<Random> randomSupplier, RenderContext context) {

        }

        @Override
        public List<BakedQuad> getQuads(BlockState state, Direction face, Random random) {
            return null;
        }

        @Override
        public boolean useAmbientOcclusion() {
            return false;
        }

        @Override
        public boolean hasDepth() {
            return false;
        }

        @Override
        public boolean isSideLit() {
            return false;
        }

        @Override
        public boolean isBuiltin() {
            return false;
        }

        @Override
        public Sprite getSprite() {
            return this.sides[0].getSprite(); // block break particle
        }

        @Override
        public ModelTransformation getTransformation() {
            return null;
        }

        @Override
        public ModelOverrideList getOverrides() {
            return null;
        }
    }
}
