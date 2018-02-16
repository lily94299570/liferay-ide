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

package com.liferay.ide.ui.fragment.tests;

import com.liferay.ide.ui.liferay.SwtbotBase;
import com.liferay.ide.ui.liferay.base.LiferayWorkspaceMavenSupport;
import com.liferay.ide.ui.liferay.base.ProjectSupport;
import com.liferay.ide.ui.liferay.base.TomcatSupport;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Lily Li
 */
@Ignore("ignore as the problem of deleting Liferay workspace")
public class NewFragmentWizardLiferayWorkspaceMavenTests extends SwtbotBase {

	@ClassRule
	public static LiferayWorkspaceMavenSupport liferayWorkspace = new LiferayWorkspaceMavenSupport(bot);

	@ClassRule
	public static TomcatSupport tomcat = new TomcatSupport(bot);

	@Test
	public void createFragmentWithJsp() {
		wizardAction.openNewFragmentWizard();

		wizardAction.newFragment.prepareMaven(project.getName());

		wizardAction.next();

		wizardAction.newFragmentInfo.openBrowseOsgiBundleDialog();

		dialogAction.prepareText("com.liferay.iframe.web");

		dialogAction.confirm();

		wizardAction.newFragmentInfo.openAddOverrideFilesDialog();

		dialogAction.selectItems("META-INF/resources/proxy.jsp");

		dialogAction.confirm();

		wizardAction.finish();

		Assert.assertTrue(viewAction.project.visibleFileTry(liferayWorkspace.getModuleFiles(project.getName())));

		viewAction.project.closeAndDeleteFromDisk(liferayWorkspace.getModuleFiles(project.getName()));
	}

	@Test
	public void createFragmentWithJspf() {
		wizardAction.openNewFragmentWizard();

		wizardAction.newFragment.prepareMaven(project.getName());

		wizardAction.next();

		wizardAction.newFragmentInfo.openBrowseOsgiBundleDialog();

		dialogAction.prepareText("com.liferay.site.memberships.web");

		dialogAction.confirm();

		wizardAction.newFragmentInfo.openAddOverrideFilesDialog();

		dialogAction.selectItems("META-INF/resources/role_columns.jspf");

		dialogAction.confirm();

		wizardAction.finish();

		Assert.assertTrue(viewAction.project.visibleFileTry(liferayWorkspace.getModuleFiles(project.getName())));

		viewAction.project.closeAndDeleteFromDisk(liferayWorkspace.getModuleFiles(project.getName()));
	}

	@Test
	public void createFragmentWithoutFiles() {
		wizardAction.openNewFragmentWizard();

		wizardAction.newFragment.prepareMaven(project.getName());

		wizardAction.next();

		wizardAction.newFragmentInfo.openBrowseOsgiBundleDialog();

		dialogAction.prepareText("com.liferay.frontend.image.editor.web");

		dialogAction.confirm();

		wizardAction.newFragmentInfo.openAddOverrideFilesDialog();

		Assert.assertFalse(dialogAction.getConfirmBtn().isEnabled());

		dialogAction.cancel();

		wizardAction.finish();

		Assert.assertTrue(viewAction.project.visibleFileTry(liferayWorkspace.getModuleFiles(project.getName())));

		viewAction.project.closeAndDeleteFromDisk(liferayWorkspace.getModuleFiles(project.getName()));
	}

	@Test
	public void createFragmentWithPortletProperites() {
		wizardAction.openNewFragmentWizard();

		wizardAction.newFragment.prepareMaven(project.getName());

		wizardAction.next();

		wizardAction.newFragmentInfo.openBrowseOsgiBundleDialog();

		dialogAction.prepareText("com.liferay.item.selector.web");

		dialogAction.confirm();

		String[] files = {"META-INF/resources/init.jsp", "META-INF/resources/view.jsp", "portlet.properties"};

		wizardAction.newFragmentInfo.openAddOverrideFilesDialog();

		dialogAction.selectItems(files);

		dialogAction.confirm();

		wizardAction.finish();

		Assert.assertTrue(viewAction.project.visibleFileTry(liferayWorkspace.getModuleFiles(project.getName())));

		viewAction.project.closeAndDeleteFromDisk(liferayWorkspace.getModuleFiles(project.getName()));
	}

	@Test
	public void createFragmentWithResourceAction() {
		wizardAction.openNewFragmentWizard();

		wizardAction.newFragment.prepareMaven(project.getName());

		wizardAction.next();

		wizardAction.newFragmentInfo.openBrowseOsgiBundleDialog();

		dialogAction.prepareText("com.liferay.layout.admin.web");

		dialogAction.confirm();

		wizardAction.newFragmentInfo.openAddOverrideFilesDialog();

		dialogAction.selectItems("resource-actions/default.xml");

		dialogAction.confirm();

		wizardAction.finish();

		Assert.assertTrue(viewAction.project.visibleFileTry(liferayWorkspace.getModuleFiles(project.getName())));

		viewAction.project.closeAndDeleteFromDisk(liferayWorkspace.getModuleFiles(project.getName()));
	}

	@Test
	public void createFragmentWithWholeFiles() {
		wizardAction.openNewFragmentWizard();

		wizardAction.newFragment.prepareMaven(project.getName());

		wizardAction.next();

		wizardAction.newFragmentInfo.openBrowseOsgiBundleDialog();

		dialogAction.prepareText("com.liferay.site.navigation.language.web");

		dialogAction.confirm();

		String[] files = {
			"META-INF/resources/configuration.jsp", "META-INF/resources/init-ext.jsp", "META-INF/resources/init.jsp",
			"META-INF/resources/view.jsp", "portlet.properties", "resource-actions/default.xml"
		};

		wizardAction.newFragmentInfo.openAddOverrideFilesDialog();

		dialogAction.selectItems(files);

		dialogAction.confirm();

		wizardAction.newFragmentInfo.openAddOverrideFilesDialog();

		Assert.assertFalse(dialogAction.getConfirmBtn().isEnabled());

		dialogAction.cancel();

		wizardAction.newFragmentInfo.selectFile("META-INF/resources/configuration.jsp");

		wizardAction.newFragmentInfo.deleteFile();

		wizardAction.newFragmentInfo.openAddOverrideFilesDialog();

		dialogAction.selectItems("META-INF/resources/configuration.jsp");

		dialogAction.confirm();

		wizardAction.finish();

		Assert.assertTrue(viewAction.project.visibleFileTry(liferayWorkspace.getModuleFiles(project.getName())));

		viewAction.project.closeAndDeleteFromDisk(liferayWorkspace.getModuleFiles(project.getName()));
	}

	@Rule
	public ProjectSupport project = new ProjectSupport(bot);

}