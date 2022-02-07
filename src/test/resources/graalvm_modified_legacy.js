var DummyObject = Java.type('org.foo.bar.ModifiedLegacyDummyJavaObject');
var dummy = new DummyObject();

var value = "abc";
var reversedValue = dummy.reverseString(value);

if (reversedValue != "cba") {
    throw "Reversed String failed Value: " + value + " != Reversed Value: " + reversedValue;
}

var SystemObject = Java.type('java.lang.System');

throw "Got System Object, getting Property 'java.version' = " + SystemObject.getProperty("java.version");