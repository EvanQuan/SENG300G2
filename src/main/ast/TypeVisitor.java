package main.ast;

import java.util.ArrayList;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.IAnnotationBinding;
import org.eclipse.jdt.core.dom.IPackageBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.QualifiedType;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import main.util.Multiset;

/**
 * A visitor for abstract syntax trees. For each different concrete AST node
 * type T, the visitor will locate the different java types present in the
 * source code, and count the number of declarations of references for each of
 * the java types present.
 * 
 * Type and subtypes
 * http://help.eclipse.org/kepler/ntopic/org.eclipse.jdt.doc.isv/reference/api/org/eclipse/jdt/core/dom/Type.html
 * http://help.eclipse.org/kepler/ntopic/org.eclipse.jdt.doc.isv/reference/api/org/eclipse/jdt/core/dom/VariableDeclarationFragment.html
 * 
 * @author Evan Quan
 * @version 2.3.0
 * @since 21 March 2018
 */
public class TypeVisitor extends ASTVisitor {

	private boolean debug;
	private ArrayList<String> types;
	private Multiset<String> declarations;
	private Multiset<String> references;
	private String packageName;

	/**
	 * Checks if the passed type already exists within the types list. [false -> add
	 * type to list create entry <type, 0> in decCounter create entry <type, 0> in
	 * refCounter] [true -> do nothing]
	 *
	 * @param type:
	 *            String, java type
	 */
	private void addTypeToList(String type) {
		if (!types.contains(type)) {
			types.add(type);
		}
	}

	/**
	 * Increment the counter value for a given type in decCounter.
	 *
	 * @param type
	 *            String, java type
	 */
	private void incrementDeclaration(String type) {
		// Check if the type exists, then increment their associated value by 1
		addTypeToList(type);
		declarations.add(type);
	}

	/**
	 * Increment the counter value for a given type in refCounter.
	 *
	 * @param type
	 *            String, java type
	 */
	private void incrementReference(String type) {
		// Check if the type exists, then increment their associated value by 1
		addTypeToList(type);
		references.add(type);
	}

	/*
	 * ========================= DEBUG FUNCTIONS =========================
	 */
	private void debug(Object message) {
		if (debug) {
			System.out.println(message);
		}
	}

	private void debug(Object node, Object type) {
		if (debug) {
			System.out.println("Node: " + node + " | Type: " + type);
		}
	}
	/*
	 * ========================= HELPER FUNCTIONS =========================
	 */

	/**
	 * Default constructor. Initialize the list of types, and the HashMaps for the
	 * counters to null.
	 */
	public TypeVisitor() {
		this(false); // Debug is false
	}

	/**
	 * Debug constructor.
	 * 
	 * @param debug
	 */
	public TypeVisitor(boolean debug) {
		this.debug = debug;
		this.types = new ArrayList<String>();
//		this.declarations = new HashMap<String, Integer>();
//		this.references = new HashMap<String, Integer>();
		this.declarations = new Multiset<String>();
		this.references = new Multiset<String>();
	}

	/**
	 * Accessor method. Fetches the map of declarations.
	 *
	 * @return declarations
	 */
//	public HashMap<String, Integer> getDeclarations() {
//		return declarations;
//	}
	public Multiset<String> getDeclarations() {
		return declarations;
	}

	/**
	 * Accessor method. Fetches the list of types.
	 *
	 * @return types
	 */
	public ArrayList<String> getTypes() {
		return types;
	}

	/**
	 * Accessor method. Fetches the map of references.
	 *
	 * @return references
	 */
//	public HashMap<String, Integer> getReferences() {
//		return references;
//	}
	public Multiset<String> getReferences() {
		return references;
	}

	/*
	 * ========================== ASTVisitor FUNCTIONS ==========================
	 */

