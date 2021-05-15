package com.janaldous.sponsorship.repository.postgres.util;

import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

class BlankStringsArgumentsProvider implements ArgumentsProvider {

	@Override
	public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
		return Stream.of(
				Arguments.of("WORKSHARE TECHNOLOGY HOLDINGS LTD", "Workshare Ltd"),
				Arguments.of("WSO2. TELCO LTD", "WSO2 (UK) Ltd"),
				Arguments.of("WSO2 (UK) Ltd", "Xero UK Ltd"));
	}
}