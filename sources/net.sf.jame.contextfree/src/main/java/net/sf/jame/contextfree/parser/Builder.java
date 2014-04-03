package net.sf.jame.contextfree.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Stack;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CharStream;

public class Builder {
	public Builder() {
	}

	private CFDG cfdg = new CFDG();
	public ASTRand48 seed;
	public Stack<ASTRepContainer> containerStack = new Stack<ASTRepContainer>();
	public ASTRepContainer paramDecls = new ASTRepContainer();
	public Map<String, Integer> flagNames = new HashMap<String, Integer>();
	public List<StackRule> longLivedParams = new ArrayList<StackRule>();
	public Stack<String> fileNames = new Stack<String>();
	public Stack<String> filesToLoad = new Stack<String>();
	public Stack<CharStream> streamsToLoad = new Stack<CharStream>();
	public Stack<Boolean> includeNamespace = new Stack<Boolean>();
	public String currentNameSpace = "";
	public String currentPath;
	public String maybeVersion;
	public int currentShape;
	public int pathCount;
	public int includeDepth;
	public int localStackDepth;
	public double maxNatual;
	public boolean allowOverlap;
	public boolean inPathContainer;

	static List<String> globals = new ArrayList<String>();
	{ 
		globals.add("CIRCLE");
		globals.add("FILL");
		globals.add("SQUARE");
		globals.add("TRIANGLE");
	}
	
	protected void warning(String message) {
		System.out.println(message);
	}
	
	protected void error(String message) {
		System.err.println(message);
	}
	
	protected boolean isPrimeShape(int nameIndex) {
		return nameIndex < 4;
	}

	public int stringToShape(String name, boolean colonsAllowed) {
		checkName(name, colonsAllowed);
		if (currentNameSpace.length() == 0) {
			return cfdg.encodeShapeName(name);
		}
		int index = Collections.binarySearch(globals, name);
		String n = currentNameSpace + name;
		if (index != -1 && cfdg.tryEncodeShapeName(n) == -1) {
			return cfdg.encodeShapeName(name);
		} else {
			return cfdg.encodeShapeName(n);
		}
	}

	public String shapeToString(int shape) {
		return cfdg.decodeShapeName(shape);
	}

	public void checkName(String name, boolean colonsAllowed) {
		int pos = name.indexOf(":");
		if (pos == -1) {
			return;
		}
		if (!colonsAllowed) {
			error("namespace specification not allowed in this context");
			return;
		}
		if (pos == 0) {
			error("improper namespace specification");
			return;
		}
		for (;;) {
			if (pos == name.length() - 1 || name.charAt(pos + 1) != ':') break;
			int next = name.indexOf(":", pos + 2);
			if (next == -1) return;
			if (next == pos + 2) break;
			pos = next;
		}
		error("improper namespace specification");
	}
	
	public void includeFile(String fileName) throws IOException {
		String path = relativeFilePath(currentPath, fileName);
		ANTLRFileStream is = new ANTLRFileStream(path);
		fileNames.push(path);
		currentPath = path;
		filesToLoad.push(currentPath);
		streamsToLoad.push(is);
		includeNamespace.push(Boolean.FALSE);
		pathCount++;
		includeDepth++;
		currentShape = -1;
		setShape(null, false);
		warning("Reading rules file " + path);
	}
	
	public boolean endInclude() throws IOException {
		boolean endOfInput = includeDepth == 0;
		setShape(null, false);
		includeDepth--;
		if (filesToLoad.isEmpty()) {
			return endOfInput;
		}
		if (includeNamespace.peek()) {
			popNameSpace();
		}
		streamsToLoad.pop();
		filesToLoad.pop();
		includeNamespace.pop();
		currentPath = filesToLoad.isEmpty() ? null : filesToLoad.peek();
		return endOfInput;
	}
	
