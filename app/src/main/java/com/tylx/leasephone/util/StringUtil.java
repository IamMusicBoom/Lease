package com.tylx.leasephone.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.EditText;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * 字符串操作工具包<br>
 * ww
 * <b>创建时间</b> 2014-8-14
 * 
 */
public class StringUtil {
	private final static Pattern emailer = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
	private final static Pattern phone = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
	private final static Pattern num = Pattern.compile("^[1-9]\\d*$");

	/**
	 * 判断给定字符串是否空白串 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
	 */
	public static boolean isEmpty(CharSequence input) {
		if (input == null || "".equals(input))
			return true;

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
				return false;
			}
		}
		return true;
	}
	public static boolean isBlank(String str) {
		return (str == null || str.trim().length() == 0);
	}
	/**
	 * 判断是不是一个合法的电子邮件地址
	 */
	public static boolean isEmail(CharSequence email) {
		if (isEmpty(email))
			return false;
		return emailer.matcher(email).matches();
	}

	public static boolean isUm(CharSequence um) {
		if (isEmpty(um))
			return false;
		return num.matcher(um).matches();
	}
	/**
	 * 判断是不是一个合法的手机号码
	 */
	public static boolean isPhone(CharSequence phoneNum) {
		if (isEmpty(phoneNum))
			return false;
		return phone.matcher(phoneNum).matches();
	}

	/**
	 * 判断字符串是否含有中文
	 */
	public static boolean isContainChinese(String str) {

		char[] chars = str.toCharArray();
		boolean isGB2312 = false;
		for (int i = 0; i < chars.length; i++) {
			byte[] bytes = ("" + chars[i]).getBytes();
			if (bytes.length == 2) {
				int[] ints = new int[2];
				ints[0] = bytes[0] & 0xff;
				ints[1] = bytes[1] & 0xff;
				if (ints[0] >= 0x81 && ints[0] <= 0xFE && ints[1] >= 0x40 && ints[1] <= 0xFE) {
					isGB2312 = true;
					break;
				}
			}
		}
		return isGB2312;
	}

	/**
	 * 判断字符串是否全为中文
	 */
	public static boolean isAllChinese(String str) {

		char[] chars = str.toCharArray();
		boolean isGB2312 = false;
		for (int i = 0; i < chars.length; i++) {
			isGB2312 = String.valueOf(chars[i]).matches("[\u4E00-\u9FA5]");
			if (!isGB2312)
				break;
		}
		return isGB2312;
	}

	/**
	 * 字符串转整数
	 * 
	 * @param str
	 * @param defValue
	 * @return
	 */
	public static int toInt(String str, int defValue) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
		}
		return defValue;
	}

	/**
	 * 对象转整
	 * 
	 * @param obj
	 * @return 转换异常返回 0
	 */
	public static int toInt(Object obj) {
		if (obj == null)
			return 0;
		return toInt(obj.toString(), 0);
	}

	/**
	 * String转long
	 * 
	 * @param obj
	 * @return 转换异常返回 0
	 */
	public static long toLong(String obj) {
		try {
			return Long.parseLong(obj);
		} catch (Exception e) {
		}
		return 0;
	}

	/**
	 * String转double
	 * 
	 * @param obj
	 * @return 转换异常返回 0
	 */
	public static double toDouble(String obj) {
		try {
			return Double.parseDouble(obj);
		} catch (Exception e) {
		}
		return 0D;
	}

	/**
	 * 字符串转布尔
	 * 
	 * @param b
	 * @return 转换异常返回 false
	 */
	public static boolean toBool(String b) {
		try {
			return Boolean.parseBoolean(b);
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * 判断一个字符串是不是数字
	 */
	public static boolean isNumber(CharSequence str) {
		try {
			Integer.parseInt(str.toString());
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * byte[]数组转换为16进制的字符串。
	 * 
	 * @param data
	 *            要转换的字节数组。
	 * @return 转换后的结果。
	 */
	public static final String byteArrayToHexString(byte[] data) {
		StringBuilder sb = new StringBuilder(data.length * 2);
		for (byte b : data) {
			int v = b & 0xff;
			if (v < 16) {
				sb.append('0');
			}
			sb.append(Integer.toHexString(v));
		}
		return sb.toString().toUpperCase(Locale.getDefault());
	}

	/**
	 * 16进制表示的字符串转换为字节数组。
	 * 
	 * @param s
	 *            16进制表示的字符串
	 * @return byte[] 字节数组
	 */
	public static byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] d = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			// 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个进制字节
			d[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
		}
		return d;
	}

	/**
	 * 转换文件大小
	 * 
	 * @param fileS
	 * @return B/KB/MB/GB
	 */
	public static String formatFileSize(long fileS) {
		java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "KB";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "MB";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}

	// 计算与当前时间间隔
	public static String timeDiffString(long startMillisecs, long endMillisecs) {
		long between = (endMillisecs - startMillisecs) / 1000;// 除以1000是为了转换成秒

		long day = between / (24 * 3600);
		long hour = between % (24 * 3600) / 3600;
		long minute = between % 3600 / 60;
		long second = between % 60 / 60;
		// System.out.println("" + day + "天" + hour + "小时" + minute + "分" +
		// second + "秒");
		if (day > 0) {
			return day + "天前";
		}
		if (hour > 0) {
			return hour + "小时前";
		}
		if (minute > 0) {
			return minute + "分钟前";
		}
		return "";
	}

	public static String timeLeftString(long startMillisecs, long endMillisecs) {
		long between = (endMillisecs - startMillisecs) / 1000;// 除以1000是为了转换成秒

		if (between > 0) {
			long day = between / (24 * 3600);
			long hour = between % (24 * 3600) / 3600;
			long minute = between % 3600 / 60;
			long second = between % 60 / 60;
			System.out.println("" + day + "天" + hour + "小时" + minute + "分" + second + "秒");
			StringBuffer buffer = new StringBuffer("剩余：");
			if (day > 0) {
				String dayStr = colorText("  " + day + "  ", "#ff8330") + "天";
				buffer.append(dayStr);
			}
			if (hour > 0) {
				String hourStr = colorText("  " + hour + "  ", "#ff8330") + "时";
				buffer.append(hourStr);
			}
			if (minute > 0) {
				String minuteStr = colorText("  " + minute + "  ", "#ff8330") + "分";
				buffer.append(minuteStr);
			}
			return buffer.toString();
		}
		return "已结束";
	}

	// 默认是xx月xx日
	@SuppressLint("SimpleDateFormat")
	public static String formatTime(long time, String... pattern) {
		if (time == 0)
			return "";
		SimpleDateFormat format = null;
		String patternDefault = "M月dd日";
		if (pattern != null && pattern.length > 0) {
			format = new SimpleDateFormat(pattern[0]);
		} else {
			format = new SimpleDateFormat(patternDefault);
		}
		return format.format(time);
	}

	public static String formatTime(String style, long date) {
		SimpleDateFormat format = new SimpleDateFormat(style);
		return format.format(new Date(date * 1000L));
	}

	// 1600.0000
	public static String distanceKM(double d) {
		d = d / 1000;
		BigDecimal b = new BigDecimal(d);
		// 四舍五入1位
		double d2 = b.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
		return d2 + "km";
	}

	/**
	 * @param content
	 *            变色内容
	 * @param color
	 *            颜色值#xxxxxx
	 * @return
	 */
	public static String colorText(String content, String color) {
		if (null == content || content.length() == 0)
			return content;
		return "<font color=\"" + color + "\">" + content + "</font>";
	}

	public static SpannableString colorText(String content, int start, int end, String color) {
		SpannableString spanString = new SpannableString(content);
		spanString.setSpan(new ForegroundColorSpan(Color.parseColor(color)), start, end,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

		return spanString;
	}

	public static SpannableString colorText(Context context, String content, int color) {
		SpannableString spanString = new SpannableString(content);
		spanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, color)), 0, content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

		return spanString;
	}

	public static SpannableString colorText(Context context, String content1, String content2, int color) {
		SpannableString spanString = new SpannableString(content1 + content2);
		spanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, color)), content1.length(), content1.length() + content2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

		return spanString;
	}

	public static SpannableString colorText(Context context, String content1, String content2, String content3, int color) {
		SpannableString spanString = new SpannableString(content1 + content2 + content3);
		spanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, color)), content1.length(), content1.length() + content2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

		return spanString;
	}

	/**
	 * 用于改变一整句中的某个字符颜色，传递要修改的前一个和后一个字符index即可
	 * @param context
	 * @param content
	 * @param start
	 * @param end
	 * @param color
	 * @return
	 */
	public static SpannableString colorText(Context context, String content, int start, int end, int color) {
		SpannableString spanString = new SpannableString(content);
		start += 1;
		if (start >= end || 0 > start || 0 > end)
			return spanString;

		spanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, color)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		return spanString;
	}

	public static Object evaluate(float fraction, Object startValue, Object endValue) {
		int startInt = (Integer) startValue;
		int startA = (startInt >> 24) & 0xff;
		int startR = (startInt >> 16) & 0xff;
		int startG = (startInt >> 8) & 0xff;
		int startB = startInt & 0xff;
		int endInt = (Integer) endValue;
		int endA = (endInt >> 24) & 0xff;
		int endR = (endInt >> 16) & 0xff;
		int endG = (endInt >> 8) & 0xff;
		int endB = endInt & 0xff;
		return (int) ((startA + (int) (fraction * (endA - startA))) << 24)
				| (int) ((startR + (int) (fraction * (endR - startR))) << 16)
				| (int) ((startG + (int) (fraction * (endG - startG))) << 8)
				| (int) ((startB + (int) (fraction * (endB - startB))));
	}

	/**
	 * 判断给定List是否为空。 若输入字符串为null或空字符串，返回true
	 *
	 * @param list
	 * @return boolean
	 */
	public static boolean isEmpty(List<?> list) {
		if (list == null)
			return true;

		if (list.size() <= 0) {
			return true;
		}
		return false;
	}

	/**
	 * 判断给定EditText是否为空。 若输入字符串为null或空字符串，返回true
	 *
	 * @param et
	 * @return
	 */
	public static boolean isEmpty(EditText et) {
		if (et.getText().toString().trim().equals(""))
			return true;

		return false;
	}

	/**
	 * 获取EditText的文字
	 *
	 * @param et
	 * @return
	 */
	public static String getEditText(EditText et) {
		return et.getText().toString().trim();
	}

}
