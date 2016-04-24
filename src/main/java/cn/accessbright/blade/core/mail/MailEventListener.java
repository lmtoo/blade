package cn.accessbright.blade.core.mail;

import cn.accessbright.blade.core.event.EventListener;
import cn.accessbright.blade.core.event.NotifyEvent;
import cn.accessbright.blade.core.event.UrgentNotifyEvent;
import cn.accessbright.blade.core.utils.Objects;
import cn.accessbright.blade.core.utils.collections.Collections;
import cn.accessbright.blade.core.utils.collections.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessagePreparator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 发送Email的事件监听器，单实例，整个项目只用同一个事件监听器，且只对NotifyEvent事件执行邮件发送
 *
 * @author ll
 */
public abstract class MailEventListener implements EventListener<NotifyEvent> {
    private static Logger logger = LoggerFactory.getLogger(MailEventListener.class);

    private static JavaMailSender MAIL_SENDER = createJavaMailSender();

    private MailManager manager;

    /**
     * 接收系统事件，且只对NotifyEvent事件执行邮件发送，可以在此处对邮件内容加工：如应用邮件模版
     * @param event
     */
    @Override
    public void onApplicationEvent(NotifyEvent event) {
        if (event.isValid()) {
            NotifyEvent notifyEvent = (NotifyEvent) event;
            List notifyList = createNotify(notifyEvent);
            for (int i = 0; i < notifyList.size(); i++) {
                NotifyRecord record = (NotifyRecord) notifyList.get(i);
                if (record.isImmediatelySend()) {
                    sendEmail(record);
                }
            }
            manager.saveMailRecord(notifyList);
        }
    }

    public void sendEmail(NotifyRecord message) {
        try {
            synchronized (MAIL_SENDER) {// 锁住同一个邮件发送者，避免并发问题
                MAIL_SENDER.send(createMimeMessagePrpartor(message));
                message.send();
            }
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        }
    }

    /**
     * 创建通知记录
     *
     * @param event
     * @return
     */
    private List createNotify(NotifyEvent event) {
        String[] to = idsToEamilUsernames(Arrays.asList(event.getIds()));
        List notifyList = new ArrayList();
        for (int i = 0; i < to.length; i++) {
            boolean isImmediatelySend = event instanceof UrgentNotifyEvent;

            NotifyRecord notify = new NotifyRecord(
                    event.getJobId(), event.getTitle(),
                    generateContent(event), to[i], event.getDate(), isImmediatelySend);

            notifyList.add(notify);
        }
        return notifyList;
    }

    /**
     * 创建符文本文件
     *
     * @param message
     * @return
     */
    protected MimeMessagePreparator createMimeMessagePrpartor(NotifyRecord message) {
        return MailMessageFactory.createMimeMessage(message.getSubject(), message.getContent(), message.getTo(), message.getCreateDate());
    }

    /**
     * 创建邮件发送正文
     *
     * @param event
     * @return
     */
    protected abstract String generateContent(NotifyEvent event);

    /**
     * 将用户id转换为邮件用户名
     *
     * @param ids
     * @return
     */
    public String[] idsToEamilUsernames(List ids) {
        return (String[]) Collections.map(ids, new ObjectMapper() {
            public Object map(Object target) {
                return Objects.getPropValue(target, "email");
            }
        }).toArray(new String[ids.size()]);
    }

    public static JavaMailSender createJavaMailSender() {
        MailConfiguration config = MailConfiguration.getInstance();
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setJavaMailProperties(config.getJavaMailProperties());
        sender.setUsername(config.getUsername());
        sender.setPassword(config.getPassword());
        return sender;
    }
}