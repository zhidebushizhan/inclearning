/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.inl;

import org.inl.corev1.*;
import org.inl.interfaces.*;
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

    
    //private IncrementalLearner incl;
    protected static int QUANTIDADE_INSTANCIAS = 3;
    protected static String DIR = "/home/daniel/Documentos/mestrado/mineracao/projeto";
    protected static int MAX_ITERACOES = 10;

    //dados para a rede neural, caso a escolha seja MLP com BackPropagation
    protected static double MOMENTUM = 0.1;
    protected static double TAXA_APRENDIZADO = 0.01;
    protected static int QTDE_CAMADAS_ESCONDIDAS = 1;
    protected static int QTDE_NEURONIOS_ESCONDIDOS = 3;

    public static void main(String[] args) {
        // TODO code application logic here


        ArrayList<Instances> grupoDados = new ArrayList<Instances>();
        IncrementalLearnerInterface inl = new IncrementalLearnerImpl();

        int qtde = 6;

        try {

            Instances data;

            for (int i = 0; i < qtde; i++) {

                data = new Instances(new BufferedReader(
                        new FileReader(DIR + "periodo" + (i + 1) + ".arff")));
                grupoDados.add(data);
                inl.adicionarInstancias(data);
            }


             //inl.iniciarTreinamento();
             //String sumario = inl.sumario();

        } catch (Exception e) {

            e.printStackTrace();
        }










    }

    public BaseSystem() {
        

    }
    
}
