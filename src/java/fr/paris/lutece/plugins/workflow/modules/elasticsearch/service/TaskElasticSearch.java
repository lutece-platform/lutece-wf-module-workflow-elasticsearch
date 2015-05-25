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
package fr.paris.lutece.plugins.workflow.modules.elasticsearch.service;

import java.io.IOException;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import fr.paris.lutece.plugins.workflow.modules.elasticsearch.business.IWorkflowElasticSearchHome;
import fr.paris.lutece.plugins.workflow.modules.elasticsearch.business.TaskElasticSearchConfig;
import fr.paris.lutece.plugins.workflowcore.business.resource.ResourceHistory;
import fr.paris.lutece.plugins.workflowcore.service.config.ITaskConfigService;
import fr.paris.lutece.plugins.workflowcore.service.resource.IResourceHistoryService;
import fr.paris.lutece.plugins.workflowcore.service.task.SimpleTask;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;

/**
 * Class TaskElasticSearch
 */
public class TaskElasticSearch extends SimpleTask
{

    private static final String BEAN_ELASTICSEARCH_CONFIG_SERVICE = "workflow-elasticsearch.taskElasticSearchConfigService";

    private static final String MESSAGE_TITLE = "message title";

    // Service
    @Inject
    private IResourceHistoryService _resourceHistoryService;
    
    @Inject
    @Named( BEAN_ELASTICSEARCH_CONFIG_SERVICE )
    private ITaskConfigService _taskElasticSearchConfigService;


    /* (non-Javadoc)
     * @see fr.paris.lutece.plugins.workflowcore.service.task.ITask#doRemoveConfig()
     */
    @Override
    public void doRemoveConfig()
    {
_taskElasticSearchConfigService.remove( this.getId( ) );
    }


    /* (non-Javadoc)
     * @see fr.paris.lutece.plugins.workflowcore.service.task.ITask#getTitle(java.util.Locale)
     */
    @Override
    public String getTitle( Locale locale )
    {
        return I18nService.getLocalizedString( MESSAGE_TITLE, locale );
    }


    /* (non-Javadoc)
     * @see fr.paris.lutece.plugins.workflowcore.service.task.ITask#processTask(int, javax.servlet.http.HttpServletRequest, java.util.Locale)
     */
    @Override
    public void processTask( int nIdResourceHistory, HttpServletRequest request, Locale locale )
    {
        ResourceHistory history = _resourceHistoryService.findByPrimaryKey( nIdResourceHistory );
        TaskElasticSearchConfig config = _taskElasticSearchConfigService.findByPrimaryKey( this.getId( ) );
        IWorkflowElasticSearchHome objectHome = SpringContextService.getBean( config.getBeanName( ) );
        Object myObject = objectHome.getDataToIndexById( this.getId( ) );
        
        // index to Elastic Search
        if ( myObject != null )
        {
            String strHost = AppPropertiesService.getProperty( WorkflowElasticSearchPlugin.PROPERTY_SERVER_HOST );
                    int nPort = Integer.parseInt( AppPropertiesService.getProperty( WorkflowElasticSearchPlugin.PROPERTY_SERVER_PORT ) );
            Client client = new TransportClient( ).addTransportAddress( new InetSocketTransportAddress( strHost, nPort ) ); 
            ObjectMapper objectMapper = new ObjectMapper();
            String strSource = "";
            try
            {
                strSource = objectMapper.writeValueAsString( myObject );
            }
            catch ( JsonGenerationException e )
            {
                strSource = "{}";
            }
            catch ( JsonMappingException e )
            {
                strSource = "{}";
            }
            catch ( IOException e )
            {
                strSource = "{}";
            } 
            
            // preparing indexation
            IndexRequestBuilder index = client.prepareIndex( config.getIndex( ), "simple" )
                    .setId( Integer.toString( history.getId( ) ) )
                    .setSource( strSource );
            index.execute( ).actionGet( );
        }
    }
}
