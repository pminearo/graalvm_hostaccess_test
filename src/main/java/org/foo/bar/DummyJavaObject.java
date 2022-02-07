package org.foo.bar;

import org.apache.commons.lang3.StringUtils;
import org.graalvm.polyglot.HostAccess;

public class DummyJavaObject {

    @HostAccess.Export
    public DummyJavaObject() {

    }

    @HostAccess.Export
    public String reverseString(final String value) {

        return StringUtils.reverse(value);
    }

}