package com.example.rspapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;


public class MainActivity extends AppCompatActivity {
    Button b_rock, b_scissor, b_paper;
    TextView tv_score, tv_human_choice, tv_cpu_choice;
    ImageView iv_CpuChoice, iv_HumanChoice;
    Integer human_score, cpu_score;


    /**
     * Basic Overrided onCreate() function.
     * super.onCreate() get's called.
     * All variables that represent the layout's components,
     * are set and linked with them ( findViewById() ).
     * Also, an editor is set, so that the cpu's and human's choices,
     * are saved every time, in order to display them
     * in both portait and landcape mode, and ensure no data is lost.
     * Finally, Score's text view is set, as well as the listeners of the buttons.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final SharedPreferences settings = getSharedPreferences("GAME_DATA", MODE_PRIVATE);
        final SharedPreferences.Editor editor = settings.edit();

        iv_CpuChoice = (ImageView) findViewById(R.id.iv_CpuChoice);
        iv_HumanChoice = (ImageView) findViewById(R.id.iv_HumanChoice);

        human_score = settings.getInt("HUMAN_SCORE",0);
        cpu_score = settings.getInt("CPU_SCORE",0);
        if(human_score == null && cpu_score == null){
            human_score=0;
            cpu_score = 0;
        }

        b_paper = (Button) findViewById(R.id.paperButton);
        b_rock = (Button) findViewById(R.id.RockButton);
        b_scissor = (Button) findViewById(R.id.scissorsButton);

        tv_score = (TextView) findViewById(R.id.Score);
        tv_human_choice = (TextView) findViewById(R.id.tv_human_choice);
        tv_cpu_choice = (TextView) findViewById(R.id.tv_cpu_choice);

        human_score = settings.getInt("HUMAN_SCORE",0);
        cpu_score = settings.getInt("CPU_SCORE",0);
        if(human_score == null && cpu_score == null){
            human_score=0;
            cpu_score = 0;
        }
        tv_score.setText("Εσύ: "+human_score+"    "+"Αντίπαλος: "+cpu_score);

        tv_human_choice.setText(settings.getString("TV_HUMAN_CHOICE", " "));
        if(tv_human_choice.getText().toString().equals("Πέτρα")){
            iv_HumanChoice.setImageResource(R.drawable.rock);
        }
        else if(tv_human_choice.getText().toString().equals("Ψαλίδι")){
            iv_HumanChoice.setImageResource(R.drawable.scissors);
        }
        else if(tv_human_choice.getText().toString().equals("Χαρτί")){
            iv_HumanChoice.setImageResource(R.drawable.paper);
        }
        else{
            iv_HumanChoice.setImageResource(R.drawable.blank);
        }

        tv_cpu_choice.setText(settings.getString("TV_CPU_CHOICE", " "));
        if(tv_cpu_choice.getText().toString().equals("Πέτρα")){
            iv_CpuChoice.setImageResource(R.drawable.rock);
        }
        else if(tv_cpu_choice.getText().toString().equals("Ψαλίδι")){
            iv_CpuChoice.setImageResource(R.drawable.scissors);
        }
        else if(tv_cpu_choice.getText().toString().equals("Χαρτί")){
            iv_CpuChoice.setImageResource(R.drawable.paper);
        }
        else{
            iv_CpuChoice.setImageResource(R.drawable.blank);
        }

        /**
         * Listener of the Paper button is set.
         * Once the button is pressed by the human,
         * the image view which represents what human selected, is set with
         * the paper image, and the textview which represents what human chose,
         * is set with the string:"Χαρτί"
         * Also, a Toast notification is displayed,
         * in order to inform the human, about the last turn
         * (if he won, lost, or it was a tie).
         * In addition, both human's and cpu's choice is saved.
         * Finally, both human and cpu scores are saved.
         */
        b_paper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_HumanChoice.setImageResource(R.drawable.paper);
                tv_human_choice.setText("Χαρτί");
                String result = play_turn("paper");
                Toast.makeText(MainActivity.this,result,Toast.LENGTH_SHORT).show();
                tv_score.setText("Εσύ: "+ Integer.toString(human_score)+"   Αντίπαλος: "+ Integer.toString(cpu_score));
                editor.putInt("HUMAN_SCORE", human_score);
                editor.putInt("CPU_SCORE", cpu_score);
                editor.putString("TV_HUMAN_CHOICE", tv_human_choice.getText().toString());
                editor.putString("TV_CPU_CHOICE", tv_cpu_choice.getText().toString());
                editor.commit();
            }
        });
        /**
         * Listener of the Rock button is set.
         * Once the button is pressed by the human,
         * the image view which represents what human selected, is set with
         * the rock image, and the textview which represents what human chose,
         * is set with the string:"Πέτρα"
         * Also, a Toast notification is displayed,
         * in order to inform the human, about the last turn
         * (if he won, lost, or it was a tie).
         * In addition, both human's and cpu's choice is saved.
         * Finally, both human and cpu scores are saved.
         */
        b_rock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_HumanChoice.setImageResource(R.drawable.rock);
                tv_human_choice.setText("Πέτρα");
                String result = play_turn("rock");
                Toast.makeText(MainActivity.this,result,Toast.LENGTH_SHORT).show();
                tv_score.setText("Εσύ: "+ Integer.toString(human_score)+"   Αντίπαλος: "+ Integer.toString(cpu_score));
                editor.putInt("HUMAN_SCORE", human_score);
                editor.putInt("CPU_SCORE", cpu_score);
                editor.putString("TV_HUMAN_CHOICE", tv_human_choice.getText().toString());
                editor.putString("TV_CPU_CHOICE", tv_cpu_choice.getText().toString());
                editor.commit();
            }
        });
        /**
         * Listener of the Scissor button is set.
         * Once the button is pressed by the human,
         * the image view which represents what human selected, is set with
         * the scissor image, and the textview which represents what human chose,
         * is set with the string:"Ψαλίδι"
         * Also, a Toast notification is displayed,
         * in order to inform the human, about the last turn
         * (if he won, lost, or it was a tie).
         * In addition, both human's and cpu's choice is saved.
         * Finally, both human and cpu scores are saved.
         */
        b_scissor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_HumanChoice.setImageResource(R.drawable.scissors);
                tv_human_choice.setText("Ψαλίδι");
                String result = play_turn("scissor");
                Toast.makeText(MainActivity.this,result,Toast.LENGTH_SHORT).show();
                tv_score.setText("Εσύ: "+ Integer.toString(human_score)+"   Αντίπαλος: "+ Integer.toString(cpu_score));
                editor.putInt("HUMAN_SCORE", human_score);
                editor.putInt("CPU_SCORE", cpu_score);
                editor.putString("TV_HUMAN_CHOICE", tv_human_choice.getText().toString());
                editor.putString("TV_CPU_CHOICE", tv_cpu_choice.getText().toString());
                editor.commit();
            }
        });
    }


    /**
     * First of all, the random cpu's choice is determined.
     * If random x == 1, then cpu's choice = "Rock",
     * else if random x == 2, then cpu's choice + "Scissor"
     * else (x == 3), then cpu's choice = "Paper".
     * After cpu's choice definition, based on human's choice,
     * cpu's choice, and the game's rules, the turn's winner is declared,
     * and he get's one more point.
     * @param human_choice, the choice that human made, according to which button he tapped
     * @return String ( Νίκη! / Ισοπαλία! / Ήττα! ) , which is actually the turn's result
     */
    private String play_turn(String human_choice){
        String cpu_choice = new String();
        Random r = new Random();
        int x = r.nextInt(3)+1;
        if(x==1){
            cpu_choice= "rock";
            tv_cpu_choice.setText("Πέτρα");
            iv_CpuChoice.setImageResource(R.drawable.rock);
        }
        else if(x==2){
            cpu_choice = "scissor";
            tv_cpu_choice.setText("Ψαλίδι");
            iv_CpuChoice.setImageResource(R.drawable.scissors);
        }
        else{
            cpu_choice = "paper";
            tv_cpu_choice.setText("Χαρτί");
            iv_CpuChoice.setImageResource(R.drawable.paper);
        }
        if (human_choice.equals(cpu_choice)){
            return "Ισοπαλία!";
        }
        else if(human_choice.equals("rock") && cpu_choice.equals("scissor")){
            human_score+=1;
            return "Νίκη!";

        }
        else if(cpu_choice.equals("rock") && human_choice.equals("scissor")){
            cpu_score+=1;
            return "Ήττα!";
        }
        else if(human_choice.equals("rock") && cpu_choice.equals("paper")){
            cpu_score+=1;
            return "Ήττα!";
        }
        else if(cpu_choice.equals("rock") && human_choice.equals("paper")){
            human_score+=1;
            return "Νίκη!";
        }
        else if(human_choice.equals("scissor") && cpu_choice.equals("paper")){
            human_score+=1;
            return "Νίκη!";
        }
        else{
            cpu_score+=1;
            return "Ήττα!";
        }
    }


}