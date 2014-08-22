package org.assertj.examples.extractor;

import java.util.List;

import org.assertj.core.api.iterable.Extractor;
import org.assertj.examples.data.BasketBallPlayer;


public class BasketballExtractors {
	BasketballExtractors() {
		
	}
	
	public static Extractor<BasketBallPlayer, List<BasketBallPlayer>> teammates() {
		return new BasketBallTeammatesExtractor();
		
	}

}
