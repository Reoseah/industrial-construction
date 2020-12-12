package com.github.reoseah.indconstr.recipes;

import java.util.List;

import com.github.reoseah.indconstr.IndConstr;
import com.github.reoseah.indconstr.items.PaintRollerItem;
import com.google.common.collect.Lists;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class PaintRollerFillingRecipe extends SpecialCraftingRecipe {
    public PaintRollerFillingRecipe(Identifier id) {
        super(id);
    }

    @Override
    public boolean matches(CraftingInventory inventory, World world) {
        ItemStack paintroller = ItemStack.EMPTY;

        DyeColor color = null;
        List<ItemStack> dyes = Lists.newArrayList();
        for (int i = 0; i < inventory.size(); ++i) {
            ItemStack stack = inventory.getStack(i);
            if (stack.isEmpty()) {
                continue;
            }
            if (stack.getItem() == IndConstr.Items.PAINT_ROLLER || stack.getItem() instanceof PaintRollerItem) {
                if (paintroller.isEmpty()) {
                    paintroller = stack;
                } else {
                    return false;
                }
            } else if (stack.getItem() instanceof DyeItem) {
                DyeColor color2 = ((DyeItem) stack.getItem()).getColor();
                if (color == null) {
                    color = color2;
                } else if (color != color2) {
                    return false;
                }
                dyes.add(stack);
            } else {
                return false;
            }
        }
        Item item = paintroller.getItem();
        if (item instanceof PaintRollerItem) {
            DyeColor paintrollerColor = ((PaintRollerItem) item).getColor();
            if (paintrollerColor != color) {
                return false;
            }
            int uses = paintroller.getMaxDamage() - paintroller.getDamage();
            int maxAdded = 4 - (int) Math.ceil(uses / 8F);
            if (dyes.size() > maxAdded) {
                return false;
            }
        }

        return !paintroller.isEmpty() && !dyes.isEmpty() && dyes.size() <= 4;
    }

    @Override
    public ItemStack craft(CraftingInventory inventory) {
        ItemStack paintroller = ItemStack.EMPTY;
        DyeColor color = null;
        List<ItemStack> dyes = Lists.newArrayList();
        for (int i = 0; i < inventory.size(); ++i) {
            ItemStack stack = inventory.getStack(i);
            if (stack.isEmpty()) {
                continue;
            }
            if (stack.getItem() == IndConstr.Items.PAINT_ROLLER || stack.getItem() instanceof PaintRollerItem) {
                if (paintroller.isEmpty()) {
                    paintroller = stack;
                } else {
                    return ItemStack.EMPTY;
                }
            } else if (stack.getItem() instanceof DyeItem) {
                DyeColor color2 = ((DyeItem) stack.getItem()).getColor();
                if (color == null) {
                    color = color2;
                } else if (color != color2) {
                    return ItemStack.EMPTY;
                }
                dyes.add(stack);
            } else {
                return ItemStack.EMPTY;
            }
        }
        Item item = paintroller.getItem();
        if (item instanceof PaintRollerItem) {
            DyeColor paintrollerColor = ((PaintRollerItem) item).getColor();
            if (paintrollerColor != color) {
                return ItemStack.EMPTY;
            }
            int uses = paintroller.getMaxDamage() - paintroller.getDamage();
            int maxAdded = 4 - (int) Math.ceil(uses / 8F);
            if (dyes.size() > maxAdded) {
                return ItemStack.EMPTY;
            }
        }
        if (!paintroller.isEmpty() && !dyes.isEmpty() && dyes.size() <= 4) {
            ItemStack paintroller2 = new ItemStack(PaintRollerItem.INSTANCES.get(color));
            int left = dyes.size() * 8;
            if (item instanceof PaintRollerItem) {
                paintroller2.setDamage(paintroller2.getMaxDamage() - left - paintroller.getMaxDamage() + paintroller.getDamage());
            } else {
                paintroller2.setDamage(paintroller2.getMaxDamage() - left);
            }
            return paintroller2;
        }
        return ItemStack.EMPTY;

    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return IndConstr.RecipeSerializers.PAINTROLLER_FILLING;
    }

}
