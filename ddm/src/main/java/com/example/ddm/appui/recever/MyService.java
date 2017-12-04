package com.example.ddm.appui.recever;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.example.ddm.R;
import com.example.ddm.appui.utils.BadgeUtil;
public class MyService extends Service {
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		int number = intent.getIntExtra("number", 0);
		if(number == 0){
			//数字为零时，清空图标上的数字
			BadgeUtil.resetBadgeCount(getApplicationContext(), R.mipmap.logo);
		}else{
			//数字大于零时，绘制数字角标
			BadgeUtil.setBadgeCount(getApplicationContext(), number, R.mipmap.logo);
		}
		return super.onStartCommand(intent, flags, startId);
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
