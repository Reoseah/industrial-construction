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
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import reoseah.velvet.blocks.CatwalkBlock;
import reoseah.velvet.blocks.ConduitBlock;
import reoseah.velvet.blocks.ExtractorBlock;
import reoseah.velvet.blocks.FrameBlock;
import reoseah.velvet.blocks.FramedConduitBlock;
import reoseah.velvet.blocks.entities.ConduitBlockEntity;
import reoseah.velvet.blocks.entities.ExtractorBlockEntity;

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
        public static final Item PAINT_SCRAPER = register("paint_scraper", new Item(new Item.Settings().maxCount(1).group(GROUP)));
        public static final Item PAINT_ROLLER = register("paint_roller", new Item(new Item.Settings().maxCount(1).group(GROUP)));

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
