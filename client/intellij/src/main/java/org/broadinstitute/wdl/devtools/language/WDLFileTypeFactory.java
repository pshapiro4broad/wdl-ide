package org.broadinstitute.wdl.devtools.language;

import com.intellij.openapi.fileTypes.*;
import org.jetbrains.annotations.NotNull;

public class WDLFileTypeFactory extends FileTypeFactory {
    @Override
    public void createFileTypes(@NotNull FileTypeConsumer fileTypeConsumer) {
        fileTypeConsumer.consume(WDLFileType.INSTANCE);
    }
}