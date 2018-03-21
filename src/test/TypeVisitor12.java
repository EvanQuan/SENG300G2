package test;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class TypeVisitor12 extends ASTVisitor {

	public int declarationCounter;
	public int referenceCounter;
	public String type;

	public TypeVisitor12(String type) {
		this.type = type;
	}

	@Override
	public boolean visit(AnnotationTypeDeclaration node) {

		ITypeBinding iType = node.resolveBinding();
		String name = iType.getQualifiedName();

		if (name.equals(type)) {
			declarationCounter += 1;
		}

		return true;
	}

	@Override
	public boolean visit(TypeDeclaration node) {

		ITypeBinding iType = node.resolveBinding();
		String name = iType.getQualifiedName();

		if (name.equals(type)) {
			declarationCounter += 1;
		}

		return true;
	}

	@Override
	public boolean visit(QualifiedName node) {

		if (node.getFullyQualifiedName().equals(type)) {
			referenceCounter += 1;
		}

		return true;
	}

	@Override
	public boolean visit(PrimitiveType node) {

		ITypeBinding iType = node.resolveBinding();
		String name = iType.getQualifiedName();

		if (name.equals(type)) {
			referenceCounter += 1;
		}

		return true;
	}

	@Override
	public boolean visit(SimpleName node) {

		if (node.getFullyQualifiedName().equals(type) && !node.isDeclaration()) {
			referenceCounter += 1;
		}
		return true;
	}

	@Override
	public boolean visit(EnumDeclaration node) {

		ITypeBinding iType = node.resolveBinding();
		String name = iType.getQualifiedName();

		if (name.equals(type)) {
			declarationCounter += 1;
		}
		return true;
	}
}
