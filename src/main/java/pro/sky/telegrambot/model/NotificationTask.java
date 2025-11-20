package pro.sky.telegrambot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity(name = "notification_task")
public class NotificationTask {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private long chat_id;
    private String message_text;
    private LocalDateTime message_time;

    public NotificationTask() {
    }

    public NotificationTask(Integer id, long chat_id, String message_text, LocalDateTime message_time) {
        this.id = id;
        this.chat_id = chat_id;
        this.message_text = message_text;
        this.message_time = message_time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public long getChat_id() {
        return chat_id;
    }

    public void setChat_id(long chat_id) {
        this.chat_id = chat_id;
    }

    public String getMessage_text() {
        return message_text;
    }

    public void setMessage_text(String message_text) {
        this.message_text = message_text;
    }

    public LocalDateTime getMessage_time() {
        return message_time;
    }

    public void setMessage_time(LocalDateTime message_time) {
        this.message_time = message_time;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        NotificationTask that = (NotificationTask) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
