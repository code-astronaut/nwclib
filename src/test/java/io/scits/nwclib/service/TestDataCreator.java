package io.scits.nwclib.service;

public interface TestDataCreator<T, G> {
    T save(T entity);

    Long saveDomainModel(G domainModel);
}
