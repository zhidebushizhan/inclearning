/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.inl.interfaces;

import weka.core.Instances;

/**
 *
 * @author daniel
 */
public interface ReSamplerInterface {


    public Instances novaColeta(Instances dados) throws Exception;

}
