package com.jstech.fluenterp;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

/**
 * Base class for all standard activities.
 *
 * Handles:
 * - Status bar colouring (ContextCompat, no deprecated API)
 * - Toolbar setup (setSupportActionBar + title + back arrow)
 * - Home/back-arrow tap → onBackPressed()
 *
 * Excluded activities: SplashActivity (extends AwesomeSplash),
 * MainActivity (NavigationDrawer), SettingsActivity / AppCompatPreferenceActivity.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupStatusBar();
    }

    private void setupStatusBar() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.status_bar_colour));
    }

    protected void setupToolbar(int toolbarResId, String title) {
        Toolbar toolbar = findViewById(toolbarResId);
        setSupportActionBar(toolbar);
        setTitle(title);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
