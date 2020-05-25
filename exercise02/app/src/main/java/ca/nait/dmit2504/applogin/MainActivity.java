package ca.nait.dmit2504.applogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String WEBSITE_URL_EXTRA = "ca.nait.dmit2504.WEBSITE_URL";

    // Define data fields for views to access
    EditText mUsernameEditText;
    EditText mPasswordEditText;
    EditText mWebsiteUrlEditText;
    Switch mRememberMeSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUsernameEditText = findViewById(R.id.username_edit);
        mPasswordEditText = findViewById(R.id.password_edit);
        mWebsiteUrlEditText = findViewById(R.id.url_edit);
        mRememberMeSwitch = findViewById(R.id.remember_switch);

        Button signButton = findViewById(R.id.signin_button);
        signButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // Retrieve the username and password values from their views
                String username = mUsernameEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();
                String websiteUrl = mWebsiteUrlEditText.getText().toString();
                // Validate that the username is "admin" (case-insensitive and password is "secret" (case-sensitive)
                if (username.equalsIgnoreCase("admin") && password.equals("secret") ) {
                    // If the rememberMe is on then save username, password, url as a preference
                    if (mRememberMeSwitch.isChecked()) {
                        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = sharedPrefs.edit();
                        editor.putString("username", username);
                        editor.putString("password", password);
                        editor.putString("websiteUrl", websiteUrl);
                        editor.commit();
                    } else {
                        // clear the prefs
                    }

                    // create an intent to start the WebViewActivity
                    Intent webviewIntent = new Intent(getApplicationContext(), WebViewActivity.class);
                    // pass the URL to the WebViewActivity as an EXTRA
                    webviewIntent.putExtra(WEBSITE_URL_EXTRA, websiteUrl);
                    startActivity(webviewIntent);
                } else {
                    // display an message that credentials are incorrect
                    Toast.makeText(getApplicationContext(), R.string.invalid_credentials, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
