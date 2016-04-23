package cn.accessbright.blade.core.mail;

import cn.accessbright.blade.core.utils.Dates;

import java.util.Date;

/**
 * 代表一个通知记录
 *
 * @author ll
 */
public class NotifyRecord {

    public static final double MAIL_HR_OFFSET_DAYS = 1;
    public static final String STATUS_NOT_SEND = "0";
    public static final String STATUS_HAS_SEND = "1";
    public static final String STATUS_HAS_CANCELED = "-1";

    private String id;
    private String jobId;//表示一个工作流的id
    private String subject;
    private String content;
    private String to;
    private Date createDate;// ֪ͨ通知产生时间
    private double offsetDays;//通知发送时间偏移天数，小于等于0的则立即发送
    private Date sendDate;//通知发送时间=通知产生时间+通知发送时间偏移量
    private String status;//邮件状态:0未发送，1：已发送，-1：已取消

    public NotifyRecord() {
    }

    /**
     * 偏移量默认取系统设置值
     *
     * @param jobId
     * @param subject
     * @param content
     * @param to
     * @param createDate
     */
    public NotifyRecord(String jobId, String subject, String content, String to, Date createDate, boolean isImmediatelySend) {
        this(jobId, subject, content, to, createDate, isImmediatelySend ? 0 : MAIL_HR_OFFSET_DAYS);
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
        return Dates.plusDay(createDate, offsetDays);
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
     * 发送
     */
    public void send() {
        status = STATUS_HAS_SEND;
    }

    /**
     * 取消发送
     */
    public void cancel() {
        status = STATUS_HAS_CANCELED;
    }

    /**
     * 是否立即发送
     *
     * @return
     */
    public boolean isImmediatelySend() {
        return offsetDays <= 0;
    }
}