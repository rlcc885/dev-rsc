/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tida.servir.services;

import com.tida.servir.entities.Ant_Laborales;
import java.util.List;

/**
 *
 * @author ale
 */
public interface IAnt_LaboralesServiceRemote {
	Ant_Laborales findAnt_Laborales(Long id);

	List<Ant_Laborales> findAnt_Laborales(int maxResults);

	List<Ant_Laborales> findAnt_Laborales(String partialName, int maxResults);

	Ant_Laborales createAnt_Laborales(Ant_Laborales ant_Laborales);

	void createAnt_Laborales(List<Ant_Laborales> ant_Laborales);

	void changeAnt_Laborales(Ant_Laborales ant_Laborales);

	void deleteAnt_Laborales(Ant_Laborales ant_Laborales);

}
