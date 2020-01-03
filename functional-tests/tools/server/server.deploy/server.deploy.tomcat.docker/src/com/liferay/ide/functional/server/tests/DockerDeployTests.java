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

package com.liferay.ide.functional.server.tests;

import com.liferay.ide.functional.liferay.SwtbotBase;
import com.liferay.ide.functional.liferay.support.project.ProjectsSupport;

import org.junit.Rule;
import org.junit.Test;

/**
 * @author Lily Li
 */
public class DockerDeployTests extends SwtbotBase {

	@Test
	public void deployModule() {
		wizardAction.openNewLiferayWorkspaceWizard();

		wizardAction.newLiferayWorkspace.prepareGradle(projects.getName(0), "7.2");

		wizardAction.finish();

		jobAction.waitForNoRunningJobs();

		viewAction.project.runInitPortalDockerBundle(projects.getName(0));

		viewAction.showConsoleView();

		jobAction.waitForNoRunningJobs();

		viewAction.servers.start("Liferay Docker Server " + projects.getName(0) + "  [Stopped]");

		jobAction.waitForServerStarted("Liferay Docker Server " + projects.getName(0));

		wizardAction.openNewLiferayModuleWizard();

		wizardAction.newModule.prepareGradle(projects.getName(1), MVC_PORTLET, "7.2");

		wizardAction.finish();

		jobAction.waitForNoRunningProjectBuildingJobs();

		viewAction.project.refreshGradleProject(projects.getName(0));

		jobAction.waitForNoRunningJobs();

		viewAction.gradleTasks.runGradleTask(
			projects.getName(0), "modules", projects.getName(1), "docker", "dockerDeploy");

		jobAction.waitForConsoleContent(
			"Liferay Docker Server " + projects.getName(0) + " [Liferay Docker]", projects.getName(1) + "_", M1);

		viewAction.servers.stop("Liferay Docker Server " + projects.getName(0) + "  [Started]");

		jobAction.waitForServerStopped("Liferay Docker Server " + projects.getName(0));

		dialogAction.deleteRuntimFromPreferences(0);

		dialogAction.confirm();

		viewAction.project.closeAndDelete(projects.getName(0), "modules", projects.getName(1));
		viewAction.project.closeAndDelete(projects.getName(0), "modules");
		viewAction.project.closeAndDelete(projects.getName(0));
	}

	@Rule
	public ProjectsSupport projects = new ProjectsSupport(bot);

}