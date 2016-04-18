package cn.accessbright.blade.core;

import org.apache.commons.lang.StringUtils;

import com.icitic.hrms.kq.util.KqDateTools;

/**
 * 时间区间，表示一个时间段，具体到日
 *
 * @author Neusoft
 *
 */
public class Period {
	private String start;
	private String end;

	/**
	 * 根据指定的考勤月份，创建考勤区间
	 *
	 * @param yearMonth
	 */
	public Period(String yearMonth) {
		this.start = KqDateTools.getMonth(yearMonth, -1) + "-26";
		this.end = yearMonth + "-25";
	}

	public Period(String start, String end) {
		this.start = start;
		this.end = end;
	}

	public String getStart() {
		return start;
	}

	public String getEnd() {
		return end;
	}

	/**
	 * 是否给定的时间在此区间内
	 *
	 * @param date
	 * @return
	 */
	public boolean isInPeriod(String date) {
		return getStart().compareTo(date) <= 0 && getEnd().compareTo(date) >= 0;
	}

	/**
	 * 返回该具体时间区间的月份区间
	 *
	 * @return
	 */
	public Period getMonthPeroid() {
		String startYearMonth = getStartMonth();
		String endYearMonth = getEndMonth();

		// 如果已经超过25号，则跨越到了下一个月份区间
		if (getEnd().compareTo(endYearMonth + "-26") >= 0) {
			endYearMonth = KqDateTools.getMonth(endYearMonth, 1);
		}
		return new Period(startYearMonth, endYearMonth);
	}

	public String getStartMonth() {
		return getStart().substring(0, 7);
	}

	public String getEndMonth() {
		return getEnd().substring(0, 7);
	}

	public boolean isPeroidStartEdge(String date) {
		return StringUtils.equals(getPeroidStartEdge(), date);
	}

	public boolean isPeroidEndNextMonthEdge(String date) {
		return StringUtils.equals(getPeroidEndNextNMonthEdge(1), date);
	}

	public boolean isPeroidEndNextTowMonthEdge(String date){
		return StringUtils.equals(getPeroidEndNextNMonthEdge(2), date);
	}

	private String getPeroidEndNextNMonthEdge(int month){
		return KqDateTools.getMonth(getEndMonth(), month);
	}

	private String getPeroidStartEdge(){
		return getStart();
	}

	public boolean isBetweenPeroidEdge(String date,int endMonthPlus){
		return getPeroidStartEdge().compareTo(date)<=0 && getPeroidEndNextNMonthEdge(endMonthPlus).compareTo(date)>=0;
	}

	public String getStartYear() {
		return getStart().substring(0, 4);
	}

	public String getEndYear() {
		return getEnd().substring(0, 4);
	}

	/**
	 * 区间是否在同一年之内
	 *
	 * @return
	 */
	public boolean isPeroidInOneYear() {
		return StringUtils.equals(getStartYear(), getEndYear());
	}

	/**
	 * 获取指定时间所在的考勤周期
	 *
	 * @param date
	 * @return
	 */
	public Period getKqPeroidByDate(String date) {
		String startMonth = getStartMonth();
		String monthPeroidEnd = startMonth + "-25";
		boolean isStartDateBeforeMonthPeroidEnd = monthPeroidEnd.compareTo(getStart()) >= 0;
		String startPeroidMonth = isStartDateBeforeMonthPeroidEnd ? startMonth : KqDateTools.getMonth(startMonth, 1);
		return new Period(startPeroidMonth);
	}

	/**
	 * 区间是否在同一个考勤周期之内
	 *
	 * @return
	 */
	public boolean isPeroidInOneMonth() {
		Period startPeroid = getKqPeroidByDate(getStart());
		return startPeroid.isInPeriod(getEnd());
	}
}