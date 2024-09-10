package com.example.tokuverseproject.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import com.example.tokuverseproject.Model.User;
import com.example.tokuverseproject.R;
import com.example.tokuverseproject.databinding.ActivityHomeBinding;


public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;
    User user;

    private static final int REQUEST_CODE_HERO_INFO = 001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        user = (User) getIntent().getSerializableExtra("user");
        String scene = getIntent().getStringExtra("scene");

        binding.bottomNavigationView.setSelectedItemId(R.id.home);
        replaceFragment(new HomeFragment(), createUserBundle(user));

        User finalUser = user;
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    Log.d("home", "1");
                    replaceFragment(new HomeFragment(), createUserBundle(finalUser));
                    break;
                case R.id.profile:
                    Log.d("profile", "2");
                    replaceFragment(new ProfileFragment(), createUserBundle(finalUser));
                    break;
                case R.id.store:
                    Log.d("Store", "3");
                    replaceFragment(new StoreFragment(), createUserBundle(finalUser));
                    break;
                case R.id.history:
                    Log.d("Store", "4");
                    replaceFragment(new HistoryFragment(), createUserBundle(finalUser));
                    break;
            }
            return true;
        });
    }


    private Bundle createUserBundle(User user) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);
        return bundle;
    }

    private void replaceFragment(Fragment fragment, Bundle bundle) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}