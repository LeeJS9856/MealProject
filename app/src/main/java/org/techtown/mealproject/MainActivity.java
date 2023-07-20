package org.techtown.mealproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private PlannerFragment plannerFragment = new PlannerFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, plannerFragment).commitAllowingStateLoss();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());
    }

    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.plannerTab:
                    transaction.replace(R.id.container, plannerFragment).commitAllowingStateLoss();
                    Log.d("tab", "1번 탭 실행 됨".toString());
                    break;

                case R.id.planEditTab:

                    break;

                case R.id.addMenuTab:

                    break;
            }

            return true;
        }
    }
}