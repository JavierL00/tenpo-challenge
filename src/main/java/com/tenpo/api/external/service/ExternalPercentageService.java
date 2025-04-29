package com.tenpo.api.external.service;

import com.tenpo.api.external.exception.ExternalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExternalPercentageService {

	private final RestTemplate restTemplate;
	private final CacheManager cacheManager;

	private static final String RANDOM_API_URL = "http://localhost:9010/test";
	private static final String CACHE_NAME = "percentageCache";
	private static final String CACHE_KEY = "'com.tenpo.api.external.service.ExternalPercentageService.getPercentage'";

	public BigDecimal getPercentage() {
		log.info("Intentando obtener porcentaje...");
		try {
			BigDecimal percentage = callExternalService();
			putInCache(percentage); // guardar manualmente en cache
			return percentage;
		}
		catch (Exception e) {
			log.error("Error llamando a API externa: {}", e.getMessage());
			BigDecimal cached = getCachedPercentage();
			if (cached != null) {
				log.warn("Usando porcentaje cacheado: {}", cached);
				return cached;
			}
			else {
				log.error("No hay porcentaje cacheado disponible");
				throw new ExternalException("El servicio externo no está disponible y no existe porcentaje cacheado previo");
			}
		}
	}

	private BigDecimal callExternalService() {
		Integer response = restTemplate.getForObject(RANDOM_API_URL, Integer.class);
		if (response != null) {
			log.info("Porcentaje recibido de API externa: {}", response);
			return BigDecimal.valueOf(response);
		}
		else {
			throw new ExternalException("Respuesta vacía del servicio externo");
		}
	}

	private void putInCache(BigDecimal percentage) {
		Cache cache = cacheManager.getCache(CACHE_NAME);
		if (cache != null) {
			cache.put(CACHE_KEY, percentage);
			log.info("Porcentaje guardado en cache: {}", percentage);
		}
	}

	private BigDecimal getCachedPercentage() {
		Cache cache = cacheManager.getCache(CACHE_NAME);
		if (cache != null) {
			Cache.ValueWrapper wrapper = cache.get(CACHE_KEY);
			if (wrapper != null && wrapper.get() instanceof BigDecimal percentage) {
				return percentage;
			}
		}
		return null;
	}
}
