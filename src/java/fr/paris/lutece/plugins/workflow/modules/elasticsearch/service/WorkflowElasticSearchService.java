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

import java.util.HashMap;
import java.util.Map;

/**
 * Class WorkflowElasticSearchService
 */
public final class WorkflowElasticSearchService
{

    public static final String BEAN = "bean";
    public static final String BEANHOME = "beanhome";
    public static final String DESCRIPTION = "description";
    private static WorkflowElasticSearchService _instance = new WorkflowElasticSearchService(  );
    private static Map<String, HashMap<String, String>> _classInformations = new HashMap<String, HashMap<String, String>>( );

    /**
     * private constructor to block multi instance
     */
    private WorkflowElasticSearchService ( )
    {
    }

    /**
     * get the instance
     * @return WorkflowElasticSearchService
     */
    public static WorkflowElasticSearchService getInstance ( )
    {
        return _instance;
    }

    /**
     * get the instance
     *  @param strBean the Bean ID to add
     *   @param strBeanHome the BEANHome ID to add
     *    @param strDescription the description to add
     * @return WorkflowElasticSearchService
     */
    public static WorkflowElasticSearchService getInstance ( String strBean, String strBeanHome, String strDescription )
    {
        addClassInformation( strBean, strBeanHome, strDescription );
        return _instance;
    }

    /**
     * @return the classInformations
     */
    public static Map<String, HashMap<String, String>> getClassInformations( )
    {
        return _classInformations;
    }

    /**
     * @param classInformations the classInformations to set
     */
    public static void setClassInformations( Map<String, Map<String, String>> classInformations )
    {
        WorkflowElasticSearchService._classInformations = _classInformations;
    }

    /**
     * @param strBean the Bean ID to add
     * @param classInformation the classInformation to add
     */
    public static void addClassInformation( String strBean, HashMap<String, String> classInformation )
    {
        if ( strBean != null )
        {
            WorkflowElasticSearchService._classInformations.put( strBean, classInformation );
        }
    }

    /**
     * @param strBean the Bean ID to add
     * @param strBeanHome the Bean Home ID to add
     * @param strDescription the description to add
     */
    public static void addClassInformation( String strBean, String strBeanHome, String strDescription )
    {
        if ( strBeanHome != null && strDescription != null )
        {
            Map<String, String> classInformation = new HashMap<String, String>( );
            classInformation.put( BEAN, strBean );
            classInformation.put( BEANHOME, strBeanHome );
            classInformation.put( DESCRIPTION, strDescription );
            addClassInformation( strBean, classInformation );
        }
    }

    /**
     * @param strBean the Bean ID to get
     * @return classes informations
     */
    public static Map<String, String> getClassInformation( String strBean )
    {
        if ( _classInformations.containsKey( strBean ) )
        {
            return _classInformations.get( strBean );
        }
        else
        {
            return null;
        }
    }
}