package board.service;

import board.model.Order;
import board.model.TransactionType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderManagementService {

	private List<Order> orderLedger;


	public OrderManagementService() {
		this.orderLedger = new ArrayList<>();
	}

	public List<Order> getOrderLedger() {
		return this.orderLedger;
	}

	public Order placeOrder(Long userId, BigDecimal quantity, BigDecimal price, TransactionType orderType) {
		Order incomingOrder = new Order(orderLedger.size(), userId, quantity, price, orderType);
		orderLedger.add(incomingOrder);

		return incomingOrder;
	}

	public Order cancelOrder(int id) {
		Order order;
		if (id < this.orderLedger.size() -1) {
			order = this.orderLedger.get(id);

			if (order != null) {
				this.orderLedger.get(id).deregister();
			}

			return order;
		}

		return null;
	}
}
