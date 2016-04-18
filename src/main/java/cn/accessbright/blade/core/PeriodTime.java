package cn.accessbright.blade.core;

import org.apache.commons.lang.StringUtils;

import com.icitic.hrms.kq.util.KqDateTools;
import com.icitic.hrms.kq.util.XjConstants;
import com.icitic.hrms.util.Tools;

/**
 * 表示一个时间区间的模型，简化了时间段之间的计算、比较、链接
 *
 * @note 具体到日和上下午
 * @author ll
 */
public class PeriodTime implements Comparable {
	public static final String SHANGWU = "shangwu";
	public static final String XIAWU = "xiawu";

	protected String startDate;
	protected String startTime;
	protected String endDate;
	protected String endTime;

	public PeriodTime(String startDate, String startTime, String endDate, String endTime) {
		this.startDate = startDate;
		this.startTime = startTime;
		this.endDate = endDate;
		this.endTime = endTime;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public String getEndDate() {
		return endDate;
	}

	public String getEndTime() {
		return endTime;
	}

	/**
	 * 判断该时间区间是否与time区间是连贯的，不考虑周末和假期
	 *
	 * @param time
	 * @return
	 */
	public boolean isConnected(PeriodTime time) {
		boolean isBefore = PeriodTime.isBefore(startDate, startTime, time.startDate, time.startTime);

		PeriodTime pre = isBefore ? this : time;
		PeriodTime post = isBefore ? time : this;

		if (Strings.equals(pre.endDate, post.startDate)) {
			return SHANGWU.equals(pre.endTime) && XIAWU.equals(post.startTime);
		} else if (Strings.equals(Tools.plusDay(pre.endDate, 1), post.startDate)) {
			return XIAWU.equals(pre.endTime) && SHANGWU.equals(post.startTime);
		}
		return false;
	}

	/**
	 * 连接本时间区间和time区间，取起始时间靠前的做为新区间的起始时间，取结束时间靠后的做为结束时间
	 *
	 * @param time
	 * @return
	 */
	public PeriodTime connect(PeriodTime time) {
		if (time == null)
			return this;
		if (!isConnected(time))
			return null;

		boolean isBefore = PeriodTime.isBefore(startDate, startTime, time.startDate, time.startTime);
		String startD = isBefore ? startDate : time.startDate;
		String startT = isBefore ? startTime : time.startTime;
		String endD = isBefore ? time.endDate : endDate;
		String endT = isBefore ? time.endTime : endTime;
		return new PeriodTime(startD, startT, endD, endT);
	}

	/**
	 * 是否时间区间在假期中
	 *
	 * @return
	 */
	public boolean isPeroidInHoliday() {
		PeriodTime theTime = this.copy();
		while (theTime.isStartDateHoliday()) {
			theTime.plusStartDateOneDay();
		}
		return isBefore(theTime.endDate, theTime.endTime, theTime.startDate, theTime.startTime);
	}

	/**
	 * 是否时间区间在假期或者周末中
	 *
	 * @return
	 */
	public boolean isPeroidInHolidayOrWeekends() {
		PeriodTime theTime = this.copy();
		while (theTime.isStartDateHolidaysOrWeekends()) {
			theTime.plusStartDateOneDay();
		}
		return isBefore(theTime.endDate, theTime.endTime, theTime.startDate, theTime.startTime);
	}

	/**
	 * 将起始时间增加一天
	 */
	protected void plusStartDateOneDay() {
		startDate = Tools.plusDay(startDate, 1);
	}

	/**
	 * 是否起始时间在假期中
	 *
	 * @return
	 */
	protected boolean isStartDateHoliday() {
		return KqDateTools.ifHld(startDate);
	}

	/**
	 * 是否起始时间在假期或者周末中
	 *
	 * @return
	 */
	protected boolean isStartDateHolidaysOrWeekends() {
		return KqDateTools.ifZmOrHld(startDate);
	}

	/**
	 * 拷贝本时间区间的一个副本
	 *
	 * @return
	 */
	public PeriodTime copy() {
		return new PeriodTime(startDate, startTime, endDate, endTime);
	}

	/**
	 * 比较两个时间区间的大小，只比较起始时间
	 */
	public int compareTo(Object time) {
		PeriodTime target = (PeriodTime) time;
		boolean isBefore = isBefore(startDate, startTime, target.startDate, target.startTime);
		return isBefore ? -1 : 1;
	}

	public String toString() {
		if (Strings.equals(startDate, endDate)) {
			if (Strings.equals(startTime, endTime)) {
				return startDate.substring(5, 10) + (SHANGWU.equals(startTime) ? "上午" : "下午");
			} else {
				return startDate.substring(5, 10);
			}
		}

		StringBuffer buffer = new StringBuffer();
		buffer.append(startDate.substring(5, 10));
		if (XIAWU.equals(startTime)) {
			buffer.append("下午");
		}
		buffer.append("~");
		buffer.append(endDate.substring(5, 10));
		if (SHANGWU.equals(endTime)) {
			buffer.append("上午");
		}
		return buffer.toString();
	}

	/**
	 * 是否给定的dateTime1小于DateTime2
	 *
	 * @param date1
	 * @param time1
	 * @param date2
	 * @param time2
	 * @return
	 */
	public static boolean isBefore(String date1, String time1, String date2, String time2) {
		if (Strings.equals(date1, date2)) {
			return SHANGWU.equals(time1) && XIAWU.equals(time2);
		}
		return date1.compareTo(date2) < 0;
	}

	/**
	 * 获取两个时间点之间的时间区间
	 *
	 * @param date1
	 * @param time1
	 * @param date2
	 * @param time2
	 * @return
	 */
	public static PeriodTime getBetweenPeroid(String date1, String time1, String date2, String time2) {
		String startDate = XIAWU.equals(time1) ? Tools.plusDay(date1, 1) : date1;
		String startTime = XIAWU.equals(time1) ? SHANGWU : XIAWU;

		String endDate = SHANGWU.equals(time2) ? Tools.minusDay(date2, 1) : date2;
		String endTime = SHANGWU.equals(time2) ? XIAWU : SHANGWU;
		return new PeriodTime(startDate, startTime, endDate, endTime);
	}
}