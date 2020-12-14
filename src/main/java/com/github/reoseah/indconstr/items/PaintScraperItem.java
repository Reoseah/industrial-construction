package com.github.reoseah.indconstr.items;

import com.github.reoseah.indconstr.api.blocks.ColorableBlock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.tag.Tag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PaintScraperItem extends Item {
    protected final Tag<Item> material;

    public PaintScraperItem(Tag<Item> material, Item.Settings settings) {
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
        if (block instanceof ColorableBlock && ((ColorableBlock) block).canColor((DyeColor) null)) {
            world.setBlockState(pos, ((ColorableBlock) block).getColoredState(state, world, pos, (DyeColor) null));
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
            context.getStack().damage(1, context.getPlayer(), player -> {
                player.sendToolBreakStatus(context.getHand());
            });
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
