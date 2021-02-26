package com.github.reoseah.iconstruct;

import java.util.Locale;

import com.github.reoseah.iconstruct.blocks.AxleBlock;
import com.github.reoseah.iconstruct.blocks.CatwalkBlock;
import com.github.reoseah.iconstruct.blocks.CatwalkStairsBlock;
import com.github.reoseah.iconstruct.blocks.ColoredConduitBlock;
import com.github.reoseah.iconstruct.blocks.ColoredExtractorBlock;
import com.github.reoseah.iconstruct.blocks.ConduitBlock;
import com.github.reoseah.iconstruct.blocks.ConduitInScaffoldingBlock;
import com.github.reoseah.iconstruct.blocks.ExtractorBlock;
import com.github.reoseah.iconstruct.blocks.GearboxBlock;
import com.github.reoseah.iconstruct.blocks.RedstoneEngineBlock;
import com.github.reoseah.iconstruct.blocks.ScaffoldingBlock;
import com.github.reoseah.iconstruct.blocks.entities.AxleBlockEntity;
import com.github.reoseah.iconstruct.blocks.entities.ConduitBlockEntity;
import com.github.reoseah.iconstruct.blocks.entities.ExtractorBlockEntity;
import com.github.reoseah.iconstruct.blocks.entities.RedstoneEngineBlockEntity;
import com.github.reoseah.iconstruct.items.DyeScrapItem;
import com.github.reoseah.iconstruct.items.PaintRollerItem;
import com.github.reoseah.iconstruct.items.PaintScraperItem;
import com.github.reoseah.iconstruct.items.WrenchItem;
import com.github.reoseah.iconstruct.recipes.PaintRollerFillingRecipe;
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

/**
 * A namespace for all Industrial Construction objects.
 */
public final class IConstruct {
    public static final ItemGroup ITEMGROUP = FabricItemGroupBuilder.build(new Identifier("iconstruct:main"), () -> new ItemStack(IConstruct.CONDUIT));

    private static final FabricBlockSettings CONSTRUCTION_IRON = FabricBlockSettings.of(Material.METAL, MaterialColor.GRAY).strength(2F, 5F).nonOpaque().sounds(BlockSoundGroup.LANTERN).breakByTool(FabricToolTags.PICKAXES);

    public static final Block CONDUIT = registerWithItem("conduit", new ConduitBlock(CONSTRUCTION_IRON));
    public static final Block EXTRACTOR = registerWithItem("extractor", new ExtractorBlock(CONSTRUCTION_IRON));

    public static final Block SCAFFOLDING = registerWithItem("scaffolding", new ScaffoldingBlock(CONSTRUCTION_IRON));
    public static final Block CONDUIT_IN_SCAFFOLDING = register("conduit_in_scaffolding", new ConduitInScaffoldingBlock(CONSTRUCTION_IRON));
    public static final Block CATWALK = registerWithItem("catwalk", new CatwalkBlock(CONSTRUCTION_IRON));
    public static final Block CATWALK_STAIRS = register("catwalk_stairs", new CatwalkStairsBlock(CONSTRUCTION_IRON));
    public static final Block REINFORCED_GLASS = registerWithItem("reinforced_glass", new GlassBlock(FabricBlockSettings.of(Material.GLASS).strength(2F, 10F).nonOpaque().sounds(BlockSoundGroup.GLASS).requiresTool().breakByTool(FabricToolTags.PICKAXES)));

    public static final Block AXLE = registerWithHiddenItem("axle", new AxleBlock(CONSTRUCTION_IRON));
    public static final Block REDSTONE_ENGINE = registerWithHiddenItem("redstone_engine", new RedstoneEngineBlock(CONSTRUCTION_IRON));
    public static final Block GEARBOX = registerWithHiddenItem("gearbox", new GearboxBlock(CONSTRUCTION_IRON));

