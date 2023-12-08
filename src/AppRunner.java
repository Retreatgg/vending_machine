import enums.ActionLetter;
import model.*;
import util.UniversalArray;
import util.UniversalArrayImpl;

import java.util.Scanner;

public class AppRunner {

    private final UniversalArray<Product> products = new UniversalArrayImpl<>();

    private final CoinAcceptor coinAcceptor;
    private static boolean isExit = false;
    private static int choosenMethodPay = 1;

    private AppRunner() {
        products.addAll(new Product[]{
                new Water(ActionLetter.B, 20),
                new CocaCola(ActionLetter.C, 50),
                new Soda(ActionLetter.D, 30),
                new Snickers(ActionLetter.E, 80),
                new Mars(ActionLetter.F, 80),
                new Pistachios(ActionLetter.G, 130)
        });
        coinAcceptor = new CoinAcceptor(100);
    }

    public static void run(Card card) {
        AppRunner app = new AppRunner();
        while (!isExit) {
            app.startSimulation(card);
        }
    }

    private void startSimulation(Card card) {
        print("В автомате доступны:");
        showProducts(products);

        print("Монет на сумму: " + Math.abs(coinAcceptor.getAmount()));
        print("На карте: " + Math.abs(card.getAmount()));

        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        allowProducts.addAll(getAllowedProducts(card).toArray());
        chooseAction(allowProducts, card);

    }

    private UniversalArray<Product> getAllowedProducts(Card card) {
        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        if(choosenMethodPay == 1){
            for (int i = 0; i < products.size(); i++) {
                if (coinAcceptor.getAmount() >= products.get(i).getPrice()) {
                    allowProducts.add(products.get(i));
                }
            }
        }
        if(choosenMethodPay == 2){
            for (int i = 0; i < products.size(); i++) {
                if (card.getAmount() >= products.get(i).getPrice()) {
                    allowProducts.add(products.get(i));
                }
            }
        }
        return allowProducts;
    }

    private void chooseAction(UniversalArray<Product> products, Card card) {
        print(" a - Пополнить баланс");
        showActions(products);
        print(" h - Выйти");
        String action = fromConsole().substring(0, 1);
        if ("a".equalsIgnoreCase(action)) {
            print("Выберите способ оплаты и что хотите пополнить: ");
            choosenMethodPay = chooseMoneyOrCoin();
            payment(choosenMethodPay, card);
            return;
        }
        try {
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getActionLetter().equals(ActionLetter.valueOf(action.toUpperCase()))) {
                    paymentMethod(choosenMethodPay, i, card);
                }
            }
        } catch (IllegalArgumentException e) {
            if ("h".equalsIgnoreCase(action)) {
                isExit = true;
            } else {
                print("Недопустимая буква. Попрбуйте еще раз.");
            }
        }
    }

    private void payment(int choosenMethodPay, Card card){
        switch (choosenMethodPay){
            case 1:
                print("Вы выбрали способ оплаты монетами");
                coinAcceptor.setAmount(coinAcceptor.getAmount() + 10);
                print("Вы пополнили баланс монетами на 10");
                break;
            case 2:
                print("Вы выбрали способ оплаты картой");
                try{
                    System.out.print("Введите номер карты: ");
                    int numberCard = Integer.parseInt(fromConsole());
                    System.out.print("Введите пароль карты: ");
                    int passwordCard = Integer.parseInt(fromConsole());
                    if(numberCard == card.getNumber() && passwordCard == card.getPasswrod()) {
                        card.setAmount(card.getAmount() + 20);
                        print("Вы пополнили баланс деньгами на 20");
                        break;
                    } else {
                        System.out.println("Неверный номер или пароль! Попытайтесь снова.");
                    }
                } catch (NumberFormatException e){
                    System.out.println("Нельзя ввести строку");
                    payment(choosenMethodPay, card);
                }
            default:break;
        }
    }

    private int chooseMoneyOrCoin() {
        print("1 - Монеты");
        print("2 - Деньги");
        int pay = 0;
        try {
            pay = Integer.parseInt(fromConsole());
            if (pay >= 3 || pay <= 0) {
                throw new Exception("Вы выбрали неверное действия");
            }
        } catch (NumberFormatException eg) {
            print("Вы ввели строку! Попробуйте снова");
            chooseMoneyOrCoin();
        } catch (Exception e) {
            print(e.getMessage());
            chooseMoneyOrCoin();
        }
        return pay;
    }

    private void paymentMethod(int chooseMoneyOrcoin, int i, Card card){
        switch (chooseMoneyOrcoin){
            case 1:
                coinAcceptor.setAmount(Math.abs(coinAcceptor.getAmount() - products.get(i).getPrice()));
                print("Вы купили " + products.get(i).getName());
                break;
            case 2:
                 card.setAmount(Math.abs((card.getAmount() - products.get(i).getPrice())));
                print("Вы купили " + products.get(i).getName());
                break;
            default:break;
        }
    }

    private void showActions(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(String.format(" %s - %s", products.get(i).getActionLetter().getValue(), products.get(i).getName()));
        }
    }

    private String fromConsole() {
        return new Scanner(System.in).nextLine();
    }

    private void showProducts(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(products.get(i).toString());
        }
    }

    private void print(String msg) {
        System.out.println(msg);
    }
}
