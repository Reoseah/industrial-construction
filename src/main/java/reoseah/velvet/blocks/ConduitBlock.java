package reoseah.velvet.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import reoseah.velvet.Velvet;
import reoseah.velvet.blocks.entities.ConduitBlockEntity;

public class ConduitBlock extends BareConduitBlock implements BlockEntityProvider {
    public ConduitBlock(Block.Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new ConduitBlockEntity();
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        return context.getStack().getItem() == Velvet.Items.FRAME || super.canReplace(state, context);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockView world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();
        BlockState state = world.getBlockState(pos);
        if (state.getBlock() == Velvet.Blocks.FRAME) {
            return Velvet.Blocks.FRAMED_CONDUIT.getPlacementState(ctx);
        }
        return super.getPlacementState(ctx);
    }

}
