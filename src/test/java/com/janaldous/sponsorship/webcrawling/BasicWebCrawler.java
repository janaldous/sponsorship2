package com.janaldous.sponsorship.webcrawling;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.WebClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BasicWebCrawler {

	private Set<String> links;
	public static Pattern careerLinkName = Pattern.compile("career|job[s]?", Pattern.CASE_INSENSITIVE);
	public static Pattern jobName = Pattern.compile("software developer|software engineer|java|creative", Pattern.CASE_INSENSITIVE);

	public BasicWebCrawler() {
		links = new HashSet<String>();
	}

	public String getPageLinks(String URL) {
		// 4. Check if you have already crawled the URLs
		// (we are intentionally not checking for duplicate content in this example)
		if (!links.contains(URL)) {
			try {
				// 4. (i) If not add it to the index
				if (links.add(URL)) {
					System.out.println(URL);
				}

				// 2. Fetch the HTML code
				log.info("going to " + URL);
				Document document = Jsoup.connect(URL).get();
				// 3. Parse the HTML to extract links to other URLs
//                Elements linksOnPage = document.select("a[href]");

				Elements linksOnPage = document.getElementsMatchingText(careerLinkName);
				Collections.reverse(linksOnPage);

				// 5. For each extracted URL... go back to Step 4.
				for (Element page : linksOnPage) {
					
					Element page2 = findNearestAnchor(page);
					if (page2 == null) continue;
					Matcher match = careerLinkName.matcher(page2.text().toLowerCase());
					if (match.matches()) {
						if ("a".equals(page2.tagName())) {
							String urlStr = getCareerPage(page2.attr("abs:href"));
							if (urlStr != null) return urlStr;
						} else {
							// search for closest A tag
							System.out.println("im here");
						}
					}
				}
			} catch (IOException e) {
				System.err.println("For '" + URL + "': " + e.getMessage());
			}
		}
		
		return null;
	}

	private Element findNearestAnchor(Element elem) {
		if (elem == null) return null;
		for (int i = 0; i < 3; i++) {
			if (elem.tagName().equals("#root")) return null;
			if ("a".equals(elem.tagName())) {
				return elem;
			}
			elem = elem.parent();
		}
		return null;
	}

	private String getCareerPage(String URL) throws IOException {
		log.info("going to " + URL);
		Document document = Jsoup.connect(URL).get();
		// 3. Parse the HTML to extract links to other URLs
		Elements linksOnPage = document.select("a[href]");

		for (Element page : linksOnPage) {
			Matcher match = careerLinkName.matcher(page.text().toLowerCase());
			if (page.text().toLowerCase().contains("openings") || match.matches()) {
				String nextUrl = page.attr("abs:href");
				String urlStr = null;
				if (nextUrl.contains("workable")) {
					urlStr = getWorkablePage(nextUrl);
				} else {
					urlStr = getOpenings(nextUrl);
				}
				if (urlStr == null) return urlStr;
			}
		}
		return null;
	}

	private String getWorkablePage(String URL) throws IOException {
		log.info("going to " + URL);
		WebClient webClient = new WebClient();
		webClient.getPage(URL);
		// 3. Parse the HTML to extract links to other URLs
		//Elements linksOnPage = document.select("a[href]");
		return URL;
	}

	private String getOpenings(String URL) throws IOException {
		log.info("going to " + URL);
		Document document = Jsoup.connect(URL).get();
		// 3. Parse the HTML to extract links to other URLs
		Elements linksOnPage = document.select("a[href]");

		for (Element page : linksOnPage) {
			if (page.text().toLowerCase().contains("openings")) {
				System.out.println(page.attr("abs:href"));
				if (page.attr("href").contains("#")) {
					boolean success = searchJobsInDiv(document.getElementById(page.attr("href").substring(1)));
					if (success) return URL;
				}
			}
		}
		return null;
	}

	private boolean searchJobsInDiv(Element element) {
		log.info("searching jobs in div");
		Elements elementsContainingText = element.getElementsMatchingText(jobName);
		if (elementsContainingText != null) {
			System.out.println(elementsContainingText.size());
			return true;
		}
		System.out.println("no jobs with name: software developer");
		return false;
	}

}
