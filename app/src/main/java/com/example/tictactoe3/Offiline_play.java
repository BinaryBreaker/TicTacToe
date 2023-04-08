package com.example.tictactoe3;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

public class Offiline_play extends AppCompatActivity {

    private helper_class current_user_data;
    private helper_class oponent_data;
    TextView player_one, player_two, one_score, two_score, one_tag, two_tag, textView1;
    private List<Button> buttons = new ArrayList<>();
    private String current_trun;
    int count = 0;
    ImageView volume;
    orginizer soundsystem;
    String[][] matrix = {{"1", "2", "3"},
            {"4", "5", "6"},
            {"7", "8", "9"}};

    private Dialog dialog2;
    private  CustomView customView;

    private boolean[] Button_Locker = {true, true, true, true, true, true, true, true, true};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);

        volume = findViewById(R.id.vloume);
        soundsystem = new orginizer(Offiline_play.this, volume);
        AdView adView =  findViewById(R.id.adView);

        new loadingAdds(adView, this);
        volume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundsystem.change();
            }
        });
        customView = findViewById(R.id.Canvas);

        player_one = findViewById(R.id.one_name);
        player_two = findViewById(R.id.two_name);
        one_score = findViewById(R.id.one_score);
        two_score = findViewById(R.id.two_score);
        one_tag = findViewById(R.id.one_tag);
        two_tag = findViewById(R.id.two_tag);

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
        oponent_data = new helper_class("Player 2 :", "O", "0", 10, 2);

        buttons.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button_presser(0, 0, 0);

            }
        });
        buttons.get(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button_presser(0, 1, 1);


            }
        });
        buttons.get(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button_presser(0, 2, 2);

            }
        });
        buttons.get(3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button_presser(1, 0, 3);

            }
        });
        buttons.get(4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button_presser(1, 1, 4);


            }
        });
        buttons.get(5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button_presser(1, 2, 5);


            }
        });
        buttons.get(6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button_presser(2, 0, 6);

            }
        });
        buttons.get(7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button_presser(2, 1, 7);

            }
        });
        buttons.get(8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button_presser(2, 2, 8);

            }
        });

    }


    boolean pressed = true;
    public void Button_presser(int pos1, int pos2, int num) {
        if (Button_Locker[num] && pressed) {
            pressed = false;
            matrix[pos1][pos2] = current_trun;
            buttons.get(num).setText(current_trun);
            Button_Locker[num] = false;
            count++;
            if (win_check()) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        winner();
                        pressed =true;
                    }
                }, 1000);
            }else {
                trun_changer();
                pressed =true;
            }
            soundsystem.sound();


        }

    }

    void trun_changer() {
        if (current_trun.equals("O")) {
            current_trun = "X";
        } else {
            current_trun = "O";
        }
        System.out.println(current_trun);

    }

    private boolean win_check() {

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

        if (count >= 9) {
            Drawer();
            return false;
        }
        return false;
    }

    private void Drawer() {
        String Winner = "Draw";
        count = 0;
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
            Winner = "Player 1 Won";
            current_user_data.setScore((Integer.toString(Integer.parseInt(current_user_data.getScore()) + 1)));
            one_score.setText(current_user_data.getScore());
        } else {
            Winner = "Player 2 Won";
            oponent_data.setScore((Integer.toString(Integer.parseInt(oponent_data.getScore()) + 1)));
            two_score.setText(oponent_data.getScore());
        }
        count = 0;
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


    void finish1() {
        finish();
    }

    private void lose_win_pop(String s) {
        DisplayMetrics displayManager = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayManager);
        int Width = displayManager.widthPixels;
        int height = displayManager.heightPixels;
        dialog2 = new Dialog(Offiline_play.this);
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
                finish1();
            }
        });
        again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog2.dismiss();

                customView.change(9);
            }
        });
        textView1.setText(s);
        dialog2.show();
    }
}
