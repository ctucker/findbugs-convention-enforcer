package com.cjtucker.findbugs.conventionenforcer;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.youdevise.fbplugins.tdd4fb.DetectorAssert;
import edu.umd.cs.findbugs.BugReporter;

@Ignore
public class IllegalExtensionOfFinalAnnotatedMethodTest {

	private BugReporter bugReporter;
	private IllegalExtensionOfFinalAnnotatedElement detector;

	@Before
	public void setUp() throws Exception {
		bugReporter = DetectorAssert.bugReporterForTesting();
		detector = new IllegalExtensionOfFinalAnnotatedElement(bugReporter);
	}


	@Test
	public void
	raisesABugAgainstMethodOverridingBaseMethodWithFinalAnnotation() throws Exception {
		DetectorAssert.assertBugReported(Samples.BadOverrideOfFinalMethod.class, detector, bugReporter);
	}

	@Test
	public void
	raisesNoBugAgainstMethodOverridingBaseMethodWithNoFinalAnnotation() throws Exception {
		DetectorAssert.assertNoBugsReported(Samples.GoodOverrideOfNonFinalMethod.class, detector, bugReporter);
	}

	@Test
	public void
	raisesNoBugAgainstOverrideOfOverrideDescendantMethod() throws Exception {
		// Might be better to report the error but gather it up; for now only reporting the parent is OK
		DetectorAssert.assertNoBugsReported(Samples.OverrideOfBadOverride.class, detector, bugReporter);
	}

	@Test
	public void
	raisesNoBugAgainstMethodThatDoesNotOverrideASuperClassMethod() throws Exception {
		DetectorAssert.assertNoBugsReported(Samples.ExtendingClassWithNoOverrides.class, detector, bugReporter);
	}


}
