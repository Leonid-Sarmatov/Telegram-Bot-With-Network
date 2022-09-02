package com.example.myfirstapp.telegram;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import static com.example.myfirstapp.telegram.Core.uploadFiles;

public class MyAmazingBot extends TelegramLongPollingBot {
    public static final String BOT_NAME = "myAboba_bot";
    public static final String BOT_TOKEN = "2146966882:AAESmBlRBvYaQZUXnM2B-6gfytMN80hTbZo";

    // Этот метод вызывается когда бот получает сообщение
    @Override
    public void onUpdateReceived(Update update) {
        // Проверяем, есть ли в обновлении сообщение и есть ли в сообщении текст
        if (update.hasMessage() && update.getMessage().hasText()) {

            SendMessage message = new SendMessage(); // // Создаем объект SendMessage с обязательными полями
            message.setChatId(update.getMessage().getChatId().toString());
            message.setText(update.getMessage().getText());
            //int i = Integer.valueOf(update.getMessage().getText());

            //message.setText(Core.responseNetwork(i));
            //System.out.println(update.getMessage().getText());

            try {
                execute(message); // Вызов метода для отправки сообщения
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            // Если в сообщении есть фото
        } else if (update.hasMessage() && update.getMessage().hasDocument()) {

            Document document = update.getMessage().getDocument();
            String fileId = document.getFileId();
            String fileName = document.getFileName();

            SendMessage message = new SendMessage();
            System.out.println("ID: " + fileId + " Name: " + fileName);

            try {
                File file = Core.uploadFiles(fileName, fileId);
                message.setChatId(update.getMessage().getChatId().toString());
                message.setText(Core.responseNetwork(file));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                execute(message); // Вызов метода для отправки сообщения
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    // этот метод всегда должен возвращать ваше имя пользователя бота
    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    // этот метод всегда должен возвращать токен бота
    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }
}