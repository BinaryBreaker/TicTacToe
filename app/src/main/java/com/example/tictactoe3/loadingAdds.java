package com.example.tictactoe3;

import android.content.Context;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class loadingAdds {
    private final  AdView mAdView;
    private final  Context context;

    public loadingAdds(AdView adView, Context context) {
        MobileAds.initialize(context, context.getString(R.string.AddId));
        this.mAdView = adView;
        this.context = context;


        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);
    }


}