	/**
	 * Visits an annotation type declaration AST node type. Looks for
	 *
	 * @interface <identifier> { }
	 *
	 *            Determine the type of the annotation, add it to types, and
	 *            increment its type's counter in decCounter.
	 *
	 *            CounterType: DECLARATION
	 *
	 * @param node
	 *            AnnotationTypeDeclaration
	 * @return boolean true to visit the children of this node
	 */
	@Override // SAME
	public boolean visit(AnnotationTypeDeclaration node) {
		ITypeBinding typeBind = node.resolveBinding();
		String type = typeBind.getQualifiedName();

		debug("AnnotationTypeDeclaration", type);
		incrementDeclaration(type);

		return true;
	}

	/**
	 * Visits an array creation AST node type. Looks for new PrimitiveType [
	 * Expression ] { [ Expression ] } { [ ] } new TypeName [ < Type { , Type } > ]
	 * [ Expression ] { [ Expression ] } { [ ] } new PrimitiveType [ ] { [ ] }
	 * ArrayInitializer new TypeName [ < Type { , Type } > ] [ ] { [ ] }
	 * ArrayInitializer
	 *
	 * Determine the elements' type (String[] = String), add it to types, and
	 * increment its type's counter in refCounter.
	 *
	 * CounterType: REFERENCE
	 *
	 * @param node
	 *            ArrayCreation
	 * @return boolean true to visit the children of this node
	 */
	// @Override // TODO replace with ArrayType?
	// public boolean visit(ArrayCreation node) {
	// ITypeBinding typeBind = node.getType().getElementType().resolveBinding();
	// String type = typeBind.getQualifiedName();
	//
	// debug("ArrayCreation", type);
	//
	// addTypeToList(type);
	// incrementReference(type);
	//
	// return true;
	// }
	/**
	 * Visits an Array reference Foo[]
	 * 
	 * @param node
	 * @return true to visit the children of this node
	 */
	@Override
	public boolean visit(ArrayType node) {
		ITypeBinding typeBind = node.resolveBinding();
		String type = typeBind.getQualifiedName();

		debug("ArrayType", type);
		incrementReference(type);

		return true;
	}

	// TODO return here
	@Override
	public boolean visit(PrimitiveType node) {
		ITypeBinding typeBind = node.resolveBinding();
		String type = typeBind.getQualifiedName();

		// void is not a primitive type
		if (!type.equals("void")) {
			debug("PrimitiveType", type);
			incrementReference(type);
		}
		return true;
	}

	// TODO what is this for? Example of QualifiedType
	@Override
	public boolean visit(QualifiedType node) {
		ITypeBinding typeBind = node.resolveBinding();
		String type = typeBind.getQualifiedName();

		debug("QualifiedType", type);
		incrementReference(type);

		return true;
	}
	
	/**
	 * Used to detect static field calls.
	 * type: Class.field
	 * qualifier: Class
	 */
	@Override
	public boolean visit(QualifiedName node) {
		String type = node.getFullyQualifiedName();
		debug("QualifiedName", type);
		debug("\tnode.getName(): " + node.getName());
		debug("\tnode.getFullyQualifiedName(): " + node.getFullyQualifiedName());
		debug("\tnode.getQualifier(): " + node.getQualifier());
		
		// RETURN HERE
		Name qualifier = node.getQualifier();
		String qualifierName = qualifier.getFullyQualifiedName();
		if (!qualifier.isQualifiedName()) {
			qualifierName = appendPackageName(qualifierName);
		}
		debug("\tname qualified: " + qualifierName);

		ASTNode parent = node.getParent();
		Class<? extends ASTNode> parentNode = parent.getClass();
		String parentNodeName = parentNode.getSimpleName();
		
		debug("\tParent: " + parentNodeName);
//
		// Check parent.
		// VariableDeclarationFragment means staticField returned
		// Assignment means static field set
		if (parentNode.equals(VariableDeclarationFragment.class) || parentNode.equals(Assignment.class)) {
			incrementReference(qualifierName);
		}
		return true;
	}

//	public boolean visit(MethodInvocation node) {
//		SimpleName methodName = node.getName();
//		debug("MethodInvocation", methodName.toString());
//
//
//		ASTNode parent = node.getParent();
//		Class<? extends ASTNode> parentNode = parent.getClass();
//		String parentNodeName = parentNode.getSimpleName();
//		
//		debug("\tParent", parentNodeName);
//
//		return true;
//	}
	
	
	// TODO Remove?
	// These can detect static methods/fields. is there some other way?
	/**
	 * Detects static method calls
	 */
	@Override
	public boolean visit(SimpleName node) {
		String type = node.getFullyQualifiedName();
		ASTNode parent = node.getParent();
		Class<? extends ASTNode> parentNode = parent.getClass();
		String parentNodeName = parentNode.getSimpleName();
		
		debug("SimpleName", type);
		debug("SimpleNameParent", parentNodeName);
//		incrementReference(type);
		return true;
	}

