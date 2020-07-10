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

package com.liferay.ide.functional.portlet.tests;

import com.liferay.ide.functional.liferay.SwtbotBase;
import com.liferay.ide.functional.liferay.support.project.ProjectSupport;
import com.liferay.ide.functional.liferay.support.workspace.LiferayWorkspaceMavenSupport;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Terry Jia
 * @author Rui Wang
 * @author Ying Xu
 */
public class NewPortletModuleMavenTests extends SwtbotBase {

	@ClassRule
	public static LiferayWorkspaceMavenSupport liferayWorkspace = new LiferayWorkspaceMavenSupport(bot);

	@Ignore("ignore because blade 3.10 remove freemarker-portlet template")
	@Test
	public void createFreemarkerPortlet() {
		wizardAction.openNewLiferayModuleWizard();

		wizardAction.newModule.prepareMaven(project.getName(), FREEMARKER_PORTLET);

		wizardAction.finish();

		jobAction.waitForNoRunningJobs();

		Assert.assertTrue(viewAction.project.visibleFileTry(liferayWorkspace.getName(), project.getName()));

		viewAction.project.closeAndDelete(project.getName());
	}

	@Test
	public void createMvcPortlet() {
		wizardAction.openNewLiferayModuleWizard();

		wizardAction.newModule.prepareMaven(project.getName(), MVC_PORTLET);

		wizardAction.finish();

		jobAction.waitForNoRunningJobs();

		Assert.assertTrue(viewAction.project.visibleFileTry(liferayWorkspace.getName(), project.getName()));

		jobAction.waitForNoRunningProjectBuildingJobs();

		viewAction.project.closeAndDeleteFromDisk(liferayWorkspace.getName(), project.getName());
	}

	@Ignore("ignore create project have too long time lead to timeout")
	@Test
	public void createNpmAngularPortlet() {
		wizardAction.openNewLiferayModuleWizard();

		wizardAction.newModule.prepareMaven(project.getName(), NPM_ANGULAR_PORTLET);

		wizardAction.finish();

		Assert.assertTrue(viewAction.project.visibleFileTry(liferayWorkspace.getName(), project.getName()));

		viewAction.project.closeAndDeleteFromDisk(liferayWorkspace.getName(), project.getName());
	}

	@Ignore("ignore create project have too long time lead to timeout")
	@Test
	public void createNpmReactPortlet() {
		wizardAction.openNewLiferayModuleWizard();

		wizardAction.newModule.prepareMaven(project.getName(), NPM_REACT_PORTLET);

		wizardAction.finish();

		Assert.assertTrue(viewAction.project.visibleFileTry(liferayWorkspace.getName(), project.getName()));

		viewAction.project.closeAndDelete(project.getName());
	}

	@Ignore("ignore create project have too long time lead to timeout")
	@Test
	public void createNpmVuejsPortlet() {
		wizardAction.openNewLiferayModuleWizard();

		wizardAction.newModule.prepareMaven(project.getName(), NPM_VUEJS_PORTLET);

		wizardAction.finish();

		Assert.assertTrue(viewAction.project.visibleFileTry(liferayWorkspace.getName(), project.getName()));

		viewAction.project.closeAndDelete(project.getName());
	}

	@Ignore("ignore because blade 3.7 remove portlet template")
	@Test
	public void createPortlet() {
		wizardAction.openNewLiferayModuleWizard();

		wizardAction.newModule.prepareMaven(project.getName(), PORTLET);

		wizardAction.finish();

		jobAction.waitForNoRunningJobs();

		Assert.assertTrue(viewAction.project.visibleFileTry(liferayWorkspace.getName(), project.getName()));

		viewAction.project.closeAndDelete(project.getName());
	}

	@Ignore("ignore because blade 3.7 remove soy-portlet template")
	@Test
	public void createSoyPortlet() {
		wizardAction.openNewLiferayModuleWizard();

		wizardAction.newModule.prepareMaven(project.getName(), SOY_PORTLET);

		wizardAction.finish();

		Assert.assertTrue(viewAction.project.visibleFileTry(liferayWorkspace.getName(), project.getName()));

		viewAction.project.closeAndDelete(project.getName());
	}

	@Ignore("ignore to wait new Spring MVC Portlet Wziard")
	@Test
	public void createSpringMvcPortlet() {
		wizardAction.openNewLiferayModuleWizard();

		wizardAction.newModule.prepareMaven(project.getName(), SPRING_MVC_PORTLET);

		wizardAction.finish();

		jobAction.waitForNoRunningJobs();

		Assert.assertTrue(viewAction.project.visibleFileTry(liferayWorkspace.getName(), project.getName()));

		viewAction.project.closeAndDelete(project.getName());
	}

	@Rule
	public ProjectSupport project = new ProjectSupport(bot);

}