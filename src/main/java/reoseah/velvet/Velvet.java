package reoseah.velvet;

import org.jetbrains.annotations.Nullable;

import com.google.common.collect.ImmutableSet;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.GlassBlock;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import reoseah.velvet.blocks.CatwalkBlock;
import reoseah.velvet.blocks.ConduitBlock;
import reoseah.velvet.blocks.ExtractorBlock;
import reoseah.velvet.blocks.FrameBlock;
import reoseah.velvet.blocks.FramedConduitBlock;
import reoseah.velvet.blocks.entities.ConduitBlockEntity;
import reoseah.velvet.blocks.entities.ExtractorBlockEntity;
import reoseah.velvet.items.PaintRollerItem;
import reoseah.velvet.items.PaintScrapperItem;
import reoseah.velvet.items.WrenchItem;
import reoseah.velvet.recipes.PaintRollerDyeRecipe;

public final class Velvet {
    public static final ItemGroup GROUP = FabricItemGroupBuilder.build(new Identifier("velvet:main"), () -> new ItemStack(Velvet.Items.FRAME));

    public static final class Blocks {
        private static final FabricBlockSettings IRON_SETTINGS = FabricBlockSettings.of(Material.METAL, MaterialColor.GRAY).strength(2F, 10F).nonOpaque().sounds(BlockSoundGroup.LANTERN).breakByTool(FabricToolTags.PICKAXES);

        public static final Block CONDUIT = register("conduit", new ConduitBlock((DyeColor) null, IRON_SETTINGS));
        public static final Block EXTRACTOR = register("extractor", new ExtractorBlock((DyeColor) null, IRON_SETTINGS));

        public static final Block FRAME = register("frame", new FrameBlock(IRON_SETTINGS));
        public static final Block FRAMED_CONDUIT = register("framed_conduit", new FramedConduitBlock(IRON_SETTINGS));
        public static final Block CATWALK = register("catwalk", new CatwalkBlock(IRON_SETTINGS));
        public static final Block REINFORCED_GLASS = register("reinforced_glass", new GlassBlock(FabricBlockSettings.of(Material.GLASS).strength(2F, 10F).nonOpaque().sounds(BlockSoundGroup.GLASS).requiresTool().breakByTool(FabricToolTags.PICKAXES)));

        public static final Block WHITE_CONDUIT = register("white_conduit", new ConduitBlock(DyeColor.WHITE, IRON_SETTINGS));
        public static final Block ORANGE_CONDUIT = register("orange_conduit", new ConduitBlock(DyeColor.ORANGE, IRON_SETTINGS));
        public static final Block MAGENTA_CONDUIT = register("magenta_conduit", new ConduitBlock(DyeColor.MAGENTA, IRON_SETTINGS));
        public static final Block LIGHT_BLUE_CONDUIT = register("light_blue_conduit", new ConduitBlock(DyeColor.LIGHT_BLUE, IRON_SETTINGS));
        public static final Block YELLOW_CONDUIT = register("yellow_conduit", new ConduitBlock(DyeColor.YELLOW, IRON_SETTINGS));
        public static final Block LIME_CONDUIT = register("lime_conduit", new ConduitBlock(DyeColor.LIME, IRON_SETTINGS));
        public static final Block PINK_CONDUIT = register("pink_conduit", new ConduitBlock(DyeColor.PINK, IRON_SETTINGS));
        public static final Block GRAY_CONDUIT = register("gray_conduit", new ConduitBlock(DyeColor.GRAY, IRON_SETTINGS));
        public static final Block LIGHT_GRAY_CONDUIT = register("light_gray_conduit", new ConduitBlock(DyeColor.LIGHT_GRAY, IRON_SETTINGS));
        public static final Block CYAN_CONDUIT = register("cyan_conduit", new ConduitBlock(DyeColor.CYAN, IRON_SETTINGS));
        public static final Block PURPLE_CONDUIT = register("purple_conduit", new ConduitBlock(DyeColor.PURPLE, IRON_SETTINGS));
        public static final Block BLUE_CONDUIT = register("blue_conduit", new ConduitBlock(DyeColor.BLUE, IRON_SETTINGS));
        public static final Block BROWN_CONDUIT = register("brown_conduit", new ConduitBlock(DyeColor.BROWN, IRON_SETTINGS));
        public static final Block GREEN_CONDUIT = register("green_conduit", new ConduitBlock(DyeColor.GREEN, IRON_SETTINGS));
        public static final Block RED_CONDUIT = register("red_conduit", new ConduitBlock(DyeColor.RED, IRON_SETTINGS));
        public static final Block BLACK_CONDUIT = register("black_conduit", new ConduitBlock(DyeColor.BLACK, IRON_SETTINGS));

