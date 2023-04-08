package com.example.tictactoe3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.VersionInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import static com.example.tictactoe3.R.drawable.pop_up_background;

public class MainActivity extends AppCompatActivity {

    EditText name, room_id;
    private RewardedAd rewardedAd;



    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.name);
        room_id = findViewById(R.id.room_id);

      rewardedAd = new RewardedAd(this, getString(R.string.RewardedAdd));
        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
                // Ad successfully loaded.
            }

            @Override
            public void onRewardedAdFailedToLoad(LoadAdError adError) {
                // Ad failed to load.
            }
        };
        rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);

//        ShowAdd();

    }

    private void ShowAdd() {

        if (rewardedAd.isLoaded()) {
            Activity activityContext = this;

            RewardedAdCallback adCallback = new RewardedAdCallback() {
                @Override
                public void onRewardedAdOpened() {
                    // Ad opened.
                }

                @Override
                public void onRewardedAdClosed() {

                }

                @Override
                public void onUserEarnedReward(@NonNull RewardItem reward) {


                }

                @Override
                public void onRewardedAdFailedToShow(AdError adError) {



                }
            };
            rewardedAd.show(activityContext, adCallback);
        } else {
            rewardedAd = new RewardedAd(MainActivity.this, getString(R.string.RewardedAdd));

            RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
                @Override
                public void onRewardedAdLoaded() {

                    ShowAdd();
                }

                @Override
                public void onRewardedAdFailedToLoad(LoadAdError adError) {


                }
            };
            rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);
        }

    }


    public void game(View view) {
        if ((!internetIsConnected())) {
            Toast.makeText(MainActivity.this, "No Internet connection ", Toast.LENGTH_LONG).show();
            return;
        }
        if (name.getText().length() <= 0) {
            name.setError("Please choice a name ");
            return;
        } else {
            name.setError(null);
        }

        Intent intent = new Intent(getApplicationContext(), playing.class);
        intent.putExtra("name", name.getText().toString().trim());
        intent.putExtra("check", "make");
        startActivity(intent);
    }

    public void join(View view) {
        if ((!internetIsConnected())) {
            Toast.makeText(MainActivity.this, "No Internet connection ", Toast.LENGTH_LONG).show();
            return;
        }
        if (name.getText().length() <= 0) {
            name.setError("Please choice a name ");
            return;
        } else {
            name.setError(null);
        }
        if (room_id.getText().length() <= 0) {
            room_id.setError("Please Provide a room id ");
            return;
        } else {
            name.setError(null);
        }

        Intent intent = new Intent(getApplicationContext(), playing.class);
        intent.putExtra("name", name.getText().toString().trim());
        intent.putExtra("check", "join");
        intent.putExtra("room_id", room_id.getText().toString().trim());
        startActivity(intent);
    }

    public boolean internetIsConnected() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void ok(View v) {
        startActivity(new Intent(getApplicationContext(), Offiline_play.class));
    }
    AlertDialog alert;


    @SuppressLint("UseCompatLoadingForDrawables")
    public void com(View v) {
        Intent intent = new Intent(MainActivity.this, computer_play.class);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this,R.style.CustomDialogTheme);
        alertDialog.setTitle("Select Difficulty");
        String[] items = {"Easy", "Medium", "Hard"};
        int checkedItem = 1;
        alertDialog.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        intent.putExtra("diff", 1);
                        startActivity(intent);
                        alert.dismiss();
                        break;
                    case 1:
                        intent.putExtra("diff", 2);
                        startActivity(intent);
                        alert.dismiss();
                        break;
                    case 2:
                        startActivity(new Intent(MainActivity.this, train_AI.class));
                        alert.dismiss();
                        break;

                }
            }
        });
         alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.getWindow().setBackgroundDrawable(getDrawable(pop_up_background));
        alert.show();


    }
}