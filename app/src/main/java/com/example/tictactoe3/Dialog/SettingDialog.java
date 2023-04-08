package com.example.tictactoe3.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.example.tictactoe3.R;
import com.example.tictactoe3.orginizer;

public class SettingDialog extends Dialog {

    private LinearLayout privacyPolicy, RateUs,Sound,ContactUs;
    private ImageView imageView;
    public  static  final int PRIVACY_POLICY_KEY = 9090;
    public  static  final int CONTACT_US_KEY = 787878;

    public SettingDialog(@NonNull Context context) {
        super(context);
        init();

    }

    private void init() {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.setting_dialog);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCancelable(false);
        hooks();

    }

    private void hooks() {
        privacyPolicy =  findViewById(R.id.privacypolicy);
        RateUs =  findViewById(R.id.rateus);
        Sound =  findViewById(R.id.sound);
        ContactUs =  findViewById(R.id.contactus);
        imageView =  findViewById(R.id.vloume);

        RateUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = "https://play.google.com/store/apps/details?id="+getContext().getPackageName();

                Intent intent  = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                getContext().startActivity(intent);
            }
        });


        Sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new orginizer(getContext(),imageView).change();
            }
        });


        ContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = "https://play.google.com/store/apps/details?id="+getContext().getPackageName();

                Intent intent  = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                getContext().startActivity(intent);
            }
        });

        privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = "https://play.google.com/store/apps/details?id="+getContext().getPackageName();

                Intent intent  = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                getContext().startActivity(intent);
            }
        });
    }
}
