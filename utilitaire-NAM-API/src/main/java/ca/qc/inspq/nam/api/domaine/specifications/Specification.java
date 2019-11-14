package ca.qc.inspq.nam.api.domaine.specifications;

public interface Specification<T> {
	
	boolean estSatisfaitePar(T objet);
}
