package org.example;

import net.thauvin.erik.crypto.CryptoException;
import net.thauvin.erik.crypto.CryptoPrice;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static java.awt.SystemColor.text;

public class MyBot extends TelegramLongPollingBot {
    public MyBot() {
        super("6461237109:AAFyjUG08qskOF6krTOMhrmEJxqLVg9PvS8");
    }

    @Override
    public void onUpdateReceived(Update update) {
        boolean ok = false;
        var currencies = new ArrayList<String>();
        currencies.add("BTC");
        currencies.add("ETH");
        currencies.add("LTC");
        currencies.add("DOGE");

        var chatId = update.getMessage().getChatId();
        var text = update.getMessage().getText().toUpperCase();

        String[] separatedCurs = text.split("[,\\s.]+");

        try {
            var message = new SendMessage();
            message.setChatId(chatId);

            if (separatedCurs.length > 1) {
                StringBuilder stringCurrencies = new StringBuilder();
                for (String sep : separatedCurs) {
                    for (String curr : currencies) {
                        if (sep.equals(curr)) {
                            var price = CryptoPrice.spotPrice(curr);
                            stringCurrencies.append(curr).append(": ").append(price.getAmount().doubleValue()).append(", ");
                        }
                    }
                }
                stringCurrencies.setLength(stringCurrencies.length() - 2);
                message.setText(stringCurrencies.toString());
                execute(message);
            }
            else {
                for (String s : currencies) {
                    if (text.equals(s)) {
                        getCurrency(text, message);
                        execute(message);
                        ok = true;
                        break;
                    }
                }
                if (!ok) {
                    if (text.equals("/START")) {
                        message.setText("Hi, dude! What's up?");
                        execute(message);
                    } else if (text.equals("/ALL")) {
                        for (String currency : currencies) {
                            getCurrency(currency, message);
                            execute(message);
                        }
                    } else {
                        message.setText("Unknown command!");
                        execute(message);
                    }
                }
            }

        } catch (TelegramApiException | IOException | CryptoException e) {
            System.out.println("Error!");
        }
    }

    void getCurrency(String currency, SendMessage message) throws IOException, CryptoException, TelegramApiException {
        var price = CryptoPrice.spotPrice(currency);
        message.setText(currency + " price: " + price.getAmount().doubleValue());
    }

    @Override
    public String getBotUsername() {
        return "CryptoCurry_bot";
    }

}


