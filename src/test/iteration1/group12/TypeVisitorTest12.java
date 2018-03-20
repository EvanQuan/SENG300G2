package test.iteration1.group12;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

public abstract class TypeVisitorTest12 {

	protected static String ls = _TestSuite12.lineSeparator;

	/**
	 * Configures ASTParser and visitor for source file
	 *
	 * @param source
	 * @param type
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
		String[] srcPath = { _TestSuite12.SOURCE_DIR };
		String[] classPath = { _TestSuite12.BIN_DIR };
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

		TypeVisitor12 visitor = new TypeVisitor12(type);
		cu.accept(visitor);

		int declarationCount = 0;
		int referenceCount = 0;
		try {
			declarationCount = visitor.declarationCounter;
		} catch (Exception e) {

		}
		try {
			referenceCount = visitor.referenceCounter;
		} catch (Exception e) {

		}

		assertEquals(expectedDeclarationCount, declarationCount);
		assertEquals(expectedReferenceCount, referenceCount);

	}
}