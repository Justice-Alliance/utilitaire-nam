package ca.qc.inspq.nam.api.specifications;

public interface Specification<T> {
	
	boolean estSatisfaitePar(T objet);
}
