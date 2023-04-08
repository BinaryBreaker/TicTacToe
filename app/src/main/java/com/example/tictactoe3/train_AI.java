package com.example.tictactoe3;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.LogPrinter;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class train_AI extends AppCompatActivity {

    private helper_class current_user_data;
    private helper_class oponent_data;
    TextView player_one, player_two, one_score, two_score, one_tag, two_tag, textView1, mode;
    private List<Button> buttons = new ArrayList<>();
    private String current_trun;
    private  CustomView customView;
    Vibrator vibe;
    ImageView volume;
    MediaPlayer mp;


    String[][] matrix = {{"1", "2", "3"},
            {"4", "5", "6"},
            {"7", "8", "9"}};

    private Dialog dialog2;

    int counter = 1;

    private boolean[] Button_Locker = {true, true, true, true, true, true, true, true, true};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);

        AdView adView =  findViewById(R.id.adView);

        new loadingAdds(adView, this);
        mp = MediaPlayer.create(this, R.raw.button);
        customView = findViewById(R.id.Canvas);

        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        player_one = findViewById(R.id.one_name);
        player_two = findViewById(R.id.two_name);
        one_score = findViewById(R.id.one_score);
        two_score = findViewById(R.id.two_score);
        one_tag = findViewById(R.id.one_tag);
        two_tag = findViewById(R.id.two_tag);
        mode = findViewById(R.id.mode);
        mode.setText("Hard Mode");

        volume = findViewById(R.id.vloume);

        buttons.add(findViewById(R.id.one));
        buttons.add(findViewById(R.id.two));
        buttons.add(findViewById(R.id.three));
        buttons.add(findViewById(R.id.four));
        buttons.add(findViewById(R.id.five));
        buttons.add(findViewById(R.id.six));
        buttons.add(findViewById(R.id.seven));
        buttons.add(findViewById(R.id.eight));
        buttons.add(findViewById(R.id.nine));
        current_trun = "X";
        current_user_data = new helper_class("Player 1 :", "X", "0", 10, 2);
        oponent_data = new helper_class("Computer :", "O", "0", 10, 2);
        player_two.setText(oponent_data.getName());
        buttons.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button_presser(0, 0, 0, "player");

            }
        });
        buttons.get(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button_presser(0, 1, 1, "player");


            }
        });
        buttons.get(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button_presser(0, 2, 2, "player");

            }
        });
        buttons.get(3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button_presser(1, 0, 3, "player");

            }
        });
        buttons.get(4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button_presser(1, 1, 4, "player");


            }
        });
        buttons.get(5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button_presser(1, 2, 5, "player");


            }
        });
        buttons.get(6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button_presser(2, 0, 6, "player");

            }
        });
        buttons.get(7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button_presser(2, 1, 7, "player");

            }
        });
        buttons.get(8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button_presser(2, 2, 8, "player");

            }
        });

        volume.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View view) {
                volume.setImageBitmap(null);
                volume.setBackground(null);
                volume.setColorFilter(ContextCompat.getColor(train_AI.this, R.color.black), android.graphics.PorterDuff.Mode.SRC_IN);
                switch (counter) {
                    case 1: {

                        volume.setImageDrawable(getDrawable(R.drawable.ic_baseline_vibration_24));
                        counter = 2;
                        break;
                    }
                    case 2: {

                        counter = 3;
                        volume.setImageDrawable(getDrawable(R.drawable.ic_baseline_volume_off_24));

                        break;
                    }
                    case 3: {
                        volume.setImageDrawable(getDrawable(R.drawable.ic_baseline_volume_up_24));
                        counter = 1;
                        break;
                    }
                }
            }
        });
    }

    private void sound() {
        switch (counter) {
            case 1: {
                mp.start();
                break;
            }
            case 2: {
                vibe.vibrate(80);
                break;
            }

        }
    }

    boolean PressedSate = true;

    public void Button_presser(int pos1, int pos2, int num, String index) {

        if (PressedSate) {
            if (Button_Locker[num] && current_user_data.getTag().equals(current_trun) && index.equals("player") || Button_Locker[num] && index.equals("computer") && oponent_data.getTag().equals(current_trun)) {
                PressedSate = false;
                matrix[pos1][pos2] = current_trun;
                buttons.get(num).setText(current_trun);
                Button_Locker[num] = false;
                trun_changer();


                if (win_check(matrix)) {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            winner();
                            PressedSate = true;
                        }
                    }, 1000);

                } else if (Draw_check(matrix)) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Drawer();
                            PressedSate = true;
                        }
                    }, 1000);

                } else if (!win_check(matrix) && !Draw_check(matrix) && !index.equals("computer")) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            computer_trun();

                        }
                    }, 1000);

                }else if (!win_check(matrix) && !Draw_check(matrix) && !index.equals("player")) {
                    PressedSate = true;



                }


                sound();
            }
        }

    }

    private void computer_trun() {
        int best_score = -1;
        int[] best_move = new int[2];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (!matrix[i][j].equals("X") && !matrix[i][j].equals("O")) {
                    String copy = new String(matrix[i][j]);
                    matrix[i][j] = oponent_data.getTag();
                    int score = minimax(matrix, 0, false);
                    matrix[i][j] = copy;
                    if (score > best_score) {
                        best_score = score;
                        best_move[0] = i;
                        best_move[1] = j;
                    }

                }
            }
        }
        PressedSate = true;
        sets_caller(best_move[0], best_move[1]);
    }

    private int minimax(String[][] matrix, int depth, boolean isMaximizing) {
        boolean wining = win_check(matrix,false);
        boolean Draw = Draw_check(matrix);
        if (!isMaximizing && wining) {
            return 1;
        } else if (isMaximizing && wining) {
            return -1;

        } else if (Draw) {
            return 0;
        }
        if (isMaximizing) {
            int best_score = -1;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (!matrix[i][j].equals("X") && !matrix[i][j].equals("O")) {
                        String copy = new String(matrix[i][j]);
                        matrix[i][j] = oponent_data.getTag();
                        int score = minimax(matrix, depth + 1, false);
                        matrix[i][j] = copy;
                        if (score > best_score) {
                            best_score = score;
                        }
                    }
                }
            }
            return best_score;
        } else {
            int best_score = 1;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (!matrix[i][j].equals("X") && !matrix[i][j].equals("O")) {
                        String copy = new String(matrix[i][j]);
                        matrix[i][j] = current_user_data.getTag();
                        int score = minimax(matrix, depth + 1, true);
                        matrix[i][j] = copy;
                        if (score < best_score) {
                            best_score = score;
                        }
                    }
                }
            }

            return best_score;
        }
    }


    private boolean Draw_check(String[][] matrix) {

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (matrix[i][j].equals("X") || matrix[i][j].equals("O")) {
                } else {
                    return false;
                }
            }
        }

        return true;
    }

    private void sets_caller(int pos1, int pos2) {
        boolean flag = true;
        int count = -1;
        for (int i = 0; i < 3 && flag; i++) {
            for (int j = 0; j < 3 && flag; j++) {
                count++;
                if (pos1 == i && pos2 == j) {
                    flag = false;
                }
            }
        }
        Button_presser(pos1, pos2, count, "computer");
    }

    void trun_changer() {
        if (current_trun.equals("O")) {
            current_trun = "X";
        } else {
            current_trun = "O";
        }
        System.out.println(current_trun);

    }

    private boolean win_check(String[][] matrix,boolean guessing) {

        for (int r = 0; r < 3; r++) {
            if (matrix[r][0].equals(matrix[r][1]) && matrix[r][1].equals(matrix[r][2])) {
                return true;
            }
        }
        for (int c = 0; c < 3; c++) {
            if (matrix[0][c].equals(matrix[1][c]) && matrix[1][c].equals(matrix[2][c]))
                return true;
        }
        if (matrix[0][0].equals(matrix[1][1]) && matrix[1][1].equals(matrix[2][2]))
            return true;

        if (matrix[0][2].equals(matrix[1][1]) && matrix[1][1].equals(matrix[2][0]))
            return true;

        return false;
    }
    private boolean win_check(String[][] matrix) {

        for (int r = 0; r < 3; r++) {
            if (matrix[r][0].equals(matrix[r][1]) && matrix[r][1].equals(matrix[r][2])) {
                switch (r){
                    case 0:
                        customView.change(1);
                        break;
                    case 1:
                        customView.change(2);
                        break;
                    case 2:
                        customView.change(3);
                        break;
                }
                return true;
            }
        }
        for (int c = 0; c < 3; c++) {
            if (matrix[0][c].equals(matrix[1][c]) && matrix[1][c].equals(matrix[2][c])) {
                switch (c){
                    case 0:
                        customView.change(4);
                        break;
                    case 1:
                        customView.change(5);
                        break;
                    case 2:
                        customView.change(6);
                        break;
                }
                return true;
            }
        }
        if (matrix[0][0].equals(matrix[1][1]) && matrix[1][1].equals(matrix[2][2])) {
            customView.change(8);
            return true;
        }
        if (matrix[0][2].equals(matrix[1][1]) && matrix[1][1].equals(matrix[2][0])) {
            customView.change(7);
            return true;
        }

        return false;
    }
    private void Drawer() {
        String Winner = "Draw";
        int count = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                matrix[i][j] = Integer.toString(count);
                count++;
            }
        }
        count = 0;
        for (int i = 0; i < 9; i++) {
            buttons.get(i).setText(null);
        }
        for (int i = 0; i < 9; i++) {
            Button_Locker[i] = true;
        }


        lose_win_pop(Winner);
    }

    private void winner() {
        String Winner;
        if (current_trun.equals(current_user_data.getTag())) {
            Winner = "Computer Won";
            oponent_data.setScore((Integer.toString(Integer.parseInt(oponent_data.getScore()) + 1)));
            two_score.setText(oponent_data.getScore());
        } else {
            trun_changer();

            Winner = "Player 1 Won";
            current_user_data.setScore((Integer.toString(Integer.parseInt(current_user_data.getScore()) + 1)));
            one_score.setText(current_user_data.getScore());

        }
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                matrix[i][j] = Integer.toString(count);
                count++;
            }
        }
        count = 0;
        for (int i = 0; i < 9; i++) {
            buttons.get(i).setText(null);
        }
        for (int i = 0; i < 9; i++) {
            Button_Locker[i] = true;
        }

        lose_win_pop(Winner);

    }


    private void lose_win_pop(String s) {
        DisplayMetrics displayManager = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayManager);
        int Width = displayManager.widthPixels;
        int height = displayManager.heightPixels;
        dialog2 = new Dialog(train_AI.this);
        dialog2.setContentView(R.layout.activity_pop_up);
        dialog2.getWindow().setLayout((int) (Width * 0.9), (int) (Width * 0.5));
        dialog2.getWindow().setBackgroundDrawable(getDrawable(R.drawable.pop_up_background));
        dialog2.setCancelable(false);
        Button menu11 = dialog2.findViewById(R.id.menu);
        Button again = dialog2.findViewById(R.id.again);
        textView1 = dialog2.findViewById(R.id.text12);
        menu11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog2.dismiss();
                finish();
            }
        });
        again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (current_trun.equals(oponent_data.getTag()))
                    computer_trun();
                dialog2.dismiss();

                customView.change(9);
            }
        });
        textView1.setText(s);

        dialog2.show();
    }

}
