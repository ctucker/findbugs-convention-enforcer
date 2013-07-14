package com.cjtucker.findbugs.conventionenforcer;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.asm.ClassNodeDetector;
import edu.umd.cs.findbugs.ba.XClass;
import edu.umd.cs.findbugs.classfile.CheckedAnalysisException;
import edu.umd.cs.findbugs.classfile.ClassDescriptor;
import edu.umd.cs.findbugs.classfile.Global;
import edu.umd.cs.findbugs.classfile.analysis.AnnotationValue;

public class IllegalExtensionOfFinalAnnotatedClass extends ClassNodeDetector {

	public IllegalExtensionOfFinalAnnotatedClass(BugReporter bugReporter) {
		super(bugReporter);
	}

	@Override
	public void visitClass(ClassDescriptor classDescriptor) throws CheckedAnalysisException {

		XClass thisClass = Global.getAnalysisCache().getClassAnalysis(XClass.class, classDescriptor);
		ClassDescriptor superclassDescriptor = thisClass.getSuperclassDescriptor();
		if (superclassDescriptor != null) {
			XClass superClass = Global.getAnalysisCache().getClassAnalysis(XClass.class, superclassDescriptor);
			for (AnnotationValue annotationValue : superClass.getAnnotations()) {
				if (annotationValue.getAnnotationClass().getClassName().endsWith("/Final")) {
					bugReporter.reportBug(new BugInstance("IEF_ILLEGAL_EXTENSION_OF_FINAL_CLASS", HIGH_PRIORITY)
							                      .addClass(classDescriptor)
							                      .addClass(superclassDescriptor));
				}
			}

		}

	}

}
