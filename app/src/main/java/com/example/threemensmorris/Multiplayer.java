package com.meghana.mythreemensmorris;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Multiplayer extends AppCompatActivity {
    private Button[] buttons;
    Button restart;
    private int PLAYER;
    private Game g;
    private int p1_count=0;
    private int p2_count=0;
    private TextView tv;
    private TextView sc1;
    private TextView sc2;
    private Player player1;
    private Player player2;
    private int prev_x;
    private int prev_y;
    private int click_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overall_layout);
        prev_x = -1;
        prev_y = -1;
        click_count = 0;
        PLAYER = 0;
        g = new Game();
        tv = findViewById(R.id.top_text);
        sc1 = findViewById(R.id.player1_score);
        sc2 = findViewById(R.id.player2_score);
        tv.setText("PLAYER 1'S TURN");
        restart = (Button) findViewById(R.id.play_again);
        Button b00 = (Button) findViewById(R.id.blank_circle00);
        Button b01 = (Button) findViewById(R.id.blank_circle01);
        Button b02 = (Button) findViewById(R.id.blank_circle02);

        Button b10 = (Button) findViewById(R.id.blank_circle10);
        Button b11 = (Button) findViewById(R.id.blank_circle11);
        Button b12 = (Button) findViewById(R.id.blank_circle12);

        Button b20 = (Button) findViewById(R.id.blank_circle20);
        Button b21 = (Button) findViewById(R.id.blank_circle21);
        Button b22 = (Button) findViewById(R.id.blank_circle22);

        buttons = new Button[9];//{b00,b01,b02, b10,b11,b12, b20,b21,b22};
        buttons[0] = b00;
        buttons[1] = b01;
        buttons[2] = b02;
        buttons[3] = b10;
        buttons[4] = b11;
        buttons[5] = b12;
        buttons[6] = b20;
        buttons[7] = b21;
        buttons[8] = b22;

        String name1 = "p1";//default values, but make sure that they are dynamically updated with the layout
        String name2 = "p2";
        g.players = new Player[2];
        player1 = new Player();
        player1.setInformation(name1, "X", 1);
        player2 = new Player();
        player2.setInformation(name2, "O", 2);
        g.players[0] = player1;
        g.players[1] = player2;


        //g.makeMoveMain(player1,-1,-1,0,0);
        //buttons[0].setOnClickListener()
    }
    public void restartGame(View v) {
        String name1 = "p1";//default values, but make sure that they are dynamically updated with the layout
        String name2 = "p2";
        prev_x = -1;
        prev_y = -1;
        click_count = 0;
        PLAYER = 0;
        g = new Game();
        g.players = new Player[2];
        player1 = new Player();
        player1.setInformation(name1, "X", 1);
        player2 = new Player();
        player2.setInformation(name2, "O", 2);
        g.players[0] = player1;
        g.players[1] = player2;
        tv.setText("PLAYER 1'S TURN");
        update_board();

    }
    public void button00click(View v) {
        handle_move(0,0);
    }
    public void button01click(View v) {
        handle_move(1,0);
    }
    public void button02click(View v) {
        handle_move(2,0);
    }
    public void button10click(View v) {
        handle_move(0,1);
    }
    public void button11click(View v) {
        handle_move(1,1);
    }
    public void button12click(View v) {
        handle_move(2,1);
    }
    public void button20click(View v) {
        handle_move(0,2);
    }
    public void button21click(View v) {
        handle_move(1,2);
    }
    public void button22click(View v) {
        handle_move(2,2);
    }
    public void handle_move(int x, int y) {
        click_count++;
        if(g.haswinner) {
            tv.setText("THERES A WINNER");
            if(g.checkWinner(player1)) {
                p1_count++;
                String s = "Score: "+p1_count;
                sc1.setText(s);
            }
            else if(g.checkWinner(player2)) {
                p2_count++;
                String s = "Score: "+p2_count;
                sc2.setText(s);
            }
            return;
        }
        if(PLAYER == 0 && player1.numOfPieces < 3) {
            tv.setText("PLAYER 2'S TURN");
            //make it red and change player
            click_count = 0;
            PLAYER = 1;
            boolean b = g.makeMoveMain(player1,-1,-1,x,y);
            if(!b) {
                PLAYER = 0;
                tv.setText("PLAYER1 TRY AGAIN");
            }
        }
        else if(PLAYER == 1 && player2.numOfPieces < 3) { //3 pieces and need to increase click count
            tv.setText("PLAYER 1'S TURN");
            click_count = 0;
            PLAYER = 0;
            boolean b = g.makeMoveMain(player2,-1,-1,x,y);
            if(!b) {
                PLAYER = 1;
                tv.setText("PLAYER2 TRY AGAIN");
            }
        }
        //cases for 3 and click count 2
        else if(click_count==2 && PLAYER == 0) {
            tv.setText("PLAYER 2'S TURN");
            click_count=0;
            boolean b = g.makeMoveMain(player1,prev_x,prev_y,x,y);
            PLAYER = 1;
            prev_x = -1;
            prev_y = -1;
            if(!b) {
                PLAYER = 0;
                tv.setText("PLAYER1 TRY AGAIN");
            }
        }
        else if(click_count==2 && PLAYER == 1) {
            tv.setText("PLAYER 1'S TURN");
            click_count=0;
            boolean b = g.makeMoveMain(player2,prev_x,prev_y,x,y);
            PLAYER = 0;
            prev_x = -1;
            prev_y = -1;
            if(!b) {
                PLAYER = 1;
                tv.setText("PLAYER2 TRY AGAIN");
            }
        }
        else {
            if(PLAYER == 0) {
                tv.setText("SELECT DEST PLAYER1");
            }
            else {
                tv.setText("SELECT DEST PLAYER2");
            }
            prev_x = x;
            prev_y = y;
        }
        update_board();
        g.display();
        if(g.haswinner) {
            tv.setText("THERES A WINNER");
            if(g.checkWinner(player1)) {
                p1_count++;
                String s = "Score: "+p1_count;
                sc1.setText(s);
            }
            else if(g.checkWinner(player2)) {
                p2_count++;
                String s = "Score: "+p2_count;
                sc2.setText(s);
            }
            return;
        }

    }
    public void update_board() {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                int index = i*3+j;
                if (g.gameBoard[i][j] == 1) {//player1
                    buttons[index].setBackground(getResources().getDrawable(R.drawable.player1_piece));
                }
                else if(g.gameBoard[i][j] == 2) {
                    buttons[index].setBackground(getResources().getDrawable(R.drawable.player2_piece));
                }
                else {
                    buttons[index].setBackground(getResources().getDrawable(R.drawable.blank_circle));
                }
            }
        }
    }

}