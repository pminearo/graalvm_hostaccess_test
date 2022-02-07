# GraalVM Test Context Setup for HostAccess

This project shows what happens when you try to control the access JavaScript has on Java objects.

This could become a good example of how HostAccess works.  Feel free to create merge requests if you have examples to add. Please **DO NOT** modify the existing code; create your own subsets.

The GraalVMTest is trying to show that GraalVM is properly allowing access to the 'org.foo.bar.DummyJavaObject'/'org.foo.bar.LegacyDummyJavaObject' and not to 'java.lang.System'.  There is one Test in it that does exactly what is expected.  The rest all fail because of various reasons.
