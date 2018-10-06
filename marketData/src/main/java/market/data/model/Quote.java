package market.data.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Quote implements Serializable {
	private String symbol;
	private String companyName;
	private String primaryExchange;
	private String sector;
	private String calculationPrice;
	private String open;
	private String openTime;
	private String close;
	private String closeTime;
	private String high;
	private String low;
	private String latestPrice;
	private String latestSource;
	private String latestTime;
	private String latestUpdate;
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getPrimaryExchange() {
		return primaryExchange;
	}
	public void setPrimaryExchange(String primaryExchange) {
		this.primaryExchange = primaryExchange;
	}
	public String getSector() {
		return sector;
	}
	public void setSector(String sector) {
		this.sector = sector;
	}
	public String getCalculationPrice() {
		return calculationPrice;
	}
	public void setCalculationPrice(String calculationPrice) {
		this.calculationPrice = calculationPrice;
	}
	public String getOpen() {
		return open;
	}
	public void setOpen(String open) {
		this.open = open;
	}
	public String getOpenTime() {
		return openTime;
	}
	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}
	public String getClose() {
		return close;
	}
	public void setClose(String close) {
		this.close = close;
	}
	public String getCloseTime() {
		return closeTime;
	}
	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
	}
	public String getHigh() {
		return high;
	}
	public void setHigh(String high) {
		this.high = high;
	}
	public String getLow() {
		return low;
	}
	public void setLow(String low) {
		this.low = low;
	}
	public String getLatestPrice() {
		return latestPrice;
	}
	public void setLatestPrice(String latestPrice) {
		this.latestPrice = latestPrice;
	}
	public String getLatestSource() {
		return latestSource;
	}
	public void setLatestSource(String latestSource) {
		this.latestSource = latestSource;
	}
	public String getLatestTime() {
		return latestTime;
	}
	public void setLatestTime(String latestTime) {
		this.latestTime = latestTime;
	}
	public String getLatestUpdate() {
		return latestUpdate;
	}
	public void setLatestUpdate(String latestUpdate) {
		this.latestUpdate = latestUpdate;
	}
	@Override
	public String toString() {
		return "Quote [symbol=" + symbol + ", companyName=" + companyName + ", primaryExchange=" + primaryExchange
				+ ", sector=" + sector + ", calculationPrice=" + calculationPrice + ", open=" + open + ", openTime="
				+ openTime + ", close=" + close + ", closeTime=" + closeTime + ", high=" + high + ", low=" + low
				+ ", latestPrice=" + latestPrice + ", latestSource=" + latestSource + ", latestTime=" + latestTime
				+ ", latestUpdate=" + latestUpdate + "]";
	}
}
