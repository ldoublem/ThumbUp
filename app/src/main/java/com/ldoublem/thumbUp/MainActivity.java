package com.ldoublem.thumbUp;

import android.graphics.Color;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ldoublem.thumbUplib.ThumbUpView;

public class MainActivity extends AppCompatActivity {


    ThumbUpView tpv1, tpv2, tpv3;
    TextView tv1, tv2, tv3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        String s = "";
//        for (long i = 8820501; i <= 8821000; i++) {
//            s = s + String.valueOf(i) + ",";
//
//        }
//        System.out.println("---" + s);

        tpv1 = (ThumbUpView) findViewById(R.id.tpv1);
        tpv2 = (ThumbUpView) findViewById(R.id.tpv2);
        tpv3 = (ThumbUpView) findViewById(R.id.tpv3);
        tpv1.setLike();
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);


        tpv3.setUnLikeType(ThumbUpView.LikeType.broken);
        tpv3.setCracksColor(Color.WHITE);
        tpv3.setFillColor(Color.rgb(11, 200, 77));
        tpv3.setEdgeColor(Color.rgb(33, 3, 219));
//        tpv3.setBgColor(Color.RED);
        tpv3.setOnThumbUp(new ThumbUpView.OnThumbUp() {
            @Override
            public void like(boolean like) {
                if (like) {
                    tv3.setText(String.valueOf(Integer.valueOf(tv3.getText().toString()) + 1));
                } else {

                    tv3.setText(String.valueOf(Integer.valueOf(tv3.getText().toString()) - 1));

                }
            }
        });

        tpv2.setOnThumbUp(new ThumbUpView.OnThumbUp() {
            @Override
            public void like(boolean like) {
                if (like) {
                    tv2.setText(String.valueOf(Integer.valueOf(tv2.getText().toString()) + 1));
                } else {

                    tv2.setText(String.valueOf(Integer.valueOf(tv2.getText().toString()) - 1));

                }
            }
        });
        tpv1.setOnThumbUp(new ThumbUpView.OnThumbUp() {
            @Override
            public void like(boolean like) {
                if (like) {
                    tv1.setText(String.valueOf(Integer.valueOf(tv1.getText().toString()) + 1));
                } else {
                    tv1.setText(String.valueOf(Integer.valueOf(tv1.getText().toString()) - 1));

                }
            }
        });

    }

    public void like(View v) {
        tpv1.Like();
        tpv2.Like();
        tpv3.Like();

    }

    public void unlike(View v) {
        tpv1.UnLike();
        tpv2.UnLike();
        tpv3.UnLike();
    }


}
