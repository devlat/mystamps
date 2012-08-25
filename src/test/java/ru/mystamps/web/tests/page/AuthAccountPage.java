/*
 * Copyright (C) 2009-2012 Slava Semushin <slava.semushin@gmail.com>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */

package ru.mystamps.web.tests.page;

import java.util.Arrays;
import java.util.Collection;

import org.openqa.selenium.WebDriver;

import ru.mystamps.web.Url;

import org.apache.commons.lang3.Validate;

import static org.apache.commons.collections.CollectionUtils.exists;
import static org.apache.commons.collections.PredicateUtils.notNullPredicate;

import static ru.mystamps.web.tests.TranslationUtils.tr;
import static ru.mystamps.web.tests.page.element.Form.with;
import static ru.mystamps.web.tests.page.element.Form.required;
import static ru.mystamps.web.tests.page.element.Form.inputField;
import static ru.mystamps.web.tests.page.element.Form.passwordField;
import static ru.mystamps.web.tests.page.element.Form.submitButton;

public class AuthAccountPage extends AbstractPageWithForm {
	
	public AuthAccountPage(final WebDriver driver) {
		super(driver, Url.AUTHENTICATION_PAGE);
		
		hasForm(
			with(
				required(inputField("login"))
					.withLabel(tr("t_login"))
					.and()
					.invalidValue("x"),
				
				required(passwordField("password"))
					.withLabel(tr("t_password"))
					.and()
					.invalidValue("x")
			)
			.and()
			.with(submitButton(tr("t_enter")))
		);
	}
	
	public boolean authenticationFormExists() {
		// TODO: probably better to check for form tag presence?
		return elementWithIdExists("authAccountForm");
	}
	
	public void authorizeUser(final String login, final String password) {
		final Collection<String> fieldNames = Arrays.asList(login, password);
		
		Validate.validState(
			exists(fieldNames, notNullPredicate()),
			"Login and password should not be a null"
		);
		
		fillLogin(login);
		fillPassword(password);
		
		submit();
	}
	
	private void fillLogin(final String login) {
		if (login != null) {
			fillField("login", login);
		}
	}
	
	private void fillPassword(final String password) {
		if (password != null) {
			fillField("password", password);
		}
	}
	
}
