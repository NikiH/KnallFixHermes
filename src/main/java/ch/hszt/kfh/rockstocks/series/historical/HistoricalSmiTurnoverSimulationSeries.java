package ch.hszt.kfh.rockstocks.series.historical;

class HistoricalSmiTurnoverSimulationSeries extends HistoricalSmiSimulationSeries {

	@Override
	protected String getSeriesDataTypeName() {
		return "Volumina";
	}

	@Override
	protected double selectData(HistoryRecord record) {
		return record.getTurnover();
	}
		

}
