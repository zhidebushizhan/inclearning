/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.inl;

import weka.core.Instances;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 *
 * @author daniel
 *
 * Sistema Base
 */
public class BaseSystem {

    private ArrayList<Instances> grupoDados;
    private IncrementalLearner incl;
    private static int QUANTIDADE_INSTANCIAS = 3;
    private static String DIR = "/home/daniel/Documentos/mestrado/mineracao/projeto";


    public static void main(String[] args) {
        // TODO code application logic here

        BaseSystem bs = new BaseSystem();

        try{

            bs.carregaInstancias(BaseSystem.QUANTIDADE_INSTANCIAS);
        
        }catch(Exception e){

            e.printStackTrace();
        }


           bs.iniciarIncLearner();


    }

    public BaseSystem() {
        this.grupoDados = new ArrayList<Instances>();

    }

    /**
     * @param args the command line arguments
     */
    private void carregaInstancias(int qtde) throws Exception {

        Instances data;

        for (int i = 0; i < qtde; i++) {

            data = new Instances(new BufferedReader(
                    new FileReader(DIR + "periodo" + (i + 1) + ".arff")));
          grupoDados.add(data);
        }





    }

    private void iniciarIncLearner(){

       incl  = new IncrementalLearner(grupoDados);

    }

}
