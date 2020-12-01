package reoseah.velvet.items;

import com.zundrel.wrenchable.block.BlockWrenchable;

import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WrenchItem extends Item {
    public WrenchItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState state = world.getBlockState(pos);
        if (state.getBlock() instanceof BlockWrenchable) {
            ((BlockWrenchable) state.getBlock()).onWrenched(world, context.getPlayer(), new BlockHitResult(context.getHitPos(), context.getSide(), pos, context.hitsInsideBlock()));
            if (context.getPlayer() != null && !context.getPlayer().isCreative()) {
                context.getStack().damage(1, context.getPlayer(), player -> {
                    player.sendToolBreakStatus(context.getHand());
                });
            }
            return ActionResult.SUCCESS;
        }
        return super.useOnBlock(context);
    }

}
