/*
 * Copyright (c) 2020 gematik GmbH
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.gematik.ti.epa.vzd.gemClient.command.commandExecutions;

import de.gematik.ti.epa.vzd.gemClient.invoker.GemApiClient;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class that maintains all the specific Executions, have to be initialisized once with a GemApiClient
 */
public class ExecutionCollection {

    private final ReadDirEntryExecution readDirEntryExecution;
    private final ReadDirEntryCertExecution readDirEntryCertExecution;
    private final AddDirEntryExecution addDirEntryExecution;
    private final AddDirEntryCertExecution addDirEntryCertExecution;
    private final ModifyDirEntryExecution modifyDirEntryExecution;
    private final ModifyDirEntryCertExecution modifyDirEntryCertExecution;

    private static Logger LOG = LoggerFactory.getLogger(ExecutionCollection.class);

    private ArrayList<ExecutionBase> specificExecutors = new ArrayList<>();

    private static ExecutionCollection executions;

    /**
     * Gives the instance as long as it exists.
     *
     * @return
     */
    public static ExecutionCollection getInstance() {
        if (executions == null) {
            throw new InstantiationError("Please instance a executor first. It needs an ApiClient");
        }
        return executions;
    }

    /**
     * Instances the executions
     *
     * @param apiClient
     * @return
     */
    public static ExecutionCollection init(GemApiClient apiClient) {
        if (executions != null) {
            LOG.error("Error occurred while initializing executions. Executor is already instanced");
            throw new InstantiationError("Executor is already instanced");
        }
        executions = new ExecutionCollection(apiClient);
        LOG.debug("Executions have been initialized correctly");

        return executions;
    }

    private ExecutionCollection(GemApiClient apiClient) {
        this.readDirEntryExecution = new ReadDirEntryExecution(apiClient);
        this.readDirEntryCertExecution = new ReadDirEntryCertExecution(apiClient);
        this.addDirEntryExecution = new AddDirEntryExecution(apiClient);
        this.addDirEntryCertExecution = new AddDirEntryCertExecution(apiClient);
        this.modifyDirEntryExecution = new ModifyDirEntryExecution(apiClient);
        this.modifyDirEntryCertExecution = new ModifyDirEntryCertExecution(apiClient);

        specificExecutors.add(readDirEntryExecution);
        specificExecutors.add(addDirEntryExecution);
        specificExecutors.add(modifyDirEntryExecution);
        specificExecutors.add(new DeleteDirEntryExecution(apiClient));
        specificExecutors.add(readDirEntryCertExecution);
        specificExecutors.add(addDirEntryCertExecution);
        specificExecutors.add(modifyDirEntryCertExecution);
        specificExecutors.add(new DeleteDirEntryCertExecution(apiClient));
    }


    //<editor-fold desc="Getter">
    public List<ExecutionBase> getExecutors() {
        return this.specificExecutors;
    }

    public ReadDirEntryExecution getReadDirEntryExecution() {
        return this.readDirEntryExecution;
    }

    public ReadDirEntryCertExecution getReadDirEntryCertExecution() {
        return this.readDirEntryCertExecution;
    }

    public AddDirEntryExecution getAddDirEntryExecution() {
        return this.addDirEntryExecution;
    }

    public AddDirEntryCertExecution getAddDirEntryCertExecution() {
        return this.addDirEntryCertExecution;
    }

    public ModifyDirEntryExecution getModifyDirEntry() {
        return this.modifyDirEntryExecution;
    }

    public ModifyDirEntryCertExecution getModifyDirEntryCertExecution() {
        return this.modifyDirEntryCertExecution;
    }
    //</editor-fold>
}