        public static final Block WHITE_EXTRACTOR = register("white_extractor", new ExtractorBlock(DyeColor.WHITE, IRON_SETTINGS));
        public static final Block ORANGE_EXTRACTOR = register("orange_extractor", new ExtractorBlock(DyeColor.ORANGE, IRON_SETTINGS));
        public static final Block MAGENTA_EXTRACTOR = register("magenta_extractor", new ExtractorBlock(DyeColor.MAGENTA, IRON_SETTINGS));
        public static final Block LIGHT_BLUE_EXTRACTOR = register("light_blue_extractor", new ExtractorBlock(DyeColor.LIGHT_BLUE, IRON_SETTINGS));
        public static final Block YELLOW_EXTRACTOR = register("yellow_extractor", new ExtractorBlock(DyeColor.YELLOW, IRON_SETTINGS));
        public static final Block LIME_EXTRACTOR = register("lime_extractor", new ExtractorBlock(DyeColor.LIME, IRON_SETTINGS));
        public static final Block PINK_EXTRACTOR = register("pink_extractor", new ExtractorBlock(DyeColor.PINK, IRON_SETTINGS));
        public static final Block GRAY_EXTRACTOR = register("gray_extractor", new ExtractorBlock(DyeColor.GRAY, IRON_SETTINGS));
        public static final Block LIGHT_GRAY_EXTRACTOR = register("light_gray_extractor", new ExtractorBlock(DyeColor.LIGHT_GRAY, IRON_SETTINGS));
        public static final Block CYAN_EXTRACTOR = register("cyan_extractor", new ExtractorBlock(DyeColor.CYAN, IRON_SETTINGS));
        public static final Block PURPLE_EXTRACTOR = register("purple_extractor", new ExtractorBlock(DyeColor.PURPLE, IRON_SETTINGS));
        public static final Block BLUE_EXTRACTOR = register("blue_extractor", new ExtractorBlock(DyeColor.BLUE, IRON_SETTINGS));
        public static final Block BROWN_EXTRACTOR = register("brown_extractor", new ExtractorBlock(DyeColor.BROWN, IRON_SETTINGS));
        public static final Block GREEN_EXTRACTOR = register("green_extractor", new ExtractorBlock(DyeColor.GREEN, IRON_SETTINGS));
        public static final Block RED_EXTRACTOR = register("red_extractor", new ExtractorBlock(DyeColor.RED, IRON_SETTINGS));
        public static final Block BLACK_EXTRACTOR = register("black_extractor", new ExtractorBlock(DyeColor.BLACK, IRON_SETTINGS));

        private static Block register(String name, Block block) {
            return Registry.register(Registry.BLOCK, "velvet:" + name, block);
        }
    }

    public static final class Items {
        public static final Item CONDUIT = register("conduit", new BlockItem(Velvet.Blocks.CONDUIT, new Item.Settings().group(Velvet.GROUP)));
        public static final Item EXTRACTOR = register("extractor", new BlockItem(Velvet.Blocks.EXTRACTOR, new Item.Settings().group(Velvet.GROUP)));

        public static final Item FRAME = register("frame", new BlockItem(Velvet.Blocks.FRAME, new Item.Settings().group(Velvet.GROUP)));
        public static final Item CATWALK = register("catwalk", new BlockItem(Velvet.Blocks.CATWALK, new Item.Settings().group(Velvet.GROUP)));
        public static final Item REINFORCED_GLASS = register("reinforced_glass", new BlockItem(Velvet.Blocks.REINFORCED_GLASS, new Item.Settings().group(Velvet.GROUP)));

        public static final Item IRON_BAR = register("iron_bar", new Item(new Item.Settings().group(GROUP)));
        public static final Item WRENCH = register("wrench", new WrenchItem(TagRegistry.item(new Identifier("c:iron_ingots")), new Item.Settings().maxDamage(512).group(GROUP)));
        public static final Item PAINT_SCRAPER = register("paint_scraper", new PaintScrapperItem(TagRegistry.item(new Identifier("c:iron_ingots")), new Item.Settings().maxDamage(256).group(GROUP)));
        public static final Item PAINT_ROLLER = register("paint_roller", new Item(new Item.Settings().maxCount(1).group(GROUP)));

