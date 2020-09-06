/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.flowable.engine.impl;

import java.io.Serializable;

import org.flowable.common.engine.api.management.TablePage;
import org.flowable.common.engine.api.management.TablePageQuery;
import org.flowable.common.engine.impl.db.ListQueryParameterObject;
import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.common.engine.impl.interceptor.CommandExecutor;
import org.flowable.engine.impl.cfg.ProcessEngineConfigurationImpl;

/**
 * 
 * @author Joram Barrez
 */
public class TablePageQueryImpl implements TablePageQuery, Command<TablePage>, Serializable {

    private static final long serialVersionUID = 1L;

    transient CommandExecutor commandExecutor;
    transient ProcessEngineConfigurationImpl engineConfiguration;

    protected String tableName;
    protected String order;
    protected int firstResult;
    protected int maxResults;

    public TablePageQueryImpl() {
    }

    public TablePageQueryImpl(CommandExecutor commandExecutor, ProcessEngineConfigurationImpl engineConfiguration) {
        this.commandExecutor = commandExecutor;
        this.engineConfiguration = engineConfiguration;
    }

    @Override
    public TablePageQueryImpl tableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    @Override
    public TablePageQueryImpl orderAsc(String column) {
        addOrder(column, ListQueryParameterObject.SORTORDER_ASC);
        return this;
    }

    @Override
    public TablePageQueryImpl orderDesc(String column) {
        addOrder(column, ListQueryParameterObject.SORTORDER_DESC);
        return this;
    }

    public String getTableName() {
        return tableName;
    }

    protected void addOrder(String column, String sortOrder) {
        if (order == null) {
            order = "";
        } else {
            order = order + ", ";
        }
        order = order + column + " " + sortOrder;
    }

    @Override
    public TablePage listPage(int firstResult, int maxResults) {
        this.firstResult = firstResult;
        this.maxResults = maxResults;
        return commandExecutor.execute(this);
    }

    @Override
    public TablePage execute(CommandContext commandContext) {
        return engineConfiguration.getTableDataManager().getTablePage(this, firstResult, maxResults);
    }

    public String getOrder() {
        return order;
    }

}
