/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.ide.functional.fragment.tests;

import com.liferay.ide.functional.fragment.wizard.base.NewFragmentWizardLiferayWorkspaceGradleBase;
import com.liferay.ide.functional.liferay.support.server.LiferaryWorkspaceTomcat71Support;
import com.liferay.ide.functional.liferay.support.workspace.LiferayWorkspaceGradle71Support;
import com.liferay.ide.functional.liferay.support.workspace.LiferayWorkspaceSupport;
import com.liferay.ide.functional.liferay.util.RuleUtil;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.RuleChain;

/**
 * @author Lily Li
 * @author Ashley Yuan
 */
public class NewFragmentWizardLiferayWorkspaceGradleTomcat71Tests extends NewFragmentWizardLiferayWorkspaceGradleBase {

	public static LiferayWorkspaceGradle71Support liferayWorkspace = new LiferayWorkspaceGradle71Support(bot);

	@Override
	protected LiferayWorkspaceSupport getLiferayWorkspace() {
		return liferayWorkspace;
	}

	public static LiferaryWorkspaceTomcat71Support server = new LiferaryWorkspaceTomcat71Support(bot, liferayWorkspace);

	@ClassRule
	public static RuleChain chain = RuleUtil.getTomcat71LiferayWorkspaceRuleChain(bot, liferayWorkspace, server);

	@Test
	public void createFragmentChangeModulesDir() {
		super.createFragmentChangeModulesDir();
	}

	@Test
	public void createFragmentWithJsp() {
		super.createFragmentWithJsp();
	}

	@Test
	public void createFragmentWithJspf() {
		super.createFragmentWithJspf();
	}

	@Test
	public void createFragmentWithoutFiles() {
		super.createFragmentWithoutFiles();
	}

	@Test
	public void createFragmentWithPortletProperites() {
		super.createFragmentWithPortletProperites();
	}

	@Test
	public void createFragmentWithResourceAction() {
		super.createFragmentWithResourceAction();
	}

	@Test
	public void createFragmentWithWholeFiles() {
		super.createFragmentWithWholeFiles();
	}

}