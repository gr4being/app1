package com.gruppe4.app1;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.core.app.ActivityCompat;

import com.aldebaran.qi.Future;
import com.aldebaran.qi.sdk.QiContext;
import com.aldebaran.qi.sdk.QiSDK;
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks;
import com.aldebaran.qi.sdk.builder.AnimateBuilder;
import com.aldebaran.qi.sdk.builder.AnimationBuilder;
import com.aldebaran.qi.sdk.builder.EnforceTabletReachabilityBuilder;
import com.aldebaran.qi.sdk.builder.QiChatbotBuilder;
import com.aldebaran.qi.sdk.builder.SayBuilder;
import com.aldebaran.qi.sdk.builder.TopicBuilder;
import com.aldebaran.qi.sdk.design.activity.RobotActivity;
import com.aldebaran.qi.sdk.object.actuation.Animate;
import com.aldebaran.qi.sdk.object.actuation.Animation;
import com.aldebaran.qi.sdk.object.actuation.EnforceTabletReachability;
import com.aldebaran.qi.sdk.object.conversation.QiChatbot;
import com.aldebaran.qi.sdk.object.conversation.Say;
import com.aldebaran.qi.sdk.object.conversation.Topic;
import com.aldebaran.qi.sdk.object.locale.Language;
import com.aldebaran.qi.sdk.object.locale.Locale;
import com.aldebaran.qi.sdk.object.locale.Region;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;

public class MainActivity extends RobotActivity implements RobotLifecycleCallbacks {

    private Activity mainActivity;
    private QiContext qiContext;
    private Animate animate;


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

    public void renameButtons(Hashtable<Integer, String> question_dict){
        Log.i("aa", "about to run the while loop");
        Enumeration<Integer> e = question_dict.keys();
        while (e.hasMoreElements()) {
            int key = e.nextElement();
            Log.i("a", ": " + key + question_dict.get(key));
        }
        Button btn_1 = (Button)findViewById(R.id.btn_question_1);
        btn_1.setText(question_dict.get(1));
        Button btn_2 = (Button)findViewById(R.id.btn_question_2);
        btn_2.setText(question_dict.get(2));
        Button btn_3 = (Button)findViewById(R.id.btn_question_3);
        btn_3.setText(question_dict.get(3));
        Button btn_4 = (Button)findViewById(R.id.btn_question_4);
        btn_4.setText(question_dict.get(4));
        Button btn_5 = (Button)findViewById(R.id.btn_question_5);
        btn_5.setText(question_dict.get(5));
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QiSDK.register(this, this);
        mainActivity=this;

        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);

        Hashtable<Integer, String> question_dict = new Hashtable<Integer, String>();

        question_dict = createHasmap("wo ist mein geld!?!?!?", "can you yeet yourself",
                "git mir mein geld zurueck", "eaaat shit", "wanna fight?");

        renameButtons(question_dict);

    }


    protected void onDestroy() {
        QiSDK.unregister(this, this);
        super.onDestroy();
    }

    public void onRobotFocusGained(QiContext qiContext) {
        Locale locale = new Locale(Language.GERMAN, Region.GERMANY);

        Say sayActionFuture = SayBuilder.with(qiContext)
                .withText("Hallo! Gruppe 4.")
                .withLocale(locale)
                .build();
        sayActionFuture.run();

        Animation animation2 = AnimationBuilder.with(qiContext)
                .withResources(R.raw.kratzen)
                .build();
        animate = AnimateBuilder.with(qiContext)
                .withAnimation(animation2)
                .build();
        animate.addOnStartedListener(() -> Log.i("animation hype",
                "Animation started."));

        Button test_button = (Button) findViewById(R.id.btn_test);
        test_button.setOnClickListener(new View.OnClickListener() {
            public void onClick (View view) {
                Log.i("aa", "button clicked");
                Future<Void> animateFuture = animate.async().run();
            }
        });
        Topic topic = TopicBuilder.with(qiContext)
                .withResource(R.raw.test).build();
        QiChatbot qiChatbot = QiChatbotBuilder.with(qiContext)
                .withTopic(topic)
                .build();

    }

    public void onRobotFocusLost() {
        // The robot focus is lost.
    }

    public void onRobotFocusRefused(String reason) {
        // The robot focus is refused.
    }
}

