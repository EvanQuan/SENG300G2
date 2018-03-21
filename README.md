SENG 300 Group Iteration 2
--------------------------

[Slack Channel link](https://seng300w2018g2i2.slack.com/)
- To do list, problems, questions, and everything can be discussed here!

Getting Started
---------------

#### 1) .classpath
After pulling, create a `.classpath` file specific to your machine (currently ignored in `.gitignore` to prevent conflicts between machines).

If using the Linux computers at the university, copy and paste this:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<classpath>
	<classpathentry kind="con" path="org.eclipse.jdt.launching.JRE_CONTAINER/org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType/JavaSE-1.8"/>
	<classpathentry kind="src" path="src"/>
	<classpathentry kind="con" path="org.eclipse.jdt.junit.JUNIT_CONTAINER/4"/>
	<classpathentry kind="lib" path="/opt/eclipse-java/plugins/org.eclipse.core.jobs_3.9.0.v20170322-0013.jar"/>
	<classpathentry kind="lib" path="/opt/eclipse-java/plugins/org.eclipse.equinox.common_3.9.0.v20170207-1454.jar"/>
	<classpathentry kind="lib" path="/opt/eclipse-java/plugins/org.eclipse.core.resources_3.12.0.v20170417-1558.jar"/>
	<classpathentry kind="lib" path="/opt/eclipse-java/plugins/org.eclipse.osgi_3.12.0.v20170512-1932.jar"/>
	<classpathentry kind="lib" path="/opt/eclipse-java/plugins/org.eclipse.core.contenttype_3.6.0.v20170207-1037.jar"/>
	<classpathentry kind="lib" path="/opt/eclipse-java/plugins/org.eclipse.equinox.preferences_3.7.0.v20170126-2132.jar"/>
	<classpathentry kind="lib" path="/opt/eclipse-java/plugins/org.eclipse.jdt.core_3.13.0.v20170516-1929.jar"/>
	<classpathentry kind="lib" path="/opt/eclipse-java/plugins/org.eclipse.core.runtime_3.13.0.v20170207-1030.jar"/>
	<classpathentry kind="output" path="bin"/>
</classpath>
```

Else, copy and paste this and manually reference the external jars in Eclipse (or use the same `.classpath` file from your previous iteration):

```xml
<?xml version="1.0" encoding="UTF-8"?>
<classpath>
	<classpathentry kind="con" path="org.eclipse.jdt.launching.JRE_CONTAINER/org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType/JavaSE-1.8"/>
	<classpathentry kind="src" path="src"/>
	<classpathentry kind="con" path="org.eclipse.jdt.junit.JUNIT_CONTAINER/4"/>
	<classpathentry kind="output" path="bin"/>
</classpath>
```

#### 2) Debug.java
Ideally we would be debugging with JUnit test cases and the Eclipse debugger.
However, if you feel inclined to use print statements to debug, please create a Debug class in the default package (immediately in the `src` folder) and set it to whatever you like. It will be ignored by git, so you don't have to worry about other people changing it when you push/pull.
```java
/**
 * Debug class. If in path ../src/Debug.java it will be ignored by git.
 */
public class Debug {
	/**
	 * true if debug mode is enabled, else false
	 */
	public static boolean on = true;

	private Debug(){
	}
}
```

Resources
---------

- [ASTParser Javadoc](https://help.eclipse.org/mars/index.jsp?topic=%2Forg.eclipse.jdt.doc.isv%2Freference%2Fapi%2Forg%2Feclipse%2Fjdt%2Fcore%2Fdom%2FASTParser.html)
- [Diagram Drawing Tool](https://draw.io)
	- Exporting diagrams as `.pdf` and save the `.xml` file in case of future edits.

Contact
------------

Evan Quan - 10154242 - evan.quan@ucalgary.ca

Mona Agh - 30033301 - mona.agh1@ucalgary.ca

Zahra Al Ibrahim - 30020048 - zahra.alibrahim@ucalgary.ca

Sunah Kim - 10155604 - suna.kim@ucalgary.ca

Marcello Di Benedetto - 30031839 - marcello.dibenede1@ucalgary.ca
