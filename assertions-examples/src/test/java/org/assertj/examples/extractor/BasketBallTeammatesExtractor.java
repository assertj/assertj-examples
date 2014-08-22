package org.assertj.examples.extractor;

import java.util.List;

import org.assertj.core.api.iterable.Extractor;
import org.assertj.examples.data.BasketBallPlayer;

class BasketBallTeammatesExtractor implements Extractor<BasketBallPlayer, List<BasketBallPlayer>> {

	@Override
	public List<BasketBallPlayer> extract(BasketBallPlayer input) {
		return input.getTeamMates();
	}

}
