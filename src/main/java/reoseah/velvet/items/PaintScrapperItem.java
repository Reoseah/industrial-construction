package reoseah.velvet.items;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.tag.Tag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import reoseah.velvet.Velvet;
import reoseah.velvet.blocks.AbstractConduitBlock;
import reoseah.velvet.blocks.ConduitBlock;
import reoseah.velvet.blocks.ExtractorBlock;

public class PaintScrapperItem extends Item {
    protected final Tag<Item> material;

    public PaintScrapperItem(Tag<Item> material, Item.Settings settings) {
        super(settings);
        this.material = material;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        boolean success = false;
        if (block instanceof ConduitBlock && ((ConduitBlock) block).getColor() != null) {
            world.setBlockState(pos, ((AbstractConduitBlock) Velvet.Blocks.CONDUIT).makeConnections(world, pos));
            success = true;
        } else if (block instanceof ExtractorBlock && ((ExtractorBlock) block).getColor() != null) {
            world.setBlockState(pos, ((AbstractConduitBlock) Velvet.Blocks.EXTRACTOR).makeConnections(world, pos).with(ExtractorBlock.DIRECTION, state.get(ExtractorBlock.DIRECTION)));
            success = true;
        } else if (block == Blocks.WHITE_STAINED_GLASS
                || block == Blocks.ORANGE_STAINED_GLASS
                || block == Blocks.MAGENTA_STAINED_GLASS
                || block == Blocks.LIGHT_BLUE_STAINED_GLASS
                || block == Blocks.YELLOW_STAINED_GLASS
                || block == Blocks.LIME_STAINED_GLASS
                || block == Blocks.PINK_STAINED_GLASS
                || block == Blocks.GRAY_STAINED_GLASS
                || block == Blocks.LIGHT_GRAY_STAINED_GLASS
                || block == Blocks.CYAN_STAINED_GLASS
                || block == Blocks.PURPLE_STAINED_GLASS
                || block == Blocks.BLUE_STAINED_GLASS
                || block == Blocks.BROWN_STAINED_GLASS
                || block == Blocks.GREEN_STAINED_GLASS
                || block == Blocks.RED_STAINED_GLASS
                || block == Blocks.BLACK_STAINED_GLASS) {
            world.setBlockState(pos, Blocks.GLASS.getDefaultState());
            success = true;
        }
        if (success) {
            if (context.getPlayer() != null && !context.getPlayer().isCreative()) {
                context.getStack().damage(1, context.getPlayer(), player -> {
                    player.sendToolBreakStatus(context.getHand());
                });
            }
            return ActionResult.SUCCESS;
        }
        return super.useOnBlock(context);
    }

    @Override
    public int getEnchantability() {
        return 1;
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return this.material.contains(ingredient.getItem());
    }
}
