package org.broadinstitute.wdl.devtools;

import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginManager;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.application.PreloadingActivity;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.extensions.PluginId;
import com.intellij.openapi.progress.ProgressIndicator;
import org.jetbrains.annotations.NotNull;
import org.wso2.lsp4intellij.IntellijLanguageClient;
import org.wso2.lsp4intellij.client.languageserver.serverdefinition.ExeLanguageServerDefinition;

import java.io.IOException;
import java.util.Scanner;

public final class PluginPreloadingActivity extends PreloadingActivity {

    private static final Logger LOG = Logger.getInstance(PluginPreloadingActivity.class);

    private static final String PLUGIN_ID = "org.broadinstitute.wdl.devtools";

    private static final String BUNDLE = "WDL";

    private static final String EXTENSION = "wdl";
    private static final String PYTHON_PATH_PROPERTY = "wdl.pythonPath";
    private static final String PYTHON_PATH_DEFAULT = "/usr/local/bin/python3";
    private static final String SERVER_MODULE = "wdl_lsp";

    @Override
    public void preload(@NotNull ProgressIndicator indicator) {
        final IdeaPluginDescriptor plugin = PluginManager.getPlugin(PluginId.getId(PLUGIN_ID));

        setupLanguageServer(plugin);
    }

    private void setupLanguageServer(final IdeaPluginDescriptor plugin) {
        final boolean isDebug = "true".equals(System.getProperty("idea.is.internal"));
        final String pythonPath = PropertiesComponent.getInstance()
                .getValue(PYTHON_PATH_PROPERTY, PYTHON_PATH_DEFAULT);
        if (!isDebug &&
            !runProcess(pythonPath, "-m", "pip", "install", "--user", "wdl-lsp==" + plugin.getVersion())
        ) {
            return;
        }
        IntellijLanguageClient.addServerDefinition(
                new ExeLanguageServerDefinition(EXTENSION, pythonPath, new String[]{
                        "-m", SERVER_MODULE, "--log", isDebug ? "DEBUG" : "WARNING",
                })
        );
    }

    private boolean runProcess(final String... command) {
        try {
            final Process p = new ProcessBuilder(command).start();
            if (p.waitFor() != 0) {
                try(final Scanner s = new Scanner(p.getErrorStream(), "UTF-8")) {
                    final String stderr = s.useDelimiter("\\A").hasNext() ? s.next() : "";
                    throw new IOException(stderr);
                }
            };
        } catch (IOException | InterruptedException e) {
            LOG.error(e);
            return false;
        }
        return true;
    }
}
