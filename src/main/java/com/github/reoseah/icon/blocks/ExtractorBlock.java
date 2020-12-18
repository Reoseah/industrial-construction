package com.github.reoseah.icon.blocks;

import org.jetbrains.annotations.Nullable;

import com.github.reoseah.icon.api.blocks.PaintableBlock;
import com.github.reoseah.icon.blocks.entities.ExtractorBlockEntity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

public class ExtractorBlock extends AbstractExtractorBlock implements PaintableBlock {
    public ExtractorBlock(Block.Settings settings) {
        super(settings);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public boolean isSideInvisible(BlockState state, BlockState state2, Direction direction) {
        return state.get(getConnectionProperty(direction)) == ConnectionType.NORMAL && state2.getBlock() instanceof SimpleConduitBlock && direction != state.get(DIRECTION)
                || super.isSideInvisible(state, state2, direction);
    }

    @Override
    public @Nullable DyeColor getColor() {
        return null;
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new ExtractorBlockEntity();
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
        BlockState painted = ColoredExtractorBlock.byColor(color).getStateForPos(world, pos).with(DIRECTION, state.get(DIRECTION));
        world.setBlockState(pos, painted, 3);
    }

}
