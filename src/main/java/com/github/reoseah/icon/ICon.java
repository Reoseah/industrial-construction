package com.github.reoseah.icon;

import com.github.reoseah.icon.blocks.AxleBlock;
import com.github.reoseah.icon.blocks.CatwalkBlock;
import com.github.reoseah.icon.blocks.CatwalkStairsBlock;
import com.github.reoseah.icon.blocks.ColoredConduitBlock;
import com.github.reoseah.icon.blocks.ColoredExtractorBlock;
import com.github.reoseah.icon.blocks.ConduitBlock;
import com.github.reoseah.icon.blocks.ConduitInScaffoldingBlock;
import com.github.reoseah.icon.blocks.ExtractorBlock;
import com.github.reoseah.icon.blocks.GearboxBlock;
import com.github.reoseah.icon.blocks.RedstoneEngineBlock;
import com.github.reoseah.icon.blocks.ScaffoldingBlock;
import com.github.reoseah.icon.blocks.entities.AxleBlockEntity;
import com.github.reoseah.icon.blocks.entities.ConduitBlockEntity;
import com.github.reoseah.icon.blocks.entities.ExtractorBlockEntity;
import com.github.reoseah.icon.blocks.entities.RedstoneEngineBlockEntity;
import com.github.reoseah.icon.enchantments.ParsimonyEnchantment;
import com.github.reoseah.icon.items.DyeScrapItem;
import com.github.reoseah.icon.items.PaintRollerItem;
import com.github.reoseah.icon.items.PaintScraperItem;
import com.github.reoseah.icon.items.WrenchItem;
import com.github.reoseah.icon.recipes.PaintRollerFillingRecipe;
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
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.tag.Tag;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;

/**
 * A namespace for all Industrial Construction objects.
 *
 * Using a class like a namespace in Java?! Yes.
 */
public final class ICon {
    public static final ItemGroup ITEMGROUP = FabricItemGroupBuilder.create(new Identifier("icon:main")).icon(() -> new ItemStack(ICon.Items.CONDUIT)).appendItems(list -> {
        for (Item item : Registry.ITEM) {
            item.appendStacks(ICon.ITEMGROUP, (DefaultedList<ItemStack>) list);
            if (item == ICon.Items.PAINT_SCRAPER) {
                ItemStack parsimonyBook = new ItemStack(net.minecraft.item.Items.ENCHANTED_BOOK);
                parsimonyBook.addEnchantment(Enchantments.PARSIMONY, 1);
                list.add(parsimonyBook);
            }
        }
    }).build();

    public static final class Blocks {
        private static final FabricBlockSettings INDCON_IRON = FabricBlockSettings.of(Material.METAL, MaterialColor.GRAY).strength(2F, 10F).nonOpaque().sounds(BlockSoundGroup.LANTERN).breakByTool(FabricToolTags.PICKAXES);

        public static final Block CONDUIT = register("conduit", new ConduitBlock(INDCON_IRON));
        public static final Block EXTRACTOR = register("extractor", new ExtractorBlock(INDCON_IRON));

        public static final Block SCAFFOLDING = register("scaffolding", new ScaffoldingBlock(INDCON_IRON));
        public static final Block CONDUIT_IN_SCAFFOLDING = register("conduit_in_scaffolding", new ConduitInScaffoldingBlock(INDCON_IRON));
        public static final Block CATWALK = register("catwalk", new CatwalkBlock(INDCON_IRON));
        public static final Block CATWALK_STAIRS = register("catwalk_stairs", new CatwalkStairsBlock(INDCON_IRON));
        public static final Block REINFORCED_GLASS = register("reinforced_glass", new GlassBlock(FabricBlockSettings.of(Material.GLASS).strength(2F, 10F).nonOpaque().sounds(BlockSoundGroup.GLASS).requiresTool().breakByTool(FabricToolTags.PICKAXES)));

