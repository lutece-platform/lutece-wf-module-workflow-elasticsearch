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

import fr.paris.lutece.plugins.workflowcore.business.config.TaskConfig;

/**
 * Class TaskElasticSearchConfig
 */
public class TaskElasticSearchConfig extends TaskConfig
{
public static final String SERVICE_BEAN_NAME = "workflow-elasticsearch.taskElasticSearchConfigService";
private int _nId;
private String _strIndex;
private String _strBeanName;

/**
 * @return the nId
 */
public int getId()
{
    return _nId;
}

/**
 * @param nId the nId to set
 */
public void setId( int nId )
{
    _nId = nId;
}

/**
 * @return index
 */
public String getIndex( )
{
    return _strIndex;
}

/**
 * @param strIndex the Index to set
 */
public void setIndex( String strIndex )
{
    _strIndex = strIndex;
}

/**
 * @param strBeanName the Bean name to set
 */
public void setBeanName( String strBeanName )
{
    _strBeanName = strBeanName;
}

/**
 * @return the Bean Name
 */
public String getBeanName( )
{
    return _strBeanName;
}
}
