package com.example.menusemanal;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class DetailRecipeActivity extends AppCompatActivity {
    public static String  RECIPE_PARAMETER = "RECIPE_PARAMETER";
    public static String  RECIPE_PARAMETER_OUT = "RECIPE_PARAMETER_OUT";
    public static int REQUEST_CODE_EDIT_RECIPE= 3;
    private TextView textTitle, textType, textDay, textIngredients, textDetails;
    private ImageButton buttonClose, buttonEdit, buttonDelete;
    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_recipe);
        Bundle bundle = getIntent().getExtras();
        textTitle = findViewById(R.id.textTitle);
        textType = findViewById(R.id.textType);
        textDay = findViewById(R.id.textDay);
        textIngredients = findViewById(R.id.textIngredients);
        textDetails = findViewById(R.id.textDetails);
        buttonClose = findViewById(R.id.buttonClose);
        buttonEdit = findViewById(R.id.buttonEdit);
        buttonDelete = findViewById(R.id.buttonDelete);
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });

        if(bundle != null){
           this.recipe = (Recipe) bundle.getSerializable(RECIPE_PARAMETER);
            if(recipe!=null) {
                textTitle.setText(recipe.getTitle());
                textDay.setText(recipe.getDayString());
                textType.setText(recipe.getTypeString());
                switch (recipe.getType()){
                    case 0:
                        textType.setTextColor(Color.parseColor("#DAC100"));
                        textDay.setTextColor(Color.parseColor("#DAC100"));
                        break;
                    case 1:
                        textType.setTextColor(Color.parseColor("#00D5C1"));
                        textDay.setTextColor(Color.parseColor("#00D5C1"));
                        break;
                    case 2:
                        textType.setTextColor(Color.parseColor("#FF5959"));
                        textDay.setTextColor(Color.parseColor("#FF5959"));
                        break;
                }
                textIngredients.setText(recipe.getIngredients());
                textDetails.setText(recipe.getDescription());
                buttonDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteConfirmedRecipe();
                    }
                });
                buttonEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editRecipe();
                    }
                });
            }
        }
    }

    private void close(){
        this.finish();
    }

    private void deleteConfirmedRecipe() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.titleAlertDialogDeleteRecipe);
        builder.setMessage(R.string.messageAlertDialogueDeleteRecipe);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteRecipe();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.create().show();
    }

    public void deleteRecipe() {
        Intent intent = new Intent();
        intent.putExtra(RECIPE_PARAMETER_OUT, recipe);
        this.setResult(MainActivity.REQUEST_CODE_DELETE_RECIPE, intent); //le decimos que queremos eliminar/
        close();
    }

    private void editRecipe() {
        Log.d("EDITING OPEN", recipe.toString());
        Intent intent = new Intent(this, EditRecipeActivity.class);
        intent.putExtra(EditRecipeActivity.RECIPE_PARAMETER, recipe);
        startActivityForResult(intent, REQUEST_CODE_EDIT_RECIPE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == REQUEST_CODE_EDIT_RECIPE) {
            Intent intent = new Intent();
            Recipe newRecipe = (Recipe) data.getSerializableExtra(EditRecipeActivity.RECIPE_PARAMETER_OUT);
            intent.putExtra(RECIPE_PARAMETER_OUT, newRecipe);
            this.setResult(REQUEST_CODE_EDIT_RECIPE, intent); //le decimos que queremos editar/
            close();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }








}
