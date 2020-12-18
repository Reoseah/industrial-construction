package com.github.reoseah.icon.blocks;

import java.util.EnumMap;
import java.util.Map;

import com.github.reoseah.icon.ICon;
import com.github.reoseah.icon.api.blocks.ColorScrapableBlock;
import com.github.reoseah.icon.api.blocks.ConduitConnectingBlock;
import com.github.reoseah.icon.blocks.entities.ConduitBlockEntity;

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

public class ColoredConduitBlock extends AbstractConduitBlock implements ColorScrapableBlock {
    public static final Map<DyeColor, ColoredConduitBlock> INSTANCES = new EnumMap<>(DyeColor.class);

    protected final DyeColor color;

    public static ColoredConduitBlock byColor(DyeColor color) {
        return INSTANCES.get(color);
    }

    public ColoredConduitBlock(DyeColor color, Block.Settings settings) {
        super(settings);
        this.color = color;
        INSTANCES.put(color, this);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public boolean isSideInvisible(BlockState state, BlockState state2, Direction direction) {
        return state.get(getConnectionProperty(direction)) && state2.getBlock() instanceof ConduitConnectingBlock
                || super.isSideInvisible(state, state2, direction);
    }

    @Override
    public DyeColor getColor() {
        return this.color;
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new ConduitBlockEntity();
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
        BlockState uncolored = ((AbstractConduitBlock) ICon.Blocks.CONDUIT).getStateForPos(world, pos);
        world.setBlockState(pos, uncolored, 3);
    }
}
