package market.data.model;

import java.io.Serializable;

public class Stock implements Serializable {
//abopnskjld1
	private double ask;
	private double bid;
	private double open;
	private double previousClose;
	private String name;
	private String symbol;
	private double _52_week_high;
	private double _52_week_low;
	private String lastTradeWithTime;
	private String lastTradeDate;
	
	public Stock(double ask, double bid, double open, double previousClose,
			String name, String symbol, double _52_week_high,
			double _52_week_low, String lastTradeWithTime, String lastTradeDate) {
		super();
		this.ask = ask;
		this.bid = bid;
		this.open = open;
		this.previousClose = previousClose;
		this.name = name;
		this.symbol = symbol;
		this._52_week_high = _52_week_high;
		this._52_week_low = _52_week_low;
		this.lastTradeWithTime = lastTradeWithTime;
		this.lastTradeDate = lastTradeDate;
	}

	public double getAsk() {
		return ask;
	}

	public double getBid() {
		return bid;
	}

	public double getOpen() {
		return open;
	}

	public double getPreviousClose() {
		return previousClose;
	}

	public String getName() {
		return name;
	}

	public String getSymbol() {
		return symbol;
	}

	public double get_52_week_high() {
		return _52_week_high;
	}

	public double get_52_week_low() {
		return _52_week_low;
	}

	public String getLastTradeWithTime() {
		return lastTradeWithTime;
	}

	public String getLastTradeDate() {
		return lastTradeDate;
	}

	@Override
	public String toString() {
		return "Stock [ask=" + ask + ", bid=" + bid + ", open=" + open
				+ ", previousClose=" + previousClose + ", name=" + name
				+ ", symbol=" + symbol + ", _52_week_high=" + _52_week_high
				+ ", _52_week_low=" + _52_week_low + ", lastTradeWithTime="
				+ lastTradeWithTime + ", lastTradeDate=" + lastTradeDate + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stock other = (Stock) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (symbol == null) {
			if (other.symbol != null)
				return false;
		} else if (!symbol.equals(other.symbol))
			return false;
		return true;
	}

}
