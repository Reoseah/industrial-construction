package com.github.reoseah.indconstr;

import com.github.reoseah.indconstr.blocks.CatwalkBlock;
import com.github.reoseah.indconstr.blocks.CatwalkStairsBlock;
import com.github.reoseah.indconstr.blocks.ColoredOpaqueConduitBlock;
import com.github.reoseah.indconstr.blocks.ColoredOpaqueExtractorBlock;
import com.github.reoseah.indconstr.blocks.ColoredTransparentConduitBlock;
import com.github.reoseah.indconstr.blocks.ColoredTransparentExtractorBlock;
import com.github.reoseah.indconstr.blocks.ConduitInScaffoldingBlock;
import com.github.reoseah.indconstr.blocks.OpaqueConduitBlock;
import com.github.reoseah.indconstr.blocks.OpaqueExtractorBlock;
import com.github.reoseah.indconstr.blocks.ScaffoldingBlock;
import com.github.reoseah.indconstr.blocks.TransparentConduitBlock;
import com.github.reoseah.indconstr.blocks.TransparentExtractorBlock;
import com.github.reoseah.indconstr.blocks.entities.ConduitBlockEntity;
import com.github.reoseah.indconstr.blocks.entities.ExtractorBlockEntity;
import com.github.reoseah.indconstr.items.PaintRollerItem;
import com.github.reoseah.indconstr.items.PaintScraperItem;
import com.github.reoseah.indconstr.items.WrenchItem;
import com.github.reoseah.indconstr.recipes.PaintRollerFillingRecipe;
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
import net.minecraft.item.TallBlockItem;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.tag.Tag;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * A namespace for all Industrial Construction objects.
 * 
 * Using a class like a namespace in Java?! Yes.
 */
public final class IndConstr {
    public static final ItemGroup ITEMGROUP = FabricItemGroupBuilder.build(new Identifier("indconstr:main"), () -> new ItemStack(IndConstr.Items.SCAFFOLDING));

    public static final class Blocks {
        private static final FabricBlockSettings INDCONSTR_IRON = FabricBlockSettings.of(Material.METAL, MaterialColor.GRAY).strength(2F, 10F).nonOpaque().sounds(BlockSoundGroup.LANTERN).breakByTool(FabricToolTags.PICKAXES);

        public static final Block CONDUIT = register("conduit", new TransparentConduitBlock(INDCONSTR_IRON));
        public static final Block OPAQUE_CONDUIT = register("opaque_conduit", new OpaqueConduitBlock(INDCONSTR_IRON));
        public static final Block EXTRACTOR = register("extractor", new TransparentExtractorBlock(INDCONSTR_IRON));
        public static final Block OPAQUE_EXTRACTOR = register("opaque_extractor", new OpaqueExtractorBlock(INDCONSTR_IRON));

        public static final Block SCAFFOLDING = register("scaffolding", new ScaffoldingBlock(INDCONSTR_IRON));
        public static final Block CONDUIT_IN_SCAFFOLDING = register("conduit_in_scaffolding", new ConduitInScaffoldingBlock(INDCONSTR_IRON));
        public static final Block CATWALK = register("catwalk", new CatwalkBlock(INDCONSTR_IRON));
        public static final Block CATWALK_STAIRS = register("catwalk_stairs", new CatwalkStairsBlock(INDCONSTR_IRON));
        public static final Block REINFORCED_GLASS = register("reinforced_glass", new GlassBlock(FabricBlockSettings.of(Material.GLASS).strength(2F, 10F).nonOpaque().sounds(BlockSoundGroup.GLASS).requiresTool().breakByTool(FabricToolTags.PICKAXES)));

