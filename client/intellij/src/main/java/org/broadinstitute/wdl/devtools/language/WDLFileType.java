package org.broadinstitute.wdl.devtools.language;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.broadinstitute.wdl.devtools.icons.WDLIcons;
import org.jetbrains.annotations.*;

import javax.swing.*;

public class WDLFileType extends LanguageFileType {
    public static final WDLFileType INSTANCE = new WDLFileType();

    private WDLFileType() {
        super(WDLLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "WDL file";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Workflow Description Language file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "wdl";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return WDLIcons.FILE;
    }
}
