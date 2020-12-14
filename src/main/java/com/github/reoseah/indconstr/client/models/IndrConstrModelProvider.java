package com.github.reoseah.indconstr.client.models;

import java.util.function.Function;

import org.jetbrains.annotations.Nullable;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.ModelProviderContext;
import net.fabricmc.fabric.api.client.model.ModelProviderException;
import net.fabricmc.fabric.api.client.model.ModelResourceProvider;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.ModelRotation;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.render.model.json.ModelVariant;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class IndrConstrModelProvider implements ModelResourceProvider {
    private static final Identifier CONDUIT = new Identifier("indconstr:block/conduit");
    private static final Identifier WHITE_CONDUIT = new Identifier("indconstr:block/white_conduit");
    private static final Identifier ORANGE_CONDUIT = new Identifier("indconstr:block/orange_conduit");
    private static final Identifier MAGENTA_CONDUIT = new Identifier("indconstr:block/magenta_conduit");
    private static final Identifier LIGHT_BLUE_CONDUIT = new Identifier("indconstr:block/light_blue_conduit");
    private static final Identifier YELLOW_CONDUIT = new Identifier("indconstr:block/yellow_conduit");
    private static final Identifier LIME_CONDUIT = new Identifier("indconstr:block/lime_conduit");
    private static final Identifier PINK_CONDUIT = new Identifier("indconstr:block/pink_conduit");
    private static final Identifier GRAY_CONDUIT = new Identifier("indconstr:block/gray_conduit");
    private static final Identifier LIGHT_GRAY_CONDUIT = new Identifier("indconstr:block/light_gray_conduit");
    private static final Identifier CYAN_CONDUIT = new Identifier("indconstr:block/cyan_conduit");
    private static final Identifier PURPLE_CONDUIT = new Identifier("indconstr:block/purple_conduit");
    private static final Identifier BLUE_CONDUIT = new Identifier("indconstr:block/blue_conduit");
    private static final Identifier BROWN_CONDUIT = new Identifier("indconstr:block/brown_conduit");
    private static final Identifier GREEN_CONDUIT = new Identifier("indconstr:block/green_conduit");
    private static final Identifier RED_CONDUIT = new Identifier("indconstr:block/red_conduit");
    private static final Identifier BLACK_CONDUIT = new Identifier("indconstr:block/black_conduit");

    private static final Identifier EXTRACTOR = new Identifier("indconstr:block/extractor");
    private static final Identifier WHITE_EXTRACTOR = new Identifier("indconstr:block/white_extractor");
    private static final Identifier ORANGE_EXTRACTOR = new Identifier("indconstr:block/orange_extractor");
    private static final Identifier MAGENTA_EXTRACTOR = new Identifier("indconstr:block/magenta_extractor");
    private static final Identifier LIGHT_BLUE_EXTRACTOR = new Identifier("indconstr:block/light_blue_extractor");
    private static final Identifier YELLOW_EXTRACTOR = new Identifier("indconstr:block/yellow_extractor");
    private static final Identifier LIME_EXTRACTOR = new Identifier("indconstr:block/lime_extractor");
    private static final Identifier PINK_EXTRACTOR = new Identifier("indconstr:block/pink_extractor");
    private static final Identifier GRAY_EXTRACTOR = new Identifier("indconstr:block/gray_extractor");
    private static final Identifier LIGHT_GRAY_EXTRACTOR = new Identifier("indconstr:block/light_gray_extractor");
    private static final Identifier CYAN_EXTRACTOR = new Identifier("indconstr:block/cyan_extractor");
    private static final Identifier PURPLE_EXTRACTOR = new Identifier("indconstr:block/purple_extractor");
    private static final Identifier BLUE_EXTRACTOR = new Identifier("indconstr:block/blue_extractor");
    private static final Identifier BROWN_EXTRACTOR = new Identifier("indconstr:block/brown_extractor");
    private static final Identifier GREEN_EXTRACTOR = new Identifier("indconstr:block/green_extractor");
    private static final Identifier RED_EXTRACTOR = new Identifier("indconstr:block/red_extractor");
    private static final Identifier BLACK_EXTRACTOR = new Identifier("indconstr:block/black_extractor");

    private BakedModel[][] sides = new BakedModel[17][];
    private BakedModel[][] connections = new BakedModel[17][];
    private BakedModel[] joints;

    private BakedModel[] extractors;
    private BakedModel[][] opaqueConnections = new BakedModel[17][];

    @Override
    public @Nullable UnbakedModel loadModelResource(Identifier resourceId, ModelProviderContext context) throws ModelProviderException {
        if (resourceId.equals(CONDUIT)) {
            return new ConduitModel(this, (DyeColor) null);
        } else if (resourceId.equals(WHITE_CONDUIT)) {
            return new ConduitModel(this, DyeColor.WHITE);
        } else if (resourceId.equals(ORANGE_CONDUIT)) {
            return new ConduitModel(this, DyeColor.ORANGE);
        } else if (resourceId.equals(MAGENTA_CONDUIT)) {
            return new ConduitModel(this, DyeColor.MAGENTA);
        } else if (resourceId.equals(LIGHT_BLUE_CONDUIT)) {
            return new ConduitModel(this, DyeColor.LIGHT_BLUE);
        } else if (resourceId.equals(YELLOW_CONDUIT)) {
            return new ConduitModel(this, DyeColor.YELLOW);
        } else if (resourceId.equals(LIME_CONDUIT)) {
            return new ConduitModel(this, DyeColor.LIME);
        } else if (resourceId.equals(PINK_CONDUIT)) {
            return new ConduitModel(this, DyeColor.PINK);
        } else if (resourceId.equals(GRAY_CONDUIT)) {
            return new ConduitModel(this, DyeColor.GRAY);
        } else if (resourceId.equals(LIGHT_GRAY_CONDUIT)) {
            return new ConduitModel(this, DyeColor.LIGHT_GRAY);
        } else if (resourceId.equals(CYAN_CONDUIT)) {
            return new ConduitModel(this, DyeColor.CYAN);
        } else if (resourceId.equals(PURPLE_CONDUIT)) {
            return new ConduitModel(this, DyeColor.PURPLE);
        } else if (resourceId.equals(BLUE_CONDUIT)) {
            return new ConduitModel(this, DyeColor.BLUE);
        } else if (resourceId.equals(BROWN_CONDUIT)) {
            return new ConduitModel(this, DyeColor.BROWN);
        } else if (resourceId.equals(GREEN_CONDUIT)) {
            return new ConduitModel(this, DyeColor.GREEN);
        } else if (resourceId.equals(RED_CONDUIT)) {
            return new ConduitModel(this, DyeColor.RED);
        } else if (resourceId.equals(BLACK_CONDUIT)) {
            return new ConduitModel(this, DyeColor.BLACK);
        }
        if (resourceId.equals(EXTRACTOR)) {
            return new ExtractorModel(this, (DyeColor) null);
        } else if (resourceId.equals(WHITE_EXTRACTOR)) {
            return new ExtractorModel(this, DyeColor.WHITE);
        } else if (resourceId.equals(ORANGE_EXTRACTOR)) {
            return new ExtractorModel(this, DyeColor.ORANGE);
        } else if (resourceId.equals(MAGENTA_EXTRACTOR)) {
            return new ExtractorModel(this, DyeColor.MAGENTA);
        } else if (resourceId.equals(LIGHT_BLUE_EXTRACTOR)) {
            return new ExtractorModel(this, DyeColor.LIGHT_BLUE);
        } else if (resourceId.equals(YELLOW_EXTRACTOR)) {
            return new ExtractorModel(this, DyeColor.YELLOW);
        } else if (resourceId.equals(LIME_EXTRACTOR)) {
            return new ExtractorModel(this, DyeColor.LIME);
        } else if (resourceId.equals(PINK_EXTRACTOR)) {
            return new ExtractorModel(this, DyeColor.PINK);
        } else if (resourceId.equals(GRAY_EXTRACTOR)) {
            return new ExtractorModel(this, DyeColor.GRAY);
        } else if (resourceId.equals(LIGHT_GRAY_EXTRACTOR)) {
            return new ExtractorModel(this, DyeColor.LIGHT_GRAY);
        } else if (resourceId.equals(CYAN_EXTRACTOR)) {
            return new ExtractorModel(this, DyeColor.CYAN);
        } else if (resourceId.equals(PURPLE_EXTRACTOR)) {
            return new ExtractorModel(this, DyeColor.PURPLE);
        } else if (resourceId.equals(BLUE_EXTRACTOR)) {
            return new ExtractorModel(this, DyeColor.BLUE);
        } else if (resourceId.equals(BROWN_EXTRACTOR)) {
            return new ExtractorModel(this, DyeColor.BROWN);
        } else if (resourceId.equals(GREEN_EXTRACTOR)) {
            return new ExtractorModel(this, DyeColor.GREEN);
        } else if (resourceId.equals(RED_EXTRACTOR)) {
            return new ExtractorModel(this, DyeColor.RED);
        } else if (resourceId.equals(BLACK_EXTRACTOR)) {
            return new ExtractorModel(this, DyeColor.BLACK);
        }
        return null;
    }

    public BakedModel[] getOrLoadSides(@Nullable DyeColor color, ModelLoader loader, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
        int idx = color == null ? 0 : 1 + color.ordinal();
        if (this.sides[idx] == null) {
            UnbakedModel side = loader.getOrLoadModel(new Identifier("indconstr:block/" + (color == null ? "" : color.asString() + "_") + "conduit_side"));
            UnbakedModel top = loader.getOrLoadModel(new Identifier("indconstr:block/" + (color == null ? "" : color.asString() + "_") + "conduit_top"));
            this.sides[idx] = new BakedModel[] {
                    top.bake(loader, textureGetter, new ModelVariant(modelId, ModelRotation.X90_Y0.getRotation(), true, 1), modelId), // down
                    top.bake(loader, textureGetter, new ModelVariant(modelId, ModelRotation.X270_Y0.getRotation(), true, 1), modelId), // up
                    side.bake(loader, textureGetter, new ModelVariant(modelId, ModelRotation.X0_Y0.getRotation(), true, 1), modelId), // north
                    side.bake(loader, textureGetter, new ModelVariant(modelId, ModelRotation.X0_Y180.getRotation(), true, 1), modelId), // south
                    side.bake(loader, textureGetter, new ModelVariant(modelId, ModelRotation.X0_Y270.getRotation(), true, 1), modelId), // west
                    side.bake(loader, textureGetter, new ModelVariant(modelId, ModelRotation.X0_Y90.getRotation(), true, 1), modelId), // east
            };
        }
        return this.sides[idx];
    }

    public BakedModel[] getOrLoadConnections(@Nullable DyeColor color, ModelLoader loader, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
        int idx = color == null ? 0 : 1 + color.ordinal();
        if (this.connections[idx] == null) {
            UnbakedModel side = loader.getOrLoadModel(new Identifier("indconstr:block/" + (color == null ? "" : color.asString() + "_") + "conduit_connection"));
            UnbakedModel top = loader.getOrLoadModel(new Identifier("indconstr:block/" + (color == null ? "" : color.asString() + "_") + "conduit_vertical_connection"));
            this.connections[idx] = new BakedModel[] {
                    top.bake(loader, textureGetter, new ModelVariant(modelId, ModelRotation.X90_Y0.getRotation(), true, 1), modelId), // down
                    top.bake(loader, textureGetter, new ModelVariant(modelId, ModelRotation.X270_Y0.getRotation(), true, 1), modelId), // up
                    side.bake(loader, textureGetter, new ModelVariant(modelId, ModelRotation.X0_Y0.getRotation(), true, 1), modelId), // north
                    side.bake(loader, textureGetter, new ModelVariant(modelId, ModelRotation.X0_Y180.getRotation(), true, 1), modelId), // south
                    side.bake(loader, textureGetter, new ModelVariant(modelId, ModelRotation.X0_Y270.getRotation(), true, 1), modelId), // west
                    side.bake(loader, textureGetter, new ModelVariant(modelId, ModelRotation.X0_Y90.getRotation(), true, 1), modelId), // east
            };
        }
        return this.connections[idx];
    }

    public BakedModel[] getOrLoadJoints(ModelLoader loader, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
        if (this.joints == null) {
            UnbakedModel joint = loader.getOrLoadModel(new Identifier("indconstr:block/conduit_joint"));
            this.joints = new BakedModel[] {
                    joint.bake(loader, textureGetter, ModelRotation.X0_Y0, modelId), // north & west
                    joint.bake(loader, textureGetter, ModelRotation.X0_Y90, modelId), // north & east
                    joint.bake(loader, textureGetter, ModelRotation.X0_Y180, modelId), // south & east
                    joint.bake(loader, textureGetter, ModelRotation.X0_Y270, modelId), // south & west

                    joint.bake(loader, textureGetter, ModelRotation.X270_Y0, modelId), // up & west
                    joint.bake(loader, textureGetter, ModelRotation.X270_Y90, modelId), // up & north
                    joint.bake(loader, textureGetter, ModelRotation.X270_Y180, modelId), // up & east
                    joint.bake(loader, textureGetter, ModelRotation.X270_Y270, modelId), // up & south

                    joint.bake(loader, textureGetter, ModelRotation.X90_Y0, modelId), // down & west
                    joint.bake(loader, textureGetter, ModelRotation.X90_Y90, modelId), // down & north
                    joint.bake(loader, textureGetter, ModelRotation.X90_Y180, modelId), // down & east
                    joint.bake(loader, textureGetter, ModelRotation.X90_Y270, modelId), // down & south
            };
        }
        return this.joints;
    }

    public BakedModel[] getOrLoadExtractors(ModelLoader loader, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
        if (this.extractors == null) {
            UnbakedModel model = loader.getOrLoadModel(new Identifier("indconstr:block/extractor_connection"));
            this.extractors = new BakedModel[] {
                    model.bake(loader, textureGetter, new ModelVariant(modelId, ModelRotation.X90_Y0.getRotation(), true, 1), modelId), // down
                    model.bake(loader, textureGetter, new ModelVariant(modelId, ModelRotation.X270_Y0.getRotation(), true, 1), modelId), // up
                    model.bake(loader, textureGetter, new ModelVariant(modelId, ModelRotation.X0_Y0.getRotation(), true, 1), modelId), // north
                    model.bake(loader, textureGetter, new ModelVariant(modelId, ModelRotation.X0_Y180.getRotation(), true, 1), modelId), // south
                    model.bake(loader, textureGetter, new ModelVariant(modelId, ModelRotation.X0_Y270.getRotation(), true, 1), modelId), // west
                    model.bake(loader, textureGetter, new ModelVariant(modelId, ModelRotation.X0_Y90.getRotation(), true, 1), modelId), // east
            };
        }
        return this.extractors;
    }

    public BakedModel[] getOrLoadOpaqueConnections(@Nullable DyeColor color, ModelLoader loader, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
        int idx = color == null ? 0 : 1 + color.ordinal();
        if (this.opaqueConnections[idx] == null) {
            UnbakedModel side = loader.getOrLoadModel(new Identifier("indconstr:block/" + (color == null ? "" : color.asString() + "_") + "opaque_conduit_connection"));
            UnbakedModel top = loader.getOrLoadModel(new Identifier("indconstr:block/" + (color == null ? "" : color.asString() + "_") + "opaque_conduit_vertical_connection"));
            this.opaqueConnections[idx] = new BakedModel[] {
                    top.bake(loader, textureGetter, new ModelVariant(modelId, ModelRotation.X90_Y0.getRotation(), true, 1), modelId), // down
                    top.bake(loader, textureGetter, new ModelVariant(modelId, ModelRotation.X270_Y0.getRotation(), true, 1), modelId), // up
                    side.bake(loader, textureGetter, new ModelVariant(modelId, ModelRotation.X0_Y0.getRotation(), true, 1), modelId), // north
                    side.bake(loader, textureGetter, new ModelVariant(modelId, ModelRotation.X0_Y180.getRotation(), true, 1), modelId), // south
                    side.bake(loader, textureGetter, new ModelVariant(modelId, ModelRotation.X0_Y270.getRotation(), true, 1), modelId), // west
                    side.bake(loader, textureGetter, new ModelVariant(modelId, ModelRotation.X0_Y90.getRotation(), true, 1), modelId), // east
            };
        }
        return this.opaqueConnections[idx];
    }

}
