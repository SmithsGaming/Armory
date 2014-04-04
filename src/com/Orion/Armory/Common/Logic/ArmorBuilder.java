package com.Orion.Armory.Common.Logic;
/*
*   ArmorBuilder
*   Created by: Orion
*   Created on: 4-4-2014
*/

import java.util.ArrayList;
import java.util.List;

public class ArmorBuilder
{
    public ArmorBuilder instance;
    public ArmorRecipes recipes = new ArmorRecipes();


    public void init()
    {
       instance = new ArmorBuilder();
       recipes.initializeRecipes();
    }



    public class ArmorRecipes
    {
        protected List<ArmorRecipe> recipeList = new ArrayList<ArmorRecipe>();

        public void addRecipeToList(ArmorRecipe recipe)
        {
            recipeList.add(recipe);
        }

        public ArmorRecipe getValidRecipe(ArmorRecipe recipeRequest)
        {
            if (recipeList.contains(recipeRequest))
            {
                return recipeList.get(recipeList.indexOf(recipeRequest));
            }

            return null;
        }

        protected void initializeRecipes()
        {

        }

    }
}
