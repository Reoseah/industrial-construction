package reoseah.velvet;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import reoseah.velvet.blocks.ConduitBlock;

public final class Velvet implements ModInitializer {
    public static final ItemGroup GROUP = FabricItemGroupBuilder.build(new Identifier("velvet:main"), () -> new ItemStack(Velvet.Items.CONDUIT));

    @Override
    public void onInitialize() {
        Velvet.Blocks.CONDUIT.getClass();
        Velvet.Items.CONDUIT.getClass();
    }

    public static final class Blocks {
        private static final FabricBlockSettings CONDUITS = FabricBlockSettings.of(Material.METAL).nonOpaque().sounds(BlockSoundGroup.LANTERN).breakByHand(true).breakByTool(FabricToolTags.AXES);

        public static final Block CONDUIT = register("conduit", new ConduitBlock(CONDUITS));

        private static Block register(String name, Block item) {
            return Registry.register(Registry.BLOCK, "velvet:" + name, item);
        }
    }

    public static final class Items {
        public static final Item CONDUIT = register("conduit", new BlockItem(Velvet.Blocks.CONDUIT, new Item.Settings().group(Velvet.GROUP)));

        private static Item register(String name, Item item) {
            return Registry.register(Registry.ITEM, "velvet:" + name, item);
        }
    }
}