	public void setShape(String name, boolean isPath) {
		if (name == null) {
			currentShape = -1;
			return;
		}
		currentShape = stringToShape(name, false);
		ASTDefine def = cfdg.findFunction(currentShape);
		if (def != null) {
			error("There is a function with the same name as this shape: " + def.getLocation());
			return;
		}
		String err = cfdg.setShapeParams(currentShape, paramDecls, paramDecls.getStackCount(), isPath);
		if (err != null) {
			error("cannot set shape params: " + err);
		}
		localStackDepth -= paramDecls.getStackCount();
		paramDecls.getParameters().clear();
		paramDecls.setStackCount(0);
	}
	
	public void addRule(ASTRule rule) {
		boolean isShapeItem = rule.getNameIndex() == -1;
		if (isShapeItem) {
			rule.setNameIndex(currentShape);
		} else {
			currentShape = -1;
		}
		if (rule.getNameIndex() == -1) {
			error("Shape rules/paths must follow a shape declaration");
		}
		ShapeTypeEnum type = cfdg.getShapeType(rule.getNameIndex());
		if ((rule.isPath() && type == ShapeTypeEnum.RuleType) || (!rule.isPath() && type == ShapeTypeEnum.PathType)) {
			error("Cannot mix rules and shapes with the same name");
		}
		boolean matchesShape = cfdg.addRuleShape(rule);
		if (!isShapeItem && matchesShape) {
			error("Rule/path name matches existing shape name");
		}
	}

	public void nextParameterDecl(String type, String name) {
		int nameIndex = stringToShape(name, false);
		checkVariableName(nameIndex, true);
		paramDecls.addParameter(type, nameIndex);
		ASTParameter param = paramDecls.getParameters().get(paramDecls.getParameters().size() - 1);
		param.setStackIndex(localStackDepth);
		paramDecls.setStackCount(paramDecls.getStackCount() + param.getTupleSize());
		localStackDepth += param.getTupleSize();
	}

	public ASTDefine makeDefinition(String name, boolean isFunction) {
		if (name.startsWith("CF::")) {
			if (isFunction) {
				error("Configuration parameters cannot be functions");
				return null;
			}
			if (containerStack.lastElement().isGlobal()) {
				error("Configuration parameters must be at global scope");
				return null;
			}
			ASTDefine def = new ASTDefine(name);
			def.setConfigDepth(includeDepth);
			def.setDefineType(DefineTypeEnum.ConfigDefine);
			return def;
		}
		if (FuncType.getFuncTypeByName(name) != FuncType.NotAFunction) {
			error("Internal function names are reserved");
			return null;
		}
		int nameIndex = stringToShape(name, false);
		ASTDefine def = cfdg.findFunction(nameIndex);
		if (def != null) {
			error("Definition with same name as user function: " + def.getLocation());
			return null;
		}
		checkVariableName(nameIndex, false);
		def = new ASTDefine(name);
		def.getRuleSpecifier().setShapeType(nameIndex);
		if (isFunction) {
			for (ASTParameter param : paramDecls.getParameters()) {
				param.setLocality(LocalityType.PureNonlocal);
			}
			def.getParameters().clear();
			def.getParameters().addAll(paramDecls.getParameters());
			def.setStackCount(paramDecls.getStackCount());
			def.setDefineType(DefineTypeEnum.FunctionDefine);
			localStackDepth -= paramDecls.getStackCount();
			paramDecls.setStackCount(0);
			cfdg.declareFunction(nameIndex, def);
		} else {
			containerStack.lastElement().addDefParameter(nameIndex, def);
		}
		return def;
	}
	
