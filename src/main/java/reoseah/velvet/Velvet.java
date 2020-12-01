package reoseah.velvet;

import org.jetbrains.annotations.Nullable;

import com.google.common.collect.ImmutableSet;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
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

        public static Block getColoredConduit(@Nullable DyeColor color) {
            if (color == null) {
                return Velvet.Blocks.CONDUIT;
            }
            switch (color) {
            case WHITE:
                return Velvet.Blocks.WHITE_CONDUIT;
            case ORANGE:
                return Velvet.Blocks.ORANGE_CONDUIT;
            case MAGENTA:
                return Velvet.Blocks.MAGENTA_CONDUIT;
            case LIGHT_BLUE:
                return Velvet.Blocks.LIGHT_BLUE_CONDUIT;
            case YELLOW:
                return Velvet.Blocks.YELLOW_CONDUIT;
            case LIME:
                return Velvet.Blocks.LIME_CONDUIT;
            case PINK:
                return Velvet.Blocks.PINK_CONDUIT;
            case GRAY:
                return Velvet.Blocks.GRAY_CONDUIT;
            case LIGHT_GRAY:
                return Velvet.Blocks.LIGHT_GRAY_CONDUIT;
            case CYAN:
                return Velvet.Blocks.CYAN_CONDUIT;
            case PURPLE:
                return Velvet.Blocks.PURPLE_CONDUIT;
            case BLUE:
                return Velvet.Blocks.BLUE_CONDUIT;
            case BROWN:
                return Velvet.Blocks.BROWN_CONDUIT;
            case GREEN:
                return Velvet.Blocks.GREEN_CONDUIT;
            case RED:
                return Velvet.Blocks.RED_CONDUIT;
            case BLACK:
                return Velvet.Blocks.BLACK_CONDUIT;
            default:
                throw new UnsupportedOperationException();
            }
        }
    }

    public static final class Items {
        public static final Item CONDUIT = register("conduit", new BlockItem(Velvet.Blocks.CONDUIT, new Item.Settings().group(Velvet.GROUP)));
        public static final Item EXTRACTOR = register("extractor", new BlockItem(Velvet.Blocks.EXTRACTOR, new Item.Settings().group(Velvet.GROUP)));
        public static final Item INSERTER = register("inserter", new BlockItem(Velvet.Blocks.INSERTER, new Item.Settings().group(null /* WIP */)));

        public static final Item WHITE_CONDUIT = register("white_conduit", new BlockItem(Velvet.Blocks.WHITE_CONDUIT, new Item.Settings().group(ItemGroup.SEARCH)));
        public static final Item ORANGE_CONDUIT = register("orange_conduit", new BlockItem(Velvet.Blocks.ORANGE_CONDUIT, new Item.Settings().group(ItemGroup.SEARCH)));
        public static final Item MAGENTA_CONDUIT = register("magenta_conduit", new BlockItem(Velvet.Blocks.MAGENTA_CONDUIT, new Item.Settings().group(ItemGroup.SEARCH)));
        public static final Item LIGHT_BLUE_CONDUIT = register("light_blue_conduit", new BlockItem(Velvet.Blocks.LIGHT_BLUE_CONDUIT, new Item.Settings().group(ItemGroup.SEARCH)));
        public static final Item YELLOW_CONDUIT = register("yellow_conduit", new BlockItem(Velvet.Blocks.YELLOW_CONDUIT, new Item.Settings().group(ItemGroup.SEARCH)));
        public static final Item LIME_CONDUIT = register("lime_conduit", new BlockItem(Velvet.Blocks.LIME_CONDUIT, new Item.Settings().group(ItemGroup.SEARCH)));
        public static final Item PINK_CONDUIT = register("pink_conduit", new BlockItem(Velvet.Blocks.PINK_CONDUIT, new Item.Settings().group(ItemGroup.SEARCH)));
        public static final Item GRAY_CONDUIT = register("gray_conduit", new BlockItem(Velvet.Blocks.GRAY_CONDUIT, new Item.Settings().group(ItemGroup.SEARCH)));
        public static final Item LIGHT_GRAY_CONDUIT = register("light_gray_conduit", new BlockItem(Velvet.Blocks.LIGHT_GRAY_CONDUIT, new Item.Settings().group(ItemGroup.SEARCH)));
        public static final Item CYAN_CONDUIT = register("cyan_conduit", new BlockItem(Velvet.Blocks.CYAN_CONDUIT, new Item.Settings().group(ItemGroup.SEARCH)));
        public static final Item PURPLE_CONDUIT = register("purple_conduit", new BlockItem(Velvet.Blocks.PURPLE_CONDUIT, new Item.Settings().group(ItemGroup.SEARCH)));
        public static final Item BLUE_CONDUIT = register("blue_conduit", new BlockItem(Velvet.Blocks.BLUE_CONDUIT, new Item.Settings().group(ItemGroup.SEARCH)));
        public static final Item BROWN_CONDUIT = register("brown_conduit", new BlockItem(Velvet.Blocks.BROWN_CONDUIT, new Item.Settings().group(ItemGroup.SEARCH)));
        public static final Item GREEN_CONDUIT = register("green_conduit", new BlockItem(Velvet.Blocks.GREEN_CONDUIT, new Item.Settings().group(ItemGroup.SEARCH)));
        public static final Item RED_CONDUIT = register("red_conduit", new BlockItem(Velvet.Blocks.RED_CONDUIT, new Item.Settings().group(ItemGroup.SEARCH)));
        public static final Item BLACK_CONDUIT = register("black_conduit", new BlockItem(Velvet.Blocks.BLACK_CONDUIT, new Item.Settings().group(ItemGroup.SEARCH)));

        public static final Item FRAME = register("frame", new BlockItem(Velvet.Blocks.FRAME, new Item.Settings().group(Velvet.GROUP)));
        public static final Item CATWALK = register("catwalk", new BlockItem(Velvet.Blocks.CATWALK, new Item.Settings().group(Velvet.GROUP)));
        public static final Item REINFORCED_GLASS = register("reinforced_glass", new BlockItem(Velvet.Blocks.REINFORCED_GLASS, new Item.Settings().group(Velvet.GROUP)));

        public static final Item IRON_BAR = register("iron_bar", new Item(new Item.Settings().group(GROUP)));
        public static final Item WRENCH = register("wrench", new WrenchItem(new Item.Settings().maxDamage(512).group(GROUP)));
        public static final Item PAINT_SCRAPER = register("paint_scraper", new PaintScrapperItem(new Item.Settings().maxDamage(256).group(GROUP)));
        public static final Item PAINT_ROLLER = register("paint_roller", new Item(new Item.Settings().maxCount(1).group(GROUP)));

        private static final Item.Settings PAINTROLLER_SETTINGS = new Item.Settings().maxDamage(32).group(GROUP);
        public static final Item WHITE_PAINT_ROLLER = register("white_paint_roller", new PaintRollerItem(DyeColor.WHITE, PAINTROLLER_SETTINGS));
        public static final Item ORANGE_PAINT_ROLLER = register("orange_paint_roller", new PaintRollerItem(DyeColor.ORANGE, PAINTROLLER_SETTINGS));
        public static final Item MAGENTA_PAINT_ROLLER = register("magenta_paint_roller", new PaintRollerItem(DyeColor.MAGENTA, PAINTROLLER_SETTINGS));
        public static final Item LIGHT_BLUE_PAINT_ROLLER = register("light_blue_paint_roller", new PaintRollerItem(DyeColor.LIGHT_BLUE, PAINTROLLER_SETTINGS));
        public static final Item YELLOW_PAINT_ROLLER = register("yellow_paint_roller", new PaintRollerItem(DyeColor.YELLOW, PAINTROLLER_SETTINGS));
        public static final Item LIME_PAINT_ROLLER = register("lime_paint_roller", new PaintRollerItem(DyeColor.LIME, PAINTROLLER_SETTINGS));
        public static final Item PINK_PAINT_ROLLER = register("pink_paint_roller", new PaintRollerItem(DyeColor.PINK, PAINTROLLER_SETTINGS));
        public static final Item GRAY_PAINT_ROLLER = register("gray_paint_roller", new PaintRollerItem(DyeColor.GRAY, PAINTROLLER_SETTINGS));
        public static final Item LIGHT_GRAY_PAINT_ROLLER = register("light_gray_paint_roller", new PaintRollerItem(DyeColor.LIGHT_GRAY, PAINTROLLER_SETTINGS));
        public static final Item CYAN_PAINT_ROLLER = register("cyan_paint_roller", new PaintRollerItem(DyeColor.CYAN, PAINTROLLER_SETTINGS));
        public static final Item PURPLE_PAINT_ROLLER = register("purple_paint_roller", new PaintRollerItem(DyeColor.PURPLE, PAINTROLLER_SETTINGS));
        public static final Item BLUE_PAINT_ROLLER = register("blue_paint_roller", new PaintRollerItem(DyeColor.BLUE, PAINTROLLER_SETTINGS));
        public static final Item BROWN_PAINT_ROLLER = register("brown_paint_roller", new PaintRollerItem(DyeColor.BROWN, PAINTROLLER_SETTINGS));
        public static final Item GREEN_PAINT_ROLLER = register("green_paint_roller", new PaintRollerItem(DyeColor.GREEN, PAINTROLLER_SETTINGS));
        public static final Item RED_PAINT_ROLLER = register("red_paint_roller", new PaintRollerItem(DyeColor.RED, PAINTROLLER_SETTINGS));
        public static final Item BLACK_PAINT_ROLLER = register("black_paint_roller", new PaintRollerItem(DyeColor.BLACK, PAINTROLLER_SETTINGS));

        private static Item register(String name, Item item) {
            return Registry.register(Registry.ITEM, "velvet:" + name, item);
        }

        public static Item getPaintRoller(@Nullable DyeColor color) {
            if (color == null) {
                return Velvet.Items.PAINT_ROLLER;
            }
            switch (color) {
            case WHITE:
                return Velvet.Items.WHITE_PAINT_ROLLER;
            case ORANGE:
                return Velvet.Items.ORANGE_PAINT_ROLLER;
            case MAGENTA:
                return Velvet.Items.MAGENTA_PAINT_ROLLER;
            case LIGHT_BLUE:
                return Velvet.Items.LIGHT_BLUE_PAINT_ROLLER;
            case YELLOW:
                return Velvet.Items.YELLOW_PAINT_ROLLER;
            case LIME:
                return Velvet.Items.LIME_PAINT_ROLLER;
            case PINK:
                return Velvet.Items.PINK_PAINT_ROLLER;
            case GRAY:
                return Velvet.Items.GRAY_PAINT_ROLLER;
            case LIGHT_GRAY:
                return Velvet.Items.LIGHT_GRAY_PAINT_ROLLER;
            case CYAN:
                return Velvet.Items.CYAN_PAINT_ROLLER;
            case PURPLE:
                return Velvet.Items.PURPLE_PAINT_ROLLER;
            case BLUE:
                return Velvet.Items.BLUE_PAINT_ROLLER;
            case BROWN:
                return Velvet.Items.BROWN_PAINT_ROLLER;
            case GREEN:
                return Velvet.Items.GREEN_PAINT_ROLLER;
            case RED:
                return Velvet.Items.RED_PAINT_ROLLER;
            case BLACK:
                return Velvet.Items.BLACK_PAINT_ROLLER;
            default:
                throw new UnsupportedOperationException();
            }
        }
    }

    public static final class BlockEntityTypes {
        public static final BlockEntityType<ConduitBlockEntity> CONDUIT = register("conduit", new BlockEntityType<>(ConduitBlockEntity::new,
                ImmutableSet.of(Velvet.Blocks.CONDUIT, Velvet.Blocks.FRAMED_CONDUIT, Velvet.Blocks.WHITE_CONDUIT, Velvet.Blocks.ORANGE_CONDUIT, Velvet.Blocks.MAGENTA_CONDUIT, Velvet.Blocks.LIGHT_BLUE_CONDUIT,
                        Velvet.Blocks.YELLOW_CONDUIT, Velvet.Blocks.LIME_CONDUIT, Velvet.Blocks.PINK_CONDUIT, Velvet.Blocks.LIGHT_GRAY_CONDUIT, Velvet.Blocks.GRAY_CONDUIT, Velvet.Blocks.CYAN_CONDUIT,
                        Velvet.Blocks.PURPLE_CONDUIT, Velvet.Blocks.BLUE_CONDUIT, Velvet.Blocks.BROWN_CONDUIT, Velvet.Blocks.GREEN_CONDUIT, Velvet.Blocks.RED_CONDUIT, Velvet.Blocks.BLACK_CONDUIT),
                null));
        public static final BlockEntityType<ConduitBlockEntity> EXTRACTOR = register("extractor", new BlockEntityType<>(ExtractorBlockEntity::new, ImmutableSet.of(Velvet.Blocks.EXTRACTOR), null));

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
}
