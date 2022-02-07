package org.foo.bar;

import org.apache.commons.lang3.StringUtils;
import org.graalvm.polyglot.HostAccess;

@JsClass()
public class ModifiedLegacyDummyJavaObject {

    @HostAccess.Export
    public ModifiedLegacyDummyJavaObject() {
    }

    @JsFunction
    public String reverseString(final String value) {

        return StringUtils.reverse(value);
    }

}