        public static final Block WHITE_CONDUIT = register("white_conduit", new ColoredTransparentConduitBlock(DyeColor.WHITE, INDCONSTR_IRON));
        public static final Block ORANGE_CONDUIT = register("orange_conduit", new ColoredTransparentConduitBlock(DyeColor.ORANGE, INDCONSTR_IRON));
        public static final Block MAGENTA_CONDUIT = register("magenta_conduit", new ColoredTransparentConduitBlock(DyeColor.MAGENTA, INDCONSTR_IRON));
        public static final Block LIGHT_BLUE_CONDUIT = register("light_blue_conduit", new ColoredTransparentConduitBlock(DyeColor.LIGHT_BLUE, INDCONSTR_IRON));
        public static final Block YELLOW_CONDUIT = register("yellow_conduit", new ColoredTransparentConduitBlock(DyeColor.YELLOW, INDCONSTR_IRON));
        public static final Block LIME_CONDUIT = register("lime_conduit", new ColoredTransparentConduitBlock(DyeColor.LIME, INDCONSTR_IRON));
        public static final Block PINK_CONDUIT = register("pink_conduit", new ColoredTransparentConduitBlock(DyeColor.PINK, INDCONSTR_IRON));
        public static final Block GRAY_CONDUIT = register("gray_conduit", new ColoredTransparentConduitBlock(DyeColor.GRAY, INDCONSTR_IRON));
        public static final Block LIGHT_GRAY_CONDUIT = register("light_gray_conduit", new ColoredTransparentConduitBlock(DyeColor.LIGHT_GRAY, INDCONSTR_IRON));
        public static final Block CYAN_CONDUIT = register("cyan_conduit", new ColoredTransparentConduitBlock(DyeColor.CYAN, INDCONSTR_IRON));
        public static final Block PURPLE_CONDUIT = register("purple_conduit", new ColoredTransparentConduitBlock(DyeColor.PURPLE, INDCONSTR_IRON));
        public static final Block BLUE_CONDUIT = register("blue_conduit", new ColoredTransparentConduitBlock(DyeColor.BLUE, INDCONSTR_IRON));
        public static final Block BROWN_CONDUIT = register("brown_conduit", new ColoredTransparentConduitBlock(DyeColor.BROWN, INDCONSTR_IRON));
        public static final Block GREEN_CONDUIT = register("green_conduit", new ColoredTransparentConduitBlock(DyeColor.GREEN, INDCONSTR_IRON));
        public static final Block RED_CONDUIT = register("red_conduit", new ColoredTransparentConduitBlock(DyeColor.RED, INDCONSTR_IRON));
        public static final Block BLACK_CONDUIT = register("black_conduit", new ColoredTransparentConduitBlock(DyeColor.BLACK, INDCONSTR_IRON));

        public static final Block WHITE_OPAQUE_CONDUIT = register("white_opaque_conduit", new ColoredOpaqueConduitBlock(DyeColor.WHITE, INDCONSTR_IRON));
        public static final Block ORANGE_OPAQUE_CONDUIT = register("orange_opaque_conduit", new ColoredOpaqueConduitBlock(DyeColor.ORANGE, INDCONSTR_IRON));
        public static final Block MAGENTA_OPAQUE_CONDUIT = register("magenta_opaque_conduit", new ColoredOpaqueConduitBlock(DyeColor.MAGENTA, INDCONSTR_IRON));
        public static final Block LIGHT_BLUE_OPAQUE_CONDUIT = register("light_blue_opaque_conduit", new ColoredOpaqueConduitBlock(DyeColor.LIGHT_BLUE, INDCONSTR_IRON));
        public static final Block YELLOW_OPAQUE_CONDUIT = register("yellow_opaque_conduit", new ColoredOpaqueConduitBlock(DyeColor.YELLOW, INDCONSTR_IRON));
        public static final Block LIME_OPAQUE_CONDUIT = register("lime_opaque_conduit", new ColoredOpaqueConduitBlock(DyeColor.LIME, INDCONSTR_IRON));
        public static final Block PINK_OPAQUE_CONDUIT = register("pink_opaque_conduit", new ColoredOpaqueConduitBlock(DyeColor.PINK, INDCONSTR_IRON));
        public static final Block GRAY_OPAQUE_CONDUIT = register("gray_opaque_conduit", new ColoredOpaqueConduitBlock(DyeColor.GRAY, INDCONSTR_IRON));
        public static final Block LIGHT_GRAY_OPAQUE_CONDUIT = register("light_gray_opaque_conduit", new ColoredOpaqueConduitBlock(DyeColor.LIGHT_GRAY, INDCONSTR_IRON));
        public static final Block CYAN_OPAQUE_CONDUIT = register("cyan_opaque_conduit", new ColoredOpaqueConduitBlock(DyeColor.CYAN, INDCONSTR_IRON));
        public static final Block PURPLE_OPAQUE_CONDUIT = register("purple_opaque_conduit", new ColoredOpaqueConduitBlock(DyeColor.PURPLE, INDCONSTR_IRON));
        public static final Block BLUE_OPAQUE_CONDUIT = register("blue_opaque_conduit", new ColoredOpaqueConduitBlock(DyeColor.BLUE, INDCONSTR_IRON));
        public static final Block BROWN_OPAQUE_CONDUIT = register("brown_opaque_conduit", new ColoredOpaqueConduitBlock(DyeColor.BROWN, INDCONSTR_IRON));
        public static final Block GREEN_OPAQUE_CONDUIT = register("green_opaque_conduit", new ColoredOpaqueConduitBlock(DyeColor.GREEN, INDCONSTR_IRON));
        public static final Block RED_OPAQUE_CONDUIT = register("red_opaque_conduit", new ColoredOpaqueConduitBlock(DyeColor.RED, INDCONSTR_IRON));
        public static final Block BLACK_OPAQUE_CONDUIT = register("black_opaque_conduit", new ColoredOpaqueConduitBlock(DyeColor.BLACK, INDCONSTR_IRON));

