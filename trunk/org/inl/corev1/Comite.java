/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.inl.corev1;


import java.util.ArrayList;
import weka.classifiers.Classifier;

/**
 *
 * @author daniel
 */
public class Comite {

   private ArrayList<Classifier> classificadores;

    /**
     *@param age idade do comite
     */

    private float age;

    /**
     * @param peso fator de peso para decisao do comite
     */
    private float peso;

    /**
     * Get the value of peso
     *
     * @return the value of peso
     */
    public float getPeso() {
        return peso;
    }

    /**
     * Set the value of peso
     *
     * @param peso new value of peso
     */
    public void setPeso(float peso) {
        this.peso = peso;
    }

    /**
     * Get the value of age
     *
     * @return the value of age
     */
    public float getAge() {
        return age;
    }

    /**
     * Set the value of age
     *
     * @param age new value of age
     */
    public void setAge(float age) {
        this.age = age;
    }

    /**
     * Adiciona classificador ao comite
     *
     * @param c Classificador a ser adicionado ao comite
     */

    public void addClassificador(Classifier c){
        this.classificadores.add(c);
    }

    public ArrayList<Classifier> getClassificadores() {
        return classificadores;
    }


    public Classifier getClassifier(int index){

     return this.classificadores.get(index);

    }

    public int getQtdeClassificadores(){

        return this.classificadores.size();

    }



}
