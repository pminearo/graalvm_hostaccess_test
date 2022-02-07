package org.foo.bar;

import org.apache.commons.lang3.StringUtils;

@JsClass
public class LegacyDummyJavaObject {

    @JsFunction
    public String reverseString(final String value) {

        return StringUtils.reverse(value);
    }

}