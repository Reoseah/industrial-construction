package com.github.reoseah.iconstruct;

import net.fabricmc.api.ModInitializer;

public class IConstructInitializer implements ModInitializer {
    @Override
    public void onInitialize() {
        IConstruct.ITEMGROUP.getClass();

        IConstruct.CONDUIT.getClass();
        IConstruct.ColoredConduits.WHITE.getClass();
        IConstruct.ColoredExtractors.WHITE.getClass();
        IConstruct.ICBlockEntities.CONDUIT.getClass();
        IConstruct.ICRecipeSerializers.PAINTROLLER_FILLING.getClass();
    }

}
