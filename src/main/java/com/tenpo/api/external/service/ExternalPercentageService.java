package com.tenpo.api.external.service;

import com.tenpo.api.external.exception.ExternalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
@Slf4j
public class ExternalPercentageService {

	private final RestTemplate restTemplate;

	private static final String RANDOM_API_URL = "https://www.randomnumberapi.com/api/v1.0/random?min=1&max=20&count=1";

	@Cacheable(value = "percentageCache")
	public BigDecimal getPercentage() {
		try {
			log.info("Obteniendo el porcentaje de una API externa...");
			Integer[] response = restTemplate.getForObject(RANDOM_API_URL, Integer[].class);
			if (response != null && response.length > 0) {
				return BigDecimal.valueOf(response[0]);
			}
			else {
				throw new ExternalException("Respuesta vacía de la API externa");
			}
		}
		catch (Exception e) {
			log.error("Error al obtener el porcentaje de la API externa: {}", e.getMessage());
			throw new ExternalException("El servicio externo no está disponible", e);
		}
	}
}
