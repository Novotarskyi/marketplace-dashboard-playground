package board.model;


import java.math.BigDecimal;
import java.math.RoundingMode;

public class BoardRow {
	public BigDecimal quantity;

	public BigDecimal price;

	public TransactionType type;


	public String toString() {
		return type.toString() +
				"| " +
				quantity.setScale(1, RoundingMode.HALF_UP).toString() +
				" kg for £" +
				price.setScale(0, RoundingMode.HALF_UP).toString() +
				"\n";
	}
}
