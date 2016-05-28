package com.mottsdk.example;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mottsdk.example.R;
import com.mobileott.api.MobileOTTManager;
import com.mobileott.api.LoginStateListener;
import com.mobileott.util1.ResourceUtil;

public class MainActivity extends Activity implements View.OnClickListener
 {

	public static final String APPKEY = "mott_test|WVIZGJVRbORbWhVjXSPCfRhZeVGAdERC";
	
	private Button btInit;
	private Button btCall;
	private Button btCallnormalphone;
	// private Button btLogout; ///

	private EditText myAppUserId;
	private EditText myAppNickName;
	private EditText myAppAvatar;

	private EditText calleeAppUserId;
	private EditText calleePhoneNumber;
	
	private String srcPackageName = "com.mobileott.sdk";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		
		myAppUserId = (EditText) findViewById(R.id.myAppUserId);
		myAppNickName = (EditText) findViewById(R.id.myAppNickName);
		myAppAvatar = (EditText) findViewById(R.id.myAppAvatar);

		calleeAppUserId = (EditText) findViewById(R.id.calleeAppUserId);
		calleePhoneNumber = (EditText) findViewById(R.id.calleePhoneNumber);
		
		btInit = (Button) findViewById(R.id.btInit);
		btCall = (Button) findViewById(R.id.btCall);
		btCallnormalphone = (Button) findViewById(R.id.btCallnormalphone);


		btInit.setOnClickListener(this);
		btCall.setOnClickListener(this);
		btCallnormalphone.setOnClickListener(this);
		
//		btLogout = (Button) findViewById(R.id.btLogout);
//		btLogout.setOnClickListener(this);

//		btAddCallStateListener = (Button) findViewById(R.id.btAddCallStateListener);
//		btAddCallStateListener.setOnClickListener(this);
		
		int xx = ResourceUtil.getStringId(srcPackageName, "mytest");
		String xxStr = getResources().getString(xx);

		myAppUserId.setText(xxStr);
       
		String xxx = "songysssstest222";
		
	}

	boolean isMicMute;
	boolean isSpeakEnable;
	
	

	@Override
	public void onClick(View v) {
		
		final  String myNickname = myAppNickName.getText().toString();
		final  String myAvatar = myAppAvatar.getText().toString();
		final  String myUserId = myAppUserId.getText().toString();
		final  String CalleeUserId = calleeAppUserId.getText().toString();
		final  String CalleePhone = calleePhoneNumber.getText().toString();		
		final String RemoteAvatar ="";
		final String RemoteNickName = "";
	
		
		MobileOTTManager mtt = MobileOTTManager.getInstance(this);



		int id = v.getId();
		switch (id) {
		case R.id.btInit:
			mtt.saveMyNicknameMyAvatar(myNickname,  myAvatar);
			mtt.ott_Login(APPKEY, myUserId, new LoginStateListener() {
				@Override
				public void onErrorKey() {
					AlertDialog dlg = new AlertDialog.Builder(MainActivity.this).
					setTitle("提示").setMessage("AppKey 错误： 您的app还没有到MobileOTT网站注册").show();   
					dismiss(dlg);
				}
				@Override
				public void onError(String arg0) {
					AlertDialog dlg = new AlertDialog.Builder(MainActivity.this).
					setTitle("提示").setMessage("错误 ：请检查网络").show();   
					dismiss(dlg);
				}
				@Override
				public void register_failed() {
					AlertDialog dlg = new AlertDialog.Builder(MainActivity.this).
					setTitle("提示").setMessage("注册失败").show();
					dismiss(dlg);
				}
				@Override
				public void register_success() {
					AlertDialog dlg = new AlertDialog.Builder(MainActivity.this).
					setTitle("提示").setMessage("注册成功").show();  
					dismiss(dlg);
				}
				@Override
				public void noRegist() {
					// TODO Auto-generated method stub
					
				}
				@Override
				public void onStartCall() {
					// TODO Auto-generated method stub
					
				}
			});
			break;

//		case R.id.btAddCallStateListener:
//			mtt.addOnCallStatedChangedListener(this);
//			break;

		case R.id.btCall:

			mtt.ott_Call(CalleeUserId, new LoginStateListener() {
				@Override
				public void onStartCall() {
					Intent intent = new Intent(MainActivity.this,
							com.mobileott.activity.OutGoingActivity.class);
					intent.putExtra("remoteavatar", RemoteAvatar);
					intent.putExtra("remotenickname", RemoteNickName);
					MainActivity.this.startActivity(intent);
				}
				@Override
				public void onErrorKey() {
					new AlertDialog.Builder(MainActivity.this).
					setTitle("提示").setMessage("AppKey 错误： 您的app还没有到MobileOTT网站注册").show();   
				}
				@Override
				public void onError(String arg0) {
					new AlertDialog.Builder(MainActivity.this).
					setTitle("提示").setMessage("错误 ：请检查网络").show();   
				}

				@Override
				public void noRegist() {
					new AlertDialog.Builder(MainActivity.this)
					.setTitle("提示").setMessage("好友还没有注册").show();  
				}
				@Override
				public void register_success() {
					// TODO Auto-generated method stub
					
				}
				@Override
				public void register_failed() {
					// TODO Auto-generated method stub
					
				}
			});
			break;
		case R.id.btCallnormalphone:
			Intent intent = new Intent(MainActivity.this,com.mobileott.activity.OutGoingActivity.class);
			mtt.voipout_Call(CalleePhone);
			intent.putExtra("remotephonenumber", CalleePhone);
			MainActivity.this.startActivity(intent);
			break;



//		case R.id.btLogout:
//			mtt.ott_Logout();
//			break;
		default:
		}
	}


	public void dismiss(final AlertDialog dlg)
	{
		final Timer t = new Timer();
	
    t.schedule(new TimerTask() {
        public void run() {
            dlg.dismiss(); 
            t.cancel(); 
        }
    }, 2000); 
	}


}
