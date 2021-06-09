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
	private List<String> percorsoBest;
	private List<String> vertici;
	
	public Model() {
		dao = new EventsDao();
	}
	
	public void creaGrafo(String categoria, int mese) {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		vertici = new ArrayList<>(dao.getVertici(categoria, mese));
		Graphs.addAllVertices(grafo, vertici);
		
		for(Adiacenza a: dao.getArchi(categoria, mese)) {
			if(grafo.getEdge(a.getC1(), a.getC2()) == null) {
				Graphs.addEdge(grafo, a.getC1(), a.getC2(), a.getPeso());
			}
		}
	}
	
	public List<String> getVertici() {
		return vertici;
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
	
	public List<String> percorsoMigliore(String partenza, String arrivo){
		percorsoBest = null;
		
		List<String> parziale = new ArrayList<>();
		parziale.add(partenza);
		
		cerca(parziale, arrivo);
		
		return percorsoBest;
	}
	
	public void cerca(List<String> parziale, String arrivo) {
		
		String ultimo = parziale.get(parziale.size()-1);
		if(arrivo.equals(ultimo)) {
			if(percorsoBest == null) {
				percorsoBest = new ArrayList<>(parziale);
				return;
			} else if( parziale.size() > this.percorsoBest.size() ) {
				percorsoBest = new ArrayList<>(parziale) ;
				return;
			} else {
				return;
			}
		}
		
		for(DefaultWeightedEdge e: this.grafo.edgesOf(ultimo)) {
			if(ultimo.equals(grafo.getEdgeTarget(e))) {
				String prossimo = grafo.getEdgeTarget(e);
				if(!parziale.contains(prossimo)) {
					parziale.add(prossimo);
					cerca(parziale, arrivo);
					parziale.remove(parziale.size()-1) ;
				}
			}
			if(ultimo.equals(grafo.getEdgeSource(e))) {
				String prossimo = grafo.getEdgeSource(e);
				if(!parziale.contains(prossimo)) {
					parziale.add(prossimo);
					cerca(parziale, arrivo);
					parziale.remove(parziale.size()-1) ;
				}
			}
		}
	}
}
