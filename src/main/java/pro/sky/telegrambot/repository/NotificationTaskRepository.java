package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambot.model.NotificationTask;

import java.util.Collection;

@Repository
public interface NotificationTaskRepository extends JpaRepository<NotificationTask, Long> {

    @Query (value = "SELECT * FROM notification_task WHERE date_trunc('minute', message_time) = " +
            "date_trunc('minute', NOW())", nativeQuery = true)
    Collection<NotificationTask> findTaskWithCurrentTime();
}
