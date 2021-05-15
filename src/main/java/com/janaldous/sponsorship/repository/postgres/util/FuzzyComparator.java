package com.janaldous.sponsorship.repository.postgres.util;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.intuit.fuzzymatcher.component.MatchService;
import com.intuit.fuzzymatcher.domain.Document;
import com.intuit.fuzzymatcher.domain.Element;
import com.intuit.fuzzymatcher.domain.ElementType;
import com.intuit.fuzzymatcher.domain.Match;

import lombok.NonNull;

@Component
public class FuzzyComparator<T> {

	private MatchService matchService = new MatchService();
	
	private NameNormalizer nameNormalizer = new NameNormalizer();
	
	private TradingAsExtractor tradingAsExtractor = new TradingAsExtractor();
	
	private boolean isSameWhenNormalized(@NonNull String a, @NonNull String b) {
		a = nameNormalizer.normalize(a);
		b = nameNormalizer.normalize(b);
		a = tradingAsExtractor.extractTradingAs(a);
		b = tradingAsExtractor.extractTradingAs(b);

		a = a.replaceAll("\\s+", "");
		b = b.replaceAll("\\s+", "");
		
		if (a.toLowerCase().endsWith("ltd")) {
			a = a.substring(0, a.length()-3);
		}
		if (b.toLowerCase().endsWith("ltd")) {
			b = b.substring(0, b.length()-3);
		}
		return a.equalsIgnoreCase(b);
	}

	public boolean isSame(@NonNull String a, @NonNull String b) {
		if (isSameWhenNormalized(a, b)) return true;
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

	public Optional<T> findMatching(String originalName, List<T> list, Function<T, String> stringExtractor) {
		
		List<Document> documentList = list.stream().map(x -> {
			String str = stringExtractor.apply(x);
			return new Document.Builder(str)
					.addElement(new Element.Builder<String>().setValue(str)
							.setType(ElementType.TEXT)
							.createElement())
					.createDocument();
		}).collect(Collectors.toList());
		
		documentList.add(new Document.Builder(originalName)
					.addElement(new Element.Builder<String>().setValue(originalName)
							.setType(ElementType.TEXT)
							.createElement())
					.createDocument());

		Map<String, List<Match<Document>>> result = matchService.applyMatchByDocId(documentList);
		
		if (result.containsKey(originalName)) {
			String mainResult = result.get(originalName).get(0).getData().getKey();
			
			return list.stream().filter(x -> stringExtractor.apply(x).equals(mainResult)).findAny();
		}
		return Optional.empty();
	}

}
