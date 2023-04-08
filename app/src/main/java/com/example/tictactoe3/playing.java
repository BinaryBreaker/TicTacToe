package com.example.tictactoe3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class playing extends AppCompatActivity {

    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("rooms");
    private String check, name;
    private helper_class current_user_data;
    private helper_class oponent_data;
    private String room_id;

    TextView player_one, player_two, one_score, two_score, one_tag, two_tag, textView1;
    private Button one, two, three, four, five, six, seven, eight, nine, menu11, again;
    private String current_trun;
    String standard;
    MediaPlayer mp;

    int count = 0;
    private CustomView customView;
    String[][] matrix = {{"1", "2", "3"},
            {"4", "5", "6"},
            {"7", "8", "9"}};

    private Dialog dialog, dialog2;
    private Button button;
    private TextView textView;

    private boolean truner = false;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);

        AdView adView =  findViewById(R.id.adView);

        new loadingAdds(adView, this);
        Bundle extras = getIntent().getExtras();
        name = extras.getString("name");
        check = extras.getString("check");
        customView = findViewById(R.id.Canvas);
        mp = MediaPlayer.create(this, R.raw.button);
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


        player_one = findViewById(R.id.one_name);
        player_two = findViewById(R.id.two_name);
        one_score = findViewById(R.id.one_score);
        two_score = findViewById(R.id.two_score);
        one_tag = findViewById(R.id.one_tag);
        two_tag = findViewById(R.id.two_tag);

        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);
        five = findViewById(R.id.five);
        six = findViewById(R.id.six);
        seven = findViewById(R.id.seven);
        eight = findViewById(R.id.eight);
        nine = findViewById(R.id.nine);


        if (check.equals("make")) {
            player_one.setText(name + " :");
            one_tag.setText("X");
            current_trun = "X";
            standard = "X";
            current_user_data = new helper_class(name, "X", "0", 10, 1);
            make_room();
        } else {
            standard = "O";
            player_one.setText(name + " :");
            current_trun = "O";
            one_tag.setText(current_trun);
            room_id = extras.getString("room_id");
            oponent_data = new helper_class("Player 2", "X", "0", 10, 1);
            current_user_data = new helper_class(name, "O", "0", 10, 2);
            popup("Joining  to room id : " + room_id);
            join_room();
        }


        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn1("1");

            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn2("1");

            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn3("1");

            }
        });
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn4("1");

            }
        });
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn5("1");

            }
        });
        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn6("1");

            }
        });
        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn7("1");

            }
        });
        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn8("1");

            }
        });
        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn9("1");

            }
        });
    }

    private void make_room() {
        room_id = getAlphaNumericString(4);
        databaseReference.child(room_id).child(room_id).setValue(current_user_data);
        popup("Waiting for player \nYour room id : " + room_id);
        join_room();
    }

    private void join_room() {
        databaseReference.child(room_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() == null) {
                    Toast.makeText(getApplicationContext(), "No room with that id ", Toast.LENGTH_LONG).show();
                    finish();
                } else if (snapshot.getValue() != null && !check.equals("make")) {
                    if (snapshot.child(room_id).child("room_members").getValue(Integer.class) > 1) {
                        Toast.makeText(getApplicationContext(), "Room already Started", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                        finish();
                    }
                    dialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference.child(room_id).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                oponent_data = (helper_class) snapshot.getValue(helper_class.class);
                if (!oponent_data.getName().equals(name)) {
                    dialog.dismiss();
                    player_two.setText(oponent_data.getName() + " :");
                    two_tag.setText(oponent_data.getTag());
                    two_score.setText(oponent_data.getScore());
                    switch (oponent_data.getButton()) {
                        case 1: {
                            btn1("2");
                            break;
                        }
                        case 2: {
                            btn2("2");
                            break;
                        }
                        case 3: {
                            btn3("2");
                            break;
                        }
                        case 4: {
                            btn4("2");
                            break;
                        }
                        case 5: {
                            btn5("2");
                            break;
                        }
                        case 6: {
                            btn6("2");
                            break;
                        }
                        case 7: {
                            btn7("2");
                            break;
                        }
                        case 8: {
                            btn8("2");
                            break;
                        }
                        case 9: {
                            btn9("2");
                            break;
                        }
                    }


                }

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error + "=================================================");

            }
        });
    }



    int counter = 1;
    Vibrator vibe;

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
    private void btn1(String index) {
        if (index.equals("1") && current_trun.equals(standard)) {
            matrix[0][0] = current_trun;
            one.setText(current_trun);
            current_user_data.setButton(1);
            current_user_data.setRoom_members(2);
            databaseReference.child(room_id).child(room_id).setValue(current_user_data);
            trun_changer();
            if (win_check()) {
                winner();
            }
            count++;
            truner = true;
            sound();

        } else if (index.equals("2")) {//X O
            matrix[0][0] = oponent_data.getTag();
            one.setText(oponent_data.getTag());
            if (truner) {
                trun_changer();
            }
            if (win_check()) {
                winner();
            }
            count++;
            truner = true;
            sound();

            sound();
        }


    }

    private void btn2(String index) {
        if (index.equals("1") && current_trun.equals(standard)) {

            matrix[0][1] = current_trun;
            two.setText(current_trun);
            current_user_data.setButton(2);
            current_user_data.setRoom_members(2);
            databaseReference.child(room_id).child(room_id).setValue(current_user_data);
            trun_changer();
            if (win_check()) {
                winner();
            }
            count++;

            truner = true;
            sound();

        } else if (index.equals("2")) {

            matrix[0][1] = oponent_data.getTag();
            two.setText(oponent_data.getTag());
            if (truner) {
                trun_changer();
            }
            if (win_check()) {
                winner();
            }
            count++;

            truner = true;
            sound();

        }

    }

    private void btn3(String index) {
        if (index.equals("1") && current_trun.equals(standard)) {

            matrix[0][2] = current_trun;
            three.setText(current_trun);
            current_user_data.setButton(3);
            current_user_data.setRoom_members(2);
            databaseReference.child(room_id).child(room_id).setValue(current_user_data);
            trun_changer();
            if (win_check()) {
                winner();
            }
            count++;

            truner = true;
            sound();


        } else if (index.equals("2")) {

            matrix[0][2] = oponent_data.getTag();
            three.setText(oponent_data.getTag());
            if (truner) {
                trun_changer();
            }
            if (win_check()) {
                winner();
            }
            count++;

            truner = true;
            sound();

        }

    }

    private void btn4(String index) {
        if (index.equals("1") && current_trun.equals(standard)) {

            matrix[1][0] = current_trun;
            four.setText(current_trun);
            current_user_data.setButton(4);
            current_user_data.setRoom_members(2);
            databaseReference.child(room_id).child(room_id).setValue(current_user_data);
            trun_changer();
            if (win_check()) {
                winner();
            }
            count++;

            truner = true;
            sound();

        } else if (index.equals("2")) {

            matrix[1][0] = oponent_data.getTag();
            four.setText(oponent_data.getTag());
            if (truner) {
                trun_changer();
            }
            if (win_check()) {
                winner();
            }
            count++;

            truner = true;
            sound();

        }

    }

    private void btn5(String index) {
        if (index.equals("1") && current_trun.equals(standard)) {

            matrix[1][1] = current_trun;
            five.setText(current_trun);
            current_user_data.setButton(5);
            current_user_data.setRoom_members(2);
            databaseReference.child(room_id).child(room_id).setValue(current_user_data);
            trun_changer();
            if (win_check()) {
                winner();
            }
            count++;

            truner = true;
            sound();


        } else if (index.equals("2")) {

            matrix[1][1] = oponent_data.getTag();
            five.setText(oponent_data.getTag());
            if (truner) {
                trun_changer();
            }
            if (win_check()) {
                winner();
            }
            count++;

            truner = true;
            sound();

        }

    }

    private void btn6(String index) {
        if (index.equals("1") && current_trun.equals(standard)) {

            matrix[1][2] = current_trun;
            six.setText(current_trun);
            current_user_data.setButton(6);
            current_user_data.setRoom_members(2);
            databaseReference.child(room_id).child(room_id).setValue(current_user_data);
            trun_changer();
            if (win_check()) {
                winner();
            }
            count++;

            truner = true;
            sound();


        } else if (index.equals("2")) {

            matrix[1][2] = oponent_data.getTag();
            six.setText(oponent_data.getTag());
            if (truner) {
                trun_changer();
            }
            if (win_check()) {
                winner();
            }
            count++;

            truner = true;
            sound();

        }

    }

    private void btn7(String index) {

        if (index.equals("1") && current_trun.equals(standard)) {

            matrix[2][0] = current_trun;
            seven.setText(current_trun);
            current_user_data.setButton(7);
            current_user_data.setRoom_members(2);
            databaseReference.child(room_id).child(room_id).setValue(current_user_data);
            trun_changer();
            if (win_check()) {
                winner();
            }
            count++;

            truner = true;
            sound();


        } else if (index.equals("2")) {

            matrix[2][0] = oponent_data.getTag();
            seven.setText(oponent_data.getTag());
            if (truner) {
                trun_changer();
            }
            if (win_check()) {
                winner();
            }
            count++;
            truner = true;
            sound();

        }

    }

    private void btn8(String index) {

        if (index.equals("1") && current_trun.equals(standard)) {

            matrix[2][1] = current_trun;
            eight.setText(current_trun);
            current_user_data.setButton(8);
            current_user_data.setRoom_members(2);
            databaseReference.child(room_id).child(room_id).setValue(current_user_data);
            trun_changer();
            if (win_check()) {
                winner();
            }
            count++;

            truner = true;
            sound();


        } else if (index.equals("2")) {

            matrix[2][1] = oponent_data.getTag();
            eight.setText(oponent_data.getTag());
            if (truner) {
                trun_changer();
            }
            if (win_check()) {
                winner();
            }
            count++;
            truner = true;
            sound();

        }


    }

    private void btn9(String index) {
        if (index.equals("1") && current_trun.equals(standard)) {

            matrix[2][2] = current_trun;
            nine.setText(current_trun);
            current_user_data.setButton(9);
            current_user_data.setRoom_members(2);
            databaseReference.child(room_id).child(room_id).setValue(current_user_data);
            trun_changer();
            if (win_check()) {
                winner();
            }
            count++;
            truner = true;
            sound();

        } else if (index.equals("2")) {

            matrix[2][2] = oponent_data.getTag();
            nine.setText(oponent_data.getTag());
            if (truner) {
                trun_changer();
            }
            if (win_check()) {
                winner();
            }
            count++;
            truner = true;
            sound();

        }

    }


    private void winner() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String Winner;
                if (current_trun.equals(standard)) {
                    Winner = "You lose";
                } else {
                    Winner = "You Won";
                    current_user_data.setScore(Integer.toString(Integer.parseInt(current_user_data.getScore()) + 1));
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
                one.setText(null);
                two.setText(null);
                three.setText(null);
                four.setText(null);
                five.setText(null);
                six.setText(null);
                seven.setText(null);
                eight.setText(null);
                nine.setText(null);
                lose_win_pop(Winner);
            }
        },800);


    }

    public String getAlphaNumericString(int n) {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());

            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }

    void trun_changer() {
        if (current_trun.equals("O")) {
            current_trun = "X";
        } else {
            current_trun = "O";
        }
        System.out.println(current_trun);

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
        one.setText(null);
        two.setText(null);
        three.setText(null);
        four.setText(null);
        five.setText(null);
        six.setText(null);
        seven.setText(null);
        eight.setText(null);
        nine.setText(null);
        lose_win_pop(Winner);
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


    void finish1() {
        finish();
    }

    private void popup(String s) {
        DisplayMetrics displayManager = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayManager);
        int Width = displayManager.widthPixels;
        dialog = new Dialog(playing.this);
        dialog.setContentView(R.layout.dialog_box);
        dialog.getWindow().setLayout((int) (Width * 0.9), ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.pop_up_background));
        dialog.setCancelable(false);
        button = dialog.findViewById(R.id.button);
        textView = dialog.findViewById(R.id.textView1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish1();
            }
        });
        textView.setText(s);
        dialog.show();
    }

    private void lose_win_pop(String s) {
        DisplayMetrics displayManager = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayManager);
        int Width = displayManager.widthPixels;
        int height = displayManager.heightPixels;
        dialog2 = new Dialog(playing.this);
        dialog2.setContentView(R.layout.activity_pop_up);
        dialog2.getWindow().setLayout((int) (Width * 0.9), (int) (height * 0.3));
        dialog2.getWindow().setBackgroundDrawable(getDrawable(R.drawable.pop_up_background));
        dialog2.setCancelable(false);
        menu11 = dialog2.findViewById(R.id.menu);
        again = dialog2.findViewById(R.id.again);
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            back();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void back() {
        DisplayMetrics displayManager = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayManager);
        int Width = displayManager.widthPixels;
        int height = displayManager.heightPixels;
        dialog2 = new Dialog(playing.this);
        dialog2.setContentView(R.layout.activity_pop_up);
        dialog2.getWindow().setLayout((int) (Width * 0.9), (int) (height * 0.2));
        dialog2.getWindow().setBackgroundDrawable(getDrawable(R.drawable.pop_up_background));
        dialog2.setCancelable(false);
        menu11 = dialog2.findViewById(R.id.menu);
        again = dialog2.findViewById(R.id.again);
        again.setText("Continue");
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

            }
        });
        textView1.setText("Are you Sure You want to Quit");
        textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        dialog2.show();
    }


}


































