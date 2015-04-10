/*
 * JAME 6.2
 * http://jame.sourceforge.net
 *
 * Copyright 2001, 2015 Andrea Medeghini
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
import net.sf.jame.devtools.ProcessorTemplateLoader;
import net.sf.jame.devtools.processor.extension.ProcessorExtensionRuntime;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class NodeActionXMLExporterProcessorRuntime extends ProcessorExtensionRuntime {
	/**
	 * @see net.sf.jame.devtools.processor.extension.ProcessorExtensionRuntime#process(java.io.File, net.sf.jame.devtools.ProcessorDescriptor, java.util.List, java.util.Map)
	 */
	@Override
	public void process(File path, ProcessorParameters parameters, Map<String, String> variables) throws DevToolsException {
		try {
			ProcessorDescriptor element = parameters.getElement();
			if (element.isExtension() || element.isConfigurableExtension()) {
				if (variables.containsKey("generateReferenceNodeActionXMLExporter")) {
					File packagePath = new File(path, variables.get("nodeActionXMLExporterPackageName").replace('.', '/'));
					packagePath.mkdirs();
					Configuration config = new Configuration();
					config.setTemplateLoader(new ProcessorTemplateLoader());
					HashMap<String, Object> map = new HashMap<String, Object>();
					Set<String> imports = new HashSet<String>();
					prepare(imports, element);
					map.putAll(variables);
					prepareReferenceNodeActionXMLExporter(imports, element, variables);
					List<String> sortedImports = new LinkedList<String>(imports);
					Collections.sort(sortedImports);
					map.put("imports", sortedImports);
					map.put("extension", element);
					Template template = config.getTemplate("templates/ReferenceNodeActionXMLExporterRuntime.ftl");
					template.process(map, new PrintWriter(new File(packagePath, capitalize(element.getElementName()) + "ReferenceNodeActionXMLExporterRuntime.java")));
				}
			}
			else if (element.isComplexElement() || element.isSimpleElement()) {
				if (variables.containsKey("generateElementNodeActionXMLExporter")) {
					File packagePath = new File(path, variables.get("nodeActionXMLExporterPackageName").replace('.', '/'));
					packagePath.mkdirs();
					Configuration config = new Configuration();
					config.setTemplateLoader(new ProcessorTemplateLoader());
					HashMap<String, Object> map = new HashMap<String, Object>();
					Set<String> imports = new HashSet<String>();
					prepare(imports, element);
					map.putAll(variables);
					prepareElementNodeActionXMLExporter(imports, element, variables);
					List<String> sortedImports = new LinkedList<String>(imports);
					Collections.sort(sortedImports);
					map.put("imports", sortedImports);
					map.put("element", element);
					Template template = config.getTemplate("templates/ElementNodeActionXMLExporterRuntime.ftl");
					template.process(map, new PrintWriter(new File(packagePath, capitalize(element.getElementName()) + "ElementNodeActionXMLExporterRuntime.java")));
				} 
				if (variables.containsKey("generateElementListNodeActionXMLExporter")) {
					File packagePath = new File(path, variables.get("nodeActionXMLExporterPackageName").replace('.', '/'));
					packagePath.mkdirs();
					Configuration config = new Configuration();
					config.setTemplateLoader(new ProcessorTemplateLoader());
					HashMap<String, Object> map = new HashMap<String, Object>();
					Set<String> imports = new HashSet<String>();
					prepare(imports, element);
					map.putAll(variables);
					prepareElementListNodeActionXMLExporter(imports, element, variables);
					List<String> sortedImports = new LinkedList<String>(imports);
					Collections.sort(sortedImports);
					map.put("imports", sortedImports);
					map.put("element", element);
					Template template = config.getTemplate("templates/ElementListNodeActionXMLExporterRuntime.ftl");
					template.process(map, new PrintWriter(new File(packagePath, capitalize(element.getElementName()) + "ElementListNodeActionXMLExporterRuntime.java")));
				}
			}
		}
		catch (Exception e) {
			throw new DevToolsException(e);
		}
	}

	/**
	 * @see net.sf.jame.devtools.processor.extension.ProcessorExtensionRuntime#getName()
	 */
	@Override
	public String getName() {
		return "NodeActionXMLExporterRuntime";
	}

	private void prepare(Set<String> imports, ProcessorDescriptor descriptor) {
	}

	private void prepareReferenceNodeActionXMLExporter(Set<String> imports, ProcessorDescriptor descriptor, Map<String, String> variables) {
		if (descriptor.isExtension()) {
			imports.add("net.sf.jame.core.util.AbstractExtensionReferenceElementNodeActionXMLExporterRuntime");
		} else {
			imports.add("net.sf.jame.core.util.AbstractConfigurableExtensionReferenceElementNodeActionXMLExporterRuntime");
		}
		imports.add(descriptor.getExtensionConfigPackageName() + "." + descriptor.getExtensionConfigClassName());
	}

	private void prepareElementNodeActionXMLExporter(Set<String> imports, ProcessorDescriptor descriptor, Map<String, String> variables) {
		imports.add("net.sf.jame.core.util.AbstractConfigElementNodeActionXMLExporterRuntime");
		imports.add(descriptor.getConfigElementPackageName() + "." + descriptor.getConfigElementClassName());
		imports.add(descriptor.getConfigElementPackageName() + "." + descriptor.getConfigElementClassName() + "XMLExporter");
	}

	private void prepareElementListNodeActionXMLExporter(Set<String> imports, ProcessorDescriptor descriptor, Map<String, String> variables) {
		imports.add("net.sf.jame.core.util.AbstractConfigElementListNodeActionXMLExporterRuntime");
		imports.add(descriptor.getConfigElementPackageName() + "." + descriptor.getConfigElementClassName());
		imports.add(descriptor.getConfigElementPackageName() + "." + descriptor.getConfigElementClassName() + "XMLExporter");
	}

	private static String capitalize(String word) {
        word = word.substring(0, 1).toUpperCase() + word.substring(1);
        return word;
    }
}
