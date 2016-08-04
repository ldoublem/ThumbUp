package com.ldoublem.thumbUp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.ldoublem.thumbUplib.ThumbUpView;

public class MainActivity extends AppCompatActivity {


    ThumbUpView tpv1, tpv2, tpv3;
    TextView tv1, tv2, tv3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tpv1 = (ThumbUpView) findViewById(R.id.tpv1);
        tpv2 = (ThumbUpView) findViewById(R.id.tpv2);
        tpv3 = (ThumbUpView) findViewById(R.id.tpv3);

        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);


        tpv3.setLikeType(ThumbUpView.LikeType.broken);
        tpv3.setCracksColor(Color.rgb(22, 33, 44));
        tpv3.setFillColor(Color.rgb(11, 200, 77));
        tpv3.setEdgeColor(Color.rgb(33, 3, 219));
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
}
