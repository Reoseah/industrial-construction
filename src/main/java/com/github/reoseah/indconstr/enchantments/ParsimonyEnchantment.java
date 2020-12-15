package com.github.reoseah.indconstr.enchantments;

import com.github.reoseah.indconstr.IndConstr;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

public class ParsimonyEnchantment extends Enchantment {
    public ParsimonyEnchantment() {
        super(Enchantment.Rarity.COMMON, EnchantmentTarget.BREAKABLE, new EquipmentSlot[] { EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND });
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return stack.getItem() == IndConstr.Items.PAINT_SCRAPER;
    }
}
