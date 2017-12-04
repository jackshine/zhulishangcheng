package com.example.ddm.appui.utils;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;

import com.example.ddm.R;
import com.example.ddm.appui.bean.CustomBorderDrawable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 应用启动图标未读消息数显示 工具类 (效果如：QQ、微信、未读短信 等应用图标) 依赖于第三方手机厂商(如：小米、三星)的Launcher定制 该工具类
 * 支持的设备有 小米、三星、索尼【其中小米、三星亲测有效、索尼未验证】以及原生系统
 */
public class BadgeUtil {

	// 默认圆角半径
	public static final int DEFAULT_CORNER_RADIUS_DIP = 8;
	// 默认边框宽度
	public static final int DEFAULT_STROKE_WIDTH_DIP = 2;
	// 边框的颜色
	public static final int DEFAULT_STROKE_COLOR = Color.WHITE;
	// 中间数字的颜色
	public static final int DEFAULT_NUM_COLOR = Color.parseColor("#CCFF0000");

	@SuppressLint("DefaultLocale")
	public static void setBadgeCount(Context context, int count, int iconRes) {
		if (count <= 0) {
			count = 0;
		} else {
			count = Math.max(0, Math.min(count, 99));
		}

		if (Build.MANUFACTURER.equalsIgnoreCase("Xiaomi")) {
			sendToXiaoMi(context, count, iconRes);
		} else if (Build.MANUFACTURER.equalsIgnoreCase("sony")) {
			sendToSony(context, count);
		} else if (Build.MANUFACTURER.toLowerCase().contains("samsung")) {
			sendToSamsumg(context, count);
		} else {
			if (isShortCutExist(context, context.getString(R.string.app_name))) {
				updateShortcutIcon(context, context.getString(R.string.app_name), generatorNumIcon4(context,
						((BitmapDrawable) context.getResources().getDrawable(iconRes)).getBitmap(), true, count + ""));
			} else {
				installRawShortCut(context, context.getClass(), true, String.valueOf(count), true, iconRes);
			}

		}
	}

	/**
	 * 向小米手机发送未读消息数广播
	 *
	 * @param count
	 */
	public static void sendToXiaoMi(Context context, int number, int iconRes) {

		NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = null;
		boolean isMiUIV6 = true;
		try {
			NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
			builder.setContentTitle("您有" + number + "未读消息");
			builder.setTicker("您有" + number + "未读消息");
			builder.setAutoCancel(true);
			// 设置通知的图标
			builder.setSmallIcon(iconRes);
			builder.setDefaults(Notification.DEFAULT_LIGHTS);
			notification = builder.build();
			// 设置图标角标
			Field field = notification.getClass().getDeclaredField("extraNotification");
			Object extraNotification = field.get(notification);
			Method method = extraNotification.getClass().getDeclaredMethod("setMessageCount", int.class);
			method.invoke(extraNotification, number);
			// Toast.makeText(context, "MIUI-V6", Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			e.printStackTrace();
			// miui 6之前的版本
			isMiUIV6 = false;
			Intent localIntent = new Intent("android.intent.action.APPLICATION_MESSAGE_UPDATE");
			localIntent.putExtra("android.intent.extra.update_application_component_name",
					context.getPackageName() + "/" + getLauncherClassName(context));
			localIntent.putExtra("android.intent.extra.update_application_message_text", String.valueOf(number));
			context.sendBroadcast(localIntent);
			// Toast.makeText(context, "MIUI-V5", Toast.LENGTH_SHORT).show();
		} finally {
			if (notification != null && isMiUIV6) {
				// miui6以上版本需要使用通知发送
				nm.notify(101010, notification);
			}
		}
	}

