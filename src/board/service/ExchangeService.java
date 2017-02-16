package board.service;


import board.model.BoardRow;
import board.model.OperationType;
import board.model.Order;
import java.math.BigDecimal;
import java.util.*;

public class ExchangeService {

	private TreeMap<BigDecimal, BoardRow> buysMap;

	private TreeMap<BigDecimal, BoardRow> sellsMap;


	public ExchangeService() {
		this.buysMap = new TreeMap<>((BigDecimal a, BigDecimal b) -> b.compareTo(a));
		this.sellsMap = new TreeMap<>();
	}

	public Object[] getBuys() {
		return buysMap.values().toArray();
	}


	public Object[] getSells() {
		return sellsMap.values().toArray();
	}

	public void onOrderOperation(Order order, OperationType operationType) {
		if (order != null) {
			Map<BigDecimal, BoardRow> dataStore;
			switch (order.getType()) {
				case BUY:
					dataStore = this.buysMap;
					break;
				case SELL:
					dataStore = this.sellsMap;
					break;
				default:
					return;
			}

			switch (operationType) {
				case SUBMIT:
					processSubmit(dataStore, order);
					break;
				case CANCEL:
					processCancel(dataStore, order);
					break;
			}
		}
	}

	private void processSubmit(Map<BigDecimal, BoardRow> dataStore, Order order) {
		BoardRow line = dataStore.get(order.getPrice());
		if (line != null) {
			line.quantity = line.quantity.add(order.getQuantity());
		} else {
			line = order.reduceToBoardRow();
		}
		dataStore.put(line.price, line);
	}

	private void processCancel(Map<BigDecimal, BoardRow> dataStore, Order order) {
		BoardRow line = dataStore.get(order.getPrice());
		line.quantity = line.quantity.subtract(order.getQuantity());
		if (line.quantity.compareTo(BigDecimal.ZERO) == 0){
			dataStore.remove(line.price);
		} else {
			dataStore.put(line.price, line);
		}
	}

	public BigDecimal currentSpread(){
		BigDecimal highestBuy = this.buysMap.firstKey();
		if (highestBuy == null) highestBuy = BigDecimal.ZERO;
		BigDecimal lowestSell = this.sellsMap.firstKey();
		if (lowestSell == null) lowestSell = BigDecimal.ZERO;
		return highestBuy.subtract(lowestSell);
	}

}
