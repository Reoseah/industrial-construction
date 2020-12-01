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
import reoseah.velvet.blocks.InsertingConduitBlock;
import reoseah.velvet.blocks.NewExtractorBlock;
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
        public static final Block EXTRACTOR = register("extractor", new ExtractorBlock(IRON_SETTINGS));
        public static final Block NEW_EXTRACTOR = register("new_extractor", new NewExtractorBlock(IRON_SETTINGS));
        public static final Block INSERTER = register("inserter", new InsertingConduitBlock(IRON_SETTINGS));

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

        public static final Block FRAME = register("frame", new FrameBlock(IRON_SETTINGS));
        public static final Block FRAMED_CONDUIT = register("framed_conduit", new FramedConduitBlock(IRON_SETTINGS));
        public static final Block CATWALK = register("catwalk", new CatwalkBlock(IRON_SETTINGS));
        public static final Block REINFORCED_GLASS = register("reinforced_glass", new GlassBlock(FabricBlockSettings.of(Material.GLASS).strength(2F, 10F).nonOpaque().sounds(BlockSoundGroup.GLASS).requiresTool().breakByTool(FabricToolTags.PICKAXES)));

        private static Block register(String name, Block block) {
            return Registry.register(Registry.BLOCK, "velvet:" + name, block);
        }
    }

    public static final class Items {
        public static final Item CONDUIT = register("conduit", new BlockItem(Velvet.Blocks.CONDUIT, new Item.Settings().group(Velvet.GROUP)));
        public static final Item EXTRACTOR = register("extractor", new BlockItem(Velvet.Blocks.EXTRACTOR, new Item.Settings().group(Velvet.GROUP)));
        public static final Item NEW_EXTRACTOR = register("new_extractor", new BlockItem(Velvet.Blocks.NEW_EXTRACTOR, new Item.Settings().group(Velvet.GROUP)));
        public static final Item INSERTER = register("inserter", new BlockItem(Velvet.Blocks.INSERTER, new Item.Settings().group(null /* WIP */)));
        public static final Item[] COLORED_CONDUITS = {
                register("white_conduit", new BlockItem(Velvet.Blocks.WHITE_CONDUIT, new Item.Settings().group(ItemGroup.SEARCH))),
                register("orange_conduit", new BlockItem(Velvet.Blocks.ORANGE_CONDUIT, new Item.Settings().group(ItemGroup.SEARCH))),
                register("magenta_conduit", new BlockItem(Velvet.Blocks.MAGENTA_CONDUIT, new Item.Settings().group(ItemGroup.SEARCH))),
                register("light_blue_conduit", new BlockItem(Velvet.Blocks.LIGHT_BLUE_CONDUIT, new Item.Settings().group(ItemGroup.SEARCH))),
                register("yellow_conduit", new BlockItem(Velvet.Blocks.YELLOW_CONDUIT, new Item.Settings().group(ItemGroup.SEARCH))),
                register("lime_conduit", new BlockItem(Velvet.Blocks.LIME_CONDUIT, new Item.Settings().group(ItemGroup.SEARCH))),
                register("pink_conduit", new BlockItem(Velvet.Blocks.PINK_CONDUIT, new Item.Settings().group(ItemGroup.SEARCH))),
                register("gray_conduit", new BlockItem(Velvet.Blocks.GRAY_CONDUIT, new Item.Settings().group(ItemGroup.SEARCH))),
                register("light_gray_conduit", new BlockItem(Velvet.Blocks.LIGHT_GRAY_CONDUIT, new Item.Settings().group(ItemGroup.SEARCH))),
                register("cyan_conduit", new BlockItem(Velvet.Blocks.CYAN_CONDUIT, new Item.Settings().group(ItemGroup.SEARCH))),
                register("purple_conduit", new BlockItem(Velvet.Blocks.PURPLE_CONDUIT, new Item.Settings().group(ItemGroup.SEARCH))),
                register("blue_conduit", new BlockItem(Velvet.Blocks.BLUE_CONDUIT, new Item.Settings().group(ItemGroup.SEARCH))),
                register("brown_conduit", new BlockItem(Velvet.Blocks.BROWN_CONDUIT, new Item.Settings().group(ItemGroup.SEARCH))),
                register("green_conduit", new BlockItem(Velvet.Blocks.GREEN_CONDUIT, new Item.Settings().group(ItemGroup.SEARCH))),
                register("red_conduit", new BlockItem(Velvet.Blocks.RED_CONDUIT, new Item.Settings().group(ItemGroup.SEARCH))),
                register("black_conduit", new BlockItem(Velvet.Blocks.BLACK_CONDUIT, new Item.Settings().group(ItemGroup.SEARCH)))
        };

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
        public static final BlockEntityType<ConduitBlockEntity> EXTRACTOR = register("extractor", new BlockEntityType<>(ExtractorBlockEntity::new, ImmutableSet.of(Blocks.EXTRACTOR), null));

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

    public static Item getPaintRoller(@Nullable DyeColor color) {
        return color != null ? Items.PAINT_ROLLERS[color.getId()] : Items.PAINT_ROLLER;
    }
}
