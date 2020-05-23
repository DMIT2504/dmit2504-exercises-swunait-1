package ca.nait.dmit2504.guessthenumber;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";   //MainActivity.class.getSimpleName();

    // Define data fields to accessing views in our layout
    private TextView mRangeText;
    private EditText mGuessNumberEdit;
    private TextView mGuessResultText;
    private TextView mGuessCountText;

    // Define data fields to manage game logic
    private int mLowerBound = 1;
    private int mUpperBound = 100;
    private int mNumberOfGuesses = 0;
    private int mSecretNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find all the views in our layout
        mRangeText = findViewById(R.id.guess_range_text);
        mGuessNumberEdit = findViewById(R.id.guess_number_edit);
        mGuessResultText = findViewById(R.id.guess_result_text);
        mGuessCountText = findViewById(R.id.guess_count_text);

        // Read the minimum and maximum number preference
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        mLowerBound = Integer.parseInt( sharedPref.getString("minimum_value_preference", "1"));
        mUpperBound = Integer.parseInt( sharedPref.getString("maximum_value_preference","100"));

        renderRange();

        generateSecretNumber();
    }

    private void renderRange() {
        // Update mRangeText to display the min and max value
        String guessRangeMessage = String.format("%d and %d", mLowerBound, mUpperBound);
        mRangeText.setText(guessRangeMessage);
    }

    private void renderNumberOfGuesses() {
        String guessText = getResources().getString(R.string.number_of_guesses);
        String guessMessage = String.format(guessText + " %d", mNumberOfGuesses);
        mGuessCountText.setText(guessMessage);
    }

    public void onSubmitClicked(View view) {
        // increment the number of guesses
        mNumberOfGuesses += 1;
        // get the number from the edit text
        int guessNumber = Integer.parseInt( mGuessNumberEdit.getText().toString() );
        // determine if guessNumber is too low, too high, or correct
        if (guessNumber < mSecretNumber) {
            // set the lower bound to the guessNumber
            mLowerBound = guessNumber;
            // update the text to display the message
            mGuessResultText.setText("Your last guess was too low");
            mGuessNumberEdit.setText("");
            renderRange();
            renderNumberOfGuesses();
        } else if (guessNumber > mSecretNumber) {
            // set the upper bound to the guessNumber
            mUpperBound = guessNumber;
            // update the text to display the result
            mGuessResultText.setText("Your last guess was too high");
            mGuessNumberEdit.setText("");
            renderRange();
            renderNumberOfGuesses();
        } else {
            mGuessResultText.setText("Your last guess was correct.");
            Toast.makeText(this, "Game Over!", Toast.LENGTH_SHORT).show();
            restartGame();
        }
    }

    private void generateSecretNumber() {
        // Generate the random number
        final int difference = mUpperBound - mLowerBound;
        final int randomNumber = new Random().nextInt(difference) + 1;
        mSecretNumber = randomNumber + mLowerBound;
        Log.d(TAG,"Secret number is " + mSecretNumber);

    }

    private void restartGame() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        mLowerBound = Integer.parseInt( sharedPref.getString("minimum_value_preference", "1"));
        mUpperBound = Integer.parseInt( sharedPref.getString("maximum_value_preference","100"));
        renderRange();
        renderNumberOfGuesses();
        generateSecretNumber();
        mGuessResultText.setText("");
        mGuessCountText.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
//        return super.onCreateOptionsMenu(menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
