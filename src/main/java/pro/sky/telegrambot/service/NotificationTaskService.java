package pro.sky.telegrambot.service;

import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;

import java.util.Collection;

@Service
public class NotificationTaskService {
    private final NotificationTaskRepository notificationTaskRepository;

    public NotificationTaskService(NotificationTaskRepository notificationTaskRepository) {
        this.notificationTaskRepository = notificationTaskRepository;
    }

    public NotificationTask save(NotificationTask notificationTask) {
        return notificationTaskRepository.save(notificationTask);
    }

    public Collection<NotificationTask> findTaskWithCurrentTime() {
        return notificationTaskRepository.findTaskWithCurrentTime();
    }
}