        public static final Block AXLE = register("axle", new AxleBlock(INDCON_IRON));
        public static final Block REDSTONE_ENGINE = register("redstone_engine", new RedstoneEngineBlock(INDCON_IRON));
        public static final Block GEARBOX = register("gearbox", new GearboxBlock(INDCON_IRON));

        public static final Block WHITE_CONDUIT = register("white_conduit", new ColoredConduitBlock(DyeColor.WHITE, INDCON_IRON));
        public static final Block ORANGE_CONDUIT = register("orange_conduit", new ColoredConduitBlock(DyeColor.ORANGE, INDCON_IRON));
        public static final Block MAGENTA_CONDUIT = register("magenta_conduit", new ColoredConduitBlock(DyeColor.MAGENTA, INDCON_IRON));
        public static final Block LIGHT_BLUE_CONDUIT = register("light_blue_conduit", new ColoredConduitBlock(DyeColor.LIGHT_BLUE, INDCON_IRON));
        public static final Block YELLOW_CONDUIT = register("yellow_conduit", new ColoredConduitBlock(DyeColor.YELLOW, INDCON_IRON));
        public static final Block LIME_CONDUIT = register("lime_conduit", new ColoredConduitBlock(DyeColor.LIME, INDCON_IRON));
        public static final Block PINK_CONDUIT = register("pink_conduit", new ColoredConduitBlock(DyeColor.PINK, INDCON_IRON));
        public static final Block GRAY_CONDUIT = register("gray_conduit", new ColoredConduitBlock(DyeColor.GRAY, INDCON_IRON));
        public static final Block LIGHT_GRAY_CONDUIT = register("light_gray_conduit", new ColoredConduitBlock(DyeColor.LIGHT_GRAY, INDCON_IRON));
        public static final Block CYAN_CONDUIT = register("cyan_conduit", new ColoredConduitBlock(DyeColor.CYAN, INDCON_IRON));
        public static final Block PURPLE_CONDUIT = register("purple_conduit", new ColoredConduitBlock(DyeColor.PURPLE, INDCON_IRON));
        public static final Block BLUE_CONDUIT = register("blue_conduit", new ColoredConduitBlock(DyeColor.BLUE, INDCON_IRON));
        public static final Block BROWN_CONDUIT = register("brown_conduit", new ColoredConduitBlock(DyeColor.BROWN, INDCON_IRON));
        public static final Block GREEN_CONDUIT = register("green_conduit", new ColoredConduitBlock(DyeColor.GREEN, INDCON_IRON));
        public static final Block RED_CONDUIT = register("red_conduit", new ColoredConduitBlock(DyeColor.RED, INDCON_IRON));
        public static final Block BLACK_CONDUIT = register("black_conduit", new ColoredConduitBlock(DyeColor.BLACK, INDCON_IRON));

