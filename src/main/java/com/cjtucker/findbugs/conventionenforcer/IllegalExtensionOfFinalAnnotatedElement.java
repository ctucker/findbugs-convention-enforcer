package com.cjtucker.findbugs.conventionenforcer;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.asm.ClassNodeDetector;
import edu.umd.cs.findbugs.ba.XClass;
import edu.umd.cs.findbugs.ba.XMethod;
import edu.umd.cs.findbugs.classfile.CheckedAnalysisException;
import edu.umd.cs.findbugs.classfile.ClassDescriptor;
import edu.umd.cs.findbugs.classfile.Global;
import edu.umd.cs.findbugs.classfile.analysis.AnnotationValue;

public class IllegalExtensionOfFinalAnnotatedElement extends ClassNodeDetector {

	public IllegalExtensionOfFinalAnnotatedElement(BugReporter bugReporter) {
		super(bugReporter);
	}

	@Override
	public void visitClass(ClassDescriptor classDescriptor) throws CheckedAnalysisException {

		XClass thisClass = Global.getAnalysisCache().getClassAnalysis(XClass.class, classDescriptor);
		ClassDescriptor superclassDescriptor = thisClass.getSuperclassDescriptor();
		if (superclassDescriptor != null) {
			XClass superClass = Global.getAnalysisCache().getClassAnalysis(XClass.class, superclassDescriptor);
			reportClassErrors(classDescriptor, superclassDescriptor, superClass);
			reportMethodErrors(classDescriptor, thisClass, superClass);
		}

	}

	private void reportMethodErrors(ClassDescriptor classDescriptor, XClass thisClass, XClass superClass) {
		// Go through all of the methods, checking for parents with an annotation
		for (XMethod method : thisClass.getXMethods()) {
			XMethod superClassMethod = superClass.findMatchingMethod(method.getMethodDescriptor());
			if (superClassMethod != null) {
				for (AnnotationValue annotationValue : superClassMethod.getAnnotations()) {
					if (isFinalAnnotation(annotationValue)) {
						bugReporter.reportBug(
								new BugInstance("IEF_ILLEGAL_EXTENSION_OF_FINAL_METHOD", HIGH_PRIORITY)
										.addMethod(method)
										.addMethod(superClassMethod)
										.addClass(classDescriptor));
					}
				}
			}
		}
	}

	private void reportClassErrors(ClassDescriptor classDescriptor, ClassDescriptor superclassDescriptor, XClass superClass) {
		for (AnnotationValue annotationValue : superClass.getAnnotations()) {
			if (isFinalAnnotation(annotationValue)) {
				bugReporter.reportBug(new BugInstance("IEF_ILLEGAL_EXTENSION_OF_FINAL_CLASS", HIGH_PRIORITY)
						                      .addClass(classDescriptor)
						                      .addClass(superclassDescriptor));
			}
		}
	}


	private boolean isFinalAnnotation(AnnotationValue annotationValue) {
		return annotationValue.getAnnotationClass().getClassName().endsWith("/Final");
	}

}
