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

package com.liferay.ide.maven.core;

import com.liferay.ide.project.core.ProjectCore;
import com.liferay.ide.project.core.modules.BladeCLI;
import com.liferay.ide.project.core.modules.BladeCLIException;
import com.liferay.ide.project.core.util.ProjectUtil;
import com.liferay.ide.project.core.workspace.BaseLiferayWorkspaceOp;
import com.liferay.ide.project.core.workspace.NewLiferayWorkspaceOp;
import com.liferay.ide.project.core.workspace.NewLiferayWorkspaceProjectProvider;
import com.liferay.ide.server.core.LiferayServerCore;
import com.liferay.ide.server.util.ServerUtil;

import java.io.File;
import java.io.FileReader;

import org.apache.maven.model.Build;
import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;

import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.sapphire.platform.PathBridge;

/**
 * @author Joye Luo
 * @author Andy Wu
 */
public class LiferayMavenWorkspaceProjectProvider
	extends LiferayMavenProjectProvider implements NewLiferayWorkspaceProjectProvider<NewLiferayWorkspaceOp> {

	@Override
	public IStatus createNewProject(NewLiferayWorkspaceOp op, IProgressMonitor monitor) throws CoreException {
		IPath location = PathBridge.create(op.getLocation().content());
		String wsName = op.getWorkspaceName().toString();

		IPath wsLocation = location.append(wsName);

		String liferayVersion = op.getLiferayVersion().content();

		StringBuilder sb = new StringBuilder();

		sb.append("--base ");
		sb.append("\"");
		sb.append(wsLocation.toFile().getAbsolutePath());
		sb.append("\" ");
		sb.append("init ");
		sb.append("-b ");
		sb.append("maven ");
		sb.append("-v ");
		sb.append(liferayVersion);

		try {
			BladeCLI.execute(sb.toString());
		}
		catch (BladeCLIException bclie) {
			return ProjectCore.createErrorStatus(bclie);
		}

		String workspaceLocation = location.append(wsName).toPortableString();
		boolean initBundle = op.getProvisionLiferayBundle().content();
		String bundleUrl = op.getBundleUrl().content(false);
		String serverName = op.getServerName().content();

		IStatus status = importProject(workspaceLocation, monitor);

		if (initBundle) {
			initBundle(monitor, bundleUrl, serverName, wsName);
		}

		return status;
	}

	@Override
	public String getInitBundleUrl(String workspaceLocation) {
		File pomFile = new File(workspaceLocation, "pom.xml");

		MavenXpp3Reader mavenReader = new MavenXpp3Reader();

		try (FileReader reader = new FileReader(pomFile)) {
			Model model = mavenReader.read(reader);

			if (model != null) {
				Build build = model.getBuild();

				Plugin plugin = build.getPluginsAsMap().get("com.liferay:com.liferay.portal.tools.bundle.support");

				if (plugin != null) {
					Xpp3Dom config = (Xpp3Dom)plugin.getConfiguration();

					if (config != null) {
						Xpp3Dom url = config.getChild("url");

						if (url != null) {
							String urlValue = url.getValue();

							if (!urlValue.isEmpty()) {
								return urlValue;
							}
						}
					}
				}
			}
		}
		catch (Exception e) {
			return BaseLiferayWorkspaceOp.LIFERAY_70_BUNDLE_URL;
		}

		return BaseLiferayWorkspaceOp.LIFERAY_70_BUNDLE_URL;
	}

	@Override
	public IStatus importProject(String location, IProgressMonitor monitor) {
		IStatus retval = Status.OK_STATUS;

		try {
				MavenUtil.importProject(location, monitor);
		}
		catch (Exception e) {
			retval = ProjectCore.createErrorStatus(e);
		}

		return retval;
	}

	@Override
	public void initBundle(IProgressMonitor monitor, String bundleUrl, String serverName, String workspaceName) {
		Job initBundleJob = new Job("Init Liferay Bundle") {

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				IProject workspaceProject = ProjectUtil.getProject(workspaceName);

				MavenProjectBuilder mavenProjectBuilder = new MavenProjectBuilder(workspaceProject);

				try {
					mavenProjectBuilder.initBundle(workspaceProject, bundleUrl, monitor);

					workspaceProject.refreshLocal(IResource.DEPTH_INFINITE, monitor);

					IPath workspaceProjectPath = workspaceProject.getLocation();

					IPath bundlesPath = workspaceProjectPath.append("bundles");

					if (LiferayServerCore.isPortalBundlePath(bundlesPath)) {
						ServerUtil.addPortalRuntimeAndServer(serverName, bundlesPath, monitor);
					}
				}
				catch (CoreException ce) {
					LiferayMavenCore.logError("Init Liferay Bundle failed", ce);
				}

				return Status.OK_STATUS;
			}

		};

		initBundleJob.schedule();
	}

	@Override
	public IStatus validateProjectLocation(String projectName, IPath path) {
		IStatus retval = Status.OK_STATUS;

		// TODO validation maven project location

		return retval;
	}

}