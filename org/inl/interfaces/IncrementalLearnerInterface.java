/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.inl.interfaces;

import org.inl.corev1.Comite;
import org.inl.*;
import java.util.ArrayList;
import weka.core.Instances;

/**
 *
 * @author daniel
 */
public interface IncrementalLearnerInterface {

//public void inicializarPesos(Instances instancias);



    public int adicionarInstancias(Instances dados) throws Exception;

    /*
  *Obtem o Sistema existente  - Comites já treinados
  */
  public ArrayList<Comite> getSistemaExistente() throws Exception;


/*
 * Computa o Erro E1 do sistema existente com novosDados e caso seja significante
 * retorna true, nao eh significante se E1>=0.5
 */
public boolean verificarSignificancia(Instances novosDados) throws Exception;



/*
 * Computa o Erro E2 do novo sistema com novosDados e caso seja significantemente
 * menor do que E1 retorna true
 */
public boolean verificarSignificanciaGeral(Instances novosDados) throws Exception;


/*
 * Treinar novo Comite com novosDados
 */
public Comite treinarNovoComite(Instances novosDados) throws Exception;

/*
 * Adiciona novo Comite ao sistema comites ArrayList<Comite.
 */
public void adicionarNovoComite(Comite com) throws Exception;


/*
 * Remove o ultimo comite. Essa operacao deve ser realizada se o
 * verificarSignificanciaGeral retornar false
 */
public void removerNovoComite() throws Exception;

/*
 * Cria comite a partir de dados
 */
public Comite criarComite(Instances periodo) throws Exception;




}


