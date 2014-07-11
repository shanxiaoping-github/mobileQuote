package cn.mobileproductquote.app.util;

import java.util.Random;

import android.content.Context;

/**
 * 数学工具类
 * 
 * @author Administrator
 * 
 */
public class MathUtil {
	/**
	 * 判断是否为浮点数字
	 * 
	 * @return
	 */
	public static boolean isFloatNumber(String numberStr,float startNumber,float endNumber,boolean havaStart,boolean havaEnd) {
		try {

			float number = Float.valueOf(numberStr);// 把字符串强制转换为数字
			if ((havaStart?number >= startNumber:number > startNumber) && (havaEnd?number <= endNumber:number <endNumber)){
				return true;// 如果是数字，返回True
			} else {
				return false;// 超过整数最大值
			}

		} catch (Exception e) {
			return false;// 如果抛出异常，返回False
		}
	}
	
	/**
	 * 返回金额的合理方式
	 * @param numberStr 金额数
	 * @return 金额处理后的表达式
	 */
	public static String getAmoutExpress(double number){
		double resultNumber = number;
		String suffix="";
		
		try{
			
			
			if(number>=10000&&number<100000000){//如果大于等于一万少于一亿
				resultNumber=number/10000;
				suffix="万";
			}else if(number>=100000000){
				resultNumber=number/100000000;
				suffix="亿";
			}
		}catch (Exception e) {
			// TODO: handle exception
			return "0";
		}
		String resultNumberStr = String.valueOf(resultNumber);
		int index=resultNumberStr.indexOf(".");
		if(index!=-1){//有小数
			String smallNumber = resultNumberStr.substring(index+1);
			int smlNumber = Integer.parseInt(smallNumber);
			if(smlNumber==0){
				resultNumberStr = resultNumberStr.substring(0,index);
			}
		}
		return resultNumberStr+suffix;
	}
	/**
	 * 获得随机数
	 * @param startNumber 起
	 * @param endNumber 结
	 * @param isBorder 是否包含边界
	 * @return
	 */
	public static int getRamdom(int startNumber,int endNumber,boolean isBorder){
		Random random = new Random();
		int result = random.nextInt((isBorder?endNumber+1:endNumber)-startNumber)+startNumber;
		return result;
	}
	
	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int diptopx(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int pxtodip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
}
