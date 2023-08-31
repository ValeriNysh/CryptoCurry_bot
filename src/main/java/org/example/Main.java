package org.example;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args)  throws Exception {
        var api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(new MyBot());


    }
}





// CryptoCurry_bot
// 6461237109:AAFyjUG08qskOF6krTOMhrmEJxqLVg9PvS8