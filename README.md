# Static analyzer

This static analyzer checks ***java*** code for some warnings.

## Usage

```
java -jar analyzer.jar <project location> <output file> [-v]
```

You could write just warnings to the output file or see
the whole process in the console using ***-v*** flag.

To warm up you can run our analyzer in the root of this
project:
```
java -jar analyzer.jar . output.txt
```

We have provided two java files with some warnings to show
you around the analyzer. This is the generated output:
```
18 warnings were found:

Class ru.B: Warning: Same operands. From Line: 12, column: 9 to Line: 12, column: 13
Statement: "a = a"

Class ru.B: Warning: Same operands. From Line: 18, column: 9 to Line: 18, column: 14
Statement: "a |= a"

Class ru.B: Warning: Empty block. From Line: 14, column: 9 to Line: 16, column: 9
Statement: "while (a != 0) {
}"

Class ru.B: Warning: Empty block. From Line: 20, column: 9 to Line: 22, column: 9
Statement: "synchronized (this) {
}"

Warning: No package was specified in /Users/Alex/Documents/Education/Analyzer/src/main/java/A.java.

Warning: Wildcard import in /Users/Alex/Documents/Education/Analyzer/src/main/java/A.java. From Line: 6, column: 1 to Line: 6, column: 19
Statement: "import java.util.*;"

Class A: Warning: Useless operation. From Line: 12, column: 17 to Line: 12, column: 21
Statement: "x | 0"

Class A: Warning: Useless operation. From Line: 13, column: 17 to Line: 13, column: 21
Statement: "y | 0"

Class A: Warning: Same operands. From Line: 14, column: 9 to Line: 14, column: 13
Statement: "z = z"

Class A: Warning: Empty block. From Line: 22, column: 13 to Line: 24, column: 13
Statement: "if (a) {
}"

Class A: Warning: Empty block. From Line: 35, column: 13 to Line: 37, column: 13
Statement: "if ((!c && e) || d) {
}"

Class A: Warning: Expression has always the same value. From Line: 22, column: 13 to Line: 24, column: 13
Statement: "if (a) {
}"
a has the value = true

Class A: Warning: Expression has always the same value. From Line: 35, column: 13 to Line: 37, column: 13
Statement: "if ((!c && e) || d) {
}"
!c has the value = false

Class A: Warning: Expression has always the same value. From Line: 35, column: 13 to Line: 37, column: 13
Statement: "if ((!c && e) || d) {
}"
c has the value = true

Class A: Warning: Expression has always the same value. From Line: 35, column: 13 to Line: 37, column: 13
Statement: "if ((!c && e) || d) {
}"
!c && e has the value = false

Class A: Warning: Expression has always the same value. From Line: 35, column: 13 to Line: 37, column: 13
Statement: "if ((!c && e) || d) {
}"
d has the value = true

Class A: Warning: Expression has always the same value. From Line: 35, column: 13 to Line: 37, column: 13
Statement: "if ((!c && e) || d) {
}"
(!c && e) || d has the value = true

Class C: Warning: Empty block. From Line: 45, column: 9 to Line: 47, column: 9
Statement: "if (1 == 0) {
}"
```

For your convenience we have put ***analyzer.jar*** to the
root of our project. 

### Capabilities

1) This analyzer can find empty blocks in conditions, loops,
try-catch and synchronized blocks.

2) We have implemented checks for some useful binary operation.

3) The tool checks for the same operand in binary expressions
like ```a = a``` and so on.

4) We believe that wildcard are not acceptable in most cases,
that's we warn you of any of them.

5) In big projects you always should specify package for
your classes, so our analyzer warns you if there is no
package.

6) If some expression in condition is known in advance and
is constant, we warn you about it.

### Example for spring-boot project

