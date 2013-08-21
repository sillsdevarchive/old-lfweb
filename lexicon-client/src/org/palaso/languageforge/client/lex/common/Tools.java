package org.palaso.languageforge.client.lex.common;

import java.util.ArrayList;
import java.util.Date;

import org.palaso.languageforge.client.lex.model.CurrentEnvironmentDto;
import org.palaso.languageforge.client.lex.model.EntryMetadataDto;
import org.palaso.languageforge.client.lex.model.Example;
import org.palaso.languageforge.client.lex.model.LexiconEntryDto;
import org.palaso.languageforge.client.lex.model.Sense;
import org.palaso.languageforge.client.lex.view.SenseView;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class Tools {

	/**
	 * Ref: http://www.merriampark.com/ldjava.htm
	 * 
	 * @param s
	 * @param t
	 * @return
	 */

	public static int getLevenshteinDistance(String s, String t) {
		if (s == null || t == null) {
			throw new IllegalArgumentException("Strings must not be null");
		}

		/*
		 * The difference between this impl. and the previous is that, rather
		 * than creating and retaining a matrix of size s.length()+1 by
		 * t.length()+1, we maintain two single-dimensional arrays of length
		 * s.length()+1. The first, d, is the 'current working' distance array
		 * that maintains the newest distance cost counts as we iterate through
		 * the characters of String s. Each time we increment the index of
		 * String t we are comparing, d is copied to p, the second int[]. Doing
		 * so allows us to retain the previous cost counts as required by the
		 * algorithm (taking the minimum of the cost count to the left, up one,
		 * and diagonally up and to the left of the current cost count being
		 * calculated). (Note that the arrays aren't really copied anymore, just
		 * switched...this is clearly much better than cloning an array or doing
		 * a System.arraycopy() each time through the outer loop.)
		 * 
		 * Effectively, the difference between the two implementations is this
		 * one does not cause an out of memory condition when calculating the LD
		 * over two very large strings.
		 */

		int n = s.length(); // length of s
		int m = t.length(); // length of t

		if (n == 0) {
			return m;
		} else if (m == 0) {
			return n;
		}

		int p[] = new int[n + 1]; // 'previous' cost array, horizontally
		int d[] = new int[n + 1]; // cost array, horizontally
		int _d[]; // placeholder to assist in swapping p and d

		// indexes into strings s and t
		int i; // iterates through s
		int j; // iterates through t

		char t_j; // jth character of t

		int cost; // cost

		for (i = 0; i <= n; i++) {
			p[i] = i;
		}

		for (j = 1; j <= m; j++) {
			t_j = t.charAt(j - 1);
			d[0] = j;

			for (i = 1; i <= n; i++) {
				cost = s.charAt(i - 1) == t_j ? 0 : 1;
				// minimum of cell to the left+1, to the top+1, diagonally left
				// and up +cost
				d[i] = Math.min(Math.min(d[i - 1] + 1, p[i] + 1), p[i - 1]
						+ cost);
			}

			// copy current distance counts to 'previous row' distance counts
			_d = p;
			p = d;
			d = _d;
		}

		// our last action in the above loop was to switch d and p, so p now
		// actually has the most recent cost counts
		return p[n];
	}

	static public native JavaScriptObject cloneJavaScriptObject(
			JavaScriptObject source)/*-{
		return JSON.parse(JSON.stringify(source));
	}-*/;

	static public LexiconEntryDto updateMetadata(LexiconEntryDto modified,
			LexiconEntryDto original) {
		int unixTimeStamp = (int) (new Date().getTime() / 1000);
		String userId = CurrentEnvironmentDto.getCurrentUser().getId();
		String userName = CurrentEnvironmentDto.getCurrentUser().getName();
		if (original == null) {

			ConsoleLog.log("Metadata for new Entry");
			// new Entry, just loop and add all new metadata
			EntryMetadataDto newMetadata = EntryMetadataDto.createObject()
					.cast();
			newMetadata.setCreatedby(userName);
			newMetadata.setCreatedbyId(userId);
			newMetadata.setCreatedDate(unixTimeStamp);
			newMetadata.setModifiedBy(userName);
			newMetadata.setModifiedById(userId);
			newMetadata.setModifiedDate(unixTimeStamp);
			modified.setMetadata(newMetadata);

			for (int i = 0; i < modified.getSenseCount(); i++) {
				// we have sense
				Sense sense = modified.getSense(i);
				sense.setMetadata(newMetadata);

				for (int j = 0; j < sense.getExampleCount(); j++) {
					// we have example
					Example example = sense.getExample(j);
					example.setMetadata(newMetadata);
				}
			}

		} else {
			ConsoleLog.log("Update metadata for Entry");
			// update, so we need compare both modified and original to find
			// what is different.
			// -->Entry Level
			if (modified.getEntry().keys().length() == original.getEntry()
					.keys().length()) {
				boolean isChange = false;
				// the same size loop to check
				for (int i = 0; i < modified.getEntry().keys().length(); i++) {
					String key = modified.getEntry().keys().get(i);
					if (original.getEntry().value(key) == null) {
						ConsoleLog
								.log("EntryCheck: original value with Key not found - "
										+ key);
						isChange = true;
						break;
					} else {
						if (modified.getEntry().value(key).trim()
								.compareTo(original.getEntry().value(key).trim()) != 0) {
							ConsoleLog.log("EntryCheck: value not the same - "
									+ modified.getEntry().value(key) + " / "
									+ original.getEntry().value(key));
							ConsoleLog.log("EntryCheck: value not the same - "
									+ modified.getEntry().value(key).length()
									+ " / "
									+ original.getEntry().value(key).length());
							isChange = true;
							break;
						}
					}
				}
				if (isChange) {
					ConsoleLog.log("Change metadata for Entry");
					modified.getMetadata().setModifiedBy(userName);
					modified.getMetadata().setModifiedById(userId);
					modified.getMetadata().setModifiedDate(unixTimeStamp);
				}
			} else {
				// size diff, entry changed
				ConsoleLog.log("Change metadata for Entry By size changed");
				modified.getMetadata().setModifiedBy(userName);
				modified.getMetadata().setModifiedById(userId);
				modified.getMetadata().setModifiedDate(unixTimeStamp);
			}

			// -->Sense Level
			for (int i = 0; i < modified.getSenseCount(); i++) {
				Sense modifiedSense = modified.getSense(i);
				Sense originalSense = null;
				boolean isSenseChange = false;
				if (i < original.getSenseCount()) {
					originalSense = original.getSense(i);
				}

				if (originalSense != null) {
					// we have a sense at the same index with modified one.
					if (!(modifiedSense.getDescription().compareTo(
							originalSense.getDescription()) == 0
							|| modifiedSense.getPOS().compareTo(
									originalSense.getPOS()) == 0
							|| modifiedSense.getSemanticDomainName().compareTo(
									originalSense.getSemanticDomainName()) == 0 || modifiedSense
							.getSemanticDomainValue().compareTo(
									originalSense.getSemanticDomainValue()) == 0)) {
						isSenseChange = true;
					} else {
						// check Definition
						for (int j = 0; j < modifiedSense.getDefinition()
								.keys().length(); j++) {
							String key = modifiedSense.getDefinition().keys()
									.get(j);
							if (originalSense.getDefinition().value(key) == null) {
								isSenseChange = true;
								break;
							} else {
								if (modifiedSense
										.getDefinition()
										.value(key)
										.compareTo(
												originalSense.getDefinition()
														.value(key)) != 0) {
									isSenseChange = true;
									break;
								}
							}
						}
					}
					if (isSenseChange) {
						ConsoleLog.log("SenseChanged compared to original");
						modifiedSense.getMetadata().setModifiedBy(userName);
						modifiedSense.getMetadata().setModifiedById(userId);
						modifiedSense.getMetadata().setModifiedDate(
								unixTimeStamp);
					}
				} else {
					// no original find, could be new.

					if (modifiedSense.getMetadata().getCreatedbyId()
							.equalsIgnoreCase("")) {
						ConsoleLog.log("Could be new Sense");
						// no creator, new one
						modifiedSense.getMetadata().setCreatedby(userName);
						modifiedSense.getMetadata().setCreatedbyId(userId);
						modifiedSense.getMetadata().setCreatedDate(
								unixTimeStamp);
						modifiedSense.getMetadata().setModifiedBy(userName);
						modifiedSense.getMetadata().setModifiedById(userId);
						modifiedSense.getMetadata().setModifiedDate(
								unixTimeStamp);
					} else {
						ConsoleLog.log("SenseChanged");
						// update
						modifiedSense.getMetadata().setModifiedBy(userName);
						modifiedSense.getMetadata().setModifiedById(userId);
						modifiedSense.getMetadata().setModifiedDate(
								unixTimeStamp);
					}
				}

				// -->Example Level

			}

		}

		return modified;
	}
}
