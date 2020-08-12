package com.example.menusemanal;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class EditRecipeActivity extends AppCompatActivity {
    public static String  RECIPE_PARAMETER = "RECIPE_PARAMETER";
    public static String  RECIPE_PARAMETER_OUT = "RECIPE_PARAMETER_OUT";
    private EditText textTitle, textIngredients, textDetails;
    private Spinner spinnerDay, spinnerType;
    private Button buttonSave, buttonCancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);
        Bundle bundle = getIntent().getExtras();
        textTitle = findViewById(R.id.itextTitle);
        textIngredients = findViewById(R.id.itextIngredients);
        textDetails = findViewById(R.id.itextDetails);
        buttonSave = findViewById(R.id.buttonSave);
        buttonCancel = findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });


        spinnerDay = findViewById(R.id.itextDay);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.days, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDay.setAdapter(adapter);


        spinnerType = findViewById(R.id.itextType);
        adapter = ArrayAdapter.createFromResource(this, R.array.types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter);



        if(bundle != null){
           final Recipe recipe = (Recipe) bundle.getSerializable(RECIPE_PARAMETER);
            if(recipe!=null) {
                textTitle.setText(recipe.getTitle());
                textIngredients.setText(recipe.getIngredients());
                textDetails.setText(recipe.getDescription());
                spinnerDay.setSelection(recipe.getDay());
                spinnerType.setSelection(recipe.getType());
                buttonSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editRecipe(false);
                    }
                });
            }
        } else {
            buttonSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editRecipe(true);
                }
            });
        }
    }

    private void close(){
        this.finish();
    }

    public void editRecipe(boolean isNew) {
        int intDay = spinnerDay.getSelectedItemPosition();
        int intType = spinnerType.getSelectedItemPosition();
        Recipe recipe = new Recipe(textTitle.getText().toString(), intDay, intType, textIngredients.getText().toString(), textDetails.getText().toString());
        Intent intent = new Intent();
        intent.putExtra(RECIPE_PARAMETER_OUT, recipe);
        if(isNew){
            this.setResult(MainActivity.REQUEST_CODE_NEW_RECIPE, intent);
        } else {
            this.setResult(DetailRecipeActivity.REQUEST_CODE_EDIT_RECIPE, intent);
        } //le decimos que queremos editar/
        close();
    }



}