```
36 warnings were found:

Class org.springframework.boot.test.context.assertj.ApplicationContextAssert: Warning: Empty block. From Line: 209, column: 3 to Line: 210, column: 3
Statement: "catch (NoSuchBeanDefinitionException ex) {
}"

Class org.springframework.boot.test.context.SpringBootTestContextBootstrapper: Warning: Expression has always the same value. From Line: 174, column: 9 to Line: 178, column: 4
Statement: "if (webApplicationType == WebApplicationType.REACTIVE && (webEnvironment.isEmbedded() || webEnvironment == WebEnvironment.MOCK)) {
    return new ReactiveWebMergedContextConfiguration(mergedConfig);
}"
(webEnvironment.isEmbedded() || webEnvironment == WebEnvironment.MOCK) has the value = true

Class org.springframework.boot.test.json.AbstractJsonMarshalTester: Warning: Empty block. From Line: 307, column: 3 to Line: 308, column: 3
Statement: "catch (IOException ex) {
}"

Class org.springframework.boot.autoconfigure.cache.CacheCondition: Warning: Empty block. From Line: 64, column: 3 to Line: 65, column: 3
Statement: "catch (BindException ex) {
}"

Class org.springframework.boot.autoconfigure.web.servlet.error.DefaultErrorViewResolver: Warning: Empty block. From Line: 131, column: 4 to Line: 132, column: 4
Statement: "catch (Exception ex) {
}"

Class org.springframework.boot.autoconfigure.condition.BeanTypeRegistry: Warning: Empty block. From Line: 229, column: 3 to Line: 230, column: 3
Statement: "catch (Exception ex) {
}"

Class org.springframework.boot.autoconfigure.condition.BeanTypeRegistry: Warning: Empty block. From Line: 307, column: 3 to Line: 308, column: 3
Statement: "catch (Exception ex) {
}"

Class org.springframework.boot.testsupport.runner.classpath.ModifiedClassPathRunner: Warning: Empty block. From Line: 149, column: 4 to Line: 150, column: 4
Statement: "catch (Exception ex) {
}"

Class org.springframework.boot.configurationprocessor.json.JSONTokener: Warning: Empty block. From Line: 329, column: 3 to Line: 330, column: 3
Statement: "catch (NumberFormatException ignored) {
}"

Class org.springframework.boot.configurationprocessor.json.JSON: Warning: Empty block. From Line: 55, column: 4 to Line: 56, column: 4
Statement: "catch (NumberFormatException ignored) {
}"

Class org.springframework.boot.configurationprocessor.json.JSON: Warning: Empty block. From Line: 72, column: 4 to Line: 73, column: 4
Statement: "catch (NumberFormatException ignored) {
}"

Class org.springframework.boot.configurationprocessor.json.JSON: Warning: Empty block. From Line: 89, column: 4 to Line: 90, column: 4
Statement: "catch (NumberFormatException ignored) {
}"

Class org.springframework.boot.configurationprocessor.json.JSONObject: Warning: Empty block. From Line: 835, column: 3 to Line: 836, column: 3
Statement: "catch (Exception ignored) {
}"

Class org.springframework.boot.ant.FindMainClass: Warning: Expression has always the same value. From Line: 55, column: 4 to Line: 59, column: 4
Statement: "if (!StringUtils.hasText(mainClass)) {
    throw new BuildException("Could not determine main class given @classesRoot " + this.classesRoot);
}"
!StringUtils.hasText(mainClass) has the value = true

Class org.springframework.boot.loader.util.SystemPropertyUtils: Warning: Expression has always the same value. From Line: 122, column: 7 to Line: 124, column: 7
Statement: "if (propVal == null) {
    propVal = defaultValue;
}"
propVal == null has the value = true

Class org.springframework.boot.loader.jar.JarURLConnection: Warning: Expression has always the same value. From Line: 103, column: 4 to Line: 105, column: 4
Statement: "if (this.jarEntry == null) {
    throwFileNotFound(this.jarEntryName, this.jarFile);
}"
this.jarEntry == null has the value = true

Class org.springframework.boot.actuate.metrics.web.servlet.WebMvcMetricsFilterTests: Warning: Empty block. From Line: 196, column: 3 to Line: 197, column: 3
Statement: "catch (Throwable ignore) {
}"

Class org.springframework.boot.devtools.livereload.Frame: Warning: Useless operation. From Line: 80, column: 23 to Line: 80, column: 46
Statement: "this.payload.length >> 0"

Class org.springframework.boot.web.servlet.server.AbstractServletWebServerFactoryTests: Warning: Empty block. From Line: 1280, column: 4 to Line: 1281, column: 4
Statement: "catch (Exception ex) {
}"

Class org.springframework.boot.SpringApplicationTests: Warning: Empty block. From Line: 858, column: 3 to Line: 859, column: 3
Statement: "catch (IllegalStateException ex) {
}"

Class org.springframework.boot.SpringApplicationTests: Warning: Empty block. From Line: 883, column: 3 to Line: 884, column: 3
Statement: "catch (IllegalStateException ex) {
}"

Class org.springframework.boot.SpringApplicationTests: Warning: Empty block. From Line: 908, column: 3 to Line: 909, column: 3
Statement: "catch (RuntimeException ex) {
}"

Class org.springframework.boot.context.properties.source.DefaultPropertyMapper: Warning: Empty block. From Line: 78, column: 3 to Line: 79, column: 3
Statement: "catch (Exception ex) {
}"

Class org.springframework.boot.context.properties.bind.handler.NoUnboundElementsBindHandler: Warning: Empty block. From Line: 102, column: 4 to Line: 103, column: 4
Statement: "catch (Exception ex) {
}"

Class org.springframework.boot.web.servlet.AbstractFilterRegistrationBean: Warning: Expression has always the same value. From Line: 248, column: 4 to Line: 251, column: 4
Statement: "if (!servletNames.isEmpty()) {
    registration.addMappingForServletNames(dispatcherTypes, this.matchAfter, StringUtils.toStringArray(servletNames));
}"
!servletNames.isEmpty() has the value = false

Class org.springframework.boot.web.servlet.AbstractFilterRegistrationBean: Warning: Expression has always the same value. From Line: 248, column: 4 to Line: 251, column: 4
Statement: "if (!servletNames.isEmpty()) {
    registration.addMappingForServletNames(dispatcherTypes, this.matchAfter, StringUtils.toStringArray(servletNames));
}"
servletNames.isEmpty() has the value = true

Class org.springframework.boot.web.servlet.AbstractFilterRegistrationBean: Warning: Expression has always the same value. From Line: 252, column: 4 to Line: 255, column: 4
Statement: "if (!this.urlPatterns.isEmpty()) {
    registration.addMappingForUrlPatterns(dispatcherTypes, this.matchAfter, StringUtils.toStringArray(this.urlPatterns));
}"
!this.urlPatterns.isEmpty() has the value = false

Class org.springframework.boot.web.servlet.AbstractFilterRegistrationBean: Warning: Expression has always the same value. From Line: 252, column: 4 to Line: 255, column: 4
Statement: "if (!this.urlPatterns.isEmpty()) {
    registration.addMappingForUrlPatterns(dispatcherTypes, this.matchAfter, StringUtils.toStringArray(this.urlPatterns));
}"
this.urlPatterns.isEmpty() has the value = true

Class org.springframework.boot.web.servlet.AbstractFilterRegistrationBean: Warning: Expression has always the same value. From Line: 272, column: 4 to Line: 274, column: 4
Statement: "if (!this.servletNames.isEmpty()) {
    builder.append(" servlets=").append(this.servletNames);
}"
!this.servletNames.isEmpty() has the value = false

Class org.springframework.boot.web.servlet.AbstractFilterRegistrationBean: Warning: Expression has always the same value. From Line: 272, column: 4 to Line: 274, column: 4
Statement: "if (!this.servletNames.isEmpty()) {
    builder.append(" servlets=").append(this.servletNames);
}"
this.servletNames.isEmpty() has the value = true

Class org.springframework.boot.web.servlet.AbstractFilterRegistrationBean: Warning: Expression has always the same value. From Line: 275, column: 4 to Line: 277, column: 4
Statement: "if (!this.urlPatterns.isEmpty()) {
    builder.append(" urls=").append(this.urlPatterns);
}"
!this.urlPatterns.isEmpty() has the value = false

Class org.springframework.boot.web.servlet.AbstractFilterRegistrationBean: Warning: Expression has always the same value. From Line: 275, column: 4 to Line: 277, column: 4
Statement: "if (!this.urlPatterns.isEmpty()) {
    builder.append(" urls=").append(this.urlPatterns);
}"
this.urlPatterns.isEmpty() has the value = true

Class org.springframework.boot.web.servlet.server.DocumentRoot: Warning: Expression has always the same value. From Line: 69, column: 8 to Line: 71, column: 3
Statement: "if (this.logger.isDebugEnabled()) {
    this.logger.debug("Document root: " + file);
}"
this.logger.isDebugEnabled() has the value = true

Class org.springframework.boot.web.servlet.support.ErrorPageFilter: Warning: Empty block. From Line: 311, column: 3 to Line: 312, column: 3
Statement: "catch (Throwable ex) {
}"

Class org.springframework.boot.system.ApplicationHome: Warning: Empty block. From Line: 83, column: 4 to Line: 84, column: 4
Statement: "catch (Exception ex) {
}"

Class org.springframework.boot.system.ApplicationHome: Warning: Empty block. From Line: 115, column: 3 to Line: 116, column: 3
Statement: "catch (Exception ex) {
}"
```