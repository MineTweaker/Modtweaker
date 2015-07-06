package modtweaker2.mods.thermalexpansion.commands;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import minetweaker.MineTweakerAPI;
import minetweaker.MineTweakerImplementationAPI;
import minetweaker.api.player.IPlayer;
import minetweaker.api.server.ICommandFunction;
import modtweaker2.helpers.InputHelper;
import modtweaker2.helpers.StringHelper;
import cofh.thermalexpansion.util.crafting.CrucibleManager;
import cofh.thermalexpansion.util.crafting.CrucibleManager.RecipeCrucible;
import cofh.thermalexpansion.util.crafting.FurnaceManager;
import cofh.thermalexpansion.util.crafting.FurnaceManager.RecipeFurnace;
import cofh.thermalexpansion.util.crafting.PulverizerManager;
import cofh.thermalexpansion.util.crafting.PulverizerManager.RecipePulverizer;
import cofh.thermalexpansion.util.crafting.SawmillManager;
import cofh.thermalexpansion.util.crafting.SawmillManager.RecipeSawmill;
import cofh.thermalexpansion.util.crafting.SmelterManager;
import cofh.thermalexpansion.util.crafting.SmelterManager.RecipeSmelter;
import cofh.thermalexpansion.util.crafting.TransposerManager;
import cofh.thermalexpansion.util.crafting.TransposerManager.RecipeTransposer;

public class ThermalExpansionLogger implements ICommandFunction {
    private static final List<String> validArguments = new LinkedList<String>();
    
    static {
        validArguments.add("crucible");
        validArguments.add("furnace");
        validArguments.add("pulverizer");
        validArguments.add("sawmill");
        validArguments.add("smelter");
        validArguments.add("transposer");
    }
    
    @Override
    public void execute(String[] arguments, IPlayer player) {
        List<String> args = StringHelper.toLowerCase(Arrays.asList(arguments));
        
        if(!validArguments.containsAll(args)) {
            if(player != null) {
                player.sendChat(MineTweakerImplementationAPI.platform.getMessage("Invalid arguments for command. Valid arguments: " + StringHelper.join(validArguments, ", ")));
            }
        } else {
            if(args.isEmpty() || args.contains("crucible")) {
                for(RecipeCrucible recipe : CrucibleManager.getRecipeList()) {
                    MineTweakerAPI.logCommand(String.format("mods.thermalexpansion.Crucible.addRecipe(%d, %s, %s);",
                            recipe.getEnergy(),
                            InputHelper.getStackDescription(recipe.getInput()),
                            InputHelper.getStackDescription(recipe.getOutput())));
                }
            }
            
            if(args.isEmpty() || args.contains("furnace")) {
                for(RecipeFurnace recipe : FurnaceManager.getRecipeList()) {
                    MineTweakerAPI.logCommand(String.format("mods.thermalexpansion.Furnace.addRecipe(%d, %s, %s);",
                            recipe.getEnergy(),
                            InputHelper.getStackDescription(recipe.getInput()),
                            InputHelper.getStackDescription(recipe.getOutput())));
                }
            }
            
            if(args.isEmpty() || args.contains("pulverizer")) {
                for(RecipePulverizer recipe : PulverizerManager.getRecipeList()) {
                    if(recipe.getSecondaryOutput() != null) {
                        MineTweakerAPI.logCommand(String.format("mods.thermalexpansion.Pulverizer.addRecipe(%d, %s, %s, %s, %d);",
                                recipe.getEnergy(),
                                InputHelper.getStackDescription(recipe.getInput()),
                                InputHelper.getStackDescription(recipe.getPrimaryOutput()),
                                InputHelper.getStackDescription(recipe.getSecondaryOutput()),
                                recipe.getSecondaryOutputChance()));
                    } else {
                        MineTweakerAPI.logCommand(String.format("mods.thermalexpansion.Pulverizer.addRecipe(%d, %s, %s);",
                                recipe.getEnergy(),
                                InputHelper.getStackDescription(recipe.getInput()),
                                InputHelper.getStackDescription(recipe.getPrimaryOutput())));                        
                    }
                }
            }
            
            if(args.isEmpty() || args.contains("sawmill")) {
                for(RecipeSawmill recipe : SawmillManager.getRecipeList()) {
                    if(recipe.getSecondaryOutput() != null) {
                        MineTweakerAPI.logCommand(String.format("mods.thermalexpansion.Sawmill.addRecipe(%d, %s, %s, %s, %d);",
                                recipe.getEnergy(),
                                InputHelper.getStackDescription(recipe.getInput()),
                                InputHelper.getStackDescription(recipe.getPrimaryOutput()),
                                InputHelper.getStackDescription(recipe.getSecondaryOutput()),
                                recipe.getSecondaryOutputChance()));
                    } else {
                        MineTweakerAPI.logCommand(String.format("mods.thermalexpansion.Sawmill.addRecipe(%d, %s, %s);",
                                recipe.getEnergy(),
                                InputHelper.getStackDescription(recipe.getInput()),
                                InputHelper.getStackDescription(recipe.getPrimaryOutput())));                        
                    }
                }
            }
            
            if(args.isEmpty() || args.contains("smelter")) {
                for(RecipeSmelter recipe : SmelterManager.getRecipeList()) {
                    if(recipe.getSecondaryOutput() != null) {
                        MineTweakerAPI.logCommand(String.format("mods.thermalexpansion.Smelter.addRecipe(%d, %s, %s, %s, %s, %d);",
                                recipe.getEnergy(),
                                InputHelper.getStackDescription(recipe.getPrimaryInput()),
                                InputHelper.getStackDescription(recipe.getSecondaryInput()),
                                InputHelper.getStackDescription(recipe.getPrimaryOutput()),
                                InputHelper.getStackDescription(recipe.getSecondaryOutput()),
                                recipe.getSecondaryOutputChance()));
                    } else {
                        MineTweakerAPI.logCommand(String.format("mods.thermalexpansion.Smelter.addRecipe(%d, %s, %s, %s);",
                                recipe.getEnergy(),
                                InputHelper.getStackDescription(recipe.getPrimaryInput()),
                                InputHelper.getStackDescription(recipe.getSecondaryInput()),
                                InputHelper.getStackDescription(recipe.getPrimaryOutput())));                        
                    }
                }
            }
            
            if(args.isEmpty() || args.contains("transposer")) {
                for(RecipeTransposer recipe : TransposerManager.getFillRecipeList()) {
                    MineTweakerAPI.logCommand(String.format("mods.thermalexpansion.Transposer.addFillRecipe(%d, %s, %s, %s);",
                            recipe.getEnergy(),
                            InputHelper.getStackDescription(recipe.getInput()),
                            InputHelper.getStackDescription(recipe.getOutput()),
                            InputHelper.getStackDescription(recipe.getFluid())));  
                }
                
                for(RecipeTransposer recipe : TransposerManager.getExtractionRecipeList()) {
                    MineTweakerAPI.logCommand(String.format("mods.thermalexpansion.Transposer.addExtractRecipe(%d, %s, %s, %s, %d);",
                            recipe.getEnergy(),
                            InputHelper.getStackDescription(recipe.getInput()),
                            InputHelper.getStackDescription(recipe.getOutput()),
                            InputHelper.getStackDescription(recipe.getFluid()),
                            recipe.getChance()));
                }
            }
            
            if (player != null) {
                player.sendChat(MineTweakerImplementationAPI.platform.getMessage("List generated; see minetweaker.log in your minecraft dir"));
            }
        }
    }
}