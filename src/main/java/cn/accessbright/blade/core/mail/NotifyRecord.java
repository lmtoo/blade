package cn.accessbright.blade.core.mail;

import java.util.Date;

import com.icitic.hrms.cache.SysCacheTool;
import com.icitic.hrms.util.Tools;

/**
 * ����һ��֪ͨ
 * 
 * @author ll
 * 
 */
public class NotifyRecord{

	public static final String STATUS_NOT_SEND = "0";
	public static final String STATUS_HAS_SEND = "1";
	public static final String STATUS_HAS_CANCELED = "-1";

	private String id;
	private String jobId;// ��ʾһ����������id
	private String subject;
	private String content;
	private String to;
	private Date createDate;// ֪ͨ����ʱ��
	private double offsetDays;// ֪ͨ����ʱ��ƫ��������С�ڵ���0������������
	private Date sendDate;// ֪ͨ����ʱ��=֪ͨ����ʱ��+֪ͨ����ʱ��ƫ����
	private String status;// �ʼ�״̬:0δ���ͣ�1���ѷ��ͣ�-1����ȡ��

	public NotifyRecord() {
	}

	/**
	 * ƫ����Ĭ��ȡϵͳ����ֵ
	 * 
	 * @param jobId
	 * @param subject
	 * @param content
	 * @param to
	 * @param createDate
	 */
	public NotifyRecord(String jobId, String subject, String content, String to, Date createDate,boolean isImmediatelySend) {
		this(jobId,subject,content,to,createDate,isImmediatelySend?0:Tools.toNumber(SysCacheTool.getSysParameter("MAIL_HR_OFFSET_DAYS")).doubleValue());
	}

	public NotifyRecord(String jobId, String subject, String content, String to, Date createDate, double offsetDays) {
		this.jobId = jobId;
		this.subject = subject;
		this.content = content;
		this.to = to;
		this.createDate = createDate;
		this.offsetDays = offsetDays;
		this.status = STATUS_NOT_SEND;
		this.sendDate = calculateSendDate(createDate, offsetDays);
	}

	private Date calculateSendDate(Date createDate, double offsetDays) {
		return Tools.plusDay(createDate,offsetDays);
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}
	
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public double getOffsetDays() {
		return offsetDays;
	}

	public void setOffsetDays(double offsetDays) {
		this.offsetDays = offsetDays;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * ����
	 */
	public void send(){
		status=STATUS_HAS_SEND;
	}
	
	/**
	 * ȡ������
	 */
	public void cancel(){
		status=STATUS_HAS_CANCELED;
	}
	
	/**
	 * �Ƿ���������
	 * @return
	 */
	public boolean isImmediatelySend(){
		return offsetDays<=0;
	}
}