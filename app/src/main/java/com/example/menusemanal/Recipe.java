package com.example.menusemanal;


import java.io.Serializable;

public class Recipe implements Serializable {

    private String title;
    private int day;
        public static int MONDAY = 0;
        public static int TUESDAY = 1;
        public static int WEDNESDAY = 2;
        public static int THURSDAY = 3;
        public static int FRIDAY = 4;
        public static int SATURDAY = 5;
        public static int SUNDAY = 6;
        public static String dayString[] = {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"};
    private int type;
        public static int BREAKFAST = 0;
        public static int LUNCH = 1;
        public static int DINNER = 2;
    public static String typeString[] = {"BREAKFAST", "LUNCH", "DINNER"};
    private String ingredients;
    private String description;


    public Recipe(String title, int day, int type, String ingredients, String description) {
        this.title = title;
        this.day = day;
        this.type = type;
        this.ingredients = ingredients;
        this.description = description;
    }



    public String getTitle() {
        return title;
    }

    public int getDay() {
        return day;
    }

    public String getDayString() {
        return dayString[this.day];
    }

    public int getType() {
        return type;
    }

    public String getTypeString() {
        return typeString[this.type];
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getDescription() {
        return description;
    }


    public String toString() {
        return "RECETA: " + title + " /" + type + " /" + day + " /" + ingredients + " /" + description + " / \n";
    }
}
