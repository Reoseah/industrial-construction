package com.github.reoseah.iconstruct.blocks;

import org.jetbrains.annotations.Nullable;

import com.github.reoseah.iconstruct.IConstruct;
import com.github.reoseah.iconstruct.api.blocks.ConduitConnectingBlock;
import com.github.reoseah.iconstruct.api.blocks.PaintableBlock;
import com.github.reoseah.iconstruct.blocks.entities.ConduitBlockEntity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

public class ConduitBlock extends AbstractConduitBlock implements PaintableBlock {
    public ConduitBlock(Block.Settings settings) {
        super(settings);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public boolean isSideInvisible(BlockState state, BlockState state2, Direction direction) {
        return state.get(getConnectionProperty(direction)) && state2.getBlock() instanceof ConduitConnectingBlock
                || super.isSideInvisible(state, state2, direction);
    }

    @Override
    public @Nullable DyeColor getColor() {
        return null;
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new ConduitBlockEntity();
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        return super.canReplace(state, context)
                || this == IConstruct.Blocks.CONDUIT
                        && context.getStack().getItem() == IConstruct.Items.SCAFFOLDING
                        && context.getPlayer() != null && !context.getPlayer().isSneaking();
    }

    @Override
    public boolean canPaintBlock(DyeColor color, BlockState state, BlockView world, BlockPos pos) {
        return true;
    }

    @Override
    public int getPaintConsumption(DyeColor color, BlockState state, BlockView world, BlockPos pos) {
        return 1;
    }

    @Override
    public void onPainted(DyeColor color, BlockState state, WorldAccess world, BlockPos pos) {
        BlockState painted = ColoredConduitBlock.byColor(color).getStateForPos(world, pos);
        world.setBlockState(pos, painted, 3);
    }
}
