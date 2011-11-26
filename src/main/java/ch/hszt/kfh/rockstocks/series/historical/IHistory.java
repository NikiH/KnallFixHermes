package ch.hszt.kfh.rockstocks.series.historical;

import java.util.Set;

public interface IHistory {
	
	HistoryCollection get(String isin);

	public Set<String> getIsins();

}