    public static final Item IRON_BAR = register("iron_bar", new Item(new Item.Settings().group(ITEMGROUP)));
    public static final Item WRENCH = register("wrench", new WrenchItem(new Item.Settings().maxDamage(512).group(ITEMGROUP)));
    public static final Item PAINT_ROLLER = register("paint_roller", new Item(new Item.Settings().maxCount(16).group(ITEMGROUP)));

    private static final Item.Settings PAINT_ROLLER_SETTINGS = new Item.Settings().maxCount(1).group(ITEMGROUP);
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

    public static final Item PAINT_SCRAPER = register("paint_scraper", new PaintScraperItem(new Item.Settings().maxDamage(256).group(ITEMGROUP)));

    public static final Item WHITE_DYE_SCRAP = register("white_dye_scrap", new DyeScrapItem(DyeColor.WHITE, new Item.Settings().group(IConstruct.ITEMGROUP)));
    public static final Item ORANGE_DYE_SCRAP = register("orange_dye_scrap", new DyeScrapItem(DyeColor.ORANGE, new Item.Settings().group(IConstruct.ITEMGROUP)));
    public static final Item MAGENTA_DYE_SCRAP = register("magenta_dye_scrap", new DyeScrapItem(DyeColor.MAGENTA, new Item.Settings().group(IConstruct.ITEMGROUP)));
    public static final Item LIGHT_BLUE_DYE_SCRAP = register("light_blue_dye_scrap", new DyeScrapItem(DyeColor.LIGHT_BLUE, new Item.Settings().group(IConstruct.ITEMGROUP)));
    public static final Item YELLOW_DYE_SCRAP = register("yellow_dye_scrap", new DyeScrapItem(DyeColor.YELLOW, new Item.Settings().group(IConstruct.ITEMGROUP)));
    public static final Item LIME_DYE_SCRAP = register("lime_dye_scrap", new DyeScrapItem(DyeColor.LIME, new Item.Settings().group(IConstruct.ITEMGROUP)));
    public static final Item PINK_DYE_SCRAP = register("pink_dye_scrap", new DyeScrapItem(DyeColor.PINK, new Item.Settings().group(IConstruct.ITEMGROUP)));
    public static final Item GRAY_DYE_SCRAP = register("gray_dye_scrap", new DyeScrapItem(DyeColor.GRAY, new Item.Settings().group(IConstruct.ITEMGROUP)));
    public static final Item LIGHT_GRAY_DYE_SCRAP = register("light_gray_dye_scrap", new DyeScrapItem(DyeColor.LIGHT_GRAY, new Item.Settings().group(IConstruct.ITEMGROUP)));
    public static final Item CYAN_DYE_SCRAP = register("cyan_dye_scrap", new DyeScrapItem(DyeColor.CYAN, new Item.Settings().group(IConstruct.ITEMGROUP)));
    public static final Item PURPLE_DYE_SCRAP = register("purple_dye_scrap", new DyeScrapItem(DyeColor.PURPLE, new Item.Settings().group(IConstruct.ITEMGROUP)));
    public static final Item BLUE_DYE_SCRAP = register("blue_dye_scrap", new DyeScrapItem(DyeColor.BLUE, new Item.Settings().group(IConstruct.ITEMGROUP)));
    public static final Item BROWN_DYE_SCRAP = register("brown_dye_scrap", new DyeScrapItem(DyeColor.BROWN, new Item.Settings().group(IConstruct.ITEMGROUP)));
    public static final Item GREEN_DYE_SCRAP = register("green_dye_scrap", new DyeScrapItem(DyeColor.GREEN, new Item.Settings().group(IConstruct.ITEMGROUP)));
    public static final Item RED_DYE_SCRAP = register("red_dye_scrap", new DyeScrapItem(DyeColor.RED, new Item.Settings().group(IConstruct.ITEMGROUP)));
    public static final Item BLACK_DYE_SCRAP = register("black_dye_scrap", new DyeScrapItem(DyeColor.BLACK, new Item.Settings().group(IConstruct.ITEMGROUP)));