        private static final Item.Settings PAINT_ROLLER_SETTINGS = new Item.Settings().maxDamage(32).group(GROUP);
        public static final Item[] PAINT_ROLLERS = {
                register("white_paint_roller", new PaintRollerItem(DyeColor.WHITE, PAINT_ROLLER_SETTINGS)),
                register("orange_paint_roller", new PaintRollerItem(DyeColor.ORANGE, PAINT_ROLLER_SETTINGS)),
                register("magenta_paint_roller", new PaintRollerItem(DyeColor.MAGENTA, PAINT_ROLLER_SETTINGS)),
                register("light_blue_paint_roller", new PaintRollerItem(DyeColor.LIGHT_BLUE, PAINT_ROLLER_SETTINGS)),
                register("yellow_paint_roller", new PaintRollerItem(DyeColor.YELLOW, PAINT_ROLLER_SETTINGS)),
                register("lime_paint_roller", new PaintRollerItem(DyeColor.LIME, PAINT_ROLLER_SETTINGS)),
                register("pink_paint_roller", new PaintRollerItem(DyeColor.PINK, PAINT_ROLLER_SETTINGS)),
                register("gray_paint_roller", new PaintRollerItem(DyeColor.GRAY, PAINT_ROLLER_SETTINGS)),
                register("light_gray_paint_roller", new PaintRollerItem(DyeColor.LIGHT_GRAY, PAINT_ROLLER_SETTINGS)),
                register("cyan_paint_roller", new PaintRollerItem(DyeColor.CYAN, PAINT_ROLLER_SETTINGS)),
                register("purple_paint_roller", new PaintRollerItem(DyeColor.PURPLE, PAINT_ROLLER_SETTINGS)),
                register("blue_paint_roller", new PaintRollerItem(DyeColor.BLUE, PAINT_ROLLER_SETTINGS)),
                register("brown_paint_roller", new PaintRollerItem(DyeColor.BROWN, PAINT_ROLLER_SETTINGS)),
                register("green_paint_roller", new PaintRollerItem(DyeColor.GREEN, PAINT_ROLLER_SETTINGS)),
                register("red_paint_roller", new PaintRollerItem(DyeColor.RED, PAINT_ROLLER_SETTINGS)),
                register("black_paint_roller", new PaintRollerItem(DyeColor.BLACK, PAINT_ROLLER_SETTINGS))
        };
        public static final Item[] COLORED_CONDUITS = {
                register("white_conduit", new BlockItem(Velvet.Blocks.WHITE_CONDUIT, new Item.Settings().group(GROUP))),
                register("orange_conduit", new BlockItem(Velvet.Blocks.ORANGE_CONDUIT, new Item.Settings().group(GROUP))),
                register("magenta_conduit", new BlockItem(Velvet.Blocks.MAGENTA_CONDUIT, new Item.Settings().group(GROUP))),
                register("light_blue_conduit", new BlockItem(Velvet.Blocks.LIGHT_BLUE_CONDUIT, new Item.Settings().group(GROUP))),
                register("yellow_conduit", new BlockItem(Velvet.Blocks.YELLOW_CONDUIT, new Item.Settings().group(GROUP))),
                register("lime_conduit", new BlockItem(Velvet.Blocks.LIME_CONDUIT, new Item.Settings().group(GROUP))),
                register("pink_conduit", new BlockItem(Velvet.Blocks.PINK_CONDUIT, new Item.Settings().group(GROUP))),
                register("gray_conduit", new BlockItem(Velvet.Blocks.GRAY_CONDUIT, new Item.Settings().group(GROUP))),
                register("light_gray_conduit", new BlockItem(Velvet.Blocks.LIGHT_GRAY_CONDUIT, new Item.Settings().group(GROUP))),
                register("cyan_conduit", new BlockItem(Velvet.Blocks.CYAN_CONDUIT, new Item.Settings().group(GROUP))),
                register("purple_conduit", new BlockItem(Velvet.Blocks.PURPLE_CONDUIT, new Item.Settings().group(GROUP))),
                register("blue_conduit", new BlockItem(Velvet.Blocks.BLUE_CONDUIT, new Item.Settings().group(GROUP))),
                register("brown_conduit", new BlockItem(Velvet.Blocks.BROWN_CONDUIT, new Item.Settings().group(GROUP))),
                register("green_conduit", new BlockItem(Velvet.Blocks.GREEN_CONDUIT, new Item.Settings().group(GROUP))),
                register("red_conduit", new BlockItem(Velvet.Blocks.RED_CONDUIT, new Item.Settings().group(GROUP))),
                register("black_conduit", new BlockItem(Velvet.Blocks.BLACK_CONDUIT, new Item.Settings().group(GROUP)))
        };
        public static final Item[] COLORED_EXTRACTORS = {
                register("white_extractor", new BlockItem(Velvet.Blocks.WHITE_EXTRACTOR, new Item.Settings().group(GROUP))),
                register("orange_extractor", new BlockItem(Velvet.Blocks.ORANGE_EXTRACTOR, new Item.Settings().group(GROUP))),
                register("magenta_extractor", new BlockItem(Velvet.Blocks.MAGENTA_EXTRACTOR, new Item.Settings().group(GROUP))),
                register("light_blue_extractor", new BlockItem(Velvet.Blocks.LIGHT_BLUE_EXTRACTOR, new Item.Settings().group(GROUP))),
                register("yellow_extractor", new BlockItem(Velvet.Blocks.YELLOW_EXTRACTOR, new Item.Settings().group(GROUP))),
                register("lime_extractor", new BlockItem(Velvet.Blocks.LIME_EXTRACTOR, new Item.Settings().group(GROUP))),
                register("pink_extractor", new BlockItem(Velvet.Blocks.PINK_EXTRACTOR, new Item.Settings().group(GROUP))),
                register("gray_extractor", new BlockItem(Velvet.Blocks.GRAY_EXTRACTOR, new Item.Settings().group(GROUP))),
                register("light_gray_extractor", new BlockItem(Velvet.Blocks.LIGHT_GRAY_EXTRACTOR, new Item.Settings().group(GROUP))),
                register("cyan_extractor", new BlockItem(Velvet.Blocks.CYAN_EXTRACTOR, new Item.Settings().group(GROUP))),
                register("purple_extractor", new BlockItem(Velvet.Blocks.PURPLE_EXTRACTOR, new Item.Settings().group(GROUP))),
                register("blue_extractor", new BlockItem(Velvet.Blocks.BLUE_EXTRACTOR, new Item.Settings().group(GROUP))),
                register("brown_extractor", new BlockItem(Velvet.Blocks.BROWN_EXTRACTOR, new Item.Settings().group(GROUP))),
                register("green_extractor", new BlockItem(Velvet.Blocks.GREEN_EXTRACTOR, new Item.Settings().group(GROUP))),
                register("red_extractor", new BlockItem(Velvet.Blocks.RED_EXTRACTOR, new Item.Settings().group(GROUP))),
                register("black_extractor", new BlockItem(Velvet.Blocks.BLACK_EXTRACTOR, new Item.Settings().group(GROUP)))
        };