	@Override
	public void preVisit(ASTNode node) {
//		debug("\n\nPREVISIT");
	}
	
	@Override
	public void postVisit(ASTNode node) {
//		debug("POSTVISIT");
	}
	// TODO
	// What is this?
//	@Override
//	public boolean visit(UnionType node) {
//		ITypeBinding typeBind = node.resolveBinding();
//		String type = typeBind.getQualifiedName();
//
//		debug("UnionType", type);
//
//		addTypeToList(type);
//		incrementReference(type);
//		return true;
//	}

	/**
	 * Visits a Enum declaration AST node type. Determine the type of the Enum
	 * identifier, add it to types, and increment its type's counter value in
	 * decCounter.
	 *
	 * CounterType: DECLARATION
	 *
	 * @param node
	 *            : EnumDeclaration
	 * @return boolean : True to visit the children of this node
	 */
	@Override
	public boolean visit(EnumDeclaration node) {
		ITypeBinding typeBind = node.resolveBinding();
		String type = typeBind.getQualifiedName();

		debug("EnumDeclaration", type);
		incrementDeclaration(type);

		return true;
	}
	
	public boolean visit(PackageDeclaration node) {
		IPackageBinding packageBind = node.resolveBinding();
		packageName = packageBind.getName();
		debug("PackageDeclaration", packageName);
		return true;
	}
	
	/**
	 * Append the current package name to name if it exists
	 * @param name
	 * @return
	 */
	private String appendPackageName(String name) {
		debug("\tBEFORE APPEND:" + name);
		if (packageName != null && !name.contains(".")) { // Check if in default package
			name = packageName + "." + name;
		}
		debug("\tAFTER APPEND:" + name);
		return name;
	}

	/**
	 * Intent: replace FieldDeclaration TODO Does this result in double count?
	 */
	@Override
	public boolean visit(SimpleType node) {
		ITypeBinding typeBind = node.resolveBinding();
		String type;
		// Strips parameterized generics off
		type = typeBind.getTypeDeclaration().getQualifiedName();

		// Add package name if does not contain package name and not in default package
		IPackageBinding packBind = typeBind.getPackage();
		String packName = packBind.getName();
		if (!type.contains(".") && packName.length() > 0) {
			type = packName + "." + type;
		}

		debug("SimpleType", type);
		incrementReference(type);

		// Check for ArrayTypes
		// Should count for both simple and array
		if (node.isArrayType()) {

		}

		return true;
	}

	/**
	 * Intent: replace FieldDeclaration TODO Does this result in double count?
	 */
//	@Override
//	public boolean visit(ParameterizedType node) {
//		ITypeBinding typeBind = node.resolveBinding();
//		String type = typeBind.getTypeDeclaration().getQualifiedName();
//
//		debug("ParameterizedType", type);
//		// Add Parameterized Type
//		addTypeToList(type);
//		incrementReference(type);
//
//		return true;
//	}