        public static final Block WHITE_EXTRACTOR = register("white_extractor", new ColoredTransparentExtractorBlock(DyeColor.WHITE, INDCONSTR_IRON));
        public static final Block ORANGE_EXTRACTOR = register("orange_extractor", new ColoredTransparentExtractorBlock(DyeColor.ORANGE, INDCONSTR_IRON));
        public static final Block MAGENTA_EXTRACTOR = register("magenta_extractor", new ColoredTransparentExtractorBlock(DyeColor.MAGENTA, INDCONSTR_IRON));
        public static final Block LIGHT_BLUE_EXTRACTOR = register("light_blue_extractor", new ColoredTransparentExtractorBlock(DyeColor.LIGHT_BLUE, INDCONSTR_IRON));
        public static final Block YELLOW_EXTRACTOR = register("yellow_extractor", new ColoredTransparentExtractorBlock(DyeColor.YELLOW, INDCONSTR_IRON));
        public static final Block LIME_EXTRACTOR = register("lime_extractor", new ColoredTransparentExtractorBlock(DyeColor.LIME, INDCONSTR_IRON));
        public static final Block PINK_EXTRACTOR = register("pink_extractor", new ColoredTransparentExtractorBlock(DyeColor.PINK, INDCONSTR_IRON));
        public static final Block GRAY_EXTRACTOR = register("gray_extractor", new ColoredTransparentExtractorBlock(DyeColor.GRAY, INDCONSTR_IRON));
        public static final Block LIGHT_GRAY_EXTRACTOR = register("light_gray_extractor", new ColoredTransparentExtractorBlock(DyeColor.LIGHT_GRAY, INDCONSTR_IRON));
        public static final Block CYAN_EXTRACTOR = register("cyan_extractor", new ColoredTransparentExtractorBlock(DyeColor.CYAN, INDCONSTR_IRON));
        public static final Block PURPLE_EXTRACTOR = register("purple_extractor", new ColoredTransparentExtractorBlock(DyeColor.PURPLE, INDCONSTR_IRON));
        public static final Block BLUE_EXTRACTOR = register("blue_extractor", new ColoredTransparentExtractorBlock(DyeColor.BLUE, INDCONSTR_IRON));
        public static final Block BROWN_EXTRACTOR = register("brown_extractor", new ColoredTransparentExtractorBlock(DyeColor.BROWN, INDCONSTR_IRON));
        public static final Block GREEN_EXTRACTOR = register("green_extractor", new ColoredTransparentExtractorBlock(DyeColor.GREEN, INDCONSTR_IRON));
        public static final Block RED_EXTRACTOR = register("red_extractor", new ColoredTransparentExtractorBlock(DyeColor.RED, INDCONSTR_IRON));
        public static final Block BLACK_EXTRACTOR = register("black_extractor", new ColoredTransparentExtractorBlock(DyeColor.BLACK, INDCONSTR_IRON));

        public static final Block WHITE_OPAQUE_EXTRACTOR = register("white_opaque_extractor", new ColoredOpaqueExtractorBlock(DyeColor.WHITE, INDCONSTR_IRON));
        public static final Block ORANGE_OPAQUE_EXTRACTOR = register("orange_opaque_extractor", new ColoredOpaqueExtractorBlock(DyeColor.ORANGE, INDCONSTR_IRON));
        public static final Block MAGENTA_OPAQUE_EXTRACTOR = register("magenta_opaque_extractor", new ColoredOpaqueExtractorBlock(DyeColor.MAGENTA, INDCONSTR_IRON));
        public static final Block LIGHT_BLUE_OPAQUE_EXTRACTOR = register("light_blue_opaque_extractor", new ColoredOpaqueExtractorBlock(DyeColor.LIGHT_BLUE, INDCONSTR_IRON));
        public static final Block YELLOW_OPAQUE_EXTRACTOR = register("yellow_opaque_extractor", new ColoredOpaqueExtractorBlock(DyeColor.YELLOW, INDCONSTR_IRON));
        public static final Block LIME_OPAQUE_EXTRACTOR = register("lime_opaque_extractor", new ColoredOpaqueExtractorBlock(DyeColor.LIME, INDCONSTR_IRON));
        public static final Block PINK_OPAQUE_EXTRACTOR = register("pink_opaque_extractor", new ColoredOpaqueExtractorBlock(DyeColor.PINK, INDCONSTR_IRON));
        public static final Block GRAY_OPAQUE_EXTRACTOR = register("gray_opaque_extractor", new ColoredOpaqueExtractorBlock(DyeColor.GRAY, INDCONSTR_IRON));
        public static final Block LIGHT_GRAY_OPAQUE_EXTRACTOR = register("light_gray_opaque_extractor", new ColoredOpaqueExtractorBlock(DyeColor.LIGHT_GRAY, INDCONSTR_IRON));
        public static final Block CYAN_OPAQUE_EXTRACTOR = register("cyan_opaque_extractor", new ColoredOpaqueExtractorBlock(DyeColor.CYAN, INDCONSTR_IRON));
        public static final Block PURPLE_OPAQUE_EXTRACTOR = register("purple_opaque_extractor", new ColoredOpaqueExtractorBlock(DyeColor.PURPLE, INDCONSTR_IRON));
        public static final Block BLUE_OPAQUE_EXTRACTOR = register("blue_opaque_extractor", new ColoredOpaqueExtractorBlock(DyeColor.BLUE, INDCONSTR_IRON));
        public static final Block BROWN_OPAQUE_EXTRACTOR = register("brown_opaque_extractor", new ColoredOpaqueExtractorBlock(DyeColor.BROWN, INDCONSTR_IRON));
        public static final Block GREEN_OPAQUE_EXTRACTOR = register("green_opaque_extractor", new ColoredOpaqueExtractorBlock(DyeColor.GREEN, INDCONSTR_IRON));
        public static final Block RED_OPAQUE_EXTRACTOR = register("red_opaque_extractor", new ColoredOpaqueExtractorBlock(DyeColor.RED, INDCONSTR_IRON));
        public static final Block BLACK_OPAQUE_EXTRACTOR = register("black_opaque_extractor", new ColoredOpaqueExtractorBlock(DyeColor.BLACK, INDCONSTR_IRON));