	public void makeConfig(ASTDefine cfg) {
		if (cfg.getName().equals("CF::Impure")) {
			double[] v = new double[] { 0.0 };
			if (cfg.getExp() != null || cfg.getExp().isConstant() || cfg.getExp().evaluate(v, 1, null) != 1) {
				error("CF::Impure requires a constant numeric expression");
			} else {
				ASTParameter.Impure = v[0] != 0.0;
			}
		}
		if (cfg.getName().equals("CF::AllowOverlap")) {
			double[] v = new double[] { 0.0 };
			if (cfg.getExp() != null || cfg.getExp().isConstant() || cfg.getExp().evaluate(v, 1, null) != 1) {
				error("CF::AllowOverlap requires a constant numeric expression");
			} else {
				allowOverlap = v[0] != 0.0;
			}
		}
		if (cfg.getName().equals("CF::StartShape") && cfg.getExp() != null && (cfg.getExp() instanceof ASTStartSpecifier)) {
			ASTRuleSpecifier rule = null;
			ASTModification mod = null;
			List<ASTExpression> specAndMod = extract(cfg.getExp());
			switch (specAndMod.size()) {
				case 2:
					if (!(specAndMod.get(1) instanceof ASTModification)) {
						error("CF::StartShape second term must be a modification");
					} else {
						mod = (ASTModification) specAndMod.get(1);
					}
					break;
	
				case 1:
					if (!(specAndMod.get(0) instanceof ASTRuleSpecifier)) {
						error("CF::StartShape must start with a shape specification");
					} else {
						rule = (ASTRuleSpecifier) specAndMod.get(0);
					}
					break;
					
				default:
					error("CF::StartShape expression must have the form shape_spec or shape_spec, modification");
					break;
			}
			if (mod == null) {
				mod = new ASTModification();
			}
			cfg.setExp(new ASTStartSpecifier(rule, mod));
		}
		ASTExpression current = cfg.getExp();
		if (!cfdg.addParameter(cfg.getName(), cfg.getExp(), cfg.getConfigDepth())) {
			error("Unknown configuration parameter");
		}
		if (cfg.getName().equals("CF::MaxNatural")) {
			ASTExpression max = cfdg.hasParameter(CFGParam.MaxNatural);
			if (max != current) {
				return;
			}
			double[] v = new double[] { -1.0 };
			if (max == null || !max.isConstant() || max.getType() != ExpType.NumericType || max.evaluate(v, 1, null) != 1) {
				error("CF::MaxNatural requires a constant numeric expression");
			} else if (v[0] < 1.0 || v[0] > 9007199254740992.0) {
				error(v[0] < 1.0 ? "CF::MaxNatural must be >= 1" : "CF::MaxNatural must be < 9007199254740992");
			} else {
				maxNatual = v[0];
			}
		}
	}
	
	private List<ASTExpression> extract(ASTExpression exp) {
		if (exp instanceof ASTCons) {
			return exp.getChildren();
		} else {
			List<ASTExpression> ret = new ArrayList<ASTExpression>();
			ret.add(exp);
			return ret;
		}
	}

	public ASTExpression makeVariable(String name) {
		Integer flagItem = flagNames.get(name);
		if (flagItem != null) {
			ASTReal flag = new ASTReal(flagItem);
			flag.setType(ExpType.FlagType);
			return flag;
		}
		if (name.startsWith("CF::")) {
			error("Configuration parameter names are reserved");
			return new ASTExpression();
		}
		if (FuncType.getFuncTypeByName(name) != FuncType.NotAFunction) {
			error("Internal function names are reserved");
			return new ASTExpression();
		}
		int varNum = stringToShape(name, true);
		boolean isGlobal = false;
		ASTParameter bound = findExpression(varNum, isGlobal);
		if (bound == null) {
			return new ASTRuleSpecifier(varNum, name, null, cfdg.getShapeParams(currentShape));
		}
		return new ASTVariable(varNum, name);
	}

	public ASTExpression makeArray(String name, ASTExpression args) {
		if (name.startsWith("CF::")) {
			error("Configuration parameter names are reserved");
			return args;
		}
		int varNum = stringToShape(name, true);
		boolean isGlobal = false;
		ASTParameter bound = findExpression(varNum, isGlobal);
		if (bound == null) {
			return args;
		}
		return new ASTArray(varNum, args, new String[1]);
	}

