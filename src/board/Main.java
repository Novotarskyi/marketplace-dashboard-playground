package board;

import board.interfaces.MarketPlaceCoordinator;
import board.model.BoardRow;
import board.model.TransactionType;

import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) {
        MarketPlaceCoordinator coordinator = new MarketPlaceCoordinator();

        System.out.println("Initial merging");
        int id1 = coordinator.placeOrder(Long.valueOf(1), BigDecimal.valueOf(3.5), BigDecimal.valueOf(306), TransactionType.SELL);
        int id2 = coordinator.placeOrder(Long.valueOf(2), BigDecimal.valueOf(1.2), BigDecimal.valueOf(310), TransactionType.SELL);
        int id3 = coordinator.placeOrder(Long.valueOf(3), BigDecimal.valueOf(1.5), BigDecimal.valueOf(307), TransactionType.SELL);
        int id4 = coordinator.placeOrder(Long.valueOf(4), BigDecimal.valueOf(2.0), BigDecimal.valueOf(306), TransactionType.SELL);

        System.out.println(coordinator.getBoardString());
        System.out.println("--------------");

        System.out.println("Removing order");
        coordinator.cancelOrder(id1);

        System.out.println(coordinator.getBoardString());
        System.out.println("--------------");
        System.out.println("Removing order and the quantity becomes 0");
        coordinator.cancelOrder(id4);

        System.out.println(coordinator.getBoardString());
        System.out.println("--------------");
        System.out.println("Removing a non existing order does nothing");
        coordinator.cancelOrder(123456789);
        coordinator.cancelOrder(1234);

        System.out.println(coordinator.getBoardString());
        System.out.println("--------------");
        System.out.println("Adding some sells");
        int id9 = coordinator.placeOrder(Long.valueOf(1), BigDecimal.valueOf(3.5), BigDecimal.valueOf(306), TransactionType.SELL);
        int id10 = coordinator.placeOrder(Long.valueOf(4), BigDecimal.valueOf(2.0), BigDecimal.valueOf(306), TransactionType.SELL);
        int id200 = coordinator.placeOrder(Long.valueOf(4), BigDecimal.valueOf(70.0), BigDecimal.valueOf(100), TransactionType.SELL);
        System.out.println(coordinator.getBoardString());
        System.out.println("--------------");
        System.out.println("And then some buys");
        int id5 = coordinator.placeOrder(Long.valueOf(1), BigDecimal.valueOf(3.5), BigDecimal.valueOf(306), TransactionType.BUY);
        int id6 = coordinator.placeOrder(Long.valueOf(2), BigDecimal.valueOf(1.2), BigDecimal.valueOf(310), TransactionType.BUY);
        int id7 = coordinator.placeOrder(Long.valueOf(3), BigDecimal.valueOf(1.5), BigDecimal.valueOf(500), TransactionType.BUY);
        int id8 = coordinator.placeOrder(Long.valueOf(4), BigDecimal.valueOf(2.0), BigDecimal.valueOf(306), TransactionType.BUY);

        System.out.println(coordinator.getBoardString());

        System.out.println("--------------");

        System.out.println("Getting Current spread: " + coordinator.getCurrentSpread());

        System.out.println("--------------");

        coordinator.killStats();
        System.out.println("All stats killed - but the ledger is safe! Current board:");
        System.out.println(coordinator.getBoardString());
        System.out.println("--------------");


        coordinator.rebuildFromLedger();
        System.out.println(coordinator.getBoardString());
        System.out.println("--------------");
    }
}
