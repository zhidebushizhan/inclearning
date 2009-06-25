/*
 * Implementa o INL;
 */
package org.inl;

import java.util.ArrayList;
import weka.core.Instances;

/**
 *
 * @author daniel
 */
public class IncrementalLearner {

    private ArrayList<Instances> grupoDados;

    public IncrementalLearner(ArrayList<Instances> grupoDados) {
        this.grupoDados = grupoDados;

    }


    public void iniciarTreinamento(){


        //para cada grupo criar comitê

        for(Instances inst : grupoDados){

            criarComite(inst);

        }

 

    }



    public void criarComite(Instances periodo){



    }

}
