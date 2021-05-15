package com.janaldous.sponsorship.repository.postgres.util;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.intuit.fuzzymatcher.component.MatchService;
import com.intuit.fuzzymatcher.domain.Document;
import com.intuit.fuzzymatcher.domain.Element;
import com.intuit.fuzzymatcher.domain.ElementType;
import com.intuit.fuzzymatcher.domain.Match;

public class FuzzyCompare {

	private MatchService matchService = new MatchService();

	public boolean isSame(String a, String b) {
		String[][] input = {
				{ a, a },
				{ b, b },
		};

		List<Document> documentList = Arrays.asList(input).stream().map(contact -> {
			return new Document.Builder(contact[0])
					.addElement(new Element.Builder<String>().setValue(contact[1])
							.setType(ElementType.TEXT)
							.createElement())
					.createDocument();
		}).collect(Collectors.toList());

		Map<String, List<Match<Document>>> result = matchService.applyMatchByDocId(documentList);

		return result.get(a) != null;
	}

}
