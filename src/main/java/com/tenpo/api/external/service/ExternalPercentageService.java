package com.tenpo.api.external.service;

import com.tenpo.api.external.exception.ExternalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExternalPercentageService {

	private final RestTemplate restTemplate;

	private static final String RANDOM_API_URL = "https://www.randomnumberapi.com/api/v1.0/random?min=1&max=20&count=1";

	@Cacheable(value = "percentageCache")
	@Retryable(value = {ExternalException.class}, backoff = @Backoff(delay = 1000))
	public BigDecimal getPercentage() {
		return fetchPercentage();
	}

	public BigDecimal fetchPercentage() {
		log.info("Obteniendo porcentaje (consultando servicio externo o usando cache)...");
		try {
			Integer[] response = restTemplate.getForObject(RANDOM_API_URL, Integer[].class);

			if (response == null) {
				log.error("Respuesta vacía del servicio externo");
				throw new ExternalException("Respuesta vacía del servicio externo");
			}

			log.info("Porcentaje recibido del servicio externo: {}", response[0]);
			return BigDecimal.valueOf(response[0]);
		}
		catch (Exception e) {
			log.error("Error al obtener porcentaje de la API externa: {}", e.getMessage());
			throw new ExternalException(e.getMessage());
		}
	}

	@Recover
	public BigDecimal recover(ExternalException e) {
		log.error("Todos los intentos fallaron. Retornando fallback.");
		throw e;
	}
}