	public ASTExpression makeLet(ASTRepContainer vars, ASTExpression exp) {
		int nameIndex = stringToShape("let", false);
		ASTDefine def = new ASTDefine("let");
		def.getRuleSpecifier().setShapeType(nameIndex);
		def.setExp(exp);
		def.setDefineType(DefineTypeEnum.LetDefine);
		return new ASTLet(vars, def);
	}

	public ASTRuleSpecifier makeRuleSpecifier(String name, ASTExpression args, ASTModification mod, boolean makeStart) {
		if (name.equals("if") || name.equals("let") || name.equals("select")) {
			if (name.equals("select")) {
				args = new ASTSelect(args, false);
			}
			if (makeStart) {
				return new ASTStartSpecifier(args, mod);
			} else {
				return new ASTRuleSpecifier(args);
			}
		}
		int nameIndex = stringToShape(name, true);
		boolean isGlobal = false;
		ASTParameter bound = findExpression(nameIndex, isGlobal);
		if (bound != null && args != null && args.getType() == ExpType.ReuseType && !makeStart && isGlobal && nameIndex == currentShape) {
			error("Shape name binds to global variable and current shape, using current shape");
		}
		if (bound != null && bound.isParameter() && bound.getType() == ExpType.RuleType) {
			return new ASTRuleSpecifier(nameIndex, name);
		}
		ASTRuleSpecifier ret = null;
		cfdg.setShapeHasNoParam(nameIndex, args);
		if (makeStart) {
			ret = new ASTStartSpecifier(nameIndex, name, args, mod);
		} else {
			ret = new ASTRuleSpecifier(nameIndex, name, args, cfdg.getShapeParams(currentShape));
		}
		if (ret.getArguments() != null && ret.getArguments().getType() == ExpType.ReuseType) {
			if (makeStart) {
				error("Startshape cannot reuse parameters");
			} else if (nameIndex == currentShape)  {
				ret.setArgSouce(ArgSource.SimpleArgs);
				ret.setTypeSignature(ret.getTypeSignature());
			}
		}
		return ret;
	}

	public ASTExpression makeModTerm(String type, ASTExpression mod, boolean target) {
		switch (type) {
			case "time":
				return new ASTModTerm(ModTypeEnum.time, mod);
				
			case "x":
				return new ASTModTerm(ModTypeEnum.x, mod);
				
			case "y":
				return new ASTModTerm(ModTypeEnum.y, mod);
				
			case "z":
				return new ASTModTerm(ModTypeEnum.z, mod);
				
			case "s":
			case "size":
				return new ASTModTerm(ModTypeEnum.size, mod);
				
			case "f":
			case "flip":
				return new ASTModTerm(ModTypeEnum.flip, mod);
				
			case "r":
			case "rotate":
				return new ASTModTerm(ModTypeEnum.rot, mod);
				
			case "skew":
				return new ASTModTerm(ModTypeEnum.skew, mod);
				
			case "trans":
			case "transform":
				return new ASTModTerm(ModTypeEnum.transform, mod);
				
			case "a":
			case "alpha":
				return new ASTModTerm(target ? ModTypeEnum.alpha : ModTypeEnum.alphaTarg, mod);
				
			case "h":
			case "hue":
				return new ASTModTerm(target ? ModTypeEnum.hue : ModTypeEnum.hueTarg, mod);
				
			case "sat":
			case "saturation":
				return new ASTModTerm(target ? ModTypeEnum.sat : ModTypeEnum.satTarg, mod);
				
			case "b":
			case "brightness":
				return new ASTModTerm(target ? ModTypeEnum.bright : ModTypeEnum.brightTarg, mod);
				
			case "|a":
			case "|alpha":
				return new ASTModTerm(ModTypeEnum.targAlpha, mod);
				
			case "|h":
			case "|hue":
				return new ASTModTerm(ModTypeEnum.targHue, mod);
				
			case "|sat":
			case "|saturation":
				return new ASTModTerm(ModTypeEnum.targSat, mod);
				
			case "|b":
			case "|brightness":
				return new ASTModTerm(ModTypeEnum.targBright, mod);
				
			default:
				break;
		}
		
		return null;
	}

