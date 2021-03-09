package com.janaldous.sponsorship.repository.postcodesio;

import java.math.BigDecimal;

import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClientException;

import com.janaldous.postcodesio.api.PostCodesApi;
import com.janaldous.postcodesio.dto.PostCodeDetail;
import com.janaldous.postcodesio.dto.PostCodeDetailResponse;

@Repository
public class PostCodesIoApi {

	@Autowired
	private PostCodesApi postCodesApi;

	public Pair<BigDecimal, BigDecimal> getCoordinate(String postcode) {
		try {
			PostCodeDetailResponse response = postCodesApi.postCodeDetailGET(postcode);
			if (response.getStatus() != null && response.getStatus() != 200) {
				throw new PostCodesIOApiException("Api responded with HTTP Status = " + response.getStatus());
			}
			PostCodeDetail result = response.getResult();
			return new Pair<BigDecimal, BigDecimal>(result.getLongitude(), result.getLatitude());
		} catch (RestClientException e) {
			throw new PostCodesIOApiException(e);
		}
	}

}