        public static final Block WHITE_EXTRACTOR = register("white_extractor", new ColoredExtractorBlock(DyeColor.WHITE, INDCON_IRON));
        public static final Block ORANGE_EXTRACTOR = register("orange_extractor", new ColoredExtractorBlock(DyeColor.ORANGE, INDCON_IRON));
        public static final Block MAGENTA_EXTRACTOR = register("magenta_extractor", new ColoredExtractorBlock(DyeColor.MAGENTA, INDCON_IRON));
        public static final Block LIGHT_BLUE_EXTRACTOR = register("light_blue_extractor", new ColoredExtractorBlock(DyeColor.LIGHT_BLUE, INDCON_IRON));
        public static final Block YELLOW_EXTRACTOR = register("yellow_extractor", new ColoredExtractorBlock(DyeColor.YELLOW, INDCON_IRON));
        public static final Block LIME_EXTRACTOR = register("lime_extractor", new ColoredExtractorBlock(DyeColor.LIME, INDCON_IRON));
        public static final Block PINK_EXTRACTOR = register("pink_extractor", new ColoredExtractorBlock(DyeColor.PINK, INDCON_IRON));
        public static final Block GRAY_EXTRACTOR = register("gray_extractor", new ColoredExtractorBlock(DyeColor.GRAY, INDCON_IRON));
        public static final Block LIGHT_GRAY_EXTRACTOR = register("light_gray_extractor", new ColoredExtractorBlock(DyeColor.LIGHT_GRAY, INDCON_IRON));
        public static final Block CYAN_EXTRACTOR = register("cyan_extractor", new ColoredExtractorBlock(DyeColor.CYAN, INDCON_IRON));
        public static final Block PURPLE_EXTRACTOR = register("purple_extractor", new ColoredExtractorBlock(DyeColor.PURPLE, INDCON_IRON));
        public static final Block BLUE_EXTRACTOR = register("blue_extractor", new ColoredExtractorBlock(DyeColor.BLUE, INDCON_IRON));
        public static final Block BROWN_EXTRACTOR = register("brown_extractor", new ColoredExtractorBlock(DyeColor.BROWN, INDCON_IRON));
        public static final Block GREEN_EXTRACTOR = register("green_extractor", new ColoredExtractorBlock(DyeColor.GREEN, INDCON_IRON));
        public static final Block RED_EXTRACTOR = register("red_extractor", new ColoredExtractorBlock(DyeColor.RED, INDCON_IRON));
        public static final Block BLACK_EXTRACTOR = register("black_extractor", new ColoredExtractorBlock(DyeColor.BLACK, INDCON_IRON));

        private static Block register(String name, Block block) {
            return Registry.register(Registry.BLOCK, "icon:" + name, block);
        }
    }

    public static final class Items {
        public static final Item CONDUIT = register("conduit", new BlockItem(ICon.Blocks.CONDUIT, new Item.Settings().group(ICon.ITEMGROUP)));
        public static final Item EXTRACTOR = register("extractor", new BlockItem(ICon.Blocks.EXTRACTOR, new Item.Settings().group(ICon.ITEMGROUP)));

        public static final Item SCAFFOLDING = register("scaffolding", new BlockItem(ICon.Blocks.SCAFFOLDING, new Item.Settings().group(ICon.ITEMGROUP)));
        public static final Item CATWALK = register("catwalk", new BlockItem(ICon.Blocks.CATWALK, new Item.Settings().group(ICon.ITEMGROUP)));
        public static final Item REINFORCED_GLASS = register("reinforced_glass", new BlockItem(ICon.Blocks.REINFORCED_GLASS, new Item.Settings().group(ICon.ITEMGROUP)));

        public static final Item AXLE = register("axle", new BlockItem(ICon.Blocks.AXLE, new Item.Settings().group(null)));
        public static final Item REDSTONE_ENGINE = register("redstone_engine", new BlockItem(ICon.Blocks.REDSTONE_ENGINE, new Item.Settings().group(null)));
        public static final Item GEARBOX = register("gearbox", new BlockItem(ICon.Blocks.GEARBOX, new Item.Settings().group(null)));

        private static final Tag<Item> IRON_INGOTS = TagRegistry.item(new Identifier("c:iron_ingots"));
        public static final Item IRON_BAR = register("iron_bar", new Item(new Item.Settings().group(ITEMGROUP)));
        public static final Item WRENCH = register("wrench", new WrenchItem(Items.IRON_INGOTS, new Item.Settings().maxDamage(512).group(ITEMGROUP)));
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

        public static final Item PAINT_SCRAPER = register("paint_scraper", new PaintScraperItem(Items.IRON_INGOTS, new Item.Settings().maxDamage(256).group(ITEMGROUP)));