	public void makeModTerm(ArrayList<ASTModTerm> dest, ASTModTerm t) {
		if (t == null) {
			return;
		}
		if (t.getModType() == ModTypeEnum.time) {
			timeWise();
		}
		if (t.getModType() == ModTypeEnum.sat || t.getModType() == ModTypeEnum.satTarg) {
			inColor();
		}
		dest.add(t);
	}
	
	public ASTReplacement makeElement(String s, ASTModification mods, ASTExpression params, boolean subPath) {
		if (inPathContainer && !subPath && (s.equals("FILL") || s.equals("STROKE"))) {
			return new ASTPathCommand(s, mods, params);
		}
		ASTRuleSpecifier r = makeRuleSpecifier(s, params, null, false);
		RepElemListEnum t = RepElemListEnum.replacement;
		if (inPathContainer) {
			boolean isGlobal = false;
			ASTParameter bound = findExpression(r.getShapeType(), isGlobal);
			if (!subPath) {
				error("Replacements are not allowed in paths");
			} else if (r.getArgSource() == ArgSource.StackArgs || r.getArgSource() == ArgSource.ShapeArgs) {
	            // Parameter subpaths must be all ops, but we must check at runtime
				t = RepElemListEnum.op;
			} else if (cfdg.getShapeType(r.getShapeType()) == ShapeTypeEnum.PathType) {
				ASTRule rule = cfdg.findRule(r.getShapeType());
				if (rule != null) {
					t = RepElemListEnum.typeByOrdinal(rule.getRuleBody().getRepType());
				} else {
					error("Subpath references must be to previously declared paths");
				}
			} else if (bound != null) {
	            // Variable subpaths must be all ops, but we must check at runtime
				t = RepElemListEnum.op;
			} else if (isPrimeShape(r.getShapeType())) {
				t = RepElemListEnum.op;
			} else {
				error("Subpath references must be to previously declared paths");
			}
		}
		return new ASTReplacement(r, mods, t); 
	}

	public ASTExpression makeFunction(String name, ASTExpression args, boolean consAllowed) {
		int nameIndex = stringToShape(name, true);
		boolean isGlobal = false;
		ASTParameter bound = findExpression(nameIndex, isGlobal);
		if (bound != null) {
			if (!consAllowed) {
				error("Cannot bind expression to variable/parameter");
			}
			return makeVariable(name).append(args); 
		}
		if (name.equals("select") || name.equals("if")) {
			return new ASTSelect(args, name.equals("if"));
		}
		FuncType t = FuncType.getFuncTypeByName(name);
		if (t == FuncType.NotAFunction) {
			return new ASTFunction(name, args, seed);
		}
		if (args != null && args.getType() == ExpType.ReuseType) {
			return makeRuleSpecifier(name, args, null, false);
		}
		return new ASTUserFunction(name, args, null);
	}
	
	public ASTModification makeModification(ASTModification mod, boolean canonial) {
		mod.setIsConstant(mod.getExpressions().isEmpty());
		mod.setCanonial(canonial);
		return mod;
	}
	
	public String getTypeInfo(int nameIndex, ASTDefine[] func, List<ASTParameter>[] p) {
		func[0] = cfdg.findFunction(nameIndex);
		p[0] = cfdg.getShapeParams(nameIndex);
		return cfdg.decodeShapeName(nameIndex);
	}

	public ASTRule getRule(int nameIndex) {
		return cfdg.findRule(nameIndex);
	}

	public void pushRepContainer(ASTRepContainer c) {
		containerStack.push(c);
		processRepContainer(c);
	}
	
	private void processRepContainer(ASTRepContainer c) {
		c.setStackCount(0);
		for (ASTParameter param : c.getParameters()) {
			if (param.isParameter() || param.isLoopIndex()) {
				param.setStackIndex(localStackDepth);
				c.setStackCount(c.getStackCount() + param.getTupleSize());
				localStackDepth += param.getTupleSize();
			} else {
				break;  // the parameters are all in front
			}
		}
	}
	
