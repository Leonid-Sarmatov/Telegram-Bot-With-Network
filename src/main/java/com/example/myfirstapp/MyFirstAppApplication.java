package com.example.myfirstapp;

import com.example.myfirstapp.telegram.Core;
import com.example.myfirstapp.telegram.MyAmazingBot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.ApiConstants;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class MyFirstAppApplication extends SpringApplication{

	public static void main(String[] args) {

		// Вызвать метод ядра по созданию нейросети
		System.out.println("Start learning!");
		Core.creationNetwork(0.01, 200000, 11, 11, 5);

		// Регистрация созданного экземпляра нового бота
		try {
			TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
			botsApi.registerBot(new MyAmazingBot());
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

}
