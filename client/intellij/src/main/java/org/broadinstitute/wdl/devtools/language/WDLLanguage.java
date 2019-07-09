package org.broadinstitute.wdl.devtools.language;

import com.intellij.lang.Language;

public class WDLLanguage extends Language {
    public static final WDLLanguage INSTANCE = new WDLLanguage();

    private WDLLanguage() {
        super("WDL");
    }
}