        private static Block register(String name, Block block) {
            return Registry.register(Registry.BLOCK, "indconstr:" + name, block);
        }
    }

    public static final class Items {
        public static final Item CONDUIT = register("conduit", new BlockItem(IndConstr.Blocks.CONDUIT, new Item.Settings().group(IndConstr.ITEMGROUP)));
        public static final Item OPAQUE_CONDUIT = register("opaque_conduit", new BlockItem(IndConstr.Blocks.OPAQUE_CONDUIT, new Item.Settings().group(IndConstr.ITEMGROUP)));
        public static final Item EXTRACTOR = register("extractor", new BlockItem(IndConstr.Blocks.EXTRACTOR, new Item.Settings().group(IndConstr.ITEMGROUP)));
        public static final Item OPAQUE_EXTRACTOR = register("opaque_extractor", new BlockItem(IndConstr.Blocks.OPAQUE_EXTRACTOR, new Item.Settings().group(IndConstr.ITEMGROUP)));

        public static final Item SCAFFOLDING = register("scaffolding", new BlockItem(IndConstr.Blocks.SCAFFOLDING, new Item.Settings().group(IndConstr.ITEMGROUP)));
        public static final Item CATWALK = register("catwalk", new BlockItem(IndConstr.Blocks.CATWALK, new Item.Settings().group(IndConstr.ITEMGROUP)));
        public static final Item CATWALK_STAIRS = register("catwalk_stairs", new TallBlockItem(IndConstr.Blocks.CATWALK_STAIRS, new Item.Settings().group(IndConstr.ITEMGROUP)));
        public static final Item REINFORCED_GLASS = register("reinforced_glass", new BlockItem(IndConstr.Blocks.REINFORCED_GLASS, new Item.Settings().group(IndConstr.ITEMGROUP)));

        private static final Tag<Item> IRON_INGOTS = TagRegistry.item(new Identifier("c:iron_ingots"));
        public static final Item IRON_BAR = register("iron_bar", new Item(new Item.Settings().group(ITEMGROUP)));
        public static final Item WRENCH = register("wrench", new WrenchItem(Items.IRON_INGOTS, new Item.Settings().maxDamage(512).group(ITEMGROUP)));
        public static final Item PAINT_SCRAPER = register("paint_scraper", new PaintScraperItem(Items.IRON_INGOTS, new Item.Settings().maxDamage(256).group(ITEMGROUP)));
        public static final Item PAINT_ROLLER = register("paint_roller", new Item(new Item.Settings().maxCount(1).group(ITEMGROUP)));

