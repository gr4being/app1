package com.gruppe4.app1;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.aldebaran.qi.Future;
import com.aldebaran.qi.sdk.QiContext;
import com.aldebaran.qi.sdk.QiSDK;
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks;
import com.aldebaran.qi.sdk.builder.AnimateBuilder;
import com.aldebaran.qi.sdk.builder.AnimationBuilder;
import com.aldebaran.qi.sdk.builder.ChatBuilder;
import com.aldebaran.qi.sdk.builder.EnforceTabletReachabilityBuilder;
import com.aldebaran.qi.sdk.builder.ListenBuilder;
import com.aldebaran.qi.sdk.builder.PhraseSetBuilder;
import com.aldebaran.qi.sdk.builder.QiChatbotBuilder;
import com.aldebaran.qi.sdk.builder.SayBuilder;
import com.aldebaran.qi.sdk.builder.TopicBuilder;
import com.aldebaran.qi.sdk.design.activity.RobotActivity;
import com.aldebaran.qi.sdk.object.actuation.Animate;
import com.aldebaran.qi.sdk.object.actuation.Animation;
import com.aldebaran.qi.sdk.object.actuation.EnforceTabletReachability;
import com.aldebaran.qi.sdk.object.conversation.Chat;
import com.aldebaran.qi.sdk.object.conversation.Listen;
import com.aldebaran.qi.sdk.object.conversation.ListenResult;
import com.aldebaran.qi.sdk.object.conversation.Phrase;
import com.aldebaran.qi.sdk.object.conversation.PhraseSet;
import com.aldebaran.qi.sdk.object.conversation.QiChatbot;
import com.aldebaran.qi.sdk.object.conversation.Say;
import com.aldebaran.qi.sdk.object.conversation.Topic;
import com.aldebaran.qi.sdk.object.locale.Language;
import com.aldebaran.qi.sdk.object.locale.Locale;
import com.aldebaran.qi.sdk.object.locale.Region;

import java.lang.reflect.Array;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;

public class MainActivity extends RobotActivity implements RobotLifecycleCallbacks {

    private Activity mainActivity;
    private QiContext qiContext;
    private Animate animate;
    private String question1;
    private String question2;
    private String question3;
    private String question4;
    private String question5;
    public String[] a_ar = new String[]{};

    private Chat chat;


    private Hashtable<Integer, String> createHasmap(String question1, String question2,
                                 String question3, String question4, String question5){
        Hashtable<Integer, String> question_dict = new Hashtable<Integer, String>();

        question_dict.put(1, question1);
        question_dict.put(2, question2);
        question_dict.put(3, question3);
        question_dict.put(4, question4);
        question_dict.put(5, question5);

        return question_dict;
    }

    private void changeText(String text){
        TextView textView  = findViewById(R.id.mytextview_id);
        textView.setText(text);
    }

    private void arrayThing(String[] ar){
        question1 = ar[1];
        question2 = ar[2];
        question3 = ar[3];
        question4 = ar[4];
        question5 = ar[5];
    }

    public void renameButtons(String[] question_dict){
        Log.i("aa", "about to run the while loop");
        //Enumeration<Integer> e = question_dict.keys();
        //while (e.hasMoreElements()) {
        //    int key = e.nextElement();
        //    Log.i("a", ": " + key + question_dict.get(key));
        //}
        Button btn_1 = (Button)findViewById(R.id.btn_question_1);
        btn_1.setText(question_dict[0]);
        Button btn_2 = (Button)findViewById(R.id.btn_question_2);
        btn_2.setText(question_dict[1]);
        Button btn_3 = (Button)findViewById(R.id.btn_question_3);
        btn_3.setText(question_dict[2]);
        Button btn_4 = (Button)findViewById(R.id.btn_question_4);
        btn_4.setText(question_dict[3]);
        Button btn_5 = (Button)findViewById(R.id.btn_question_5);
        btn_5.setText(question_dict[4]);
    }

    private void sayText(QiContext qiContext, String text){
        Locale locale = new Locale(Language.GERMAN, Region.GERMANY);
        Phrase phrase = new Phrase(text);
        Future<Say> sayBuilding = SayBuilder.with(qiContext)
                .withPhrase(phrase)
                .withLocale(locale)
                .buildAsync();
        Future<Void> sayActionFuture = sayBuilding.andThenCompose(say -> say.async().run());
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QiSDK.register(this, this);
        mainActivity=this;

        String[] ar = new String[]{"a", "b", "c", "d", "e"};
        a_ar = new String[]{"no", "yes", "etc", "idgaf", "sure m8"};
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);

        Hashtable<Integer, String> question_dict = new Hashtable<Integer, String>();

        question_dict = createHasmap("wo ist mein geld!?!?!?", "can you yeet yourself",
                "git mir mein geld zurueck", "eaaat shit", "wanna fight?");

        renameButtons(ar);
    }

    protected void onDestroy() {
        QiSDK.unregister(this, this);
        super.onDestroy();
    }

    public void onRobotFocusGained(QiContext qiContext) {
        Locale locale = new Locale(Language.GERMAN, Region.GERMANY);

        sayText(qiContext, "Hallo, Gruppe 4");


        Animation animation = AnimationBuilder.with(qiContext)
                .withResources(R.raw.stand3)
                .build();
        animate = AnimateBuilder.with(qiContext)
                .withAnimation(animation)
                .build();
        animate.addOnStartedListener(() -> Log.i("animation hype", "Animation started."));

        Button test_button = (Button) findViewById(R.id.btn_test);
        test_button.setOnClickListener(new View.OnClickListener() {
            public void onClick (View view) {
                Log.i("aa", "button clicked");
                Future<Void> animateFuture = animate.async().run();
                View a = findViewById(R.id.btn_question_1);
                a.setVisibility(View.VISIBLE);
                View b = findViewById(R.id.btn_question_2);
                b.setVisibility(View.VISIBLE);
                View c = findViewById(R.id.btn_question_3);
                c.setVisibility(View.VISIBLE);
                View d = findViewById(R.id.btn_question_4);
                d.setVisibility(View.VISIBLE);
                View e = findViewById(R.id.btn_question_5);
                e.setVisibility(View.VISIBLE);
            }
        });
        Button question_button_1 = (Button) findViewById(R.id.btn_question_1);
        question_button_1.setOnClickListener(new View.OnClickListener() {
            public void onClick (View view) {
                Log.i("aa", "button question 1 clicked");
                sayText(qiContext, a_ar[0]);
                changeText(a_ar[0]);
            }
        });

        // ---------------
        // chat code
        // ---------------

        PhraseSet phraseSet = PhraseSetBuilder.with(qiContext)
                .withTexts("Hello")
                .build();

        // Build the action.
        Listen listen = ListenBuilder.with(qiContext)
                .withPhraseSet(phraseSet)
                .build();

        // Run the action synchronously.
        ListenResult listenResult = listen.run();
        //((TextView)findViewById(R.id.mytextview_id)).setText(listenResult.getHeardPhrase().getText());
        changeText(listenResult.getHeardPhrase().getText());
        Log.i("aa", "Heard phrase: " + listenResult.getHeardPhrase().getText());

        //View b = findViewById(R.id.btn_test);
        //b.setVisibility(View.INVISIBLE);

    }

    public void onRobotFocusLost() {
        // The robot focus is lost.
        if (chat != null) {
            chat.removeAllOnStartedListeners();
        }
    }

    public void onRobotFocusRefused(String reason) {
        // The robot focus is refused.
    }
}

