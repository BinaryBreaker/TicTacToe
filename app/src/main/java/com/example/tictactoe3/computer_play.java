package com.example.tictactoe3;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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

public class computer_play extends AppCompatActivity {

    private helper_class current_user_data;
    private helper_class oponent_data;
    TextView player_one, player_two, one_score, two_score, one_tag, two_tag, textView1;
    private List<Button> buttons = new ArrayList<>();
    private List<Integer> remaings = new ArrayList<>();
    private List<Integer> users_moves = new ArrayList<>();
    private List<Integer> comp_move = new ArrayList<>();
    private List<Integer> user_won = new ArrayList<>();
    private String current_trun;
    int count = 0;
    int diff = 3;
    Vibrator vibe;
    ImageView volume;
    MediaPlayer mp;

    private CustomView customView;

    String[][] matrix = {{"1", "2", "3"},
            {"4", "5", "6"},
            {"7", "8", "9"}};

    private Dialog dialog2;
    int medium = 1;

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
        customView = findViewById(R.id.Canvas);

        mp = MediaPlayer.create(this, R.raw.button);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            diff = extras.getInt("diff");
            // and get whatever type user account id is
        }
        TextView mode = findViewById(R.id.mode);
        if (diff == 2) {
            mode.setText("Normal Mode");
        } else if (diff == 1) {
            mode.setText("Easy Mode");
        }

        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        player_one = findViewById(R.id.one_name);
        player_two = findViewById(R.id.two_name);
        one_score = findViewById(R.id.one_score);
        two_score = findViewById(R.id.two_score);
        one_tag = findViewById(R.id.one_tag);
        two_tag = findViewById(R.id.two_tag);

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
        for (int i = 0; i < 9; i++) {
            remaings.add(i);
        }
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
                volume.setColorFilter(ContextCompat.getColor(computer_play.this, R.color.black), android.graphics.PorterDuff.Mode.SRC_IN);
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


    boolean Presser = true;

    public void Button_presser(int pos1, int pos2, int num, String index) {
        if (index.equals("computer")) {
            Presser = true;
        }
        if (Presser) {
            if (Button_Locker[num] && current_user_data.getTag().equals(current_trun) || Button_Locker[num] && index.equals("computer")) {
                Presser = false;
                matrix[pos1][pos2] = current_trun;
                buttons.get(num).setText(current_trun);
                Button_Locker[num] = false;
                count++;
                remaings.remove((Integer) num);
                if (!index.equals("computer")) {
                    users_moves.add(num);
                }
                trun_changer();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (win_check()) {

                            winner();
                            Presser = true;
                        } else {

                            if (!index.equals("computer")) {
                                computer_trun();
                            } else {
                                Presser = true;
                            }
                        }
                    }
                }, 800);

                sound();
            }
        }

    }

    private void computer_trun() {
        switch (diff) {
            case 1: {
                if (medium % 3 != 0) {
                    medium++;
                    think_move();
                } else {
                    medium = 1;
                    random_trun();
                }
                break;
            }
            case 2: {
                medium_diff();
                break;
            }
            case 3: {
                think_move();
                break;
            }
            default: {
                random_trun();
            }
        }
    }


    private void medium_diff() {
        if (medium % 4 != 0) {
            medium++;
            think_move();
        } else {
            medium = 1;
            random_trun();
        }

    }

    private void think_move() {
        ArrayList<Integer> n = new ArrayList<>();
        ArrayList<Integer> p = new ArrayList<>();

        int try_move;
        int apna_move;
        while (true) {
            try_move = oopo_check(n, current_user_data.getTag());
            if (try_move == 10) {
                break;
            }
            if (!remaings.contains(try_move)) {
                n.add(try_move);
            } else if (remaings.contains(try_move)) {
                break;
            }

        }
        while (true) {
            apna_move = oopo_check(p, oponent_data.getTag());
            if (apna_move == 10) {
                break;
            }
            if (!remaings.contains(apna_move)) {
                p.add(apna_move);
            } else if (remaings.contains(apna_move)) {
                break;
            }
        }
        if (apna_move != 10 && remaings.contains(apna_move)) {
            sets_caller(apna_move);
            remaings.remove((Integer) apna_move);
        } else if (try_move != 10 && remaings.contains(try_move)) {
            sets_caller(try_move);
            remaings.remove((Integer) try_move);
        } else if (users_moves.size() <= 0 && remaings.size() == 9) {
            Random rand = new Random();
            int[] a = {0, 2, 4, 6, 8};
            int index = rand.nextInt(a.length);
            int element = a[index];
            sets_caller(element);
            remaings.remove((Integer) element);

        } else if (un_beatable()) {

        } else if (remaings.size() == 8 && users_moves.contains(4)) {
            Random rand = new Random();
            int[] b = {0, 2, 6, 8};
            int index = rand.nextInt(b.length);
            int element = b[index];
            sets_caller(element);
            remaings.remove((Integer) element);

        } else if (users_moves.size() == 2 && users_moves.contains(2) && users_moves.contains(6) || users_moves.size() == 2 && users_moves.contains(0) && users_moves.contains(8)) {
            Random rand = new Random();
            int[] b = {1, 3, 5, 7};
            int index = rand.nextInt(b.length);
            int element = b[index];
            sets_caller(element);
            remaings.remove((Integer) element);
        } else if (comp_move.size() == 1 && users_moves.size() == 2) {
            int[] b = {0, 2, 6, 8};
            for (int i = 0; i < 4; i++) {
                if (!comp_move.contains(b[i]) && !users_moves.contains(b[i])) {
                    sets_caller(b[i]);
                    remaings.remove((Integer) b[i]);
                    return;
                }
            }

        } else if (remaings.contains(4)) {
            sets_caller(4);
            remaings.remove((Integer) 4);
        } else {

            random_trun();

        }

    }

    private boolean un_beatable() {
        if (users_moves.size() == 1 && remaings.size() == 8 && users_moves.contains(3)) {
            sets_caller(0);
            remaings.remove((Integer) (50));
            return true;
        }
        if (users_moves.size() == 1 && remaings.size() == 8 && users_moves.contains(5)) {
            sets_caller(2);
            remaings.remove((Integer) (2));
            return true;
        }
        if (remaings.size() == 6 && users_moves.size() == 2 && remaings.contains(4)) {
            if (users_moves.get(0) % 2 != 0) {
                if (users_moves.get(0).equals(users_moves.get(1) + 1)) {
                    sets_caller(4);
                    remaings.remove((Integer) (4));
                    return true;
                }
            }
        }

        if (remaings.size() == 8 && users_moves.size() == 1 && users_moves.contains(0) || remaings.size() == 8 && users_moves.size() == 1 && users_moves.contains(6)) {
            sets_caller(users_moves.get(0) + 1);
            remaings.remove((Integer) (users_moves.get(0) + 1));
            return true;
        } else if (remaings.size() == 8 && users_moves.size() == 1 && users_moves.contains(2) || remaings.size() == 8 && users_moves.size() == 1 && users_moves.contains(8)) {
            sets_caller(users_moves.get(0) - 1);
            remaings.remove((Integer) (users_moves.get(0) - 1));
            return true;
        } else if (remaings.size() == 6 && users_moves.size() == 2 && users_moves.get(users_moves.size() - 2) % 2 == 0) {
            if (users_moves.get(users_moves.size() - 2) == 0 && users_moves.get(users_moves.size() - 1) == 7 || users_moves.get(users_moves.size() - 2) == 2 && users_moves.get(users_moves.size() - 1) == 7) {
                sets_caller(8);
                remaings.remove((Integer) (8));
                return true;

            }
            if (users_moves.get(users_moves.size() - 2) == 6 && users_moves.get(users_moves.size() - 1) == 1 || users_moves.get(users_moves.size() - 2) == 8 && users_moves.get(users_moves.size() - 1) == 1) {
                sets_caller(0);
                remaings.remove((Integer) (0));
                return true;
            }
        } else if (remaings.size() == 6 && users_moves.size() == 2 && users_moves.get(users_moves.size() - 1) % 2 == 0) {
            if (users_moves.get(users_moves.size() - 1) == 0 && users_moves.get(users_moves.size() - 2) != 1 || users_moves.get(users_moves.size() - 1) == 2 && users_moves.get(users_moves.size() - 2) != 1) {
                sets_caller(1);
                remaings.remove((Integer) (1));
                return true;
            } else if (users_moves.get(users_moves.size() - 1) == 6 && users_moves.get(users_moves.size() - 2) != 7 || users_moves.get(users_moves.size() - 1) == 8 && users_moves.get(users_moves.size() - 2) != 7) {
                sets_caller(7);
                remaings.remove((Integer) (7));
                return true;
            }
        } else if (remaings.size() == 8 && users_moves.size() == 1 && users_moves.get(0) % 2 != 0) {
            sets_caller(users_moves.get(0) + 1);
            remaings.remove((Integer) (users_moves.get(0) + 1));
            return true;
        } else {
            return false;
        }

        return false;
    }


    private int oopo_check(ArrayList<Integer> n, String tag) {


        if (!n.contains(0)) {
            if ((tag.equals(matrix[0][1]) && tag.equals(matrix[0][2]) || tag.equals(matrix[1][0]) && tag.equals(matrix[2][0]) || tag.equals(matrix[1][1]) && tag.equals(matrix[2][2])))
                return 0;
        }
        if (!n.contains(1)) {
            if (tag.equals(matrix[0][0]) && tag.equals(matrix[0][2]) || tag.equals(matrix[1][1]) && tag.equals(matrix[2][1]))
                return 1;
        }
        if (!n.contains(2)) {
            if (tag.equals(matrix[0][0]) && tag.equals(matrix[0][1]) || tag.equals(matrix[1][2]) && tag.equals(matrix[2][2]) || tag.equals(matrix[1][1]) && tag.equals(matrix[2][0]))
                return 2;
        }
        if (!n.contains(3)) {
            if (tag.equals(matrix[1][1]) && tag.equals(matrix[1][2]) || tag.equals(matrix[0][0]) && tag.equals(matrix[2][0]))
                return 3;
        }
        if (!n.contains(4)) {
            if (tag.equals(matrix[1][0]) && tag.equals(matrix[1][2]) || tag.equals(matrix[0][1]) && tag.equals(matrix[2][1]) || tag.equals(matrix[0][0]) && tag.equals(matrix[2][2]) || tag.equals(matrix[2][0]) && tag.equals(matrix[0][2]))
                return 4;
        }
        if (!n.contains(5)) {
            if (tag.equals(matrix[1][0]) && tag.equals(matrix[1][1]) || tag.equals(matrix[0][2]) && tag.equals(matrix[2][2]))
                return 5;
        }
        if (!n.contains(6)) {
            if (tag.equals(matrix[2][1]) && tag.equals(matrix[2][2]) || tag.equals(matrix[1][0]) && tag.equals(matrix[0][0]) || tag.equals(matrix[1][1]) && tag.equals(matrix[0][2]))
                return 6;
        }
        if (!n.contains(7)) {
            if (tag.equals(matrix[2][0]) && tag.equals(matrix[2][2]) || tag.equals(matrix[1][1]) && tag.equals(matrix[0][1]))
                return 7;
        }
        if (!n.contains(8)) {
            if (tag.equals(matrix[2][0]) && tag.equals(matrix[2][1]) || tag.equals(matrix[1][2]) && tag.equals(matrix[0][2]) || tag.equals(matrix[0][0]) && tag.equals(matrix[1][1]))
                return 8;
        }

        return 10;


    }


    private void sets_caller(int pos) {
        final int[][] refrence = {{0, 0}, {0, 1}, {0, 2},
                {1, 0}, {1, 1}, {1, 2},
                {2, 0}, {2, 1}, {2, 2}};
        comp_move.add(pos);
        Button_presser(refrence[pos][0], refrence[pos][1], pos, "computer");
    }

    public void random_trun() {
        Random rand = new Random();
        int randomIndex = rand.nextInt(remaings.size());
        int randomElement = remaings.get(randomIndex);
        remaings.remove(randomIndex);
        sets_caller(randomElement);

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
                switch (r) {
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
                switch (c) {
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
        remaings.clear();
        for (int i = 0; i < 9; i++) {
            remaings.add(i);
        }
        users_moves.clear();
        comp_move.clear();
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
        remaings.clear();
        for (int i = 0; i < 9; i++) {
            remaings.add(i);
        }
        users_moves.clear();
        comp_move.clear();
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
        dialog2 = new Dialog(computer_play.this);
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
