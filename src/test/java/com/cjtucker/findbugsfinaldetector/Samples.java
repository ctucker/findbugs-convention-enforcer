package com.cjtucker.findbugsfinaldetector;

public class Samples {

	@Final static class ShouldNotBeExtended {}

	static class BadClass extends ShouldNotBeExtended {}

	static class SafeToExtend {}

	static class GoodSubclass extends SafeToExtend {}

	@Final static class FinalSubclass {}


}
