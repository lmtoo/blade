package cn.accessbright.blade.core;

import com.icitic.hrms.core.util.PeriodTime;
import com.icitic.hrms.kq.util.XjConstants;

/**
 * 继承自PeriodTime，并添加了对相同假期类型时间段连接的支持 <br/>
 * 重写了isConnected方法，来判断相应的假期时间段之间是否可以连接起来
 *
 * @note 具体到日和上下午
 *
 * @author Neusoft
 *
 */
public class KqPeriodTime extends PeriodTime {
	private String holidayType;

	public KqPeriodTime(String startDate, String startTime, String endDate, String endTime, String holidayType) {
		super(startDate, startTime, endDate, endTime);
		this.holidayType = holidayType;
	}

	public boolean isConnected(PeriodTime time) {
		boolean isConnected = super.isConnected(time);
		if (isConnected)
			return true;
		if (XjConstants.TANQINJIA.equals(holidayType))
			return false;

		boolean isBefore = isBefore(startDate, startTime, time.getStartDate(), time.getStartTime());

		PeriodTime pre = isBefore ? this : time;
		PeriodTime post = isBefore ? time : this;

		boolean isEndBefore = isBefore(pre.getEndDate(), pre.getEndTime(), post.getStartDate(), post.getStartTime());

		if (isEndBefore) {
			PeriodTime betweenPeroid = getBetweenPeroid(pre.getEndDate(), pre.getEndTime(), post.getStartDate(), post.getStartTime());
			boolean chanjia = (XjConstants.CHANJIA.equals(holidayType) && betweenPeroid.isPeroidInHoliday());
			boolean other = betweenPeroid.isPeroidInHolidayOrWeekends();
			return chanjia || other;
		}
		return false;
	}

	public PeriodTime copy() {
		return new KqPeriodTime(startDate, startTime, endDate, endTime, holidayType);
	}
}