        private static Item register(String name, Item item) {
            return Registry.register(Registry.ITEM, "velvet:" + name, item);
        }
    }

    public static final class BlockEntityTypes {
        public static final BlockEntityType<ConduitBlockEntity> CONDUIT = register("conduit", new BlockEntityType<>(ConduitBlockEntity::new,
                ImmutableSet.of(Blocks.CONDUIT, Blocks.FRAMED_CONDUIT, Blocks.WHITE_CONDUIT, Blocks.ORANGE_CONDUIT, Blocks.MAGENTA_CONDUIT, Blocks.LIGHT_BLUE_CONDUIT,
                        Blocks.YELLOW_CONDUIT, Blocks.LIME_CONDUIT, Blocks.PINK_CONDUIT, Blocks.LIGHT_GRAY_CONDUIT, Blocks.GRAY_CONDUIT, Blocks.CYAN_CONDUIT,
                        Blocks.PURPLE_CONDUIT, Blocks.BLUE_CONDUIT, Blocks.BROWN_CONDUIT, Blocks.GREEN_CONDUIT, Blocks.RED_CONDUIT, Blocks.BLACK_CONDUIT),
                null));
        public static final BlockEntityType<ConduitBlockEntity> EXTRACTOR = register("extractor", new BlockEntityType<>(ExtractorBlockEntity::new,
                ImmutableSet.of(Blocks.EXTRACTOR,
                        Blocks.WHITE_EXTRACTOR, Blocks.ORANGE_EXTRACTOR, Blocks.MAGENTA_EXTRACTOR, Blocks.LIGHT_BLUE_EXTRACTOR,
                        Blocks.YELLOW_EXTRACTOR, Blocks.LIME_EXTRACTOR, Blocks.PINK_EXTRACTOR, Blocks.LIGHT_GRAY_EXTRACTOR, Blocks.GRAY_EXTRACTOR, Blocks.CYAN_EXTRACTOR,
                        Blocks.PURPLE_EXTRACTOR, Blocks.BLUE_EXTRACTOR, Blocks.BROWN_EXTRACTOR, Blocks.GREEN_EXTRACTOR, Blocks.RED_EXTRACTOR, Blocks.BLACK_EXTRACTOR),
                null));

