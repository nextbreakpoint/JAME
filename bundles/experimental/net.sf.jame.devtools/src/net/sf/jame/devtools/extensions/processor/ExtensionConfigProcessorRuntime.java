/*
 * JAME 6.1 
 * http://jame.sourceforge.net
 *
 * Copyright 2001, 2010 Andrea Medeghini
 * http://andreamedeghini.users.sourceforge.net
 *
 * This file is part of JAME.
 *
 * JAME is an application for creating fractals and other graphics artifacts.
 *
 * JAME is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JAME is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JAME.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package net.sf.jame.devtools.extensions.processor;

import java.io.File;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.jame.devtools.DevToolsException;
import net.sf.jame.devtools.ProcessorDescriptor;
import net.sf.jame.devtools.ProcessorParameters;
import net.sf.jame.devtools.extension.ProcessorExtensionRuntime;
import net.sf.jame.devtools.extensions.ProcessorTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class ExtensionConfigProcessorRuntime extends ProcessorExtensionRuntime {
	/**
	 * @see net.sf.jame.devtools.extension.ProcessorExtensionRuntime#process(java.io.File, net.sf.jame.devtools.ProcessorDescriptor, java.util.List, java.util.Map)
	 */
	@Override
	public void process(File path, ProcessorParameters parameters, Map<String, String> variables) throws DevToolsException {
		try {
			ProcessorDescriptor element = parameters.getElement();
			HashMap<String, Object> map = new HashMap<String, Object>();
			Set<String> imports = new HashSet<String>();
			prepare(imports, element);
			List<String> sortedImports = new LinkedList<String>(imports);
			Collections.sort(sortedImports);
			map.putAll(variables);
			map.put("imports", sortedImports);
			map.put("extension", element);
			if (element.isConfigurableExtension()) {
				File packagePath = new File(path, element.getExtensionConfigPackageName().replace('.', '/'));
				packagePath.mkdirs();
				Configuration config = new Configuration();
				config.setTemplateLoader(new ProcessorTemplateLoader());
				Template template = config.getTemplate("templates/ExtensionConfig.ftl");
				template.process(map, new PrintWriter(new File(packagePath, element.getExtensionConfigClassName() + ".java")));
			}
		}
		catch (Exception e) {
			throw new DevToolsException(e);
		}
	}

	/**
	 * @see net.sf.jame.devtools.extension.ProcessorExtensionRuntime#getName()
	 */
	@Override
	public String getName() {
		return "ExtensionConfig";
	}

	private void prepare(Set<String> imports, ProcessorDescriptor descriptor) {
		if (descriptor.isConfigurableExtension()) {
			imports.add("net.sf.jame.core.extension.ExtensionConfig");
		}
	}
}