	/**
	 * Visits a Field declaration node type. This type of node collects MULTIPLE
	 * VARIABLE DECL FRAGMENT into a single body declaration. They all share the
	 * same base type.
	 *
	 * Determine the type of the Field identifier, add it to types, and increment
	 * its type's counter value in refCounter based on the number of fragments.
	 *
	 * CounterType: REFERENCE
	 *
	 * @param node
	 *            : FieldDeclaration
	 * @return boolean : True to visit the children of this node
	 */
	// TODO This is to be replaced by lower children nodes
	// @Override
	// public boolean visit(FieldDeclaration node) {
	// boolean isParameterized = node.getType().isParameterizedType();
	//
	// if (isParameterized) {
	// ITypeBinding typeBind = node.getType().resolveBinding().getTypeDeclaration();
	// String type = typeBind.getQualifiedName();
	//
	// addTypeToList(type);
	// incrementReference(type);
	//
	// // inc count for all the arguments
	// for (ITypeBinding paramBind :
	// node.getType().resolveBinding().getTypeArguments()) {
	// String paramType = paramBind.getQualifiedName();
	// addTypeToList(paramType);
	// incrementReference(paramType);
	// }
	//
	// // get initializers if they exists
	// List<VariableDeclarationFragment> fragments = node.fragments();
	// for (VariableDeclarationFragment fragment : fragments) {
	// if (fragment.getInitializer() instanceof TypeLiteral) {
	// String initType = ((TypeLiteral)
	// fragment.getInitializer()).getType().resolveBinding()
	// .getQualifiedName();
	// addTypeToList(initType);
	// incrementReference(initType);
	// }
	// }
	//
	// } else {
	// boolean isArrayType = node.getType().isArrayType();
	// if (isArrayType) {
	// ITypeBinding arrTypeBind = node.getType().resolveBinding().getElementType();
	// String type = arrTypeBind.getQualifiedName();
	// addTypeToList(type);
	// incrementReference(type);
	// } else {
	// ITypeBinding typeBind = node.getType().resolveBinding();
	// String type = typeBind.getQualifiedName();
	//
	// addTypeToList(type);
	//
	// // iterate through all the fragments, and increment the type counter
	// for (Object fragment : node.fragments()) {
	// if (fragment instanceof VariableDeclarationFragment) {
	// incrementReference(type);
	// }
	// }
	// }
	// }
	// return true;
	// }

	/**
	 * Visits for statements AST node type. for (forInit; expression; forUpdate)
	 *
	 * forInit & forUpdate are of type Expression
	 *
	 * Determine the type of the expression in forInit, add it to types, and
	 * increment its type's counter in refCounter.
	 *
	 * CounterType: REFERENCE
	 *
	 * @param node
	 *            ForStatement
	 * @return boolean true to visit its children nodes
	 */
//	@Override
//	public boolean visit(ForStatement node) {
//		// Initializers
//		List<VariableDeclarationExpression> varExprs = node.initializers();
//
//		for (VariableDeclarationExpression varExpr : varExprs) {
//			String type = varExpr.getType().resolveBinding().getQualifiedName();
//			addTypeToList(type);
//
//			for (Object fragment : varExpr.fragments()) {
//				if (fragment instanceof VariableDeclarationFragment) {
//					incrementReference(type);
//				}
//			}
//		}
//
//		return true;
//	}

	/**
	 * import bar.Foo;
	 * 
	 * Gets "bar.Foo"
	 */
	@Override
	public boolean visit(ImportDeclaration node) {
		if (node.getName().resolveTypeBinding() != null) {
			String type = node.getName().toString();
			// Importing wildcard (eg. import bar.*) will return only package name (bar).
			// Since we want a fully qualified class name, we reject only package name.
			if (type.contains(".")) {
				debug("ImportDeclaration", type);
				incrementReference(type);
			}
		}

		return true;
	}

	/**
	 * Visits a Marker annotation node type. Marker annotation "@<typeName>" is
	 * equivalent to normal annotation "@<typeName>()"
	 *
	 * Determine the type of annotation, add it to types, and increment its type's
	 * counter value in refCounter.
	 *
	 * CounterType: REFERENCE
	 *
	 * @param node
	 *            MarkerAnnotation
	 * @return boolean : True to visit the children of this node
	 *
	 *         TODO: Cannot recognize full qualified names for IMPORTS. Works for
	 *         java.lang.* e.g. @Test from org.junit.Test appears as
	 *         <currentPackage>.Test
	 */
	@Override
	public boolean visit(MarkerAnnotation node) {
		IAnnotationBinding annBind = node.resolveAnnotationBinding();
		ITypeBinding typeBind = annBind.getAnnotationType();
		String type = typeBind.getQualifiedName();

		debug("MarkerAnnotation", type);
		incrementReference(type);

		return true;
	}

