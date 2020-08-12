package com.example.menusemanal;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BDRecipes bd;
    private ListView listView;
    private RecipeAdapter adapter;
    private FloatingActionButton fabAdd;
    private Recipe seeRecipe;
    public static int REQUEST_CODE_NEW_RECIPE= 0;
    public static int REQUEST_CODE_SEE_RECIPE= 1;
    public static int REQUEST_CODE_DELETE_RECIPE= 2;
    public static int REQUEST_CODE_EDIT_RECIPE= 3;
    private static String FILE_NAME = "bdrecipes.obj";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //--------INICIALIZO LA BBDD----------
        //bd= BDRecipes.getDummyRecipes();
        //bd= BDRecipes.getEmptyRecipes();
        //saveToFile();


        try{
            bd = BDRecipes.getFromFile(this.openFileInput(FILE_NAME));
        }catch (Exception e){
            bd = new BDRecipes();
            bd= BDRecipes.getEmptyRecipes();
            saveToFile();
        }


        listView =  findViewById(R.id.all);
        adapter = new RecipeAdapter(this, bd.getRecipes());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Recipe item = (Recipe) parent.getItemAtPosition(position);
                if(item.getType()<0){
                    System.out.println(item.getTitle());
                }else{
                System.out.println(position);
                seeRecipe(item);
                }
            }
        });


        fabAdd = findViewById(R.id.floatingActionButton);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRecipe();
            }
        });

    }



    private void addRecipe() {
        Intent intent = new Intent(this, EditRecipeActivity.class);
        startActivityForResult(intent, REQUEST_CODE_NEW_RECIPE);
        Log.d("ADDING RECIPE","new recipe");
    }



    private void seeRecipe(Recipe r) {
        seeRecipe = r;
        Intent intent = new Intent(this, DetailRecipeActivity.class);
        intent.putExtra(DetailRecipeActivity.RECIPE_PARAMETER, r);
        startActivityForResult(intent, REQUEST_CODE_SEE_RECIPE);
        Log.d("SEEN RECIPE",r.toString());
    }



     @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
         if(data != null){
             if(resultCode != REQUEST_CODE_NEW_RECIPE){
                 List<Recipe> seeList =  bd.getRecipes().get(seeRecipe.getDay());
                if(resultCode == REQUEST_CODE_DELETE_RECIPE) {
                   seeList.remove(seeRecipe);
                   adapter.updateReceiptsList(bd.getRecipes());
                   listView.setAdapter(adapter);
                   saveToFile();
                   Log.d("DELETED",seeRecipe.toString());

                } else if (resultCode == REQUEST_CODE_EDIT_RECIPE) {
                   Recipe recipe = (Recipe) data.getSerializableExtra(DetailRecipeActivity.RECIPE_PARAMETER_OUT);
                   seeList.remove(seeRecipe);
                   bd.addRecipe(recipe);
                   adapter.updateReceiptsList(bd.getRecipes());
                   listView.setAdapter(adapter);
                   saveToFile();
                   Log.d("EDITED", recipe.toString());
                }
             } else {
               Recipe recipe = (Recipe) data.getSerializableExtra(EditRecipeActivity.RECIPE_PARAMETER_OUT);
               bd.addRecipe(recipe);
               adapter.updateReceiptsList(bd.getRecipes());
               listView.setAdapter(adapter);
               saveToFile();
               Log.d("SAVED NEW", recipe.toString());
             }
         }
        super.onActivityResult(requestCode, resultCode, data);
    }



    private void saveToFile(){
        try{
            bd.writeToFile(this.openFileOutput(FILE_NAME, MODE_PRIVATE));
        } catch (Exception e){}
    }





}
