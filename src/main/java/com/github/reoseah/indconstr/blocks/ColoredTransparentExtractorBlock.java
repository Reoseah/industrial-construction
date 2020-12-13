package com.github.reoseah.indconstr.blocks;

import java.util.EnumMap;
import java.util.Map;

import org.jetbrains.annotations.Nullable;

import com.github.reoseah.indconstr.IndConstr;
import com.github.reoseah.indconstr.api.blocks.ColorableBlock;
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

public class ColoredTransparentExtractorBlock extends ExtractorBlock implements ColorableBlock {
    public static final Map<DyeColor, Block> INSTANCES = new EnumMap<>(DyeColor.class);

    protected final @Nullable DyeColor color;

    public ColoredTransparentExtractorBlock(DyeColor color, Block.Settings settings) {
        super(settings);
        this.color = color;
        INSTANCES.put(color, this);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public boolean isSideInvisible(BlockState state, BlockState state2, Direction direction) {
        return state.get(getConnectionProperty(direction)) == ConnectionType.NORMAL && state2.getBlock() instanceof AbstractConduitBlock && direction != state.get(DIRECTION)
                || super.isSideInvisible(state, state2, direction);
    }

    @Override
    public @Nullable DyeColor getColor() {
        return this.color;
    }

    @Override
    public boolean canColor(@Nullable DyeColor color) {
        return color == null;
    }

    @Override
    public BlockState getColoredState(BlockState state, BlockView world, BlockPos pos, @Nullable DyeColor color) {
        return ((AbstractConduitBlock) IndConstr.Blocks.EXTRACTOR)
                .getStateForPos(world, pos).with(DIRECTION, state.get(DIRECTION));
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return ExtractorBlockEntity.createTransparent();
    }

}
