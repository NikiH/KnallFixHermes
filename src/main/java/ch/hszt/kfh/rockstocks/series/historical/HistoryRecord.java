package ch.hszt.kfh.rockstocks.series.historical;

public class HistoryRecord {
	
	private double price;
	
	private double turnover;
	
	public HistoryRecord(double price, double turnover) {
		this.price = price;
		this.turnover = turnover;
	}
	
	public double getPrice() {
		return price;
	}
	
	public double getTurnover() {
		return turnover;
	}

}
