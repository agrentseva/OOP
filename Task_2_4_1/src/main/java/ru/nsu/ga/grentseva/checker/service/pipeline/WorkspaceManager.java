package ru.nsu.ga.grentseva.checker.service.pipeline;

import java.io.File;
import java.io.IOException;

public class WorkspaceManager {

    private static final String WORKSPACE_DIRECTORY = "checker_workspace";

    public File getWorkspace() throws IOException {
        File workspace = new File(WORKSPACE_DIRECTORY);

        if (!workspace.exists()) {
            boolean created = workspace.mkdirs();

            if (!created) {
                throw new IOException("Failed to create workspace directory: " + workspace.getAbsolutePath());
            }
        }

        return workspace;
    }
}