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
package org.flowable.cmmn.engine.impl.cmd;

import org.flowable.cmmn.engine.CmmnEngineConfiguration;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;

/**
 * @author Joram Barrez
 */
public class DeleteDeploymentCmd implements Command<Void> {

    protected CmmnEngineConfiguration cmmnEngineConfiguration;
    
    protected String deploymentId;
    protected boolean cascade;

    public DeleteDeploymentCmd(String deploymentId, boolean cascade, CmmnEngineConfiguration cmmnEngineConfiguration) {
        this.deploymentId = deploymentId;
        this.cascade = cascade;
        this.cmmnEngineConfiguration = cmmnEngineConfiguration;
    }

    @Override
    public Void execute(CommandContext commandContext) {
        if (deploymentId == null) {
            throw new FlowableIllegalArgumentException("deploymentId is null");
        }
        cmmnEngineConfiguration.getDeploymentManager().removeDeployment(deploymentId, cascade);
        return null;
    }
}