        public static final Item WHITE_DYE_SCRAP = register("white_dye_scrap", new DyeScrapItem(DyeColor.WHITE, new Item.Settings().group(ICon.ITEMGROUP)));
        public static final Item ORANGE_DYE_SCRAP = register("orange_dye_scrap", new DyeScrapItem(DyeColor.ORANGE, new Item.Settings().group(ICon.ITEMGROUP)));
        public static final Item MAGENTA_DYE_SCRAP = register("magenta_dye_scrap", new DyeScrapItem(DyeColor.MAGENTA, new Item.Settings().group(ICon.ITEMGROUP)));
        public static final Item LIGHT_BLUE_DYE_SCRAP = register("light_blue_dye_scrap", new DyeScrapItem(DyeColor.LIGHT_BLUE, new Item.Settings().group(ICon.ITEMGROUP)));
        public static final Item YELLOW_DYE_SCRAP = register("yellow_dye_scrap", new DyeScrapItem(DyeColor.YELLOW, new Item.Settings().group(ICon.ITEMGROUP)));
        public static final Item LIME_DYE_SCRAP = register("lime_dye_scrap", new DyeScrapItem(DyeColor.LIME, new Item.Settings().group(ICon.ITEMGROUP)));
        public static final Item PINK_DYE_SCRAP = register("pink_dye_scrap", new DyeScrapItem(DyeColor.PINK, new Item.Settings().group(ICon.ITEMGROUP)));
        public static final Item GRAY_DYE_SCRAP = register("gray_dye_scrap", new DyeScrapItem(DyeColor.GRAY, new Item.Settings().group(ICon.ITEMGROUP)));
        public static final Item LIGHT_GRAY_DYE_SCRAP = register("light_gray_dye_scrap", new DyeScrapItem(DyeColor.LIGHT_GRAY, new Item.Settings().group(ICon.ITEMGROUP)));
        public static final Item CYAN_DYE_SCRAP = register("cyan_dye_scrap", new DyeScrapItem(DyeColor.CYAN, new Item.Settings().group(ICon.ITEMGROUP)));
        public static final Item PURPLE_DYE_SCRAP = register("purple_dye_scrap", new DyeScrapItem(DyeColor.PURPLE, new Item.Settings().group(ICon.ITEMGROUP)));
        public static final Item BLUE_DYE_SCRAP = register("blue_dye_scrap", new DyeScrapItem(DyeColor.BLUE, new Item.Settings().group(ICon.ITEMGROUP)));
        public static final Item BROWN_DYE_SCRAP = register("brown_dye_scrap", new DyeScrapItem(DyeColor.BROWN, new Item.Settings().group(ICon.ITEMGROUP)));
        public static final Item GREEN_DYE_SCRAP = register("green_dye_scrap", new DyeScrapItem(DyeColor.GREEN, new Item.Settings().group(ICon.ITEMGROUP)));
        public static final Item RED_DYE_SCRAP = register("red_dye_scrap", new DyeScrapItem(DyeColor.RED, new Item.Settings().group(ICon.ITEMGROUP)));
        public static final Item BLACK_DYE_SCRAP = register("black_dye_scrap", new DyeScrapItem(DyeColor.BLACK, new Item.Settings().group(ICon.ITEMGROUP)));

