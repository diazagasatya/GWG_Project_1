package com.udacity.sandwichclub.utils;

import android.util.Log;
import com.udacity.sandwichclub.model.Sandwich;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonUtils {

    // Initialization of strings before JSON parsing
    private final static String INITIALIZATION = "N/A";

    /**
     * This method will parse the JSON information and instantiate a Sandwich Object
     * with the respected information to it and then return it.
     * @param json              The name of the JSON Object
     * @return                  Sandwich Object
     */
    public static Sandwich parseSandwichJson(String json) {

        // Store the string and list variables to instantiate Sandwich object
        String mainName = INITIALIZATION;
        String placeOfOrigin = INITIALIZATION;
        String description = INITIALIZATION;
        String image = INITIALIZATION;
        List<String> alsoKnownAsList = new ArrayList<>();
        List<String> ingredientsList = new ArrayList<>();
        Boolean validation = false;

        try {

            // Grab the sandwich kind into a JSONObject
            JSONObject sandwich = new JSONObject(json);

            // Grab the name object
            JSONObject name = sandwich.getJSONObject("name");

            // Grab the main name & also known as
            mainName = name.getString("mainName");

            // Grab the string value of alsoKnownAs get rid of the square brackets
            String alsoKnownAsString = name.getString("alsoKnownAs")
                    .replaceAll("\\[", "")
                    .replaceAll("]","")
                    .replaceAll("\"", "");

            // Iterate to all of the string parse and store string values to List<String>
            alsoKnownAsList = Arrays.asList(alsoKnownAsString.split(","));

            // Grab the place of the origin
            if(!sandwich.get("placeOfOrigin").equals("")) {
                placeOfOrigin = sandwich.getString("placeOfOrigin");
            }

            // Grab the description
            description = sandwich.getString("description");

            // Grab the image URL
            image = sandwich.getString("image");

            // Grab the ingredients
            String ingredients = sandwich.getString("ingredients")
                    .replaceAll("\\[", "")
                    .replaceAll("]","")
                    .replaceAll("\"", "");

            ingredientsList = Arrays.asList(ingredients.split(","));

            // If pass here, therefore there's no exception
            validation = true;


        } catch (Exception e) {
            Log.d("JSONEXCEPTION", "Not Available Parameter " + e);
        }

        // Instantiate Sandwich object only if no Exception thrown
        if(validation) {
            Sandwich initializedSandwich = new Sandwich(mainName,alsoKnownAsList,
                    placeOfOrigin,description,image,ingredientsList);
            return initializedSandwich;
        }

        return null;
    }

}