	/**
	 * 向索尼手机发送未读消息数广播 据说：需添加权限：<uses-permission android:name=
	 * "com.sonyericsson.home.permission.BROADCAST_BADGE" /> [未验证]
	 *
	 * @param count
	 */
	public static void sendToSony(Context context, int count) {
		String launcherClassName = getLauncherClassName(context);
		if (launcherClassName == null) {
			return;
		}

		boolean isShow = true;
		if (count == 0) {
			isShow = false;
		}

		Intent localIntent = new Intent();
		localIntent.setAction("com.sonyericsson.home.action.UPDATE_BADGE");
		localIntent.putExtra("com.sonyericsson.home.intent.extra.badge.SHOW_MESSAGE", isShow);// 是否显示
		localIntent.putExtra("com.sonyericsson.home.intent.extra.badge.ACTIVITY_NAME", launcherClassName);// 启动页
		localIntent.putExtra("com.sonyericsson.home.intent.extra.badge.MESSAGE", String.valueOf(count));// 数字
		localIntent.putExtra("com.sonyericsson.home.intent.extra.badge.PACKAGE_NAME", context.getPackageName());// 包名
		context.sendBroadcast(localIntent);
	}

	/**
	 * 向三星手机发送未读消息数广播
	 *
	 * @param count
	 */
	public static void sendToSamsumg(Context context, int count) {
		String launcherClassName = getLauncherClassName(context);
		if (launcherClassName == null) {
			return;
		}
		Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
		intent.putExtra("badge_count", count);
		intent.putExtra("badge_count_package_name", context.getPackageName());
		intent.putExtra("badge_count_class_name", launcherClassName);
		context.sendBroadcast(intent);
	}

	/**
	 * 重置、清除Badge未读显示数<br/>
	 *
	 * @param context
	 */
	public static void resetBadgeCount(Context context, int iconRes) {
		setBadgeCount(context, 0, iconRes);
	}

	/**
	 * Retrieve launcher activity name of the application from the context
	 *
	 * @param context
	 *            The context of the application package.
	 * @return launcher activity name of this application. From the
	 *         "android:name" attribute.
	 */
	public static String getLauncherClassName(Context context) {
		PackageManager packageManager = context.getPackageManager();

		Intent intent = new Intent(Intent.ACTION_MAIN);
		// To limit the components this Intent will resolve to, by setting an
		// explicit package name.
		intent.setPackage(context.getPackageName());
		intent.addCategory(Intent.CATEGORY_LAUNCHER);

		// All Application must have 1 Activity at least.
		// Launcher activity must be found!
		ResolveInfo info = packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);

		// get a ResolveInfo containing ACTION_MAIN, CATEGORY_LAUNCHER
		// if there is no Activity which has filtered by CATEGORY_DEFAULT
		if (info == null) {
			info = packageManager.resolveActivity(intent, 0);
		}

