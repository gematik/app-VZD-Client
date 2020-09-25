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

package de.gematik.ti.epa.vzd.gem.command;

import de.gematik.ti.epa.vzd.gem.command.commandExecutions.AddDirEntryCertExecution;
import de.gematik.ti.epa.vzd.gem.command.commandExecutions.AddDirEntryExecution;
import de.gematik.ti.epa.vzd.gem.command.commandExecutions.DeleteDirEntryCertExecution;
import de.gematik.ti.epa.vzd.gem.command.commandExecutions.DeleteDirEntryExecution;
import de.gematik.ti.epa.vzd.gem.command.commandExecutions.ExecutionBase;
import de.gematik.ti.epa.vzd.gem.command.commandExecutions.ModifyDirEntryCertExecution;
import de.gematik.ti.epa.vzd.gem.command.commandExecutions.ModifyDirEntryExecution;
import de.gematik.ti.epa.vzd.gem.command.commandExecutions.ReadDirEntryCertExecution;
import de.gematik.ti.epa.vzd.gem.command.commandExecutions.ReadDirEntryExecution;
import de.gematik.ti.epa.vzd.gem.command.commandExecutions.SaveModifyDirEntryExecution;
import de.gematik.ti.epa.vzd.gem.invoker.IConnectionPool;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class that maintains all the specific Executions, have to be initialisized once with a GemApiClient
 */
public class ExecutionCollection {

    private static ExecutionCollection executionCollection;

    private final ReadDirEntryExecution readDirEntryExecution;
    private final ReadDirEntryCertExecution readDirEntryCertExecution;
    private final AddDirEntryExecution addDirEntryExecution;
    private final AddDirEntryCertExecution addDirEntryCertExecution;
    private final ModifyDirEntryExecution modifyDirEntryExecution;
    private final ModifyDirEntryCertExecution modifyDirEntryCertExecution;

    private static Logger LOG = LoggerFactory.getLogger(ExecutionCollection.class);

    private ArrayList<ExecutionBase> executors = new ArrayList<>();


    /**
     * Gives the instance as long as it exists.
     *
     * @return
     */
    public static ExecutionCollection getInstance() {
        if (executionCollection == null) {
            throw new InstantiationError("Please instance a executor first. It needs an ConnectionPool");
        }
        return executionCollection;
    }

    /**
     * Instances the executions
     *
     * @param connectionPool
     * @return
     */
    public static ExecutionCollection init(IConnectionPool connectionPool) {
        if (executionCollection != null) {
            LOG.error("Error occurred while initializing executions. Executor is already instanced");
            throw new InstantiationError("Executor is already instanced");
        }
        executionCollection = new ExecutionCollection(connectionPool);
        LOG.debug("Executiors have been initialized correctly");

        return executionCollection;
    }

    private ExecutionCollection(IConnectionPool connectionPool) {
        this.readDirEntryExecution = new ReadDirEntryExecution(connectionPool);
        this.readDirEntryCertExecution = new ReadDirEntryCertExecution(connectionPool);
        this.addDirEntryExecution = new AddDirEntryExecution(connectionPool);
        this.addDirEntryCertExecution = new AddDirEntryCertExecution(connectionPool);
        this.modifyDirEntryExecution = new ModifyDirEntryExecution(connectionPool);
        this.modifyDirEntryCertExecution = new ModifyDirEntryCertExecution(connectionPool);

        executors.add(readDirEntryExecution);
        executors.add(addDirEntryExecution);
        executors.add(modifyDirEntryExecution);
        executors.add(new DeleteDirEntryExecution(connectionPool));
        executors.add(readDirEntryCertExecution);
        executors.add(addDirEntryCertExecution);
        executors.add(modifyDirEntryCertExecution);
        executors.add(new DeleteDirEntryCertExecution(connectionPool));
        executors.add(new SaveModifyDirEntryExecution(connectionPool));
    }


    //<editor-fold desc="Getter">
    public void setExecutionCollection(ExecutionCollection executionCollection) {
        this.executionCollection = executionCollection;
    }

    public List<ExecutionBase> getExecutors() {
        return this.executors;
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
