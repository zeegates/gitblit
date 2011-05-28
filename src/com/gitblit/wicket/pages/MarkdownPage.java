/*
 * Copyright 2011 gitblit.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gitblit.wicket.pages;

import java.text.ParseException;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

import com.gitblit.utils.JGitUtils;
import com.gitblit.utils.MarkdownUtils;
import com.gitblit.wicket.WicketUtils;

public class MarkdownPage extends RepositoryPage {

	public MarkdownPage(PageParameters params) {
		super(params);

		final String markdownPath = WicketUtils.getPath(params);

		Repository r = getRepository();
		RevCommit commit = JGitUtils.getCommit(r, objectId);

		// markdown page links
		add(new Label("blameLink", getString("gb.blame")));
		add(new BookmarkablePageLink<Void>("historyLink", HistoryPage.class,
				WicketUtils.newPathParameter(repositoryName, objectId, markdownPath)));
		add(new BookmarkablePageLink<Void>("rawLink", RawPage.class, WicketUtils.newPathParameter(
				repositoryName, objectId, markdownPath)));
		add(new BookmarkablePageLink<Void>("headLink", MarkdownPage.class,
				WicketUtils.newPathParameter(repositoryName, Constants.HEAD, markdownPath)));

		// Read raw markdown content and transform it to html
		String markdownText = JGitUtils.getRawContentAsString(r, commit, markdownPath);
		String htmlText;
		try {
			htmlText = MarkdownUtils.transformMarkdown(markdownText);
		} catch (ParseException p) {
			error(p.getMessage());
			htmlText = markdownText;
		}

		// Add the html to the page
		add(new Label("markdownText", htmlText).setEscapeModelStrings(false));
	}

	@Override
	protected String getPageName() {
		return getString("gb.markdown");
	}
}
