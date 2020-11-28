package reoseah.velvet.items;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PaintScrapperItem extends Item {
    public PaintScrapperItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        if (block == Blocks.WHITE_STAINED_GLASS
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
            if (context.getPlayer() != null && !context.getPlayer().isCreative()) {
                context.getStack().damage(1, context.getPlayer(), player -> {
                    player.sendToolBreakStatus(context.getHand());
                });
            }
        }
        return super.useOnBlock(context);
    }
}
