package reoseah.velvet;

import com.google.common.collect.ImmutableSet;

import net.fabricmc.api.ModInitializer;
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

public final class Velvet implements ModInitializer {
    public static final ItemGroup GROUP = FabricItemGroupBuilder.build(new Identifier("velvet:main"), () -> new ItemStack(Velvet.Items.FRAME));

    @Override
    public void onInitialize() {
        Velvet.Blocks.CONDUIT.getClass();
        Velvet.Items.CONDUIT.getClass();
        Velvet.BlockEntityTypes.CONDUIT.getClass();
    }

    public static final class Blocks {
        private static final FabricBlockSettings IRON_SETTINGS = FabricBlockSettings.of(Material.METAL, MaterialColor.GRAY).strength(2F, 10F).nonOpaque().sounds(BlockSoundGroup.LANTERN).breakByTool(FabricToolTags.PICKAXES);

        public static final Block CONDUIT = register("conduit", new ConduitBlock(IRON_SETTINGS));
        public static final Block EXTRACTOR = register("extractor", new ExtractorBlock(IRON_SETTINGS));
        public static final Block FRAME = register("frame", new FrameBlock(IRON_SETTINGS));
        public static final Block FRAMED_CONDUIT = register("framed_conduit", new FramedConduitBlock(IRON_SETTINGS));
        public static final Block CATWALK = register("catwalk", new CatwalkBlock(IRON_SETTINGS));
        public static final Block REINFORCED_GLASS = register("reinforced_glass", new GlassBlock(FabricBlockSettings.of(Material.GLASS).strength(2F, 10F).nonOpaque().sounds(BlockSoundGroup.GLASS).breakByTool(FabricToolTags.PICKAXES)));

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
        public static final Item ADJUSTABLE_WRENCH = register("adjustable_wrench", new Item(new Item.Settings().maxCount(1).group(GROUP)));
        public static final Item PAINT_SCRAPER = register("paint_scraper", new PaintScrapperItem(new Item.Settings().maxDamage(256).group(GROUP)));
        public static final Item PAINT_ROLLER = register("paint_roller", new Item(new Item.Settings().maxCount(1).group(GROUP)));

        private static final Item.Settings PAINTROLLER_SETTINGS = new Item.Settings().maxCount(1).recipeRemainder(Velvet.Items.PAINT_ROLLER).group(GROUP);
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
    }

    public static final class BlockEntityTypes {
        public static final BlockEntityType<ConduitBlockEntity> CONDUIT = register("conduit", new BlockEntityType<>(ConduitBlockEntity::new, ImmutableSet.of(Velvet.Blocks.CONDUIT, Velvet.Blocks.FRAMED_CONDUIT), null));
        public static final BlockEntityType<ConduitBlockEntity> EXTRACTOR = register("extractor", new BlockEntityType<>(ExtractorBlockEntity::new, ImmutableSet.of(Velvet.Blocks.EXTRACTOR), null));

        private static <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType<T> type) {
            return Registry.register(Registry.BLOCK_ENTITY_TYPE, "velvet:" + name, type);
        }
    }
}
