package com.samuel.wechat.wechattest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXImageObject;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXTextObject;
import com.tencent.mm.sdk.openapi.WXVideoObject;
import com.tencent.mm.sdk.openapi.WXWebpageObject;
import com.tencent.mm.sdk.platformtools.Util;

public class MainActivity extends AppCompatActivity {

    private final String AppID = "wxb20cfbdc9649c4f2";
    private final String AppSecret = "61ab310ffff725d442a1fa02f92b925f";
    private IWXAPI mApi;
    private int THUMB_SIZE = 200;

    TextView tvShareText;
    private TextView tvShareImg;
    private TextView tvShareVideo;
    private TextView tvShareWebPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvShareText = (TextView)findViewById(R.id.tvShareText);
        tvShareImg = (TextView)findViewById(R.id.tvShareImg);
        tvShareVideo = (TextView)findViewById(R.id.tvShareVideo);
        tvShareWebPage = (TextView)findViewById(R.id.tvShareWebPage);

        // register app id
        regToWx();
        // set up listeners
        initListener();
    }

    private void regToWx(){
        mApi = WXAPIFactory.createWXAPI(this, AppID, true);
        mApi.registerApp(AppID);
    }

    private void initListener(){
        tvShareText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendTextShareReqToWeChat();
            }
        });

        tvShareImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendImgShareReqToWeChat();
            }
        });

        tvShareVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendVideoShareReqToWeChat();
            }
        });

        tvShareWebPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendWebpageShareReqToWeChat();
            }
        });
    }

    public void sendTextShareReqToWeChat(){
        WXTextObject textObj = new WXTextObject();
        textObj.text = "Hello Wechat";

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        msg.description = "This is testing message from Whova wechat testing account";

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneTimeline;

        mApi.sendReq(req);
    }

    public void sendImgShareReqToWeChat(){
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.mountain);
        WXImageObject imgObj = new WXImageObject(bmp);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;

        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
        bmp.recycle();
        msg.thumbData = Util.bmpToByteArray(thumbBmp, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneTimeline;

        mApi.sendReq(req);

    }

    public void sendVideoShareReqToWeChat(){
        WXVideoObject video = new WXVideoObject();
        video.videoUrl = "https://www.youtube.com/watch?v=cjahYtairmo";

        WXMediaMessage msg = new WXMediaMessage(video);
        msg.title = "NBA Playoffs";
        msg.description = "Golden Warriors vs Portland Trail Blazers";
        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.mipmap.video);
        msg.thumbData = Util.bmpToByteArray(thumb, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneTimeline;

        mApi.sendReq(req);
    }

    public void sendWebpageShareReqToWeChat(){
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = "http://www.forbes.com/sites/neilwinton/2016/05/06/tesla-motors-production-acceleration-plan-spooks-some-investors/#2aa7c82510c6";

        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = "Tesla Motors Production Acceleration Plan Spooks Some Investors";
        msg.description = "Some investors were reeling after Tesla Motors TSLA +1.68% pulled forward its 500,000 car production forecast by 2 years, but others were sticking with the faith.";
        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.mipmap.forbes);
        msg.thumbData = Util.bmpToByteArray(thumb, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneTimeline;

        mApi.sendReq(req);
    }

}
