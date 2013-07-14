package com.cjtucker.findbugs.conventionenforcer;

import static com.cjtucker.findbugs.conventionenforcer.Samples.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.cjtucker.findbugs.conventionenforcer.IllegalExtensionOfFinalAnnotatedClass;
import com.youdevise.fbplugins.tdd4fb.DetectorAssert;
import edu.umd.cs.findbugs.BugReporter;

@Ignore
public class IllegalExtensionOfFinalAnnotatedClassTest {

	private BugReporter bugReporter;
	private IllegalExtensionOfFinalAnnotatedClass detector;

	@Before
	public void setUp() throws Exception {
		bugReporter = DetectorAssert.bugReporterForTesting();
		detector = new IllegalExtensionOfFinalAnnotatedClass(bugReporter);
	}

	@Test
	public void
	raisesABugAgainsClassExtendingSuperclassWithFinalAnnotation() throws Exception {
		DetectorAssert.assertBugReported(Samples.BadClass.class, detector, bugReporter);
	}

	@Test
	public void
	raisesNoBugAgainstClassWithNoExplicitSuperclass() throws Exception {
		DetectorAssert.assertNoBugsReported(Samples.SafeToExtend.class, detector, bugReporter);
	}

	@Test
	public void
	raisesNoBugAgainstSubclassWithFinalAnnotation() throws Exception {
		DetectorAssert.assertNoBugsReported(Samples.FinalSubclass.class, detector, bugReporter);
	}


	@Test
	public void
	raisesNoBugAgainstClassWithSuperclassThatDoesNotHaveFinalAnnotation() throws Exception {
		DetectorAssert.assertNoBugsReported(Samples.GoodSubclass.class, detector, bugReporter);
	}


}
