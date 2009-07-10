/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.inl.corev1;

import java.util.ArrayList;
import java.util.Random;
import org.inl.interfaces.IncrementalLearnerInterface;
import org.inl.interfaces.ReSamplerInterface;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instance;
import weka.core.Instances;

/**
 *
 * @author daniel
 */
public class IncrementalLearnerImpl implements IncrementalLearnerInterface {

    private ReSamplerInterface rs = new ReSamplerImpl();
    static boolean DEBUG = true;
    private ArrayList<Comite> comites = new ArrayList<Comite>();
    private ArrayList<Instances> periodos = new ArrayList<Instances>();

    private void inicializarPesos(Instances dados) {

        int tamanhoDados = dados.numInstances();

        for (int i = 0; i < tamanhoDados; i++) {

            dados.instance(i).setWeight(1 / tamanhoDados);

        }

    }

    public void adicionarNovoComite(Comite com) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Comite criarComite(Instances periodo) throws Exception {

        boolean criterioTreinamento = false;

        Instances coleta = null;
        Instances coletaAnterior = null;
        Classifier classificador = null;
        Classifier classificadorAnterior = null;
        double betaAnterior = 0.0;
        //erro do E1 anterior sobre os periodo atuais
        double erroE1Anterior = 0.0;
        double erroE1Atual = 0.0;
        double constNormalizacaoZanterior = 0.0;
        Comite comite = new Comite();

        double classe = 0;
        double predicao = 0;
        Instance instancia = null;


        this.inicializarPesos(periodo);

        int n = 0;

        while (!criterioTreinamento) {

            //coleta = rs.novaColeta(periodo);
             coleta = periodo.resampleWithWeights(new Random());

            if (n > 0 || !comite.getClassificadores().isEmpty()) {

                classificador = comite.getClassificadores().get(comite.getClassificadores().size() - 1);


            } else {
                //primeira rede
                classificador = treinarClassificador(coleta, "mlp");

            }

            erroE1Anterior = 0.0;
            //calcular erroE1Anterior
            for (int i = 0; i < coleta.numInstances(); i++) {

                instancia = coleta.instance(i);

                predicao = classificador.classifyInstance(instancia);
                classe = Double.valueOf(instancia.classAttribute().value(0));

                if (predicao != classe) {
                    erroE1Anterior = erroE1Anterior + instancia.weight();
                }

            }//fim for


             if (n > 0) {

                    classificador = treinarClassificador(coleta, "mlp");

                    erroE1Atual = 0.0;
                    //calcular erroE1Atual
                    for (int i = 0; i < coleta.numInstances(); i++) {

                        instancia = coleta.instance(i);

                        predicao = classificador.classifyInstance(instancia);
                        classe = Double.valueOf(instancia.classAttribute().value(0));

                        if (predicao != classe) {
                            erroE1Atual = erroE1Atual + instancia.weight();
                        }

                    }//fim for



                }//fim if



            if( n > 0 && ( erroE1Atual >= erroE1Anterior || erroE1Atual >= 0.5 )){

                 criterioTreinamento = true;//parar treinamento

            } else if (erroE1Anterior >= 0.5) {

                criterioTreinamento = true;//parar treinamento

            } else {

               //atualizar pesos

                //calcular constNormalizacaoZ

                constNormalizacaoZanterior = calculaZ(erroE1Anterior);

                //calcular betaAnterior
                betaAnterior = erroE1Anterior / (1 - erroE1Anterior);
                double novoPeso = 0.0;

                for (int i = 0; i < coleta.numInstances(); i++) {

                    instancia = coleta.instance(i);

                    predicao = classificador.classifyInstance(instancia);
                    classe = Double.valueOf(instancia.classAttribute().value(0));


                    if (predicao == classe) {
                        //atualiza Pesos
                        instancia.setWeight((instancia.weight() / constNormalizacaoZanterior) * betaAnterior);
                    } else {
                        instancia.setWeight(instancia.weight() / constNormalizacaoZanterior);
                    }


                }//fim for atualizacao pesos

                comite.addClassificador(classificador);

            }//fim else erroE1Anterior<0.5

          n++;

        }//fim where criterioTreinamento


        return comite;

    }

    public ArrayList<Comite> getSistemaExistente() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void removerNovoComite() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Comite treinarNovoComite(Instances novosDados) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean verificarSignificancia(Instances novosDados) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean verificarSignificanciaGeral(Instances novosDados) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int adicionarInstancias(Instances periodo) throws Exception {

        this.periodos.add(periodo);
        Comite comite = null;

        if (periodos.size() < 2) {

            comite = this.criarComite(periodo);
            return 2;//conceito aprendido

        } else if (this.verificarSignificancia(periodo)) {

            comite = this.criarComite(periodo);
            this.adicionarNovoComite(comite);

            if (this.verificarSignificanciaGeral(periodo)) {
                return 2;
            } else {
                this.removerNovoComite();
                return 1;//conceito descartado
            }


        } else {

            return 0;//conceito não significante
        }

    }

    private Classifier treinarClassificador(Instances dados, String tipo) throws Exception {

        Classifier classificador = null;

        if (tipo.equalsIgnoreCase("mlp")) {

            MultilayerPerceptron mlp = null;
            mlp = new MultilayerPerceptron();
            mlp.setAutoBuild(true);
            mlp.setMomentum(0.1);
            mlp.setHiddenLayers("2");//1 camada escondida com 2 neurônios
            mlp.setLearningRate(0.01);
            mlp.setTrainingTime(200);

            mlp.buildClassifier(dados);




        } else {
            throw new Exception("Apenas classificador mlp suportado");
        }


        return classificador;
    }

    private double calculaZ(double erroE1Anterior) {

        double z = ((1 / Math.sqrt(2 * Math.PI)) * Math.exp(-Math.pow(erroE1Anterior, 2.0) / 2));

        return z;

    }
}
