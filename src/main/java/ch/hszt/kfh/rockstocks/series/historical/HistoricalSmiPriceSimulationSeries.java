package ch.hszt.kfh.rockstocks.series.historical;

class HistoricalSmiPriceSimulationSeries extends HistoricalSmiSimulationSeries {

	@Override
	protected String getSeriesDataTypeName() {
		return "Preise";
	}

	@Override
	protected double selectData(HistoryRecord record) {
		return record.getPrice();
	}
		

}