    public enum ColoredConduits {
        WHITE,
        ORANGE,
        MAGENTA,
        LIGHT_BLUE,
        YELLOW,
        LIME,
        PINK,
        GRAY,
        LIGHT_GRAY,
        CYAN,
        PURPLE,
        BLUE,
        BROWN,
        GREEN,
        RED,
        BLACK;

        public final String name;
        public final Block block;

        private ColoredConduits() {
            this.name = this.name().toLowerCase(Locale.ROOT) + "_conduit";
            this.block = IConstruct.registerWithItem(this.name, new ColoredConduitBlock(DyeColor.byId(this.ordinal()), CONSTRUCTION_IRON));
        }
    }

    public enum ColoredExtractors {
        WHITE,
        ORANGE,
        MAGENTA,
        LIGHT_BLUE,
        YELLOW,
        LIME,
        PINK,
        GRAY,
        LIGHT_GRAY,
        CYAN,
        PURPLE,
        BLUE,
        BROWN,
        GREEN,
        RED,
        BLACK;

        public final String name;
        public final Block block;

        private ColoredExtractors() {
            this.name = this.name().toLowerCase(Locale.ROOT) + "_extractor";
            this.block = IConstruct.registerWithItem(this.name, new ColoredExtractorBlock(DyeColor.byId(this.ordinal()), CONSTRUCTION_IRON));
        }
    }

    private static Block register(String name, Block block) {
        return Registry.register(Registry.BLOCK, "iconstruct:" + name, block);
    }

    private static Block registerWithItem(String name, Block block) {
        Registry.register(Registry.ITEM, "iconstruct:" + name, new BlockItem(block, new Item.Settings().group(ITEMGROUP)));
        return Registry.register(Registry.BLOCK, "iconstruct:" + name, block);
    }

    private static Block registerWithHiddenItem(String name, Block block) {
        Registry.register(Registry.ITEM, "iconstruct:" + name, new BlockItem(block, new Item.Settings().group(null)));
        return Registry.register(Registry.BLOCK, "iconstruct:" + name, block);
    }

    private static Item register(String name, Item item) {
        return Registry.register(Registry.ITEM, "iconstruct:" + name, item);
    }

    public static final class ICBlockEntities {
        public static final BlockEntityType<ConduitBlockEntity> CONDUIT = register("conduit", new BlockEntityType<>(ConduitBlockEntity::new, ImmutableSet.<Block>builder().add(IConstruct.CONDUIT, IConstruct.CONDUIT_IN_SCAFFOLDING).addAll(ColoredConduitBlock.INSTANCES.values()).build(), null));
        public static final BlockEntityType<ExtractorBlockEntity> EXTRACTOR = register("extractor", new BlockEntityType<>(ExtractorBlockEntity::new, ImmutableSet.<Block>builder().add(IConstruct.EXTRACTOR).addAll(ColoredExtractorBlock.INSTANCES.values()).build(), null));

        public static final BlockEntityType<AxleBlockEntity> AXLE = register("axle", new BlockEntityType<>(AxleBlockEntity::new, ImmutableSet.of(IConstruct.AXLE), null));
        public static final BlockEntityType<RedstoneEngineBlockEntity> REDSTONE_ENGINE = register("redstone_engine", new BlockEntityType<>(RedstoneEngineBlockEntity::new, ImmutableSet.of(IConstruct.REDSTONE_ENGINE), null));

        private static <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType<T> type) {
            return Registry.register(Registry.BLOCK_ENTITY_TYPE, "iconstruct:" + name, type);
        }
    }

    public static final class ICRecipeSerializers {
        public static final RecipeSerializer<PaintRollerFillingRecipe> PAINTROLLER_FILLING = register("crafting_special_paint_roller_filling", new SpecialRecipeSerializer<>(PaintRollerFillingRecipe::new));

        private static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String name, S serializer) {
            return Registry.register(Registry.RECIPE_SERIALIZER, "iconstruct:" + name, serializer);
        }
    }
}
