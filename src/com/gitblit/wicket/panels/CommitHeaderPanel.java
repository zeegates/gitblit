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
package com.gitblit.wicket.panels;

import org.apache.wicket.markup.html.basic.Label;
import org.eclipse.jgit.revwalk.RevCommit;

import com.gitblit.wicket.WicketUtils;
import com.gitblit.wicket.pages.CommitPage;

public class CommitHeaderPanel extends BasePanel {

	private static final long serialVersionUID = 1L;

	public CommitHeaderPanel(String id, String repositoryName, RevCommit c) {
		super(id);
		add(new LinkPanel("shortmessage", "title", c.getShortMessage(), CommitPage.class,
				WicketUtils.newObjectParameter(repositoryName, c.getName())));
		add(new Label("commitid", "(" + c.getName().substring(0, 8) + ")"));
		add(new Label("author", c.getAuthorIdent().getName()));
		add(WicketUtils.createDateLabel("date", c.getAuthorIdent().getWhen(), getTimeZone()));
	}
}