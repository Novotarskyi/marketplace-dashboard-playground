package board.interfaces;

import board.model.BoardRow;
import board.model.OperationType;
import board.model.TransactionType;
import board.service.OrderManagementService;


import board.model.Order;
import board.service.ExchangeService;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class MarketPlaceCoordinator {

	private OrderManagementService orderManagementService;

	private ExchangeService exchangeService;

	public MarketPlaceCoordinator() {

		this.orderManagementService = new OrderManagementService();
		this.exchangeService = new ExchangeService();
	}

	public void rebuildFromLedger() {
		List<Order> ledger = this.orderManagementService.getOrderLedger()
						.stream()
						.filter(order -> order.isActive())
						.collect(Collectors.toList());

		for (Order order: ledger) {
			exchangeService.onOrderOperation(order, OperationType.SUBMIT);
		}
	}

	public void killStats() {
		this.exchangeService = new ExchangeService();
	}

	public int placeOrder(Long userId, BigDecimal quantity, BigDecimal price, TransactionType orderType) {
		Order placedOrder = orderManagementService.placeOrder(userId, quantity, price, orderType);
		exchangeService.onOrderOperation(placedOrder, OperationType.SUBMIT);
		return placedOrder.getId();
	}

	public void cancelOrder(int id) {
		Order cancelledOrder = orderManagementService.cancelOrder(id);
		exchangeService.onOrderOperation(cancelledOrder, OperationType.CANCEL);
	}

	public BigDecimal getCurrentSpread() {
		return this.exchangeService.currentSpread();
	}

	public Object[] getBuys() {
		return this.exchangeService.getBuys();
	}

	public Object[] getSells() {
		return this.exchangeService.getSells();
	}

	public Object[] getBoard() {
		Object[] buys = this.getBuys();
		Object[] sells = this.getSells();
		Object[] allOrders = new BoardRow[buys.length + sells.length];

		System.arraycopy(buys, 0, allOrders, 0, buys .length);
		System.arraycopy(sells, 0, allOrders, buys.length, sells.length);

		return allOrders;
	}

	public String getBoardString() {
		Object[] board = this.getBoard();
		String result = "";
		for (Object row: board) {
			result = result + row.toString();
		}

		return result;
	}




}