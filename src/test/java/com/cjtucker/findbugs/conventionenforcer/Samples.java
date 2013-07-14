package com.cjtucker.findbugs.conventionenforcer;

public class Samples {

	@Final
	static class ShouldNotBeExtended {}

	static class BadClass extends ShouldNotBeExtended {}

	static class SafeToExtend {}

	static class GoodSubclass extends SafeToExtend {}

	@Final static class FinalSubclass {}


	class WithFinalMethod {
		@Final void doNotOverride() {}
		void safeToOverride() {}
	}

	class BadOverrideOfFinalMethod extends WithFinalMethod {
		void doNotOverride() {}
	}

	class GoodOverrideOfNonFinalMethod extends WithFinalMethod {
		void safeToOverride() {}
	}

	class OverrideOfBadOverride extends BadOverrideOfFinalMethod {
		void doNotOverride() {}
	}

	class ExtendingClassWithNoOverrides extends WithFinalMethod {
		void notOverridingAnything() {}
	}

}