        private static <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType<T> type) {
            return Registry.register(Registry.BLOCK_ENTITY_TYPE, "velvet:" + name, type);
        }
    }

    public static final class RecipeSerializers {
        public static final RecipeSerializer<PaintRollerDyeRecipe> PAINTROLLER_DYE = register("crafting_special_paint_roller_dye", new SpecialRecipeSerializer<>(PaintRollerDyeRecipe::new));

        private static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String name, S serializer) {
            return Registry.register(Registry.RECIPE_SERIALIZER, "velvet:" + name, serializer);
        }
    }

    public static Block getColoredConduit(@Nullable DyeColor color) {
        if (color == null) {
            return Blocks.CONDUIT;
        }
        switch (color) {
        case WHITE:
            return Blocks.WHITE_CONDUIT;
        case ORANGE:
            return Blocks.ORANGE_CONDUIT;
        case MAGENTA:
            return Blocks.MAGENTA_CONDUIT;
        case LIGHT_BLUE:
            return Blocks.LIGHT_BLUE_CONDUIT;
        case YELLOW:
            return Blocks.YELLOW_CONDUIT;
        case LIME:
            return Blocks.LIME_CONDUIT;
        case PINK:
            return Blocks.PINK_CONDUIT;
        case GRAY:
            return Blocks.GRAY_CONDUIT;
        case LIGHT_GRAY:
            return Blocks.LIGHT_GRAY_CONDUIT;
        case CYAN:
            return Blocks.CYAN_CONDUIT;
        case PURPLE:
            return Blocks.PURPLE_CONDUIT;
        case BLUE:
            return Blocks.BLUE_CONDUIT;
        case BROWN:
            return Blocks.BROWN_CONDUIT;
        case GREEN:
            return Blocks.GREEN_CONDUIT;
        case RED:
            return Blocks.RED_CONDUIT;
        case BLACK:
            return Blocks.BLACK_CONDUIT;
        default:
            throw new UnsupportedOperationException();
        }
    }

    public static Block getColoredExtractor(@Nullable DyeColor color) {
        if (color == null) {
            return Blocks.EXTRACTOR;
        }
        switch (color) {
        case WHITE:
            return Blocks.WHITE_EXTRACTOR;
        case ORANGE:
            return Blocks.ORANGE_EXTRACTOR;
        case MAGENTA:
            return Blocks.MAGENTA_EXTRACTOR;
        case LIGHT_BLUE:
            return Blocks.LIGHT_BLUE_EXTRACTOR;
        case YELLOW:
            return Blocks.YELLOW_EXTRACTOR;
        case LIME:
            return Blocks.LIME_EXTRACTOR;
        case PINK:
            return Blocks.PINK_EXTRACTOR;
        case GRAY:
            return Blocks.GRAY_EXTRACTOR;
        case LIGHT_GRAY:
            return Blocks.LIGHT_GRAY_EXTRACTOR;
        case CYAN:
            return Blocks.CYAN_EXTRACTOR;
        case PURPLE:
            return Blocks.PURPLE_EXTRACTOR;
        case BLUE:
            return Blocks.BLUE_EXTRACTOR;
        case BROWN:
            return Blocks.BROWN_EXTRACTOR;
        case GREEN:
            return Blocks.GREEN_EXTRACTOR;
        case RED:
            return Blocks.RED_EXTRACTOR;
        case BLACK:
            return Blocks.BLACK_EXTRACTOR;
        default:
            throw new UnsupportedOperationException();
        }
    }

    public static Item getPaintRoller(@Nullable DyeColor color) {
        return color != null ? Items.PAINT_ROLLERS[color.getId()] : Items.PAINT_ROLLER;
    }
}
