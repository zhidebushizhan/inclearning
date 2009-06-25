/*
 * Implementa o INL;
 */
package org.inl;

import java.util.ArrayList;
import weka.classifiers.Classifier;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instances;

/**
 *
 * @author daniel
 */
public class IncrementalLearner {

    /**
     * grupo de Dados enviados pelo Base System
     */
    private ArrayList<Instances> grupoDados;
    /**
     * grupo de Dados a serem criados em cada iteracao do INL.
     * Cada novo grupo sera criado a partir das hipoteses com maior
     * peso, ou seja, que não foram classificadas corretamente pelo
     * modelo da iteração anterior.
     */
    private ArrayList<Instances> grupoDadosIteracao;
    /**
     * Sera responsavel pela recolera com reposicao
     */
    ReSampler sampler = new ReSampler();

    public IncrementalLearner(ArrayList<Instances> grupoDados, int maxIteracoes) {
        this.grupoDados = grupoDados;

    }

    public void iniciarTreinamento() throws Exception{

        //para cada grupo criar comitê

        Comite comite = null;
        Instances instanciasIteracao = null;
        ArrayList<Instances> grupoInstanciasIteracao = null;
        Classifier cModel = null;
        MultilayerPerceptron mlp = null;

         boolean primeiro = true;

        for (Instances inst : grupoDados) {

            if(!primeiro){

                //computar erro E1
                //se E1 for significante continuar

            }

            //inicializar pesos de cada instancia/registro
            inicializarPesos(inst);

            comite = new Comite();

            for (int i = 0; i < BaseSystem.MAX_ITERACOES; i++) {

                //na primeira iteracao, utiliza-se o grupo completo
                // a partir da segunda deve ser resampleado
                if (i != 0) {
                    instanciasIteracao = sampler.novaColeta(inst);


                } else {
                    instanciasIteracao = inst;
                }



                //treinar rede neural com dados coletados
                mlp = new MultilayerPerceptron();
                mlp.setAutoBuild(true);
                mlp.setMomentum(BaseSystem.MOMENTUM);
                mlp.setHiddenLayers("2");//1 camada escondida com 2 neurônios
                mlp.setLearningRate(BaseSystem.TAXA_APRENDIZADO);
                
                cModel = (Classifier) mlp;
                
                cModel.buildClassifier(instanciasIteracao);




            }
            //incializar pesos dos dados


        }//fim for Instances inst



    }

    public void criarComite(Instances periodo) {
    }

    public void inicializarPesos(Instances inst) {

        int qtdeInstancias = inst.numInstances();
        double pesoInicial = 1 / qtdeInstancias;

        for (int i = 0; i < qtdeInstancias; i++) {
            inst.instance(i).setWeight(pesoInicial);
        }



    }
}
