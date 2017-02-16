package board.model;

import java.math.BigDecimal;


public class Order{

	private int id;

	private Long userId;

	private BigDecimal quantity;

	private BigDecimal price;

	private TransactionType type;

	private boolean active = true;

	public Order(int id, Long userId, BigDecimal quantity, BigDecimal price, TransactionType orderType) {
		this.id = id;
		this.type = orderType;
		this.price = price;
		this.quantity = quantity;
		this.userId = userId;
	}

	public BoardRow reduceToBoardRow(){
		BoardRow boardRow = new BoardRow();
		boardRow.price = this.price;
		boardRow.quantity = this.quantity;
		boardRow.type = this.type;
		return boardRow;
	}

	public int getId(){
		return this.id;
	}

	public boolean isActive() {
		return this.active;
	}


	public Long getUserId() {
		return userId;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public TransactionType getType() {
		return type;
	}

	public void deregister(){
		this.active = false;
	}

}