		return info.activityInfo.name;
	}

	/***
	 * 创建原生系统的快捷方式
	 *
	 * @param context
	 * @param clazz
	 *            启动的activity
	 * @param isShowNum
	 *            是否显示数字
	 * @param num
	 *            显示的数字：整型
	 * @param isStroke
	 *            是否加上边框
	 */
	public static void installRawShortCut(Context context, Class<?> clazz, boolean isShowNum, String num,
										  boolean isStroke, int iconRes) {

		Intent shortcutIntent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
		// 名称
		shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, context.getString(R.string.app_name));

		// 是否可以有多个快捷方式的副本，参数如果是true就可以生成多个快捷方式，如果是false就不会重复添加
		shortcutIntent.putExtra("duplicate", false);

		// 点击快捷方式：打开activity
		Intent mainIntent = new Intent(Intent.ACTION_MAIN);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		mainIntent.setClass(context, clazz);
		shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, mainIntent);

		// 快捷方式的图标
		if (isStroke) {
			shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON, generatorNumIcon4(context,
					((BitmapDrawable) context.getResources().getDrawable(iconRes)).getBitmap(), isShowNum, num));
		} else {
			shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON, generatorNumIcon2(context,
					((BitmapDrawable) context.getResources().getDrawable(iconRes)).getBitmap(), isShowNum, num));
		}
		context.sendBroadcast(shortcutIntent);
	}

	/***
	 *
	 * 生成有数字的图片(没有边框)
	 *
	 * @param context
	 * @param icon
	 *            图片
	 * @param isShowNum
	 *            是否要绘制数字
	 * @param num
	 *            数字字符串：整型数字 超过99，显示为"99+"
	 * @return
	 */
	public static Bitmap generatorNumIcon2(Context context, Bitmap icon, boolean isShowNum, String num) {

		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		// 基准屏幕密度
		float baseDensity = 1.5f;// 240dpi
		float factor = dm.density / baseDensity;

		// 初始化画布
		int iconSize = (int) context.getResources().getDimension(android.R.dimen.app_icon_size);
		Bitmap numIcon = Bitmap.createBitmap(iconSize, iconSize, Config.ARGB_8888);
		Canvas canvas = new Canvas(numIcon);

		// 拷贝图片
		Paint iconPaint = new Paint();
		iconPaint.setDither(true);// 防抖动
		iconPaint.setFilterBitmap(true);// 用来对Bitmap进行滤波处理，这样，当你选择Drawable时，会有抗锯齿的效果
		Rect src = new Rect(0, 0, icon.getWidth(), icon.getHeight());
		Rect dst = new Rect(0, 0, iconSize, iconSize);
		canvas.drawBitmap(icon, src, dst, iconPaint);

		if (isShowNum) {

			if (TextUtils.isEmpty(num)) {
				num = "0";
			}

			if (!TextUtils.isDigitsOnly(num)) {
				// 非数字
				num = "0";
			}

			int numInt = Integer.valueOf(num);

			if (numInt > 99) {// 超过99
				num = "99+";
			} else if (numInt == 0) {
				return numIcon;
			}

			// 启用抗锯齿和使用设备的文本字体大小
			// 测量文本占用的宽度
			Paint numPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DEV_KERN_TEXT_FLAG);
			numPaint.setColor(Color.WHITE);
			numPaint.setTextSize(20f * factor);
			numPaint.setTypeface(Typeface.DEFAULT_BOLD);
			int textWidth = (int) numPaint.measureText(num, 0, num.length());

			/**
			 * ----------------------------------* 绘制圆角矩形背景 start
			 * ------------------------------------
			 */
			// 圆角矩形背景的宽度
			int backgroundHeight = (int) (2 * 15 * factor);
			int backgroundWidth = textWidth > backgroundHeight ? (int) (textWidth + 10 * factor) : backgroundHeight;

			canvas.save();// 保存状态

			ShapeDrawable drawable = getDefaultBackground(context);
			drawable.setIntrinsicHeight(backgroundHeight);
			drawable.setIntrinsicWidth(backgroundWidth);
			drawable.setBounds(0, 0, backgroundWidth, backgroundHeight);
			canvas.translate(iconSize - backgroundWidth, 0);
			drawable.draw(canvas);

			canvas.restore();// 重置为之前保存的状态

			/**
			 * ----------------------------------* 绘制圆角矩形背景 end
			 * ------------------------------------
			 */

			// 绘制数字
			canvas.drawText(num, (float) (iconSize - (backgroundWidth + textWidth) / 2), 22 * factor, numPaint);
		}
		return numIcon;
	}

	/***
	 *
	 * 生成有数字的图片(有边框的)
	 *
	 * @param context
	 * @param icon
	 *            图片
	 * @param isShowNum
	 *            是否要绘制数字
	 * @param num
	 *            数字字符串：整型数字 超过99，显示为"99+"
	 * @return
	 */
	public static Bitmap generatorNumIcon4(Context context, Bitmap icon, boolean isShowNum, String num) {

		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		// 基准屏幕密度
		float baseDensity = 1.5f;// 240dpi
		float factor = dm.density / baseDensity;

		// 初始化画布
		int iconSize = (int) context.getResources().getDimension(android.R.dimen.app_icon_size);
		Bitmap numIcon = Bitmap.createBitmap(iconSize, iconSize, Config.ARGB_8888);
		Canvas canvas = new Canvas(numIcon);

		// 拷贝图片
		Paint iconPaint = new Paint();
		iconPaint.setDither(true);// 防抖处理
		iconPaint.setFilterBitmap(true);// 用来对Bitmap进行滤波处理，这样，当你选择Drawable时，会有抗锯齿的效果
		Rect src = new Rect(0, 0, icon.getWidth(), icon.getHeight());
		Rect dst = new Rect(0, 0, iconSize, iconSize);
		canvas.drawBitmap(icon, src, dst, iconPaint);

		if (isShowNum) {

			if (TextUtils.isEmpty(num)) {
				num = "0";
			}

			if (!TextUtils.isDigitsOnly(num)) {
				// 非数字
				num = "0";
			}

			int numInt = Integer.valueOf(num);

			if (numInt > 99) {// 超过99
				num = "99+";
			} else if (numInt == 0) {
				return numIcon;
			}

			// 启用抗锯齿和使用设备的文本字体
			// 测量文本占用的宽度
			Paint numPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DEV_KERN_TEXT_FLAG);
			numPaint.setColor(Color.WHITE);
			numPaint.setTextSize(25f * factor);
			numPaint.setTypeface(Typeface.DEFAULT_BOLD);
			int textWidth = (int) numPaint.measureText(num, 0, num.length());

			/**
			 * ----------------------------------* 绘制圆角矩形背景 start
			 * ------------------------------------
			 */
			// 边框的宽度
			int strokeThickness = (int) (DEFAULT_STROKE_WIDTH_DIP * factor);
			// 圆角矩形背景的宽度
			float radiusPx = 15 * factor;
			int backgroundHeight = (int) (2 * (radiusPx + strokeThickness));// 2*(半径+边框宽度)
			int backgroundWidth = textWidth > backgroundHeight ? (int) (textWidth + 10 * factor + 2 * strokeThickness)
					: backgroundHeight;

			canvas.save();// 保存状态

			ShapeDrawable drawable = getDefaultBackground2(context);
			drawable.setIntrinsicHeight(backgroundHeight);
			drawable.setIntrinsicWidth(backgroundWidth);
			drawable.setBounds(0, 0, backgroundWidth, backgroundHeight);
			canvas.translate(iconSize - backgroundWidth - strokeThickness, 2 * strokeThickness);
			drawable.draw(canvas);

			canvas.restore();// 重置为之前保存的状态

			/**
			 * ----------------------------------* 绘制圆角矩形背景 end
			 * ------------------------------------
			 */

			// 绘制数字
			canvas.drawText(num, (float) (iconSize - (backgroundWidth + textWidth + 2 * strokeThickness) / 2),
					(float) (25 * factor + 2.5 * strokeThickness), numPaint);
		}
		return numIcon;
	}

	/***
	 * 得到一个默认的背景：圆角矩形 使用代码来生成一个背景：相当于用<shape>的xml的背景
	 *
	 * @return
	 */
	public static ShapeDrawable getDefaultBackground(Context context) {

		// 这个是为了应对不同分辨率的手机，屏幕兼容性
		int r = dipToPixels(context, DEFAULT_CORNER_RADIUS_DIP);
		float[] outerR = new float[] { r, r, r, r, r, r, r, r };

		// 圆角矩形
		RoundRectShape rr = new RoundRectShape(outerR, null, null);
		ShapeDrawable drawable = new ShapeDrawable(rr);
		drawable.getPaint().setColor(DEFAULT_NUM_COLOR);// 设置颜色
		return drawable;

	}

	/***
	 * 得到一个默认的背景：圆角矩形 使用代码来生成一个背景：相当于用<shape>的xml的背景
	 *
	 * @return
	 */
	public static ShapeDrawable getDefaultBackground2(Context context) {

		// 这个是为了应对不同分辨率的手机，屏幕兼容性
		int r = dipToPixels(context, DEFAULT_CORNER_RADIUS_DIP);
		float[] outerR = new float[] { r, r, r, r, r, r, r, r };
		int distance = dipToPixels(context, DEFAULT_STROKE_WIDTH_DIP);

		// 圆角矩形
		RoundRectShape rr = new RoundRectShape(outerR, null, null);
		CustomBorderDrawable drawable = new CustomBorderDrawable(context, rr);
		drawable.getFillpaint().setColor(DEFAULT_NUM_COLOR);// 设置填充颜色
		drawable.getStrokepaint().setColor(DEFAULT_STROKE_COLOR);// 设置边框颜色
		drawable.getStrokepaint().setStrokeWidth(distance);// 设置边框宽度
		return drawable;

	}

	/***
	 * dp to px
	 *
	 * @param dip
	 * @return
	 */
	public static int dipToPixels(Context context, int dip) {
		Resources r = context.getResources();
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, r.getDisplayMetrics());
		return (int) px;
	}

	/***
	 * 删除原生系统的快捷方式
	 *
	 * @param context
	 * @param clazz
	 * 启动的activity
	 */
	public static void deleteRawShortCut(Context context, Class<?> clazz) {
		Intent intent = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
		// 快捷方式的名称
		intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, context.getString(R.string.app_name));

		Intent intent2 = new Intent();
		intent2.setClass(context, clazz);
		intent2.setAction(Intent.ACTION_MAIN);
		intent2.addCategory(Intent.CATEGORY_LAUNCHER);
		intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent2);

		context.sendBroadcast(intent);
	}

	/**
	 * 更新桌面快捷方式图标，不一定所有图标都有效
	 * 如果快捷方式不存在，则不更新
	 *
	 */
	public static void updateShortcutIcon(Context context, String title, Bitmap bitmap) {
		if (bitmap == null) {
			Log.i("mz", "update shortcut icon,bitmap empty");
			return;
		}
		try {
			final ContentResolver cr = context.getContentResolver();
			StringBuilder uriStr = new StringBuilder();
			String urlTemp = "";
			String authority = getAuthorityFromPermissionDefault(context);
			if (authority == null || authority.trim().equals("")) {
				authority = getAuthorityFromPermission(context,
						getCurrentLauncherPackageName(context) + ".permission.READ_SETTINGS");
			}
			uriStr.append("content://");
			if (TextUtils.isEmpty(authority)) {
				int sdkInt = Build.VERSION.SDK_INT;
				if (sdkInt < 8) { // Android 2.1.x(API 7)以及以下的
					uriStr.append("com.android.launcher.settings");
				} else if (sdkInt < 19) {// Android 4.4以下
					uriStr.append("com.android.launcher2.settings");
				} else {// 4.4以及以上
					uriStr.append("com.android.launcher3.settings");
				}
			} else {
				uriStr.append(authority);
			}
			urlTemp = uriStr.toString();
			uriStr.append("/favorites?notify=true");
			Uri uri = Uri.parse(uriStr.toString());
			Cursor c = cr.query(uri, new String[] { "_id", "title", "intent" }, "title=?", new String[] { title },
					null);
			int index = -1;
			if (c != null && c.getCount() > 0) {
				c.moveToFirst();
				index = c.getInt(0);// 获得图标索引
				ContentValues cv = new ContentValues();
				cv.put("icon", flattenBitmap(bitmap));
				Uri uri2 = Uri.parse(urlTemp + "/favorites/" + index + "?notify=true");
				int i = context.getContentResolver().update(uri2, cv, null, null);
				context.getContentResolver().notifyChange(uri, null);// 此处不能用uri2，是个坑
				Log.i("mz", "update ok: affected " + i + " rows,index is" + index);
			} else {
				Log.i("mz", "update result failed");
			}
			if (c != null && !c.isClosed()) {
				c.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			Log.i("mz", "update shortcut icon,get errors:" + ex.getMessage());
		}
	}

	private static byte[] flattenBitmap(Bitmap bitmap) {
		// Try go guesstimate how much space the icon will take when serialized
		// to avoid unnecessary allocations/copies during the write.
		int size = bitmap.getWidth() * bitmap.getHeight() * 4;
		ByteArrayOutputStream out = new ByteArrayOutputStream(size);
		try {
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
			out.flush();
			out.close();
			return out.toByteArray();
		} catch (IOException e) {
			Log.w("mz", "Could not write icon");
			return null;
		}
	}

	/**
	 * LauncherUtil
	 */
	private static String AUTHORITY = null;

	public static boolean isShortCutExist(Context context, String title) {

		boolean isInstallShortcut = false;

		if (null == context || TextUtils.isEmpty(title))
			return isInstallShortcut;

		if (TextUtils.isEmpty(AUTHORITY))
			AUTHORITY = getAuthorityFromPermission(context);

		final ContentResolver cr = context.getContentResolver();

		if (!TextUtils.isEmpty(AUTHORITY)) {
			try {
				final Uri CONTENT_URI = Uri.parse(AUTHORITY);

				Cursor c = cr.query(CONTENT_URI, new String[] { "title", "iconResource" }, "title=?",
						new String[] { title }, null);

				// XXX表示应用名称。
				if (c != null && c.getCount() > 0) {
					isInstallShortcut = true;
				}
				if (null != c && !c.isClosed())
					c.close();
			} catch (Exception e) {

				Log.e("isShortCutExist", e.getMessage());
			}

		}
		return isInstallShortcut;

	}

	public static String getCurrentLauncherPackageName(Context context) {

		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		ResolveInfo res = context.getPackageManager().resolveActivity(intent, 0);
		if (res == null || res.activityInfo == null) {
			// should not happen. A home is always installed, isn't it?
			return "";
		}
		if (res.activityInfo.packageName.equals("android")) {
			return "";
		} else {
			return res.activityInfo.packageName;
		}
	}

	public static String getAuthorityFromPermissionDefault(Context context) {

		return getThirdAuthorityFromPermission(context, "com.android.launcher.permission.READ_SETTINGS");
	}

	public static String getThirdAuthorityFromPermission(Context context, String permission) {
		if (TextUtils.isEmpty(permission)) {
			return "";
		}

		try {
			List<PackageInfo> packs = context.getPackageManager().getInstalledPackages(PackageManager.GET_PROVIDERS);
			if (packs == null) {
				return "";
			}
			for (PackageInfo pack : packs) {
				ProviderInfo[] providers = pack.providers;
				if (providers != null) {
					for (ProviderInfo provider : providers) {
						if (permission.equals(provider.readPermission) || permission.equals(provider.writePermission)) {
							if (!TextUtils.isEmpty(provider.authority)// 精准匹配launcher.settings，再一次验证
									&& (provider.authority).contains(".launcher.settings"))
								return provider.authority;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String getAuthorityFromPermission(Context context) {
		// 获取默认
		String authority = getAuthorityFromPermissionDefault(context);
		// 获取特殊第三方
		if (authority == null || authority.trim().equals("")) {
			String packageName = getCurrentLauncherPackageName(context);
			packageName += ".permission.READ_SETTINGS";
			authority = getThirdAuthorityFromPermission(context, packageName);
		}
		// 还是获取不到，直接写死
		if (TextUtils.isEmpty(authority)) {
			int sdkInt = Build.VERSION.SDK_INT;
			if (sdkInt < 8) { // Android 2.1.x(API 7)以及以下的
				authority = "com.android.launcher.settings";
			} else if (sdkInt < 19) {// Android 4.4以下
				authority = "com.android.launcher2.settings";
			} else {// 4.4以及以上
				authority = "com.android.launcher3.settings";
			}
		}
		authority = "content://" + authority + "/favorites?notify=true";
		return authority;

	}

	/***
	 * 取得权限相应的认证URI
	 *
	 * @param context
	 * @param permission
	 * @return
	 */
	public static String getAuthorityFromPermission(Context context, String permission) {
		if (TextUtils.isEmpty(permission)) {
			return null;
		}
		List<PackageInfo> packInfos = context.getPackageManager().getInstalledPackages(PackageManager.GET_PROVIDERS);
		if (packInfos == null) {
			return null;
		}
		for (PackageInfo info : packInfos) {
			ProviderInfo[] providers = info.providers;
			if (providers != null) {
				for (ProviderInfo provider : providers) {
					if (permission.equals(provider.readPermission) || permission.equals(provider.writePermission)) {
						return provider.authority;
					}
				}
			}
		}
		return null;
	}

}