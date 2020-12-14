package com.github.reoseah.indconstr;

import net.fabricmc.api.ModInitializer;

public class IndConstrInitializer implements ModInitializer {
    @Override
    public void onInitialize() {
        IndConstr.ITEMGROUP.getClass();

        IndConstr.Blocks.CONDUIT.getClass();
        IndConstr.Items.CONDUIT.getClass();
        IndConstr.BlockEntityTypes.CONDUIT.getClass();
        IndConstr.RecipeSerializers.PAINTROLLER_FILLING.getClass();
    }

}