        private static final Item.Settings PAINT_ROLLER_SETTINGS = new Item.Settings().maxDamage(32).group(ITEMGROUP);
        public static final Item WHITE_PAINT_ROLLER = register("white_paint_roller", new PaintRollerItem(DyeColor.WHITE, PAINT_ROLLER_SETTINGS));
        public static final Item ORANGE_PAINT_ROLLER = register("orange_paint_roller", new PaintRollerItem(DyeColor.ORANGE, PAINT_ROLLER_SETTINGS));
        public static final Item MAGENTA_PAINT_ROLLER = register("magenta_paint_roller", new PaintRollerItem(DyeColor.MAGENTA, PAINT_ROLLER_SETTINGS));
        public static final Item LIGHT_BLUE_PAINT_ROLLER = register("light_blue_paint_roller", new PaintRollerItem(DyeColor.LIGHT_BLUE, PAINT_ROLLER_SETTINGS));
        public static final Item YELLOW_PAINT_ROLLER = register("yellow_paint_roller", new PaintRollerItem(DyeColor.YELLOW, PAINT_ROLLER_SETTINGS));
        public static final Item LIME_PAINT_ROLLER = register("lime_paint_roller", new PaintRollerItem(DyeColor.LIME, PAINT_ROLLER_SETTINGS));
        public static final Item PINK_PAINT_ROLLER = register("pink_paint_roller", new PaintRollerItem(DyeColor.PINK, PAINT_ROLLER_SETTINGS));
        public static final Item GRAY_PAINT_ROLLER = register("gray_paint_roller", new PaintRollerItem(DyeColor.GRAY, PAINT_ROLLER_SETTINGS));
        public static final Item LIGHT_GRAY_PAINT_ROLLER = register("light_gray_paint_roller", new PaintRollerItem(DyeColor.LIGHT_GRAY, PAINT_ROLLER_SETTINGS));
        public static final Item CYAN_PAINT_ROLLER = register("cyan_paint_roller", new PaintRollerItem(DyeColor.CYAN, PAINT_ROLLER_SETTINGS));
        public static final Item PURPLE_PAINT_ROLLER = register("purple_paint_roller", new PaintRollerItem(DyeColor.PURPLE, PAINT_ROLLER_SETTINGS));
        public static final Item BLUE_PAINT_ROLLER = register("blue_paint_roller", new PaintRollerItem(DyeColor.BLUE, PAINT_ROLLER_SETTINGS));
        public static final Item BROWN_PAINT_ROLLER = register("brown_paint_roller", new PaintRollerItem(DyeColor.BROWN, PAINT_ROLLER_SETTINGS));
        public static final Item GREEN_PAINT_ROLLER = register("green_paint_roller", new PaintRollerItem(DyeColor.GREEN, PAINT_ROLLER_SETTINGS));
        public static final Item RED_PAINT_ROLLER = register("red_paint_roller", new PaintRollerItem(DyeColor.RED, PAINT_ROLLER_SETTINGS));
        public static final Item BLACK_PAINT_ROLLER = register("black_paint_roller", new PaintRollerItem(DyeColor.BLACK, PAINT_ROLLER_SETTINGS));

        public static final Item WHITE_CONDUIT = register("white_conduit", new BlockItem(IndConstr.Blocks.WHITE_CONDUIT, new Item.Settings().group(ITEMGROUP)));
        public static final Item ORANGE_CONDUIT = register("orange_conduit", new BlockItem(IndConstr.Blocks.ORANGE_CONDUIT, new Item.Settings().group(ITEMGROUP)));
        public static final Item MAGENTA_CONDUIT = register("magenta_conduit", new BlockItem(IndConstr.Blocks.MAGENTA_CONDUIT, new Item.Settings().group(ITEMGROUP)));
        public static final Item LIGHT_BLUE_CONDUIT = register("light_blue_conduit", new BlockItem(IndConstr.Blocks.LIGHT_BLUE_CONDUIT, new Item.Settings().group(ITEMGROUP)));
        public static final Item YELLOW_CONDUIT = register("yellow_conduit", new BlockItem(IndConstr.Blocks.YELLOW_CONDUIT, new Item.Settings().group(ITEMGROUP)));
        public static final Item LIME_CONDUIT = register("lime_conduit", new BlockItem(IndConstr.Blocks.LIME_CONDUIT, new Item.Settings().group(ITEMGROUP)));
        public static final Item PINK_CONDUIT = register("pink_conduit", new BlockItem(IndConstr.Blocks.PINK_CONDUIT, new Item.Settings().group(ITEMGROUP)));
        public static final Item GRAY_CONDUIT = register("gray_conduit", new BlockItem(IndConstr.Blocks.GRAY_CONDUIT, new Item.Settings().group(ITEMGROUP)));
        public static final Item LIGHT_GRAY_CONDUIT = register("light_gray_conduit", new BlockItem(IndConstr.Blocks.LIGHT_GRAY_CONDUIT, new Item.Settings().group(ITEMGROUP)));
        public static final Item CYAN_CONDUIT = register("cyan_conduit", new BlockItem(IndConstr.Blocks.CYAN_CONDUIT, new Item.Settings().group(ITEMGROUP)));
        public static final Item PURPLE_CONDUIT = register("purple_conduit", new BlockItem(IndConstr.Blocks.PURPLE_CONDUIT, new Item.Settings().group(ITEMGROUP)));
        public static final Item BLUE_CONDUIT = register("blue_conduit", new BlockItem(IndConstr.Blocks.BLUE_CONDUIT, new Item.Settings().group(ITEMGROUP)));
        public static final Item BROWN_CONDUIT = register("brown_conduit", new BlockItem(IndConstr.Blocks.BROWN_CONDUIT, new Item.Settings().group(ITEMGROUP)));
        public static final Item GREEN_CONDUIT = register("green_conduit", new BlockItem(IndConstr.Blocks.GREEN_CONDUIT, new Item.Settings().group(ITEMGROUP)));
        public static final Item RED_CONDUIT = register("red_conduit", new BlockItem(IndConstr.Blocks.RED_CONDUIT, new Item.Settings().group(ITEMGROUP)));
        public static final Item BLACK_CONDUIT = register("black_conduit", new BlockItem(IndConstr.Blocks.BLACK_CONDUIT, new Item.Settings().group(ITEMGROUP)));

