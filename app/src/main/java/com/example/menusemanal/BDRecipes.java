package com.example.menusemanal;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class BDRecipes implements Serializable {
    public static String[] days = {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"};

    private List<List<Recipe>> recipes;

    public BDRecipes(){
        this.recipes = new ArrayList<List<Recipe>>();
    }


    public List<List<Recipe>> getRecipes() {
        return recipes;
    }


    public void addRecipe(Recipe recipe) {
        List<Recipe> dayList = recipes.get(recipe.getDay());
        if(recipe.getType()==Recipe.DINNER ||  dayList.size()==1 ){
            dayList.add(recipe);
        } else {
            int i = 1;
            for (i = 1; i<dayList.size(); i++){
                if(dayList.get(i).getType()>recipe.getType()){
                    recipes.get(recipe.getDay()).add(i, recipe);
                    i=100;
                }
            }
            if(i!=100){
                dayList.add(recipe);
            }
        }
    }


    public static  BDRecipes getFromFile(FileInputStream fis) {
        try {
            ObjectInputStream ois = new ObjectInputStream(fis);
            BDRecipes bd = (BDRecipes) ois.readObject();
            return bd;
        }
        catch (Exception e){
            return new BDRecipes();
        }
    }


    public boolean writeToFile(FileOutputStream fos){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            return true;
        } catch (Exception e){
            return false;
        }
    }


    public static BDRecipes getDummyRecipes(){
        BDRecipes bd = new BDRecipes();
        for (int i=0;i<=6; i++){
            bd.getRecipes().add(new ArrayList<Recipe>());
            bd.getRecipes().get(i).add(new Recipe(days[i], i, -1, "", ""));
            for (int j=0;j<=2; j++){
                bd.getRecipes().get(i).add(new Recipe("Title "+i, i, j, "ingrediente", "descripcion"));
            }
        }
        return bd;
    }


    public static BDRecipes getEmptyRecipes(){
        BDRecipes bd = new BDRecipes();
        for (int i=0;i<=6; i++){
            bd.getRecipes().add(new ArrayList<Recipe>());
            bd.getRecipes().get(i).add(new Recipe(days[i], i, -1, "", ""));
        }
        return bd;
    }


    public String toString() {

        String results = "List Recipes: \n";
        for (int i=0;i<=6; i++){
            for(Recipe d : recipes.get(i)) {
                results += d.toString();
            }
        }
        return results;
    }


}
