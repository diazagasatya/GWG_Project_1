package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    // Access sandwich details
    private TextView mAlsoKnownAs, mPlaceOfOrigin, mIngredients, mDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Bind the text-views and image-view with the layout
        ImageView ingredientsIv = findViewById(R.id.image_iv);
        mAlsoKnownAs = findViewById(R.id.also_known_tv);
        mPlaceOfOrigin = findViewById(R.id.origin_tv);
        mIngredients = findViewById(R.id.ingredients_tv);
        mDescription = findViewById(R.id.description_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    /**
     * This will print out toast stating the failure of grabbing data from JsonUtils
     */
    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    /**
     * In here you will set the textView and imageView to the corresponding data grabbed
     * from JsonUtils.
     */
    private void populateUI(Sandwich sandwich) {

        // Represent the list<Strings> as single string separated by commas
        StringBuilder alsoKnownAsString = new StringBuilder();
        int alsoKnownAsLength = sandwich.getAlsoKnownAs().size();
        StringBuilder ingredientsString = new StringBuilder();
        int ingredientsLength = sandwich.getIngredients().size();

        // Grab all elements in alsoKnownAs and ingredients
        if(alsoKnownAsLength > 1) {
            for(int i = 0; i < alsoKnownAsLength; i++) {
                alsoKnownAsString.append(sandwich.getAlsoKnownAs().get(i));

                // if reached end element don't add comma
                if(i < alsoKnownAsLength - 1) {
                    alsoKnownAsString.append(", ");
                }
            }
        }
        else {
            // Not available
            alsoKnownAsString = new StringBuilder("N/A");
        }

        if(ingredientsLength > 1) {
            for(int i = 0; i < ingredientsLength; i++) {
                ingredientsString.append(sandwich.getIngredients().get(i));

                // if reached end element don't add comma
                if(i < ingredientsLength - 1) {
                    ingredientsString.append(", ");
                }
            }
        }
        else {
            ingredientsString = new StringBuilder("N/A");
        }

        // Set textViews here
        mAlsoKnownAs.setText(alsoKnownAsString.toString());
        mDescription.setText(sandwich.getDescription());
        mIngredients.setText(ingredientsString.toString());
        mPlaceOfOrigin.setText(sandwich.getPlaceOfOrigin());
    }
}
