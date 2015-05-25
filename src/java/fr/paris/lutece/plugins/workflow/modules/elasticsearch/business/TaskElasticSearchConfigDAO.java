/*
 * Copyright (c) 2002-2015, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.workflow.modules.elasticsearch.business;

import fr.paris.lutece.plugins.workflow.modules.elasticsearch.service.WorkflowElasticSearchPlugin;
import fr.paris.lutece.plugins.workflowcore.business.config.ITaskConfigDAO;
import fr.paris.lutece.util.sql.DAOUtil;

/**
 * Class TaskElasticSearchConfigDAO
 */
public class TaskElasticSearchConfigDAO implements ITaskConfigDAO<TaskElasticSearchConfig>
{

    private static final String SQL_QUERY_INSERT = "INSERT INTO workflow_task_elasticsearch (id, `index`, bean_name) VALUES( ? , ? , ? )";
    private static final String SQL_QUERY_DELETE = "DELETE FROM workflow_task_elasticsearch WHERE id = ?";
    private static final String SQL_QUERY_FIND_BY_PRIMARY_KEY = "SELECT id, `index`, bean_name FROM workflow_task_elasticsearch WHERE id = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE workflow_task_elasticsearch SET `index` = ? , bean_name = ? WHERE id = ?";

    /* (non-Javadoc)
     * @see fr.paris.lutece.plugins.workflowcore.business.config.ITaskConfigDAO#delete(int)
     */
    @Override
    public void delete( int nId )
    {
        DAOUtil dao = new DAOUtil( SQL_QUERY_DELETE, WorkflowElasticSearchPlugin.getPlugin(  ) );
        dao.setInt( 1, nId );
        dao.executeUpdate( );
        dao.free(  );
    }

    /* (non-Javadoc)
     * @see fr.paris.lutece.plugins.workflowcore.business.config.ITaskConfigDAO#insert(fr.paris.lutece.plugins.workflowcore.business.config.ITaskConfig)
     */
    @Override
    public synchronized void insert( TaskElasticSearchConfig config )
    {
        DAOUtil dao = new DAOUtil( SQL_QUERY_INSERT, WorkflowElasticSearchPlugin.getPlugin(  ) );
        
        int nPos = 1;
        dao.setInt( nPos++, config.getId(  ) );
        dao.setString( nPos++, config.getIndex(  ) );
        dao.setString( nPos++, config.getBeanName(  ) );
        
        dao.executeUpdate(  );
        dao.free(  );
    }

    /* (non-Javadoc)
     * @see fr.paris.lutece.plugins.workflowcore.business.config.ITaskConfigDAO#load(int)
     */
    @Override
    public TaskElasticSearchConfig load( int nId )
    {
        int nPos = 1;
        TaskElasticSearchConfig config = null;
        DAOUtil dao = new DAOUtil( SQL_QUERY_FIND_BY_PRIMARY_KEY, WorkflowElasticSearchPlugin.getPlugin(  ) );
        dao.setInt( nPos, nId );
        dao.executeQuery(  );
        if ( dao.next(  ) )
        {
            config = new TaskElasticSearchConfig(  );
            config.setId( dao.getInt( nPos++ ) );
            config.setIndex( dao.getString( nPos++ ) );
            config.setBeanName( dao.getString( nPos ) );
        }
        dao.free(  );
        return config;
    }

    /* (non-Javadoc)
     * @see fr.paris.lutece.plugins.workflowcore.business.config.ITaskConfigDAO#store(fr.paris.lutece.plugins.workflowcore.business.config.ITaskConfig)
     */
    @Override
    public void store( TaskElasticSearchConfig config )
    {
        int nPos = 1;
        DAOUtil dao = new DAOUtil( SQL_QUERY_UPDATE, WorkflowElasticSearchPlugin.getPlugin(  ) );
        dao.setString( nPos++,  config.getIndex( ) );
        dao.setString( nPos++,  config.getBeanName( ) );
        dao.setInt( nPos,  config.getId(  ) );
        dao.executeUpdate(  );
        dao.free(  );
    }

}