	/**
	 * Visits a Method declaration node type. Method declaration is a union of
	 * method declaration and constructor declaration. (void is not a type, any void
	 * methods will be ignored)
	 *
	 * Determine if the method is a constructor. [true -> true] [false -> get return
	 * type of method add type to types increment reference count return true]
	 *
	 * CounterType: REFERENCE
	 *
	 * TODO: Get return type parameters -- should be done, please double check
	 *
	 * @param node
	 *            : MethodDeclaration
	 * @return boolean : True to visit the children of this node
	 */
//	@Override
//	public boolean visit(MethodDeclaration node) {
//		boolean isConstructor = node.isConstructor();
//
//		if (!isConstructor) {
//			boolean isParameterized = node.getReturnType2().isParameterizedType();
//			if (isParameterized) {
//				ITypeBinding typeBind = node.getReturnType2().resolveBinding().getTypeDeclaration();
//				String type = typeBind.getQualifiedName();
//
//				addTypeToList(type);
//				incrementReference(type);
//
//				for (ITypeBinding paramBind : node.getReturnType2().resolveBinding().getTypeArguments()) {
//					String paramType = paramBind.getQualifiedName();
//					addTypeToList(paramType);
//					incrementReference(paramType);
//				}
//			} else {
//				// their type = return type
//				ITypeBinding typeBind = node.getReturnType2().resolveBinding();
//				String type = typeBind.getQualifiedName();
//
//				// ignore all void methods
//				if (!type.equals("void")) {
//					addTypeToList(type);
//					incrementReference(type);
//				}
//			}
//		} else {
//			// These are constructors, their type = declaring class
//			ITypeBinding typeBind = node.resolveBinding().getDeclaringClass();
//			String type = typeBind.getQualifiedName();
//
//			addTypeToList(type);
//			incrementReference(type);
//		}
//		return true;
//	}

	/**
	 * Visits normal annotation AST node type. @ TypeName ( [ MemberValuePair { ,
	 * MemberValuePair } ] )
	 *
	 * Determine the typename of the normal annotation, add it to the types, and
	 * increment the type's counter in refCounter.
	 *
	 * This also goes into the MemberValuePair, and for all TypeLiterals, the type
	 * is recorded, and its reference counter incremented
	 *
	 * CounterType: Reference
	 *
	 * @param node
	 *            NormalAnnotation
	 * @return boolean true to visit its children nodes
	 */
//	@Override
	public boolean visit(NormalAnnotation node) {
		IAnnotationBinding annBind = node.resolveAnnotationBinding();
		ITypeBinding typeBind = annBind.getAnnotationType();
		String type = typeBind.getQualifiedName();

		debug("NormalAnnotation", type);
		incrementReference(type);

		return true;
	}
	
	@Override
	public boolean visit(SingleMemberAnnotation node) {
		IAnnotationBinding annBind = node.resolveAnnotationBinding();
		ITypeBinding typeBind = annBind.getAnnotationType();
		String type = typeBind.getQualifiedName();

		debug("SingleMemberAnnotation", type);
		incrementReference(type);

		return true;
	}

	/**
	 * Visits a single variable declaration node type. These are used only in formal
	 * parameter lists, and catch clauses. They are not used for field declarations,
	 * and regular variable declaration statements
	 *
	 * Determine the type of variable, add it to types, and increment the counter
	 * value associated to the type in refCounter.
	 *
	 * CounterType: REFERENCE
	 *
	 * @param node:
	 *            SingleVariableDeclaration
	 * @return boolean : True to visit the children of this node
	 */
//	@Override
//	public boolean visit(SingleVariableDeclaration node) {
//		boolean isParameterized = node.getType().isParameterizedType();
//
//		// get parameterized variables
//		if (isParameterized) {
//			ITypeBinding typeBind = node.getType().resolveBinding().getTypeDeclaration();
//			String type = typeBind.getQualifiedName();
//
//			addTypeToList(type);
//			incrementReference(type);
//
//			// inc count for all the arguments
//			for (ITypeBinding paramBind : node.getType().resolveBinding().getTypeArguments()) {
//				String paramType = paramBind.getQualifiedName();
//				addTypeToList(paramType);
//				incrementReference(paramType);
//			}
//
//		} else {
//			IVariableBinding varBind = node.resolveBinding();
//			ITypeBinding typeBind = varBind.getType();
//			String type = typeBind.getQualifiedName();
//
//			addTypeToList(type);
//			incrementReference(type);
//		}
//
//		return true;
//	}

