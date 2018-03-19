package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IAnnotationBinding;
import org.eclipse.jdt.core.dom.IPackageBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

/**
 * A visitor for abstract syntax trees. For each different concrete AST node
 * type T, the visitor will locate the different java types present in the
 * source code, and count the number of declarations of references for each of
 * the java types present.
 * 
 * TODO - SimpleType - SingleMemberAnnotation
 * 
 * 
 *
 * @author Sze Lok Irene Chan
 * @author Evan Quan
 * @version 1.1.0
 *
 * @since 19 March 2018
 */
public class TypeVisitor extends ASTVisitor {

	private ArrayList<String> types;
	private HashMap<String, Integer> declarations;
	private HashMap<String, Integer> references;

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
			declarations.put(type, 0);
			references.put(type, 0);
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
		if (declarations.containsKey(type)) {
			declarations.put(type, declarations.get(type) + 1);
		}
	}

	/**
	 * Increment the counter value for a given type in refCounter.
	 *
	 * @param type
	 *            String, java type
	 */
	private void incrementReference(String type) {
		// Check if the type exists, then increment their associated value by 1
		if (references.containsKey(type)) {
			references.put(type, references.get(type) + 1);
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
		// initialize list and counters to null
		types = new ArrayList<String>();
		declarations = new HashMap<String, Integer>();
		references = new HashMap<String, Integer>();
	}

	/**
	 * Accessor method. Fetches the map of declarations.
	 *
	 * @return declarations
	 */
	public HashMap<String, Integer> getDeclarations() {
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
	public HashMap<String, Integer> getReferences() {
		return references;
	}

	/*
	 * ============================== ASTVisitor FUNCTIONS
	 * ==============================
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
	@Override
	public boolean visit(AnnotationTypeDeclaration node) {
		ITypeBinding typeBind = node.resolveBinding();
		String type = typeBind.getQualifiedName();

		addTypeToList(type);
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
	@Override
	public boolean visit(ArrayCreation node) {
		ITypeBinding typeBind = node.getType().getElementType().resolveBinding();
		String type = typeBind.getQualifiedName();

		addTypeToList(type);
		incrementReference(type);

		return true;
	}

	/**
	 * Visits a Class instance creation expression AST node type. Determine the type
	 * of the Class instance being created, add it to types, and increment its
	 * type's counter value in refCounter.
	 *
	 * CounterType: REFERENCE
	 *
	 * LIMITATION: Given public class Other { Fuck x = new Bar<Foo, String, Foo>();
	 * } if Bar is not declared before, then the parameter arguments Foo, String,
	 * Foo will not be recognized
	 *
	 * @param node
	 *            : ClassInstanceCreation
	 * @return boolean : True to visit the children of this node
	 */
	@Override
	public boolean visit(ClassInstanceCreation node) {
		boolean isParameterized = node.getType().isParameterizedType();
		if (isParameterized) {
			ITypeBinding typeBind = node.getType().resolveBinding().getTypeDeclaration();
			String type = typeBind.getQualifiedName();

			addTypeToList(type);
			incrementReference(type);

			// inc count for all the arguments
			for (ITypeBinding paramBind : node.getType().resolveBinding().getTypeArguments()) {
				String paramType = paramBind.getQualifiedName();
				addTypeToList(paramType);
				incrementReference(paramType);
			}
		} else {
			/**
			 * Limitation: Unless the type in new <Type>(); is a nested class or a
			 * java.lang.whatever shit, it will not be able to compute the full qualified
			 * name (main.FUCK.foo)
			 */
			ITypeBinding typeBind = node.getType().resolveBinding();
			String type = typeBind.getQualifiedName();
			IPackageBinding packBind = typeBind.getPackage();
			String packName = packBind.getName();

			// Add package name if does not contain package name and not in default package
			if (!type.contains(".") && packName.length() > 0) {
				type = packName + "." + type;
			}

			addTypeToList(type);
			incrementReference(type);
		}

		return true;
	}

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

		addTypeToList(type);
		incrementDeclaration(type);

		return true;
	}

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
	@Override
	public boolean visit(FieldDeclaration node) {
		boolean isParameterized = node.getType().isParameterizedType();

		if (isParameterized) {
			ITypeBinding typeBind = node.getType().resolveBinding().getTypeDeclaration();
			String type = typeBind.getQualifiedName();

			addTypeToList(type);
			incrementReference(type);

			// inc count for all the arguments
			for (ITypeBinding paramBind : node.getType().resolveBinding().getTypeArguments()) {
				String paramType = paramBind.getQualifiedName();
				addTypeToList(paramType);
				incrementReference(paramType);
			}

			// get initializers if they exists
			List<VariableDeclarationFragment> fragments = node.fragments();
			for (VariableDeclarationFragment fragment : fragments) {
				if (fragment.getInitializer() instanceof TypeLiteral) {
					String initType = ((TypeLiteral) fragment.getInitializer()).getType().resolveBinding()
							.getQualifiedName();
					addTypeToList(initType);
					incrementReference(initType);
				}
			}

		} else {
			boolean isArrayType = node.getType().isArrayType();
			if (isArrayType) {
				ITypeBinding arrTypeBind = node.getType().resolveBinding().getElementType();
				String type = arrTypeBind.getQualifiedName();
				addTypeToList(type);
				incrementReference(type);
			} else {
				ITypeBinding typeBind = node.getType().resolveBinding();
				String type = typeBind.getQualifiedName();

				addTypeToList(type);

				// iterate through all the fragments, and increment the type counter
				for (Object fragment : node.fragments()) {
					if (fragment instanceof VariableDeclarationFragment) {
						incrementReference(type);
					}
				}
			}
		}
		return true;
	}

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
	@Override
	public boolean visit(ForStatement node) {
		// Initializers
		List<VariableDeclarationExpression> varExprs = node.initializers();

		for (VariableDeclarationExpression varExpr : varExprs) {
			String type = varExpr.getType().resolveBinding().getQualifiedName();
			addTypeToList(type);

			for (Object fragment : varExpr.fragments()) {
				if (fragment instanceof VariableDeclarationFragment) {
					incrementReference(type);
				}
			}
		}

		return true;
	}

	/**
	 * ToDO: javadocs for this
	 */
	@Override
	public boolean visit(ImportDeclaration node) {
		if (node.getName().resolveTypeBinding() != null) {
			String type = node.getName().resolveTypeBinding().getQualifiedName();
			addTypeToList(type);
			incrementReference(type);
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

		addTypeToList(type);
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
	@Override
	public boolean visit(MethodDeclaration node) {
		boolean isConstructor = node.isConstructor();

		if (!isConstructor) {
			boolean isParameterized = node.getReturnType2().isParameterizedType();
			if (isParameterized) {
				ITypeBinding typeBind = node.getReturnType2().resolveBinding().getTypeDeclaration();
				String type = typeBind.getQualifiedName();

				addTypeToList(type);
				incrementReference(type);

				for (ITypeBinding paramBind : node.getReturnType2().resolveBinding().getTypeArguments()) {
					String paramType = paramBind.getQualifiedName();
					addTypeToList(paramType);
					incrementReference(paramType);
				}
			} else {
				// their type = return type
				ITypeBinding typeBind = node.getReturnType2().resolveBinding();
				String type = typeBind.getQualifiedName();

				// ignore all void methods
				if (!type.equals("void")) {
					addTypeToList(type);
					incrementReference(type);
				}
			}
		} else {
			// These are constructors, their type = declaring class
			ITypeBinding typeBind = node.resolveBinding().getDeclaringClass();
			String type = typeBind.getQualifiedName();

			addTypeToList(type);
			incrementReference(type);
		}
		return true;
	}

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
	@Override
	public boolean visit(NormalAnnotation node) {
		IAnnotationBinding annBind = node.resolveAnnotationBinding();
		ITypeBinding typeBind = annBind.getAnnotationType();
		String type = typeBind.getQualifiedName();

		addTypeToList(type);
		incrementReference(type);

		List<MemberValuePair> valuePairs = node.values();

		for (MemberValuePair valuePair : valuePairs) {
			if (valuePair.getValue() instanceof TypeLiteral) {
				String valType = ((TypeLiteral) valuePair.getValue()).getType().resolveBinding().getQualifiedName();
				addTypeToList(valType);
				incrementReference(valType);
			}
		}

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
	@Override
	public boolean visit(SingleVariableDeclaration node) {
		boolean isParameterized = node.getType().isParameterizedType();

		// get parameterized variables
		if (isParameterized) {
			ITypeBinding typeBind = node.getType().resolveBinding().getTypeDeclaration();
			String type = typeBind.getQualifiedName();

			addTypeToList(type);
			incrementReference(type);

			// inc count for all the arguments
			for (ITypeBinding paramBind : node.getType().resolveBinding().getTypeArguments()) {
				String paramType = paramBind.getQualifiedName();
				addTypeToList(paramType);
				incrementReference(paramType);
			}

		} else {
			IVariableBinding varBind = node.resolveBinding();
			ITypeBinding typeBind = varBind.getType();
			String type = typeBind.getQualifiedName();

			addTypeToList(type);
			incrementReference(type);
		}

		return true;
	}

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
	@Override
	public boolean visit(TypeDeclaration node) {
		ITypeBinding typeBind = node.resolveBinding();
		String type = typeBind.getQualifiedName();

		addTypeToList(type);
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
	@Override
	public boolean visit(VariableDeclarationStatement node) {
		boolean isParameterized = node.getType().isParameterizedType();

		// get parameterized variables
		if (isParameterized) {
			ITypeBinding typeBind = node.getType().resolveBinding().getTypeDeclaration();
			String type = typeBind.getQualifiedName();

			addTypeToList(type);
			incrementReference(type);

			// inc count for all the arguments
			for (ITypeBinding paramBind : node.getType().resolveBinding().getTypeArguments()) {
				String paramType = paramBind.getQualifiedName();
				addTypeToList(paramType);
				incrementReference(paramType);
			}

		} else {
			// iterate through all the fragments, and increment the type counter
			for (Object fragment : node.fragments()) {
				if (fragment instanceof VariableDeclarationFragment) {
					ITypeBinding arrTypeBind = ((VariableDeclarationFragment) fragment).resolveBinding().getType()
							.getElementType();
					ITypeBinding typeBind = ((VariableDeclarationFragment) fragment).resolveBinding().getType();
					String type = typeBind.getQualifiedName();

					if (arrTypeBind != null) {
						type = arrTypeBind.getQualifiedName();
					}

					addTypeToList(type);
					incrementReference(type);
				}
			}

		}

		return true;
	}

}