        public static final Item WHITE_CONDUIT = register("white_conduit", new BlockItem(ICon.Blocks.WHITE_CONDUIT, new Item.Settings().group(ITEMGROUP)));
        public static final Item ORANGE_CONDUIT = register("orange_conduit", new BlockItem(ICon.Blocks.ORANGE_CONDUIT, new Item.Settings().group(ITEMGROUP)));
        public static final Item MAGENTA_CONDUIT = register("magenta_conduit", new BlockItem(ICon.Blocks.MAGENTA_CONDUIT, new Item.Settings().group(ITEMGROUP)));
        public static final Item LIGHT_BLUE_CONDUIT = register("light_blue_conduit", new BlockItem(ICon.Blocks.LIGHT_BLUE_CONDUIT, new Item.Settings().group(ITEMGROUP)));
        public static final Item YELLOW_CONDUIT = register("yellow_conduit", new BlockItem(ICon.Blocks.YELLOW_CONDUIT, new Item.Settings().group(ITEMGROUP)));
        public static final Item LIME_CONDUIT = register("lime_conduit", new BlockItem(ICon.Blocks.LIME_CONDUIT, new Item.Settings().group(ITEMGROUP)));
        public static final Item PINK_CONDUIT = register("pink_conduit", new BlockItem(ICon.Blocks.PINK_CONDUIT, new Item.Settings().group(ITEMGROUP)));
        public static final Item GRAY_CONDUIT = register("gray_conduit", new BlockItem(ICon.Blocks.GRAY_CONDUIT, new Item.Settings().group(ITEMGROUP)));
        public static final Item LIGHT_GRAY_CONDUIT = register("light_gray_conduit", new BlockItem(ICon.Blocks.LIGHT_GRAY_CONDUIT, new Item.Settings().group(ITEMGROUP)));
        public static final Item CYAN_CONDUIT = register("cyan_conduit", new BlockItem(ICon.Blocks.CYAN_CONDUIT, new Item.Settings().group(ITEMGROUP)));
        public static final Item PURPLE_CONDUIT = register("purple_conduit", new BlockItem(ICon.Blocks.PURPLE_CONDUIT, new Item.Settings().group(ITEMGROUP)));
        public static final Item BLUE_CONDUIT = register("blue_conduit", new BlockItem(ICon.Blocks.BLUE_CONDUIT, new Item.Settings().group(ITEMGROUP)));
        public static final Item BROWN_CONDUIT = register("brown_conduit", new BlockItem(ICon.Blocks.BROWN_CONDUIT, new Item.Settings().group(ITEMGROUP)));
        public static final Item GREEN_CONDUIT = register("green_conduit", new BlockItem(ICon.Blocks.GREEN_CONDUIT, new Item.Settings().group(ITEMGROUP)));
        public static final Item RED_CONDUIT = register("red_conduit", new BlockItem(ICon.Blocks.RED_CONDUIT, new Item.Settings().group(ITEMGROUP)));
        public static final Item BLACK_CONDUIT = register("black_conduit", new BlockItem(ICon.Blocks.BLACK_CONDUIT, new Item.Settings().group(ITEMGROUP)));

