package com.tylx.leasephone.util;


import com.tylx.leasephone.system.AppContext;

/**
 * 常用单位转换的辅助类
 *
 * @author blueam
 */
public class DpUtil {

	/**
	 * 将px值转换为dip或dp值，保证尺寸大小不变
	 *
	 * @param pxValue
	 * @return
	 */
	public static int px2dip(float pxValue) {
		final float scale = getAppInstance().getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 将dip或dp值转换为px值，保证尺寸大小不变
	 *
	 * @param dipValue
	 * @return
	 */
	public static int dip2px(float dipValue) {
		float scale = getAppInstance().getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * 将px值转换为sp值，保证文字大小不变
	 *
	 * @param pxValue
	 * @return
	 */
	public static int px2sp(float pxValue) {
		final float fontScale = getAppInstance().getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 * <p/>
	 * （DisplayMetrics类中属性scaledDensity）
	 *
	 * @return
	 */
	public static int sp2px(float spValue) {
		final float fontScale = getAppInstance().getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	private static AppContext getAppInstance() {
		return AppContext.getInstance();
	}
}
