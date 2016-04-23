package cn.accessbright.blade.core.mail;

import cn.accessbright.blade.core.utils.collections.Collections;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MailManager {
    private String pattern = "yyyy-MM-dd hh:mm:ss";
    private EntityManager entityManager;

    private MailEventListener mailEventListener;

    /**
     * 保存消息记录
     *
     * @param records
     */
    public void saveMailRecord(List records) {
        if (!Collections.isNotEmpty(records)) {
            for (int i = 0; i < records.size(); i++) {
                NotifyRecord record = (NotifyRecord) records.get(i);
                Query query = entityManager.createQuery("from NotifyRecord n where n.status='" + NotifyRecord.STATUS_NOT_SEND + "' and n.jobId='" + record.getJobId() + "'");
                List notifyList = query.getResultList();
                if (Collections.isNotEmpty(notifyList)) {
                    for (int j = 0; j < notifyList.size(); j++) {
                        NotifyRecord recorded = (NotifyRecord) notifyList.get(j);
                        recorded.cancel();
                        entityManager.persist(recorded);
                    }
                }
                entityManager.persist(record);
            }
        }
    }

    /**
     * 通知发送消息
     *
     * @param now
     */

    public void send(Date now) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);

        String hql = "from NotifyRecord n where n.status='" + NotifyRecord.STATUS_NOT_SEND
                + "' and to_char(n.sendDate,'" + pattern + "')<='" + format.format(now) + "' "
                + " order by n.sendDate asc";

        Query query = entityManager.createQuery(hql);
        List records = query.getResultList();
        if (Collections.isNotEmpty(records)) {
            for (int i = 0; i < records.size(); i++) {
                NotifyRecord record = (NotifyRecord) records.get(i);
                mailEventListener.sendEmail(record);
                entityManager.persist(record);
            }
        }
    }
}