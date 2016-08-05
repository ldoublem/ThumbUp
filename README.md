# ThumbUp
A nice thumb up control

点赞还可以这么玩，原产地[dribbble](https://dribbble.com/shots/2661577-Like-Unlike-microinteraction-for-Loliful-io)
---
#Preview
![gif](https://github.com/ldoublem/ThumbUp/blob/master/screenShot/shot.gif)

##Gradle
```
compile 'com.ldoublem.thumbUplib:ThumbUplib:0.1'
```

##Usage  xml
```
   <com.ldoublem.thumbUplib.ThumbUpView
            android:id="@+id/tpv"
            android:layout_width="50dp"
            android:layout_height="50dp"
            app:cracksColor="#33475f"
            app:edgeColor="#9d55b8"
            app:fillColor="#ea8010"
            app:unlikeType="1"
            />
```
##java
```
        mThumbUpView.setUnLikeType(ThumbUpView.LikeType.broken);
        mThumbUpView.setCracksColor(Color.rgb(22, 33, 44));
        mThumbUpView.setFillColor(Color.rgb(11, 200, 77));
        mThumbUpView.setEdgeColor(Color.rgb(33, 3, 219));
        mThumbUpView.setOnThumbUp(new ThumbUpView.OnThumbUp() {
            @Override
            public void like(boolean like) {
            }
        });
        mThumbUpView.Like();
        mThumbUpView.UnLike();
```
## About me

An android developer in Hangzhou.

If you want to make friends with me, You can email to me.
my [email](mailto:1227102260@qq.com) :smiley:


License
=======

    The MIT License (MIT)

	Copyright (c) 2016 ldoublem

	Permission is hereby granted, free of charge, to any person obtaining a copy
	of this software and associated documentation files (the "Software"), to deal
	in the Software without restriction, including without limitation the rights
	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
	copies of the Software, and to permit persons to whom the Software is
	furnished to do so, subject to the following conditions:

	The above copyright notice and this permission notice shall be included in all
	copies or substantial portions of the Software.

	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
	SOFTWARE.