        public static final Item WHITE_OPAQUE_CONDUIT = register("white_opaque_conduit", new BlockItem(IndConstr.Blocks.WHITE_OPAQUE_CONDUIT, new Item.Settings().group(ITEMGROUP)));
        public static final Item ORANGE_OPAQUE_CONDUIT = register("orange_opaque_conduit", new BlockItem(IndConstr.Blocks.ORANGE_OPAQUE_CONDUIT, new Item.Settings().group(ITEMGROUP)));
        public static final Item MAGENTA_OPAQUE_CONDUIT = register("magenta_opaque_conduit", new BlockItem(IndConstr.Blocks.MAGENTA_OPAQUE_CONDUIT, new Item.Settings().group(ITEMGROUP)));
        public static final Item LIGHT_BLUE_OPAQUE_CONDUIT = register("light_blue_opaque_conduit", new BlockItem(IndConstr.Blocks.LIGHT_BLUE_OPAQUE_CONDUIT, new Item.Settings().group(ITEMGROUP)));
        public static final Item YELLOW_OPAQUE_CONDUIT = register("yellow_opaque_conduit", new BlockItem(IndConstr.Blocks.YELLOW_OPAQUE_CONDUIT, new Item.Settings().group(ITEMGROUP)));
        public static final Item LIME_OPAQUE_CONDUIT = register("lime_opaque_conduit", new BlockItem(IndConstr.Blocks.LIME_OPAQUE_CONDUIT, new Item.Settings().group(ITEMGROUP)));
        public static final Item PINK_OPAQUE_CONDUIT = register("pink_opaque_conduit", new BlockItem(IndConstr.Blocks.PINK_OPAQUE_CONDUIT, new Item.Settings().group(ITEMGROUP)));
        public static final Item GRAY_OPAQUE_CONDUIT = register("gray_opaque_conduit", new BlockItem(IndConstr.Blocks.GRAY_OPAQUE_CONDUIT, new Item.Settings().group(ITEMGROUP)));
        public static final Item LIGHT_GRAY_OPAQUE_CONDUIT = register("light_gray_opaque_conduit", new BlockItem(IndConstr.Blocks.LIGHT_GRAY_OPAQUE_CONDUIT, new Item.Settings().group(ITEMGROUP)));
        public static final Item CYAN_OPAQUE_CONDUIT = register("cyan_opaque_conduit", new BlockItem(IndConstr.Blocks.CYAN_OPAQUE_CONDUIT, new Item.Settings().group(ITEMGROUP)));
        public static final Item PURPLE_OPAQUE_CONDUIT = register("purple_opaque_conduit", new BlockItem(IndConstr.Blocks.PURPLE_OPAQUE_CONDUIT, new Item.Settings().group(ITEMGROUP)));
        public static final Item BLUE_OPAQUE_CONDUIT = register("blue_opaque_conduit", new BlockItem(IndConstr.Blocks.BLUE_OPAQUE_CONDUIT, new Item.Settings().group(ITEMGROUP)));
        public static final Item BROWN_OPAQUE_CONDUIT = register("brown_opaque_conduit", new BlockItem(IndConstr.Blocks.BROWN_OPAQUE_CONDUIT, new Item.Settings().group(ITEMGROUP)));
        public static final Item GREEN_OPAQUE_CONDUIT = register("green_opaque_conduit", new BlockItem(IndConstr.Blocks.GREEN_OPAQUE_CONDUIT, new Item.Settings().group(ITEMGROUP)));
        public static final Item RED_OPAQUE_CONDUIT = register("red_opaque_conduit", new BlockItem(IndConstr.Blocks.RED_OPAQUE_CONDUIT, new Item.Settings().group(ITEMGROUP)));
        public static final Item BLACK_OPAQUE_CONDUIT = register("black_opaque_conduit", new BlockItem(IndConstr.Blocks.BLACK_OPAQUE_CONDUIT, new Item.Settings().group(ITEMGROUP)));

