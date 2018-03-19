package test.iteration1.group7;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import main.TypeVisitor7;

public abstract class TypeVisitorTest7 {

	protected static String ls = _TestSuite7.lineSeparator;

	/**
	 * Configures ASTParser and visitor for source file
	 *
	 * @param source
	 * @param expectedDeclarationCount
	 * @param expectedReferenceCount
	 */
	protected static void configureParser(String source, String type, int expectedDeclarationCount,
			int expectedReferenceCount) {
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setSource(source.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setResolveBindings(true);
		parser.setBindingsRecovery(true);
		// these are needed for binding to be resolved due to SOURCE is a char[]
		String[] srcPath = { _TestSuite7.SOURCE_DIR };
		String[] classPath = { _TestSuite7.BIN_DIR };
		parser.setEnvironment(classPath, srcPath, null, true);
		// parser.setEnvironment(null, null, null, true);
		// TODO: Fix up the name to be something other than name?
		parser.setUnitName("Name");

		// ensures nodes are being parsed properly
		Map<String, String> options = JavaCore.getOptions();
		options.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_8);
		options.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, JavaCore.VERSION_1_8);
		options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_8);
		parser.setCompilerOptions(options);

		CompilationUnit cu = (CompilationUnit) parser.createAST(null);

		TypeVisitor7 visitor = new TypeVisitor7(cu, type);
		cu.accept(visitor);

		int declarationCount = 0;
		int referenceCount = 0;
		try {
			declarationCount = visitor.getDeclarationCount();
		} catch (Exception e) {

		}
		try {
			referenceCount = visitor.getReferenceCount();
		} catch (Exception e) {

		}

		assertEquals(expectedDeclarationCount, declarationCount);
		assertEquals(expectedReferenceCount, referenceCount);
	}
}
