package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    public static Pattern PATTERN = Pattern.compile("(\\d{2}\\.\\d{2}\\.\\d{4}\\s\\d{2}:\\d{2})(\\s+)(.+)");
    public static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    @Autowired
    private NotificationTaskRepository notificationTaskRepository;
    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot telegramBot;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            NotificationTask notificationTask = new NotificationTask();
            long chatId = update.message().chat().id();
            if (update.message().text().equals("/start")) {
                SendMessage message = new SendMessage(chatId, "Приветствую! Какую напоминалку ставим?");
                SendResponse response = telegramBot.execute(message);
            }
            Matcher matcher = PATTERN.matcher(update.message().text());
            if (matcher.matches()) {
                notificationTask.setChat_id(chatId);
                String data = matcher.group(1);
                String item = matcher.group(3);
                LocalDateTime messageTime = LocalDateTime.parse(data, FORMATTER);
                notificationTask.setMessage_time(messageTime);
                notificationTask.setMessage_text(item);
                notificationTask.setChat_id(chatId);
                notificationTaskRepository.save(notificationTask);
            } else {
                SendMessage message = new SendMessage(chatId,
                        "Формат сообщения должен быть \"ДД:ММ:ГГГГ ЧЧ:ММ Текст напоминания\"");
                SendResponse response = telegramBot.execute(message);
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    @Scheduled(fixedDelay = 59_000L)
    public int sendMessage() {
        notificationTaskRepository.findTaskWithCurrentTime().forEach(
                task -> {
                    long chat_id = task.getChat_id();
                    String message_text = task.getMessage_text();
                    SendMessage message = new SendMessage(chat_id, message_text);
                    SendResponse response = telegramBot.execute(message);
                });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
