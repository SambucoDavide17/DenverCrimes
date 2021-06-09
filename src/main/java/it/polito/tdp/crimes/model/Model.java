package it.polito.tdp.crimes.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.crimes.db.EventsDao;


public class Model {
	
	private Graph<String, DefaultWeightedEdge> grafo ;
	private EventsDao dao;
	
	public Model() {
		dao = new EventsDao();
	}
	
	public void creaGrafo(String categoria, int mese) {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(grafo, dao.getVertici(categoria, mese));
		
		for(Adiacenza a: dao.getArchi(categoria, mese)) {
			if(grafo.getEdge(a.getC1(), a.getC2()) == null) {
				Graphs.addEdge(grafo, a.getC1(), a.getC2(), a.getPeso());
			}
		}
	}
	
	public List<String> getCategorie(){
		return dao.getCategorie();
	}
	
	public List<Adiacenza> getSopraMedia(){
		int somma = 0;
		double media = 0;
		List<Adiacenza> sopraMedia = new ArrayList<>();
		for(DefaultWeightedEdge e: grafo.edgeSet()) {
			somma += grafo.getEdgeWeight(e);
		}
		media = somma/grafo.edgeSet().size();
		for(DefaultWeightedEdge e: grafo.edgeSet()) {
			if(grafo.getEdgeWeight(e) > media) {
				Adiacenza nuova = new Adiacenza(grafo.getEdgeSource(e), grafo.getEdgeTarget(e), grafo.getEdgeWeight(e));
				sopraMedia.add(nuova);
			}
		}
		return sopraMedia;
	}
}
