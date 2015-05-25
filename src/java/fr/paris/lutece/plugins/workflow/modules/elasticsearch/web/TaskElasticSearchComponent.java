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
package fr.paris.lutece.plugins.workflow.modules.elasticsearch.web;

import java.util.Locale;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.workflow.modules.elasticsearch.business.TaskElasticSearchConfig;
import fr.paris.lutece.plugins.workflow.modules.elasticsearch.service.WorkflowElasticSearchPlugin;
import fr.paris.lutece.plugins.workflow.modules.elasticsearch.service.WorkflowElasticSearchService;
import fr.paris.lutece.plugins.workflow.web.task.NoFormTaskComponent;
import fr.paris.lutece.plugins.workflowcore.service.config.ITaskConfigService;
import fr.paris.lutece.plugins.workflowcore.service.task.ITask;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.util.html.HtmlTemplate;


/**
 * ClassTaskElasticSearchComponent       
 */
public class TaskElasticSearchComponent extends NoFormTaskComponent
{
    
    // Template
    private static final String TEMPLATE_TASK_ELASTICSEARCH_CONFIG = "admin/plugins/workflow/modules/elasticsearch/task_config.html";
    
    // MARKS
    private static final String MARK_CONFIG = "config";
    private static final String MARK_BEAN = "beans";

    //  Parameters
    private static final String PARAMETER_INDEX = "index";
    private static final String PARAMETER_BEAN_NAME = "bean_name";

    // Fields
    private static final String FIELD_INDEX = "module.workflow.elasticsearch.task_config.label_index";
    private static final String FIELD_BEAN_NAME = "module.workflow.elasticsearch.task_config.label_bean_name";

    // Messages
    private static final String MESSAGE_MANDATORY_FIELD = "module.workflow.elasticsearch.task_config.mandatory_field";
    private static final String MESSAGE_BAD_BEAN_NAME = "module.workflow.elasticsearch.task_config.bad_bean_name";
    private static final String MESSAGE_NO_PROPERTIES_SET = "module.workflow.elasticsearch.task_config.no_properties_set";
    
    //Service
    @Inject
    @Named( TaskElasticSearchConfig.SERVICE_BEAN_NAME )
    private ITaskConfigService _taskElasticSearchConfigService; 

    /* (non-Javadoc)
     * @see fr.paris.lutece.plugins.workflowcore.web.task.ITaskComponent#getDisplayConfigForm(javax.servlet.http.HttpServletRequest, java.util.Locale, fr.paris.lutece.plugins.workflowcore.service.task.ITask)
     */
    @Override
    public String getDisplayConfigForm( HttpServletRequest request, Locale locale,
            ITask task )
    {
        String strHost = AppPropertiesService.getProperty( WorkflowElasticSearchPlugin.PROPERTY_SERVER_HOST, null );
        int nPort = AppPropertiesService.getPropertyInt( WorkflowElasticSearchPlugin.PROPERTY_SERVER_PORT, 0 );
        TaskElasticSearchConfig config = _taskElasticSearchConfigService.findByPrimaryKey( task.getId( ) );
        Map<String, Object> model = new HashMap<String, Object>(  );

        if ( strHost != null && ! strHost.isEmpty( ) && nPort != 0 )
        {
            model.put( MARK_BEAN, WorkflowElasticSearchService.getClassInformations( ) );
        }
        model.put( MARK_CONFIG, config );
        
        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_TASK_ELASTICSEARCH_CONFIG, locale, model );
        return template.getHtml( );
    }

    /* (non-Javadoc)
     * @see fr.paris.lutece.plugins.workflowcore.web.task.ITaskComponent#getDisplayTaskInformation(int, javax.servlet.http.HttpServletRequest, java.util.Locale, fr.paris.lutece.plugins.workflowcore.service.task.ITask)
     */
    @Override
    public String getDisplayTaskInformation( int nIdHistory, HttpServletRequest request,
            Locale locale, ITask task )
    {
        return "";
    }

    /* (non-Javadoc)
     * @see fr.paris.lutece.plugins.workflowcore.web.task.ITaskComponent#getTaskInformationXml(int, javax.servlet.http.HttpServletRequest, java.util.Locale, fr.paris.lutece.plugins.workflowcore.service.task.ITask)
     */
    @Override
    public String getTaskInformationXml( int nIdHistory, HttpServletRequest request,
            Locale locale, ITask task )
    {
        return null;
    }
    
    @Override
    public String doSaveConfig( HttpServletRequest request, Locale locale, ITask task )
    {
        // check if properties are set...
        String strHost = AppPropertiesService.getProperty( WorkflowElasticSearchPlugin.PROPERTY_SERVER_HOST, null );
        int nPort = AppPropertiesService.getPropertyInt( WorkflowElasticSearchPlugin.PROPERTY_SERVER_PORT, 0 );
        if ( strHost != null && ! strHost.isEmpty( ) && nPort != 0 )
        {
            return AdminMessageService.getMessageUrl( request, MESSAGE_NO_PROPERTIES_SET, AdminMessage.TYPE_ERROR );
        }
        
        TaskElasticSearchConfig config = _taskElasticSearchConfigService.findByPrimaryKey( task.getId( ) );
        Boolean bIsNew = Boolean.FALSE;
        
        if ( config == null )
        {
            bIsNew = Boolean.TRUE;
            config = new TaskElasticSearchConfig( );
            config.setId( task.getId( ) );
        }
        
        String strIndex = request.getParameter( PARAMETER_INDEX );
        String strBeanName = request.getParameter( PARAMETER_BEAN_NAME );
        if ( strIndex == null || strIndex.isEmpty( ) )
        {
            Object[] required = { I18nService.getLocalizedString( FIELD_INDEX, locale ) };
            return AdminMessageService.getMessageUrl( request, MESSAGE_MANDATORY_FIELD, required, AdminMessage.TYPE_STOP );
        }
        
        if ( strBeanName == null || strBeanName.isEmpty( ) )
        {
            Object[] required = { I18nService.getLocalizedString( FIELD_BEAN_NAME, locale ) };
            return AdminMessageService.getMessageUrl( request, MESSAGE_MANDATORY_FIELD, required, AdminMessage.TYPE_STOP );
        }
        Object bean = SpringContextService.getBean( strBeanName );
        if ( bean == null )
        {
            Object[] required = { strBeanName };
            return AdminMessageService.getMessageUrl( request, MESSAGE_BAD_BEAN_NAME, required, AdminMessage.TYPE_STOP );
        }
        
        // index is set and class exists
        config.setIndex( strIndex );
        config.setBeanName( strBeanName );
        
        if ( bIsNew )
        {
            _taskElasticSearchConfigService.create( config );
        }
        else
        {
            _taskElasticSearchConfigService.update( config );
        }
        
        return null;
    }
}