	public void popRepContainer(ASTReplacement r) {
		ASTRepContainer lastContainer = containerStack.lastElement();
		localStackDepth -= lastContainer.getStackCount();
		if (r != null) {
			r.setRepType(RepElemListEnum.typeByOrdinal(r.getRepType().ordinal() | lastContainer.getRepType()));
			if (r.getPathOp() == PathOpEnum.UNKNOWN) {
				r.setPathOp(lastContainer.getPathOp());
			}
		}
		containerStack.pop();
	}

	private boolean badContainer(int containerType) {
		return (containerType & (RepElemListEnum.op.ordinal() | RepElemListEnum.replacement.ordinal())) == (RepElemListEnum.op.ordinal() | RepElemListEnum.replacement.ordinal());
	}
	
	public void pushRep(ASTReplacement r, boolean global) {
		if (r == null) {
			return;
		}
		ASTRepContainer container = containerStack.lastElement();
		container.getBody().remove(container.getBody().size() - 1);
		container.getBody().add(r);
		if (container.getPathOp() == PathOpEnum.UNKNOWN) {
			container.setPathOp(r.getPathOp());
		}
		int oldType = container.getRepType();
		container.setRepType(oldType | r.getRepType().ordinal());
		if (badContainer(container.getRepType()) && !badContainer(oldType) && !global) {
			error("Cannot mix path elements and replacements in the same container");
		}
	}
	
	public ASTParameter findExpression(int nameIndex, boolean isGlobal) {
		for (ListIterator<ASTRepContainer> i = containerStack.listIterator(); i.hasPrevious();) {
			ASTRepContainer repCont = i.previous();
			for (ListIterator<ASTParameter> p = repCont.getParameters().listIterator(); i.hasPrevious();) {
				ASTParameter param = p.previous();
				if (param.getNameIndex() == nameIndex) {
					isGlobal = repCont.isGlobal();
					return param;
				}
			}
		}
		return null;
	}
	
	protected void checkVariableName(int nameIndex, boolean param) {
		if (allowOverlap && !param) {
			return;
		}
		ASTRepContainer repCont = param ? paramDecls : containerStack.lastElement();
		for (ListIterator<ASTParameter> i = repCont.getParameters().listIterator(); i.hasPrevious();) {
			ASTParameter p = i.previous();
			if (p.getNameIndex() == nameIndex) {
				error("Scope of name overlaps variable/parameter with same name: + " + p.getLocation());
			}
		}
	}

	protected String relativeFilePath(String base, String rel) {
		int i = base.lastIndexOf("/");
		if (i == -1) {
			return rel;
		}
		return base.substring(0, i) + rel;
	}
	
	protected void popNameSpace() {
		currentNameSpace = currentNameSpace.substring(0, currentNameSpace.length() - 2);
		int end = currentNameSpace.lastIndexOf(":");
		if (end == -1) {
			currentNameSpace = "";
		} else {
			currentNameSpace.substring(0, end + 1);
		}
	}

	protected void pushNameSpace(String n) {
		if (n.equals("CF")) {
			error("CF namespace is reserved");
			return;
		}
		if (n.length() == 0) {
			error("zero-length namespace");
			return;
		}
		checkName(n, false);
		includeNamespace.pop();
		includeNamespace.push(Boolean.TRUE);
		currentNameSpace = currentNameSpace + n + "::";
	}
	
	public void inColor() {
		cfdg.addParameter(Param.Color);
	}

	public void timeWise() {
		cfdg.addParameter(Param.Time);
	}

	public void storeParams(StackRule p) {
		p.setRefCount(Integer.MAX_VALUE);
		longLivedParams.add(p);
	}

	public String getMaybeVersion() {
		return maybeVersion;
	}

	public void setMaybeVersion(String maybeVersion) {
		this.maybeVersion = maybeVersion;
	}
}