        public static final Item WHITE_EXTRACTOR = register("white_extractor", new BlockItem(IndConstr.Blocks.WHITE_EXTRACTOR, new Item.Settings().group(ITEMGROUP)));
        public static final Item ORANGE_EXTRACTOR = register("orange_extractor", new BlockItem(IndConstr.Blocks.ORANGE_EXTRACTOR, new Item.Settings().group(ITEMGROUP)));
        public static final Item MAGENTA_EXTRACTOR = register("magenta_extractor", new BlockItem(IndConstr.Blocks.MAGENTA_EXTRACTOR, new Item.Settings().group(ITEMGROUP)));
        public static final Item LIGHT_BLUE_EXTRACTOR = register("light_blue_extractor", new BlockItem(IndConstr.Blocks.LIGHT_BLUE_EXTRACTOR, new Item.Settings().group(ITEMGROUP)));
        public static final Item YELLOW_EXTRACTOR = register("yellow_extractor", new BlockItem(IndConstr.Blocks.YELLOW_EXTRACTOR, new Item.Settings().group(ITEMGROUP)));
        public static final Item LIME_EXTRACTOR = register("lime_extractor", new BlockItem(IndConstr.Blocks.LIME_EXTRACTOR, new Item.Settings().group(ITEMGROUP)));
        public static final Item PINK_EXTRACTOR = register("pink_extractor", new BlockItem(IndConstr.Blocks.PINK_EXTRACTOR, new Item.Settings().group(ITEMGROUP)));
        public static final Item GRAY_EXTRACTOR = register("gray_extractor", new BlockItem(IndConstr.Blocks.GRAY_EXTRACTOR, new Item.Settings().group(ITEMGROUP)));
        public static final Item LIGHT_GRAY_EXTRACTOR = register("light_gray_extractor", new BlockItem(IndConstr.Blocks.LIGHT_GRAY_EXTRACTOR, new Item.Settings().group(ITEMGROUP)));
        public static final Item CYAN_EXTRACTOR = register("cyan_extractor", new BlockItem(IndConstr.Blocks.CYAN_EXTRACTOR, new Item.Settings().group(ITEMGROUP)));
        public static final Item PURPLE_EXTRACTOR = register("purple_extractor", new BlockItem(IndConstr.Blocks.PURPLE_EXTRACTOR, new Item.Settings().group(ITEMGROUP)));
        public static final Item BLUE_EXTRACTOR = register("blue_extractor", new BlockItem(IndConstr.Blocks.BLUE_EXTRACTOR, new Item.Settings().group(ITEMGROUP)));
        public static final Item BROWN_EXTRACTOR = register("brown_extractor", new BlockItem(IndConstr.Blocks.BROWN_EXTRACTOR, new Item.Settings().group(ITEMGROUP)));
        public static final Item GREEN_EXTRACTOR = register("green_extractor", new BlockItem(IndConstr.Blocks.GREEN_EXTRACTOR, new Item.Settings().group(ITEMGROUP)));
        public static final Item RED_EXTRACTOR = register("red_extractor", new BlockItem(IndConstr.Blocks.RED_EXTRACTOR, new Item.Settings().group(ITEMGROUP)));
        public static final Item BLACK_EXTRACTOR = register("black_extractor", new BlockItem(IndConstr.Blocks.BLACK_EXTRACTOR, new Item.Settings().group(ITEMGROUP)));

