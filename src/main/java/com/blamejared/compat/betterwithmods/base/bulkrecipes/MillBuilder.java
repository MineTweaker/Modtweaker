package com.blamejared.compat.betterwithmods.base.bulkrecipes;

import betterwithmods.common.registry.bulk.manager.CraftingManagerBulk;
import betterwithmods.common.registry.bulk.recipes.MillRecipe;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenMethod;

public class MillBuilder extends BulkRecipeBuilder<MillRecipe> {

    private int grindType;

    public MillBuilder(CraftingManagerBulk<MillRecipe> registry, String name) {
        super(registry, name);
    }

    @ZenMethod
    @Override
    public void build() {
        addRecipe(new MillRecipe(inputs, outputs, grindType).setPriority(priority));
    }

    @ZenMethod
    public MillBuilder setPriority(int priority) {
        this.priority = priority;
        return this;
    }

    @ZenMethod
    public MillBuilder setGrindType(int grindType) {
        this.grindType = grindType;
        return this;
    }

    @ZenMethod
    public MillBuilder buildRecipe(IIngredient[] inputs, IItemStack[] outputs) {
        _buildRecipe(inputs,outputs);
        return this;
    }


}
