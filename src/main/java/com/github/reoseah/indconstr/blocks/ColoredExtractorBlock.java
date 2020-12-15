package com.github.reoseah.indconstr.blocks;

import java.util.EnumMap;
import java.util.Map;

import com.github.reoseah.indconstr.IndConstr;
import com.github.reoseah.indconstr.api.blocks.ColorScrapableBlock;
import com.github.reoseah.indconstr.blocks.entities.ExtractorBlockEntity;

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

public class ColoredExtractorBlock extends AbstractExtractorBlock implements ColorScrapableBlock {
    public static final Map<DyeColor, ColoredExtractorBlock> INSTANCES = new EnumMap<>(DyeColor.class);

    protected final DyeColor color;

    public static ColoredExtractorBlock byColor(DyeColor color) {
        return INSTANCES.get(color);
    }

    public ColoredExtractorBlock(DyeColor color, Block.Settings settings) {
        super(settings);
        this.color = color;
        INSTANCES.put(color, this);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public boolean isSideInvisible(BlockState state, BlockState state2, Direction direction) {
        return state.get(getConnectionProperty(direction)) == ConnectionType.NORMAL && state2.getBlock() instanceof SimpleConduitBlock && direction != state.get(DIRECTION)
                || super.isSideInvisible(state, state2, direction);
    }

    @Override
    public DyeColor getColor() {
        return this.color;
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new ExtractorBlockEntity();
    }

    @Override
    public boolean canScrapColor(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }

    @Override
    public DyeColor getScrapColor(BlockState state, BlockView world, BlockPos pos) {
        return this.color;
    }

    @Override
    public int getScrapCount(BlockState state, BlockView world, BlockPos pos) {
        return 1;
    }

    @Override
    public void onScraped(BlockState state, WorldAccess world, BlockPos pos) {
        BlockState uncolored = ((AbstractExtractorBlock) IndConstr.Blocks.EXTRACTOR).getStateForPos(world, pos).with(DIRECTION, state.get(DIRECTION));
        world.setBlockState(pos, uncolored, 3);
    }
}