	/**
	 * Visits a type declaration node type. Type declaration node is the union of
	 * class declaration, and interface declaration.
	 *
	 * Determine the type of class, add it to types, and increment the declaration
	 * counter associated to the type.
	 *
	 * CounterType: DECLARATION
	 *
	 * @param node
	 *            : TypeDeclaration
	 * @return boolean : True to visit the children of this node
	 */
	// TODO This is to be override by children nodes
	@Override
	public boolean visit(TypeDeclaration node) {
		ITypeBinding typeBind = node.resolveBinding();
		String type = typeBind.getQualifiedName();

		// Local classes do not have qualified names, only simple names
		if (type.equals("")) {
			type = typeBind.getTypeDeclaration().getName();
			debug("TypeDclaration LOCAL", type);
		} else {
			debug("TypeDeclaration", type);
		}

		incrementDeclaration(type);

		return true;
	}

	/**
	 * Visits a local variable declaration statement node type. This type of node
	 * contains several variable declaration fragments into a statement. They all
	 * have the same base type and modifier.
	 *
	 * Determine the type of variable, add it to types, and increment the
	 * declaration counter associated to the type depending on the number of
	 * fragments.
	 *
	 * Note: For any imported packages methods/classes, you must include the full
	 * qualified name in the code itself in order for this parser to bind it as the
	 * type
	 *
	 * CounterType: REFERENCE
	 *
	 * @param node
	 *            : VariableDeclarationStatement
	 * @return boolean : True to visit the children of this node
	 */
//	@Override
//	public boolean visit(VariableDeclarationStatement node) {
//		boolean isParameterized = node.getType().isParameterizedType();
//
//		// get parameterized variables
//		if (isParameterized) {
//			ITypeBinding typeBind = node.getType().resolveBinding().getTypeDeclaration();
//			String type = typeBind.getQualifiedName();
//
//			addTypeToList(type);
//			incrementReference(type);
//
//			// inc count for all the arguments
//			for (ITypeBinding paramBind : node.getType().resolveBinding().getTypeArguments()) {
//				String paramType = paramBind.getQualifiedName();
//				addTypeToList(paramType);
//				incrementReference(paramType);
//			}
//
//		} else {
//			// iterate through all the fragments, and increment the type counter
//			for (Object fragment : node.fragments()) {
//				if (fragment instanceof VariableDeclarationFragment) {
//					ITypeBinding arrTypeBind = ((VariableDeclarationFragment) fragment).resolveBinding().getType()
//							.getElementType();
//					ITypeBinding typeBind = ((VariableDeclarationFragment) fragment).resolveBinding().getType();
//					String type = typeBind.getQualifiedName();
//
//					if (arrTypeBind != null) {
//						type = arrTypeBind.getQualifiedName();
//					}
//
//					addTypeToList(type);
//					incrementReference(type);
//				}
//			}
//
//		}
//
//		return true;
//	}
	// TODO what does this cover?
//	 public boolean visit(VariableDeclarationFragment node) {
//		 SimpleName name = node.getName();
//		ITypeBinding typeBind = name.resolveTypeBinding();
//		String type = typeBind.getQualifiedName();
//
//		debug("VariableDeclarationFragment", type);
//
//		addTypeToList(type);
//		incrementReference(type);
//		return true;
//	 }
	
	// TODO remove this
//	public boolean visit(FieldAccess node) {
//		ITypeBinding typeBind = node.resolveTypeBinding();
//		String type = typeBind.getQualifiedName();
//
//		debug("FieldAccess", type);
//
//		addTypeToList(type);
//		incrementReference(type);
//		return true;
//	}

}