        public static final Item WHITE_OPAQUE_EXTRACTOR = register("white_opaque_extractor", new BlockItem(IndConstr.Blocks.WHITE_OPAQUE_EXTRACTOR, new Item.Settings().group(ITEMGROUP)));
        public static final Item ORANGE_OPAQUE_EXTRACTOR = register("orange_opaque_extractor", new BlockItem(IndConstr.Blocks.ORANGE_OPAQUE_EXTRACTOR, new Item.Settings().group(ITEMGROUP)));
        public static final Item MAGENTA_OPAQUE_EXTRACTOR = register("magenta_opaque_extractor", new BlockItem(IndConstr.Blocks.MAGENTA_OPAQUE_EXTRACTOR, new Item.Settings().group(ITEMGROUP)));
        public static final Item LIGHT_BLUE_OPAQUE_EXTRACTOR = register("light_blue_opaque_extractor", new BlockItem(IndConstr.Blocks.LIGHT_BLUE_OPAQUE_EXTRACTOR, new Item.Settings().group(ITEMGROUP)));
        public static final Item YELLOW_OPAQUE_EXTRACTOR = register("yellow_opaque_extractor", new BlockItem(IndConstr.Blocks.YELLOW_OPAQUE_EXTRACTOR, new Item.Settings().group(ITEMGROUP)));
        public static final Item LIME_OPAQUE_EXTRACTOR = register("lime_opaque_extractor", new BlockItem(IndConstr.Blocks.LIME_OPAQUE_EXTRACTOR, new Item.Settings().group(ITEMGROUP)));
        public static final Item PINK_OPAQUE_EXTRACTOR = register("pink_opaque_extractor", new BlockItem(IndConstr.Blocks.PINK_OPAQUE_EXTRACTOR, new Item.Settings().group(ITEMGROUP)));
        public static final Item GRAY_OPAQUE_EXTRACTOR = register("gray_opaque_extractor", new BlockItem(IndConstr.Blocks.GRAY_OPAQUE_EXTRACTOR, new Item.Settings().group(ITEMGROUP)));
        public static final Item LIGHT_GRAY_OPAQUE_EXTRACTOR = register("light_gray_opaque_extractor", new BlockItem(IndConstr.Blocks.LIGHT_GRAY_OPAQUE_EXTRACTOR, new Item.Settings().group(ITEMGROUP)));
        public static final Item CYAN_OPAQUE_EXTRACTOR = register("cyan_opaque_extractor", new BlockItem(IndConstr.Blocks.CYAN_OPAQUE_EXTRACTOR, new Item.Settings().group(ITEMGROUP)));
        public static final Item PURPLE_OPAQUE_EXTRACTOR = register("purple_opaque_extractor", new BlockItem(IndConstr.Blocks.PURPLE_OPAQUE_EXTRACTOR, new Item.Settings().group(ITEMGROUP)));
        public static final Item BLUE_OPAQUE_EXTRACTOR = register("blue_opaque_extractor", new BlockItem(IndConstr.Blocks.BLUE_OPAQUE_EXTRACTOR, new Item.Settings().group(ITEMGROUP)));
        public static final Item BROWN_OPAQUE_EXTRACTOR = register("brown_opaque_extractor", new BlockItem(IndConstr.Blocks.BROWN_OPAQUE_EXTRACTOR, new Item.Settings().group(ITEMGROUP)));
        public static final Item GREEN_OPAQUE_EXTRACTOR = register("green_opaque_extractor", new BlockItem(IndConstr.Blocks.GREEN_OPAQUE_EXTRACTOR, new Item.Settings().group(ITEMGROUP)));
        public static final Item RED_OPAQUE_EXTRACTOR = register("red_opaque_extractor", new BlockItem(IndConstr.Blocks.RED_OPAQUE_EXTRACTOR, new Item.Settings().group(ITEMGROUP)));
        public static final Item BLACK_OPAQUE_EXTRACTOR = register("black_opaque_extractor", new BlockItem(IndConstr.Blocks.BLACK_OPAQUE_EXTRACTOR, new Item.Settings().group(ITEMGROUP)));

        private static Item register(String name, Item item) {
            return Registry.register(Registry.ITEM, "indconstr:" + name, item);
        }
    }

    public static final class BlockEntityTypes {
        public static final BlockEntityType<ConduitBlockEntity> CONDUIT = register("conduit",
                new BlockEntityType<>(ConduitBlockEntity::createTransparent, ImmutableSet.<Block>builder().add(Blocks.CONDUIT, Blocks.CONDUIT_IN_SCAFFOLDING).addAll(ColoredTransparentConduitBlock.INSTANCES.values()).build(), null));
        public static final BlockEntityType<ExtractorBlockEntity> EXTRACTOR = register("extractor",
                new BlockEntityType<>(ExtractorBlockEntity::createTransparent, ImmutableSet.<Block>builder().add(Blocks.EXTRACTOR).addAll(ColoredTransparentExtractorBlock.INSTANCES.values()).build(), null));
        public static final BlockEntityType<ConduitBlockEntity> OPAQUE_CONDUIT = register("opaque_conduit",
                new BlockEntityType<>(ConduitBlockEntity::createOpaque, ImmutableSet.<Block>builder().add(Blocks.OPAQUE_CONDUIT).addAll(ColoredOpaqueConduitBlock.INSTANCES.values()).build(), null));
        public static final BlockEntityType<ConduitBlockEntity> OPAQUE_EXTRACTOR = register("opaque_extractor",
                new BlockEntityType<>(ExtractorBlockEntity::createOpaque, ImmutableSet.<Block>builder().add(Blocks.OPAQUE_EXTRACTOR).addAll(ColoredOpaqueExtractorBlock.INSTANCES.values()).build(), null));

        private static <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType<T> type) {
            return Registry.register(Registry.BLOCK_ENTITY_TYPE, "indconstr:" + name, type);
        }
    }

    public static final class RecipeSerializers {
        public static final RecipeSerializer<PaintRollerFillingRecipe> PAINTROLLER_FILLING = register("crafting_special_paint_roller_filling", new SpecialRecipeSerializer<>(PaintRollerFillingRecipe::new));

        private static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String name, S serializer) {
            return Registry.register(Registry.RECIPE_SERIALIZER, "indconstr:" + name, serializer);
        }
    }
}
