package com.example.buttonmasher;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;

public class MainActivity extends AppCompatActivity {

    // Firestore
    final private static FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    private static MainActivity mInstance;
    private static Profile mProfile;
    private static AppBarConfiguration mAppBarConfiguration;

    private String mID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInstance = this;
        // Getting data from Firestore
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mID = sharedPreferences.getString("ID", "");
        firestore.collection("Profiles").document(mID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    // Getting data
                    if (document.exists()) {
                        // set data
                        mProfile = document.toObject(Profile.class);
                    } else {
                        Log.i("MainActivity", "Creating new profile");
                        mProfile = new Profile(0, 0, 0, 0);
                        firestore.collection("Profiles").add(mProfile)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    mID = documentReference.getId();
                                    sharedPreferences.edit().putString("ID", mID).apply();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.e("MainActivity", "Error adding document", e);
                                }
                            });
                    }
                    // Setup view
                    setContentView(R.layout.activity_main);
                    BottomNavigationView navView = findViewById(R.id.nav_view);
                    // Passing each menu ID as a set of Ids because each
                    // menu should be considered as top level destinations.
                    mAppBarConfiguration = new AppBarConfiguration.Builder(
                            R.id.navigation_play, R.id.navigation_shop, R.id.list_settings)
                            .build();
                    NavController navController = Navigation.findNavController(mInstance, R.id.nav_host_fragment);
                    NavigationUI.setupActionBarWithNavController(mInstance, navController, mAppBarConfiguration);
                    NavigationUI.setupWithNavController(navView, navController);
                } else {
                    // retry / ask for wifi
                    Log.e("MainActivity", "Failed to get task - " + task.getResult());
                }
            }
        });

        setTitle(getString(R.string.app_name));
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return navController.popBackStack();
                // NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }

    @Override
    protected void onStop() {
        super.onStop();
        firestore.collection("Profiles").document(mID).set(mProfile);
    }

    public static MainActivity getInstance() {
        return mInstance;
    }

    public static Profile getProfile() { return mProfile; }

    public void displayToast(String message, int length) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), message, length).show();
            }
        });
    }

    public void displayToast(String message) {
        displayToast(message, Toast.LENGTH_SHORT);
    }
}