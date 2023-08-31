package org.example;

import net.thauvin.erik.crypto.CryptoException;
import net.thauvin.erik.crypto.CryptoPrice;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;

import static java.awt.SystemColor.text;

public class MyBot extends TelegramLongPollingBot {
    public MyBot() {
        super("6461237109:AAFyjUG08qskOF6krTOMhrmEJxqLVg9PvS8");
    }

    @Override
    public void onUpdateReceived(Update update) {
        var currencies = new ArrayList<String>();
        currencies.add("BTC");
        currencies.add("ETH");
        currencies.add("LTC");
        currencies.add("DOGE");

//        for (String currency : currencies) {
//            System.out.println(currency);


            var chatId = update.getMessage().getChatId();
            // What User sends to me
            var text = update.getMessage().getText().toUpperCase();

            // What I send to user
            var message = new SendMessage();
            message.setChatId(chatId);

            boolean ok = false;

            try {
                for (int i=0; i<currencies.size(); i++) {
                    if (text.equals(currencies.get(i))) {
                        getCurrency(text, message);
                        ok = true;
                        break;
                    }
                }
                if (!ok) {
                    if (text.equals("/START")) {
                        message.setText("Hi, dude! What's up?");
                        execute(message);
                    } else if (text.equals("/ALL")) {
                        for (int i=0; i<currencies.size(); i++) {
                            getCurrency(currencies.get(i), message);
                        }
                    } else {
                        message.setText("Unknown command!");
                        execute(message);
                    }

//                    execute(message);
                }
            } catch (TelegramApiException | IOException | CryptoException e) {
                System.out.println("Error!");
            }
    }

    @Override
    public String getBotUsername() {
        return "CryptoCurry_bot";
    }

    void getCurrency(String currency, SendMessage message) throws IOException, CryptoException, TelegramApiException {
        var price = CryptoPrice.spotPrice(currency);
        message.setText(currency + " price: " + price.getAmount().doubleValue());
        execute(message);
    }

}