        public static final Item WHITE_EXTRACTOR = register("white_extractor", new BlockItem(ICon.Blocks.WHITE_EXTRACTOR, new Item.Settings().group(ITEMGROUP)));
        public static final Item ORANGE_EXTRACTOR = register("orange_extractor", new BlockItem(ICon.Blocks.ORANGE_EXTRACTOR, new Item.Settings().group(ITEMGROUP)));
        public static final Item MAGENTA_EXTRACTOR = register("magenta_extractor", new BlockItem(ICon.Blocks.MAGENTA_EXTRACTOR, new Item.Settings().group(ITEMGROUP)));
        public static final Item LIGHT_BLUE_EXTRACTOR = register("light_blue_extractor", new BlockItem(ICon.Blocks.LIGHT_BLUE_EXTRACTOR, new Item.Settings().group(ITEMGROUP)));
        public static final Item YELLOW_EXTRACTOR = register("yellow_extractor", new BlockItem(ICon.Blocks.YELLOW_EXTRACTOR, new Item.Settings().group(ITEMGROUP)));
        public static final Item LIME_EXTRACTOR = register("lime_extractor", new BlockItem(ICon.Blocks.LIME_EXTRACTOR, new Item.Settings().group(ITEMGROUP)));
        public static final Item PINK_EXTRACTOR = register("pink_extractor", new BlockItem(ICon.Blocks.PINK_EXTRACTOR, new Item.Settings().group(ITEMGROUP)));
        public static final Item GRAY_EXTRACTOR = register("gray_extractor", new BlockItem(ICon.Blocks.GRAY_EXTRACTOR, new Item.Settings().group(ITEMGROUP)));
        public static final Item LIGHT_GRAY_EXTRACTOR = register("light_gray_extractor", new BlockItem(ICon.Blocks.LIGHT_GRAY_EXTRACTOR, new Item.Settings().group(ITEMGROUP)));
        public static final Item CYAN_EXTRACTOR = register("cyan_extractor", new BlockItem(ICon.Blocks.CYAN_EXTRACTOR, new Item.Settings().group(ITEMGROUP)));
        public static final Item PURPLE_EXTRACTOR = register("purple_extractor", new BlockItem(ICon.Blocks.PURPLE_EXTRACTOR, new Item.Settings().group(ITEMGROUP)));
        public static final Item BLUE_EXTRACTOR = register("blue_extractor", new BlockItem(ICon.Blocks.BLUE_EXTRACTOR, new Item.Settings().group(ITEMGROUP)));
        public static final Item BROWN_EXTRACTOR = register("brown_extractor", new BlockItem(ICon.Blocks.BROWN_EXTRACTOR, new Item.Settings().group(ITEMGROUP)));
        public static final Item GREEN_EXTRACTOR = register("green_extractor", new BlockItem(ICon.Blocks.GREEN_EXTRACTOR, new Item.Settings().group(ITEMGROUP)));
        public static final Item RED_EXTRACTOR = register("red_extractor", new BlockItem(ICon.Blocks.RED_EXTRACTOR, new Item.Settings().group(ITEMGROUP)));
        public static final Item BLACK_EXTRACTOR = register("black_extractor", new BlockItem(ICon.Blocks.BLACK_EXTRACTOR, new Item.Settings().group(ITEMGROUP)));

        private static Item register(String name, Item item) {
            return Registry.register(Registry.ITEM, "icon:" + name, item);
        }
    }

    public static final class Enchantments {
        public static final Enchantment PARSIMONY = register("parsimony", new ParsimonyEnchantment());

        private static Enchantment register(String name, Enchantment enchantment) {
            return Registry.register(Registry.ENCHANTMENT, "icon:" + name, enchantment);
        }
    }

    public static final class BlockEntityTypes {
        public static final BlockEntityType<ConduitBlockEntity> CONDUIT = register("conduit",
                new BlockEntityType<>(ConduitBlockEntity::new, ImmutableSet.<Block>builder().add(Blocks.CONDUIT, Blocks.CONDUIT_IN_SCAFFOLDING).addAll(ColoredConduitBlock.INSTANCES.values()).build(), null));
        public static final BlockEntityType<ExtractorBlockEntity> EXTRACTOR = register("extractor",
                new BlockEntityType<>(ExtractorBlockEntity::new, ImmutableSet.<Block>builder().add(Blocks.EXTRACTOR).addAll(ColoredExtractorBlock.INSTANCES.values()).build(), null));

        public static final BlockEntityType<AxleBlockEntity> AXLE = register("axle", new BlockEntityType<>(AxleBlockEntity::new, ImmutableSet.of(Blocks.AXLE), null));
        public static final BlockEntityType<RedstoneEngineBlockEntity> REDSTONE_ENGINE = register("redstone_engine", new BlockEntityType<>(RedstoneEngineBlockEntity::new, ImmutableSet.of(Blocks.REDSTONE_ENGINE), null));

        private static <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType<T> type) {
            return Registry.register(Registry.BLOCK_ENTITY_TYPE, "icon:" + name, type);
        }
    }

    public static final class RecipeSerializers {
        public static final RecipeSerializer<PaintRollerFillingRecipe> PAINTROLLER_FILLING = register("crafting_special_paint_roller_filling", new SpecialRecipeSerializer<>(PaintRollerFillingRecipe::new));

        private static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String name, S serializer) {
            return Registry.register(Registry.RECIPE_SERIALIZER, "icon:" + name, serializer);
        }
    }
}