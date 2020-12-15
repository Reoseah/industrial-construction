package com.github.reoseah.indconstr.client.models;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.jetbrains.annotations.Nullable;

import com.github.reoseah.indconstr.blocks.AbstractExtractorBlock;
import com.github.reoseah.indconstr.blocks.SpecialConduitBlock;
import com.github.reoseah.indconstr.blocks.SpecialConduitBlock.ConnectionType;
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
public class ExtractorModel implements UnbakedModel {
    private final IndrConstrModelProvider provider;
    private final @Nullable DyeColor color;

    public ExtractorModel(IndrConstrModelProvider provider, @Nullable DyeColor color) {
        this.provider = provider;
        this.color = color;
    }

    @Override
    public Collection<Identifier> getModelDependencies() {
        return ImmutableSet.of(
                new Identifier("indconstr:block/" + (this.color == null ? "" : this.color.asString() + "_") + "conduit_side"),
                new Identifier("indconstr:block/" + (this.color == null ? "" : this.color.asString() + "_") + "conduit_top"),
                new Identifier("indconstr:block/" + (this.color == null ? "" : this.color.asString() + "_") + "conduit_connection"),
                new Identifier("indconstr:block/" + (this.color == null ? "" : this.color.asString() + "_") + "conduit_vertical_connection"),
                new Identifier("indconstr:block/" + (this.color == null ? "" : this.color.asString() + "_") + "opaque_conduit_connection"),
                new Identifier("indconstr:block/" + (this.color == null ? "" : this.color.asString() + "_") + "opaque_conduit_vertical_connection"),
                new Identifier("indconstr:block/extractor_connection"),
                new Identifier("indconstr:block/conduit_joint"));
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
        BakedModel[] extractors = this.provider.getOrLoadExtractors(loader, textureGetter, rotationContainer, modelId);
        BakedModel[] opaqueConnections = this.provider.getOrLoadOpaqueConnections(this.color, loader, textureGetter, rotationContainer, modelId);

        return new BakedExtractorModel(sides, connections, joints, extractors, opaqueConnections);
    }

    public static class BakedExtractorModel implements BakedModel, FabricBakedModel {
        private final BakedModel[] sides;
        private final BakedModel[] connections;
        private final BakedModel[] joints;
        private final BakedModel[] extractors;
        private final BakedModel[] opaqueConnections;

        public BakedExtractorModel(BakedModel[] sides, BakedModel[] connections, BakedModel[] joints, BakedModel[] extractors, BakedModel[] opaqueConnections) {
            this.sides = sides;
            this.connections = connections;
            this.joints = joints;
            this.extractors = extractors;
            this.opaqueConnections = opaqueConnections;
        }

        @Override
        public boolean isVanillaAdapter() {
            return false;
        }

        @Override
        public void emitBlockQuads(BlockRenderView blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context) {
            ConnectionType down = state.get(SpecialConduitBlock.DOWN);
            ConnectionType up = state.get(SpecialConduitBlock.UP);
            ConnectionType north = state.get(SpecialConduitBlock.NORTH);
            ConnectionType south = state.get(SpecialConduitBlock.SOUTH);
            ConnectionType west = state.get(SpecialConduitBlock.WEST);
            ConnectionType east = state.get(SpecialConduitBlock.EAST);
            Direction direction = state.get(AbstractExtractorBlock.DIRECTION);

            context.fallbackConsumer().accept(this.extractors[direction.ordinal()]);

            if (direction != Direction.DOWN) {
                context.fallbackConsumer().accept(down == ConnectionType.SPECIAL ? this.opaqueConnections[0] : down == ConnectionType.NORMAL ? this.connections[0] : this.sides[0]);
            }
            if (direction != Direction.UP) {
                context.fallbackConsumer().accept(up == ConnectionType.SPECIAL ? this.opaqueConnections[1] : up == ConnectionType.NORMAL ? this.connections[1] : this.sides[1]);
            }
            if (direction != Direction.NORTH) {
                context.fallbackConsumer().accept(north == ConnectionType.SPECIAL ? this.opaqueConnections[2] : north == ConnectionType.NORMAL ? this.connections[2] : this.sides[2]);
            }
            if (direction != Direction.SOUTH) {
                context.fallbackConsumer().accept(south == ConnectionType.SPECIAL ? this.opaqueConnections[3] : south == ConnectionType.NORMAL ? this.connections[3] : this.sides[3]);
            }
            if (direction != Direction.WEST) {
                context.fallbackConsumer().accept(west == ConnectionType.SPECIAL ? this.opaqueConnections[4] : west == ConnectionType.NORMAL ? this.connections[4] : this.sides[4]);
            }
            if (direction != Direction.EAST) {
                context.fallbackConsumer().accept(east == ConnectionType.SPECIAL ? this.opaqueConnections[5] : east == ConnectionType.NORMAL ? this.connections[5] : this.sides[5]);
            }

            if (north == ConnectionType.NORMAL && west == ConnectionType.NORMAL) {
                context.fallbackConsumer().accept(this.joints[0]);
            }
            if (north == ConnectionType.NORMAL && east == ConnectionType.NORMAL) {
                context.fallbackConsumer().accept(this.joints[1]);
            }
            if (south == ConnectionType.NORMAL && east == ConnectionType.NORMAL) {
                context.fallbackConsumer().accept(this.joints[2]);
            }
            if (south == ConnectionType.NORMAL && west == ConnectionType.NORMAL) {
                context.fallbackConsumer().accept(this.joints[3]);
            }
            if (up == ConnectionType.NORMAL && west == ConnectionType.NORMAL) {
                context.fallbackConsumer().accept(this.joints[4]);
            }
            if (up == ConnectionType.NORMAL && north == ConnectionType.NORMAL) {
                context.fallbackConsumer().accept(this.joints[5]);
            }
            if (up == ConnectionType.NORMAL && east == ConnectionType.NORMAL) {
                context.fallbackConsumer().accept(this.joints[6]);
            }
            if (up == ConnectionType.NORMAL && south == ConnectionType.NORMAL) {
                context.fallbackConsumer().accept(this.joints[7]);
            }
            if (down == ConnectionType.NORMAL && west == ConnectionType.NORMAL) {
                context.fallbackConsumer().accept(this.joints[8]);
            }
            if (down == ConnectionType.NORMAL && north == ConnectionType.NORMAL) {
                context.fallbackConsumer().accept(this.joints[9]);
            }
            if (down == ConnectionType.NORMAL && east == ConnectionType.NORMAL) {
                context.fallbackConsumer().accept(this.joints[10]);
            }
            if (down == ConnectionType.NORMAL && south == ConnectionType.NORMAL) {
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
